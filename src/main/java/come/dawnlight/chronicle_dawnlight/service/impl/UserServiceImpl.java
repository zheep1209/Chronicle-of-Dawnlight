package come.dawnlight.chronicle_dawnlight.service.impl;

import come.dawnlight.chronicle_dawnlight.common.Result;
import come.dawnlight.chronicle_dawnlight.common.Role;
import come.dawnlight.chronicle_dawnlight.common.Status;
import come.dawnlight.chronicle_dawnlight.common.exception.BaseException;
import come.dawnlight.chronicle_dawnlight.common.utils.RedisUtil;
import come.dawnlight.chronicle_dawnlight.mapper.RoleMapper;
import come.dawnlight.chronicle_dawnlight.mapper.UserMapper;
import come.dawnlight.chronicle_dawnlight.pojo.dto.UserDTO;
import come.dawnlight.chronicle_dawnlight.pojo.po.UserPO;
import come.dawnlight.chronicle_dawnlight.pojo.vo.UserVO;
import come.dawnlight.chronicle_dawnlight.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.UUID;

import static come.dawnlight.chronicle_dawnlight.common.utils.ValidationUtil.*;


@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Integer registerUser(UserDTO userDTO, String code) throws BaseException {
//        格式校验
        // 校验用户名
        if (!isValidUsername(userDTO.getUsername())) {
            throw new BaseException("无效的用户名。用户名必须是3-20个字符，且只能包含字母、数字、下划线和短横线。");
        }
        // 校验用户名是否重复
        UserPO temp = new UserPO();
        temp.setUsername(userDTO.getUsername());
        if (userMapper.select(temp)!=null)
            throw new BaseException("用户名"+userDTO.getUsername()+"重复");
        // 校验邮箱
        if (!isValidEmail(userDTO.getEmail())) {
            throw new BaseException("无效的邮箱格式.");
        }
        // 校验邮箱是否重复
        temp = new UserPO();
        temp.setUsername(userDTO.getEmail());
        if (userMapper.select(temp)!=null)
            throw new BaseException("该邮箱已注册");

        // 校验密码
        if (!isValidPassword(userDTO.getPassword())) {
            throw new BaseException("无效的密码。密码必须是6-20个字符。");
        }
        // 校验验证码
        if (code.isEmpty()) {
            throw new BaseException("请输入验证码");
        }
        if (!code.equals(redisUtil.get("code"))) {
            throw new BaseException("验证码错误");
        }
        UserPO userPO = new UserPO();
        BeanUtils.copyProperties(userDTO, userPO);
//        UUID
        userPO.setId(UUID.randomUUID().toString());
//        默认头像
        userPO.setAvatar("https://img.picgo.net/2024/10/25/d693c6f605470263c9e42740e8bed7d2909387c156c4a646.png");
//        密码加密
        userPO.setPassword(DigestUtils.md5DigestAsHex(userDTO.getPassword().getBytes()));
//        新增状态
        userPO.setStatus(Status.NORMAL);
//        新增身份
        userPO.setRole(Role.ORDINARY);
//        创建时间
        userPO.setCreatedAt(LocalDateTime.now());
//        修改时间
        userPO.setUpdatedAt(LocalDateTime.now());
        log.info("Registering user: {}", userPO);
        return userMapper.registerUser(userPO);
    }

    @Override
    public Result loginByPassword(@Param("identifier") String identifier, @Param("password") String password) {
        String loginResult = userMapper.loginByPassword(identifier, DigestUtils.md5DigestAsHex(password.getBytes()));
        if (loginResult == null) {
            log.info("用户名或密码错误");
            return Result.error("用户名或密码错误");
        }else {
            log.info("登陆成功");
            return Result.success(loginResult);
        }
    }

    @Override
    public Result loginByEmail(String identifier,String code) throws BaseException {
        String result =  userMapper.selectByEmail(identifier);
        if (result == null || result.isEmpty()){
            return Result.error("该邮箱暂未注册");
        }
        // 校验验证码
        if (code.isEmpty()) {
            throw new BaseException("请输入验证码");
        }
        if (!code.equals(redisUtil.get("code"))) {
            throw new BaseException("验证码错误");
        }
        return Result.success(result);
    }
    //修改头像
    @Override
    public void updateAvatar(String id,String avatar) {
        UserPO userPO = new UserPO();
        userPO.setId(id);
        userPO.setAvatar(avatar);
        userPO.setUpdatedAt(LocalDateTime.now());
        userMapper.update(userPO);
    }
    //修改用户名
    @Override
    public void updateUserName(String id,String username) throws BaseException {
        // 校验用户名是否重复
        UserPO temp = new UserPO();
        temp.setUsername(username);
        if (userMapper.select(temp)!=null)
            throw new BaseException("用户名"+username+"重复");
        UserPO userPO = new UserPO();
        userPO.setId(id);
        userPO.setUsername(username);
        userPO.setUpdatedAt(LocalDateTime.now());
        userMapper.update(userPO);
    }
    //修改密码
    @Override
    public void updatePassward(String id,String password,String code) throws BaseException {
        // 校验验证码
        if (code.isEmpty()) {
            throw new BaseException("请输入验证码");
        }
        if (!code.equals(redisUtil.get("code"))) {
            throw new BaseException("验证码错误");
        }
        UserPO userPO = new UserPO();
        userPO.setId(id);
        userPO.setPassword(password);
        userPO.setUpdatedAt(LocalDateTime.now());
        userMapper.update(userPO);
    }

    @Override
    public Result getUser(UUID currentThreadId) {
        return Result.success(userMapper.getUser(currentThreadId.toString()));
    }
}

package come.dawnlight.chronicle_dawnlight.controller;

import come.dawnlight.chronicle_dawnlight.common.Result;
import come.dawnlight.chronicle_dawnlight.common.exception.BaseException;
import come.dawnlight.chronicle_dawnlight.common.utils.BaseContext;
import come.dawnlight.chronicle_dawnlight.common.utils.JwtUtil;
import come.dawnlight.chronicle_dawnlight.mapper.UserMapper;
import come.dawnlight.chronicle_dawnlight.pojo.dto.UserDTO;
import come.dawnlight.chronicle_dawnlight.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/user")
@Slf4j
public class UserController {
    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
//    @Autowired
//    private  JwtUtil jwtUtil;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO, String code) throws BaseException {
        log.info("新增用户{}", userDTO);
        Integer result = userService.registerUser(userDTO, code);
        if (result == 1) {
            return Result.success();
        } else return Result.error("添加失败");
    }

    /**
     * 用户名密码登录
     */
    @GetMapping("/loginWithPassword")
    public Result loginWithPassword(@RequestParam String username, @RequestParam String password) {

        // 处理用户名 + 密码的登录逻辑
        Result result = userService.loginByPassword(username, password);
        System.out.println(result);
        Map<String, Object> data = new HashMap<>();
        if (result.getCode() == 1) {
            //登录成功后，生成jwt令牌
            return getResult(result, data);
        } else {
            return Result.error(result.getMsg());
        }
    }

    /**
     * 邮箱登录
     */
    @GetMapping("/loginWithEmail")
    public Result loginWithEmail(@RequestParam String email, @RequestParam String code) throws BaseException {
        // 处理邮箱 + 验证码的登录逻辑
        Result result = userService.loginByEmail(email, code);
        if (result.getCode() == 1) {
            result.setData(userMapper.selectByUserName((String) result.getData()));
            //登录成功后，生成jwt令牌
            Map<String, Object> data = new HashMap<>();
            log.info("测试测试{},{}", result, data);
            return getResult(result, data);
        } else {
            return Result.error(result.getMsg());
        }
    }

    //提取生成Jwt方法
    private Result getResult(Result result, Map<String, Object> data) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", result.getData());
        String token = JwtUtil.createJWT("zheep", 30L * 24 * 60 * 60 * 1000, claims);
        data.put("Token", token);
        data.put("id", result.getData());
        return Result.success(data);
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/getUser")
    public Result getUser() {
        return userService.getUser(BaseContext.getCurrentThreadId());
    }

    /**
     * 更新数据
     */
//    同时传多个需要修改的值可能有漏洞，需要修复
    @PutMapping("/update")
    public Result update(@RequestParam(value = "id") String id, @RequestParam(value = "avatar", required = false) String avatar, @RequestParam(value = "password", required = false) String password, @RequestParam(value = "username", required = false) String username, @RequestParam(value = "code", required = false) String code) throws BaseException {
        if (avatar != null) {
            userService.updateAvatar(id, avatar);
        }
        if (username != null) {
            userService.updateUserName(id, username);
        }
        if (password != null) {
            userService.updatePassward(id, username, code);
        }
        return Result.error("请输入数据");
    }
}

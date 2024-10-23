package come.dawnlight.chronicle_dawnlight.controller;

import come.dawnlight.chronicle_dawnlight.common.Result;
import come.dawnlight.chronicle_dawnlight.common.exception.BaseException;
import come.dawnlight.chronicle_dawnlight.pojo.dto.UserDTO;
import come.dawnlight.chronicle_dawnlight.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

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
    @PostMapping("/loginWithPassword")
    public Result loginWithPassword(@RequestParam String username, @RequestParam String password) {
        // 处理用户名 + 密码的登录逻辑
        return userService.loginByPassword(username, password);
    }

    /**
     * 邮箱登录
     */
    @PostMapping("/loginWithEmail")
    public Result loginWithEmail(@RequestParam String email, @RequestParam String code) throws BaseException {
        // 处理邮箱 + 验证码的登录逻辑
        return userService.loginByEmail(email, code);
    }

    /**
     * 更新数据
     */
    @PostMapping("/update")
    public Result update(@Param("id") String id,
                         @Param("avatar") String avatar,
                         @Param("password") String password,
                         @Param("username") String username,
                         @Param("code") String code) throws BaseException {
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

package com.gameshare.controller;

import com.gameshare.dto.Result;
import com.gameshare.entity.User;
import com.gameshare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<User> login(@RequestBody Map<String, String> params) {
        String code = params.get("code");
        if (code == null || code.isEmpty()) {
            return Result.fail("code不能为空");
        }
        // 实际应该用code换取openid，这里简化处理
        // 微信小程序登录流程: wx.login() -> code -> 后端换取openid/session
        // 这里暂时用code作为openid演示
        User user = userService.login(code);
        return Result.success(user);
    }

    @GetMapping("/userinfo")
    public Result<User> userinfo(@RequestParam Long userId) {
        User user = userService.getUserInfo(userId);
        if (user == null) {
            return Result.fail("用户不存在");
        }
        return Result.success(user);
    }
}

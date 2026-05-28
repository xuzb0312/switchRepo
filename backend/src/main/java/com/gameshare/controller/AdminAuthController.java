package com.gameshare.controller;

import com.gameshare.entity.Admin;
import com.gameshare.mapper.AdminMapper;
import com.gameshare.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/auth")
@CrossOrigin
public class AdminAuthController {

    @Autowired
    private AdminMapper adminMapper;

    // 简单token存储（生产环境用Redis）
    private static String currentToken = null;
    private static Long tokenExpireTime = null;
    private static final long TOKEN_VALID_MS = 7 * 24 * 60 * 60 * 1000L; // 7天

    @PostMapping("/login")
    public Result<Map<String, String>> login(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        
        if (username == null || password == null) {
            return Result.fail("用户名或密码不能为空");
        }

        Admin admin = adminMapper.selectOne(
            new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<Admin>()
                .eq(Admin::getUsername, username)
        );

        if (admin == null) {
            return Result.fail("用户不存在");
        }

        // 简单密码验证（生产环境用BCrypt）
        if (!password.equals("admin123")) {
            return Result.fail("密码错误");
        }

        // 生成token
        String token = UUID.randomUUID().toString().replace("-", "");
        currentToken = token;
        tokenExpireTime = System.currentTimeMillis() + TOKEN_VALID_MS;

        return Result.success(Map.of(
            "token", token,
            "username", admin.getNickname() != null ? admin.getNickname() : admin.getUsername(),
            "role", admin.getRole()
        ));
    }

    @GetMapping("/check")
    public Result<Boolean> check(@RequestHeader(value = "Authorization", required = false) String token) {
        if (token == null || !token.equals(currentToken) || System.currentTimeMillis() > tokenExpireTime) {
            return Result.success(false);
        }
        return Result.success(true);
    }

    @PostMapping("/logout")
    public Result<Void> logout() {
        currentToken = null;
        tokenExpireTime = null;
        return Result.success();
    }

    // 获取当前管理员信息
    @GetMapping("/info")
    public Result<Map<String, String>> info(@RequestHeader(value = "Authorization", required = false) String token) {
        if (token == null || !token.equals(currentToken) || System.currentTimeMillis() > tokenExpireTime) {
            return Result.fail("未登录");
        }
        return Result.success(Map.of("username", "admin", "role", "superadmin"));
    }
}

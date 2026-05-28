package com.gameshare.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gameshare.entity.*;
import com.gameshare.mapper.*;
import com.gameshare.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private GameMapper gameMapper;
    @Autowired
    private BannerMapper bannerMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UnlockRecordMapper unlockRecordMapper;

    @GetMapping("/stats")
    public Result<Map<String, Object>> stats(
            @RequestHeader(value = "Authorization", required = false) String token) {
        if (!isValidToken(token)) {
            return Result.fail("未登录");
        }
        
        Map<String, Object> stats = new HashMap<>();
        
        // 游戏统计
        long totalGames = gameMapper.selectCount(null);
        stats.put("totalGames", totalGames);
        
        // 总浏览量
        long totalViews = gameMapper.selectList(null).stream()
            .mapToLong(g -> g.getViewCount() != null ? g.getViewCount() : 0)
            .sum();
        stats.put("totalViews", totalViews);
        
        // 用户统计
        long totalUsers = userMapper.selectCount(null);
        stats.put("totalUsers", totalUsers);
        
        // Banner统计
        long totalBanners = bannerMapper.selectCount(
            new LambdaQueryWrapper<Banner>().eq(Banner::getStatus, 1));
        stats.put("totalBanners", totalBanners);
        
        // 解锁统计
        long totalUnlocks = unlockRecordMapper.selectCount(null);
        stats.put("totalUnlocks", totalUnlocks);
        
        // 今日解锁
        java.time.LocalDate today = java.time.LocalDate.now();
        java.time.LocalDateTime startOfDay = today.atStartOfDay();
        long todayUnlocks = unlockRecordMapper.selectCount(
            new LambdaQueryWrapper<UnlockRecord>()
                .ge(UnlockRecord::getCreateTime, startOfDay));
        stats.put("todayUnlocks", todayUnlocks);
        
        return Result.success(stats);
    }

    @GetMapping("/unlock-records")
    public Result<List<UnlockRecord>> unlockRecords(
            @RequestHeader(value = "Authorization", required = false) String token) {
        if (!isValidToken(token)) {
            return Result.fail("未登录");
        }
        
        List<UnlockRecord> records = unlockRecordMapper.selectList(
            new LambdaQueryWrapper<UnlockRecord>()
                .orderByDesc(UnlockRecord::getCreateTime)
                .last("LIMIT 100")
        );
        
        return Result.success(records);
    }

    private boolean isValidToken(String token) {
        if (token == null || token.isEmpty()) return false;
        // 简化验证，实际应该查数据库或Redis
        return token.length() > 10;
    }
}

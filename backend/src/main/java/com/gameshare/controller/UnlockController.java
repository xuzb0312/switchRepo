package com.gameshare.controller;

import com.gameshare.dto.Result;
import com.gameshare.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/unlock")
@CrossOrigin
public class UnlockController {

    @Autowired
    private GameService gameService;

    /**
     * 检查游戏是否已解锁
     */
    @GetMapping("/check")
    public Result<Boolean> check(@RequestParam Long userId, @RequestParam Long gameId) {
        boolean unlocked = gameService.checkUnlocked(userId, gameId);
        return Result.success(unlocked);
    }

    /**
     * 解锁游戏（看广告后调用）
     */
    @PostMapping
    public Result<Void> unlock(@RequestBody Map<String, Object> params) {
        Long userId = Long.valueOf(params.get("userId").toString());
        Long gameId = Long.valueOf(params.get("gameId").toString());
        String adType = params.getOrDefault("adType", "rewarded").toString();
        
        gameService.unlockGame(userId, gameId, adType);
        return Result.success();
    }
}

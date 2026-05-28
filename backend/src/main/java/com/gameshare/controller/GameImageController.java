package com.gameshare.controller;

import com.gameshare.dto.Result;
import com.gameshare.entity.GameImage;
import com.gameshare.mapper.GameImageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/games/{gameId}/images")
@CrossOrigin
public class GameImageController {

    @Autowired
    private GameImageMapper gameImageMapper;

    @GetMapping
    public Result<List<GameImage>> list(@PathVariable Long gameId) {
        List<GameImage> images = gameImageMapper.selectList(
            new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<GameImage>()
                .eq(GameImage::getGameId, gameId)
                .orderByAsc(GameImage::getSort)
        );
        return Result.success(images);
    }

    @PostMapping
    public Result<Void> add(@PathVariable Long gameId, @RequestBody Map<String, String> params) {
        GameImage image = new GameImage();
        image.setGameId(gameId);
        image.setUrl(params.get("url"));
        image.setSort(Integer.parseInt(params.getOrDefault("sort", "0")));
        gameImageMapper.insert(image);
        return Result.success();
    }

    @PutMapping
    public Result<Void> updateAll(@PathVariable Long gameId, @RequestBody List<Map<String, String>> images) {
        // 先删后插
        gameImageMapper.delete(
            new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<GameImage>()
                .eq(GameImage::getGameId, gameId)
        );
        for (int i = 0; i < images.size(); i++) {
            Map<String, String> img = images.get(i);
            GameImage image = new GameImage();
            image.setGameId(gameId);
            image.setUrl(img.get("url"));
            image.setSort(i);  // 使用数组索引作为排序
            gameImageMapper.insert(image);
        }
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        gameImageMapper.deleteById(id);
        return Result.success();
    }
}

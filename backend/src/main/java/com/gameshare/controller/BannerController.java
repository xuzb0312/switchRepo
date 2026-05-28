package com.gameshare.controller;

import com.gameshare.dto.Result;
import com.gameshare.entity.Banner;
import com.gameshare.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/banners")
@CrossOrigin
public class BannerController {

    @Autowired
    private GameService gameService;

    @GetMapping
    public Result<List<Banner>> list() {
        return Result.success(gameService.getBanners());
    }
}

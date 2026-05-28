package com.gameshare.controller;

import com.gameshare.entity.Game;
import com.gameshare.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/games")
@CrossOrigin
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping
    public Result<List<Game>> list(@RequestParam(required = false) String platform,
                                    @RequestParam(required = false) String keyword) {
        return Result.success(gameService.listGames(platform, keyword));
    }

    @GetMapping("/{id}")
    public Result<Game> detail(@PathVariable Long id) {
        return Result.success(gameService.getGameDetail(id));
    }

    @PostMapping
    public Result<Void> add(@RequestBody Game game) {
        gameService.addGame(game);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result<Void> update(@RequestBody Game game) {
        gameService.updateGame(game);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        gameService.deleteGame(id);
        return Result.success();
    }
}

class Result<T> {
    public int code;
    public String msg;
    public T data;
    public static <T> Result<T> success(T data) {
        Result<T> r = new Result<>();
        r.code = 200;
        r.msg = "success";
        r.data = data;
        return r;
    }
}

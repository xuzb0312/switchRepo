package com.gameshare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gameshare.entity.DownloadLink;
import com.gameshare.entity.Game;
import com.gameshare.mapper.DownloadLinkMapper;
import com.gameshare.mapper.GameMapper;
import com.gameshare.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameMapper gameMapper;
    @Autowired
    private DownloadLinkMapper downloadLinkMapper;

    @Override
    public List<Game> listGames(String platform, String keyword) {
        LambdaQueryWrapper<Game> wrapper = new LambdaQueryWrapper<>();
        if (platform != null && !platform.isEmpty()) {
            wrapper.eq(Game::getPlatform, platform);
        }
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(Game::getName, keyword);
        }
        wrapper.orderByDesc(Game::getCreateTime);
        return gameMapper.selectList(wrapper);
    }

    @Override
    public Game getGameDetail(Long id) {
        Game game = gameMapper.selectById(id);
        if (game != null) {
            LambdaQueryWrapper<DownloadLink> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(DownloadLink::getGameId, id).orderByAsc(DownloadLink::getSort);
            // 待补充：注入到 game.images 等
        }
        return game;
    }

    @Override
    public void addGame(Game game) {
        gameMapper.insert(game);
    }

    @Override
    public void updateGame(Game game) {
        gameMapper.updateById(game);
    }

    @Override
    public void deleteGame(Long id) {
        gameMapper.deleteById(id);
    }
}

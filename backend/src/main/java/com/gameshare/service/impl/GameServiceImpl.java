package com.gameshare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.gameshare.entity.*;
import com.gameshare.mapper.*;
import com.gameshare.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameMapper gameMapper;
    @Autowired
    private BannerMapper bannerMapper;
    @Autowired
    private DownloadLinkMapper downloadLinkMapper;
    @Autowired
    private GameImageMapper gameImageMapper;
    @Autowired
    private UnlockRecordMapper unlockRecordMapper;

    @Override
    public List<Banner> getBanners() {
        LambdaQueryWrapper<Banner> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Banner::getStatus, 1).orderByAsc(Banner::getSort);
        return bannerMapper.selectList(wrapper);
    }

    @Override
    public List<Game> listGames(String platform, String keyword) {
        LambdaQueryWrapper<Game> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Game::getStatus, 1);
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
    public Game getGameDetail(Long id, Long userId) {
        Game game = gameMapper.selectById(id);
        if (game == null) return null;
        
        // 查询下载链接
        LambdaQueryWrapper<DownloadLink> linkWrapper = new LambdaQueryWrapper<>();
        linkWrapper.eq(DownloadLink::getGameId, id).orderByAsc(DownloadLink::getSort);
        game.setDownloadLinks(downloadLinkMapper.selectList(linkWrapper));
        
        // 查询截图
        LambdaQueryWrapper<GameImage> imgWrapper = new LambdaQueryWrapper<>();
        imgWrapper.eq(GameImage::getGameId, id).orderByAsc(GameImage::getSort);
        List<GameImage> images = gameImageMapper.selectList(imgWrapper);
        game.setImages(images.stream().map(GameImage::getUrl).collect(Collectors.toList()));
        
        // 更新浏览次数
        LambdaUpdateWrapper<Game> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Game::getId, id).set(Game::getViewCount, game.getViewCount() + 1);
        gameMapper.update(null, updateWrapper);
        
        // 检查用户是否已解锁
        if (userId != null) {
            game.setUnlocked(checkUnlocked(userId, id));
        }
        
        return game;
    }

    @Override
    public boolean checkUnlocked(Long userId, Long gameId) {
        LambdaQueryWrapper<UnlockRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UnlockRecord::getUserId, userId).eq(UnlockRecord::getGameId, gameId);
        return unlockRecordMapper.selectCount(wrapper) > 0;
    }

    @Override
    @Transactional
    public void unlockGame(Long userId, Long gameId, String adType) {
        // 检查是否已解锁
        if (checkUnlocked(userId, gameId)) return;
        
        UnlockRecord record = new UnlockRecord();
        record.setUserId(userId);
        record.setGameId(gameId);
        record.setAdType(adType);
        unlockRecordMapper.insert(record);
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

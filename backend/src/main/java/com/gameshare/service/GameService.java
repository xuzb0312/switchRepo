package com.gameshare.service;

import com.gameshare.entity.*;
import java.util.List;

public interface GameService {
    List<Banner> getBanners();
    List<Game> listGames(String platform, String keyword);
    Game getGameDetail(Long id, Long userId);
    boolean checkUnlocked(Long userId, Long gameId);
    void unlockGame(Long userId, Long gameId, String adType);
    void addGame(Game game);
    void updateGame(Game game);
    void deleteGame(Long id);
}

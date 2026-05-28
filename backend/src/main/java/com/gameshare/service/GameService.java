package com.gameshare.service;

import com.gameshare.entity.Game;
import java.util.List;

public interface GameService {
    List<Game> listGames(String platform, String keyword);
    Game getGameDetail(Long id);
    void addGame(Game game);
    void updateGame(Game game);
    void deleteGame(Long id);
}

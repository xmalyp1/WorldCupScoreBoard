package org.example.maly.service.driver;

import java.util.List;
import org.example.maly.model.Game;

// maybe we could consider the interface segregation principle but the most of the sports uses those actions.
public interface ScoreBoardDriver {

    public void startGame(Game game);
    public void updateGame(Game game, int homeScore, int awayScore);
    public void finishGame(Game game);
    public List<Game> getSummary();

}

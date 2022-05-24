package org.example.maly.service.driver;

import java.util.List;
import org.example.maly.model.Game;
import org.example.maly.model.TeamInstance;
import org.example.maly.service.dao.GameRepository;

public class FootballScoreBoardDriver implements ScoreBoardDriver {

    GameRepository gameRepository;

    public FootballScoreBoardDriver(){
        gameRepository = GameRepository.getInstance();
    }

    @Override
    public void startGame(Game game) {
        validateGame(game);

        if(!gameRepository.addItem(game)){
            throw new IllegalArgumentException(String.format("Unable to add %s a game to scoreboard",game));
        }
    }

    /**
     * NOTE: if the scoreboard will be REAL TIME we need to check also the values - (it is not possible to score two or more goals within couple millis)
     */
    @Override
    public void updateGame(Game game,int homeScore,int awayScore) {
        validateGameUpdate(game, homeScore, awayScore);
        Game foundGame = gameRepository.findGame(game).orElseThrow(()-> new IllegalArgumentException(String.format("Game %s have not been found",game)));
        updateScore(foundGame,homeScore,awayScore);
    }

    @Override
    public void finishGame(Game game) {
        validateArgument(game == null, "Unable to update a NULL game");
        if( gameRepository.removeItem(game)){
            throw new IllegalArgumentException(String.format("Unable to remove the game %s",game));
        }
    }

    @Override
    public List<Game> getSummary() {
        return gameRepository.getSortedGames();
    }

    private void validateGame(Game game){
        if(game == null){
            throw new IllegalArgumentException("A game cannot be null");
        }

        if(game.getHomeTeam().getTeam().equals(game.getAwayTeam().getTeam())) {
            throw new IllegalArgumentException("Same team on both away / home side.");
        }

        checkAlreadyRunningGame(game.getHomeTeam());
        checkAlreadyRunningGame(game.getAwayTeam());
    }

    private void checkAlreadyRunningGame(TeamInstance teamInstance) {
        if (gameRepository.findGameForTeam(teamInstance.getTeam()).isPresent()) {
            throw new IllegalStateException(String.format("There is already a running game for team %s", teamInstance.getTeam()));
        }
    }

    private void validateGameUpdate(Game game, int homeScore, int awayScore) {
        validateArgument(game == null, "Unable to update a NULL game");

        // we can accept also a decrease of score. (For example VAR decision)
        validateArgument(homeScore < 0 || awayScore < 0,
            String.format("Illegal score update={home team score = %d, away team score = %d}", homeScore, awayScore));
    }

    private void validateArgument(boolean b, String s) {
        if (b) {
            throw new IllegalArgumentException(s);
        }
    }

    private void updateScore(Game game,int homeScore,int awayScore){
        game.getHomeTeam().setScore(homeScore);
        game.getAwayTeam().setScore(awayScore);
    }

}

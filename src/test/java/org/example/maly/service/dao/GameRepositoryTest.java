package org.example.maly.service.dao;

import org.example.maly.model.Game;
import org.example.maly.model.Team;
import org.example.maly.model.TeamInstance;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class GameRepositoryTest {


    private static GameRepository gameRepository;

    @BeforeAll
    public static void setup(){
        gameRepository = GameRepository.getInstance();
    }

    @Test
    public void testGameRepository(){

        Game semifinal1 = new Game(new TeamInstance(new Team("England")),new TeamInstance(new Team("Argentina")));
        Game semifinal2 = new Game(new TeamInstance(new Team("France")),new TeamInstance(new Team("Germany")));

        Assertions.assertAll(
            ()-> Assertions.assertTrue(gameRepository.addItem(semifinal1),String.format("Unable to add game %s to repo",semifinal1)),
            ()-> Assertions.assertFalse(gameRepository.addItem(semifinal1),String.format("Game should not be added twice - %s",semifinal1)),
            ()-> Assertions.assertTrue(gameRepository.addItem(semifinal2),String.format("Unable to add game %s to repo",semifinal2))
        );

        //TODO: implement later
        Assertions.assertThrows(UnsupportedOperationException.class,()->gameRepository.getSortedGames());


    }

}

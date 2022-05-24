package org.example.maly.service.dao;

import java.util.Optional;
import org.example.maly.model.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TeamRepositoryTest {

    private static TeamRepository teamRepository;

    @BeforeAll
    public static void setup(){
        teamRepository = TeamRepository.getInstance();
    }

    @Test
    public void testRepository(){
        Team italy = new Team("Italy");
        Team england = new Team("England");

        Assertions.assertAll(
            ()-> Assertions.assertTrue(teamRepository.addItem(italy),String.format("Unable to add team %s",italy)),
            ()-> Assertions.assertFalse(teamRepository.addItem(italy),String.format("The same team should not be added twice - %s",italy)),
            ()-> Assertions.assertTrue(teamRepository.addItem(england),String.format("Unable to add team %s",england))
        );

        Optional<Team> optionalItaly = teamRepository.getTeamByName("Italy");
        if(optionalItaly.isEmpty()){
            Assertions.fail("Team Italy have not been found.");
        }
        Assertions.assertSame(italy,optionalItaly.get(),String.format("Team %s have not been found in repository",italy));
    }

}

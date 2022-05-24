package org.example.maly.service.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.example.maly.model.Game;
import org.example.maly.model.Team;

public class GameRepository extends AbstractRepository<Game>{

    private static GameRepository instance;

    private GameRepository(){
        //for simplicity we will use and ordered collection since we need the order of items
        super(new ArrayList<>());
    }

    public static GameRepository getInstance() {
        if(instance == null){
            //synchronized block to remove overhead
            synchronized (GameRepository.class) {
                instance = new GameRepository();
            }
        }
        return instance;
    }

    @Override
    public boolean addItem(Game item) {
        //do not allow multiple games.
        if(findGame(item).isPresent())
            return false;

        return super.addItem(item);
    }

    public List<Game> getSortedGames(){
        List<Game> copyOfItems = new ArrayList<>(getItems());
        //since we stick to implementation of list we need to reverse the items
        Collections.reverse(copyOfItems);
        return copyOfItems.stream().sorted().collect(Collectors.toUnmodifiableList());
    }

    public Optional<Game> findGame(Game game){
        return findItem(g -> g.equals(game));
    }

    public Optional<Game> findGameForTeam(Team team){
        return findItem(game -> game.getHomeTeam().getTeam().equals(team) || game.getAwayTeam().getTeam().equals(team));
    }
}

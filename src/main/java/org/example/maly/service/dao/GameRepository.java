package org.example.maly.service.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.example.maly.model.Game;

public class GameRepository extends AbstractRepository<Game>{

    private static GameRepository instance;

    private GameRepository(){
        super(new HashSet<>());
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

    public List<Game> getSortedGames(){
        throw new UnsupportedOperationException("TODO: implement comparator");
    }
}

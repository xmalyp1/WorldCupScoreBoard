package org.example.maly.service.dao;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import org.example.maly.model.Team;

/**
 * A singleton repository a set of Teams
 */
public class TeamRepository extends AbstractRepository<Team>{

    private static TeamRepository instance;

    private TeamRepository(){
        super(new HashSet<>());
    }

    public static TeamRepository getInstance() {
        if(instance == null){
            //synchronized block to remove overhead
            synchronized (TeamRepository.class) {
                instance = new TeamRepository();
            }
        }
        return instance;
    }

    public Optional<Team> getTeamByName(String name){
        return findItem(team -> team.getName().equals(name));
    }
}

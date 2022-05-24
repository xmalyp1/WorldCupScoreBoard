package org.example.maly.model;

import java.util.Objects;

public class Game {

    private TeamInstance awayTeam;
    private TeamInstance homeTeam;

    public Game(TeamInstance awayTeam, TeamInstance homeTeam) {
        this.awayTeam = awayTeam;
        this.homeTeam = homeTeam;
    }

    public TeamInstance getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(TeamInstance awayTeam) {
        this.awayTeam = awayTeam;
    }

    public TeamInstance getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(TeamInstance homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getScore(){
        return String.format("%s %d - %d %s",this.homeTeam.getTeam().getName(),this.homeTeam.getScore(),this.awayTeam.getTeam().getName(),this.awayTeam.getScore());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Game)) {
            return false;
        }
        Game game = (Game) o;
        return Objects.equals(awayTeam, game.awayTeam) && Objects.equals(homeTeam, game.homeTeam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(awayTeam, homeTeam);
    }
}

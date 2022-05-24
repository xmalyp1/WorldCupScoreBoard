package org.example.maly.model;

import java.util.Objects;
import java.util.StringJoiner;

public class Game implements Comparable<Game>{

    private TeamInstance awayTeam;
    private TeamInstance homeTeam;

    public Game(TeamInstance homeTeam, TeamInstance awayTeam) {
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
        return String.format("%s - %s: %d - %d",this.homeTeam.getTeam().getName(),this.awayTeam.getTeam().getName(),this.homeTeam.getScore(),this.awayTeam.getScore());
    }

    private int getTotalScore(){
        return this.awayTeam.getScore()+this.homeTeam.getScore();
    }

    public static Builder getBuilder(){
        return new Builder();
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

        if (awayTeam != null ? !awayTeam.equals(game.awayTeam) : game.awayTeam != null) {
            return false;
        }
        return homeTeam != null ? homeTeam.equals(game.homeTeam) : game.homeTeam == null;
    }

    @Override
    public int hashCode() {
        int result = awayTeam != null ? awayTeam.hashCode() : 0;
        result = 31 * result + (homeTeam != null ? homeTeam.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Game o) {
        return o.getTotalScore() - this.getTotalScore();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Game.class.getSimpleName() + "[", "]")
            .add("awayTeam=" + awayTeam)
            .add("homeTeam=" + homeTeam)
            .toString();
    }

    public static class Builder{
        private Team homeTeam;
        private int homeScore;

        private Team awayTeam;
        private int awayScore;

        public Builder setHomeTeam(Team homeTeam){
            this.homeTeam = homeTeam;
            return this;
        }

        public Builder setHomeScore(int homeScore){
            this.homeScore = homeScore;
            return this;
        }

        public Builder setAwayTeam(Team awayTeam){
            this.awayTeam = awayTeam;
            return this;
        }

        public Builder setAwayScore(int awayScore){
            this.awayScore = awayScore;
            return this;
        }


        public Game build(){
            TeamInstance home = homeTeam != null ? new TeamInstance(homeTeam,homeScore) : null;
            TeamInstance away = awayTeam != null ? new TeamInstance(awayTeam,awayScore) : null;
            return new Game(home,away);
        }
    }
}

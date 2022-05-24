package org.example.maly.model;

public class TeamInstance {

    private Team team;
    private int score;

    public TeamInstance(Team team){
        this(team,0);
    }

    // Maybe we can use it if we want to display a result of already started game
    public TeamInstance(Team team, int score){
        this.team = team;
        this.score = score;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

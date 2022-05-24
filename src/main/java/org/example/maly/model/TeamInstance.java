package org.example.maly.model;

import java.util.StringJoiner;

public class TeamInstance {

    private final Team team;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TeamInstance.class.getSimpleName() + "[", "]")
            .add("team=" + team)
            .add("score=" + score)
            .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TeamInstance)) {
            return false;
        }

        TeamInstance that = (TeamInstance) o;

        if (score != that.score) {
            return false;
        }
        return team != null ? team.equals(that.team) : that.team == null;
    }

    @Override
    public int hashCode() {
        int result = team != null ? team.hashCode() : 0;
        result = 31 * result + score;
        return result;
    }
}

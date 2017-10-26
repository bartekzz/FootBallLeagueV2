package common;

import java.util.Date;

/**
 * This class mirrors the Game-table in DB
 */
public class Game {

    private Integer id;
    private Date date;
    private Integer stadiumId;
    private Integer team1Id;
    private Integer team2Id;

    public Game() {}

    /**
     * This constructs a game with id, home team-and away-team
     * @param date the date that a game is played
     * @param team1Id the id of home-team (from class Team)
     * @param team2Id the id of away-team (from class Team)
     */
    public Game(Date date, Integer team1Id, Integer team2Id) {
        this.date = date;
        this.team1Id = team1Id;
        this.team2Id = team2Id;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getStadiumId() {
        return stadiumId;
    }

    public void setStadiumId(Integer stadiumId) {
        this.stadiumId = stadiumId;
    }

    public Integer getTeam1Id() {
        return team1Id;
    }

    public void setTeam1Id(Integer team1Id) {
        this.team1Id = team1Id;
    }

    public Integer getTeam2Id() {
        return team2Id;
    }

    public void setTeam2Id(Integer team2Id) {
        this.team2Id = team2Id;
    }

}

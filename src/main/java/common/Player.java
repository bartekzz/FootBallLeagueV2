package common;

import java.io.Serializable;

/**
 * This class mirrors the Player-table in DB
 */
public class Player implements Serializable{

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String fname;
    private String lname;
    private Integer positionId;

    public Player() {}

    /**
     * This constructs a player with first name, last name and position
     * @param fname the first name
     * @param lname the last name
     * @param positionId the position id (from class Position)
     */
    public Player(String fname, String lname, Integer positionId) {
        this.fname = fname;
        this.lname = lname;
        this.positionId = positionId;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFname() {
        return this.fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return this.lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

}

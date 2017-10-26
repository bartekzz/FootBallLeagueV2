package common;

/**
 * This class mirrors a Team in DB
 */
public class Team {

    private Integer id;
    private String name;
    private Integer coachId;

    public Team() {}

    /**
     * This constructs a Team with name and coach id
     * @param name the name of the team
     * @param coachId the coach's id (from class Coach)
     */
    public Team(String name, Integer coachId) {
        this.name = name;
        this.coachId = coachId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCoachId() {
        return coachId;
    }

    public void setCoachId(Integer coachId) {
        this.coachId = coachId;
    }
}

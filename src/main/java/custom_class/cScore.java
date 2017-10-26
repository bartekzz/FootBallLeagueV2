package custom_class;

/**
 * This class is to store team and points
 */
public class cScore {

    private String teamName;
    private Integer points;

    public cScore() {
    }

    /**
     * This creates a cScore-object with team name and points that the team has won
     * @param teamName the name of the team
     * @param points the points that the team has won
     */
    public cScore(String teamName, Integer points) {
        this.teamName = teamName;
        this.points = points;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}

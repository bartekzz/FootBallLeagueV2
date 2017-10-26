package common;

/**
 * This class mirrors Result-table in DB
 */
public class Result {

    private Integer id;
    private Integer team1Score;
    private Integer team2Score;
    private Integer gameId;
    private Game game;

    private static int minimum = 0;
    private static int maximum = 5;

    public Result() {}

    /**
     * This construct a Result with a game id (and adds two random scores, team1Score and team2Score)
     * @param gameId the id of a game (from class Game)
     */
    public Result(Integer gameId) {
        this.gameId = gameId;
        //setGameId(gameId);
        this.team1Score = minimum + (int)(Math.random() * maximum);
        this.team2Score = minimum + (int)(Math.random() * maximum);

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = this.gameId;
    }

    public Integer getTeam1Score() {
        return team1Score;
    }

    public void setTeam1Score(Integer team1Score) {
        this.team1Score = team1Score;
    }

    public Integer getTeam2Score() {
        return team2Score;
    }

    public void setTeam2Score(Integer team2Score) {
        this.team2Score = team2Score;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}

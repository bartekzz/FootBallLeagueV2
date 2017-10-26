package sample;

import common.Game;
import common.Player;
import common.Result;
import custom_class.cScore;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.*;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class holds all the methods that executes from individual class models (i.e ResultModel)
 * and methods that display data and make visual changes to the application view
 */
public class Model {

    private Main main;

    /**
     * This construct the model that get the Main class as instance variable
     * @param main the Main class instance variable
     */
    public Model(Main main) {
        this.main = main;
    }

    public void initHibernate() {
        System.out.println("Maven + Hibernate + MySQL");

        //Delete all results
        ResultModel.deleteAllResults();

        // Create Queue from all games in db
        Queue<Game> games = GameModel.queueAllGames();

        // Create result from each queue member and store in db, until queue empty
        //ResultModel.createResult(session, games);
        ResultModel.createAllResults(games);
    }

    /**
     * This clears gridpane's all children elements
     */
    public void clearGrid() {
        main.getGrid().getChildren().clear();
    }

    /**
     * This create a textfield with given width
     * @param prefWidth the size of the width
     * @return the textfield object
     */
    public static TextField createTextField(double prefWidth) {
        TextField textField = new TextField();
        textField.setPrefWidth(prefWidth);
        return textField;
    }

    /**
     * This creates a vertical box which acts as a menu with links
     * @return the box
     */
    public VBox addVBox() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        Text title = new Text("Actions");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        vbox.getChildren().add(title);

        main.getOptions().add(new Hyperlink("Show Games"));
        main.getOptions().add(new Hyperlink("Show Results"));
        main.getOptions().add(new Hyperlink("Ranking Table"));
        main.getOptions().add(new Hyperlink("Search Player"));

        main.getListeners().addMenuLinkListener1();
        main.getListeners().addMenuLinkListener2();
        main.getListeners().addMenuLinkListener3();
        main.getListeners().addMenuLinkListener4();

        for(Hyperlink option : main.getOptions()) {
            VBox.setMargin(option, new Insets(0, 0, 0, 8));
            vbox.getChildren().add(option);
        }

        return vbox;
    }

    /**
     * This sends the values from Textfield (after numeric check) to ResultModel which stores the values to DB
     */
    public void updateScores() {

        boolean isNumeric = true;

        List<List<TextField>> allRowResFields = main.getAllRowResFields();

        for(List<TextField> rowResFields : allRowResFields) {
            for(TextField textfield : rowResFields) {
                System.out.println("Textfield value: " + textfield.getText());
                if(!StringUtils.isNumeric(textfield.getText()) && !textfield.getText().equals("")) {
                    isNumeric = false;
                    System.out.print("Numeric is false");
                }
            }
        }

        if(isNumeric) {
            ResultModel.updateScores(main.getAllResFields());
            System.out.println("allResFields: " + main.getAllResFields());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Success!");
            alert.setContentText("Score are updated!");
            alert.showAndWait();

            listScores();

        } else {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Wrong input");
            alert.setContentText("There is non-numeric input in one of the cells.");
            alert.showAndWait();
        }
    }

    /**
     * Send the input from search player textfield (Search Player menu) to PlayerModel
     * if player is not found in DB it display error message
     * @param name
     */
    public void findPlayerByName(String name) {
        //Remove TextField with player name (if previous search was made)
        main.getGrid().getChildren().remove(main.getPlayerName());

        List<Player> players = PlayerModel.findPlayerByName(name);

        if(players.isEmpty()) {
            Text text = new Text("Player could not be found");
            text.setFill(Color.RED);
            main.setPlayerName(text);
            main.getGrid().add(main.getPlayerName(), 0, 2);
        } else {
            for (Player player : players) {
                main.setPlayerName(new Text(player.getFname() + " " + player.getLname() + ", "
                        + PositionModel.getPositionType(player.getPositionId())));
                main.getGrid().add(main.getPlayerName(), 0, 2);
            }
        }
    }

    /**
     * Gets the results from DB (Game and Result table) and displays them
     */
    public void listScores() {

        clearGrid();

        List<Object[]> results = ResultModel.getResultTeams();

        for (int i = 0; i < results.size(); i++) {
            Result res = (Result) results.get(i)[0];
            Game game = (Game) results.get(i)[1];

            Integer gameId = game.getId();

            String team1Name = TeamModel.getTeamName(game.getTeam1Id());
            String team2Name = TeamModel.getTeamName(game.getTeam2Id());

            Integer team1Score = res.getTeam1Score();
            Integer team2Score = res.getTeam2Score();


            Text teamsTitle = new Text("Teams");
            teamsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            main.getGrid().add(teamsTitle, 0, 0);
            Text scoreTitle = new Text("Score");
            scoreTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            main.getGrid().add(scoreTitle, 1, 0);
            Text updateTitle = new Text("Update score");
            updateTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            main.getGrid().add(updateTitle, 2, 0);


            Text teamName = new Text(team1Name + " - " + team2Name);
            teamName.setFont(Font.font("Arial", FontWeight.MEDIUM, 16));
            main.getGrid().add(teamName, 0, i+1);
            Text points = new Text(team1Score + " : " + team2Score);
            points.setFont(Font.font("Arial", FontWeight.MEDIUM, 16));
            main.getGrid().add(points, 1, i+1);


            List<TextField> rowResFields = new ArrayList<>();

            rowResFields.add(createTextField(40));
            main.getGrid().add(rowResFields.get(0), 2, i+1);
            rowResFields.add(createTextField(100));
            main.getGrid().add(rowResFields.get(1), 3, i+1);

            //Add rowResField to list
            main.getAllRowResFields().add(rowResFields);

            // Create hashmap (gameid, list of updated scores)
            Map<Integer, List<TextField>> hashMapFields = new HashMap<>();
            hashMapFields.put(gameId, rowResFields);

            // Add hashmap to main list (allResFields)
            main.getAllResFields().add(hashMapFields);

            main.getUpdateResButtons().add(new Button("Update score"));
            main.getGrid().add(main.getUpdateResButtons().get(i), 4, i+1);

            main.getListeners().addUpdateButtonListeners();

        }
    }

    /**
     * Gets all games from DB (GameModel) and displays date, stadium and teams
     */
    public void showGames() {

        clearGrid();

        List<Game> games = GameModel.getAllGames();

        for (int i = 0; i < games.size(); i++) { ;
            Game game = games.get(i);

            Date date = game.getDate();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = df.format(date);

            String stadiumName = StadiumModel.getStadiumName(game.getStadiumId());

            String team1Name = TeamModel.getTeamName(game.getTeam1Id());
            String team2Name = TeamModel.getTeamName(game.getTeam2Id());


            Text dateTitle = new Text("Date");
            dateTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            main.getGrid().add(dateTitle, 1, 0);
            Text stadiumTitle = new Text("Stadium");
            stadiumTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            main.getGrid().add(stadiumTitle, 2, 0);
            Text teamsTitle = new Text("Teams");
            teamsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            main.getGrid().add(teamsTitle, 3, 0);

            Text playDate = new Text(dateStr);
            playDate.setFont(Font.font("Arial", FontWeight.MEDIUM, 16));
            main.getGrid().add(playDate, 1, i+1);
            Text stadium = new Text(stadiumName);
            stadium.setFont(Font.font("Arial", FontWeight.MEDIUM, 16));
            main.getGrid().add(stadium, 2, i+1);
            Text teamNames = new Text(team1Name + " - " + team2Name);
            teamNames.setFont(Font.font("Arial", FontWeight.MEDIUM, 16));
            main.getGrid().add(teamNames, 3, i+1);

        }
    }

    /**
     * Gets results from Result table in DB and calculate points from scores and builds a rankings table
     * which is displayed
     */
    public void showRankingTable() {
        System.out.println("Executing link 3 (ranking table)");
        clearGrid();

        List<Object[]> res = ResultModel.getResultTeams();

        Map<Integer, Integer> scoreTable = ResultModel.createScoreTable(res);
        List<cScore> scoreList = ResultModel.sortScoreTable(scoreTable);

        Text rankingTitle = new Text("Ranking Table");
        rankingTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        main.getGrid().add(rankingTitle, 0, 0);

        for(int i = 0; i < scoreList.size(); i++) {
            Text teamName = new Text(scoreList.get(i).getTeamName());
            teamName.setFont(Font.font("Arial", FontWeight.MEDIUM, 16));
            main.getGrid().add(teamName, 0, i+1);
            Text points = new Text(scoreList.get(i).getPoints().toString());
            points.setFont(Font.font("Arial", FontWeight.MEDIUM, 16));
            main.getGrid().add(points, 1, i+1);
        }
    }

    /**
     * Display a input field to search for player by name
     */
    public void searchPlayer() {

        clearGrid();

        Text searchTitle = new Text("Player Search");
        searchTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        main.getGrid().add(searchTitle, 0, 0);

        main.setSearchField(new TextField());
        main.getGrid().add(main.getSearchField(), 0,1);

        main.setSearchButton(new Button("Search player"));
        main.getGrid().add(main.getSearchButton(), 1, 1);

        main.getListeners().addSearchButtonListeners();
    }

}







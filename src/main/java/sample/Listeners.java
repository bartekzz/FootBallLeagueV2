package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 * This class holds everything associated with listeners for JavaFX application
 */
public class Listeners {

    Main main;
    Model model;

    /**
     * This construct a Listeners class with main and model instance variables
     * @param main instance variable of the Main class
     * @param model instance variable of the Model class
     */
    public Listeners(Main main, Model model) {
        this.main = main;
        this.model = model;
    }

    /**
     * This set the listener to the first link in the left menu (Show Games)
     * which get all games from DB and shows (date, stadium and teams playing)
     */
    public void addMenuLinkListener1() {
        main.getOptions().get(0).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                model.showGames();
            }
        });

    }

    /**
     * This set the listener to the first link in the left menu (Show Results)
     * which get all score from all games and gives you the possibility to update the scores
     */
    public void addMenuLinkListener2() {
        main.getOptions().get(1).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                model.listScores();
            }
        });
    }

    /**
     * This set the listener to the first link in the left menu (Ranking Table)
     * which create a score table and shows all teams' scores
     */
    public void addMenuLinkListener3() {
        main.getOptions().get(2).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                model.showRankingTable();
            }
        });
    }

    /**
     * This set the listener to the first link in the left menu (Search Player)
     * which lets you to search for a player by name and return the name and position
     */
    public void addMenuLinkListener4() {
        main.getOptions().get(3).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                model.searchPlayer();
            }
        });
    }

    /**
     * Add listeners to all buttons that updates the scores
     */
    public void addUpdateButtonListeners() {
        for(Button button : main.getUpdateResButtons()) {
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    model.updateScores();
                }
            });
        }
    }

    /**
     * Adds listners to search button which execute the search for player
     */
    public void addSearchButtonListeners() {

        main.getSearchButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                model.findPlayerByName(main.getSearchField().getText());
            }
        });
    }
}

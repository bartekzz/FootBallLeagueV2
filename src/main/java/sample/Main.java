package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;


public class Main extends Application {

    private Model model;
    private Listeners listeners;

    private List<Hyperlink> options = new ArrayList<>();

    private BorderPane border;
    private GridPane grid;
    private List<Button> updateResButtons = new ArrayList<>();
    private List<Map<Integer, List<TextField>>> allResFields = new ArrayList<>();
    private List<List<TextField>> allRowResFields = new ArrayList<>();

    private TextField searchField;
    private Button searchButton;

    private Text playerName;

    @Override
    public void start(Stage primaryStage) throws Exception {
        model = new Model(this);
        listeners = new Listeners(this, model);

        model.initHibernate();

        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));

        border = new BorderPane();
        border.setLeft(model.addVBox());

        grid = new GridPane();
        grid.setHgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPrefWidth(Control.USE_COMPUTED_SIZE);
        col1.setHalignment(HPos.LEFT);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPrefWidth(Control.USE_COMPUTED_SIZE);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPrefWidth(Control.USE_COMPUTED_SIZE);
        ColumnConstraints col4 = new ColumnConstraints();
        col3.setPrefWidth(Control.USE_COMPUTED_SIZE);
        ColumnConstraints col5 = new ColumnConstraints();
        col3.setPrefWidth(Control.USE_COMPUTED_SIZE);

        grid.getColumnConstraints().addAll(col1,col2,col3,col4,col5);

        border.setRight(grid);

        primaryStage.setTitle("Football League");
        primaryStage.setScene(new Scene(border, 800, 400));
        primaryStage.show();
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Listeners getListeners() {
        return listeners;
    }

    public void setListeners(Listeners listeners) {
        this.listeners = listeners;
    }

    public List<Hyperlink> getOptions() {
        return options;
    }

    public void setOptions(List<Hyperlink> options) {
        this.options = options;
    }

    public BorderPane getBorder() {
        return border;
    }

    public void setBorder(BorderPane border) {
        this.border = border;
    }

    public GridPane getGrid() {
        return grid;
    }

    public void setGrid(GridPane grid) {
        this.grid = grid;
    }

    public List<Button> getUpdateResButtons() {
        return updateResButtons;
    }

    public void setUpdateResButtons(List<Button> updateResButtons) {
        this.updateResButtons = updateResButtons;
    }

    public List<Map<Integer, List<TextField>>> getAllResFields() {
        return allResFields;
    }

    public void setAllResFields(List<Map<Integer, List<TextField>>> allResFields) {
        this.allResFields = allResFields;
    }

    public TextField getSearchField() {
        return searchField;
    }

    public void setSearchField(TextField searchField) {
        this.searchField = searchField;
    }

    public Button getSearchButton() {
        return searchButton;
    }

    public void setSearchButton(Button searchButton) {
        this.searchButton = searchButton;
    }

    public Text getPlayerName() {
        return playerName;
    }

    public void setPlayerName(Text playerName) {
        this.playerName = playerName;
    }

    public List<List<TextField>> getAllRowResFields() {
        return allRowResFields;
    }

    public void setAllRowResFields(List<List<TextField>> allRowResFields) {
        this.allRowResFields = allRowResFields;
    }

    public static void main(String[] args) {
        launch(args);
    }

}

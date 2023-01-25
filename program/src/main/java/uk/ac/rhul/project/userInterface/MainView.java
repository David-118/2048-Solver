package uk.ac.rhul.project.userInterface;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import uk.ac.rhul.project.game.GameConfiguration;
import uk.ac.rhul.project.game.GameState;
import uk.ac.rhul.project.heursitics.Monotonic;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

public class MainView extends Application implements View
{
    /**
     * Labesl representing each tile in a 2048 game.
     */
    private Label[][] labels;

    /**
     * Height of the current game.
     */
    private int height;

    /**
     * Width of the current game.
     */
    private int width;

    /**
     * Current instance of the 2048 game.
     */
    private static volatile MainView instance = null;

    /**
     * The base tile size for a 2048 game.
     */
    private static final float TITLE_SIZE = 100f;

    /**
     * Multiplier to enlarge / shrink tile size.
     */
    private float scale;


    /**
     *  The background colour associated with each tile <= 2048
     *  <br><br>
     *  Colour comes from [5]
     */
    private static final HashMap<Integer, String> BACK_COLOURS = new HashMap<Integer, String>() {{
        put(0, "rgba(238, 228, 218, 0.35)");
        put(2, "#eee4da");
        put(4, "#eee1c9");
        put(8, "#f3b27a");
        put(16, "#f69664");
        put(32, "#f77c5f");
        put(64, "#f75f3b");
        put(128, "#edd073");
        put(256, "#edcc62");
        put(512, "#edc950");
        put(1024, "#edc53f");
        put(2048, "#edc22e");
    }};

    /**
     * The text colour for the two tiles that are a different colour.
     */
    private static final HashMap<Integer, String> TEXT_COLOURS = new HashMap<>()
    {{
        put(2, "#776e65");
        put(4, "#776e65");
    }};

    /**
     * An observer that gets trigger when a new game is created.
     */
    private NewGameObserver newGameObserver;

    /**
     * The grid pane used to display 2048 games.
     */
    @FXML
    private GridPane gameView;

    /**
     * Button used to create a new game.
     */
    @FXML
    private Button newGame;

    /**
     * Label used to display score of the current game.
     */
    @FXML
    private Label score;

    /**
     * The root element of the ui.
     */
    @FXML
    private Scene root;

    /**
     * Grid used to display entire user interface.
     */
    @FXML
    private GridPane mainGrid;

    /**
     * Button to start solver 2048 game.
     */
    @FXML
    private Button solve;

    /**
     * Dialog for user to create new game.
     */
    private Dialog<int[]> newGameDialog;

    /**
     * Initialise the user interface when the JavaFX window loads.
     */
    @FXML
    void initialize()
    {
        this.height = 4;
        this.width = 4;


        // Based on login dialog [6]
        this.newGameDialog = new Dialog<>();
        this.newGameDialog.setResizable(false);
        this.newGameDialog.setTitle("New Game");
        this.newGameDialog.setHeaderText("Enter dimensions of new game");

        ButtonType submitButton = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        this.newGameDialog.getDialogPane().getButtonTypes().addAll(submitButton, ButtonType.CANCEL);

        GridPane pane = new GridPane();

        pane.setHgap(10d);
        pane.setVgap(10d);

        pane.addColumn(0);
        ColumnConstraints lables = new ColumnConstraints(50);
        ColumnConstraints spinners = new ColumnConstraints(200);
        pane.getColumnConstraints().addAll(lables, spinners);

        Spinner<Integer> widthIn = new Spinner<Integer>();
        widthIn.setMaxWidth(Double.MAX_VALUE);
        widthIn.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 20, 4));

        Label widthLabel = new Label("Width:");

        Spinner<Integer> heightIn = new Spinner<Integer>();
        heightIn.setMaxWidth(Double.MAX_VALUE);
        heightIn.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 20, 4));

        Label heightLabel = new Label("Height:");

        pane.add(widthLabel, 0, 0);
        pane.add(widthIn, 1, 0);
        pane.add(heightLabel, 0, 1);
        pane.add(heightIn, 1, 1);

        this.newGameDialog.getDialogPane().setContent(pane);

        this.newGameDialog.setResultConverter(buttonType -> {
            if (buttonType == submitButton)
            {
                return new int[]{heightIn.getValue(), widthIn.getValue()};
            }
            return new int[]{};
        });

        instance = this;
    }


    /**
     * Get an instance of the singleton MainView.
     * @return Either a new instance or the one existing instance of MainView.
     */
    public static synchronized MainView getInstance()
    {
       if (instance==null)
       {
           new Thread(() -> Application.launch(MainView.class)).start();
           while (instance == null);
       }

       return instance;
    }


    /**
     * Open the main javaFX Window.
     * @param primaryStage The main stage for the entire UI.
     *
     * @throws IOException thrown if an error occurs when reading and interpreting the fxml file.
     */
    @Override
    public void start(Stage primaryStage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
        MainView controller = new MainView();
        fxmlLoader.setController(controller);

        Scene root = fxmlLoader.load();

        primaryStage.setScene(root);
        primaryStage.show();
    }

    /**
     * Set up an observer for to be activated when a user creates a new game.
     * @param method Method to be triggered when a new game is created.
     */
    public void addNewGameObserver(NewGameObserver method)
    {
        this.newGameObserver = method;
        this.newGame.setOnAction(event ->
        {
            Optional<int[]> result = this.newGameDialog.showAndWait();
            result.ifPresent(size -> {
                this.make2048Grid(size[0], size[1]);
                this.newGameObserver.notifyObservers(new GameConfiguration(size[0], size[1], 7,
                        new Monotonic()));
            });

        });
    }

    /**
     * Add an observer to be triggered when the solve button is clicked.
     * @param method The method triggered when the solve button is clicked.
     */
    public void addSolveObserver(SolveObserver method)
    {
        this.solve.setOnAction(actionEvent -> method.notifyObserver(false));
    }

    /**
     * Initialise a 2048 grid.
     * @param height Height (in cells) of the grid
     * @param width Width (in cells) of the grid.
     */
    public void make2048Grid(int height, int width)
    {
        this.height = height;
        this.width = width;

        this.setScale(height, width);
        this.prepareGameView(height, width);
        this.buildNewGameGrid(height, width);
    }

    /**
     * Set the scale of the game to ensure the game fits in the window.
     * @param height The height of the 2048 game.
     * @param width The width of the 2048 game.
     */
    private void setScale(int height, int width)
    {
        if (height > 16) { this.scale = 0.4f;}
        else if (width > 16 || height > 8) {this.scale = 0.5f;}
        else if (width > 8 || height  > 4) { this.scale = 1f;}
        else {this.scale = 1.5f;}
    }

    /**
     * Prepare the game view and window for a game of a given size.
     */
    private void prepareGameView(int height, int width)
    {
        //Remove any existing 2048 grid.
        this.gameView.getChildren().removeAll(this.gameView.getChildren());
        this.gameView.getRowConstraints().removeAll(this.gameView.getRowConstraints());
        this.gameView.getColumnConstraints().removeAll(this.gameView.getColumnConstraints());

        // Create enough space for a new 2048 game in the grid.
        this.mainGrid.getRowConstraints().get(3).setPrefHeight(height * TITLE_SIZE * this.scale + 30f );
        this.mainGrid.getColumnConstraints().get(0).setPrefWidth(width * TITLE_SIZE * this.scale - 110);

        // Make the window big enough for a 2048 game.
        this.root.getWindow().setHeight(height * TITLE_SIZE * this.scale+ 220f);
        this.root.getWindow().setWidth(width * TITLE_SIZE * this.scale + 40f);

        this.gameView.setPrefSize(width * TITLE_SIZE * this.scale, height * TITLE_SIZE * this.scale);
        this.labels = new Label[height][width];
    }

    /**
     * Builds the new grid for a game of giiden size.
     * @param height Height of the game.
     * @param width Width of the game.
     */
    private void buildNewGameGrid(int height, int width)
    {
        // Set up columns in game's grid view.
        for (int col = 0; col < width; col++)
        {
            ColumnConstraints columnConstraint = new ColumnConstraints();
            columnConstraint.setPercentWidth(100D / width);

            this.gameView.getColumnConstraints().add(columnConstraint);
        }

        // Set up each row and its labels in the game's grid view.
        for (int row = 0; row < height; row++)
        {
            RowConstraints rowConstraint = new RowConstraints();
            rowConstraint.setPercentHeight(100D / height);

            this.gameView.getRowConstraints().add(rowConstraint);
            for (int col = 0; col < width; col++)
            {

                labels[row][col] = new Label("");
                labels[row][col].setAlignment(Pos.CENTER);
                labels[row][col].setFont(Font.font("System", FontWeight.BOLD, 48D));

                labels[row][col].setMaxWidth(Double.MAX_VALUE);
                labels[row][col].setMaxHeight(Double.MAX_VALUE);


                this.setLabel(row, col, 0);

                this.gameView.add(labels[row][col], col, row);
                GridPane.setMargin(labels[row][col], new Insets(7.5f * scale));
            }
        }
    }

    /**
     * Set the value in each label in the grid view.
     * @param state The current state of the game.
     */
    public void setValues(GameState state)
    {
        if (state.getGrid().length == height && state.getGrid()[0].length == width)
        {
            for (int row = 0; row < height; row++)
            {
                for (int col = 0; col < width; col++)
                {
                    setLabel(row, col, state.getGrid()[row][col]);
                }
            }
        }
        this.score.setText(Integer.toString(state.getScore()));
    }

    @Override
    public void updateGrid(GameState state)
    {
        Platform.runLater(() -> this.setValues(state));
    }

    /**
     * Set the value of a specific label in the grid.
     * @param row Row of the label to set.
     * @param col Column of the label to set.
     * @param value Value to display in the cell.
     */
    private void setLabel(int row, int col, int value)
    {
        String str_value = Integer.toString(value);
        if (value == 0)
        {
            str_value = "";
        }

        double font_size = 32 * this.scale;
        if (str_value.length() >= 5)
        {
            font_size = 16D * this.scale;
        }
        else if (str_value.length() >= 3)
        {
            font_size = 24D * this.scale;
        }


        labels[row][col].setFont(Font.font("System", FontWeight.BOLD, font_size));

        this.labels[row][col].setText(str_value);
        this.labels[row][col].setBackground(
                Background.fill(Paint.valueOf(BACK_COLOURS.getOrDefault(value, "#3c3a33"))));

        this.labels[row][col].setTextFill(
                 Paint.valueOf(TEXT_COLOURS.getOrDefault(value, "#f9f6f2")));
    }
}

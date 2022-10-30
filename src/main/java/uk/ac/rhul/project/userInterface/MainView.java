package uk.ac.rhul.project.userInterface;

import javafx.application.Application;
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
import uk.ac.rhul.project.MoveObserver;
import uk.ac.rhul.project.NewGameObserver;
import uk.ac.rhul.project.game.Direction;

import java.util.HashMap;
import java.util.Optional;

public class MainView extends Application
{
    Label[][] labels;
    private int height;
    private int width;
    private static volatile MainView instance = null;

    private static final float TITLE_SIZE = 100f;

    public float scale;


    // Colour comes from [5]. It is the colour of an empty cell.
    private static final HashMap<Integer, String> backColours = new HashMap<Integer, String>() {{
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

    private static final HashMap<Integer, String> foreColours = new HashMap<Integer, String>() {{
        put(2, "#776e65");
        put(4, "#776e65");
    }};

    private NewGameObserver newGameObserver;

    @FXML
    private GridPane gameView;

    @FXML
    private Button newGame;

    @FXML
    private Label score;

    @FXML
    private Scene root;

    @FXML
    private GridPane mainGrid;
    
    private Dialog<int[]> newGameDialog;

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

    public static synchronized MainView getInstance()
    {
       if (instance==null)
       {
           new Thread(() -> Application.launch(MainView.class)).start();
           while (instance == null);
       }

       return instance;
    }

    public void startNewGame()
    {
        this.newGameObserver.notifyObservers(4, 4);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
        MainView controller = new MainView();
        fxmlLoader.setController(controller);

        Scene root = fxmlLoader.load();

        primaryStage.setScene(root);
        primaryStage.show();
    }

    public void addNewGameObserver(NewGameObserver method)
    {
        this.newGameObserver = method;
        this.newGame.setOnAction(event ->
        {
            Optional<int[]> result = this.newGameDialog.showAndWait();
            result.ifPresent(size -> {
                this.make2048Grid(size[0], size[1]);
                method.notifyObservers(size[0], size[1]);
            });

        });
    }

    public void addMoveObserver(MoveObserver method)
    {

        this.root.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode())
            {
                case W -> method.notifyObservers(Direction.UP);
                case A -> method.notifyObservers(Direction.LEFT);
                case S -> method.notifyObservers(Direction.DOWN);
                case D -> method.notifyObservers(Direction.RIGHT);
            }
        });
    }

    public void make2048Grid(int height, int width)
    {
        this.height = height;
        this.width = width;

        if (height > 16)
        {
            this.scale = 0.4f;
        }
        else if (width > 16 || height > 8)
        {
            this.scale = 0.5f;
        } else if (width > 8 || height  > 4)
        {
            this.scale = 1f;
        } else
        {
            this.scale = 1.5f;
        }

        this.gameView.getChildren().removeAll(this.gameView.getChildren());
        this.gameView.getRowConstraints().removeAll(this.gameView.getRowConstraints());
        this.gameView.getColumnConstraints().removeAll(this.gameView.getColumnConstraints());

        this.mainGrid.getRowConstraints().get(3).setPrefHeight(height * TITLE_SIZE * this.scale + 30f );
        this.mainGrid.getColumnConstraints().get(0).setPrefWidth(width * TITLE_SIZE * this.scale - 110);

        this.root.getWindow().setHeight(height * TITLE_SIZE * this.scale+ 220f);
        this.root.getWindow().setWidth(width * TITLE_SIZE * this.scale + 40f);

        this.gameView.setPrefSize(width * TITLE_SIZE * this.scale, height * TITLE_SIZE * this.scale);

        this.labels = new Label[height][width];


        for (int col = 0; col < width; col++)
        {
            ColumnConstraints columnConstraint = new ColumnConstraints();
            columnConstraint.setPercentWidth(100D / width);

            this.gameView.getColumnConstraints().add(columnConstraint);
        }

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

    public void setValues(int[][] arr, int score)
    {
        if (arr.length == height && arr[0].length == width)
        {
            for (int row = 0; row < height; row++)
            {
                for (int col = 0; col < width; col++)
                {
                    setLabel(row, col, arr[row][col]);
                }
            }
        }
        this.score.setText(Integer.toString(score));
    }

    public void setLabel(int row, int col, int value)
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
                Background.fill(Paint.valueOf(backColours.getOrDefault(value, "#3c3a33"))));

        this.labels[row][col].setTextFill(
                 Paint.valueOf(foreColours.getOrDefault(value, "#f9f6f2")));
    }
}

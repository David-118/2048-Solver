package uk.ac.rhul.project.game;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import uk.ac.rhul.project.MoveObserver;
import uk.ac.rhul.project.Observer;

public class GameView extends Application
{
    Label[][] labels;
    private int height;
    private int width;
    private static volatile GameView instance = null;

    private Observer newGameObserver;

    @FXML
    private GridPane gameView;

    @FXML
    private Button newGame;

    @FXML
    private Scene root;

    @FXML
    void initialize()
    {
        this.make2048Grid(4, 4);
        System.out.println("Loaded");
        instance = this;
    }

    public static synchronized GameView getInstance()
    {
       if (instance==null)
       {
           new Thread(() -> Application.launch(GameView.class)).start();
           while (instance == null);
       }

       return instance;
    }

    public void startNewGame()
    {
        this.newGameObserver.notifyObservers();
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
        GameView controller = new GameView();
        fxmlLoader.setController(controller);

        Scene root = fxmlLoader.load();

        primaryStage.setScene(root);
        primaryStage.show();
    }

    public void addNewGameObserver(Observer method)
    {
        this.newGameObserver = method;
        this.newGame.setOnAction(event -> method.notifyObservers());
        this.newGame.setOnMouseClicked(event -> method.notifyObservers());
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

        this.labels = new Label[height][width];


        this.gameView.getColumnConstraints().removeAll();
        this.gameView.getRowConstraints().removeAll();
        this.gameView.getChildren().removeAll();


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

                // Colour comes from [5]. It is the colour of an empty cell.
                labels[row][col].setBackground(Background.fill(Paint.valueOf("rgba(238, 228, 218, 0.35)")));

                this.gameView.add(labels[row][col], col, row);
                GridPane.setMargin(labels[row][col], new Insets(10));
            }
        }
    }

    public void setValues(int[][] arr)
    {
        if (arr.length == height && arr[0].length == width)
        {
            for (int row = 0; row < height; row++)
            {
                for (int col = 0; col < width; col++)
                {
                    if (arr[row][col] != 0)
                    {
                        this.labels[row][col].setText(Integer.toString(arr[row][col]));
                        this.labels[row][col].setBackground(Background.fill(Paint.valueOf("#eee4da")));
                    } else
                    {
                        this.labels[row][col].setText("");
                        labels[row][col].setBackground(Background.fill(Paint.valueOf("rgba(238, 228, 218, 0.35)")));
                    }
                }
            }
        }
    }
}

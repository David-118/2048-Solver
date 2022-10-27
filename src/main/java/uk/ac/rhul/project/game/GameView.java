package uk.ac.rhul.project.game;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javax.swing.*;

public class GameView
{
    Label[][] labels;

    @FXML
    private GridPane gameView;

    @FXML
    void initialize()
    {
        make2048Grid(4, 4);
        System.out.println("Loaded");
    }

    public void make2048Grid(int height, int width)
    {
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
}

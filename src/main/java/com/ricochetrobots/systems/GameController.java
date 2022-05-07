package com.ricochetrobots.systems;

import com.ricochetrobots.components.ColorRobot;
import com.ricochetrobots.entities.Robot;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import org.controlsfx.control.spreadsheet.Grid;

import java.io.File;
import java.io.InputStream;

public class GameController {
    @FXML
    public GridPane gridPane;
    private final Pane[][] board = new Pane[16][16];

    public String urlImage = "file:assets/";

    @FXML
    public void initialize() {
        for (int i =0 ; i<16; i++){
            for (int j=0 ; j<16 ;j++){
                Pane pane = new Pane() ;
                Image imageCell = new Image(urlImage + "GridUnit.png", 37.5, 37.5, false, false);
                ImageView image = new ImageView(imageCell);

                pane.getChildren().add(image);

                int finalI = i;
                int finalJ = j;

                if((i != 8 && i != 7) || (j != 7 && j != 8)){
                    this.board[i][j] = pane;
                    this.gridPane.add(pane, i, j);
                }
            }
        }

        addRobotToBoard(ColorRobot.RED);
        addRobotToBoard(ColorRobot.GREEN);
        addRobotToBoard(ColorRobot.BLUE);
        addRobotToBoard(ColorRobot.YELLOW);
    }

    public void addRobotToBoard(ColorRobot color){
        Robot robot = new Robot(color);
        ImageView imgRobot = new ImageView(new Image(urlImage + robot.getImageSignature() + ".png", 35, 35, false, true));
        gridPane.add(imgRobot, robot.getCol(), robot.getLig());

    }
}
package com.ricochetrobots.systems;

import com.ricochetrobots.components.ColorRobot;
import com.ricochetrobots.components.Position;
import com.ricochetrobots.entities.Game;
import com.ricochetrobots.entities.Robot;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.List;

public class GameController {
    @FXML
    public GridPane gridPane;
    private final Pane[][] board = new Pane[16][16];
    private Robot[][] robots = new Robot[16][16];
    private List<Position> possibleMoves;

    public String urlImage = "file:assets/";

    private Game game = new Game(this);
    @FXML
    public void initialize() {
        // On affiche les cellule sur le plateau de jeu
        for (int i = 0 ; i < 16; i++){
            for (int j = 0 ; j< 16 ;j++){
                Image imageCell = new Image(urlImage + "GridUnit.png", 37.5, 37.5, false, false);
                ImageView image = new ImageView(imageCell);
                ImageView imageRobot = new ImageView();

                Pane pane = new Pane() ;
                pane.getChildren().add(image);
                pane.getChildren().add(imageRobot);

                int finalI = i;
                int finalJ = j;

                if((i != 8 && i != 7) || (j != 7 && j != 8)){
                    this.board[i][j] = pane;
                    this.gridPane.add(pane, i, j);
                    pane.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, e -> game.onRobotClick(finalJ, finalI, this.robots));
                }
            }
        }

        addRobotToBoard(ColorRobot.RED);
        addRobotToBoard(ColorRobot.GREEN);
        addRobotToBoard(ColorRobot.BLUE);
        addRobotToBoard(ColorRobot.YELLOW);
    }

    // On ajoute les robots dans le tableau à l'initialisation
    public void addRobotToBoard(ColorRobot color){
        Robot robot = new Robot(color);
        this.robots[robot.getLig()][robot.getCol()] = robot;
        setRobot(robot.getLig(), robot.getCol(), robot);
        /*ImageView imgRobot = new ImageView(new Image(urlImage + robot.getImageSignature() + ".png", 35, 35, false, true));
        gridPane.add(imgRobot, robot.getCol(), robot.getLig());
        imgRobot.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, e -> game.onRobotClick(robot.getLig(), robot.getCol(), this.robots));*/
    }

    // On met à jour
    public void setRobot(int x, int y, Robot robot) {
        ImageView imageRobot = (ImageView) board[x][y].getChildren().get(1);
        imageRobot.setImage(new Image(urlImage + robot.getImageSignature() + ".png", 35, 35, false, true));
        //imageRobot.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, e -> game.onRobotClick(y, x, this.robots));
    }

    public void clearPiece(int x, int y, Robot robot) {
        if (board[x][y] != null) {
            ImageView imageRobot = (ImageView) board[x][y].getChildren().get(1);
            imageRobot.setImage(null);
        }
    }

    public void setPossibleMove(Position pos) {
        Pane pane = board[pos.getX()][pos.getY()];

        ImageView imgCell = (ImageView) pane.getChildren().get(0);
        imgCell.setImage(new Image(urlImage + "GridUnit.png", 37.5, 37.5, false, false));
        imgCell.setOpacity(0.4);
    }

    public void clearPossibleMoves() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                Pane pane = board[i][j];
                if (pane != null) {
                    ImageView imgCell = (ImageView) pane.getChildren().get(0);
                    imgCell.setImage(new Image(urlImage + "GridUnit.png", 37.5, 37.5, false, false));
                    imgCell.setOpacity(1.0);
                }
            }
        }
    }
}
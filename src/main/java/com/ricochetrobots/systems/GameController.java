package com.ricochetrobots.systems;

import com.ricochetrobots.components.ColorRobot;
import com.ricochetrobots.components.Orientation;
import com.ricochetrobots.components.Pattern;
import com.ricochetrobots.components.Position;
import com.ricochetrobots.entities.Game;
import com.ricochetrobots.entities.Robot;
import com.ricochetrobots.entities.Token;
import com.ricochetrobots.entities.Wall;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


import java.util.List;

public class GameController {
    @FXML
    public GridPane gridPane;
    private final Pane[][] board = new Pane[16][16];
    public Label targetSentenceText;
    public ImageView targetImage;
    private Robot[][] robots = new Robot[16][16];
    private Token[][] tokens = new Token[16][16];
    private Wall[][] walls = new Wall[16][16];
    private List<Position> possibleMoves;

    public String urlImage = "file:assets/";
    private String colorTargetText;
    private String patternTargetText;

    private Game game = new Game(this);
    @FXML
    public void initialize() {

        // On affiche les cellule sur le plateau de jeu
        Insets mars_pads = new Insets(0, 0, 0, 0);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(mars_pads);
        gridPane.setHgap(0);
        gridPane.setVgap(0);
        gridPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, new BorderWidths(5))));

        // On affiche les cellule sur le plateau de jeu
        for (int i = 0 ; i < 16; i++){
            for (int j = 0 ; j< 16 ;j++){
                Image imageCell = new Image(urlImage + "GridUnit.png", 37.5, 37.5, false, true);
                ImageView image = new ImageView(imageCell);
                ImageView imageRobot = new ImageView();
                ImageView imageToken = new ImageView();
                ImageView imageWall = new ImageView();

                StackPane pane = new StackPane() ;

                pane.getChildren().add(image);
                pane.getChildren().add(imageToken);
                pane.getChildren().add(imageRobot);
                StackPane.setAlignment(imageToken,Pos.CENTER); //set it to the Center Left(by default it's on the center)
                StackPane.setAlignment(imageRobot,Pos.CENTER); //set it to the Center Left(by default it's on the center)
                pane.getChildren().add(imageWall);

                int finalI = i;
                int finalJ = j;

                // Si l'on est pas dans l'axe du tableau, on affiche les cellules
                if((i != 8 && i != 7) || (j != 7 && j != 8)){
                    this.board[i][j] = pane;
                    this.gridPane.add(pane, i, j);
                    pane.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> game.onRobotClick(finalJ, finalI, this.robots));
                }
            }
        }

        // On ajoute les 16 tokens sur le plateau
        /*addTokenToGrid(ColorRobot.RED, Pattern.PLANET, 5, 2);
        addTokenToGrid(ColorRobot.BLUE, Pattern.SUN, 7, 4);
        addTokenToGrid(ColorRobot.GREEN, Pattern.STAR, 2, 5);
        addTokenToGrid(ColorRobot.YELLOW, Pattern.MOON, 2, 6);
        addTokenToGrid(ColorRobot.RED, Pattern.STAR, 1, 10);
        addTokenToGrid(ColorRobot.BLUE, Pattern.MOON, 3, 11);
        addTokenToGrid(ColorRobot.GREEN, Pattern.PLANET, 4, 11);
        addTokenToGrid(ColorRobot.YELLOW, Pattern.SUN, 6, 13);
        addTokenToGrid(ColorRobot.GREEN, Pattern.SUN, 12, 1);
        addTokenToGrid(ColorRobot.RED, Pattern.MOON, 14, 4);
        addTokenToGrid(ColorRobot.BLUE, Pattern.PLANET, 11, 6);
        addTokenToGrid(ColorRobot.YELLOW, Pattern.STAR, 9, 3);
        addTokenToGrid(ColorRobot.RED, Pattern.SUN, 14, 12);
        addTokenToGrid(ColorRobot.BLUE, Pattern.STAR, 13, 9);
        addTokenToGrid(ColorRobot.YELLOW, Pattern.PLANET, 10, 10);
        addTokenToGrid(ColorRobot.GREEN, Pattern.MOON, 11, 14);*/

        addTokenToGrid(ColorRobot.RED, Pattern.PLANET, 15, 6);
        addTokenToGrid(ColorRobot.BLUE, Pattern.SUN, 15, 5);
        addTokenToGrid(ColorRobot.GREEN, Pattern.STAR, 4, 15);
        addTokenToGrid(ColorRobot.YELLOW, Pattern.MOON, 3, 15);
        addTokenToGrid(ColorRobot.RED, Pattern.STAR, 2, 15);
        addTokenToGrid(ColorRobot.BLUE, Pattern.MOON, 1, 15);
        addTokenToGrid(ColorRobot.GREEN, Pattern.PLANET, 0, 15);
        addTokenToGrid(ColorRobot.YELLOW, Pattern.SUN, 15, 5);
        addTokenToGrid(ColorRobot.GREEN, Pattern.SUN, 12, 0);
        addTokenToGrid(ColorRobot.RED, Pattern.MOON, 15, 4);
        addTokenToGrid(ColorRobot.BLUE, Pattern.PLANET, 15, 3);
        addTokenToGrid(ColorRobot.YELLOW, Pattern.STAR, 15, 2);
        addTokenToGrid(ColorRobot.RED, Pattern.SUN, 15, 1);
        addTokenToGrid(ColorRobot.BLUE, Pattern.STAR, 15, 0);
        addTokenToGrid(ColorRobot.YELLOW, Pattern.PLANET, 15, 15);
        addTokenToGrid(ColorRobot.GREEN, Pattern.MOON, 0, 15);

        // On met en place les murs
        addWallToBoard(Orientation.EAST, 10,14);


        // On définit le jeton cible
        game.defineTarget();

        // On ajoute les robots
        addRobotToBoard(ColorRobot.RED);
        addRobotToBoard(ColorRobot.GREEN);
        addRobotToBoard(ColorRobot.BLUE);
        addRobotToBoard(ColorRobot.YELLOW);

        updateScreen();

    }

    // Méthode pour ajouter les robots sur le plateau
    public void addRobotToBoard(ColorRobot color){
        Robot robot = new Robot(color, this);
        this.robots[robot.getLig()][robot.getCol()] = robot;
        setRobot(robot.getLig(), robot.getCol(), robot);
    }

    // On définit le robot
    public void setRobot(int x, int y, Robot robot) {
        ImageView imageRobot = (ImageView) board[x][y].getChildren().get(2);
        imageRobot.setImage(new Image(urlImage + robot.getImageSignature() + ".png", 35, 35, false, true));
        //imageRobot.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, e -> game.onRobotClick(y, x, this.robots));
    }

    // S'il n'y a pas de robot, on l'efface
    public void clearPiece(int x, int y, Robot robot) {
        if (board[x][y] != null) {
            ImageView imageRobot = (ImageView) board[x][y].getChildren().get(2);
            imageRobot.setImage(null);
        }
    }

    // On affiche les mouvements possibles du robot sur le tableau
    public void setPossibleMove(Position pos) {
        Pane pane = board[pos.getX()][pos.getY()];

        ImageView imgCell = (ImageView) pane.getChildren().get(0);
        imgCell.setImage(new Image(urlImage + "GridUnit.png", 37.5, 37.5, false, true));
        imgCell.setOpacity(0.4);
    }

    // On "nettoie" les mouvements du robot
    public void clearPossibleMoves() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                Pane pane = board[i][j];
                if (pane != null) {
                    ImageView imgCell = (ImageView) pane.getChildren().get(0);
                    imgCell.setImage(new Image(urlImage + "GridUnit.png", 37.5, 37.5, false, true));
                    imgCell.setOpacity(1.0);
                }
            }
        }
    }

    public void addTokenToGrid(ColorRobot color, Pattern pattern, int x, int y){
        Token token = new Token(color, pattern, x, y);
        this.tokens[token.getLig()][token.getCol()] = token;
        setToken(token.getLig(), token.getCol(), token);
    }

    public void setToken(int x, int y, Token token) {
        ImageView imageRobot = (ImageView) board[x][y].getChildren().get(1);
        imageRobot.setImage(new Image(urlImage + "/tokens/" + token.getImageSignature() + ".png", 28,28, true, true));
    }

    public Token[][] getTokens() {
        return tokens;
    }

    public void setTargetImage(){
        for (int i = 0; i<16; i++){
            for (int j = 0; j<16; j++){
                if (getTokens()[i][j] != null) {
                    Token token = getTokens()[i][j];
                    if (token.isTarget()) {          // Si les noms correspondent, on définit la cible
                        targetImage.setImage(new Image(urlImage + "tokens/" + token.getImageSignature() + ".png"));
                    }
                }
            }
        }
    }

    public String getColorTargetText(){
        for (int i = 0; i<16; i++){
            for (int j = 0; j<16; j++){
                if (getTokens()[i][j] != null) {
                    Token token = getTokens()[i][j];
                    if (token.isTarget()) {          // Si les noms correspondent, on définit la cible
                        String colorToken = token.getColor().toString();
                        switch(colorToken){
                            case "R" -> colorTargetText = "rouge";
                            case "B" -> colorTargetText = "bleu";
                            case "G" -> colorTargetText = "vert";
                            case "Y" -> colorTargetText = "jaune";
                        }
                    }
                }
            }
        }
        return colorTargetText;

    }

    public String getPatternTargetText(){
        for (int i = 0; i<16; i++){
            for (int j = 0; j<16; j++){
                if (getTokens()[i][j] != null) {
                    Token token = getTokens()[i][j];
                    if (token.isTarget()) {          // Si les noms correspondent, on définit la cible
                        String patternToken = token.getPattern().toString();
                        switch(patternToken){
                            case "M" -> patternTargetText = "lune";
                            case "P" -> patternTargetText = "planête";
                            case "ST" -> patternTargetText = "étoile";
                            case "SU" -> patternTargetText = "soleil";
                        }
                    }
                }
            }
        }
        return patternTargetText;
    }

    public void setTargetSentenceText(){
        String color = getColorTargetText();
        String pattern = getPatternTargetText();
        targetSentenceText.setText("Déplacer le robot " + color + " jusqu'à sa cible " + pattern + " !");
    }

    public void updateScreen(){
        setTargetImage();
        setTargetSentenceText();
    }

    public void addWallToBoard(Orientation orientation, int x, int y){
        Wall wall = new Wall(x,y,orientation );
        this.walls[wall.getLig()][wall.getCol()] = wall;
        setWall(wall.getLig(), wall.getCol(), wall);
    }

    // On définit le robot
    public void setWall(int x, int y, Wall wall) {
        ImageView imageWall = (ImageView) board[x][y].getChildren().get(3);
        System.out.println(urlImage +"Wall/" + wall.getImageSignature() + ".png");
        imageWall.setImage(new Image(urlImage +"Wall/" + wall.getImageSignature() + ".png", 35, 35, false, true));
    }
}
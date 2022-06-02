package com.ricochetrobots.systems;

import com.ricochetrobots.components.ColorRobot;
import com.ricochetrobots.components.Orientation;
import com.ricochetrobots.components.Pattern;
import com.ricochetrobots.components.Position;
import com.ricochetrobots.entities.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameController {

    private List<Object> dataTransmitted = (List<Object>) MainApplication.stage.getUserData();
    private final List<Player> players = (List<Player>) dataTransmitted.get(0);
    public List<Token> tokenList = new ArrayList<Token>();
    private int scoreToReach = (int) dataTransmitted.get(1);

    @FXML
    public GridPane gridPane;
    private final Pane[][] board = new Pane[16][16];
    public Label targetSentenceText;
    public HBox hBoxPlayer1;
    public TextField textFieldPlayer1;
    public HBox hBoxPlayer2;
    public TextField textFieldPlayer2;
    public Button validateShotsButton;
    public Label namePlayer2;
    public Label textScorePlayer2;
    public Label namePlayer1;
    public Label textScorePlayer1;
    public Label scoreToReachLabel;
    public Label numberOfShotsPlayedLabel;
    public Label maxNumberOfShotsLabel;
    public Label timerLabel;
    public VBox vBoxTimer;

    private Robot[][] robots = new Robot[16][16];
    private Token[][] tokens = new Token[16][16];
    private Wall[][] walls = new Wall[16][16];
    private List<Position> possibleMoves;

    public String urlImage = "file:assets/";
    private String colorTargetText;
    private String patternTargetText;

    private Game game = new Game(players, scoreToReach);
    public int numberOfShotsPlayer1;
    public int numberOfShotsPlayer2;
    public  Player playerTurn;

    @FXML
    public void initialize() {

        gridPane.setDisable(true);
        setVisibilityHBox();
        scoreToReachLabel.setText("" + game.getScoreToReach());
        maxNumberOfShotsLabel.setVisible(false);
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
                ImageView imageCenterToken = new ImageView();

                StackPane pane = new StackPane() ;

                pane.getChildren().add(image);
                pane.getChildren().add(imageToken);
                pane.getChildren().add(imageRobot);
                StackPane.setAlignment(imageToken,Pos.CENTER); //set it to the Center Left(by default it's on the center)
                StackPane.setAlignment(imageRobot,Pos.CENTER); //set it to the Center Left(by default it's on the center)

                pane.getChildren().add(imageWall);
                pane.getChildren().add(imageCenterToken);

                int finalI = i;
                int finalJ = j;

                // Si l'on est pas dans l'axe du tableau, on affiche les cellules
                if((i != 8 && i != 7) || (j != 7 && j != 8)){
                    this.board[i][j] = pane;
                    this.gridPane.add(pane, i, j);
                    pane.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                        try {
                            game.onRobotClick(finalJ, finalI, this.robots, this.walls, this);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    });
                }
            }
        }

        /*addTokenToGrid(ColorRobot.RED, Pattern.PLANET, 15, 6);
        addTokenToGrid(ColorRobot.BLUE, Pattern.SUN, 15, 14);
        addTokenToGrid(ColorRobot.GREEN, Pattern.STAR, 4, 15);
        addTokenToGrid(ColorRobot.YELLOW, Pattern.MOON, 3, 15);
        addTokenToGrid(ColorRobot.RED, Pattern.STAR, 2, 15);
        addTokenToGrid(ColorRobot.BLUE, Pattern.MOON, 1, 15);
        addTokenToGrid(ColorRobot.GREEN, Pattern.PLANET, 0, 14);
        addTokenToGrid(ColorRobot.YELLOW, Pattern.SUN, 15, 5);
        addTokenToGrid(ColorRobot.GREEN, Pattern.SUN, 0, 0);
        addTokenToGrid(ColorRobot.RED, Pattern.MOON, 15, 4);
        addTokenToGrid(ColorRobot.BLUE, Pattern.PLANET, 15, 3);
        addTokenToGrid(ColorRobot.YELLOW, Pattern.STAR, 15, 2);
        addTokenToGrid(ColorRobot.RED, Pattern.SUN, 15, 1);
        addTokenToGrid(ColorRobot.BLUE, Pattern.STAR, 15, 0);
        addTokenToGrid(ColorRobot.YELLOW, Pattern.PLANET, 15, 15);
        addTokenToGrid(ColorRobot.GREEN, Pattern.MOON, 0, 15);*/

        // On ajoute les 16 tokens sur le plateau
        addTokenToGrid(ColorRobot.BLUE, Pattern.STAR, 2, 5);
        addTokenToGrid(ColorRobot.GREEN, Pattern.SUN, 1, 13);
        addTokenToGrid(ColorRobot.BLUE, Pattern.PLANET, 3, 9);
        addTokenToGrid(ColorRobot.GREEN, Pattern.MOON, 4, 2);
        addTokenToGrid(ColorRobot.RED, Pattern.MOON, 4, 14);
        addTokenToGrid(ColorRobot.RED, Pattern.SUN, 5, 7);
        addTokenToGrid(ColorRobot.YELLOW, Pattern.PLANET, 6, 1);
        addTokenToGrid(ColorRobot.YELLOW, Pattern.STAR, 6, 12);
        addTokenToGrid(ColorRobot.YELLOW, Pattern.SUN, 9, 4);
        addTokenToGrid(ColorRobot.BLUE, Pattern.SUN, 9, 13);
        addTokenToGrid(ColorRobot.BLUE, Pattern.MOON, 10, 6);
        addTokenToGrid(ColorRobot.YELLOW, Pattern.MOON, 11, 9);
        addTokenToGrid(ColorRobot.RED, Pattern.STAR, 13, 1);
        addTokenToGrid(ColorRobot.RED, Pattern.PLANET, 13, 14);
        addTokenToGrid(ColorRobot.GREEN, Pattern.PLANET, 14, 3);
        addTokenToGrid(ColorRobot.GREEN, Pattern.STAR, 14, 10);

        // On met en place les murs
        addWallToBoard(Orientation.EAST, 0,3);
        addWallToBoard(Orientation.EAST, 0,9);
        addWallToBoard(Orientation.EAST, 2,5);
        addWallToBoard(Orientation.NORTH, 3,5);
        addWallToBoard(Orientation.WEST, 1,14);
        addWallToBoard(Orientation.NORTH, 2,13);
        addWallToBoard(Orientation.WEST, 3,9);
        addWallToBoard(Orientation.SOUTH, 2,9);
        addWallToBoard(Orientation.SOUTH, 4,0);
        addWallToBoard(Orientation.SOUTH, 10,0);
        addWallToBoard(Orientation.EAST, 15,4);
        addWallToBoard(Orientation.EAST, 15,11);
        addWallToBoard(Orientation.SOUTH, 1,15);
        addWallToBoard(Orientation.SOUTH, 11,15);
        addWallToBoard(Orientation.SOUTH, 3,2);
        addWallToBoard(Orientation.EAST, 4,2);
        addWallToBoard(Orientation.EAST, 4,14);
        addWallToBoard(Orientation.SOUTH, 3,14);
        addWallToBoard(Orientation.EAST, 5,6);
        addWallToBoard(Orientation.NORTH, 6,7);
        addWallToBoard(Orientation.EAST, 6,0);
        addWallToBoard(Orientation.SOUTH, 5,1);
        addWallToBoard(Orientation.EAST, 6,11);
        addWallToBoard(Orientation.NORTH, 7,12);
        addWallToBoard(Orientation.EAST, 9,3);
        addWallToBoard(Orientation.NORTH, 10,4);
        addWallToBoard(Orientation.EAST, 9,12);
        addWallToBoard(Orientation.NORTH, 10,13);
        addWallToBoard(Orientation.EAST, 10,5);
        addWallToBoard(Orientation.SOUTH, 9,6);
        addWallToBoard(Orientation.EAST, 11,9);
        addWallToBoard(Orientation.NORTH, 12,9);
        addWallToBoard(Orientation.SOUTH, 11,7);
        addWallToBoard(Orientation.EAST, 12,7);
        addWallToBoard(Orientation.NORTH, 13,1);
        addWallToBoard(Orientation.WEST, 13,2);
        addWallToBoard(Orientation.SOUTH, 14,3);
        addWallToBoard(Orientation.WEST, 14,4);
        addWallToBoard(Orientation.WEST, 14,10);
        addWallToBoard(Orientation.SOUTH, 13,10);
        addWallToBoard(Orientation.NORTH, 13,14);
        addWallToBoard(Orientation.WEST, 13,15);

        // On définit le jeton cible
        game.defineTarget(robots, this);

        // On ajoute les robots
        addRobotToBoard(ColorRobot.RED);
        addRobotToBoard(ColorRobot.GREEN);
        addRobotToBoard(ColorRobot.BLUE);
        addRobotToBoard(ColorRobot.YELLOW);

        // Image du centre
        setCenterTargetImages();
        updateScreen();
    }

    // Méthode pour ajouter les robots sur le plateau
    public void addRobotToBoard(ColorRobot color){
        Robot robot = new Robot(color, getTokens());
        this.robots[robot.getLig()][robot.getCol()] = robot;
        setRobot(robot.getLig(), robot.getCol(), robot);
    }

    // On définit le robot
    public void setRobot(int x, int y, Robot robot) {
        ImageView imageRobot = (ImageView) board[x][y].getChildren().get(2);
        imageRobot.setImage(new Image(urlImage + robot.getImageSignature() + ".png", 35, 35, false, true));
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

    // On ajoute le jeton sur la grille
    public void addTokenToGrid(ColorRobot color, Pattern pattern, int x, int y){
        Token token = new Token(color, pattern, x, y);
        this.tokens[token.getLig()][token.getCol()] = token;
        setToken(token.getLig(), token.getCol(), token);
        tokenList.add(token);
    }

    // On définit le jeton graphiquement
    public void setToken(int x, int y, Token token) {
        ImageView imageRobot = (ImageView) board[x][y].getChildren().get(1);
        imageRobot.setImage(new Image(urlImage + "/tokens/" + token.getImageSignature() + ".png", 28,28, true, true));
    }

    // On nettoie un token si un joueur gagne une partie
    public void clearToken(int x, int y, Token token) {
        if (board[x][y] != null) {
            ImageView imageToken = (ImageView) board[x][y].getChildren().get(1);
            imageToken.setImage(null);
        }
    }

    public Token[][] getTokens() {
        return tokens;
    }

    // On récupère le jeton cible
    public Token getTargetToken(){
        Token targetToken = null;
        for (int i = 0; i<16; i++){
            for (int j = 0; j<16; j++){
                if (getTokens()[i][j] != null) {
                    Token token = getTokens()[i][j];
                    if (token.isTarget()) {
                        targetToken = token;
                        break;
                    }
                }
            }
        }
        return targetToken;
    }

    // On place l'image du jeton cible au centre
    public void setCenterTargetImages(){
        for (int i = 0 ; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (getTargetToken() != null) {
                    if (i == 7 && j == 7) {
                        Image imageTargetCenter = new Image(urlImage + "/center_tokens/" + getTargetToken().getImageSignature() + "_1.png", 37.5, 37.5, true, true);
                        ImageView imageCenter = new ImageView(imageTargetCenter);
                        this.gridPane.add(imageCenter, i, j);
                    }
                    if (i == 8 && j == 7) {
                        Image imageTargetCenter = new Image(urlImage + "/center_tokens/" + getTargetToken().getImageSignature() + "_2.png", 37.5, 37.5, true, true);
                        ImageView imageCenter = new ImageView(imageTargetCenter);
                        this.gridPane.add(imageCenter, i, j);
                    }
                    if (i == 7 && j == 8) {
                        Image imageTargetCenter = new Image(urlImage + "/center_tokens/" + getTargetToken().getImageSignature() + "_3.png", 37.5, 37.5, true, true);
                        ImageView imageCenter = new ImageView(imageTargetCenter);
                        this.gridPane.add(imageCenter, i, j);
                    }
                    if (i == 8 && j == 8) {
                        Image imageTargetCenter = new Image(urlImage + "/center_tokens/" + getTargetToken().getImageSignature() + "_4.png", 37.5, 37.5, true, true);
                        ImageView imageCenter = new ImageView(imageTargetCenter);
                        this.gridPane.add(imageCenter, i, j);
                    }
                }
            }
        }
    }

    // Paramètres pour le texte en haut
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

    // Paramètres pour le texte en haut
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

    // Texte en haut
    public void setTargetSentenceText(){
        String color = getColorTargetText();
        String pattern = getPatternTargetText();
        targetSentenceText.setText("Déplacer le robot " + color + " jusqu'à sa cible " + pattern + " !");
    }

    // On met à jour certaines données affichées à l'écran
    public void updateScreen(){
        setTargetSentenceText();
        // Nom et score des joueurs
        namePlayer1.setText(players.get(0).getName() + " : ");
        textScorePlayer1.setText(" " + players.get(0).getScore());

        if (players.size() > 1) {
            namePlayer2.setText(players.get(1).getName() + " : ");
            textScorePlayer2.setText(" " + players.get(1).getScore());
        }


    }

    // On ajoute les murs
    public void addWallToBoard(Orientation orientation, int x, int y){
        Wall wall = new Wall(x,y,orientation );
        this.walls[wall.getLig()][wall.getCol()] = wall;
        setWall(wall.getLig(), wall.getCol(), wall);
    }

    // On définit graphiquement les murs
    public void setWall(int x, int y, Wall wall) {
        ImageView imageWall = (ImageView) board[x][y].getChildren().get(3);
        imageWall.setImage(new Image(urlImage +"Wall/" + wall.getImageSignature() + ".png", 37.5, 37.5, false, true));
    }

    // Bouton valider le nombre de coups
    public void validateShotsOnClick(ActionEvent actionEvent) {
        game.getNumberOfShotsExpected(numberOfShotsPlayer1, numberOfShotsPlayer2, this);
        if (game.isTimerOn()){
            game.getChrono().schedule(new CustomTimer(10, this, game), 0, 1000); // On lance le timer
        }
    }

    // Nombre de coups
    @FXML
    public void onSetNumber() {

        // Si on ne rentre pas un nombre, on supprime ce qui vient d'être écrit
        textFieldPlayer1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    textFieldPlayer1.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        numberOfShotsPlayer1 = Integer.parseInt(textFieldPlayer1.getText());

        textFieldPlayer2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    textFieldPlayer2.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        numberOfShotsPlayer2 = Integer.parseInt(textFieldPlayer2.getText());
    }

    // On n'affiche pas les données du deuxième joueur si jeu en solo
    private void setVisibilityHBox(){
        if (players.size() == 1){
            hBoxPlayer2.setVisible(false);
            namePlayer2.setVisible(false);
            textScorePlayer2.setVisible(false);
        }
    }
}


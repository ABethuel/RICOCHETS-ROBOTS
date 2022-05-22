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
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


import java.util.ArrayList;
import java.util.List;

public class GameController {
    public List<Player> players = (List<Player>) MainApplication.stage.getUserData();
    public List<Token> tokenList = new ArrayList<Token>();
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

    private Robot[][] robots = new Robot[16][16];
    private Token[][] tokens = new Token[16][16];
    private Wall[][] walls = new Wall[16][16];
    private List<Position> possibleMoves;

    public String urlImage = "file:assets/";
    private String colorTargetText;
    private String patternTargetText;

    private Game game = new Game(this, players);
    public int numberOfShotsPlayer1;
    public int numberOfShotsPlayer2;
    public  Player playerTurn;

    @FXML
    public void initialize() {

        gridPane.setDisable(true);
        setVisibilityHBox();
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
                    pane.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> game.onRobotClick(finalJ, finalI, this.robots));
                }
            }
        }

        // On ajoute les 16 tokens sur le plateau
       /* addTokenToGrid(ColorRobot.RED, Pattern.PLANET, 5, 2);
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
        addTokenToGrid(ColorRobot.GREEN, Pattern.SUN, 0, 0);
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
        game.defineTarget(robots);

        // On ajoute les robots
        addRobotToBoard(ColorRobot.RED);
        addRobotToBoard(ColorRobot.GREEN);
        addRobotToBoard(ColorRobot.BLUE);
        addRobotToBoard(ColorRobot.YELLOW);

        setCenterTargetImages();
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
        tokenList.add(token);
    }

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

    public void addWallToBoard(Orientation orientation, int x, int y){
        Wall wall = new Wall(x,y,orientation );
        this.walls[wall.getLig()][wall.getCol()] = wall;
        setWall(wall.getLig(), wall.getCol(), wall);
    }

    // On définit le robot
    public void setWall(int x, int y, Wall wall) {
        ImageView imageWall = (ImageView) board[x][y].getChildren().get(3);
        imageWall.setImage(new Image(urlImage +"Wall/" + wall.getImageSignature() + ".png", 35, 35, true, true));
    }

    public void validateShotsOnClick(ActionEvent actionEvent) {
        gridPane.setDisable(false);
        game.getNumberOfShotsExpected(numberOfShotsPlayer1, numberOfShotsPlayer2);
        System.out.println("Coups attendus : " + game.getNumberOfShotsExpected(numberOfShotsPlayer1, numberOfShotsPlayer2));

        textFieldPlayer2.setDisable(true);
        textFieldPlayer1.setDisable(true);
        validateShotsButton.setDisable(true);
    }

    @FXML
    public void onSetNumber() {
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

    private void setVisibilityHBox(){
        if (players.size() == 1){
            hBoxPlayer2.setVisible(false);
            namePlayer2.setVisible(false);
            textScorePlayer2.setVisible(false);
        }
    }
}


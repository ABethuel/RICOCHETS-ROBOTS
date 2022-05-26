package com.ricochetrobots.entities;

import com.ricochetrobots.components.ColorRobot;
import com.ricochetrobots.components.Position;
import com.ricochetrobots.systems.GameController;
import com.ricochetrobots.systems.MainApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class Game {

    private final Robot[][] board = new Robot[16][16];
    private List<Position> possibleMoves;
    private final GameController gameController;
    private Position selectedPiece;
    private List<Player> players;
    private int numberOfShotsExpected;
    private int scoreToReach;

    private String colorGame;
    private String patternGame;
    private Token targetToken;

    private int numberOfMoves = 0;
    private boolean isGameWon = false;

    private Player playerTurn;

    public Game(GameController gameController, List<Player> players, int scoreToReach) {
        this.gameController = gameController;
        this.players = players;
        this.scoreToReach = scoreToReach;
        setColorGame();
        setPatternGame();
    }

    public Robot[][] getBoard() {
        return board;
    }

    public boolean isInBoard(Position position) {
        return position.getX() >= 0 && position.getX() < 8 && position.getY() >= 0 && position.getY() < 8;
    }

    public boolean isInBoard(int x, int y) {
        return isInBoard(new Position(x, y));
    }

    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    public void setNumberOfMoves(int numberOfMoves) {
        this.numberOfMoves = numberOfMoves;
    }

    public boolean isGameWon() {
        return isGameWon;
    }

    public void setGameWon(boolean gameWon) {
        isGameWon = gameWon;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getNumberOfShotsExpected(int n1, int n2) {
        if (gameController.textFieldPlayer2.getText().equals("0") && !gameController.textFieldPlayer1.getText().equals("0")){
            setNumberOfShotsExpected(n1);
            setPlayerTurn(players.get(0));
        }else if (!gameController.textFieldPlayer2.getText().equals("0") && gameController.textFieldPlayer1.getText().equals("0")){
            setNumberOfShotsExpected(n2);
            setPlayerTurn(players.get(1));
        }else if (!gameController.textFieldPlayer2.getText().equals("0") && !gameController.textFieldPlayer1.getText().equals("0")){
            if (n1 < n2 && n1 != 0){
                setNumberOfShotsExpected(n1);
                setPlayerTurn(players.get(0));
            }else if (n2 < n1 && n2 != 0){
                setNumberOfShotsExpected(n2);
                setPlayerTurn(players.get(1));
            }
        }
        gameController.maxNumberOfShotsLabel.setVisible(true);
        gameController.maxNumberOfShotsLabel.setText("Coups maximums pour atteindre la cible : " + numberOfShotsExpected);
        return numberOfShotsExpected;

    }

    public void setNumberOfShotsExpected(int numberOfShotsExpected) {
        this.numberOfShotsExpected = numberOfShotsExpected;
    }

    public Player getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(Player playerTurn) {
        this.playerTurn = playerTurn;
    }

    public int getScoreToReach() {
        return scoreToReach;
    }

    // Quand on clique sur le plateau de jeu. On vérifier qu'il y a bien un robot ici.
    public void onRobotClick(int x, int y, Robot[][] robots) throws IOException {
        Position containedPosition = new Position(x, y);

        if (robots[y][x] != null){
            Robot robot = robots[y][x];
            possibleMoves = robot.getPossibleMoves(this, robots); // On récupère les mouvements potentiels d'un robot quand on clique dessus
            selectedPiece = new Position(x, y);
            update(robots);
        }else if (selectedPiece != null){
            for (Position p : possibleMoves){
                if (p.getX() == containedPosition.getY() && p.getY() == containedPosition.getX()){  // Condtitions pour valider le déplacement d'une pièce
                    setNumberOfMoves(getNumberOfMoves() + 1);  // On incrémente le nombre de coups
                    movePiece(selectedPiece, new Position(x, y), robots);
                    selectedPiece = null;
                    gameController.numberOfShotsPlayedLabel.setText("Nombre de coups joués : " + getNumberOfMoves());
                }
            }
        }
    }

    // On met à jour l'écran
   private void update(Robot[][] robots) throws IOException {
        boolean n = true;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (robots[i][j] != null){
                    if (numberOfMoves > getNumberOfShotsExpected(gameController.numberOfShotsPlayer1, gameController.numberOfShotsPlayer2) && n){
                        updateGridAfterLoss(robots);
                        n = false;
                        break;
                    }else{
                        gameController.setRobot(i, j, robots[i][j]);
                    }
                }
               else {
                   gameController.clearPiece(i, j, robots[i][j]);
               }
            }
        }
        if (possibleMoves != null) {
            gameController.clearPossibleMoves();
            for (Position p : possibleMoves) {
                gameController.setPossibleMove(p);
            }
        }

        isGameWon(robots);
        gameController.updateScreen();
   }

   public boolean hasObstacleAt(int x, int y, Robot[][] robots) {
        if (isInBoard(x, y)){
            System.out.println("Obstacle x = " + x + "  y = " + y);
            return robots[y][x] != null;
        }
        else return false;
   }

   private void movePiece(Position from, Position to, Robot[][] robots ) throws IOException {
        robots[to.getY()][to.getX()] = robots[from.getY()][from.getX()];
        robots[from.getY()][from.getX()] = null;
        robots[to.getY()][to.getX()].moved();
        robots[to.getY()][to.getX()].setPosition(to.getY(), to.getX(), getNumberOfMoves(), gameController.getTokens());
        update(robots);
        gameController.clearPossibleMoves();
   }

   // On définit au hasard la couleur du jeton cible
   public void randomColorGame(){
        Random random = new Random();
        int color = random.nextInt(4);
        switch(color){
            case 0 -> colorGame = "R";
            case 1 -> colorGame = "G";
            case 2 -> colorGame = "B";
            case 3 -> colorGame = "Y";
        }
   }

   // On définit au hasard le motif du jeton cible
   public void randomPatternGame(){
        Random random = new Random();
        int pattern = random.nextInt(4);
        switch(pattern){
            case 0 -> patternGame = "M";
            case 1 -> patternGame = "P";
            case 2 -> patternGame = "ST";
            case 3 -> patternGame = "SU";
        }
   }

   public String getColorGame() {
        return colorGame;
    }

   public void setColorGame() {
        randomColorGame();
   }

   public String getPatternGame() {
        return patternGame;
    }

   public void setPatternGame() {
        randomPatternGame();
   }

   // On récupère une chaine de caractère du jeton cible
   public String getSignatureTargetToken() {
       String target = getPatternGame();
       String color = getColorGame();
       return color + "_" + target;
   }

   // On définit le jeton cible
   public void defineTarget(Robot[][] robots){

        Random random = new Random();
        if (gameController.tokenList.size() > 0) { // Si il y a des tokens dans la liste
            int number = random.nextInt(gameController.tokenList.size()); // On définit au hasard un jeton dans la liste
            Token randomToken = gameController.tokenList.get(number);
            if (robots[randomToken.getLig()][randomToken.getCol()] == null){    // Si le jeton tiré ne se trouve pas sous un robot
                this.targetToken = randomToken;
                targetToken.setTarget(true);
                System.out.println("On définit la cible");
                System.out.println("Cible : " + targetToken.getName());
            }else{
                defineTarget(robots);
            }
        }
        /*for (int i = 0; i<16; i++){
           for (int j = 0; j<16; j++){
               if (gameController.getTokens()[i][j] != null) {
                   Token token = gameController.getTokens()[i][j];
                   if (gameController.tokenList.contains(token)) {
                       if (token.getName().equals(getSignatureTargetToken()) && gameController.tokenList.contains(token) && robots[i][j] == null && !token.getName().equals(previousToken)) {
                           this.targetToken = token;// Si les noms correspondent, on définit la cible
                           this.targetToken.setTarget(true);
                           System.out.println("On définit la cible");
                           System.out.println("Cible : " + targetToken.getName());
                       } else if (token.getName().equals(getSignatureTargetToken()) && !gameController.tokenList.contains(token)) {
                           randomPatternGame();
                           randomColorGame();
                       }
                   }
               }
           }
       }*/
   }

    public Token getTargetToken(){
        return targetToken;
    }

    // On vérifie si la partie est gagnée
    public void isGameWon(Robot[][] robots) throws IOException {
        for (int i = 0 ; i < 16; i++){
            for (int j = 0; j < 16; j++){
                Robot robot = robots[j][i];
                if (robot != null) {
                    // Si les conditions sont respectés, la partie est gagnée
                    if (robot.getCol() == targetToken.getCol() && robot.getLig() == targetToken.getLig() && robot.getColor() == targetToken.getColor()
                            && numberOfMoves <= getNumberOfShotsExpected(gameController.numberOfShotsPlayer1, gameController.numberOfShotsPlayer2)) {
                        System.out.println("Partie gagnée !");
                        setGameWon(true);
                        getPlayerTurn().setScore(getPlayerTurn().getScore() + 1);
                        endgameScreen();
                        setNumberOfMoves(0);  // On incrémente le nombre de coups
                        updateGridAfterWin(robots, targetToken, i, j);
                        gameController.validateShotsButton.setDisable(false);
                        gameController.textFieldPlayer2.setDisable(false);
                        gameController.textFieldPlayer1.setDisable(false);
                        gameController.textFieldPlayer2.setText("0");
                        gameController.textFieldPlayer1.setText("0");
                        gameController.gridPane.setDisable(true);
                        break;
                    }
                }
            }
        }
    }

    // On met à jour l'écran lorsqu'un joueur trouve un jeton
    public void updateGridAfterWin(Robot[][] robots, Token targetToken, int i, int j){
        System.out.println("---- update grid after a win ----");
        targetToken.setTarget(false);
        gameController.clearToken(j,i, targetToken); // On enlève le jeton trouvé de la grille
        gameController.tokenList.remove(targetToken);
        randomPatternGame();
        randomColorGame();
        defineTarget(robots);
        gameController.setCenterTargetImages();
        setGameWon(false);
        gameController.maxNumberOfShotsLabel.setVisible(false);
    }

    public void updateGridAfterLoss(Robot[][] robots){
        for (int k = 0; k < 16; k++) {
            for (int m = 0; m < 16; m++) {
                if (robots[m][k] != null) {
                    gameController.clearPiece(m, k, robots[m][k]);
                    robots[m][k] = null;
                }
            }
        }
        gameController.addRobotToBoard(ColorRobot.RED);
        gameController.addRobotToBoard(ColorRobot.GREEN);
        gameController.addRobotToBoard(ColorRobot.BLUE);
        gameController.addRobotToBoard(ColorRobot.YELLOW);

        gameController.validateShotsButton.setDisable(false);
        gameController.textFieldPlayer2.setDisable(false);
        gameController.textFieldPlayer1.setDisable(false);
        gameController.textFieldPlayer2.setText("0");
        gameController.textFieldPlayer1.setText("0");
        gameController.gridPane.setDisable(true);
    }

    public void endgameScreen() throws IOException {
        if (players.size() == 1){
            if (players.get(0).getScore() == getScoreToReach()){
                MainApplication.stage.setUserData(players);
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("end-game-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 850, 600);
                MainApplication.stage.setScene(scene);
                MainApplication.stage.show();
            }
        }else{
            if (players.get(0).getScore() + players.get(1).getScore() == getScoreToReach()){
                MainApplication.stage.setUserData(players);
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("end-game-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 850, 600);
                MainApplication.stage.setScene(scene);
                MainApplication.stage.show();
            }
        }
    }
}

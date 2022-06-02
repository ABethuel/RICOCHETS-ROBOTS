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
import java.util.Timer;

public class Game {

    private final Robot[][] board = new Robot[16][16];
    private List<Position> possibleMoves;
    private Position selectedPiece;
    private List<Player> players;
    private int numberOfShotsExpected;
    private int scoreToReach;
    private Timer chrono = new Timer();
    private boolean timerOn = true;

    private Token targetToken;

    private int numberOfMoves = 0;
    private boolean isGameWon = false;

    private Player playerTurn;

    public Game( List<Player> players, int scoreToReach) {
        this.players = players;
        this.scoreToReach = scoreToReach;
    }

    public Robot[][] getBoard() {
        return board;
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
        if (isGameWon()){
            getPlayerTurn().setScore(getPlayerTurn().getScore() + 1);
            setNumberOfMoves(0);  // On rénitialise le nombre de coups
            setTimerOn(true);
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getNumberOfShotsExpected(int n1, int n2, GameController gameController) {
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
            }else if (n2 == n1){
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

    public Timer getChrono() {
        return chrono;
    }

    public boolean isTimerOn() {
        return timerOn;
    }

    public void setTimerOn(boolean timerOn) {
        this.timerOn = timerOn;
    }

    // Quand on clique sur le plateau de jeu. On vérifier qu'il y a bien un robot ici.
    public void onRobotClick(int x, int y, Robot[][] robots, Wall[][] walls, GameController gameController) throws IOException {
        Position containedPosition = new Position(x, y);

        if (robots[y][x] != null){
            Robot robot = robots[y][x];
            possibleMoves = robot.getPossibleMoves(robots, walls); // On récupère les mouvements potentiels d'un robot quand on clique dessus
            selectedPiece = new Position(x, y);
            update(robots, gameController);
        }else if (selectedPiece != null){
            for (Position p : possibleMoves){
                if (p.getX() == containedPosition.getY() && p.getY() == containedPosition.getX()){  // Condtitions pour valider le déplacement d'une pièce
                    setNumberOfMoves(getNumberOfMoves() + 1);  // On incrémente le nombre de coups
                    movePiece(selectedPiece, new Position(x, y), robots, gameController);
                    selectedPiece = null;
                    gameController.numberOfShotsPlayedLabel.setText("Nombre de coups joués : " + getNumberOfMoves());
                }
            }
        }
    }

    // On met à jour l'écran
   private void update(Robot[][] robots, GameController gameController) throws IOException {
        boolean n = true;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (robots[i][j] != null){
                    if (numberOfMoves > getNumberOfShotsExpected(gameController.numberOfShotsPlayer1, gameController.numberOfShotsPlayer2, gameController) && n){
                        updateGridAfterLoss(robots, gameController);
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

        isGameWon(robots, gameController);
        gameController.updateScreen();
   }

   private void movePiece(Position from, Position to, Robot[][] robots, GameController gameController ) throws IOException {
        robots[to.getY()][to.getX()] = robots[from.getY()][from.getX()];
        robots[from.getY()][from.getX()] = null;
        robots[to.getY()][to.getX()].moved();
        robots[to.getY()][to.getX()].setPosition(to.getY(), to.getX(), getNumberOfMoves(), gameController.getTokens());
        update(robots, gameController);
        gameController.clearPossibleMoves();
   }

   // On définit le jeton cible
   public void defineTarget(Robot[][] robots, GameController gameController){

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
                defineTarget(robots, gameController);
            }
        }
    }

    public Token getTargetToken(){
        return targetToken;
    }

    // On vérifie si la partie est gagnée
    public void isGameWon(Robot[][] robots, GameController gameController) throws IOException {
        for (int i = 0 ; i < 16; i++){
            for (int j = 0; j < 16; j++){
                Robot robot = robots[j][i];
                if (robot != null) {
                    // Si les conditions sont respectés, la partie est gagnée
                    if (robot.getCol() == targetToken.getCol() && robot.getLig() == targetToken.getLig() && robot.getColor() == targetToken.getColor()
                            && numberOfMoves <= getNumberOfShotsExpected(gameController.numberOfShotsPlayer1, gameController.numberOfShotsPlayer2, gameController)) {
                        System.out.println("Partie gagnée !");
                        setGameWon(true);
                        endgameScreen();
                        updateGridAfterWin(robots, targetToken, i, j, gameController);
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
    public void updateGridAfterWin(Robot[][] robots, Token targetToken, int i, int j, GameController gameController){
        System.out.println("---- update grid after a win ----");
        targetToken.setTarget(false);
        gameController.clearToken(j,i, targetToken); // On enlève le jeton trouvé de la grille
        gameController.tokenList.remove(targetToken);
        defineTarget(robots, gameController);
        gameController.setCenterTargetImages();
        setGameWon(false);
        gameController.maxNumberOfShotsLabel.setVisible(false);
        gameController.gridPane.setOpacity(0.8);
    }

    public void updateGridAfterLoss(Robot[][] robots, GameController gameController){
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
        setTimerOn(true);

        gameController.validateShotsButton.setDisable(false);
        gameController.textFieldPlayer2.setDisable(false);
        gameController.textFieldPlayer1.setDisable(false);
        gameController.textFieldPlayer2.setText("0");
        gameController.textFieldPlayer1.setText("0");
        gameController.gridPane.setDisable(true);
        gameController.gridPane.setOpacity(0.8);

    }

    public void endgameScreen() throws IOException {
        if (players.size() == 1){
            if (players.get(0).getScore() >= getScoreToReach()){
                MainApplication.stage.setUserData(players);
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("end-game-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 850, 600);
                MainApplication.stage.setScene(scene);
                MainApplication.stage.show();
            }
        }else{
            if (players.get(0).getScore() + players.get(1).getScore() >= getScoreToReach()){
                MainApplication.stage.setUserData(players);
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("end-game-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 850, 600);
                MainApplication.stage.setScene(scene);
                MainApplication.stage.show();
            }
        }
    }
}

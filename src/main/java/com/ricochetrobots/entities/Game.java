package com.ricochetrobots.entities;

import com.ricochetrobots.components.ColorRobot;
import com.ricochetrobots.components.Position;
import com.ricochetrobots.systems.GameController;
import javafx.geometry.Pos;
import javafx.scene.image.Image;

import java.util.List;
import java.util.Random;

public class Game {

    private final Robot[][] board = new Robot[16][16];
    private List<Position> possibleMoves;
    private final GameController gameController;
    private Position selectedPiece;
    private List<Player> players;
    private int numberOfShotsExpected;

    private String colorGame;
    private String patternGame;
    private Token targetToken;
    private String previousToken;

    private int numberOfMoves = 0;
    private boolean isGameWon = false;

    private Player playerTurn;

    public Game(GameController gameController, List<Player> players) {
        this.gameController = gameController;
        this.players = players;
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
        /*if (n1 < n2 && n1 != 0) numberOfShotsExpected = n1;  // Le nombre de coup a joué est le nombre le plus bas
        else if (n2 < n1 && n2!= 0) numberOfShotsExpected = n2;
        return numberOfShotsExpected;*/

        if (gameController.textFieldPlayer2.getText().equals("0") && !gameController.textFieldPlayer1.getText().equals("0")){
            numberOfShotsExpected = n1;
            setPlayerTurn(players.get(0));
        }else if (!gameController.textFieldPlayer2.getText().equals("0") && gameController.textFieldPlayer1.getText().equals("0")){
            numberOfShotsExpected = n2;
            setPlayerTurn(players.get(1));
        }else if (!gameController.textFieldPlayer2.getText().equals("0") && !gameController.textFieldPlayer1.getText().equals("0")){
            if (n1 < n2 && n1 != 0){
                numberOfShotsExpected = n1;
                setPlayerTurn(players.get(0));
            }else if (n2 < n1 && n2 != 0){
                numberOfShotsExpected = n2;
                setPlayerTurn(players.get(1));
            }
        }
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

    // Quand on clique sur le plateau de jeu. On vérifier qu'il y a bien un robot ici.
    public void onRobotClick(int x, int y, Robot[][] robots){
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
                }
            }
        }
    }

    // On met à jour l'écran
   private void update(Robot[][] robots) {
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

   private void movePiece(Position from, Position to, Robot[][] robots ) {
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
                   if (token.getName().equals(getSignatureTargetToken()) && gameController.tokenList.contains(token) && robots[i][j] == null && !token.getName().equals(previousToken)) {
                       this.targetToken = token;// Si les noms correspondent, on définit la cible
                       this.targetToken.setTarget(true);
                       System.out.println("On définit la cible");
                       System.out.println("Cible : " + targetToken.getName());
                       break;
                   }
                   else if (token.getName().equals(getSignatureTargetToken()) && !gameController.tokenList.contains(token)){
                       randomPatternGame();
                       randomColorGame();
                   }
               }
           }
       }*/
   }

    public Token getTargetToken(){
        return targetToken;
    }

    // On vérifie si la partie est gagnée
    public void isGameWon(Robot[][] robots) {
        for (int i = 0 ; i < 16; i++){
            for (int j = 0; j < 16; j++){
                Robot robot = robots[j][i];
                if (robot != null) {
                    // Si les conditions sont respectés, la partie est gagnée
                    if (robot.getCol() == targetToken.getCol() && robot.getLig() == targetToken.getLig() && robot.getColor() == targetToken.getColor()
                            && numberOfMoves <= getNumberOfShotsExpected(gameController.numberOfShotsPlayer1, gameController.numberOfShotsPlayer2)) {
                        System.out.println("Partie gagnée !");
                        previousToken = targetToken.getName();
                        setGameWon(true);
                        getPlayerTurn().setScore(getPlayerTurn().getScore() + 1);
                        setNumberOfMoves(0);  // On incrémente le nombre de coups
                        updateGridAfterWin(robots);
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

    public void updateGridAfterWin(Robot[][] robots){
        for (int i = 0; i<16; i++){
            for (int j = 0; j<16; j++){
                if (gameController.getTokens()[i][j] != null) {
                    Token token = gameController.getTokens()[i][j];
                    if (token.isTarget() && isGameWon() ) {
                        System.out.println("---- update grid after a win ----");
                        token.setTarget(false);
                        gameController.clearToken(i,j, token);
                        System.out.println("clear token : " + i + "  " + j);
                        gameController.tokenList.remove(token);
                        randomPatternGame();
                        randomColorGame();
                        defineTarget(robots);
                        System.out.println("Ancienne cible : " + previousToken);
                        gameController.setCenterTargetImages();
                        setGameWon(false);
                        break;
                    }
                }
            }
        }
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
}

package com.ricochetrobots.entities;

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

    private String colorGame;
    private String patternGame;
    private Token targetToken;

    private int numberOfMoves = 0;
    private boolean isGameWon = false;

    public Game(GameController gameController) {
        this.gameController = gameController;
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

    // Quand on clique sur le plateau de jeu. On vérifier qu'il y a bien un robot ici.
    public void onRobotClick(int x, int y, Robot[][] robots){
        System.out.println("Board x = " + y + "  y = " + x);
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
                    System.out.println("Nombre de coups : " + getNumberOfMoves());
                }
            }
        }
    }

    // On met à jour l'écran
   private void update(Robot[][] robots) {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (robots[i][j] != null){
                    gameController.setRobot(i, j, robots[i][j]);
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
        if (isGameWon){
            System.out.println("c'est gagné");
        }
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
        System.out.println(color);
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
        System.out.println(pattern);
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
   public void defineTarget(){
       for (int i = 0; i<16; i++){
           for (int j = 0; j<16; j++){
               if (gameController.getTokens()[i][j] != null) {
                   Token token = gameController.getTokens()[i][j];
                   if (token.getName().equals(getSignatureTargetToken())) {
                       targetToken = token;// Si les noms correspondent, on définit la cible
                       targetToken.setTarget(true);
                       break;
                   }
               }
           }
       }
       /*for (int i = 0; i<16; i++){
           for (int j = 0; j<16; j++){
               if (gameController.getTokens()[i][j] != null) {
                   Token token = gameController.getTokens()[i][j];
                   if (token.isTarget()) {          // Si les noms correspondent, on définit la cible
                       targetImage.setImage(new Image(urlImage + "tokens/" + token.getImageSignature() + ".png"));
                   }
               }
           }
       }*/
   }

    public Token getTargetToken(){
        System.out.println(targetToken.getName());
        System.out.println(targetToken.getColor().toString());
        System.out.println(targetToken.getPattern().toString());

        return targetToken;
    }

    public void isGameWon(Robot[][] robots) {
        for (int i = 0 ; i < 16; i++){
            for (int j = 0; j < 16; j++){
                Robot robot = robots[j][i];
                if (robot != null) {
                    if (robot.getCol() == targetToken.getCol() && robot.getLig() == targetToken.getLig()) {
                        System.out.println("Partie gagnée !");
                        setGameWon(true);
                        break;
                    }
                }
            }
        }
    }


}

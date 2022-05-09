package com.ricochetrobots.entities;

import com.ricochetrobots.components.Position;
import com.ricochetrobots.systems.GameController;

import java.util.List;

public class Game {

    private final Robot[][] board = new Robot[16][16];
    private List<Position> possibleMoves;
    private final GameController gameController;

    public Game(GameController gameController) {
        this.gameController = gameController;
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

    public boolean hasPieceAt(int x, int y) {
        if (isInBoard(x, y))
            return board[x][y] != null;
        else return false;
    }
    public boolean hasPieceAt(Position position) {
        return hasPieceAt(position.getX(), position.getY());
    }

    // Quand on clique sur le tableau de jeu
    public void onBoardClick(int x, int y) {
        if (board[x][y] != null){
            System.out.println("Y a un robot là fraté");
        }
        System.out.println("Board x = " + x + "  y = " + y);
    }

    // Quand on clique sur le plateau de jeu. On vérifier qu'il y a bien un robot ici.
    public void onRobotClick(int x, int y, Robot[][] robots){
        System.out.println("Board x = " + x + "  y = " + y);
        if (robots[y][x] != null){
            System.out.println("Il y a un robot ici");
            Robot robot = robots[y][x];
            possibleMoves = robot.getPossibleMoves();
            update(robots);
            for (Position p : possibleMoves) {
                System.out.println("possible moves  x : " + p.getY() + "  y : " + p.getX());
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
                gameController.setPossibleMove(p, hasPieceAt(p));
            }
        }
    }

    public boolean hasObstacleAt(int x, int y) {
        if (isInBoard(x, y)){
            return board[x][y] != null;
        }
        else return false;
    }

    public boolean hasObstacleAt(Position position) {
        return hasObstacleAt(position.getX(), position.getY());
    }

}

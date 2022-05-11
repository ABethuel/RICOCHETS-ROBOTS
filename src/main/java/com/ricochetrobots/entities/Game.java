package com.ricochetrobots.entities;

import com.ricochetrobots.components.Position;
import com.ricochetrobots.systems.GameController;
import javafx.geometry.Pos;

import java.util.List;

public class Game {

    private final Robot[][] board = new Robot[16][16];
    private List<Position> possibleMoves;
    private final GameController gameController;
    private Position selectedPiece;

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

    // Quand on clique sur le plateau de jeu. On vérifier qu'il y a bien un robot ici.
    public void onRobotClick(int x, int y, Robot[][] robots){
        System.out.println("Board x = " + y + "  y = " + x);
        Position containedPosition = new Position(x, y);
        System.out.println("Contained moves x = " + containedPosition.getX() + "  y = " + containedPosition.getY());

        if (robots[y][x] != null){
            Robot robot = robots[y][x];
            possibleMoves = robot.getPossibleMoves(this, robots);
            selectedPiece = new Position(x, y);
            update(robots);

        }else if (selectedPiece != null /*&& possibleMoves.contains(containedPosition)*/){
            for (Position p : possibleMoves){
                if (p.getX() == containedPosition.getY() && p.getY() == containedPosition.getX()){
                    movePiece(selectedPiece, new Position(x, y), robots);
                    selectedPiece = null;
                }
            }
        }

        /*if (hasFriendlyPieceAt(i, j)) {
            System.out.println("test");
            selectedPiece = new Position(i, j);
            possibleMoves = board[i][j].getPossibleMoves(new Position(i, j), this);
            update();
        } else if (selectedPiece != null && possibleMoves.contains(new Position(i, j))) {
            movePiece(selectedPiece, new Position(i, j));
            selectedPiece = null;
        } else if (!hasPieceAt(i, j))
            selectedPiece = null;*/
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
        update(robots);
        gameController.clearPossibleMoves();
    }
}

package com.ricochetrobots.entities;

import com.ricochetrobots.components.Position;

public class Game {

    private final Robot[][] board = new Robot[16][16];

    public boolean hasObstacleAt(int x, int y) {
        if (isInBoard(x, y)){
            return board[x][y] != null;
        }
        else return false;
    }

    public boolean hasObstacleAt(Position position) {
        return hasObstacleAt(position.getX(), position.getY());
    }

    public boolean isInBoard(Position position) {
        return position.getX() >= 0 && position.getX() < 8 && position.getY() >= 0 && position.getY() < 8;
    }

    public boolean isInBoard(int x, int y) {
        return isInBoard(new Position(x, y));
    }



}

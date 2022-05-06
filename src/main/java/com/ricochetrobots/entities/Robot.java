package com.ricochetrobots.entities;

import com.ricochetrobots.components.ColorRobot;
import com.ricochetrobots.components.Position;

import java.util.ArrayList;
import java.util.List;

public class Robot {

    protected ColorRobot color;
    protected boolean hasMoved;

    public Robot(ColorRobot color) {
        this.color = color;
        this.hasMoved = false;
    }

    public String getImageSignature() {
        String className = getClass().getSimpleName().toLowerCase();
        return color.toString() + "_" + className;
    }

    public void moved() {
        this.hasMoved = true;
    }
    public boolean hasMoved() {
        return hasMoved;
    }

    public ColorRobot getColor() {
        return color;
    }

    // On définit les zones où peuvent se déplacer les robots
    //@Override
    public List<Position> getPossibleMoves(Position position, Game game) {
        List<Position> moves = new ArrayList<>();
        int x = position.getX();
        int y = position.getY();
        for (int i = x + 1; i < 16; i++) {
            moves.add(new Position(i, y));
            /*if (!game.hasObstacleAt(i, y)){
                moves.add(new Position(i, y));
            }else{
                break;
            }*/
        }

        for (int i = x - 1; i >= 0; i--) {
            moves.add(new Position(i, y));
        }

        for (int j = y + 1; j < 16; j++) {
            moves.add(new Position(j, y));
        }

        for (int j = y - 1; j >= 0; j--) {
            moves.add(new Position(j, y));
        }
        return moves;
    }
}

package com.ricochetrobots.entities;

import com.ricochetrobots.components.ColorRobot;
import com.ricochetrobots.components.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Robot {

    protected ColorRobot color;
    protected boolean hasMoved;
    private int col;
    private int lig;

    public Robot(ColorRobot color) {
        this.color = color;
        this.hasMoved = false;
        Random random = new Random();
        setPosition( random.nextInt(16), random.nextInt(16) );
    }

    // On récupère le nom de l'image correspondant au robot
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

    public int getCol() {
        return col;
    }

    public int getLig() {
        return lig;
    }

    public ColorRobot getColor() {
        return color;
    }

    // On définit la position du robot
    public void setPosition(int lig, int col) {
        // Si les positions aléatoires donnent le robot au centre on refait le tirage
        while (( lig == 7 && col == 7 ) || (lig == 7 && col == 8) || (lig == 8 && col == 7) || (lig == 8 && col == 8)){
            Random random = new Random();
            lig = random.nextInt(16);
            col = random.nextInt(16);
        }
        this.lig = lig;
        this.col = col;
    }

    // On définit les zones où peuvent se déplacer les robots
    public List<Position> getPossibleMoves() {
        List<Position> moves = new ArrayList<>();
        int x = getLig();
        int y = getCol();
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
            moves.add(new Position(x, j));
        }

        for (int j = y - 1; j >= 0; j--) {
            moves.add(new Position(x, j));
        }
        return moves;
    }
}

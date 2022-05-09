package com.ricochetrobots.entities;

import com.ricochetrobots.components.ColorRobot;
import com.ricochetrobots.components.Position;

import java.util.ArrayList;
import java.util.Arrays;
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
    public List<Position> getPossibleMoves(Game game, Robot[][] robots) {
        List<Position> moves = new ArrayList<>();
        int x = getLig();
        int y = getCol();

        for (int i = 0 ; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if (robots[i][j] != null){
                    System.out.println("Obstacle x = " + i + "  y = " + j);
                }
            }
        }

        for (int i = x + 1; i < 16; i++) {

            if (robots[i][y] == null){
                if ((y == 7 || y == 8) && (x <= 6)){
                    moves.add(new Position(6, y));
                }else{
                    moves.add(new Position(15, y));
                }
                System.out.println("no obstacle");
            }else{
                System.out.println("même ligne qu'un robot mon reuf");
                moves.add(new Position(i - 1 , y));
                Position position = new Position(15, y);
                if (moves.contains(position)){
                    moves.remove(position);
                }
                break;
            }

        }
        for (int i = x - 1; i >= 0; i--) {
            if ((y == 7 || y == 8) && (x >= 9 )){
                moves.add(new Position(9, y));
            }else{
                moves.add(new Position(0, y));
            }
        }

        for (int j = y + 1; j < 16; j++) {
            if ((x == 7 || x == 8) && (y <= 6)){
                moves.add(new Position(x, 6));
            }else{
                moves.add(new Position(x, 15));
            }
        }

        for (int j = y - 1; j >= 0; j--) {
            if ((x == 7 || x == 8) && (y >= 9)){
                moves.add(new Position(x, 9));
            }else{
                moves.add(new Position(x, 0));
            }
        }
        return moves;
    }
}

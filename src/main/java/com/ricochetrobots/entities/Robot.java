package com.ricochetrobots.entities;

import com.ricochetrobots.components.ColorRobot;
import com.ricochetrobots.components.Orientation;
import com.ricochetrobots.components.Position;
import com.ricochetrobots.systems.GameController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Robot {

    protected ColorRobot color;
    protected boolean hasMoved;
    private int col;
    private int lig;

    public Robot(ColorRobot color, Token[][] tokens) {
        this.color = color;
        this.hasMoved = false;
        Random random = new Random();
        setPosition( random.nextInt(16), random.nextInt(16), 0, tokens);
    }

    // On récupère le nom de l'image correspondant au robot
    public String getImageSignature() {
        String className = getClass().getSimpleName().toLowerCase();
        return color.toString() + "_" + className;
    }

    // Si le robot a changé de position on définit que la variable hasMoved est vraie
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
    public void setPosition(int lig, int col, int numberOfMoves, Token[][] tokens) {
        // Si les positions aléatoires donnent le robot au centre on refait le tirage
        while (( lig == 7 && col == 7 ) || (lig == 7 && col == 8) || (lig == 8 && col == 7) || (lig == 8 && col == 8) || (numberOfMoves == 0 && tokens[lig][col] != null)){
            Random random = new Random();
            lig = random.nextInt(16);
            col = random.nextInt(16);
        }
        this.lig = lig;
        this.col = col;
    }

    // On définit les zones où peuvent se déplacer les robots
    public List<Position> getPossibleMoves(Robot[][] robots, Wall[][] walls) {
        List<Position> moves = new ArrayList<>();
        int x = getLig();
        int y = getCol();

        // On navigue à droite du robot
        for (int i = x + 1; i < 16; i++) {
            if (walls[x][y] != null && (walls[x][y].getOrientation() == Orientation.EAST)){
                break;
            }
            else if (robots[i][y] != null || (walls[i][y] != null && (walls[i][y].getOrientation() == Orientation.EAST || walls[i][y].getOrientation() == Orientation.WEST))){  // S'il y a un robot ou un mur sur la route du robot
                if (robots[i][y] == null && walls[i][y] != null && walls[i][y].getOrientation() == Orientation.EAST){ // Si le mur est à droite
                    moves.add(new Position(i, y));
                    break;
                }
                else if (robots[i][y] == null && walls[i][y]!= null && walls[i][y].getOrientation() == Orientation.WEST ){ // A gauche
                    moves.add(new Position(i - 1 , y));
                    break;
                }else{  // S'il n'y a pas de mur
                    moves.add(new Position(i - 1 , y));
                    break;
                }
            }else{
                if ((y == 7 || y == 8) && (x <= 6)){ // Si le robot est dans la trajectoire de l'axe du tableau
                    if (i == 6){
                        moves.add(new Position(6, y));
                    }
                }else{
                    if (i == 15){ // Si jamais on a rencontré aucun obstacle, le robot peut aller au bout du tableau
                        moves.add(new Position(15, y));
                    }
                }
            }
        }

        // On fait la même chose qu'au dessus mais à gauche du robot
        for (int i = x - 1; i >= 0; i--) {
            if (walls[x][y] != null && (walls[x][y].getOrientation() == Orientation.WEST)){
                break;
            }
            else if (robots[i][y] != null || (walls[i][y] != null && (walls[i][y].getOrientation() == Orientation.EAST || walls[i][y].getOrientation() == Orientation.WEST))){
                if (robots[i][y] == null && walls[i][y] != null && walls[i][y].getOrientation() == Orientation.WEST){
                    moves.add(new Position(i, y));
                    break;
                }
                else if (robots[i][y] == null && walls[i][y]!= null && walls[i][y].getOrientation() == Orientation.EAST){
                    moves.add(new Position(i + 1 , y));
                    break;
                }else{
                    moves.add(new Position(i + 1 , y));
                    break;
                }
            }else {
                if ((y == 7 || y == 8) && (x >= 9)) {
                    if (i == 9) {
                        moves.add(new Position(9, y));
                        break;
                    }
                } else {
                    if (i == 0) {
                        moves.add(new Position(0, y));
                        break;
                    }
                }
            }
        }

        // On fait la même chose qu'au dessus mais en bas du robot
        for (int j = y + 1; j < 16; j++) {
            if (walls[x][y] != null && (walls[x][y].getOrientation() == Orientation.SOUTH)){
                break;
            }
            else if (robots[x][j] != null || (walls[x][j] != null && (walls[x][j].getOrientation() == Orientation.NORTH || walls[x][j].getOrientation() == Orientation.SOUTH))){
                if (robots[x][j] == null && walls[x][j] != null && walls[x][j].getOrientation() == Orientation.SOUTH){
                    moves.add(new Position(x, j));
                    break;
                }
                else if (robots[x][j] == null && walls[x][j]!= null && walls[x][j].getOrientation() == Orientation.NORTH){
                    moves.add(new Position(x , j - 1));
                    break;
                }else{
                    moves.add(new Position(x , j-1));
                    break;
                }
            }else{
                if ((x == 7 || x == 8) && (y <= 6)){
                    if (j == 6) {
                        moves.add(new Position(x, 6));
                    }
                }else{
                    if (j == 15 ) {
                        moves.add(new Position(x, 15));
                    }
                }
            }
        }

        // On fait la même chose qu'au dessus mais en haut du robot
        for (int j = y-1; j >= 0; j--) {
            if (walls[x][y] != null && (walls[x][y].getOrientation() == Orientation.NORTH)){
                break;
            }
            else if (robots[x][j] != null || (walls[x][j] != null && (walls[x][j].getOrientation() == Orientation.NORTH || walls[x][j].getOrientation() == Orientation.SOUTH))){
                if (walls[x][y] != null && walls[x][y].getOrientation() == Orientation.NORTH){
                    break;
                }
                else if (robots[x][j] == null && walls[x][j] != null && walls[x][j].getOrientation() == Orientation.SOUTH){
                    moves.add(new Position(x, j+1));
                    break;
                }
                else if (robots[x][j] == null && walls[x][j]!= null && walls[x][j].getOrientation() == Orientation.NORTH ){
                    moves.add(new Position(x , j ));
                    break;
                }else{
                    moves.add(new Position(x , j + 1));
                    break;
                }
            }else {
                if ((x == 7 || x == 8) && (y >= 9)) {
                    if (j == 9){
                        moves.add(new Position(x, 9));
                    }
                } else {
                    if (j == 0){
                        moves.add(new Position(x, 0));
                    }
                }
            }
        }
        return moves;
    }
}

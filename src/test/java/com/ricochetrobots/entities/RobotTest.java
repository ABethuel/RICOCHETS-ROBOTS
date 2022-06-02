package com.ricochetrobots.entities;

import com.ricochetrobots.components.ColorRobot;
import com.ricochetrobots.components.Position;
import org.junit.jupiter.api.Test;

import java.util.List;

import static javax.swing.text.StyleConstants.Orientation;
import static org.junit.jupiter.api.Assertions.*;

public class RobotTest {

    @Test
    public void testImageSignature(){
        Token[][] tokens = new Token[16][16];
        Robot robot = new Robot(ColorRobot.RED, tokens);
        assertEquals(robot.getImageSignature(), "R_robot");
    }

    @Test
    public void testRobotNotOnCenterOfCircle(){
        Token[][] tokens = new Token[16][16];
        Robot robot = new Robot(ColorRobot.RED, tokens);
        robot.setPosition(7, 7, 0, tokens);
        assertFalse((robot.getLig() == 7 || robot.getLig() == 8) && (robot.getCol() == 7 || robot.getCol() == 8));
    }
    
    @Test
    public void testRobotsPossibleMovesWithoutObstacles(){
        Token[][] tokens = new Token[16][16];
        Robot robot = new Robot(ColorRobot.RED, tokens);
        Wall[][] walls = new Wall[16][16];
        Robot[][] robots = new Robot[16][16];

        int x = robot.getLig();
        int y = robot.getCol();

        robots[y][x] = robot;
        
        Position positionN = null;
        for (int i = x + 1; i < 16; i++) {
            if ((y == 7 || y == 8) && (x <= 6)){ // Si le robot est dans la trajectoire de l'axe du tableau
                if (i == 6){
                    positionN = new Position(6, y);
                    break;
                }
            }else{
                if (i == 15){ // Si jamais on a rencontré aucun obstacle, le robot peut aller au bout du tableau
                    positionN = new Position(15, y);
                    break;
                }
            }
        }

        boolean Test = false;
        List<Position> getPossibleMoves = robot.getPossibleMoves(robots, walls);
        for (Position getPossibleMove : getPossibleMoves) {
            if (getPossibleMove.getX() == positionN.getX() && getPossibleMove.getY() == positionN.getY()){
                Test = true;
                break;
            }
        }
        assertTrue(Test);
    }
}

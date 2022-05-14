package com.ricochetrobots.entities;

import com.ricochetrobots.components.ColorRobot;
import com.ricochetrobots.components.Pattern;

import java.util.Random;

public class Token {
    protected ColorRobot color;
    protected Pattern pattern;
    private int col;
    private int lig;
    private boolean isTarget;

    public Token(ColorRobot color, Pattern pattern, int col, int lig) {
        this.color = color;
        this.pattern = pattern;
        this.col = col;
        this.lig = lig;
        this.isTarget = false;
    }

    public ColorRobot getColor() {
        return color;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public int getCol() {
        return col;
    }

    public int getLig() {
        return lig;
    }

    public String getImageSignature() {
        String className = getClass().getSimpleName().toLowerCase();
        return getColor().toString() + "_" + getPattern().toString() + "_" +  className;
    }

    public String getName(){
        return getColor().toString() + "_" + getPattern().toString();
    }

    public void setPosition(int lig, int col) {
        this.lig = lig;
        this.col = col;
    }

    public boolean isTarget() {
        return isTarget;
    }

    public void setTarget(boolean target) {
        isTarget = target;
    }
}

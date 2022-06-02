package com.ricochetrobots.entities;

import com.ricochetrobots.components.ColorRobot;
import com.ricochetrobots.components.Pattern;

public class Token {
    protected ColorRobot color;
    protected Pattern pattern;
    private final int col;
    private final int lig;
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

    // Nom de l'image
    public String getImageSignature() {
        String className = getClass().getSimpleName().toLowerCase();
        return getColor().toString() + "_" + getPattern().toString() + "_" +  className;
    }

    public String getName(){
        return getColor().toString() + "_" + getPattern().toString();
    }

    public boolean isTarget() {
        return isTarget;
    }

    public void setTarget(boolean target) {
        isTarget = target;
    }
}

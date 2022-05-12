package com.ricochetrobots.entities;

import com.ricochetrobots.components.ColorRobot;

import java.util.Random;

public abstract class Token {
    protected ColorRobot color;
    private int col;
    private int lig;

    public ColorRobot getColor() {
        return color;
    }

    public int getCol() {
        return col;
    }

    public int getLig() {
        return lig;
    }

    public String getImageSignature() {
        String className = getClass().getSimpleName().toLowerCase();
        return color.toString() + "_" + className;
    }

    public void setPosition(int lig, int col) {
        this.lig = lig;
        this.col = col;
    }
}

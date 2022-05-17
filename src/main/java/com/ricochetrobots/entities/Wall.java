package com.ricochetrobots.entities;

import com.ricochetrobots.components.Orientation;

public class Wall {;
    private int col;
    private int lig;
    private Orientation orientation;
    public Wall(int col, int lig, Orientation a){
        this.orientation=a;
        this.col=col;
        this.lig=lig;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public void setWallPosition (int lig, int col){
        this.lig = lig;
        this.col = col;
    }

    public int getCol() {
        return col;
    }

    public int getLig() {
        return lig;
    }

    public String getImageSignature() {
        String className = getClass().getSimpleName().toLowerCase();
        return getOrientation().toString() + "_" +  className;
    }


}


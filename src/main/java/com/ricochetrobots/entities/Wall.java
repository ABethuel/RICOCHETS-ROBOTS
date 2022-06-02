package com.ricochetrobots.entities;

import com.ricochetrobots.components.Orientation;

public class Wall {;
    private final int col;
    private final int lig;
    private final Orientation orientation;
    public Wall(int col, int lig, Orientation a){
        this.orientation=a;
        this.col=col;
        this.lig=lig;
    }


    public Orientation getOrientation() {
        return orientation;
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


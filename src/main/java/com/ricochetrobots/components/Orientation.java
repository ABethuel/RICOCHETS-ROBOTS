package com.ricochetrobots.components;

public enum Orientation {
    NORTH,
    SOUTH,
    EAST,
    WEST;

    // On attribue une lettre pour chaque orientation
    public String toString() {
        String orientation = "";
        if (this == NORTH){
            orientation = "T";
        }
        else if (this == SOUTH){
            orientation = "B";
        }
        else if (this == EAST){
            orientation = "R";
        }
        else if (this == WEST){
            orientation = "L";
        }

        return orientation;
    }
}

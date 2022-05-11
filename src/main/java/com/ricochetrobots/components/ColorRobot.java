package com.ricochetrobots.components;

import javafx.scene.paint.Color;

public enum ColorRobot {
    RED,
    GREEN,
    BLUE,
    YELLOW;

    // On attribue une lettre en fonction de la couleur
    @Override
    public String toString() {
        String color = "";
        if (this == RED){
            color = "R";
        }
        else if (this == GREEN){
            color = "G";
        }
        else if (this == BLUE){
            color = "B";
        }
        else if (this == YELLOW){
            color = "Y";
        }

        return color;
    }

    /*public Color toColor() {

        if (this == RED){

        }
        return this == BLACK ? new Color(.2, .2, .2, 1) : new Color(.8, .8, .8, 1);
    }*/
}

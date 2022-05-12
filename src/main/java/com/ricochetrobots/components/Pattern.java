package com.ricochetrobots.components;

public enum Pattern {
    MOON,
    PLANET,
    STAR,
    SUN;

    @Override
    public String toString() {
        String pattern = "";
        if (this == MOON){
            pattern = "M";
        }
        else if (this == PLANET){
            pattern = "G";
        }
        else if (this == STAR){
            pattern = "B";
        }
        else if (this == SUN){
            pattern = "Y";
        }

        return pattern;
    }

}


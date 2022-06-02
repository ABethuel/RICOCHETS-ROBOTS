package com.ricochetrobots.components;

public enum Pattern {
    MOON,
    PLANET,
    STAR,
    SUN;

    // On attribue une lettre pour chaque motif
    @Override
    public String toString() {
        String pattern = "";
        if (this == MOON){
            pattern = "M";
        }
        else if (this == PLANET){
            pattern = "P";
        }
        else if (this == STAR){
            pattern = "ST";
        }
        else if (this == SUN){
            pattern = "SU";
        }

        return pattern;
    }

}


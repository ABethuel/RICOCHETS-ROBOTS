package com.ricochetrobots.components;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ComponentTest {
    @Test
    public void colorToString(){
        ColorRobot color = ColorRobot.RED;
        assertEquals(color.toString(), "R");
    }

    @Test
    public void patternToString(){
        Pattern pattern = Pattern.STAR;
        assertEquals(pattern.toString(), "ST");
    }

    @Test
    public void orientationToString(){
        Orientation orientation = Orientation.EAST;
        assertEquals(orientation.toString(), "R");
    }
}

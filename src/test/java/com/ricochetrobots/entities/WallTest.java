package com.ricochetrobots.entities;

import com.ricochetrobots.components.ColorRobot;
import com.ricochetrobots.components.Orientation;
import com.ricochetrobots.components.Pattern;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WallTest {

    @Test
    public void testWallImageSignature(){
        Wall wall = new Wall(1, 1, Orientation.EAST);
        assertEquals(wall.getImageSignature(), "R_wall");
    }

}

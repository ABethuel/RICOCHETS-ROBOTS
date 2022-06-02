package com.ricochetrobots.entities;

import com.ricochetrobots.components.ColorRobot;
import com.ricochetrobots.components.Pattern;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TokenTest {
    @Test
    public void testImageSignature(){
        Token token = new Token(ColorRobot.BLUE, Pattern.MOON, 1,1);
        assertEquals(token.getImageSignature(), "B_M_token");
    }
}

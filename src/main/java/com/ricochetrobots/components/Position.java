package com.ricochetrobots.components;

import java.util.Objects;

public class Position {

    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

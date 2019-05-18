package com.jessedewild.seekinglight.entities;

import com.jessedewild.seekinglight.lib.Entity;

public class Tile extends Entity {

    private int firstgid;
    private float x;
    private float y;

    public Tile(int firstgid) {
        this.firstgid = firstgid;
    }

    public int getFirstgid() {
        return firstgid;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setXandY(float x, float y) {
        this.x = x;
        this.y = y;
    }
}

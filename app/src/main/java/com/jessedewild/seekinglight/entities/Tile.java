package com.jessedewild.seekinglight.entities;

import com.jessedewild.seekinglight.lib.Entity;

public class Tile extends Entity {

    private int firstgid;
    private int x;
    private int y;

    public Tile(int firstgid) {
        this.firstgid = firstgid;
    }

    public int getFirstgid() {
        return firstgid;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setXandY(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

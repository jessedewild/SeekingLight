package com.jessedewild.seekinglight.entities;

import com.jessedewild.seekinglight.lib.Entity;

public class Obtainable extends Entity {

    private final int objectId;
    private float x;
    private float y;
    private float mapX;
    private float mapY;
    private final float size = 0.5f;

    public Obtainable(int objectId, float x, float y, float mapX, float mapY) {
        this.objectId = objectId;
        this.x = x;
        this.y = y;
        this.mapX = mapX;
        this.mapY = mapY;
    }

    public int getId() {
        return objectId;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getMapX() {
        return mapX;
    }

    public float getMapY() {
        return mapY;
    }

    public float getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "Obtainable{" +
                "objectId=" + objectId +
                ", x=" + x +
                ", y=" + y +
                ", mapX=" + mapX +
                ", mapY=" + mapY +
                '}';
    }
}

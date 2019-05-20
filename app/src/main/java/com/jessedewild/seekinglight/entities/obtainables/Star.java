package com.jessedewild.seekinglight.entities.obtainables;

import com.jessedewild.seekinglight.entities.Obtainable;

public class Star extends Obtainable {

    private int resourceId;

    public Star(int id, float x, float y, float mapX, float mapY) {
        super(id, x, y, mapX, mapY);
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

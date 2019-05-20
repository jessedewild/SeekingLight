package com.jessedewild.seekinglight.constructors;

import com.jessedewild.seekinglight.entities.Obtainable;

public class Layer {

    private int[] data;
    private int height;
    private String name;
    private int opacity;
    private String type;
    private boolean visible;
    private int width;
    private float x;
    private float y;
    private Obtainable[] objects;

    public Layer(int[] data, int height, String name, int opacity, String type, boolean visible, int width, float x, float y, Obtainable[] objects) {
        this.data = data;
        this.height = height;
        this.name = name;
        this.opacity = opacity;
        this.type = type;
        this.visible = visible;
        this.width = width;
        this.x = x;
        this.y = y;
        this.objects = objects;
    }

    public int[] getData() {
        return data;
    }

    public int getHeight() {
        return height;
    }

    public String getName() {
        return name;
    }

    public int getOpacity() {
        return opacity;
    }

    public String getType() {
        return type;
    }

    public boolean isVisible() {
        return visible;
    }

    public int getWidth() {
        return width;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Obtainable[] getObjects() {
        return objects;
    }
}

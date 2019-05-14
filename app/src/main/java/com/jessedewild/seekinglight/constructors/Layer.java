package com.jessedewild.seekinglight.constructors;

public class Layer {

    private int[] data;
    private int height;
    private int id;
    private String name;
    private int opacity;
    private String type;
    private boolean visible;
    private int width;
    private int x;
    private int y;

    public Layer(int[] data, int height, int id, String name, int opacity, String type, boolean visible, int width, int x, int y) {
        this.data = data;
        this.height = height;
        this.id = id;
        this.name = name;
        this.opacity = opacity;
        this.type = type;
        this.visible = visible;
        this.width = width;
        this.x = x;
        this.y = y;
    }

    public int[] getData() {
        return data;
    }

    public int getHeight() {
        return height;
    }

    public int getId() {
        return id;
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

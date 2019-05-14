package com.jessedewild.seekinglight.constructors;

public class Level {

    private int height;
    private boolean infinite;
    private Layer[] layers;
    private int nextlayerid;
    private int nextobjectid;
    private String orientation;
    private String renderorder;
    private String tiledversion;
    private String tileheight;
    private Tileset[] tilesets;
    private int tilewidth;
    private String type;
    private double version;
    private int width;

    public Level(int height, boolean infinite, Layer[] layers, int nextlayerid, int nextobjectid, String orientation, String renderorder, String tiledversion, String tileheight, Tileset[] tilesets, int tilewidth, String type, double version, int width) {
        this.height = height;
        this.infinite = infinite;
        this.layers = layers;
        this.nextlayerid = nextlayerid;
        this.nextobjectid = nextobjectid;
        this.orientation = orientation;
        this.renderorder = renderorder;
        this.tiledversion = tiledversion;
        this.tileheight = tileheight;
        this.tilesets = tilesets;
        this.tilewidth = tilewidth;
        this.type = type;
        this.version = version;
        this.width = width;
    }

    public int[] getData(int i) {
        return layers[i].getData();
    }

    public int getHeight() {
        return height;
    }

    public boolean isInfinite() {
        return infinite;
    }

    public Layer[] getLayers() {
        return layers;
    }

    public int getNextlayerid() {
        return nextlayerid;
    }

    public int getNextobjectid() {
        return nextobjectid;
    }

    public String getOrientation() {
        return orientation;
    }

    public String getRenderorder() {
        return renderorder;
    }

    public String getTiledversion() {
        return tiledversion;
    }

    public String getTileheight() {
        return tileheight;
    }

    public Tileset[] getTilesets() {
        return tilesets;
    }

    public int getTilewidth() {
        return tilewidth;
    }

    public String getType() {
        return type;
    }

    public double getVersion() {
        return version;
    }

    public int getWidth() {
        return width;
    }
}

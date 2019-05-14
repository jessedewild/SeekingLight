package com.jessedewild.seekinglight.constructors;

/**
 * Constructor class for the Tileset for the Tile
 */
public class Tileset {

    /**
     * @param firstgid 1 = floor, 2 = wall
     * @param source the floor and wall textures
     */
    private int firstgid;
    private String source;

    public Tileset(int firstgid, String source) {
        this.setFirstgid(firstgid);
        this.setSource(source);
    }

    public int getFirstgid() {
        return firstgid;
    }

    public void setFirstgid(int firstgid) {
        this.firstgid = firstgid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}

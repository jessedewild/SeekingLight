package com.jessedewild.seekinglight.game;

import android.graphics.Bitmap;

import com.jessedewild.seekinglight.lib.Entity;
import com.jessedewild.seekinglight.platformer.Game;

public class Map extends Entity {

    // Size of the level in tiles.
    static final int height = 30;
    static final int width = 30;

    // For each tile, a boolean is saved if it is a floor or a wall
    // 1 == floor
    // 2 == wall
    private int[][] tiles = new int[height][width];

    // When resources are first used, the decoded Bitmap is written to this array, as a cache.
    static private Bitmap[] spriteBitmaps;

    private Game game;

    public Map(Game game) {
        this.game = game;

    }

    private void generateMapTiles() {

    }
}

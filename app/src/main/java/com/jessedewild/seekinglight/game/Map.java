package com.jessedewild.seekinglight.game;

import android.graphics.Bitmap;

import com.google.gson.Gson;
import com.jessedewild.seekinglight.R;
import com.jessedewild.seekinglight.constructors.Level;
import com.jessedewild.seekinglight.lib.Entity;
import com.jessedewild.seekinglight.lib.GameView;

public class Map extends Entity {

    private Game game;
    private Level level;
    private String json;
    private int[][] tiles;

    // Size of the tile
    private float size;

    // Size of the level in tiles.
    public static int width = 41;
    public static int height = 41;

    // The list of resource ids to draw each tile type with.
    static private final int[] spriteResourceIds = {0, R.drawable.floor, R.drawable.wall};

    // When resources are first used, the decoded Bitmap is written to this array, as a cache.
    static private Bitmap[] spriteBitmaps;

    public Map(Game game, String json) {
        this.game = game;
        this.json = json;
        this.level = new Gson().fromJson(json, Level.class);

        generateMapTiles();

        width = level.getWidth();
        height = level.getHeight();

        if (spriteBitmaps == null) spriteBitmaps = new Bitmap[spriteResourceIds.length];
    }

    private void generateMapTiles() {
        size = game.getHeight() / 10;
        int[] data = level.getData(0);
        int i = 0;
        tiles = new int[level.getHeight()][level.getWidth()];
        for (int row = 0; row < level.getHeight(); row++) {
            for (int column = 0; column < level.getWidth(); column++) {
                tiles[row][column] = data[i];
                i++;
            }
        }
    }

    @Override
    public void draw(GameView gv) {
        // Calculate which tiles are visible at the current scroll position.
        float scrollX = game.scroller.x;
        float scrollY = game.scroller.y;

        // Draw any visible tiles.
        for (int row = 0; row < level.getHeight(); row++) {
            for (int column = 0; column < level.getWidth(); column++) {
                int tile = tiles[row][column];
                if (tile == 0) continue;
                if (spriteBitmaps[tile] == null) {
                    // Load/decode bitmaps before we first draw them.
                    spriteBitmaps[tile] = gv.getBitmapFromResource(spriteResourceIds[tile]);
                }
                gv.drawBitmap(spriteBitmaps[tile], (float) column * size - scrollX, (float) row * size - scrollY, size, size);
            }
        }
    }
}

package com.jessedewild.seekinglight.game;

import android.graphics.Bitmap;

import com.google.gson.Gson;
import com.jessedewild.seekinglight.R;
import com.jessedewild.seekinglight.constructors.Level;
import com.jessedewild.seekinglight.entities.Tile;
import com.jessedewild.seekinglight.lib.Entity;
import com.jessedewild.seekinglight.lib.GameView;

import java.util.ArrayList;
import java.util.List;

public class Map extends Entity {

    private Game game;
    private Level level;
    private String json;
    private List<Tile> tiles = new ArrayList<>();

    // Size of the tile
    private float size;

    // Scroll position
    private float scrollX;
    private float scrollY;

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

    public Level getLevel() {
        return level;
    }

    private void generateMapTiles() {
        size = game.getHeight() / 8;

        int[] data = level.getData(0);
        int i = 0;
        for (int row = 0; row < level.getHeight(); row++) {
            for (int column = 0; column < level.getWidth(); column++) {
                Tile tile = new Tile(data[i]);
                tile.setXandY(column, row);
                tiles.add(tile);
                i++;
            }
        }
    }

    public void setScroller(float x, float y) {
        this.scrollX = scrollX + x;
        this.scrollY = scrollY + y;
    }

    @Override
    public void draw(GameView gv) {
        if (game.isAutoScroll()) {
            this.scrollX = game.scroller.x;
            this.scrollY = game.scroller.y;
        }

        // Draw any visible tiles.
        for (int row = 0; row < level.getHeight(); row++) {
            for (int column = 0; column < level.getWidth(); column++) {
                int tile = getTile(column, row).getFirstgid();
                if (tile == 0) continue;
                if (spriteBitmaps[tile] == null) {
                    // Load/decode bitmaps before we first draw them.
                    spriteBitmaps[tile] = gv.getBitmapFromResource(spriteResourceIds[tile]);
                }
                gv.drawBitmap(spriteBitmaps[tile], (float) column * size - scrollX, (float) row * size - scrollY, size, size);
            }
        }
    }

    public Tile getTile(int x, int y) {
        for (Tile tile : tiles) {
            if (tile.getX() == x && tile.getY() == y) {
                return tile;
            }
        }
        return null;
    }

    public float getSize() {
        return size;
    }
}

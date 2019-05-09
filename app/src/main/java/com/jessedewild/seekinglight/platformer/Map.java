package com.jessedewild.seekinglight.platformer;

import android.graphics.Bitmap;

import com.jessedewild.seekinglight.lib.Entity;
import com.jessedewild.seekinglight.lib.GameView;
import com.jessedewild.seekinglight.R;

public class Map extends Entity {

    // Size of the level in tiles.
    static final int width = 50;
    static final int height = 10;

    // For each tile, an integer indexing `spriteResourceIds`. 0 means nothing is shown.
    int[][] tiles = new int[width][height];

    // The list of resource ids to draw each tile type with.
    static private final int[] spriteResourceIds = {0, R.drawable.tile_1, R.drawable.tile_2, R.drawable.tile_3, R.drawable.tile_4, R.drawable.tile_5, R.drawable.tile_6, R.drawable.tile_7, R.drawable.tile_8, R.drawable.tile_9, R.drawable.tile_10, R.drawable.tile_11, R.drawable.tile_12, R.drawable.tile_13, R.drawable.tile_14, R.drawable.tile_15};

    // When resources are first used, the decoded Bitmap is written to this array, as a cache.
    static private Bitmap[] spriteBitmaps;

    private Game game;

    Map(Game game) {
        this.game = game;
        generatePlatforms(0, 3, 0);
        generatePlatforms(9, -1, 4);
        if (spriteBitmaps == null) spriteBitmaps = new Bitmap[spriteResourceIds.length];
    }

    // Modify tiles to add either the solid ground islands or the platforms.
    // Commenting this code is left as an exercise for the reader. :-)
    private void generatePlatforms(int topTileBase, int lowerTileBase, int minHeight) {
        int platformHeight = minHeight;
        boolean platform = Math.random() > 0.5;

        for (int x = 0; x < width; x++) {
            int tile = 0;

            if (platform) {
                if (Math.random() < 0.2) {
                    platform = false;
                    tile = 3; // end
                } else {
                    tile = 2; // sustain
                }
            } else {
                if (Math.random() < 0.7) {
                    platform = true;
                    tile = 1; // begin
                }
                platformHeight = minHeight + (int) Math.floor(Math.random() * 3d);
            }

            if (tile > 0) {
                if (lowerTileBase >= 0) {
                    for (int y = 0; y < platformHeight; y++) {
                        tiles[x][y] = tile + lowerTileBase;
                    }
                }
                tiles[x][platformHeight] = tile + topTileBase;
            }
        }
    }

    @Override
    public void draw(GameView gv) {
        // Calculate which tiles are visible at the current scroll position.
        float scrollX = game.scroller.x;
        int startX = Math.max(0, (int) Math.floor(scrollX));
        int endX = Math.min(startX + 1 + (int) game.getWidth(), width);
        int endY = Math.min((int) Math.ceil(game.getHeight()), height);

        // Draw any visible tiles.
        for (int x = startX; x < endX; x++) {
            for (int y = 0; y < endY; y++) {
                int tile = tiles[x][y];
                if (tile == 0) continue;
                if (spriteBitmaps[tile] == null) {
                    // Load/decode bitmaps before we first draw them.
                    spriteBitmaps[tile] = gv.getBitmapFromResource(spriteResourceIds[tile]);
                }
                gv.drawBitmap(spriteBitmaps[tile], (float) x - scrollX, (float) game.getHeight() - y - 1, 1, 1);
            }
        }
    }
}

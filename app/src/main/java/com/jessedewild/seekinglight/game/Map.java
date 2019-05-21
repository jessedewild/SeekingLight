package com.jessedewild.seekinglight.game;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.gson.Gson;
import com.jessedewild.seekinglight.R;
import com.jessedewild.seekinglight.constructors.Level;
import com.jessedewild.seekinglight.entities.Obtainable;
import com.jessedewild.seekinglight.entities.Tile;
import com.jessedewild.seekinglight.entities.characters.Seeker;
import com.jessedewild.seekinglight.entities.obtainables.Coin;
import com.jessedewild.seekinglight.entities.obtainables.Star;
import com.jessedewild.seekinglight.lib.Entity;
import com.jessedewild.seekinglight.lib.GameView;

import java.util.ArrayList;
import java.util.List;

public class Map extends Entity {

    private Game game;
    private Level level;
    private String json;
    private List<Tile> tiles = new ArrayList<>();
    private List<Obtainable> obtainables = new ArrayList<>();

    // Size of the tile
    public float size;

    // Map position
    public float x;
    public float y;

    // Size of the level in tiles.
    public static int width = 41;
    public static int height = 41;
    public float tileSize;

    // The list of resource ids to draw each tile type with.
    static private final int[] spriteResourceIds = {0, R.drawable.floor, R.drawable.wall, R.drawable.coin, R.drawable.star};

    // When resources are first used, the decoded Bitmap is written to this array, as a cache.
    static private Bitmap[] spriteBitmaps;

    public Map(Game game, String json) {
        this.game = game;
        this.json = json;
        this.level = new Gson().fromJson(json, Level.class);

        generateMapTiles();
        generateObtainables();

        width = level.getWidth();
        height = level.getHeight();

        if (spriteBitmaps == null) spriteBitmaps = new Bitmap[spriteResourceIds.length];
    }

    private void generateMapTiles() {
        size = game.getHeight() / 8;
        Log.e("Map Size:", "" + size);

        int[] data = level.getData(0);
        int i = 0;
        for (float row = 0; row < level.getHeight(); row++) {
            for (float column = 0; column < level.getWidth(); column++) {
                Tile tile = new Tile(data[i]);
                tile.setXandY(column, row);
                tiles.add(tile);
                i++;
            }
        }
    }

    private void generateObtainables() {
        tileSize = (float) level.getTileheight();
        for (Obtainable obtainable : level.getLayers()[1].getObjects()) {
            int random = (int) (Math.random() * level.getLayers()[1].getObjects().length);
            if (random == level.getLayers()[1].getObjects().length) {
                Star star = new Star(obtainable.getId(), obtainable.getX(), obtainable.getY(), obtainable.getX() / tileSize, obtainable.getY() / tileSize);
                star.setResourceId(4);
                obtainables.add(star);
            } else {
                Coin coin = new Coin(obtainable.getId(), obtainable.getX(), obtainable.getY(), obtainable.getX() / tileSize, obtainable.getY() / tileSize);
                coin.setResourceId(3);
                obtainables.add(coin);
            }
        }
    }

    public List<Obtainable> getObtainables() {
        return obtainables;
    }

    public void setPos(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void move(float x, float y) {
        Seeker seeker = game.getEntity(Seeker.class);
        if (!seeker.collision()) {
            this.x = this.x + x;
            this.y = this.y + y;
        }
    }

    @Override
    public void draw(GameView gv) {
        if (game.isAutoScroll()) {
            this.x = game.scroller.x;
            this.y = game.scroller.y;
        }

        // Draw any visible tiles.
        for (float row = 0; row < level.getHeight(); row++) {
            for (float column = 0; column < level.getWidth(); column++) {
                int tile = getTile(column, row, false).getFirstgid();
                if (tile == 0) continue;
                if (spriteBitmaps[tile] == null) {
                    // Load/decode bitmaps before we first draw them.
                    spriteBitmaps[tile] = gv.getBitmapFromResource(spriteResourceIds[tile]);
                }
                gv.drawBitmap(spriteBitmaps[tile], column * size - x, row * size - y, size, size);
            }
        }

        int resourceId = 0;
        for (Obtainable obtainable : obtainables) {
            float obtainableX = obtainable.getMapX();
            float obtainableY = obtainable.getMapY();

            if (obtainable instanceof Coin) {
                resourceId = ((Coin) obtainable).getResourceId();
            } else if (obtainable instanceof Star) {
                resourceId = ((Star) obtainable).getResourceId();
            }
            if (spriteBitmaps[resourceId] == null) {
                // Load/decode bitmaps before we first draw them.
                spriteBitmaps[resourceId] = gv.getBitmapFromResource(spriteResourceIds[resourceId]);
            }
            gv.drawBitmap(spriteBitmaps[resourceId], obtainableX * size - x, obtainableY * size - y, size / 2, size / 2);
        }
    }

    private Obtainable getObtainable(float x, float y) {
        for (Obtainable obtainable : obtainables) {
            if (obtainable.getMapX() == x && obtainable.getMapY() == y) {
                return obtainable;
            }
        }
        return null;
    }

    public void removeObtainable(Obtainable obtainable) {
        obtainables.remove(obtainable);
    }

    public Tile getTile(float x, float y, boolean forCollision) {
        boolean logPositions = false;
        for (Tile tile : tiles) {
            if (!forCollision) {
                if (tile.getX() == x && tile.getY() == y) {
                    return tile;
                }
            } else {
                try {
                    if ((x >= tile.getX() && x < tile.getX() + 1) && (y >= tile.getY() && y < tile.getY() + 1)) {

                        if (logPositions) Log.i("TILE", "Size: " + size +
                                "\nFirstgid: " + tile.getFirstgid() +
                                "\nmapX: " + this.x +
                                "\nmapY: " + this.y +
                                "\nseekerX: " + x +
                                "\nseekerY: " + y +
                                "\ntileX: " + tile.getX() +
                                "\ntileY: " + tile.getY());

                        return tile;
                    }
                } catch (NullPointerException npe) {
                    npe.printStackTrace();
                }
            }
        }
        return null;
    }
}

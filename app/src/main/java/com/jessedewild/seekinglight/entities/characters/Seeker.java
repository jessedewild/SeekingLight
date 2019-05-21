package com.jessedewild.seekinglight.entities.characters;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.jessedewild.seekinglight.R;
import com.jessedewild.seekinglight.constructors.Level;
import com.jessedewild.seekinglight.entities.Obtainable;
import com.jessedewild.seekinglight.entities.Tile;
import com.jessedewild.seekinglight.entities.obtainables.Coin;
import com.jessedewild.seekinglight.entities.obtainables.Star;
import com.jessedewild.seekinglight.game.Game;
import com.jessedewild.seekinglight.game.Map;
import com.jessedewild.seekinglight.lib.Entity;
import com.jessedewild.seekinglight.lib.GameView;
import com.jessedewild.seekinglight.utils.Constants;

public class Seeker extends Entity {

    // Game
    private Game game;

    // Model
    private final int[] drawables = {R.drawable.npc6_fr1, R.drawable.npc6_fr2, R.drawable.npc6_bk1, R.drawable.npc6_bk2, R.drawable.npc6_lf1, R.drawable.npc6_lf2, R.drawable.npc6_rt1, R.drawable.npc6_rt2};
    private Bitmap bitmap;
    public float size = 1.3f;
    private final float bitmapSize = (84 / size / 100);

    // Stats
    private int health;
    private int damage;

    // Position
    private Constants.FACING_POSITION facingPosition = Constants.FACING_POSITION.BACK;
    private float x;
    private float y;
    private float seekerX;
    private float seekerY;

    public Seeker(Game game) {
        this.game = game;
        this.health = 200;
        this.damage = 50;
        this.x = game.getWidth() / 2 - bitmapSize;
        this.y = game.getHeight() / 2 - bitmapSize;
    }

    public int[] getDrawables() {
        return drawables;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int enemyDamage) {
        this.health = this.health - enemyDamage;
    }

    public int getDamage() {
        return damage;
    }

    public void setFacingPosition(Constants.FACING_POSITION facingPosition) {
        this.facingPosition = facingPosition;
    }

    public Constants.FACING_POSITION getFacingPosition() {
        return facingPosition;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void move(float x, float y) {
        this.x = this.x + x;
        this.y = this.y + y;
    }

    @Override
    public void draw(GameView gv) {
        size = game.getHeight() / 16f;
        if (facingPosition.equals(Constants.FACING_POSITION.FRONT)) {
            bitmap = gv.getBitmapFromResource(drawables[0]);
        } else if (facingPosition.equals(Constants.FACING_POSITION.BACK)) {
            bitmap = gv.getBitmapFromResource(drawables[2]);
        } else if (facingPosition.equals(Constants.FACING_POSITION.LEFT)) {
            bitmap = gv.getBitmapFromResource(drawables[4]);
        } else {
            bitmap = gv.getBitmapFromResource(drawables[6]);
        }

        if (game.isShowCharactersOnMap()) gv.drawBitmap(bitmap, x, y, size, size);
    }

    public boolean collision() {
        Map map = game.getEntity(Map.class);
        float firstFutureX = 0, firstFutureY = 0, secondFutureX = 0, secondFutureY = 0;
        seekerX = (map.x + game.getWidthByTwo() - bitmapSize) / map.size;
        seekerY = (map.y + game.getHeightByTwo() - bitmapSize) / map.size;
        float bitmapWidth = (float) bitmap.getWidth() / 100 - bitmapSize / 2;
        float bitmapHeight = (float) bitmap.getHeight() / 100 - bitmapSize / 2;
        float distance = 0.022f;

        if (facingPosition.equals(Constants.FACING_POSITION.BACK)) {
            firstFutureX = seekerX;
            firstFutureY = seekerY + -distance;

            secondFutureX = firstFutureX + bitmapHeight + distance * size - distance;
            secondFutureY = firstFutureY;
        } else if (facingPosition.equals(Constants.FACING_POSITION.LEFT)) {
            firstFutureX = seekerX + -distance;
            firstFutureY = seekerY;

            secondFutureX = firstFutureX;
            secondFutureY = firstFutureY + bitmapHeight + distance * size - distance;
        } else if (facingPosition.equals(Constants.FACING_POSITION.FRONT)) {
            firstFutureX = seekerX + bitmapWidth + distance * size;
            firstFutureY = seekerY + bitmapHeight + distance * size - distance * 2;

            secondFutureX = firstFutureX - bitmapWidth;
            secondFutureY = firstFutureY;
        } else if (facingPosition.equals(Constants.FACING_POSITION.RIGHT)) {
            firstFutureX = seekerX + bitmapWidth + distance * size - distance * 2;
            firstFutureY = seekerY + bitmapHeight + distance * size;

            secondFutureX = firstFutureX;
            secondFutureY = firstFutureY - bitmapHeight;
        }

        Tile firstFutureTile, secondFutureTile;
        try {
            firstFutureTile = map.getTile(firstFutureX, firstFutureY, true);
            secondFutureTile = map.getTile(secondFutureX, secondFutureY, true);

            return firstFutureTile.getFirstgid() == 2 || secondFutureTile.getFirstgid() == 2;
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
        return false;
    }

    @Override
    public void tick() {
        if (!game.isAutoScroll()) {
            Map map = game.getEntity(Map.class);
            Obtainable removeObtainable = null;
            for (Obtainable obtainable : map.getObtainables()) {
                if (distance(obtainable.getMapX(), obtainable.getMapY(), seekerX, seekerY) < obtainable.getSize()) {
                    if (obtainable instanceof Coin) {
                        Log.e("COIN", "COIN!");
                        Constants.coins = Constants.coins + 1;
                        game.getCoinsView().setCoins(String.valueOf(Constants.coins));
                        // TODO: java.util.ConcurrentModificationException
                        removeObtainable = obtainable;
                    } else if (obtainable instanceof Star) {
                        Log.e("STAR", "STAR!");
                    }
                }
            }
            if (removeObtainable != null) map.removeObtainable(removeObtainable);
        }
    }

    private float distance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }
}

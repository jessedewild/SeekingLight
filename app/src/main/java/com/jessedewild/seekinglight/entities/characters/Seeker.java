package com.jessedewild.seekinglight.entities.characters;

import android.graphics.Bitmap;
import android.util.Log;

import com.jessedewild.seekinglight.R;
import com.jessedewild.seekinglight.entities.Tile;
import com.jessedewild.seekinglight.game.Game;
import com.jessedewild.seekinglight.game.Map;
import com.jessedewild.seekinglight.lib.Entity;
import com.jessedewild.seekinglight.lib.GameView;
import com.jessedewild.seekinglight.utils.CollisionDetection;
import com.jessedewild.seekinglight.utils.Constants;

public class Seeker extends Entity {

    // Game
    private Game game;

    // Model
    private final int[] drawables = {R.drawable.npc6_fr1, R.drawable.npc6_fr2, R.drawable.npc6_bk1, R.drawable.npc6_bk2, R.drawable.npc6_lf1, R.drawable.npc6_lf2, R.drawable.npc6_rt1, R.drawable.npc6_rt2};
    private Bitmap bitmap;
    private final float size = 1.3f;

    // Stats
    private int health;
    private int damage;

    // Position
    private Constants.FACING_POSITION facingPosition = Constants.FACING_POSITION.BACK;
    private float xPos, yPos;

    public Seeker(Game game) {
        this.game = game;
        this.health = 200;
        this.damage = 50;
        this.xPos = game.getWidth() / 2;
        this.yPos = game.getHeight() / 2;
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

    public float getXPos() {
        return xPos;
    }

    public float getYPos() {
        return yPos;
    }

    public void move(float x, float y) {
        this.xPos = xPos + x;
        this.yPos = yPos + y;
    }

    @Override
    public void draw(GameView gv) {
        if (facingPosition.equals(Constants.FACING_POSITION.FRONT)) {
            bitmap = gv.getBitmapFromResource(drawables[0]);
        } else if (facingPosition.equals(Constants.FACING_POSITION.BACK)) {
            bitmap = gv.getBitmapFromResource(drawables[2]);
        } else if (facingPosition.equals(Constants.FACING_POSITION.LEFT)) {
            bitmap = gv.getBitmapFromResource(drawables[4]);
        } else {
            bitmap = gv.getBitmapFromResource(drawables[6]);
        }

        if (game.isShowCharactersOnMap()) gv.drawBitmap(bitmap, xPos, yPos, size, size);
    }

    private boolean collision() {
        float futureX, futureY;
        if (facingPosition.equals(Constants.FACING_POSITION.FRONT)) {
            futureX = xPos;
            futureY = yPos + 1.5f;
        } else if (facingPosition.equals(Constants.FACING_POSITION.BACK)) {
            futureX = xPos;
            futureY = yPos + -1.5f;
        } else if (facingPosition.equals(Constants.FACING_POSITION.LEFT)) {
            futureX = xPos + -1f;
            futureY = yPos + 0.5f;
        } else {
            futureX = xPos + 2f;
            futureY = yPos;
        }

        Map map = game.getEntity(Map.class);
        Tile mainTile = map.getTile((int) xPos, (int) yPos);
        Tile futureTile = map.getTile((int) futureX, (int) futureY);

        Log.i("POSITIONS", facingPosition +
                "\nMAIN POS " + xPos + " " + yPos + " " + mainTile.getFirstgid() +
                "\nFUTURE   " + futureX + " " + futureY + " " + futureTile.getFirstgid() +
                "\nDISTANCE: " + distance(futureX, futureY, xPos, yPos));

        if (mainTile.getFirstgid() == 1 && distance(futureX, futureY, xPos, yPos) > 1) {
            return false;
        } else if (futureTile.getFirstgid() == 2 && distance(futureX, futureY, xPos, yPos) == 1) {
            return true;
        } else {
            return false;
        }
    }

    private float distance(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }
}

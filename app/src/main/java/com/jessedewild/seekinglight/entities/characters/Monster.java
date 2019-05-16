package com.jessedewild.seekinglight.entities.characters;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.jessedewild.seekinglight.R;
import com.jessedewild.seekinglight.game.Game;
import com.jessedewild.seekinglight.lib.Entity;

public class Monster extends Entity {

    private Game game;
    private int[] drawables = {R.drawable.skl1_fr1, R.drawable.skl1_fr2, R.drawable.skl1_bk1, R.drawable.skl1_bk2, R.drawable.skl1_lf1, R.drawable.skl1_lf2, R.drawable.skl1_rt1, R.drawable.skl1_rt2};
    private Bitmap bitmap;
    private int health;
    private int numOfMonsters;
    private final float size;
    private int[][] position;

    public Monster(Game game, int monsters) {
        this.game = game;
        this.size = game.getHeight() / 6;
        this.health = 150;
        this.setNumOfMonsters(monsters);
    }

    public int[] getDrawables() {
        return drawables;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(int drawable) {
        this.bitmap = getBitmapFromResource(drawable);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int damage) {
        this.health = health - damage;
    }

    public int getNumOfMonsters() {
        return numOfMonsters;
    }

    public void setNumOfMonsters(int numOfMonsters) {
        this.numOfMonsters = numOfMonsters;
    }

    public Bitmap getBitmapFromResource(int resourceId) {
        return BitmapFactory.decodeResource(Resources.getSystem(), resourceId);
    }
}

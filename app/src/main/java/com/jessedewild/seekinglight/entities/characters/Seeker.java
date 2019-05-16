package com.jessedewild.seekinglight.entities.characters;

import android.graphics.Bitmap;

import com.jessedewild.seekinglight.R;
import com.jessedewild.seekinglight.entities.Scroller;
import com.jessedewild.seekinglight.game.Game;
import com.jessedewild.seekinglight.lib.Entity;
import com.jessedewild.seekinglight.lib.GameView;
import com.jessedewild.seekinglight.utils.Constants;

public class Seeker extends Entity {

    private Game game;
    private final int[] drawables = {R.drawable.npc6_fr1, R.drawable.npc6_fr2, R.drawable.npc6_bk1, R.drawable.npc6_bk2, R.drawable.npc6_lf1, R.drawable.npc6_lf2, R.drawable.npc6_rt1, R.drawable.npc6_rt2};
    private Bitmap bitmap;
    private int health;
    private int damage;
    private final float size;
    private int[][] position;
    private Constants.FACING_POSITION facingPosition = Constants.FACING_POSITION.BACK;

    public Seeker(Game game) {
        this.game = game;
        this.size = game.getHeight() / 6;
        this.health = 200;
        this.damage = 50;
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

    public void setHealth(int damage) {
        this.health = this.health - damage;
    }

    public void setFacingPosition(Constants.FACING_POSITION facingPosition) {
        this.facingPosition = facingPosition;
    }

    public Constants.FACING_POSITION getFacingPosition() {
        return facingPosition;
    }

    @Override
    public void draw(GameView gv) {
        // Calculate which tiles are visible at the current scroll position.
        float scrollX = game.scroller.x;
        float scrollY = game.scroller.y;

        Bitmap bitmap;
        if (facingPosition.equals(Constants.FACING_POSITION.FRONT)) {
            bitmap = gv.getBitmapFromResource(drawables[0]);
        } else if (facingPosition.equals(Constants.FACING_POSITION.BACK)) {
            bitmap = gv.getBitmapFromResource(drawables[2]);
        } else if (facingPosition.equals(Constants.FACING_POSITION.LEFT)) {
            bitmap = gv.getBitmapFromResource(drawables[4]);
        } else {
            bitmap = gv.getBitmapFromResource(drawables[6]);
        }
//        System.out.println(facingPosition + " SEEKER.CLASS");

        gv.drawBitmap(bitmap, game.getEntity(Scroller.class).x * size - scrollX, game.getEntity(Scroller.class).y * size - scrollY, 1, 1);
    }
}

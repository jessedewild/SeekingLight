package com.jessedewild.seekinglight.spaceshooter;

import android.graphics.Bitmap;

import com.jessedewild.seekinglight.lib.Entity;
import com.jessedewild.seekinglight.lib.GameView;
import com.jessedewild.seekinglight.R;

public class Rock extends Entity {

    float xSpeed, ySpeed, aSpeed, xVal, yVal, aVal, size;
    static private Bitmap bitmap;
    private Game game;

    Rock(Game game) {
        this.game = game;

        aSpeed = (float) Math.random() * 0.3f - 0.15f;
        size = (float) Math.random() * 12 + 5;

        // Generate a random starting position just outside one of the four screen edges.
        // The speed is set such that the rock will move towards the screen.

        if (Math.random() > 0.5) {
            if (Math.random() > 0.5) { // right screen edge
                xVal = game.getWidth() + size;
                xSpeed = -0.1f * (float) Math.random() - 0.02f;
            } else { // left screen edge
                xVal = -size;
                xSpeed = 0.1f * (float) Math.random() + 0.02f;
            }
            yVal = (float) Math.random() * game.getHeight();
            ySpeed = 0.2f * (float) Math.random() - 0.1f;
        } else {
            if (Math.random() > 0.5) { // bottom screen edge
                yVal = game.getHeight() + size;
                ySpeed = -0.1f * (float) Math.random() - 0.02f;
            } else { // top screen edge
                yVal = -size;
                ySpeed = 0.1f * (float) Math.random() + 0.02f;
            }

            xVal = (float) Math.random() * game.getWidth();
            xSpeed = 0.2f * (float) Math.random() - 0.1f;
        }
    }

    @Override
    public void tick() {
        xVal += xSpeed;
        yVal += ySpeed;
        aVal += aSpeed;

        // Remove after exiting the screen
        if (xSpeed < 0 ? xVal < -size : xVal > game.getWidth() + size) game.removeEntity(this);
        else if (ySpeed < 0 ? yVal < -size : yVal > game.getHeight() + size)
            game.removeEntity(this);
    }

    @Override
    public void draw(GameView gv) {
        if (bitmap == null) {
            bitmap = gv.getBitmapFromResource(R.drawable.rock);
        }
        gv.drawBitmap(bitmap, xVal - size / 2, yVal - size / 2, size, size, aVal);
    }
}

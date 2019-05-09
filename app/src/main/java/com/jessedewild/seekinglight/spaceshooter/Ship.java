package com.jessedewild.seekinglight.spaceshooter;

import android.graphics.Bitmap;

import com.jessedewild.seekinglight.lib.Entity;
import com.jessedewild.seekinglight.lib.GameView;
import com.jessedewild.seekinglight.R;
import com.jessedewild.seekinglight.lib.GameModel;

public class Ship extends Entity {

    private final float acceleration = 0.003f;
    private final float rotationSpeed = 1f;
    private final float slowdown = 0.999f;
    private final float width = 10f;
    private final float height = 10f;
    private final float bounce = -0.8f; // 80% of original speed

    float xSpeed, ySpeed, xVal, yVal, aVal;
    static private Bitmap bitmap;
    private Game game;

    Ship(Game game) {
        this.game = game;
        xVal = game.getWidth() / 2;
        yVal = game.getHeight() / 2;
    }

    @Override
    public void tick() {
        xSpeed *= slowdown;
        ySpeed *= slowdown;

        // Scan the list of current touches to see if we need to rotate or accelerate.
        boolean left = false, right = false;
        for (GameModel.Touch touch : game.touches) {
            if (touch.x < game.getWidth() * 0.333) left = true;
            else if (touch.x < game.getWidth() * 0.666) left = right = true; // middle
            else right = true;
        }

        if (left && right) { // accelerate
            xSpeed += acceleration * Math.sin(Math.toRadians(aVal));
            ySpeed -= acceleration * Math.cos(Math.toRadians(aVal));
        } else if (left) {
            aVal -= rotationSpeed;
        } else if (right) {
            aVal += rotationSpeed;
        }
        xVal += xSpeed;
        yVal += ySpeed;

        // Bounce
        if (xVal < 0) {
            xVal = 2 * 0 - xVal;
            xSpeed = bounce * xSpeed;
        }
        if (xVal > game.getWidth()) {
            xVal = 2 * game.getWidth() - xVal;
            xSpeed = bounce * xSpeed;
        }
        if (yVal < 0) {
            yVal = 2 * 0 - yVal;
            ySpeed = bounce * ySpeed;
        }
        if (yVal > game.getHeight()) {
            yVal = 2 * game.getHeight() - yVal;
            ySpeed = bounce * ySpeed;
        }
    }

    @Override
    public void draw(GameView gv) {
        if (bitmap == null) {
            bitmap = gv.getBitmapFromResource(R.drawable.ship2);
        }
        gv.drawBitmap(bitmap, xVal - width / 2, yVal - height / 2, width, height, aVal);
    }
}

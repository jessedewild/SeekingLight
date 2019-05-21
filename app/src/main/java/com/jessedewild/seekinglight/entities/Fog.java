package com.jessedewild.seekinglight.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;

import com.jessedewild.seekinglight.game.Game;
import com.jessedewild.seekinglight.lib.Entity;

public class Fog extends Entity {

    // Game
    private Game game;

    // Bitmap
    private Bitmap bitmap;

    // Canvas
    private Canvas canvas;

    // Fog
    private float centerX;
    private float centerY;
    private float radius;
    public float size = 1.5f;
    private int alpha = 255;

    public Fog(Game game) {
        this.game = game;
        createFog();
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public void createFog() {
        bitmap = Bitmap.createBitmap((int) game.getWidth(), (int) game.getHeight(), Bitmap.Config.ARGB_8888); // Create a new image we will draw over the map
        canvas = new Canvas(bitmap); // Create a   canvas to draw onto the new image

        RectF outerRectangle = new RectF(0, 0, game.getWidth(), game.getHeight());

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG); // Anti alias allows for smooth corners
        paint.setColor(Color.BLACK); // This is the color of your activity background
        paint.setAlpha(alpha);
        canvas.drawRect(outerRectangle, paint);

        paint.setColor(Color.TRANSPARENT); // An obvious color to help debugging
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT)); // A out B http://en.wikipedia.org/wiki/File:Alpha_compositing.svg
        centerX = game.getWidth() / 2;
        centerY = game.getHeight() / 2;
        radius = Math.min(game.getWidth(), game.getHeight()) / 2 - size;
        canvas.drawCircle(centerX, centerY, radius, paint);
    }
}

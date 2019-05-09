package com.jessedewild.seekinglight.spaceshooter;

import android.graphics.Bitmap;

import com.jessedewild.seekinglight.lib.Entity;
import com.jessedewild.seekinglight.lib.GameView;
import com.jessedewild.seekinglight.R;

public class Stars extends Entity {
    private float angle = 0;
    private float zoom = 1.5f;

    private final float zoomMax = 10;
    private final float zoomMin = 0.01f;
    private final float zoomStep = 5;

    static private Bitmap bitmap;

    private Game game;

    Stars(Game game) {
        this.game = game;
    }

    @Override
    public void tick() {
        angle += 0.05;
        zoom *= 1 + 0.01;
        while (zoom >= zoomMax) {
            zoom /= zoomStep;
        }
    }

    @Override
    public void draw(GameView gv) {
        if (bitmap == null) {
            bitmap = gv.getBitmapFromResource(R.drawable.stars2);
        }
        float viewportSize = Math.max(game.getWidth(), game.getHeight());

        // The tricks is that we'll draw the same star field bitmap at different zoom
        // levels, each continuously rotating, growing and becoming less opaque as they grow.
        for (float z = zoom; z >= zoomMin; z /= zoomStep) {
            float size = z * viewportSize;
            gv.drawBitmap(
                    bitmap,
                    game.getWidth() / 2f - size / 2f,
                    game.getHeight() / 2f - size / 2f,
                    size,
                    size,
                    angle + z,
                    (int) (255f - 255f / zoomMax * z)
            );
        }
    }
}

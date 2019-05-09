package com.jessedewild.seekinglight.entities;

import android.graphics.Bitmap;

import com.jessedewild.seekinglight.R;
import com.jessedewild.seekinglight.lib.Entity;
import com.jessedewild.seekinglight.lib.GameView;
import com.jessedewild.seekinglight.game.Game;

public class Background extends Entity {

    private static Bitmap bitmap;
    private Game game;

    public Background(Game game) {
        this.game = game;
    }

    @Override
    public void draw(GameView gv) {
        if (bitmap == null) {
            bitmap = gv.getBitmapFromResource(R.drawable.background);
        }

        float bgWidth = (float) bitmap.getWidth() / (float) bitmap.getHeight() * game.getHeight();
        float offset = (game.scroller.x / 3f) % bgWidth; // one-third speed relative to foreground

        for (int x = 0; x <= Math.ceil(game.getWidth() / bgWidth); x++) {
            gv.drawBitmap(bitmap, (float) x * bgWidth - offset, 0, bgWidth, game.getHeight());
        }
    }
}

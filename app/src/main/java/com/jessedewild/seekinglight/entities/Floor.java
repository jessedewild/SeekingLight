package com.jessedewild.seekinglight.entities;

import android.graphics.Bitmap;

import com.jessedewild.seekinglight.R;
import com.jessedewild.seekinglight.game.Game;
import com.jessedewild.seekinglight.lib.Entity;
import com.jessedewild.seekinglight.lib.GameView;

public class Floor extends Entity {

    static private Bitmap bitmap;
    private Game game;

    private float size = Math.max(game.getWidth(), game.getHeight());

    public Floor(Game game) {
        this.game = game;
    }

    @Override
    public void draw(GameView gv) {
        if (bitmap == null) {
            bitmap = gv.getBitmapFromResource(R.drawable.floor);
        }

        gv.drawBitmap(bitmap, game.getWidth() / 2f - size / 2f, game.getHeight() / 2f - size / 2f);
    }
}

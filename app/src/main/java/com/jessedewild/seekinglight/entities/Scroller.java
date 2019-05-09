package com.jessedewild.seekinglight.entities;

import android.view.MotionEvent;

import com.jessedewild.seekinglight.lib.Entity;
import com.jessedewild.seekinglight.lib.GameModel;
import com.jessedewild.seekinglight.game.Game;

public class Scroller extends Entity {

    private Game game;
    public float x = 0;

    public Scroller(Game game) {
        this.game = game;
    }

    // Auto-scroll
    @Override
    public void tick() {
        scroll(0.3f / game.ticksPerSecond());
    }

    // Scroll on drag
    @Override
    public void handleTouch(GameModel.Touch touch, MotionEvent event) {
        scroll(-touch.deltaX);
    }

    private void scroll(float delta) {
        x = (x + delta) % Map.width;
        for (Game.Listener listener : game.listeners) {
            listener.scrollChanged();
        }
    }
}

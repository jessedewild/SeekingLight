package com.jessedewild.seekinglight.entities;

import android.view.MotionEvent;

import com.jessedewild.seekinglight.lib.Entity;
import com.jessedewild.seekinglight.lib.GameModel;
import com.jessedewild.seekinglight.game.Game;
import com.jessedewild.seekinglight.game.Map;

public class Scroller extends Entity {

    private Game game;
    private Map map;
    public float x = 0;
    public float y = 0;
    public boolean autoScroll = false;

    public Scroller(Game game) {
        this.game = game;
        this.map = game.getEntity(Map.class);
    }

    // Auto-scroll
    @Override
    public void tick() {
        if (autoScroll) scroll(0.2f / game.ticksPerSecond());
    }

    // Scroll on drag
    @Override
    public void handleTouch(GameModel.Touch touch, MotionEvent event) {
        scroll(-touch.deltaX, -touch.deltaY);
    }

    private void scroll(float deltaX, float deltaY) {
        x = (x + deltaX) % map.width;
        y = (y + deltaY) % map.height;
        for (Game.Listener listener : game.listeners) {
            listener.scrollChanged();
        }
    }

    private void scroll(float delta) {
        x = (x + delta) % map.width;
        y = (y + delta) % map.height;
        for (Game.Listener listener : game.listeners) {
            listener.scrollChanged();
        }
    }

    public void setAutoScroll(boolean autoScroll) {
        this.autoScroll = autoScroll;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}

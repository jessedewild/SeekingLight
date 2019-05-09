package com.jessedewild.seekinglight.platformer;

import java.util.ArrayList;

import com.jessedewild.seekinglight.lib.GameModel;

public class Game extends GameModel {

    // GameModel state
    Map map;
    Scroller scroller;

    // The listener receives calls when some game state is changed that should be
    // shown in Android Views other than the `GameView`. In this case, we're only
    // calling a method when scrollX changes.
    // The default implementation does nothing.
    // This variable is marked `transient` as it is not actually part of the model,
    // and should (and could) therefore not be serialized when the game is
    // suspended by Android.
    public interface Listener {
        void scrollChanged();
    }

    public transient ArrayList<Listener> listeners = new ArrayList<>();

    @Override
    public float getWidth() {
        // Width is always 8 units.
        return 8f;
    }

    @Override
    public float getHeight() {
        // Height fills actual screen size, but is based on width scaling.
        return actualHeight / actualWidth * getWidth();
    }

    @Override
    public void start() {
        addEntity(new Background(this));

        map = new Map(this);
        addEntity(map);

        scroller = new Scroller(this);
        addEntity(scroller);

        // Fire event to set initial value in scroll view
        for (Listener listener : listeners) {
            listener.scrollChanged();
        }
    }
}

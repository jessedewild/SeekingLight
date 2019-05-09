package com.jessedewild.seekinglight.game;

import com.jessedewild.seekinglight.entities.Background;
import com.jessedewild.seekinglight.lib.GameModel;
import com.jessedewild.seekinglight.entities.Map;
import com.jessedewild.seekinglight.entities.Scroller;

import java.util.ArrayList;

public class Game extends GameModel {
    // GameModel state
    private Map map;
    public Scroller scroller;

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

    public transient ArrayList<Game.Listener> listeners = new ArrayList<>();

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
        for (Game.Listener listener : listeners) {
            listener.scrollChanged();
        }
    }
}

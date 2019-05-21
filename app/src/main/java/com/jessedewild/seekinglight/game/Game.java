package com.jessedewild.seekinglight.game;

import android.content.Context;

import com.google.gson.Gson;
import com.jessedewild.seekinglight.compounds.CoinsView;
import com.jessedewild.seekinglight.constructors.Level;
import com.jessedewild.seekinglight.entities.Background;
import com.jessedewild.seekinglight.entities.Fog;
import com.jessedewild.seekinglight.entities.characters.Monster;
import com.jessedewild.seekinglight.entities.characters.Seeker;
import com.jessedewild.seekinglight.lib.GameModel;
import com.jessedewild.seekinglight.entities.Scroller;

import java.util.ArrayList;

public class Game extends GameModel {

    // GameModel state
    public Context context;
    private Background background;
    public Map map;
    public Scroller scroller;
    public Seeker seeker;
    public Monster monster;
    public Fog fog;
    private String json;
    private boolean autoScroll, showCharactersOnMap, showFog;
    private CoinsView coinsView;

    public Game(Context context) {
        this.context = context;
    }

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
        return 10f;
    }

    @Override
    public float getHeight() {
        // Height fills actual screen size, but is based on width scaling.
        return actualHeight / actualWidth * getWidth();
    }

    @Override
    public void start() {
        // Background entity
        background = new Background(this);
        addEntity(background);

        // Map entity
        map = new Map(this, json);
        Level.LevelProperty[] levelProperty = new Gson().fromJson(json, Level.class).getProperties();
        float x = levelProperty[2].getValue() * map.size - getWidthByTwo() + (map.size / 2);
        float y = levelProperty[3].getValue() * map.size - getHeightByTwo() + (map.size / 2);
        System.out.println(getHeight());
        map.setPos(x, y);
        addEntity(map);

        // Scroller entity for positioning
        scroller = new Scroller(this);
        scroller.setAutoScroll(autoScroll);
        addEntity(scroller);

        // Seeker entity
        seeker = new Seeker(this);
        addEntity(seeker);

        // Monster entity
        monster = new Monster(this, 5);
        addEntity(monster);

        // Fog entity
        fog = new Fog(this);
        addEntity(fog);

        // Fire event to set initial value in scroll view
        for (Game.Listener listener : listeners) {
            listener.scrollChanged();
        }
    }

    public float getWidthByTwo() {
        return 10f / 2;
    }

    public float getHeightByTwo() {
        return (actualHeight / actualWidth * getWidth()) / 2;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public boolean isAutoScroll() {
        return autoScroll;
    }

    public void setAutoScroll(boolean autoScroll) {
        this.autoScroll = autoScroll;
    }

    public boolean isShowCharactersOnMap() {
        return showCharactersOnMap;
    }

    public void setShowCharactersOnMap(boolean showCharactersOnMap) {
        this.showCharactersOnMap = showCharactersOnMap;
    }

    public CoinsView getCoinsView() {
        return coinsView;
    }

    public void setCoinsView(CoinsView coinsView) {
        this.coinsView = coinsView;
    }

    public boolean isShowFog() {
        return showFog;
    }

    public void setShowFog(boolean showFog) {
        this.showFog = showFog;
    }
}

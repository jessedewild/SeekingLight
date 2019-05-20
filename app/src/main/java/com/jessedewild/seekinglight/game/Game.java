package com.jessedewild.seekinglight.game;

import android.widget.TextView;

import com.google.gson.Gson;
import com.jessedewild.seekinglight.constructors.Level;
import com.jessedewild.seekinglight.entities.characters.Monster;
import com.jessedewild.seekinglight.entities.characters.Seeker;
import com.jessedewild.seekinglight.lib.GameModel;
import com.jessedewild.seekinglight.entities.Scroller;

import java.util.ArrayList;

public class Game extends GameModel {

    // GameModel state
    public Map map;
    public Scroller scroller;
    public Seeker seeker;
    public Monster monster;
    private String json;
    private boolean autoScroll, showCharactersOnMap;
    private float deviceWidth, deviceHeight;
    private TextView coinsView;

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
        map = new Map(this, json);
        Level.LevelProperty[] levelProperty = new Gson().fromJson(json, Level.class).getProperties();
        map.setPos(levelProperty[1].getValue(), levelProperty[2].getValue());
//        float layerX = layer.getX() / 1080.0f * (actualWidth * (actualHeight / 1794.0f));
//        float layerY = layer.getY() / 1794.0f * actualHeight;
//        map.setPos(layerX, layerY);
        addEntity(map);

        scroller = new Scroller(this);
        scroller.setAutoScroll(autoScroll);
        addEntity(scroller);

        seeker = new Seeker(this);
        addEntity(seeker);

        monster = new Monster(this, 5);
        addEntity(monster);

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

    public void setDeviceSize(float deviceWidth, float deviceHeight) {
        this.deviceWidth = deviceWidth;
        this.deviceHeight = deviceHeight;
    }

    public TextView getCoinsView() {
        return coinsView;
    }

    public void setCoinsView(TextView coinsView) {
        this.coinsView = coinsView;
    }
}

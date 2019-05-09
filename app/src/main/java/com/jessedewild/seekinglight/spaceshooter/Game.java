package com.jessedewild.seekinglight.spaceshooter;

import com.jessedewild.seekinglight.lib.GameModel;

public class Game extends GameModel {

    @Override
    public void start() {
        addEntity(new Stars(this));
        addEntity(new Ship(this));
        addEntity(new Redness(this));
        addEntity(new RockCreator(this));
    }

    @Override
    public float getWidth() {
        // Virtual screen should be at least 100 wide and 100 high.
        return 100f * actualWidth / Math.min(actualWidth,actualHeight);
    }
    
    @Override
    public float getHeight() {
        // Virtual screen should be at least 100 wide and 100 high.
        return 100f * actualHeight / Math.min(actualWidth,actualHeight);
    }
}

package com.jessedewild.seekinglight.spaceshooter;

import java.util.Random;

import com.jessedewild.seekinglight.lib.Entity;

public class RockCreator extends Entity {

    private Game game;
    private Random random = new Random();
    private int tickCount;

    public RockCreator(Game game) {
        this.game = game;
    }

    @Override
    public void tick() {
        float gameTime = (float) ++tickCount / game.ticksPerSecond();
        // Start with one rock every 2s. every 1s after 2m. every 0.66s after 4m. etc.
        float avgTimeBetweenRocks = 2f * 120f / (120f + gameTime);
        while (random.nextFloat() < (1f / avgTimeBetweenRocks) / game.ticksPerSecond()) {
            game.addEntity(new Rock(game));
        }
    }
}


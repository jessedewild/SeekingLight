package com.jessedewild.seekinglight.utils;

public class Save {

    private int coins;
    private int level;

    public Save() {
        this.coins = Constants.coins;
        this.level = Constants.level;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}

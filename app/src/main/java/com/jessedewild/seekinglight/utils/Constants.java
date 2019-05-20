package com.jessedewild.seekinglight.utils;

public class Constants {

    public static int coins;

    public enum FACING_POSITION {
        FRONT,
        BACK,
        LEFT,
        RIGHT
    }

    public static final float mainMapSize = 2.0763888f;

    @Override
    public String toString() {
        return String.valueOf(coins);
    }
}

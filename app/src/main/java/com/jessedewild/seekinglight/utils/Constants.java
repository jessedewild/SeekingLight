package com.jessedewild.seekinglight.utils;

public class Constants {

    public static int coins;
    public static int level = 1;

    public enum FACING_POSITION {
        FRONT,
        BACK,
        LEFT,
        RIGHT
    }

    @Override
    public String toString() {
        return String.valueOf(coins);
    }
}

package com.jessedewild.seekinglight.entities.contracts;

import android.content.ContentValues;
import android.content.Entity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

abstract public class CharacterInterface {

    /**
     * Get health
     *
     * @return int
     */

    int getHealth() {
        return 0;
    }

    /**
     * If Entity is a character
     */
    void setHealth(int damage) {
    }

    /**
     * Load/decode an image. This is pretty slow.
     * For instance: `Bitmap b = gameModel.getBitmapFromResource(R.drawable.my_image);`
     */
    Bitmap getBitmapFromResource(int resourceId) {
        return BitmapFactory.decodeResource(Resources.getSystem(), resourceId);
    }

    enum FACING_POSITION {
        FRONT,
        BACK,
        LEFT,
        RIGHT
    }
}

package com.jessedewild.seekinglight.lib;

import android.support.annotation.NonNull;
import android.view.MotionEvent;

import java.io.Serializable;

abstract public class Entity implements Comparable<Entity>, Serializable {

    // Used to sort objects on the same layer in the entities tree.
    private int id;

    // Static variable that provides the next `id`.
    private static int count = 0;

    // The constructor assigns an id that is used for ordering draws.
    public Entity() {
        id = ++count;
    }

    /**
     * Override this method to determine the rendering order for this
     * object. Higher numbers get drawn later, overdrawing.
     * The number you return *should be constant* for a specific object.
     * The default layer is 0. Negative layer numbers are allowed.
     * Objects in the same layer are drawn in the order they were created.
     *
     * @return Layer id.
     */
    public int getLayer() {
        return 0;
    }

    /**
     * Called `GameModel::ticksPerSecond()` times per second.
     * The method is to update the game state accordingly.
     */
    public void tick() {
    }

    /**
     * Called up to 60 times per second, system performance allowing.
     * The method is to draw the Entity to the GameView. Entities
     * can be more abstract in nature (CollisionChecker, ObjectSpawner, ..),
     * in which case this method does not need to be overridden.
     *
     * @param gv The `GameView` to draw to.
     */
    public void draw(GameView gv) {
    }

    /**
     * Can be overridden if the entity wants to act on touch events.
     *
     * @param touch Information about the touch this event is about.
     * @param event ACTION_DOWN, ACTION_UP or ACTION_MOVE.
     */
    public void handleTouch(GameModel.Touch touch, MotionEvent event) {
    }

    // Used by the TreeSet to order GameObjects.
    // We order by layer first, and then by id.
    @Override
    public int compareTo(@NonNull Entity o) {
        int prio = getLayer() - o.getLayer();
        return prio == 0 ? id - o.id : prio;
    }
}

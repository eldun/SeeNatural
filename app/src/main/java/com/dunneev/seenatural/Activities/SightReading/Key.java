package com.dunneev.seenatural.Activities.SightReading;

import android.graphics.RectF;

class Key {

    protected int sound;
    protected RectF rect;
    protected boolean down;

    private static final String LOG_TAG = Key.class.getSimpleName();

    protected Key(RectF rect, int sound) {
        this.sound = sound;
        this.rect = rect;
    }
}

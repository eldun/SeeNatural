package com.dunneev.seenatural.Activities.SightRead;

import android.graphics.RectF;

class Key {

    public int sound;
    public RectF rect;
    public boolean down;

    public Key(RectF rect, int sound) {
        this.sound = sound;
        this.rect = rect;
    }
}

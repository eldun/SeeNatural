package com.dunneev.seenatural.Activities.SightReading;

import android.graphics.RectF;

class PianoKey {

    private static final String LOG_TAG = PianoKey.class.getSimpleName();

    protected int pianoKeyValue;
    protected RectF rectangle;
    protected boolean isDown;


    public PianoKey(RectF rectangle, int keyNumber) {
        this.pianoKeyValue = keyNumber;
        this.rectangle = rectangle;
    }
}

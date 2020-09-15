package com.dunneev.seenatural.Activities.SightReading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

class PianoKey extends View {

    private static final String LOG_TAG = PianoKey.class.getSimpleName();

    private PianoNote note;

    private RectF whiteKeySize;
    private RectF blackKeySize;

    private Paint upColor;
    private Paint downColor;

    private boolean isDown;

    public interface PianoKeyListener {
        void keyDown(PianoKey key);

        void keyUp(PianoKey key);
    }

    private PianoKeyListener pianoKeyListener;


    public PianoNote getNote() {
        return note;
    }
    public void setNote(PianoNote note) {
        this.note = note;
    }

    public Paint getUpColor() {
        return upColor;
    }
    public void setUpColor(Paint upColor) {
        this.upColor = upColor;
    }

    public Paint getDownColor() {
        return downColor;
    }
    public void setDownColor(Paint downColor) {
        this.downColor = downColor;
    }

    public boolean isDown() {
        return isDown;
    }
    public void setIsDown(boolean isDown) {
        if (isDown) this.isDown = true;
        else this.isDown = false;
    }

    // Assign the listener implementing events interface that will receive the events
    public void setPianoKeyListener(PianoKeyListener listener) {
        this.pianoKeyListener = listener;
    }

    public PianoKey(Context context) {
        super(context);
    }

    public PianoKey(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PianoKey(Context context, PianoNote note) {
        super(context);
        setId(note.absoluteNotePositionIndex); // This is done to be able to reference individual notes in the sight-reading activity
        this.note = note;
        upColor = new Paint();
        upColor.setColor(note.keyColor);
        downColor = new Paint();
        downColor.setColor(note.keyDownColor);
        isDown = false;
        this.pianoKeyListener = null;
    }

    @Override
    public void draw(Canvas canvas) {
        Log.i(LOG_TAG, "draw()");

        super.draw(canvas);
        RectF rectF = new RectF(0,0, 200, 200);
        canvas.drawRect(rectF, new Paint(upColor));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.i(LOG_TAG, "onSizeChanged(w: " + w + " h: " + h + " oldw: " + oldw + " oldh: " + oldh);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        Log.i(LOG_TAG, "onTouchEvent(motionEvent)");
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(LOG_TAG, "action down");
                setIsDown(true);
                pianoKeyListener.keyDown(this);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(LOG_TAG, "moving: (" + x + ", " + y + ")");
                break;
            case MotionEvent.ACTION_UP:
                Log.i(LOG_TAG, "action up");
                setIsDown(false);
                pianoKeyListener.keyUp(this);
                invalidate();
                break;
        }
        return true;
    }

    @Override
    public boolean performClick() {
        // todo: implelement performClick() for accessibility reasons
        return super.performClick();
    }

    @Override
    public String toString() {
        return this.note.label;
    }
}

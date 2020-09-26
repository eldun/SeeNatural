package com.dunneev.seenatural.Activities.SightReading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

class PianoKey extends View {

    private static final String LOG_TAG = PianoKey.class.getSimpleName();

    private PianoNote note;

    RectF keyRectangle = new RectF();
    private int color;
    private Paint upColor;
    private Paint downColor;
    private Paint strokePaint;

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

    public int getColor() {
        return color;
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
        this.isDown = isDown;
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

        setId(note.absoluteKeyIndex); // This is done to be able to reference individual notes in the sight-reading activity
        this.note = note;
        this.color = note.keyColor;
        upColor = new Paint();
        upColor.setColor(note.keyColor);
        downColor = new Paint();
        downColor.setColor(note.keyDownColor);
        strokePaint = new Paint();
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setColor(Color.BLACK);
        isDown = false;
        this.pianoKeyListener = null;
    }

    @Override
    public void onDraw(Canvas canvas) {

        int cornerRadius = 10;
        keyRectangle.set(0, 0, getWidth(), getHeight());
        strokePaint.setStrokeWidth((float) (getWidth()/24.0));
        canvas.drawRoundRect(keyRectangle, cornerRadius, cornerRadius, this.upColor);
        canvas.drawRoundRect(keyRectangle, cornerRadius, cornerRadius, strokePaint);

//        Log.d(LOG_TAG, String.format("Draw key: (0, 0, %d, %d), %s", getWidth(), getHeight(), this.upColor));
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        Log.i(LOG_TAG, "onMeasure()");
//
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }

//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        Log.i(LOG_TAG, "onSizeChanged(w: " + w + " h: " + h + " oldw: " + oldw + " oldh: " + oldh);
//        super.onSizeChanged(w, h, oldw, oldh);
//    }

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

    @NonNull
    public String toString() {
        return this.note.label;
    }
}

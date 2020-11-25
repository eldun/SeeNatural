package com.dunneev.seenatural.Piano;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

import com.dunneev.seenatural.Enums.PianoNote;

public class PianoKey extends View {

    private static final String LOG_TAG = PianoKey.class.getSimpleName();

    // Key measurements in inches
    private static final double whiteToBlackWidthRatio = (7.0/8.0)/(15.0/32.0);
    private static final double whiteToBlackHeightRatio = (6.0)/(63.0/16.0);

    public static int whiteKeyWidth;
    public static int blackKeyWidth;
    public static int whiteKeyHeight;
    public static int blackKeyHeight;
    public static int whiteCount;
    public static int blackCount;
    public static int count;

    private PianoNote note;

    RectF keyRectangle = new RectF();

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        if (note.keyColor == Color.WHITE) {
            this.isWhiteKey = true;
            this.isBlackKey = false;
        }

        else {
            this.isWhiteKey = false;
            this.isBlackKey = true;
        }
    }

    private int color;
    private Paint upColor;
    private Paint downColor;
    private Paint strokePaint;

    private boolean isDown;

    private boolean isWhiteKey;
    private boolean isBlackKey;

    public boolean isWhiteKey() {
        return isWhiteKey;
    }
    public boolean isBlackKey() {
        return isBlackKey;
    }





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

        this.note = note;

        setColor(note.keyColor);


        this.pianoKeyListener = null;
        upColor = new Paint();
        upColor.setColor(note.keyColor);
        downColor = new Paint();
        downColor.setColor(note.keyDownColor);
        strokePaint = new Paint();
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setColor(Color.BLACK);
        isDown = false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // Default values just in case something goes wrong
        int desiredWidth = 100;
        int desiredHeight = 100;

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);


        whiteKeyWidth = widthSize / PianoKey.whiteCount;
        whiteKeyHeight = heightSize;
        blackKeyWidth = (int) (whiteKeyWidth / whiteToBlackWidthRatio);
        blackKeyHeight = (int) (whiteKeyHeight / whiteToBlackHeightRatio);

        if (isWhiteKey) {
            desiredWidth = whiteKeyWidth;
            desiredHeight = whiteKeyHeight;
        }
        else {
            desiredWidth = blackKeyWidth;
            desiredHeight = blackKeyHeight;
        }


        int width;
        int height;

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(desiredHeight, heightSize);
        } else {
            //Be whatever you want
            height = desiredHeight;
        }

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        } else {
            //Be whatever you want
            width = desiredWidth;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    public void onDraw(Canvas canvas) {

        int cornerRadius = 10;
        keyRectangle.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
        strokePaint.setStrokeWidth((float) 3);
        canvas.drawRoundRect(keyRectangle, cornerRadius, cornerRadius, this.upColor);
        canvas.drawRoundRect(keyRectangle, cornerRadius, cornerRadius, strokePaint);

//        TextDrawable textDrawable = new TextDrawable(note.label);
//        Rect noteBoundsRect = new Rect();
//        noteBoundsRect.set(0,getMeasuredHeight() - 50, getMeasuredWidth(), getMeasuredHeight());
//
//        textDrawable.setBounds(noteBoundsRect);
//        textDrawable.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        Log.i(LOG_TAG, "onTouchEvent(motionEvent)");
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                Log.i(LOG_TAG, "action down");
                setIsDown(true);
                pianoKeyListener.keyDown(this);
                break;
            case MotionEvent.ACTION_MOVE:
//                Log.i(LOG_TAG, "moving: (" + x + ", " + y + ")");
                break;
            case MotionEvent.ACTION_UP:
//                Log.i(LOG_TAG, "action up");
                setIsDown(false);
                pianoKeyListener.keyUp(this);
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

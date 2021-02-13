package com.dunneev.seenatural.Fragments.Piano;

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

    private Paint keyPaint;
    private Paint strokePaint;

    public boolean isWhite;
    public boolean isBlack;


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
        isWhite = note.isWhiteKey;
        isBlack = !isWhite;

        this.pianoKeyListener = null;

        keyPaint = new Paint();

        if(isWhite){
            keyPaint.setColor(PianoView.getWhiteKeyUpColor());
        }
        else if(isBlack){
            keyPaint.setColor(PianoView.getBlackKeyUpColor());
        }

        strokePaint = new Paint();
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setColor(Color.BLACK);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // Default values just in case something goes wrong
        int desiredWidth;
        int desiredHeight;

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);


        double whiteKeyCount = (double) PianoKey.whiteCount;
        whiteKeyWidth = (int) Math.round(widthSize / whiteKeyCount);
        whiteKeyHeight = heightSize;
        blackKeyWidth = (int) (whiteKeyWidth / whiteToBlackWidthRatio);
        blackKeyHeight = (int) (whiteKeyHeight / whiteToBlackHeightRatio);

        if (isWhite) {
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
        canvas.drawRoundRect(keyRectangle, cornerRadius, cornerRadius, keyPaint);
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
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                Log.i(LOG_TAG, "action down");
                pianoKeyListener.keyDown(this);
                drawKeyDown();
                break;
            case MotionEvent.ACTION_MOVE:
//                Log.i(LOG_TAG, "moving: (" + x + ", " + y + ")");
                break;
            case MotionEvent.ACTION_UP:
//                Log.i(LOG_TAG, "action up");
                pianoKeyListener.keyUp(this);
                drawKeyUp();
                break;
        }
        return true;
    }

    private void drawKeyDown() {
        if(isWhite)
            keyPaint.setColor(PianoView.getWhiteKeyDownColor());
        else
            keyPaint.setColor(PianoView.getBlackKeyDownColor());
        invalidate();
    }

    private void drawKeyUp() {
        if(isWhite)
            keyPaint.setColor(PianoView.getWhiteKeyUpColor());
        else
            keyPaint.setColor(PianoView.getBlackKeyUpColor());
        invalidate();
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

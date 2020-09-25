package com.dunneev.seenatural.Activities.SightReading;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.dunneev.seenatural.R;

import java.util.ArrayList;
import java.util.Collections;

public class PianoView extends ViewGroup {

    private static final String LOG_TAG = PianoView.class.getSimpleName();

    // in inches (for reference)
    private static final double whiteToBlackWidthRatio = (7.0/8.0)/(15.0/32.0);
    private static final double whiteToBlackHeightRatio = (6.0)/(63.0/16.0);

    private int absoluteStartingPianoKeyIndex = 39;
    private int numberOfKeys = 12;

    private double whiteKeyWidth;
    private double whiteKeyHeight;
    private double blackKeyWidth;
    private double blackKeyHeight;
    private Paint white, black, yellow, green, red ; // TODO: Change colors to facilitate correct/incorrect when sight-reading

    private ArrayList<PianoKey> pianoKeys = new ArrayList<>();
    private ArrayList<PianoKey> whitePianoKeys = new ArrayList<>();
    private ArrayList<PianoKey> blackPianoKeys = new ArrayList<>();




    // Getters & Setters
    public int getAbsoluteStartingPianoKeyIndex () {
        return absoluteStartingPianoKeyIndex;
    }

    public void setAbsoluteStartingPianoKeyIndex ( int absoluteStartingPianoKeyIndex){
        this.absoluteStartingPianoKeyIndex = absoluteStartingPianoKeyIndex;
    }

    public int getNumberOfKeys () {
        return numberOfKeys;
    }

    public void setNumberOfKeys ( int numberOfKeys){
        this.numberOfKeys = numberOfKeys;
    }

    public double getWhiteKeyWidth() {
        return whiteKeyWidth;
    }

    public void setWhiteKeyWidth(double whiteKeyWidth) {
        this.whiteKeyWidth = whiteKeyWidth;
    }

    public double getWhiteKeyHeight() {
        return whiteKeyHeight;
    }

    public void setWhiteKeyHeight(double whiteKeyHeight) {
        this.whiteKeyHeight = whiteKeyHeight;
    }

    public double getBlackKeyWidth() {
        return blackKeyWidth;
    }

    public void setBlackKeyWidth(double blackKeyWidth) {
        this.blackKeyWidth = blackKeyWidth;
    }

    public double getBlackKeyHeight() {
        return blackKeyHeight;
    }

    public void setBlackKeyHeight(double blackKeyHeight) {
        this.blackKeyHeight = blackKeyHeight;
    }

    public ArrayList<PianoKey> getPianoKeys () {
        return pianoKeys;
    }

    public ArrayList<PianoKey> getWhitePianoKeys() {
        return whitePianoKeys;
    }

    public void setWhitePianoKeys(ArrayList<PianoKey> whitePianoKeys) {
        this.whitePianoKeys = whitePianoKeys;
    }

    public ArrayList<PianoKey> getBlackPianoKeys() {
        return blackPianoKeys;
    }

    public void setBlackPianoKeys(ArrayList<PianoKey> blackPianoKeys) {
        this.blackPianoKeys = blackPianoKeys;
    }





    public PianoView(Context context, int startingKeyIndex, int numberOfKeys) {
        super(context);
        setAbsoluteStartingPianoKeyIndex(startingKeyIndex);
        setNumberOfKeys(numberOfKeys);
        init();
    }

    public PianoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PianoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        white = new Paint();
        white.setColor(Color.WHITE);
        white.setStyle(Paint.Style.FILL);
        black = new Paint();
        black.setColor(Color.BLACK);
        yellow = new Paint();
        yellow.setColor(Color.YELLOW);
        yellow.setStyle(Paint.Style.FILL);
        green = new Paint();
        green.setColor(Color.GREEN);
        green.setStyle(Paint.Style.FILL);
        red = new Paint();
        red.setColor(Color.RED);
        red.setStyle(Paint.Style.FILL);

        populatePianoKeyArrays();
        addKeysToView();
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        PianoKey childView;

        // White keys first
        int whiteKeyCounter = 0;
        for (PianoKey key : whitePianoKeys) {
            childView = (PianoKey) getChildAt(whiteKeyCounter);

            int whiteLeftCoord = (int) (whiteKeyCounter * whiteKeyWidth);
            int whiteRightCoord = (int) ((whiteKeyCounter + 1) * whiteKeyWidth);
            childView.layout(whiteLeftCoord, 0, whiteRightCoord, (int) whiteKeyHeight);

            whiteKeyCounter++;
        }

        // Black keys

        // blackKeyCounter is set to whitePianoKeys.size() because the black key child views come
        // after the white keys. As such, they will be drawn on top of the white keys.
        int blackKeyCounter = whitePianoKeys.size();

        // Using just the index of the black keys would create too much space between them.
        int blackKeyOffsetAdjust = 0;

        for (int i=0; i<pianoKeys.size(); i++) {
            if (pianoKeys.get(i).getColor() == Color.BLACK) {
                childView = (PianoKey) getChildAt(blackKeyCounter);

                int blackLeftCoord = (int) ((whiteKeyWidth * (i - blackKeyOffsetAdjust)) - (0.5 * blackKeyWidth));
                int blackRightCoord = (int) ((whiteKeyWidth * (i - blackKeyOffsetAdjust)) + (0.5 * blackKeyWidth));
                childView.layout(blackLeftCoord, 0, blackRightCoord, (int) blackKeyHeight);

                blackKeyCounter++;
                blackKeyOffsetAdjust++;
            }
        }
    }

    protected void populatePianoKeyArrays() {
        pianoKeys.clear();
        whitePianoKeys.clear();
        blackPianoKeys.clear();

        for (int i=0;i<numberOfKeys;i++) {

            PianoKey key = new PianoKey(getContext(), PianoNote.valueOfNotePosition(absoluteStartingPianoKeyIndex + i));
            pianoKeys.add(key);

            if (key.getColor() == Color.WHITE)
                whitePianoKeys.add(key);
            else
                blackPianoKeys.add(key);
        }
    }

    // Add child views of PianoKey to PianoView
    // White first, then black - for when the piano is actually drawn.
    protected void addKeysToView() {
        removeAllViews();

        for (PianoKey key : whitePianoKeys) {
            addView(key);
        }

        for (PianoKey key : blackPianoKeys) {
            addView(key);
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        whiteKeyWidth = (double) w / whitePianoKeys.size();
        blackKeyWidth = whiteKeyWidth / whiteToBlackWidthRatio;
        whiteKeyHeight = h;
        blackKeyHeight = whiteKeyHeight / whiteToBlackHeightRatio;

    }


    @Override
    protected void onDraw(Canvas canvas) { // TODO: Add setting to turn on/off "drawing" capability
        Log.i(LOG_TAG, "onDraw(canvas)");
    }

    @Override
    public void invalidate() {
        super.invalidate();
        init();
    }
}



















//        for (PianoKey k : pianoKeys) {
//            canvas.drawRect(k.getKeyRectangle(), k.isDown() ? k.getDownColor() : k.getUpColor());
//        }

//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        int action = event.getAction();
//        boolean isDownPress = action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE;
//
//        for (int touchIndex = 0; touchIndex < event.getPointerCount(); touchIndex++) {
//            float x = event.getX(touchIndex);
//            float y = event.getY(touchIndex);
//
//            PianoKey k = keyTouched(x,y);
//
//            if (k != null) {
//                k.isDown = isDownPress;
//            }
//        }
//
//        ArrayList<PianoKey> tmp = new ArrayList<>(whiteKeys);
//        tmp.addAll(blackKeys);
//
//        return true;
//    }
//
//    @org.jetbrains.annotations.Nullable
//    private PianoKey keyTouched(float x, float y) {
//        for (PianoKey k : blackKeys) {
//            if (k.rectangle.contains(x,y)) {
//                return k;
//            }
//        }
//
//        for (PianoKey k : whiteKeys) {
//            if (k.rectangle.contains(x,y)) {
//                return k;
//            }
//        }
//
//        return null;
//    }
//
//    private void releaseKey(final PianoKey k) {
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                k.isDown = false;
//                handler.sendEmptyMessage(0);
//            }
//        }, 100);
//    }
//
//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            invalidate();
//        }
//    };
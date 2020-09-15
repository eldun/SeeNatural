package com.dunneev.seenatural.Activities.SightReading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dunneev.seenatural.R;

import java.util.ArrayList;

public class PianoView extends ViewGroup {

    private static final String LOG_TAG = PianoView.class.getSimpleName();

    private int startingPianoKey = PianoNote.C4.midiValue;
    private int numberOfKeys = 1;
    private Paint white, black, yellow, green, red ; // TODO: Change colors to facilitate correct/incorrect when sight-reading

    private ArrayList<PianoKey> pianoKeys = new ArrayList<>();

    public ArrayList<PianoKey> getPianoKeys() {
        return pianoKeys;
    }

    public PianoView(Context context, AttributeSet attrs) {
        super(context, attrs);
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

        for (int i=0;i<numberOfKeys;i++) {
            pianoKeys.add(new PianoKey(context, PianoNote.valueOfMidi(startingPianoKey + i)));
        }

        PianoView myView  = findViewById(R.id.pianoView);
        for (PianoKey key : pianoKeys) {
            myView.addView(key);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final View child = getChildAt(0);
        child.layout(0, 0, this.getWidth(), this.getHeight());
    }

//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//        keyWidth = w / numberOfKeys;
//        height = h;
//
//        for (int i = 0; i < numberOfKeys; i++) {
//            int left = i * keyWidth;
//            int right = left + keyWidth;
//
//            if (i == numberOfKeys - 1) {
//                right = w;
//            }
//
//            RectF rect = new RectF(left, 0, right, h);
//            whiteKeys.add(new PianoKey(rect, PianoNote.C4));
//
//            if (i != 0  &&   i != 3  &&  i != 7  &&  i != 10) {
//                rect = new RectF((float) (i - 1) * keyWidth + 0.5f * keyWidth + 0.25f * keyWidth, 0,
//                        (float) i * keyWidth + 0.25f * keyWidth, 0.67f * height);
//                blackKeys.add(new PianoKey(rect, PianoNote.C_SHARP_5));
//            }
//        }
//    }




//    @Override
//    protected void onDraw(Canvas canvas) { // TODO: Add setting to turn on/off "drawing" capability
//        Log.i(LOG_TAG, "onDraw(canvas)");
//
//        for (PianoKey key : pianoKeys) {
//            key.draw(canvas);
//        }
//
//
//
//
//
//    }


    //
    // Input Routines
    //

//    @Override
//    public boolean onTouchEvent(MotionEvent motionEvent) {
//        Log.i(LOG_TAG, "onTouchEvent(motionEvent)");
//        int x = (int) motionEvent.getX();
//        int y = (int) motionEvent.getY();
//
//        switch (motionEvent.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                Log.i(LOG_TAG, "action down");
////                setIsDown(true);
////                keyDown();
//                invalidate();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.i(LOG_TAG, "moving: (" + x + ", " + y + ")");
//                break;
//            case MotionEvent.ACTION_UP:
//                Log.i(LOG_TAG, "action up");
////                setIsDown(false);
////                keyDown();
//                invalidate();
//                break;
//        }
//        return true;
//    }

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
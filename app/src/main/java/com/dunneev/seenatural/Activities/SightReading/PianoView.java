package com.dunneev.seenatural.Activities.SightReading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class PianoView extends View {

    private static final String LOG_TAG = PianoView.class.getSimpleName();

    private int startingPianoKey = 28; // Key C3
    private int NB = 14; // TODO: Change to single octave and test
    private Paint white, black, yellow, green, red ; // TODO: Change colors to facilitate correct/incorrect when sight-reading
    private ArrayList<PianoKey> whiteKeys = new ArrayList<>();
    private ArrayList<PianoKey> blackKeys = new ArrayList<>();
    private int keyWidth, height;
    private SoundPlayer soundPlayer;



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
        soundPlayer = new SoundPlayer(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        keyWidth = w / NB;
        height = h;
        int count = 15;

        for (int i = 0; i < NB; i++) {
            int left = i * keyWidth;
            int right = left + keyWidth;

            if (i == NB - 1) {
                right = w;
            }

            RectF rect = new RectF(left, 0, right, h);
            whiteKeys.add(new PianoKey(rect, i + 1));

            if (i != 0  &&   i != 3  &&  i != 7  &&  i != 10) {
                rect = new RectF((float) (i - 1) * keyWidth + 0.5f * keyWidth + 0.25f * keyWidth, 0,
                        (float) i * keyWidth + 0.25f * keyWidth, 0.67f * height);
                blackKeys.add(new PianoKey(rect, count));
                count++;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) { // TODO: Add setting to turn on/off "drawing" capability
        for (PianoKey k : whiteKeys) {
            canvas.drawRect(k.rectangle, k.isDown ? yellow : white);
        }

        for (int i = 1; i < NB; i++) {
            canvas.drawLine(i * keyWidth, 0, i * keyWidth, height, black);
        }

        for (PianoKey k : blackKeys) {
            canvas.drawRect(k.rectangle, k.isDown ? yellow : black);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        boolean isDownAction = action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE;

        for (int touchIndex = 0; touchIndex < event.getPointerCount(); touchIndex++) {
            float x = event.getX(touchIndex);
            float y = event.getY(touchIndex);

            PianoKey k = keyForCoords(x,y);

            if (k != null) {
                k.isDown = isDownAction;
            }
        }

        ArrayList<PianoKey> tmp = new ArrayList<>(whiteKeys);
        tmp.addAll(blackKeys);

        for (PianoKey k : tmp) {
            if (k.isDown) {
                if (!soundPlayer.isNotePlaying(k.pianoKeyValue)) {
                    soundPlayer.playNote(k.pianoKeyValue);
                    invalidate();
                } else {
                    releaseKey(k);
                }
            } else {
                soundPlayer.stopNote(k.pianoKeyValue);
                releaseKey(k);
            }
        }

        return true;
    }

    private PianoKey keyForCoords(float x, float y) {
        for (PianoKey k : blackKeys) {
            if (k.rectangle.contains(x,y)) {
                return k;
            }
        }

        for (PianoKey k : whiteKeys) {
            if (k.rectangle.contains(x,y)) {
                return k;
            }
        }

        return null;
    }

    private void releaseKey(final PianoKey k) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                k.isDown = false;
                handler.sendEmptyMessage(0);
            }
        }, 100);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            invalidate();
        }
    };
}
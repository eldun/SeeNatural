package com.dunneev.seenatural.Activities.SightReading;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.dunneev.seenatural.R;

import java.util.ArrayList;

public class PianoView extends ViewGroup {

    private static final String LOG_TAG = PianoView.class.getSimpleName();

    public PianoNote getLowestPracticeNote() {
        return lowestPracticeNote;
    }

    public void setLowestPracticeNote(PianoNote lowestPracticeNote) {
        this.lowestPracticeNote = lowestPracticeNote;
        init();
    }

    public PianoNote getHighestPracticeNote() {
        return highestPracticeNote;
    }

    public void setHighestPracticeNote(PianoNote highestPracticeNote) {
        this.highestPracticeNote = highestPracticeNote;
        init();
    }

    private PianoNote lowestPracticeNote;
    private PianoNote highestPracticeNote;

    // TODO: Change colors to facilitate correct/incorrect when sight-reading
    private Paint white = new Paint();
    private Paint black = new Paint();


    private ArrayList<PianoKey> pianoKeys = new ArrayList<>();
    private ArrayList<PianoKey> whitePianoKeys = new ArrayList<>();
    private ArrayList<PianoKey> blackPianoKeys = new ArrayList<>();

    public ArrayList<PianoKey> getPianoKeys() {
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


    public PianoView(Context context, PianoNote lowestPracticeNote, PianoNote highestPracticeNote) {
        super(context);
        this.lowestPracticeNote = lowestPracticeNote;
        this.highestPracticeNote = highestPracticeNote;
        init();
    }

    public PianoView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // This section is mostly to display the XML preview
        TypedArray styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.PianoView);

        lowestPracticeNote = PianoNote.valueOfAbsoluteKeyIndex(styledAttributes.getInt(R.styleable.PianoView_pianoLowPracticeNote, 39));
        highestPracticeNote = PianoNote.valueOfAbsoluteKeyIndex(styledAttributes.getInt(R.styleable.PianoView_pianoHighPracticeNote, 51));

        styledAttributes.recycle();

        init();
    }

    private void init() {
        removeAllViews();

        white.setColor(Color.WHITE);
        white.setStyle(Paint.Style.FILL);
        black.setColor(Color.BLACK);
        black.setStyle(Paint.Style.FILL);

        PianoKey.count = (highestPracticeNote.absoluteKeyIndex - lowestPracticeNote.absoluteKeyIndex) + 1;

        populatePianoKeyArrays();
        addKeysToView();
    }

    protected void populatePianoKeyArrays() {
        pianoKeys.clear();
        whitePianoKeys.clear();
        blackPianoKeys.clear();

        for (int i = 0; i < PianoKey.count; i++) {

            PianoKey key = new PianoKey(getContext(), PianoNote.valueOfAbsoluteKeyIndex(lowestPracticeNote.absoluteKeyIndex + i));
            pianoKeys.add(key);
            if (key.isWhiteKey()) {
                whitePianoKeys.add(key);
            }
            else
                blackPianoKeys.add(key);
        }
        PianoKey.whiteCount = whitePianoKeys.size();
        PianoKey.blackCount = blackPianoKeys.size();
    }

    // Add child views of PianoKey to PianoView
    // White first, then black (for drawing order).
    protected void addKeysToView() {
        for (PianoKey key : whitePianoKeys) {
            addView(key);
        }

        for (PianoKey key : blackPianoKeys) {
            addView(key);
        }

    }

    /**
     * Calculate size of PianoView and all children
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        // Default layout rectangle values
        int childLeft = 100;
        int childTop = 100;
        int childRight = 300;
        int childBottom = 300;

        final int childCount = getChildCount();

        if (childCount == 0) {
            return;
        }

        // Using just the index of the black keys would create too much space between them.
        int blackKeyOffsetAdjust = 0;

        for (int i = 0; i < childCount; i++) {
            final PianoKey child = (PianoKey) getChildAt(i);

            if (child.isWhiteKey()) {
                childLeft = PianoKey.whiteKeyWidth * i;
                childTop = 0;
                childRight = childLeft + PianoKey.whiteKeyWidth;
                childBottom = childTop + PianoKey.whiteKeyHeight;
            }

            else {
                int blackKeyIndex = pianoKeys.indexOf(child);
                childLeft = (int) ((PianoKey.whiteKeyWidth * (blackKeyIndex - blackKeyOffsetAdjust)) - (0.5 * PianoKey.blackKeyWidth));
                childTop = 0;
                childRight = childLeft + PianoKey.blackKeyWidth;
                childBottom = childTop + PianoKey.blackKeyHeight;
                blackKeyOffsetAdjust++;
            }

            child.layout(childLeft, childTop, childRight, childBottom);
        }
    }
}
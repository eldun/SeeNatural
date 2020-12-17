package com.dunneev.seenatural.Fragments.Piano;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.dunneev.seenatural.Enums.PianoNote;
import com.dunneev.seenatural.R;

import java.util.ArrayList;

public class PianoView extends ViewGroup {

    private static final String LOG_TAG = PianoView.class.getSimpleName();

    private PianoNote lowestPracticeNote;
    private PianoNote highestPracticeNote;


    private ArrayList<PianoKey> pianoKeys = new ArrayList<>();
    private ArrayList<PianoKey> whitePianoKeys = new ArrayList<>();
    private ArrayList<PianoKey> blackPianoKeys = new ArrayList<>();

    // TODO: Change colors to facilitate correct/incorrect when sight-reading
    private static int whiteKeyUpColor;
    private static int whiteKeyDownColor;
    private static int blackKeyUpColor;
    private static int blackKeyDownColor;

    public PianoNote getLowestPracticeNote() {
        return lowestPracticeNote;
    }

    public void setLowestPracticeNote(PianoNote lowestPracticeNote) {
        this.lowestPracticeNote = lowestPracticeNote;
    }

    public PianoNote getHighestPracticeNote() {
        return highestPracticeNote;
    }

    public void setHighestPracticeNote(PianoNote highestPracticeNote) {
        this.highestPracticeNote = highestPracticeNote;
    }

    public static int getWhiteKeyUpColor() {
        return whiteKeyUpColor;
    }

    public static int getWhiteKeyDownColor() {
        return whiteKeyDownColor;
    }


    public static int getBlackKeyUpColor() {
        return blackKeyUpColor;
    }

    public static int getBlackKeyDownColor() {
        return blackKeyDownColor;
    }

    public static void setWhiteKeyUpColor(int whiteKeyUpColor) {
        PianoView.whiteKeyUpColor = whiteKeyUpColor;
    }

    public static void setWhiteKeyDownColor(int whiteKeyDownColor) {
        PianoView.whiteKeyDownColor = whiteKeyDownColor;
    }


    public static void setBlackKeyUpColor(int blackKeyUpColor) {
        PianoView.blackKeyUpColor = blackKeyUpColor;
    }

    public static void setBlackKeyDownColor(int blackKeyDownColor) {
        PianoView.blackKeyDownColor = blackKeyDownColor;
    }

    public ArrayList<PianoKey> getPianoKeys() {
        return pianoKeys;
    }

    public PianoView(Context context) {
        super(context);
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

    public void init() {

        PianoKey.count = (highestPracticeNote.absoluteKeyIndex - lowestPracticeNote.absoluteKeyIndex) + 1;

        populatePianoKeyArrays();
        addKeysToView();
    }

    private void populatePianoKeyArrays() {
        pianoKeys.clear();
        whitePianoKeys.clear();
        blackPianoKeys.clear();

        for (int i = 0; i < PianoKey.count; i++) {

            PianoKey key = new PianoKey(getContext(), PianoNote.valueOfAbsoluteKeyIndex(lowestPracticeNote.absoluteKeyIndex + i));
            pianoKeys.add(key);
            if (key.isWhite) {
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
        this.removeAllViews();
        for (PianoKey key : whitePianoKeys) {
            addView(key);
        }

        for (PianoKey key : blackPianoKeys) {
            addView(key);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
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

            if (child.isWhite) {
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
package com.dunneev.seenatural.Activities.SightReading;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.dunneev.seenatural.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class StaffView extends ViewGroup {

    private static final String LOG_TAG = StaffView.class.getSimpleName();

    boolean trebleClef = true;
    boolean bassClef;
    private KeySignature keySignature = KeySignature.A_MINOR;
    int clefWidth;
    int staffLineSpacing;
    int visibleStaffHeight;
    int totalStaffHeight;
    int noteWidth;
    protected Paint staffLinePaint;
    PianoNote lowPracticeNote;
    PianoNote highPracticeNote;
    int numberOfPracticeNotes;
    int notesOnStaff = 0;
    ArrayList<PianoNote> practiceNotesAscending;
    ArrayList<PianoNote> practiceNotesDescending;
    ArrayList<StaffLine> staffLines;
    int staffLineThickness;
    int yCoord = 0;
    static int staffViewOnMeasureCount = 0;

    HorizontalScrollView scrollView;
    LinearLayout noteLinearLayout;


    private static final Map<PianoNote, Integer> noteStaffCoordinateMap = new HashMap<>();


    public boolean isTrebleClef() {
        return trebleClef;
    }

    public void setTrebleClef(boolean trebleClef) {
        this.trebleClef = trebleClef;
    }

    public boolean isBassClef() {
        return bassClef;
    }

    public void setBassClef(boolean bassClef) {
        this.bassClef = bassClef;
    }

    public KeySignature getKeySignature() {
        return keySignature;
    }

    public void setKeySignature(KeySignature keySignature) {
        this.keySignature = keySignature;
    }

    public StaffView(Context context) {
        super(context);
        init();
    }

    public StaffView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        init();
    }

    public StaffView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void init() {
        staffLinePaint = new Paint();
        staffLinePaint.setColor(Color.RED);

        lowPracticeNote = PianoNote.C4;
        highPracticeNote = PianoNote.C6;
        numberOfPracticeNotes = (highPracticeNote.absoluteKeyIndex -
                lowPracticeNote.absoluteKeyIndex)
                + 1;
        practiceNotesAscending = new ArrayList<>();
        practiceNotesDescending = new ArrayList<>();

        staffLines = new ArrayList<>();

        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));


        populatePracticeNoteArrays();
        addStaffLinesToView();
        addClefToView();
        addNoteScrollerToView();
        setClipChildren(false);
    }

    public void addTestButton(View view) {
        Button button0 = new Button(getContext());
        button0.setText("button0");
        button0.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        noteLinearLayout.addView(button0);
    }

    /**
     * Calculate size of StaffView and all children
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        staffLineSpacing = MeasureSpec.getSize(heightMeasureSpec) / staffLines.size();
        staffLineThickness = MeasureSpec.getSize(heightMeasureSpec)/(numberOfPracticeNotes * 4);

        visibleStaffHeight = staffLineSpacing * 8;
        totalStaffHeight = staffLineSpacing * numberOfPracticeNotes;
        noteWidth = staffLineSpacing * 3;

        StaffLine.setDesiredHeight(staffLineSpacing);
        StaffClef.setDesiredHeight(visibleStaffHeight);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

//
//        staffLineSpacing = getHeight() / staffLines.size();
//        int clefWidth = MeasureSpec.makeMeasureSpec(staffLineSpacing * 3, MeasureSpec.EXACTLY);
//        int clefHeight = MeasureSpec.makeMeasureSpec(staffLineSpacing * 8, MeasureSpec.EXACTLY);
//
//        getChildAt(staffLines.size() + 1).measure(clefWidth, clefHeight);

        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
    }


    /**
     * Position all children within this layout.
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        int childLeft = 100;
        int childTop = 100;
        int childRight = 300;
        int childBottom = 300;

        // Reset staff coord to zero so that if the ViewGroup is
        // laid out more than once, the staff isn't off-screen
        yCoord = 0;

        final int childCount = getChildCount();

        if (childCount == 0) {
                return;
        }

            for (int i = 0; i < childCount; i++) {
                final View child = getChildAt(i);

                if (child.getClass() == StaffLine.class) {

                    childLeft = 0;
                    childTop = yCoord;
                    childRight = child.getMeasuredWidth();
                    childBottom = childTop + child.getMeasuredHeight();

                    noteStaffCoordinateMap.put(((StaffLine) child).note, (childTop + childBottom) / 2);

                    yCoord += staffLineSpacing;
                }

                else if (child.getClass() == StaffClef.class) {

                    childLeft = 0;
                    childTop = noteStaffCoordinateMap.get(PianoNote.F5);
                    childRight = child.getMeasuredWidth();
                    childBottom = childTop + child.getMeasuredHeight();

                    clefWidth = childRight;
                }

                // NoteScroller
                else if (child.getClass() == HorizontalScrollView.class) {
                    childLeft = clefWidth;
                    childTop = 0;
                    childRight = getMeasuredWidth();
                    childBottom = getMeasuredHeight();
                }

                else {
                    Log.i(LOG_TAG, "you missed laying out " + child.getClass().toString());
                }

                child.layout(childLeft, childTop, childRight, childBottom);

            }
        }


    private void populatePracticeNoteArrays() {

        for (int i=0; i<numberOfPracticeNotes; i++) {
            PianoNote note = PianoNote.valueOfAbsoluteKeyIndex(lowPracticeNote.absoluteKeyIndex + i);
            practiceNotesAscending.add(note);
            practiceNotesDescending.add(note);
        }
        Collections.reverse(practiceNotesDescending);
    }

    private void addStaffLinesToView() {
        StaffLine line;


        for (PianoNote note: practiceNotesDescending) {

            // Staff lines are only ever "natural" (white).
            // Whether they are sharp or flat is signified by
            // either the key signature or a ♯/♮/♭ symbol if the note in question is an accidental.
            if (note.keyColor == Color.WHITE) {
                line = new StaffLine(this.getContext(), note);
                line.setStaffLinePaint(staffLinePaint);
                staffLines.add(line);
                LayoutParams staffLineParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                addView(line, staffLineParams);
            }
        }
    }




    private void addClefToView() {

        StaffClef staffClef = CreateClef();

        // The treble clef is taller than the staff, but the clipped parts should still be visible.
        // The bounding box (which sets the font size) is only as tall as the staff.
//        setClipChildren(false);

        LayoutParams staffClefParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        staffClef.setLayoutParams(staffClefParams);

        addView(staffClef);
    }

    private StaffClef CreateClef() {
        if (trebleClef) {
            return new StaffClef(getContext(), getResources().getString(R.string.char_treble_clef), keySignature);
        }

        else {
            return new StaffClef(getContext(), getResources().getString(R.string.char_bass_clef), keySignature);
        }
    }



    private void addNoteScrollerToView() {

        // Create scroll view
        scrollView = new HorizontalScrollView(getContext());
//        scrollView.setBackgroundColor(Color.WHITE);
//        scrollView.setAlpha(.3f);
        LayoutParams scrollViewParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        scrollView.setLayoutParams(scrollViewParams);

        // Create linear layout for scrollable elements
        noteLinearLayout = new LinearLayout(getContext());

        FrameLayout.LayoutParams noteLinearLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT);
        noteLinearLayout.setLayoutParams(noteLinearLayoutParams);
        noteLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

//        noteLinearLayout.setBackgroundColor(Color.GREEN);
//        noteLinearLayout.setAlpha(.4f);

        // Accidental symbols were getting clipped
        noteLinearLayout.setClipChildren(false);
        scrollView.addView(noteLinearLayout);

        addView(scrollView);
    }


    protected void addNote(PianoNote note) {
        StaffNote staffNote = new StaffNote(getContext(), keySignature, note);

        // noteStaffCoordinateMap only contains coordinates for non-accidental notes,
        // which is why we use the natural note field to determine the position.
        LinearLayout.LayoutParams staffNoteParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, visibleStaffHeight);

        staffNoteParams.setMargins(200, 0, 200, 0);

        staffNote.setLayoutParams(staffNoteParams);
        staffNote.setTranslationY(noteStaffCoordinateMap.get(PianoNote.valueOfLabel(note.naturalNoteLabel)) - visibleStaffHeight);

        noteLinearLayout.addView(staffNote);

        notesOnStaff++;

    }

    protected void removeNote(PianoNote note) {

    }

}


package com.dunneev.seenatural.Activities.SightReading;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.dunneev.seenatural.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class StaffView extends ViewGroup {

    public static final String LOG_TAG = StaffView.class.getSimpleName();
    public static final int TYPE_TREBLE_CLEF = 0;
    public static final int TYPE_BASS_CLEF = 1;
    public static final PianoNote lowestNote = PianoNote.A0;
    public static final PianoNote highestNote = PianoNote.C8;

    static String selectedClef;
    private KeySignature keySignature = KeySignature.A_MINOR;
    int clefWidth;

    // The distance between natural notes e.g. A4 to B4
    int staffLineSpacing;
    int staffNoteHorizontalMargins = 100;
    int visibleStaffHeight;
    int totalStaffHeight;
    int noteWidth;
    PianoNote lowestPracticeNote;
    PianoNote highestPracticeNote;
    int numberOfPracticeNotes;
    int notesOnStaff = 0;
    ArrayList<PianoNote> practiceNotesAscending;
    ArrayList<PianoNote> practiceNotesDescending;
    ArrayList<StaffLine> staffLines;
    int staffLineYCoordinate = 0;

    HorizontalScrollView scrollView;
    LinearLayout noteLinearLayout;


    private static final Map<PianoNote, Integer> noteStaffCoordinateMap = new HashMap<>();


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

        // These lines are mostly just so that the XML preview shows the staff
        TypedArray styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.StaffView);

        if (styledAttributes.getInt(R.styleable.StaffView_selectedClef, 0) == TYPE_TREBLE_CLEF) {
            selectedClef = context.getString(R.string.trebleClef);
        }
        else if (styledAttributes.getInt(R.styleable.StaffView_selectedClef, 0) == TYPE_BASS_CLEF) {
            selectedClef = context.getString(R.string.bassClef);
        }


        lowestPracticeNote = PianoNote.valueOfAbsoluteKeyIndex(styledAttributes.getInt(R.styleable.StaffView_lowPracticeNote, 39));
        highestPracticeNote = PianoNote.valueOfAbsoluteKeyIndex(styledAttributes.getInt(R.styleable.StaffView_highPracticeNote, 51));

        styledAttributes.recycle();

        /* Not sure if casting the context is the right way to go about getting
        the settings selected in previous activities(clef & difficulty). However, XML attrs
        cannot be set dynamically, which is what I would have liked to have done. Another option was
        static variables. Might have to make some changes when the time comes to implement
        sight reading with both clefs.

        Will likely use SharedPreferences instead of extras in the future.
         */
//        if (context instanceof SightReadingActivity) {
//            selectedClef = ((SightReadingActivity) context).getSelectedClef();
//        }
//

        init();
    }

    private void init() {
        staffLines = new ArrayList<>();

        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));


        setPracticeNoteRange();
        populatePracticeNoteArrays();
        addStaffLinesToView();
        addClefsToView();
        addNoteScrollerToView();

        // The treble clef is taller than the staff, but the clipped parts should still be visible.
        // The bounding box (which sets the font size) is only as tall as the staff.
        setClipChildren(false);
    }

    private void setPracticeNoteRange() {

        numberOfPracticeNotes = (highestPracticeNote.absoluteKeyIndex -
                lowestPracticeNote.absoluteKeyIndex)
                + 1;
        practiceNotesAscending = new ArrayList<>();
        practiceNotesDescending = new ArrayList<>();
    }

    /**
     * Calculate size of StaffView and all children
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);
        staffLineSpacing = height / staffLines.size();
        staffNoteHorizontalMargins = staffLineSpacing * 4;

        visibleStaffHeight = staffLineSpacing * 8;
        totalStaffHeight = staffLineSpacing * numberOfPracticeNotes;
        noteWidth = staffLineSpacing * 3;

        StaffLine.setDesiredHeight(staffLineSpacing);
        StaffClef.setDesiredHeight(visibleStaffHeight);


        measureChildren(widthMeasureSpec, heightMeasureSpec);

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

        // Only white notes take up space
        int clippedHighStaffNoteCount= PianoNote.numberOfWhiteKeysInRangeInclusive(highestPracticeNote, highestNote);
        staffLineYCoordinate = -(clippedHighStaffNoteCount * staffLineSpacing);


        // I've decided to map every note instead of available practice notes.
        // It's more flexible - a clef can be drawn partially off the screen, for example.
        for (int i=87;i>=0;i--) {
            PianoNote note = PianoNote.valueOfAbsoluteKeyIndex(i);

            Log.i(LOG_TAG, "putting " + note.toString() + " at " + staffLineYCoordinate);


            noteStaffCoordinateMap.put(note, staffLineYCoordinate);

            if (note.keyColor == Color.WHITE) {
                staffLineYCoordinate += (staffLineSpacing);
            }
        }


        final int childCount = getChildCount();

        if (childCount == 0) {
            return;
        }

        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);

            if (child.getClass() == StaffLine.class) {

                Log.i(LOG_TAG, "laying out " + child.toString());
//                    child.setBackgroundColor(Color.WHITE);
//                    child.setAlpha(.3f);
                childLeft = 0;
//                    childTop = staffLineYCoordinate;
                // noteStaffMap stores the actual position of the staff line, not its bounds
                childTop = noteStaffCoordinateMap.get(((StaffLine) child).note) - (staffLineSpacing / 2);
                childRight = child.getMeasuredWidth();
                childBottom = childTop + child.getMeasuredHeight();

//                    noteStaffCoordinateMap.put(((StaffLine) child).note, (childTop + childBottom) / 2);

//                    staffLineYCoordinate += staffLineSpacing;
            }

            else if (child.getClass() == StaffClef.class) {

                if (((StaffClef) child).getClef().equals(getResources().getString(R.string.trebleClef))) {
                    childLeft = 0;
                    childTop = noteStaffCoordinateMap.get(PianoNote.F5);
                    childRight = child.getMeasuredWidth();
                    childBottom = childTop + child.getMeasuredHeight();
                }

                else if (((StaffClef) child).getClef().equals(getResources().getString(R.string.bassClef))) {
                    childLeft = 0;
                    childTop = noteStaffCoordinateMap.get(PianoNote.B3) + (staffLineSpacing/4);
                    childRight = child.getMeasuredWidth();
                    childBottom = childTop + child.getMeasuredHeight();
                }

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
            PianoNote note = PianoNote.valueOfAbsoluteKeyIndex(lowestPracticeNote.absoluteKeyIndex + i);
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
                line = new StaffLine(getContext(), note);
//                line.setStaffLinePaint(staffLinePaint);
                staffLines.add(line);
                LayoutParams staffLineParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                addView(line, staffLineParams);
            }
        }
    }




    private void addClefsToView() {

        StaffClef trebleClef = new StaffClef(getContext(), getResources().getString(R.string.trebleClef), keySignature);

        StaffClef bassClef = new StaffClef(getContext(), getResources().getString(R.string.bassClef), keySignature);

        trebleClef.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        bassClef.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        addView(trebleClef);
        addView(bassClef);
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


        // Prevent accidental symbols from getting clipped
        noteLinearLayout.setClipChildren(false);
        // Setting StaffView.clipChildren to false to display the whole clef resulted in notes scrolling under the clef.
        // Setting scrollView.clipChildren didn't seem to do anything.
        // This is a workaround for clipping notes as they approach the staff.
        scrollView.setPadding(-1,0,0,0);
        scrollView.setClipToPadding(true);

        scrollView.addView(noteLinearLayout);

        addView(scrollView);
    }


    protected void addNote(PianoNote note) {
        StaffNote staffNote = new StaffNote(getContext(), keySignature, note);

        // noteStaffCoordinateMap only contains coordinates for non-accidental notes,
        // which is why we use the natural note field to determine the position.
        LinearLayout.LayoutParams staffNoteParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, visibleStaffHeight);
        staffNote.setLayoutParams(staffNoteParams);

        staffNoteParams.setMargins(0, 0, staffNoteHorizontalMargins, 0);

        staffNote.setTranslationY(noteStaffCoordinateMap.get(PianoNote.valueOfLabel(note.naturalNoteLabel)) - visibleStaffHeight + staffLineSpacing);

        noteLinearLayout.addView(staffNote);

        notesOnStaff++;

    }

    protected void removeNote(PianoNote note) {

    }

}


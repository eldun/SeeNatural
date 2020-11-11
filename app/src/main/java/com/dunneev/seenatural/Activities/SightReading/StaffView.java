package com.dunneev.seenatural.Activities.SightReading;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
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

    HorizontalScrollView scrollView;
    LinearLayout noteLinearLayout;

    LinearLayout.LayoutParams noteLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);



    LayoutParams staffWrapParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);



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
        super(context, attrs);
        init();
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
        populatePracticeNotes();
        populateStaffLines();
    }

    private void populatePracticeNotes() {

        for (int i=0; i<numberOfPracticeNotes; i++) {
            PianoNote note = PianoNote.valueOfAbsoluteKeyIndex(lowPracticeNote.absoluteKeyIndex + i);
            practiceNotesAscending.add(note);
            practiceNotesDescending.add(note);
        }
        Collections.reverse(practiceNotesDescending);
    }

    private void populateStaffLines() {
        StaffLine line;

        for (PianoNote note: practiceNotesDescending) {

            // Staff lines are only ever "natural" (white).
            // Whether they are sharp or flat is signified by
            // either the key signature or a ♯/♮/♭ symbol if the note in question is an accidental.
            if (note.keyColor == Color.WHITE) {
                line = new StaffLine(this.getContext(), note);
                staffLines.add(line);
            }
        }
    }


    private void drawStaffLines() {

        StaffLine staffLine;

        staffLineSpacing = getHeight() / (staffLines.size());
        staffLineThickness = getHeight()/(numberOfPracticeNotes * 4);
//        int staffLineSpacing = viewHeight / (numberOfPracticeNotes);
        int yCoordinate = 0;

        staffLinePaint.setStrokeWidth(staffLineThickness);


        for (int i = 0; i < staffLines.size(); i++) {
            staffLine = staffLines.get(i);
            staffLine.setStaffLinePaint(staffLinePaint);

            staffLine.layout(0, yCoordinate, getWidth(), yCoordinate+staffLineSpacing);

            // Map notes to Y coordinates on the staff
            noteStaffCoordinateMap.put(staffLine.note, yCoordinate + staffLineSpacing + (staffLineThickness / 2));

            yCoordinate += staffLineSpacing;

            addViewInLayout(staffLine, -1, staffWrapParams,true);
        }

        visibleStaffHeight = staffLineSpacing * 8;
        totalStaffHeight = staffLineSpacing * numberOfPracticeNotes;
        noteWidth = staffLineSpacing * 3;
        clefWidth = noteWidth * 2;
    }

    private void drawClef() {

        StaffClef staffClef = CreateClef();

        staffClef.measure((int) (clefWidth), visibleStaffHeight);

        // The treble clef is taller than the staff, but these parts should still be visible.
        // The bounding box (which sets the font size) is only as tall as the staff.
        setClipChildren(false);

        staffClef.layout(0, noteStaffCoordinateMap.get(PianoNote.G5), getMeasuredWidth(), noteStaffCoordinateMap.get(PianoNote.G5) + visibleStaffHeight);

        addViewInLayout(staffClef, -1, staffWrapParams, true);
    }

    private StaffClef CreateClef() {
        if (trebleClef) {
            return new StaffClef(getContext(), getResources().getString(R.string.char_treble_clef), keySignature);
        }

        else {
            return new StaffClef(getContext(), getResources().getString(R.string.char_bass_clef), keySignature);
        }
    }



    private void addNoteScroller() {

        // Create scroll view
        scrollView = new HorizontalScrollView(getContext());

        // Setting StaffView.clipChildren to false to display the whole clef resulted in notes scrolling under the clef.
        // Setting scrollView.clipChildren didn't seem to do anything. This is a workaround.
        scrollView.setPadding(-1,0,0,0);
        scrollView.setClipToPadding(true);

        LayoutParams scrollViewParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        scrollView.setLayoutParams(scrollViewParams);
//        scrollView.setBackgroundColor(Color.WHITE);
//        scrollView.setAlpha(.8f);


        // Create linear layout for scrollable elements
        noteLinearLayout = new LinearLayout(getContext());
        FrameLayout.LayoutParams noteLinearLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT);
        noteLinearLayout.setLayoutParams(noteLinearLayoutParams);
        noteLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
//        noteLinearLayout.setBackgroundColor(Color.GREEN);
//        noteLinearLayout.setAlpha(.3f);

        scrollView.addView(noteLinearLayout);

        addViewInLayout(scrollView, -1, scrollViewParams);
    }

    private void updateNoteScroller() {
        // Configure scroll size and layout
        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(getWidth() - clefWidth, MeasureSpec.EXACTLY);
        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY);
        scrollView.measure(widthMeasureSpec, heightMeasureSpec);

        scrollView.layout(clefWidth, 0, clefWidth + scrollView.getMeasuredWidth(), scrollView.getMeasuredHeight());
    }


    protected void placeNote(PianoNote note) {
        drawNote(note);
    }

    private void drawNote(PianoNote note) {
        StaffNote staffNote = new StaffNote(getContext(), keySignature, note);

        // noteStaffCoordinateMap only contains coordinates for non-accidental notes,
        // which is why we use the natural note field to determine the position.
//        LinearLayout.LayoutParams staffNoteParams = new LinearLayout.LayoutParams(noteWidth, visibleStaffHeight);
        LinearLayout.LayoutParams staffNoteParams = new LinearLayout.LayoutParams(noteWidth, visibleStaffHeight);

        staffNoteParams.setMargins(noteWidth/2, 0, noteWidth/2, 0);

        staffNote.setLayoutParams(staffNoteParams);
        staffNote.setTranslationY(noteStaffCoordinateMap.get(PianoNote.valueOfLabel(note.naturalNoteLabel)) - visibleStaffHeight);

        noteLinearLayout.addView(staffNote);

        notesOnStaff++;

    }

    protected void removeNote(PianoNote note) {
        Log.i(LOG_TAG, "Removing " + note);

        View childNoteView = getChildAt(staffLines.size() + notesOnStaff);

        removeView(childNoteView);
        notesOnStaff--;

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            drawStaffLines();
            drawClef();
            addNoteScroller();
        }

        updateNoteScroller();
    }
}


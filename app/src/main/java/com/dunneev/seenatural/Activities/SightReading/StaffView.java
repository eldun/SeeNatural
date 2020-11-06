package com.dunneev.seenatural.Activities.SightReading;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

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
    int visibleStaffHeight = 0;
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

    LayoutParams wrapParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

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

    private void addStaffLinesToView() {
        // Staff lines should always be the lowest view
//        int i = 0;
        for (StaffLine line : staffLines) {
//            addView(line);
            addViewInLayout(line, -1, wrapParams,true);
//            i++;
        }
    }

    private void drawStaffLines() {
        addStaffLinesToView();

        StaffLine childStaffLineView;

        staffLineSpacing = getHeight() / (staffLines.size());
        staffLineThickness = getHeight()/(numberOfPracticeNotes * 4);
//        int staffLineSpacing = viewHeight / (numberOfPracticeNotes);
        int yCoordinate = 0;

        staffLinePaint.setStrokeWidth(staffLineThickness);


        for (int i = 0; i < staffLines.size(); i++) {
            childStaffLineView = (StaffLine) getChildAt(i);
            childStaffLineView.setStaffLinePaint(staffLinePaint);

//            Log.i(LOG_TAG, childStaffLineView.note.toString() + " Y coordinates: " + yCoordinate + " to " + (yCoordinate + staffLineSpacing));

            childStaffLineView.layout(0, yCoordinate, getWidth(), yCoordinate+staffLineSpacing);

            // Map notes to Y coordinates on the staff
            noteStaffCoordinateMap.put(childStaffLineView.note, yCoordinate + staffLineSpacing + (staffLineThickness / 2));

            yCoordinate += staffLineSpacing;

        }

        visibleStaffHeight = staffLineSpacing * 8;
        noteWidth = staffLineSpacing * 3;
        clefWidth = noteWidth * 2;

    }

    private void addClefToView() {

        if (trebleClef) {
//            addView(new StaffClef(getContext(), getResources().getString(R.string.char_treble_clef), keySignature));
            addViewInLayout(new StaffClef(getContext(), getResources().getString(R.string.char_treble_clef), keySignature), -1, wrapParams, true);
        }

        else if (bassClef) {
//            addView(new StaffClef(getContext(), getResources().getString(R.string.char_bass_clef), keySignature));
            addViewInLayout(new StaffClef(getContext(), getResources().getString(R.string.char_bass_clef), keySignature), staffLines.size(), wrapParams, true);

        }
    }

    private void drawClef() {
        addClefToView();

        View childClefView = getChildAt(staffLines.size());

        childClefView.measure((int) (clefWidth), visibleStaffHeight);

        // The treble clef is taller than the staff, but these parts should still be visible.
        // The bounding box (which sets the font size) is only as tall as the staff.
        setClipChildren(false);

        childClefView.layout(0, noteStaffCoordinateMap.get(PianoNote.G5), getMeasuredWidth(), noteStaffCoordinateMap.get(PianoNote.G5) + visibleStaffHeight);
    }

    private void addNoteToView(PianoNote note) {
        StaffNote staffNote = new StaffNote(getContext(), keySignature, note);
//        addView(staffNote, staffLines.size() + 1 + notesOnStaff);
        addViewInLayout(staffNote, staffLines.size() + 1 + notesOnStaff, wrapParams, true);
        notesOnStaff++;

    }

    protected void placeNote(PianoNote note) {
        drawNote(note);
    }

    private void drawNote(PianoNote note) {
        try {
            addNoteToView(note);

            Log.i(LOG_TAG, "Drawing " + note);

            View childNoteView = getChildAt(staffLines.size() + notesOnStaff);
//            View childNoteView = getChildAt(staffLines.size() + 1 + visibleNotesOnStaff);


            // noteStaffCoordinateMap only contains coordinates for non-accidental notes,
            // which is why we use the natural note field to determine the position.
            childNoteView.measure(noteWidth, visibleStaffHeight);
//        int l = (clefWidth * 2) + (noteWidth) * visibleNotesOnStaff;
            int l = clefWidth + (noteWidth * 2);
            int t = noteStaffCoordinateMap.get(PianoNote.valueOfLabel(note.naturalNoteLabel)) - visibleStaffHeight;
            int r = l + noteWidth;
            int b = noteStaffCoordinateMap.get(PianoNote.valueOfLabel(note.naturalNoteLabel));

            // Temporary layout arguments. Eventually, there will be multiple notes on the staff.
            childNoteView.layout(l, t, r, b);
        }
        catch (NullPointerException e) {
            Log.e(LOG_TAG, "NullPointerException trying to draw " + note.toString() +
                    "\nat line " + e.getStackTrace()[0].getLineNumber() +
                    "\nusing natural note " + note.naturalNoteLabel);
            Log.e(LOG_TAG, e.toString());
        }
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
        }
    }
}


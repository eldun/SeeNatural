package com.dunneev.seenatural.Activities.SightReading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.dunneev.seenatural.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class StaffView extends ViewGroup {

    private static final String LOG_TAG = StaffView.class.getSimpleName();

    boolean trebleClef = true;
    boolean bassClef;
    int clefWidth;
    int staffLineSpacing;
    int visibleStaffHeight = 0;
    int noteWidth;
    protected Paint staffLinePaint;
    PianoNote lowPracticeNote;
    PianoNote highPracticeNote;
    int numberOfPracticeNotes;
    int visibleNotesOnStaff;
    ArrayList<PianoNote> practiceNotesAscending;
    ArrayList<PianoNote> practiceNotesDescending;
    ArrayList<StaffLine> staffLines;
    int staffLineThickness;

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
        addStaffLinesToView();
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
            // either the key signature or a ♯/♮/♭ symbol if the note is an accidental.
            if (note.keyColor == Color.WHITE) {
                line = new StaffLine(this.getContext(), note);
                staffLines.add(line);
            }
        }


    }

    private void addStaffLinesToView() {
        for (StaffLine line : staffLines) {
            addView(line);
        }
    }

    private void drawStaffLines() {
        StaffLine childStaffLineView;
        int childCount = getChildCount();

        staffLineSpacing = getHeight() / (staffLines.size());
        staffLineThickness = getHeight()/(numberOfPracticeNotes * 4);
//        int staffLineSpacing = viewHeight / (numberOfPracticeNotes);
        int yCoordinate = 0;

        staffLinePaint.setStrokeWidth(staffLineThickness);


        for (int i = 0; i < childCount; i++) {
            childStaffLineView = (StaffLine) getChildAt(i);
            childStaffLineView.setStaffLinePaint(staffLinePaint);

//            Log.i(LOG_TAG, childStaffLineView.note.toString() + " Y coordinates: " + yCoordinate + " to " + (yCoordinate + staffLineSpacing));

            childStaffLineView.layout(0, yCoordinate, getWidth(), yCoordinate+staffLineSpacing);

            // Map notes to Y coordinates on the staff
            noteStaffCoordinateMap.put(childStaffLineView.note, yCoordinate + staffLineSpacing + (staffLineThickness / 2));

            yCoordinate += staffLineSpacing;

        }

        visibleStaffHeight = staffLineSpacing * 8;
        noteWidth = (int) (visibleStaffHeight / 2.5);
        clefWidth = (int) (noteWidth * 1.75);

    }

    private void addClefToView() {

        if (trebleClef) {
            addView(new StaffClef(getContext(), getResources().getString(R.string.char_treble_clef)));
        }

        else if (bassClef) {
            addView(new StaffClef(getContext(), getResources().getString(R.string.char_bass_clef)));
        }
    }

    private void drawClef() {
        View childClefView = getChildAt(staffLines.size());

        childClefView.measure((int) (noteWidth * 1.75), visibleStaffHeight);

        // The treble clef is taller than the staff, but these parts should still be visible.
        // The bounding box (which sets the font size) is only as tall as the staff.
        setClipChildren(false);

        childClefView.layout(0, noteStaffCoordinateMap.get(PianoNote.G5), getMeasuredWidth(), noteStaffCoordinateMap.get(PianoNote.G5) + visibleStaffHeight);
    }

    private void drawNote(PianoNote note) {
        addView(new StaffNote(getContext(), note));

        // The child note to be drawn comes after the staff lines(staffLines.size()) and the clef(1) children.
        View childNoteView = getChildAt(staffLines.size() + 1 + visibleNotesOnStaff);


        childNoteView.measure(noteWidth, visibleStaffHeight);
//        int l = (clefWidth * 2) + (noteWidth) * visibleNotesOnStaff;
        int l = (clefWidth * 2) + (noteWidth * 2) * visibleNotesOnStaff;
        int t = noteStaffCoordinateMap.get(note) - visibleStaffHeight;
        int r = l + noteWidth;
        int b = noteStaffCoordinateMap.get(note);
        // Temporary layout arguments. Eventually, there will be multiple notes on the staff.
        childNoteView.layout(l, t, r, b);
        visibleNotesOnStaff++;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w,h,oldw,oldh);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        
        drawStaffLines();
        addClefToView();
        drawClef();
        drawNote(PianoNote.F4);
        drawNote(PianoNote.C5);
        drawNote(PianoNote.C5);

    }


}


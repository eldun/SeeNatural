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

    boolean trebleClef;
    boolean bassClef;
    int staffLineSpacing;
    int visibleStaffHeight;
    protected Paint staffLinePaint;
    PianoNote lowPracticeNote;
    PianoNote highPracticeNote;
    int numberOfPracticeNotes;
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
    }

    protected void addNoteToView(PianoNote note) {
        addView(new StaffNote(getContext(), note));
    }

    private void drawNote(PianoNote note) {
        View childNoteView = getChildAt(staffLines.size());

        // Quarter notes are never going to be super wide.
        // More importantly: they're as tall as the staff. This is used to set the font size in TextDrawable
        childNoteView.measure((int) (visibleStaffHeight / 2.5), visibleStaffHeight);

        childNoteView.layout(0, noteStaffCoordinateMap.get(note) - visibleStaffHeight, childNoteView.getMeasuredWidth(),noteStaffCoordinateMap.get(note));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w,h,oldw,oldh);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        
        drawStaffLines();
        addNoteToView(PianoNote.F4);
        drawNote(PianoNote.F4);
    }


}


package com.dunneev.seenatural.Activities.SightReading;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import com.dunneev.seenatural.Activities.Clef.ClefActivity;
import com.dunneev.seenatural.Activities.Difficulty.DifficultyActivity;
import com.dunneev.seenatural.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;


public class StaffView extends View {
    private static final String LOG_TAG = StaffView.class.getSimpleName();


    //    Create staff depending on clef selected. Add another view if both clefs are chosen
    private Paint linePaint;
    String selectedClef;
    int numberOfNotesToPractice = 35;
    PianoNote lowestNote = PianoNote.C4;
    float staffHorizontalStart;
    float staffHorizontalEnd;
    float staffVerticalStart;
    float staffViewHeight;
    int totalStaffLines;
    float lineSpacing;
    int noteScale;

    /**
     * Y coordinates of staff lines from
     */
    ArrayList<Pair<PianoNote, Object>> staffLineYCoords = new ArrayList<>();

    Resources res;



    // TODO: determine clef in constructor
    public StaffView(Context context, AttributeSet attrs) {
        super(context, attrs);
        linePaint = new Paint();
        linePaint.setStrokeWidth(5);
        linePaint.setColor(Color.RED);

        res = getContext().getResources();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.d(LOG_TAG, "onSizeChanged");
        Log.d(LOG_TAG, String.format("w: %s\nh: %h\noldw: %s\noldh: %h\n", w, h, oldw, oldh));
        super.onSizeChanged(w, h, oldw, oldh);

        staffHorizontalStart = 0;
        staffHorizontalEnd = w;
        staffVerticalStart = 0;
        staffViewHeight = h;
        lineSpacing = (float) h / numberOfNotesToPractice * 2;
        noteScale = h/numberOfNotesToPractice * 2;

        generateStaffCoordinates();
    }

    private void generateStaffCoordinates() {
        // todo: add support for bass clef
        // Generate the Y coordinates for the staff lines (both visible and invisible)
        // from C4 to... something (for now) starting from middle C at index 0.
        int spacingIterator = 0;
        float Ycoord;
        for(int i=0; i<numberOfNotesToPractice; i++){

            PianoNote note = PianoNote.valueOfNotePosition(lowestNote.absoluteNotePositionIndex + i);
            if (note.keyColor == Color.WHITE) {

                Ycoord = staffViewHeight - (lineSpacing * spacingIterator);

                staffLineYCoords.add(Pair.create(note, Ycoord));
                spacingIterator++;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas){
        Log.d(LOG_TAG, "onDraw");
        Log.d(LOG_TAG, String.format("Paint Color: %s", linePaint.getColor()));

        drawStaff(canvas);

        // Draw a note
        Resources res = getContext().getResources();
        Drawable noteImage = res.getDrawable(R.drawable.quarter_note_white);
        noteImage.setBounds(0, 0, noteImage.getIntrinsicWidth()/6, noteImage.getIntrinsicHeight()/6);
        noteImage.draw(canvas);

    }

    private void drawStaff(Canvas canvas) {
        float staffLine0 = getStaffLineYCoordinate(PianoNote.E4);
        float staffLine1 = getStaffLineYCoordinate(PianoNote.G4);
        float staffLine2 = getStaffLineYCoordinate(PianoNote.B4);
        float staffLine3 = getStaffLineYCoordinate(PianoNote.D5);
        float staffLine4 = getStaffLineYCoordinate(PianoNote.F5);


        // Draw the staff. TODO: Make the staff movable/flexible/dynamic
        canvas.drawLine(staffHorizontalStart, staffLine0, staffHorizontalEnd, staffLine0, linePaint);
        canvas.drawLine(staffHorizontalStart, staffLine1, staffHorizontalEnd, staffLine1, linePaint);
        canvas.drawLine(staffHorizontalStart, staffLine2, staffHorizontalEnd, staffLine2, linePaint);
        canvas.drawLine(staffHorizontalStart, staffLine3, staffHorizontalEnd, staffLine3, linePaint);
        canvas.drawLine(staffHorizontalStart, staffLine4, staffHorizontalEnd, staffLine4, linePaint);

//        for (Pair pair:staffLineYCoords) {
//            if (pair.first == PianoNote.C4)
//                linePaint.setColor(Color.YELLOW);
//            else if(pair.first == PianoNote.E4 ||
//                    pair.first == PianoNote.G4 ||
//                    pair.first == PianoNote.B4 ||
//                    pair.first == PianoNote.D5 ||
//                    pair.first == PianoNote.F5) {
//                linePaint.setColor(Color.GREEN);
//            }
//            else {
//                linePaint.setColor(Color.RED);
//            }
//            canvas.drawLine(staffHorizontalStart, (float)pair.second, staffHorizontalEnd, (float)pair.second, linePaint);
//        }
    }

    private float getStaffLineYCoordinate(PianoNote note) {
        for (Pair<PianoNote, Object> noteCoordPair : staffLineYCoords) {
            if (noteCoordPair.first == note) {
                return (float) noteCoordPair.second;
            }
        }
        Log.e(LOG_TAG, "note " + note + " not found in staffLineYCoords");
        return -1;
    }
}


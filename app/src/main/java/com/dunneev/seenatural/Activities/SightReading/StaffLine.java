package com.dunneev.seenatural.Activities.SightReading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class StaffLine extends View {

    private static final String LOG_TAG = StaffLine.class.getSimpleName();

    protected boolean trebleClef = true;
    protected boolean bassClef;

    protected PianoNote note;

    protected static Paint staffLinePaint;

    public Paint getStaffLinePaint() {
        return staffLinePaint;
    }

    public void setStaffLinePaint(Paint staffLinePaint) {
        StaffLine.staffLinePaint = staffLinePaint;
    }


    public StaffLine(Context context, PianoNote note) {
        super(context);
        this.note = note;

        init();
    }

    public StaffLine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        assignVisibility();
    }

    private void assignVisibility() {
        if (trebleClef) {
            if (note == PianoNote.E4 ||
                    note == PianoNote.G4 ||
                    note == PianoNote.B4 ||
                    note == PianoNote.D5 ||
                    note == PianoNote.F5) {
                setVisibility(VISIBLE);
                return;
            }
        }

        else if (bassClef) {
            if (note == PianoNote.G2 ||
                    note == PianoNote.B2 ||
                    note == PianoNote.D3 ||
                    note == PianoNote.F3 ||
                    note == PianoNote.A3) {
                setVisibility(VISIBLE);
                return;
            }
        }

        setVisibility(INVISIBLE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawLine(0, 0,getWidth(),0, staffLinePaint);
    }

    @Override
    public String toString() {
        int[] xyCoordinates = new int[2];
        getLocationInWindow(xyCoordinates);
        return note.toString() + "(" + xyCoordinates[0] + "," + xyCoordinates[1] + ")";
    }
}

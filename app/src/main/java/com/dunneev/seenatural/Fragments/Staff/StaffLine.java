package com.dunneev.seenatural.Fragments.Staff;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.dunneev.seenatural.Enums.PianoNote;

public class StaffLine extends View {

    private static final String LOG_TAG = StaffLine.class.getSimpleName();
    public static boolean hideTrebleClefLines;
    public static boolean hideBassClefLines;

    private static int desiredWidth = 100;
    private static int desiredHeight = 100;

    protected PianoNote note;

    protected Paint staffLinePaint = new Paint();

    public static int lineCount = 0;


    public Paint getStaffLinePaint() {
        return staffLinePaint;
    }

    public void setStaffLinePaint(Paint staffLinePaint) {
        this.staffLinePaint = staffLinePaint;
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


        if (!hideBassClefLines) {
            if (note == PianoNote.G2 ||
                    note == PianoNote.B2 ||
                    note == PianoNote.D3 ||
                    note == PianoNote.F3 ||
                    note == PianoNote.A3) {
                staffLinePaint.setColor(Color.GREEN);
                setVisibility(VISIBLE);
                return;
            }
        }

        if (!hideTrebleClefLines) {
            if (note == PianoNote.E4 ||
                    note == PianoNote.G4 ||
                    note == PianoNote.B4 ||
                    note == PianoNote.D5 ||
                    note == PianoNote.F5) {
                staffLinePaint.setColor(Color.RED);
                setVisibility(VISIBLE);
                return;
            }
        }

        setVisibility(INVISIBLE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        desiredHeight = heightSize / lineCount;


        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(desiredHeight, MeasureSpec.EXACTLY));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawLine(0, getMeasuredHeight()/2,getMeasuredWidth(),getMeasuredHeight()/2, staffLinePaint);
    }

    @Override
    public String toString() {
        return "StaffLine " + note.toString();
    }
}

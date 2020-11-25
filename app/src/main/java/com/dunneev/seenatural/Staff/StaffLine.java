package com.dunneev.seenatural.Staff;

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

    private static int desiredWidth = 100;
    private static int desiredHeight = 100;

    protected PianoNote note;

    protected Paint staffLinePaint = new Paint();

    protected static int staffLineCount = 0;


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
        staffLineCount++;
    }

    public static int getDesiredWidth() {
        return desiredWidth;
    }

    public static void setDesiredWidth(int desiredWidth) {
        StaffLine.desiredWidth = desiredWidth;
    }

    public static int getDesiredHeight() {
        return desiredHeight;
    }

    public static void setDesiredHeight(int desiredHeight) {
        StaffLine.desiredHeight = desiredHeight;
    }

    private void assignVisibility() {

            if (note == PianoNote.G2 ||
                    note == PianoNote.B2 ||
                    note == PianoNote.D3 ||
                    note == PianoNote.F3 ||
                    note == PianoNote.A3) {
                staffLinePaint.setColor(Color.GREEN);
                setVisibility(VISIBLE);
                return;
            }
            else if (note == PianoNote.E4 ||
                    note == PianoNote.G4 ||
                    note == PianoNote.B4 ||
                    note == PianoNote.D5 ||
                    note == PianoNote.F5) {
                staffLinePaint.setColor(Color.RED);
                setVisibility(VISIBLE);
                return;
            }

        setVisibility(INVISIBLE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        Log.i(LOG_TAG, "\nonMeasure Width: " + MeasureSpec.toString(widthMeasureSpec) +
//                "\nonMeasure Height: " + MeasureSpec.toString(heightMeasureSpec));
//
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//
//        heightSize = heightSize / staffLineCount;
//        heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
//
//        Log.i(LOG_TAG, "\nMeasured Width: " + MeasureSpec.toString(widthMeasureSpec) +
//                "\nMeasured Height: " + MeasureSpec.toString(heightMeasureSpec));
//        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);


//
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width;
        int height;
//
//
//        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        } else {
            //Be whatever you want
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(desiredHeight, heightSize);
        } else {
            //Be whatever you want
            height = desiredHeight;
        }
//

        setMeasuredDimension(width, height);
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

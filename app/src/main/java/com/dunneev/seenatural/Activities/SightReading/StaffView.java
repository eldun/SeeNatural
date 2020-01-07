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
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.dunneev.seenatural.Activities.Clef.ClefActivity;
import com.dunneev.seenatural.Activities.Difficulty.DifficultyActivity;
import com.dunneev.seenatural.R;


public class StaffView extends View {

//    Create staff depending on clef selected. Add another view if both clefs are chosen
    private Paint linePaint;
    String selectedClef;
    int staffHorizontalStart;
    int staffHorizontalEnd;
    int staffVerticalStart;
    int lineSpacing;
    int noteHeight;
    Resources res;

    private static final String LOG_TAG = StaffView.class.getSimpleName();


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
        staffHorizontalStart = 0;
        staffHorizontalEnd = w;
        staffVerticalStart = h/3;
        lineSpacing = h /15;
        noteHeight = h/3;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas){
        Log.d(LOG_TAG, "onDraw");
        Log.d(LOG_TAG, String.format("Paint Color: %s", linePaint.getColor()));

        // Draw the staff. TODO: Make the staff movable/flexible/dynamic
        canvas.drawLine(staffHorizontalStart,staffVerticalStart, staffHorizontalEnd,staffVerticalStart, linePaint);
        canvas.drawLine(staffHorizontalStart,staffVerticalStart + lineSpacing,staffHorizontalEnd,staffVerticalStart + lineSpacing, linePaint);
        canvas.drawLine(staffHorizontalStart,staffVerticalStart + (lineSpacing * 2),staffHorizontalEnd,staffVerticalStart + (lineSpacing * 2), linePaint);
        canvas.drawLine(staffHorizontalStart,staffVerticalStart + (lineSpacing * 3),staffHorizontalEnd,staffVerticalStart + (lineSpacing * 3), linePaint);
        canvas.drawLine(staffHorizontalStart,staffVerticalStart + (lineSpacing * 4),staffHorizontalEnd,staffVerticalStart + (lineSpacing * 4), linePaint);

        // Draw a note
        Resources res = getContext().getResources();
        Drawable noteImage = res.getDrawable(R.drawable.quarter_note_white);
        noteImage.setBounds(0, 0,noteImage.getIntrinsicWidth()/3, noteHeight);
        noteImage.draw(canvas);

    }



}


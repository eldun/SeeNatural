package com.dunneev.seenatural.Activities.SightReading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.dunneev.seenatural.R;
import com.dunneev.seenatural.TextDrawable;

public class StaffClef extends View {
    private static final String LOG_TAG = StaffClef.class.getSimpleName();


    private String clefChar;
    TextDrawable clefDrawable;
    private KeySignature keySignature;


    private static int desiredWidth = 500;
    private static int desiredHeight = 500;

    Rect boundsRect = new Rect();

    public StaffClef(Context context, String clefChar, KeySignature keySignature) {
        super(context);
        this.clefChar = clefChar;
        this.clefDrawable = new TextDrawable(clefChar);
        this.keySignature = keySignature;
    }

    public StaffClef(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.clefChar = getResources().getString(R.string.char_treble_clef);
        this.clefDrawable = new TextDrawable(clefChar);
        this.keySignature = KeySignature.C_MAJOR;
    }

    public static int getDesiredWidth() {
        return desiredWidth;
    }

    public static void setDesiredWidth(int desiredWidth) {
        StaffClef.desiredWidth = desiredWidth;
    }

    public static int getDesiredHeight() {
        return desiredHeight;
    }

    public static void setDesiredHeight(int desiredHeight) {
        StaffClef.desiredHeight = desiredHeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        desiredWidth = (int) (desiredHeight * clefDrawable.getAspectRatio());


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
//
//        Log.i(LOG_TAG, "\nMeasured Width: " + width +
//                "\nMeasured Height: " + height);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        boundsRect.set(0,0, getMeasuredWidth(), getMeasuredHeight());
        clefDrawable.setBounds(boundsRect);
//        //        super.onDraw(canvas);
//        Paint greenPaint = new Paint();
//        greenPaint.setColor(Color.GREEN);
//        canvas.drawCircle(0,0,50, greenPaint);
        clefDrawable.draw(canvas);
    }
}

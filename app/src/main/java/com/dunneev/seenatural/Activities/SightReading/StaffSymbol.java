package com.dunneev.seenatural.Activities.SightReading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import com.dunneev.seenatural.TextDrawable;

public class StaffSymbol extends View {

    private static final String LOG_TAG = StaffSymbol.class.getSimpleName();

    private boolean sharps;
    private boolean flats;
    private TextDrawable symbolDrawable;


    private static int desiredWidth;

    public static void setDesiredWidth(int desiredWidth) {
        StaffSymbol.desiredWidth = desiredWidth;
    }

    public static void setDesiredHeight(int desiredHeight) {
        StaffSymbol.desiredHeight = desiredHeight;
    }

    private static int desiredHeight;

    public StaffSymbol(Context context, String symbolChar) {
        super(context);
        symbolDrawable = new TextDrawable(symbolChar);
        symbolDrawable.setCenterInBounds(true);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        desiredWidth = (int) (desiredHeight * symbolDrawable.getAspectRatio());


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

        symbolDrawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
        symbolDrawable.setCenterInBounds(true);
        symbolDrawable.draw(canvas);
    }
}

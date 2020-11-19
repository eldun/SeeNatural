package com.dunneev.seenatural.Activities.SightReading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.dunneev.seenatural.R;
import com.dunneev.seenatural.TextDrawable;

import java.util.ArrayList;

public class StaffClef extends View {
    private static final String LOG_TAG = StaffClef.class.getSimpleName();

    private String clef;
    private TextDrawable clefDrawable;
    private KeySignature keySignature;
    private ArrayList<TextDrawable> symbolList;

    private static int desiredWidth = 500;
    private static int desiredHeight = 500;

    Rect boundsRect = new Rect();

    public String getClef() {
        return clef;
    }

    public StaffClef(Context context, String clef, KeySignature keySignature) {
        super(context);
        this.clef = clef;
        this.keySignature = keySignature;

        init();
    }

    public StaffClef(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        createDefaultClef();

        init();
    }

    private void createDefaultClef() {
        this.clef = getResources().getString(R.string.treble);
        this.clefDrawable = new TextDrawable(getResources().getString(R.string.char_treble_clef));
        this.keySignature = KeySignature.G_MAJOR;
    }

    private void init() {
        setClefDrawable();
        populateKeySignatureSymbols();
    }

    private void populateKeySignatureSymbols() {
        symbolList = new ArrayList();
        if (keySignature.hasSharps) {
            for (int i=0;i<keySignature.sharpCount;i++){
                symbolList.add(new TextDrawable(getResources().getString(R.string.char_sharp_sign)));
            }
        }
        
        else if (keySignature.hasFlats) {
            for (int i=0;i<keySignature.flatCount;i++){
                symbolList.add(new TextDrawable(getResources().getString(R.string.char_flat_sign)));
            }
        }
    }

    private void setClefDrawable() {
        if (clef.equals(getResources().getString(R.string.treble))) {
            this.clefDrawable = new TextDrawable(getResources().getString(R.string.char_treble_clef));
        }

        else if (clef.equals(getResources().getString(R.string.bass))) {
            this.clefDrawable = new TextDrawable(getResources().getString(R.string.char_bass_clef));
        }
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
        Paint greenPaint = new Paint();
        greenPaint.setColor(Color.GREEN);
//        canvas.drawCircle(0,0,50, greenPaint);
        clefDrawable.draw(canvas);

//        canvas.drawLine(0,0, getMeasuredWidth(), 0,greenPaint);

        symbolList.get(0).setBounds(0,0, 200, 200);
        symbolList.get(0).draw(canvas);
    }
}

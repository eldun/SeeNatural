package com.dunneev.seenatural.Activities.SightReading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.dunneev.seenatural.R;
import com.dunneev.seenatural.TextDrawable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.function.ToDoubleBiFunction;

public class StaffClef extends View {
    private static final String LOG_TAG = StaffClef.class.getSimpleName();

    private String clef;
    private TextDrawable clefDrawable;
    private KeySignature keySignature;
    private ArrayList<TextDrawable> symbolList;
    int clefWidth;
    private static int desiredWidth = 500;
    private static int desiredHeight = 500;

    Rect boundsRect = new Rect();
    private boolean isTreble;
    private boolean isBass;
    private int symbolHeight;
    private int symbolWidth;

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
        this.clefDrawable = new TextDrawable(getResources().getString(R.string.char_treble_clef), TextDrawable.positioningInBounds.DEFAULT);
        this.keySignature = KeySignature.C_SHARP_MAJOR;
    }

    private void init() {
        setClefDrawable();
        populateSymbolList();
    }

    private void populateSymbolList() {
        symbolList = new ArrayList<>();

        if (keySignature.hasSharps) {
            for (int i=0;i< keySignature.sharpCount;i++){
                symbolList.add(new TextDrawable(getResources().getString(R.string.char_sharp_symbol),
                        TextDrawable.positioningInBounds.CENTERED));
            }

        }
        else if (keySignature.hasFlats) {
            for (int i=0;i< keySignature.flatCount;i++){
                symbolList.add(new TextDrawable(getResources().getString(R.string.char_flat_symbol),
                        TextDrawable.positioningInBounds.BOTTOM));
            }
        }
        else {
            return;
        }
    }

    private void setClefDrawable() {
        if (clef.equals(getResources().getString(R.string.treble))) {
            this.clefDrawable = new TextDrawable(getResources().getString(R.string.char_treble_clef), TextDrawable.positioningInBounds.DEFAULT);
            isTreble = true;
            isBass = false;
        }

        else if (clef.equals(getResources().getString(R.string.bass))) {
            this.clefDrawable = new TextDrawable(getResources().getString(R.string.char_bass_clef), TextDrawable.positioningInBounds.TOP);
            isTreble = false;
            isBass = true;
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

        clefWidth = (int) (desiredHeight * clefDrawable.getAspectRatio());
        if (!symbolList.isEmpty()) {
            symbolWidth = desiredHeight/4;
            desiredWidth = clefWidth + (symbolWidth * (symbolList.size()));
        }
        else
            desiredWidth = clefWidth;



        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width;
        int height;


        //Measure Width
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

        drawSymbols(canvas);

    }


    // TODO: 11/23/2020 Make this prettier. More efficient. Customizable.
    private void drawSymbols(Canvas canvas) {

        if (keySignature.hasSharps)
            symbolHeight = getMeasuredHeight()/2;

        else if (keySignature.hasFlats)
            symbolHeight = getMeasuredHeight();

        int staffLineSpacing = getMeasuredHeight() / 4;
        ArrayList<Integer> staffLineCoords = new ArrayList<Integer>();
        staffLineCoords.add(0);
        staffLineCoords.add(staffLineSpacing);
        staffLineCoords.add(staffLineSpacing * 2);
        staffLineCoords.add(staffLineSpacing * 3);
        staffLineCoords.add(staffLineSpacing * 4);


        Rect boundsRect = new Rect();
        int boundsLeft = 0;
        int boundsTop = 0;
        int boundsRight = 100;
        int boundsBottom = 100;

        for (int i = 0; i < symbolList.size(); i++) {
            TextDrawable symbol = symbolList.get(i);

            boundsLeft = clefWidth + (i * symbolWidth);
            boundsTop = 0;
            boundsRight = (boundsLeft + symbolWidth);
            if (keySignature.hasSharps) {

                switch (i) {

                    // F♯
                    case 0:
                        boundsTop = (-staffLineSpacing / 2);
                        break;

                    // C♯
                    case 1:
                        boundsTop = staffLineCoords.get(1);
                        break;
                    // G♯
                    case 2:
                        boundsTop = -staffLineSpacing;
                        break;
                    // D♯
                    case 3:
                        boundsTop = staffLineSpacing / 2;
                        break;
                    // A♯
                    case 4:
                        boundsTop = staffLineCoords.get(2);
                        break;
                    // E♯
                    case 5:
                        boundsTop = 0;
                        break;
                    // B♯
                    case 6:
                        boundsTop = staffLineCoords.get(1) + (staffLineSpacing / 2);
                        break;

                }

                boundsBottom = boundsTop + symbolHeight;

            }

            else if (keySignature.hasFlats) {
                switch (i) {

                    // B♭
                    case 0:
                        boundsBottom = staffLineCoords.get(2) + (staffLineSpacing / 2);
                        break;

                    // E♭
                    case 1:
                        boundsBottom = staffLineCoords.get(1);
                        break;
                    // A♭
                    case 2:
                        boundsBottom = staffLineCoords.get(3);
                        break;
                    // D♭
                    case 3:
                        boundsBottom = staffLineCoords.get(1) + (staffLineSpacing / 2);
                        break;
                    // G♭
                    case 4:
                        boundsBottom = staffLineCoords.get(3) + (staffLineSpacing / 2);
                        break;
                    // C♭
                    case 5:
                        boundsBottom = staffLineCoords.get(2);
                        break;
                    // F♭
                    case 6:
                        boundsBottom = staffLineCoords.get(4);
                        break;

                }

                boundsTop = boundsBottom - symbolHeight;
            }

            // Notes on the bass staff are exactly the same as on treble, but shifted down one whole step.
            if (isBass) {
                boundsTop += staffLineSpacing;
                boundsBottom += staffLineSpacing;
            }



            boundsRect.set(boundsLeft, boundsTop, boundsRight, boundsBottom);
            symbol.setBounds(boundsRect);
            symbol.draw(canvas);
        }
    }
}

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

    private String clef;
    TextDrawable clefDrawable;
    private KeySignature keySignature;

    Rect boundsRect = new Rect();

    public StaffClef(Context context, String clef, KeySignature keySignature) {
        super(context);
        this.clef = clef;
        this.clefDrawable = new TextDrawable(clef);
        this.keySignature = keySignature;
    }

    public StaffClef(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.clef = getResources().getString(R.string.char_treble_clef);
        this.clefDrawable = new TextDrawable(clef);
        this.keySignature = KeySignature.C_MAJOR;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        boundsRect.set(0,0, getMeasuredWidth(), getMeasuredHeight());
        clefDrawable.setBounds(boundsRect);
        //        super.onDraw(canvas);
//        Paint greenPaint = new Paint();
//        greenPaint.setColor(Color.GREEN);
//        canvas.drawCircle(100,100,50, greenPaint);
        clefDrawable.draw(canvas);
    }
}

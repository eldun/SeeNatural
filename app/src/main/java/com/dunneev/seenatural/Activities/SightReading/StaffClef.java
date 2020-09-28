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
    Rect boundsRect = new Rect();

    public StaffClef(Context context, String clef) {
        super(context);
        this.clef = clef;
    }

    public StaffClef(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        TextDrawable textDrawable = new TextDrawable(clef);
        boundsRect.set(0,0, getMeasuredWidth(), getMeasuredHeight());
        textDrawable.setBounds(boundsRect);
        //        super.onDraw(canvas);
//        Paint greenPaint = new Paint();
//        greenPaint.setColor(Color.GREEN);
//        canvas.drawCircle(100,100,50, greenPaint);
        textDrawable.draw(canvas);

    }
}

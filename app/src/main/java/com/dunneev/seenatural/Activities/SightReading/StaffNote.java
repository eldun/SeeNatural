package com.dunneev.seenatural.Activities.SightReading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.dunneev.seenatural.R;
import com.dunneev.seenatural.TextDrawable;

public class StaffNote extends View {

    public PianoNote note;
    Rect boundsRect = new Rect();
    TextDrawable textDrawable;

    public StaffNote(Context context, PianoNote note) {
        super(context);
        this.note = note;
        init();
    }

    public StaffNote(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
//        setText(R.string.char_treble_clef);
//        setVisibility(VISIBLE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        TextDrawable textDrawable = new TextDrawable(getResources(), getResources().getString(R.string.char_quarter_note));
        boundsRect.set(0,0, getMeasuredWidth(), getMeasuredHeight());
        textDrawable.setBounds(boundsRect);
        //        super.onDraw(canvas);
//        Paint greenPaint = new Paint();
//        greenPaint.setColor(Color.GREEN);
//        canvas.drawCircle(100,100,50, greenPaint);
        textDrawable.draw(canvas);

    }
}

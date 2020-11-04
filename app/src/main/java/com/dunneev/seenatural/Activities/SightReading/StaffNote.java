package com.dunneev.seenatural.Activities.SightReading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.dunneev.seenatural.R;
import com.dunneev.seenatural.TextDrawable;

import org.w3c.dom.Text;

public class StaffNote extends View {

    private static final String LOG_TAG = StaffNote.class.getSimpleName();

    private KeySignature keySignature;
    public PianoNote note;

    private boolean isAccidental;
    private String accidentalSymbol;
    private TextDrawable accidentalDrawable;

    private TextDrawable noteDrawable;

    private Rect noteBoundsRect = new Rect();

    // todo: draw a ledger line when necessary
    public StaffNote(Context context, KeySignature keySignature, PianoNote note) {
        super(context);
        this.note = note;
        this.keySignature = keySignature;
        isAccidental = checkIfAccidental(note);

        if (isAccidental) {
            accidentalDrawable = new TextDrawable(accidentalSymbol);
        }

        noteDrawable = new TextDrawable(getResources().getString(R.string.char_quarter_note));

    }

    protected boolean checkIfAccidental(PianoNote note) {
        if (keySignature.containsNote(note))
            return false;

        else {
            if (note.label.contains(getResources().getString(R.string.char_sharp_sign))) {
                accidentalSymbol = getResources().getString(R.string.char_sharp_sign);
            }

            else if (note.label.contains(getResources().getString(R.string.char_flat_sign)))
                accidentalSymbol = getResources().getString(R.string.char_flat_sign);

            else
                accidentalSymbol = getResources().getString(R.string.char_natural_sign);
        }

        return true;
    }

    public StaffNote(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        noteBoundsRect.set(0,0, getMeasuredWidth(), getMeasuredHeight());

        if (isAccidental) {
            accidentalDrawable.setBounds(noteBoundsRect.left - noteBoundsRect.width(), noteBoundsRect.top, noteBoundsRect.left, noteBoundsRect.bottom);

            accidentalDrawable.draw(canvas);
        }


        noteDrawable.setBounds(noteBoundsRect);

        //        super.onDraw(canvas);
        //        Paint greenPaint = new Paint();
        //        greenPaint.setColor(Color.GREEN);
        //        canvas.drawCircle(100,100,50, greenPaint);

        Log.i(LOG_TAG, "isAccidental: " + isAccidental);
        noteDrawable.draw(canvas);

    }
}

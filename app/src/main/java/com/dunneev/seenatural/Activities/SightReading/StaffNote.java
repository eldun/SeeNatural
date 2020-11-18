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

import org.w3c.dom.Text;

public class StaffNote extends View {

    private static final String LOG_TAG = StaffNote.class.getSimpleName();

    private KeySignature keySignature;
    private PianoNote note;

    private boolean isAccidental;
    private String accidentalSymbol;
    private TextDrawable noteDrawable;

    private Rect noteBoundsRect = new Rect();

    // todo: draw a ledger line when necessary
    public StaffNote(Context context, KeySignature keySignature, PianoNote note) {
        super(context);
        this.note = note;
        this.keySignature = keySignature;
        init();
    }

    public StaffNote(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.note = PianoNote.B_FLAT_4;
        this.keySignature = KeySignature.C_MAJOR;
        init();
    }

    private void init() {

        isAccidental = checkIfAccidental(note);

        if (isAccidental) {
            noteDrawable = new TextDrawable(accidentalSymbol + getResources().getString(R.string.char_quarter_note));
        }

        else {
            noteDrawable = new TextDrawable(getResources().getString(R.string.char_quarter_note));
        }
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        // Default values just in case something goes wrong
        int desiredWidth = 100;
        int desiredHeight = 100;

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width;
        int height;

        // Staff notes should always be passed visibleStaffHeight.EXACTLY for height.
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

        // Width is be dependent on the length of text, type of note, and height of text.
        desiredWidth = (int) (height * noteDrawable.getAspectRatio());

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

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        noteBoundsRect.set(0,0, getMeasuredWidth(), getMeasuredHeight());
//        Paint blackPaint = new Paint();
//        blackPaint.setColor(Color.BLACK);
//        canvas.drawRect(0,0, getMeasuredWidth(), getMeasuredHeight(), blackPaint);

        noteDrawable.setBounds(noteBoundsRect);
        noteDrawable.draw(canvas);

    }
}

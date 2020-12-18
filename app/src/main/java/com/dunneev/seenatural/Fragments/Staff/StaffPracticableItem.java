package com.dunneev.seenatural.Fragments.Staff;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.dunneev.seenatural.Enums.KeySignature;
import com.dunneev.seenatural.Enums.PianoNote;
import com.dunneev.seenatural.R;
import com.dunneev.seenatural.Utilities.TextDrawable;

import java.util.ArrayList;
import java.util.Collection;

public class StaffPracticableItem extends ViewGroup {

    private static final String LOG_TAG = StaffPracticableItem.class.getSimpleName();

    private KeySignature keySignature;
    private ArrayList<PianoNote> notes = new ArrayList<>();

    public StaffPracticableItem(Context context, KeySignature keySignature, PianoNote note) {
        super(context);
        this.keySignature = keySignature;
        this.notes.add(note);
        init();
    }

    public StaffPracticableItem(Context context, KeySignature keySignature, ArrayList<PianoNote> notes) {
        super(context);
        this.keySignature = keySignature;
        this.notes = notes;
        init();
    }

    public void init() {

        setClipChildren(false);

        for (PianoNote pianoNote:notes) {
            StaffNote staffNote = new StaffNote(getContext(), keySignature, pianoNote);
            LayoutParams staffNoteParams = new LayoutParams(LayoutParams.WRAP_CONTENT, StaffView.visibleStaffHeight);
            staffNote.setLayoutParams(staffNoteParams);

            this.addView(staffNote);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        // Default value, just in case something goes wrong
        int desiredWidth = 100;


        // We wish to be as wide as the widest child view
        final int childCount = getChildCount();
        for (int i=0; i<childCount; i++) {
            desiredWidth = Math.max(desiredWidth, getChildAt(i).getMeasuredWidth());
        }

        int width;

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


        setMeasuredDimension(width, heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.i(LOG_TAG, "onLayout");
        final int childCount = getChildCount();

        if (childCount == 0) {
            return;
        }


        for (int i = 0; i < childCount; i++) {
            StaffNote note = (StaffNote) getChildAt(i);
//            note.setBackgroundColor(Color.BLACK);
            note.layout(0, 0, note.getMeasuredWidth(), note.getMeasuredHeight());
            note.setTranslationY(StaffView.noteStaffCoordinateMap.get(PianoNote.valueOfLabel(note.note.naturalNoteLabel)) - note.getMeasuredHeight() + StaffView.staffLineSpacing);

        }
    }


    private class StaffNote extends View {

        private final String LOG_TAG = StaffNote.class.getSimpleName();

        private KeySignature keySignature;
        public PianoNote note;
        public int color = Color.WHITE;
        public boolean isIncorrect;

        private boolean isAccidental;
        private String accidentalSymbol;
        private TextDrawable noteDrawable;

        private Rect noteBoundsRect = new Rect();

        private int desiredWidth = 500;
        private int desiredHeight = 500;


        public int getDesiredWidth() {
            return desiredWidth;
        }

        public void setDesiredWidth(int desiredWidth) {
            this.desiredWidth = desiredWidth;
        }

        public int getDesiredHeight() {
            return desiredHeight;
        }

        public void setDesiredHeight(int desiredHeight) {
            this.desiredHeight = desiredHeight;
        }


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



            if (PianoNote.isAccidental(note, keySignature)) {
                noteDrawable = new TextDrawable(note.symbol + getResources().getString(R.string.char_quarter_note), TextDrawable.PositioningInBounds.DEFAULT);
            }

            else {
                noteDrawable = new TextDrawable(getResources().getString(R.string.char_quarter_note), TextDrawable.PositioningInBounds.DEFAULT);
            }
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

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

            noteDrawable.setBounds(noteBoundsRect);
            noteDrawable.setColor(color);
            noteDrawable.draw(canvas);

        }

    }
}

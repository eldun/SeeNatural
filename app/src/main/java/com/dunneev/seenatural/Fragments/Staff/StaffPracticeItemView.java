package com.dunneev.seenatural.Fragments.Staff;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.dunneev.seenatural.Enums.PianoNote;
import com.dunneev.seenatural.R;
import com.dunneev.seenatural.Utilities.TextDrawable;

import java.util.Iterator;

public class StaffPracticeItemView extends ViewGroup {
    private static final String LOG_TAG = StaffPracticeItemView.class.getSimpleName();


    LayoutParams noteParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);



    public StaffPracticeItemView(Context context, StaffPracticeItem item) {
        super(context);

        setClipChildren(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        measureChildren(widthMeasureSpec, StaffView.visibleStaffHeight);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);




        // We wish to be as wide as the widest child view
        int desiredWidth = 0;

        final int childCount = getChildCount();
        for (int i=0; i<childCount; i++) {
            desiredWidth = Math.max(desiredWidth, getChildAt(i).getMeasuredWidth());
        }



        setMeasuredDimension(desiredWidth, heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.i(LOG_TAG, "onLayout");
        final int childCount = getChildCount();

        // todo: invert note if conditions are right
        for (int i = 0; i < childCount; i++) {
            StaffNoteView note = (StaffNoteView) getChildAt(i);
//            note.setBackgroundColor(Color.BLACK);
//            note.layout(0, 0, note.getMeasuredWidth(), note.getMeasuredHeight());
            note.setTranslationY(StaffView.noteStaffCoordinateMap.get(PianoNote.valueOfLabel(note.staffNote.getNote().naturalNoteLabel)) - note.getMeasuredHeight() + StaffView.staffLineSpacing);

        }
    }

    public void decorate(StaffPracticeItem item) {
        removeAllViews();

        StaffPracticeItem.StaffNote staffNote = null;
        StaffNoteView staffNoteView;
        int index = 0;

        // seems wobbly
        Iterator<StaffPracticeItem.StaffNote> noteIterator = item.iterator();
        while (noteIterator.hasNext()) {
            staffNote = noteIterator.next();

            staffNoteView = new StaffNoteView(getContext(), staffNote);
            staffNoteView.setColor(staffNote.getColor());


            // Draw original note on bottom, incorrect note on top
            if (staffNote.state == StaffPracticeItem.NoteState.NEUTRAL)
                addView(staffNoteView, 0, noteParams);

            else
                addView(staffNoteView, noteParams);

        }

    }


    private class StaffNoteView extends View {

        private final String LOG_TAG = StaffNoteView.class.getSimpleName();

        public StaffPracticeItem.StaffNote staffNote;
        private int color = Color.WHITE;

        private TextDrawable accidentalDrawable;
        private TextDrawable noteDrawable;

        private int noteWidth = 0;
        private int accidentalWidth = 0;

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

        public void setColor(int color) {
            this.color = color;
        }

        // todo: draw a ledger line when necessary
        public StaffNoteView(Context context, StaffPracticeItem.StaffNote note) {
            super(context);
            this.staffNote = note;
            init();
        }

//        public StaffNoteView(Context context, @Nullable AttributeSet attrs) {
//            super(context, attrs);
//            this.note = PianoNote.B_FLAT_4;
//            this.keySignature = KeySignature.C_MAJOR;
//            init();
//        }

        private void init() {


            accidentalDrawable = new TextDrawable(staffNote.getNote().symbol, TextDrawable.PositioningInBounds.DEFAULT);
            noteDrawable = new TextDrawable(getResources().getString(R.string.char_quarter_note), TextDrawable.PositioningInBounds.DEFAULT);

//            if (PianoNote.isAccidental(note, keySignature)) {
//                noteDrawable = new TextDrawable(note.symbol + getResources().getString(R.string.char_quarter_note), TextDrawable.PositioningInBounds.DEFAULT);
//            }
//
//            else {
//                noteDrawable = new TextDrawable(getResources().getString(R.string.char_quarter_note), TextDrawable.PositioningInBounds.DEFAULT);
//            }
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

            int widthSize = MeasureSpec.getSize(widthMeasureSpec);
            int widthMode = MeasureSpec.getMode(widthMeasureSpec);
            int heightSize = MeasureSpec.getSize(heightMeasureSpec);
            int heightMode = MeasureSpec.getMode(heightMeasureSpec);

            int width;
            int height = heightSize;


            // This is stupid, but I'm doing it.
            // When overlaying StaffNotes with incorrect notes, accidentals of the same position
            // on the staff would not line up (the symbol would be on top of the note.)
            // I'll use these values in onDraw().
            accidentalWidth = (int) (height * accidentalDrawable.getAspectRatio());
            noteWidth = (int) (height * noteDrawable.getAspectRatio());

            // Width is dependent on the length of text, type of note, and height of text.
            width = accidentalWidth + noteWidth;

            setMeasuredDimension(width, height);
        }


        @Override
        protected void onDraw(Canvas canvas) {

            // Draw the note starting with the accidental, to allow room for accidental symbols
            // (Mainly to prevent them getting covered by the staff clef)
            if (staffNote.isAccidental) {
//                accidentalDrawable.setBounds(-accidentalWidth, 0, 0, getMeasuredHeight());
                accidentalDrawable.setBounds(0, 0, accidentalWidth, this.getMeasuredHeight());
                accidentalDrawable.setColor(color);
                accidentalDrawable.draw(canvas);
            }

//            noteDrawable.setBounds(0,0,noteWidth,getMeasuredHeight());
            noteDrawable.setBounds(accidentalWidth, 0, accidentalWidth + noteWidth, this.getMeasuredHeight());
            noteDrawable.setColor(color);
            noteDrawable.draw(canvas);

        }

        @Override
        public String toString() {
            return this.staffNote + ", " +
                    this.getLeft()  + ", " +
                    this.getTop() + ", " +
                    this.getRight() + ", " +
                    this.getBottom();
        }
    }
}

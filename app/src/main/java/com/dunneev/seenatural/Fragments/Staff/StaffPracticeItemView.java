package com.dunneev.seenatural.Fragments.Staff;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.RotateDrawable;
import android.text.style.TtsSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.dunneev.seenatural.Enums.PianoNote;
import com.dunneev.seenatural.R;
import com.dunneev.seenatural.Utilities.TextDrawable;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.SplittableRandom;

public class StaffPracticeItemView extends ViewGroup {
    private static final String LOG_TAG = StaffPracticeItemView.class.getSimpleName();


    LayoutParams noteParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

    StaffPracticeItem item;
    Map noteCoordinateMap;



    public StaffPracticeItemView(Context context, StaffPracticeItem item, Map noteCoordinateMap) {
        super(context);

        this.item = item;
        this.noteCoordinateMap = noteCoordinateMap;

        setClipChildren(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        measureChildren(widthMeasureSpec, Math.round(StaffView.visibleStaffHeight));

        // Set ledger lines to the width of the noteDrawable of StaffNoteView
        StaffNoteView staffNoteView = (StaffNoteView) getChildAt(0);


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

        float accidentalWidth = 0;
        float noteWidth = 0;
        final int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getClass() == StaffNoteView.class) {
                accidentalWidth = ((StaffNoteView) child).accidentalWidth;
                noteWidth = ((StaffNoteView) child).noteWidth;

                // All staff note coordinates are stored in noteCoordinate map, even if off screen
                child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
                int yCoord = Math.round((Float) noteCoordinateMap.get(PianoNote.valueOfLabel(((StaffNoteView) child).staffNote.getNote().naturalNoteLabel)));
                child.setTranslationY(yCoord - StaffView.visibleStaffHeight + StaffView.staffLineSpacing + 2);

            }

            else if (getChildAt(i).getClass() == GuidingLedgerLineView.class) {
//                child.setBackgroundColor(Color.WHITE);
                child.layout((Math.round(accidentalWidth)),0, Math.round(accidentalWidth + noteWidth),2);
                int yCoord = Math.round((Float) noteCoordinateMap.get(PianoNote.valueOfLabel(((GuidingLedgerLineView) child).note.naturalNoteLabel)));
                child.setTranslationY(yCoord);
            }
        }
    }



    public void decorate(StaffPracticeItem item) {
        removeAllViews();

        StaffPracticeItem.StaffNote staffNote = null;
        StaffNoteView staffNoteView;
        int index = 0;

        // Draw all notes
        // seems wobbly
        Iterator<StaffPracticeItem.StaffNote> noteIterator = item.iterator();
        while (noteIterator.hasNext()) {
            staffNote = noteIterator.next();

            staffNoteView = new StaffNoteView(getContext(), staffNote);
            staffNoteView.setColor(staffNote.getColor());


            // Draw original note on bottom, incorrect note on top
            if (staffNote.state == StaffPracticeItem.StaffNote.State.NEUTRAL)
                addView(staffNoteView, 0, noteParams);

            else
                addView(staffNoteView, noteParams);

        }

        // Add necessary ledger lines to item
        for (PianoNote ledgerLineNote:item.ledgerLineNotes) {
            addView(new GuidingLedgerLineView(getContext(), ledgerLineNote));
        }

    }


    private class StaffNoteView extends View {

        private final String LOG_TAG = StaffNoteView.class.getSimpleName();

        public StaffPracticeItem.StaffNote staffNote;
        private int color = Color.WHITE;

        private TextDrawable accidentalDrawable;
        private TextDrawable noteDrawable;
        private boolean isInverted;

        private float noteWidth = 0;
        private float accidentalWidth = 0;

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

        public float getNoteWidth() {
            return noteWidth;
        }

        public void setColor(int color) {
            this.color = color;
        }

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

            isInverted = false;

            // Note is at or above middle line of treble clef
            if (staffNote.getNote().getNaturalNote().compareTo(PianoNote.B4) >= 0){
                isInverted = true;
            }

            // Note is C4, switch between inverted and non-inverted to simulate left/right hand
            if (staffNote.getNote().getNaturalNote().equals(PianoNote.C4)){
                isInverted = new Random().nextBoolean();
//                Log.i(LOG_TAG, String.valueOf(isInverted));
            }
            // Note is at or below middle line of bass clef
            if (staffNote.getNote().getNaturalNote().compareTo(PianoNote.D3) <= 0){
                isInverted = true;
            }



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
            width = (int) (accidentalWidth + noteWidth);

            setMeasuredDimension(width, height);
        }


        @Override
        protected void onDraw(Canvas canvas) {

//            canvas.drawRect(0,0,this.getMeasuredWidth(), this.getMeasuredHeight(), noteDrawable.getPaint());


            // Draw the note starting with the accidental, to allow room for accidental symbols
            // (Mainly to prevent them getting covered by the staff clef)
            if (staffNote.isAccidental) {
//                accidentalDrawable.setBounds(-accidentalWidth, 0, 0, getMeasuredHeight());
                accidentalDrawable.setBounds(0, 0, Math.round(accidentalWidth), this.getMeasuredHeight());
                accidentalDrawable.setColor(color);
                accidentalDrawable.draw(canvas);
            }

//            noteDrawable.setBounds(0,0,noteWidth,getMeasuredHeight());
            noteDrawable.setBounds(Math.round(accidentalWidth), 0, Math.round(accidentalWidth + noteWidth), this.getMeasuredHeight());
            noteDrawable.setColor(color);

            if (isInverted) {
                canvas.save();
                canvas.rotate(180, accidentalWidth + (noteWidth/2.0f), (7.0f / 8.0f) * this.getMeasuredHeight());
                noteDrawable.draw(canvas);
                canvas.restore();
            }

            else {
                noteDrawable.draw(canvas);
            }

            if (staffNote.gudingLedgerLine == StaffPracticeItem.StaffNote.GuidingLedgerLine.TANGENTIAL) {
                // note height is always 8 spaces
//                float tangentialLineCoords = (float) ((6.0/8.0)*this.getMeasuredHeight());
//                canvas.drawLine(accidentalWidth, tangentialLineCoords, accidentalWidth + noteWidth, tangentialLineCoords, noteDrawable.getPaint());
            }

            else if (staffNote.gudingLedgerLine == StaffPracticeItem.StaffNote.GuidingLedgerLine.THROUGH) {
                // note height is always 8 spaces
//                float throughLineCoords = (float) ((7.0/8.0)*this.getMeasuredHeight());
//                noteDrawable.getPaint().setStrokeWidth(2);
//
//                canvas.drawLine(accidentalWidth, throughLineCoords, accidentalWidth + noteWidth, throughLineCoords, noteDrawable.getPaint());
            }

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

    private class GuidingLedgerLineView extends View {

        private Paint paint;
        private PianoNote note;

        public GuidingLedgerLineView(Context context, PianoNote note) {
            super(context);
            paint = new Paint();
            paint.setColor(Color.WHITE);
            this.note = note;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawLine(0,this.getHeight(),this.getWidth(),this.getHeight(), paint);
        }
    }
}

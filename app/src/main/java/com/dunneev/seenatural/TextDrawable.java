package com.dunneev.seenatural;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;

public class TextDrawable extends Drawable {

    private static final String LOG_TAG = TextDrawable.class.getSimpleName();

    private static int color = Color.WHITE;
    private static final int DEFAULT_TEXTSIZE = 100;
    private Paint textPaint;
    private CharSequence text;
    private int intrinsicWidth;
    private int intrinsicHeight;
    private float aspectRatio;

    public TextDrawable(CharSequence text) {
        this.text = text;
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(color);
        textPaint.setTextAlign(Paint.Align.LEFT);
        intrinsicWidth = (int) (textPaint.measureText(text, 0, text.length()) + 1);
        intrinsicHeight = textPaint.getFontMetricsInt(null);
        aspectRatio = (float)intrinsicWidth/intrinsicHeight;
    }
    @Override
    public void draw(Canvas canvas) {
        Rect bounds = getBounds();

//         Just testing to see the bounds are correct
        Paint boundsPaint = new Paint();
        boundsPaint.setColor(Color.BLACK);
        boundsPaint.setAlpha(70);
        canvas.drawRect(bounds, boundsPaint);


        textPaint.setTextSize(bounds.height());

        canvas.drawText(text, 0, text.length(),
                bounds.left, bounds.bottom, textPaint);

    }

    public float getAspectRatio() {
        return aspectRatio;
    }
    @Override
    public int getOpacity() {
        return textPaint.getAlpha();
    }
    @Override
    public int getIntrinsicWidth() {
        return intrinsicWidth;
    }
    @Override
    public int getIntrinsicHeight() {
        return intrinsicHeight;
    }
    @Override
    public void setAlpha(int alpha) {
        textPaint.setAlpha(alpha);
    }
    @Override
    public void setColorFilter(ColorFilter filter) {
        textPaint.setColorFilter(filter);
    }
}

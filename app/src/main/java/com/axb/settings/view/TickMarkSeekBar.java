package com.axb.settings.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatSeekBar;

/* loaded from: classes.dex */
public class TickMarkSeekBar extends AppCompatSeekBar {
    private boolean isShowTopOfThumb;
    private int mRadius;
    private Paint mRectPaint;
    private int mRulerColor;
    private int mRulerCount;

    public TickMarkSeekBar(Context context) {
        super(context);
        this.mRadius = 15;
        this.mRulerCount = 3;
        this.mRulerColor = Color.rgb(88, 171, 246);
        this.isShowTopOfThumb = false;
        init();
    }

    public TickMarkSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRadius = 15;
        this.mRulerCount = 3;
        this.mRulerColor = Color.rgb(88, 171, 246);
        this.isShowTopOfThumb = false;
        init();
    }

    public TickMarkSeekBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mRadius = 15;
        this.mRulerCount = 3;
        this.mRulerColor = Color.rgb(88, 171, 246);
        this.isShowTopOfThumb = false;
        init();
    }

    private void init() {
        Paint paint = new Paint();
        this.mRectPaint = paint;
        paint.setColor(this.mRulerColor);
        this.mRectPaint.setStrokeWidth(3.0f);
        this.mRectPaint.setStyle(Paint.Style.FILL);
        if (Build.VERSION.SDK_INT >= 21) {
            setSplitTrack(false);
        }
    }

    @Override // android.widget.AbsSeekBar, android.widget.ProgressBar, android.view.View
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getWidth() > 0 && this.mRulerCount > 0) {
            int width = ((getWidth() - getPaddingLeft()) - getPaddingRight()) / this.mRulerCount;
            int height = getHeight() / 2;
            Rect bounds = getThumb() != null ? getThumb().getBounds() : null;
            for (int i = 0; i <= this.mRulerCount; i++) {
                int paddingLeft = (i * width) + getPaddingLeft();
                if (i == 0) {
                    paddingLeft += this.mRadius;
                }
                if (i == this.mRulerCount) {
                    paddingLeft -= this.mRadius - 5;
                }
                if (this.isShowTopOfThumb || bounds == null || paddingLeft - getPaddingLeft() <= bounds.left || paddingLeft - getPaddingLeft() >= bounds.right) {
                    canvas.drawCircle(paddingLeft, height, this.mRadius, this.mRectPaint);
                }
            }
        }
    }

    public void setRulerCount(int i) {
        this.mRulerCount = i;
        requestLayout();
    }

    public void setRadius(int i) {
        this.mRadius = i;
        requestLayout();
    }

    public void setRulerColor(int i) {
        this.mRulerColor = i;
        Paint paint = this.mRectPaint;
        if (paint != null) {
            paint.setColor(i);
            requestLayout();
        }
    }

    public void setShowTopOfThumb(boolean z) {
        this.isShowTopOfThumb = z;
        requestLayout();
    }
}

package com.aicp.extras.preference;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class FrameView extends View {

    private Paint mFramePaint;
    private int mColor;
    private Rect mClipBounds = new Rect();
    private Rect mOutline = new Rect();

    public FrameView(Context context, int color) {
        super(context);
        mColor = color;
    }

    private Paint getFramePaint() {
        if (mFramePaint == null) {
            mFramePaint = new Paint();
            mFramePaint.setColor(mColor);
            mFramePaint.setStyle(Paint.Style.STROKE);
            mFramePaint.setStrokeWidth(3);
        }
        return mFramePaint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.getClipBounds(mClipBounds) ;
        mOutline.left = 1;
        mOutline.top = 1;
        mOutline.bottom = mClipBounds.bottom-1;
        mOutline.right = mClipBounds.bottom-1;
        canvas.drawRect(mOutline, getFramePaint()) ;
    }
}

package com.example.quickindex.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by haha on 2016/9/3.
 */
public class QuickIndexBar extends View {

    private String[] indexArr = {"A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z"};
    private Paint mPaint;
    private int mWidth;
    private float mCellHeight;

    public QuickIndexBar(Context context) {
        super(context);
        init();
    }

    public QuickIndexBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public QuickIndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(28);
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mCellHeight = getMeasuredHeight() * 1f / indexArr.length;

    }

    private int lastIndex = -1;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < indexArr.length; i++) {
            int x = mWidth / 2;
            int y = (int) (mCellHeight / 2 + getTextHeight(indexArr[i]) / 2 + i * mCellHeight);
            mPaint.setColor(lastIndex == i?Color.BLACK:Color.WHITE);
            canvas.drawText(indexArr[i], x, y, mPaint);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                int index = (int) (y / mCellHeight);
                if (lastIndex != index) {
                    if (index >= 0 && index < indexArr.length) {
                        if (mListener != null) {
                            mListener.onTouchLetter(indexArr[index]);
                        }
                    }
                }
                lastIndex = index;
                break;
            case MotionEvent.ACTION_UP:
                lastIndex = -1;
                break;
        }
        invalidate();
        return true;


    }

    private int getTextHeight(String s) {
        Rect bounds = new Rect();
        mPaint.getTextBounds(s, 0, s.length(), bounds);
        return bounds.height();
    }

    private OnTouchLetterListener mListener;

    public void setOnTouchLetterListener(OnTouchLetterListener listener) {
        this.mListener = listener;
    }

    public interface OnTouchLetterListener {
         void onTouchLetter(String letter);
    }


}

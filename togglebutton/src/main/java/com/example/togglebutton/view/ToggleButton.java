package com.example.togglebutton.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.togglebutton.R;

/**
 * Created by haha on 2016/9/4.
 */
public class ToggleButton extends View  implements View.OnClickListener{

    private Bitmap mSwitch_bg;
    private Bitmap mSlide_button;
    private Paint mPaint;
    private int mDownX;

    public ToggleButton(Context context) {
        super(context);
        init();
    }

    public ToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mSwitch_bg = BitmapFactory.decodeResource(getResources(), R.mipmap.switch_background);
        mSlide_button = BitmapFactory.decodeResource(getResources(), R.mipmap.slide_button);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        setOnClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mSwitch_bg.getWidth(),mSwitch_bg.getHeight());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    private float slide_left;
    private  boolean currentState = false;
    private boolean isDrag = false;
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mSwitch_bg,0,0,mPaint);
        canvas.drawBitmap(mSlide_button,slide_left,0,mPaint);
    }

    @Override
    public void onClick(View v) {
        if (!isDrag) {
            currentState = !currentState;
            flushState();
        }
    }

    private void flushState() {
        if (currentState){
            slide_left = mSwitch_bg.getWidth() - mSlide_button.getWidth();
        }else {
            slide_left = 0;
        }

        flushView();
    }

    private void flushView() {

        int maxLeft = mSwitch_bg.getWidth() - mSlide_button.getWidth();
        slide_left= slide_left > 0? slide_left:0;
        slide_left =slide_left >maxLeft?maxLeft:slide_left;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case  MotionEvent.ACTION_DOWN:
                mDownX = (int) event.getX();
                isDrag =false;
                break;
            case MotionEvent.ACTION_MOVE:

                if (Math.abs(event.getY() - mDownX) > 5){
                    isDrag = true;
                }
                int moveX = (int) event.getX();
                int dx = moveX - mDownX;
                slide_left = slide_left+dx;
                mDownX = moveX;
                break;
            case MotionEvent.ACTION_UP:
                if (isDrag) {
                    int maxLeft = mSwitch_bg.getWidth() - mSlide_button.getWidth();
                    if (slide_left > maxLeft / 2) {
                        currentState = true;
                    } else {
                        currentState = false;
                    }
                }
                flushState();
                break;

        }

        flushView();
        return true;
    }


}

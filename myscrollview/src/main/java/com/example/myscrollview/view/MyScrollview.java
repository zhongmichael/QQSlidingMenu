package com.example.myscrollview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by haha on 2016/9/4.
 */
public class MyScrollview extends ViewGroup  {

    private GestureDetector mGestureDetector;
    private int mFirstX;
    private Scroller mScroller;

    public MyScrollview(Context context) {
        super(context);
        init();
    }

    public MyScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyScrollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mScroller = new Scroller(getContext());

        mGestureDetector = new GestureDetector(getContext(), new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                scrollBy((int) distanceX,0);
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).measure(widthMeasureSpec,heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            int left = i*getWidth();
            int top = 0 ;
            int right = left + getWidth();
            int bottom = getHeight();
            getChildAt(i).layout(left,top,right,bottom);
        }
    }

    private boolean isfling = false;
    private int currId = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        mGestureDetector.onTouchEvent(event);

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mFirstX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if (!isfling){
                    int nextId = 0;
                    if ((event.getX() - mFirstX)>getWidth() / 2){
                        nextId =currId -1;
                    }else if ( (mFirstX - event.getX()) > getWidth() /2){
                        nextId = currId + 1;
                    }
                    moveToDest(nextId);
                }
                isfling = false;
                break;
        }
        return true;
    }

    private void moveToDest(int nextId) {

        nextId = nextId > 0 ? nextId : 0 ;
        nextId = nextId > getChildCount() -1? getChildCount() -1:nextId;

        int distance = currId*getWidth() - getScrollX();
        mScroller.startScroll(getScrollX(),0,distance,0,Math.abs(distance));

        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()){
            int newX = mScroller.getCurrX();
            scrollTo(newX,0);
            invalidate();
        }
    }
}

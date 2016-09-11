package view;



import android.content.Context;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.example.haha.qqslidemenu.ColorUtil;
import com.nineoldandroids.animation.FloatEvaluator;
import com.nineoldandroids.animation.IntEvaluator;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by haha on 2016/9/2.
 */
public class SideMenu extends FrameLayout  {

    private ViewDragHelper mViewDragHelper;
    private View mMenuView;
    private View mMainView;
    private FloatEvaluator mFloatEvaluator;
    private IntEvaluator mIntEvaluator;
    private int mWidth;
    private float mDragRange;

    public SideMenu(Context context) {
        super(context);
        init();
    }

    public SideMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SideMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mViewDragHelper = ViewDragHelper.create(this,callBack);
        mFloatEvaluator = new FloatEvaluator();
        mIntEvaluator = new IntEvaluator();

    }

    enum DragState {
        Open,Close;
    }

    private DragState currentState = DragState.Close;

    public DragState getCurrentState(){
        return  currentState;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mDragRange = mWidth*0.6f;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mMenuView = getChildAt(0);
        mMainView = getChildAt(1);
    }

    private  ViewDragHelper.Callback callBack = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mMainView || child == mMenuView;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return (int) mDragRange;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == mMainView) {
                if (left < 0) {
                    left = 0;
                } else if (left > (getMeasuredWidth() - child.getMeasuredWidth())) {
                    left = getMeasuredWidth() - child.getMeasuredWidth();
                }
            }
            return left;
        }

        /*
        用来做伴随移动的
         */
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if(changedView==mMenuView){
                //固定住menuView
                mMenuView.layout(0, 0, mMenuView.getMeasuredWidth(),mMenuView.getMeasuredHeight());
                //让mainView移动起来
                int newLeft = mMainView.getLeft()+dx;
                if(newLeft<0)newLeft=0;//限制mainView的左边
                if(newLeft>mDragRange)newLeft=(int) mDragRange;//限制mainView的右边
                mMainView.layout(newLeft,mMainView.getTop()+dy,newLeft+mMainView.getMeasuredWidth(),mMainView.getBottom()+dy);
            }

            //1.计算滑动的百分比
            float fraction = mMainView.getLeft()/mDragRange;
            //2.执行伴随动画
            executeAnimation(fraction);
            //3.更改状态，回调listener的方法
            if(fraction==0 && currentState!=DragState.Close){
                //更改状态为关闭，并回调关闭的方法
                currentState = DragState.Close;
                if(mListener!=null)mListener.close();
            }else if (fraction==1f && currentState!=DragState.Open) {
                //更改状态为打开，并回调打开的方法
                currentState = DragState.Open;
                if(mListener!=null)mListener.open();
            }
            //将drag的fraction暴漏给外界
            if(mListener!=null){
                mListener.onDraging(fraction);
            }
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if(mMainView.getLeft()<mDragRange/2){
                //在左半边
                close();
            }else {
                //在右半边
                open();
            }

            //处理用户的稍微滑动
            if(xvel>200 && currentState!=DragState.Open){
                open();
            }else if (xvel<-200 && currentState!=DragState.Close) {
                close();
            }
        }
    };



    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean result = mViewDragHelper.shouldInterceptTouchEvent(ev);
        return result;
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(SideMenu.this);
        }
        //super.computeScroll();
    }

    private void executeAnimation(float fraction){
        ViewHelper.setScaleX(mMainView,mFloatEvaluator.evaluate(fraction,1f,0.8f));
        ViewHelper.setScaleY(mMainView,mFloatEvaluator.evaluate(fraction,1f,0.8f));

        ViewHelper.setTranslationX(mMenuView,mIntEvaluator.evaluate(fraction,-mMenuView.getMeasuredWidth()/2,0));
        //放大menuView
        ViewHelper.setScaleX(mMenuView,mFloatEvaluator.evaluate(fraction,0.5f,1f));
        ViewHelper.setScaleY(mMenuView,mFloatEvaluator.evaluate(fraction,0.5f,1f));
        //改变menuView的透明度
        ViewHelper.setAlpha(mMenuView,mFloatEvaluator.evaluate(fraction,0.3f,1f));

        //给SlideMenu的背景添加黑色的遮罩效果
        getBackground().setColorFilter((Integer) ColorUtil.evaluateColor(fraction, Color.BLACK,Color.TRANSPARENT),
                PorterDuff.Mode.SRC_OVER);

    }

    private OnDragStateChangeListener mListener;

    public void setOnDragStateChangeListener(OnDragStateChangeListener listener){
        mListener = listener;
    }

    public void close(){
        mViewDragHelper.smoothSlideViewTo(mMainView,0,mMainView.getTop());
        ViewCompat.postInvalidateOnAnimation(SideMenu.this);
    }

    public void open(){
        mViewDragHelper.smoothSlideViewTo(mMenuView, (int) mDragRange,mMainView.getTop());
        ViewCompat.postInvalidateOnAnimation(SideMenu.this);
    }
    public interface  OnDragStateChangeListener {
        void open();
        void close();
        void onDraging(float fraction);
    }
}

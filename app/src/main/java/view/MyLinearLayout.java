package view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by haha on 2016/9/2.
 */
public class MyLinearLayout extends LinearLayout {

    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private SideMenu mSideMenu;
    public void setSideMenu(SideMenu sideMenu){
        mSideMenu = sideMenu;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (mSideMenu != null && mSideMenu.getCurrentState() == SideMenu.DragState.Open){
            return true;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (mSideMenu !=null && mSideMenu.getCurrentState() == SideMenu.DragState.Open){
            if (event.getAction() == MotionEvent.ACTION_UP){
                mSideMenu.close();
            }

            return true;
        }
        return super.onTouchEvent(event);
    }
}

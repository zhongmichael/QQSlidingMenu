package com.example.haha.qqslidemenu;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.CycleInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.Random;

import view.MyLinearLayout;
import view.SideMenu;

public class MainActivity extends Activity {

    private ListView mMain_listview;
    private ListView mMenu_listview;
    private SideMenu mSlidemenu;
    private MyLinearLayout mMyLayout;
    private ImageView mIv_head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();


    }



    private void initData() {
        mMenu_listview.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Constant.sCheeseStrings){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(Color.WHITE);
                return textView;
            }
        });

        mMain_listview.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Constant.NAMES){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = convertView==null?super.getView(position, convertView, parent):convertView;
                //先缩小view
                ViewHelper.setScaleX(view, 0.5f);
                ViewHelper.setScaleY(view, 0.5f);
                //以属性动画放大
                ViewPropertyAnimator.animate(view).scaleX(1).setDuration(350).start();
                ViewPropertyAnimator.animate(view).scaleY(1).setDuration(350).start();
                return view;
            }
        });

        mSlidemenu.setOnDragStateChangeListener(new SideMenu.OnDragStateChangeListener() {
            @Override
            public void open() {
                //				Log.e("tag", "onOpen");
                mMenu_listview.smoothScrollToPosition(new Random().nextInt(mMenu_listview.getCount()));
            }
            @Override
            public void onDraging(float fraction) {
                //				Log.e("tag", "onDraging fraction:"+fraction);
                ViewHelper.setAlpha(mIv_head,1-fraction);
            }
            @Override
            public void close() {
                //				Log.e("tag", "onClose");
                ViewPropertyAnimator.animate(mIv_head).translationXBy(15)
                        .setInterpolator(new CycleInterpolator(4))
                        .setDuration(500)
                        .start();
            }
        });

        mMyLayout.setSideMenu(mSlidemenu);
    }

    private void initView() {
        mMain_listview = (ListView) findViewById(R.id.main_listview);
        mMenu_listview = (ListView) findViewById(R.id.menu_listview);

        mSlidemenu = (SideMenu) findViewById(R.id.slidemenu);
        mMyLayout = (MyLinearLayout) findViewById(R.id.my_layout);
        mIv_head = (ImageView) findViewById(R.id.iv_head);
    }
}

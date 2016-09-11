package com.example.myscrollview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.myscrollview.view.MyScrollview;

import java.util.ArrayList;

public class MainActivity extends Activity {


    private int[] ids = new int[]{R.mipmap.a1,R.mipmap.a2,R.mipmap.a3,R.mipmap.a4,
            R.mipmap.a5,R.mipmap.a6};
    private MyScrollview msv;
    //private ArrayList<ImageView> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        msv = (MyScrollview)findViewById(R.id.msc);

        initData();
    }

    private void initData() {
       // mList = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setBackgroundResource(ids[i]);
            msv.addView(iv);
           // mList.add(iv);
        }

    }
}

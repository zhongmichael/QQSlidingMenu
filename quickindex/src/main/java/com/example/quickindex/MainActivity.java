package com.example.quickindex;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.quickindex.view.QuickIndexBar;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private QuickIndexBar mQuickIndexBar;
    private ArrayList<Friend> friends = new ArrayList<>();
    private TextView mTv_current_word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.listview);
        mQuickIndexBar = (QuickIndexBar) findViewById(R.id.quickindex_bar);
        mTv_current_word = (TextView) findViewById(R.id.currentWord);
        fillList();
        Collections.sort(friends);

        ViewHelper.setScaleX(mTv_current_word,0);
        ViewHelper.setScaleY(mTv_current_word,0);

        mListView.setAdapter(new MyAdapter(this,friends));

        mQuickIndexBar.setOnTouchLetterListener(new QuickIndexBar.OnTouchLetterListener() {
            @Override
            public void onTouchLetter(String letter) {
                for (int i = 0; i < friends.size(); i++) {
                    String firstWord = friends.get(i).getPinyin().charAt(0)+"";
                    if (firstWord.equals(letter)){
                        mListView.setSelection(i);
                        break;
                    }
                }

                showCurrentWord(letter);
            }


        });

    }

    private boolean isScale =false;
    private Handler mhandler = new Handler();

    private void showCurrentWord(String letter) {
        mTv_current_word.setText(letter);
        if (!isScale){
            isScale = true;
            ViewPropertyAnimator.animate(mTv_current_word).scaleX(1f).setDuration(450).start();
            ViewPropertyAnimator.animate(mTv_current_word).scaleY(1f).setDuration(450).start();
        }
        mhandler.removeCallbacksAndMessages(null);

        mhandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ViewPropertyAnimator.animate(mTv_current_word).scaleX(0f).setDuration(450).start();
                ViewPropertyAnimator.animate(mTv_current_word).scaleY(0f).setDuration(450).start();
                isScale = false;
            }
        },500);
    }

    private void fillList() {
        // 虚拟数据
        friends.add(new Friend("李伟"));
        friends.add(new Friend("张三"));
        friends.add(new Friend("阿三"));
        friends.add(new Friend("阿四"));
        friends.add(new Friend("段誉"));
        friends.add(new Friend("段正淳"));
        friends.add(new Friend("张三丰"));
        friends.add(new Friend("陈坤"));
        friends.add(new Friend("林俊杰1"));
        friends.add(new Friend("陈坤2"));
        friends.add(new Friend("王二a"));
        friends.add(new Friend("林俊杰a"));
        friends.add(new Friend("张四"));
        friends.add(new Friend("林俊杰"));
        friends.add(new Friend("王二"));
        friends.add(new Friend("王二b"));
        friends.add(new Friend("赵四"));
        friends.add(new Friend("杨坤"));
        friends.add(new Friend("赵子龙"));
        friends.add(new Friend("杨坤1"));
        friends.add(new Friend("李伟1"));
        friends.add(new Friend("宋江"));
        friends.add(new Friend("宋江1"));
        friends.add(new Friend("李伟3"));
        friends.add(new Friend("michael"));
    }

}

package com.example.quickindex;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by haha on 2016/9/3.
 */
public class MyAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Friend> mList;
    public MyAdapter(Context context,ArrayList<Friend> list){
        this.mContext = context;
        mList = list;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = View.inflate(mContext, R.layout.adapter_friend,null);
        }
        ViewHolder holder = ViewHolder.getHolder(convertView);
        Friend friend = mList.get(position);
        holder.name.setText(friend.getName());

        String currentWord = friend.getPinyin().charAt(0)+"";
        if (position > 0){
            String lastWord = mList.get(position - 1).getPinyin().charAt(0)+"";
            if (currentWord.equals(lastWord)){
                holder.first_word.setVisibility(View.GONE);
            }else {
                holder.first_word.setVisibility(View.VISIBLE);
                holder.first_word.setText(currentWord);
            }
        }else {
            holder.first_word.setVisibility(View.VISIBLE);
            holder.first_word.setText(currentWord);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView name,first_word;

        public ViewHolder(View convertView){
            name = (TextView) convertView.findViewById(R.id.name);
            first_word = (TextView) convertView.findViewById(R.id.first_word);
        }

        public static ViewHolder getHolder(View convertView){
            ViewHolder holder = (ViewHolder) convertView.getTag();
            if (holder == null){
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            return  holder;
        }
    }
}

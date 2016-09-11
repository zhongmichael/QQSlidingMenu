package com.example.quickindex;

/**
 * Created by haha on 2016/9/3.
 */
public class Friend implements  Comparable<Friend>{

    private  String name;
    private String pinyin;
    public  Friend(String name){
        this.name = name;
        setPinyin(PinYinUtil.getPinyin(name));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    @Override
    public int compareTo(Friend o) {
        return getPinyin().compareTo(o.getPinyin());
    }
}

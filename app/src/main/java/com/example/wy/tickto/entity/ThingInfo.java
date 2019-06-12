package com.example.wy.tickto.entity;

import org.litepal.crud.DataSupport;

import java.io.Serializable;


public class ThingInfo extends DataSupport implements Serializable{

    private long id;
    //设置等级
    //1-important
    //2-medium(默认)
    //3-easy
    private int howimportant;

    //事件
    private String whatthing;

    //备注
    private String note;

    //设置是否完成
    private boolean isdone;

    //设置一个时间
    private String cal_date;

    //创建时间，便于展示当天日程和过去日程
    //唯一一个public
    public String date;


    //构造函数
    public ThingInfo(){

    }

    public ThingInfo(boolean isdone,String whatthing,String cal_date,int howimportant){
        this.isdone = isdone;
        this.whatthing = whatthing;
        this.cal_date = cal_date;
        this.howimportant = howimportant;
    }

    public ThingInfo(String wahtthing,String note,int howimportant,boolean ischeck,String cal_date) {
        this.whatthing = wahtthing;
        this.note = note;
        this.howimportant = howimportant;
        this.isdone = ischeck;
        this.cal_date = cal_date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getHowimportant() {
        return howimportant;
    }

    public void setHowimportant(int howimportant) {
        this.howimportant = howimportant;
    }

    public String getWhatthing() {
        return whatthing;
    }

    public void setWhatthing(String whatthing) {
        this.whatthing = whatthing;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isDone() {
        return isdone;
    }

    public void setDone(boolean done) {
        this.isdone = done;
    }

    public String getCal_date() { return cal_date; }

    public void setCal_date(String cal_date) { this.cal_date = cal_date;}

    @Override
    public String toString() {
        String string = "thing=" + getWhatthing() + " important rank=" + getHowimportant()
                + " note=" + getNote()  + " isCheck=" + isDone() +"cal_date"+getCal_date() + " date=" + date;
        return string;

    }
}

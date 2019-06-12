package com.example.wy.tickto.calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wy.tickto.MainActivity;
import com.example.wy.tickto.R;
import com.example.wy.tickto.adapter.MainAdapter;
import com.example.wy.tickto.entity.Operation4DB;
import com.example.wy.tickto.entity.ThingInfo;
import com.example.wy.tickto.leftmenu.AddThing;

import org.litepal.crud.DataSupport;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyCalendar extends AppCompatActivity implements NewCalendar.NewCalendarListener {

    public final int DB_NOTADD = 1;
    public final int DB_ADD = 2;
    public final int SET_WRITEDIARYTIME = 3;
    private static final String TAG = "MyCalendar";
    private int updatePosition;
    public ThingInfo info;

    public List<ThingInfo> ThingsInfoList = new ArrayList<>();

    private MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_calendar);

        NewCalendar calendar = findViewById(R.id.newCalendar);
        calendar.listener = this;
    }

    @Override
    public void onItemLongPress(Date day) {
        Intent intent = new Intent(MyCalendar.this, AddThing.class);
        ThingInfo temp = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        intent.putExtra("showThingInfo",temp);
        intent.putExtra("cal_date_now",df.format(day));

        startActivityForResult(intent,DB_NOTADD);
//
//        Toast.makeText(this,df.format(day), Toast.LENGTH_SHORT).show();
    }

    private List<ThingInfo> calList = new ArrayList<>();
    @Override
    public void onShortPress(Date day) {
        //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        //Log.d("MyCalendar","日历时间为："+day.getTime());
        initList(day);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.cal_recyclerView);
        CalendarAdapter adapter = new CalendarAdapter(calList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
//        calList.clear();
    }

    private void initList(Date day){
        List<ThingInfo> initThing = DataSupport.select("cal_date","whatthing","howimportant","isdone").find(ThingInfo.class);
        calList.clear();
        for(ThingInfo init:initThing){
            if((day.getTime()-StrToDate(init.getCal_date()).getTime())<100000000&&(day.getTime()-StrToDate(init.getCal_date()).getTime())>0) {
                Log.d("MyCalendar", "日历时间为：" + day.getTime());
                ThingInfo initS = new ThingInfo(init.isDone(),init.getWhatthing(), init.getCal_date(),init.getHowimportant());
                Log.d("MyCalendar", "事件完成情况：" + init.isDone());
                Log.d("MyCalendar", "事件等级：" + init.getHowimportant());
                Log.d("MyCalendar", "事件时间为：" + StrToDate(init.getCal_date()).getTime());
                Log.d("MyCalendar", "时间差值为：" + (day.getTime()-StrToDate(init.getCal_date()).getTime())+"毫秒，故为点击日历时期，可予以显示");
                calList.add(initS);
            }else{
                Log.d("MyCalendar", "当天无事件可添加至集合：" );
            }
        }
    }

    //字符串转日期
    public static Date StrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    //不同活动间的交互
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==DB_NOTADD){
            if(data==null)
            {
                Toast.makeText(MyCalendar.this,"暂无添加",Toast.LENGTH_SHORT).show();
                return ;
            }
            ThingInfo newThinginfo = (ThingInfo) data.getSerializableExtra("addThing");

            Operation4DB.add(newThinginfo);
            Log.e(TAG, "测试返回结果 "+newThinginfo.toString());

            ThingsInfoList.add(newThinginfo);
            mainAdapter.notifyDataSetChanged(ThingsInfoList);
        }
        else if(requestCode==DB_ADD){
            if(data==null) {
                Toast.makeText(MyCalendar.this,"暂无添加",Toast.LENGTH_SHORT).show();
                return ;
            }
            ThingInfo newThinginfo = (ThingInfo) data.getSerializableExtra("addThing");
            if(newThinginfo==null) {
                Toast.makeText(MyCalendar.this,"暂无添加",Toast.LENGTH_SHORT).show();
                return ;
            }
            ThingInfo updateinfo = Operation4DB.find(newThinginfo.getId());

            updateinfo.setWhatthing(newThinginfo.getWhatthing());
            updateinfo.setDone(newThinginfo.isDone());
            updateinfo.setHowimportant(newThinginfo.getHowimportant());
            updateinfo.setNote(newThinginfo.getNote());
            updateinfo.save();

            ThingsInfoList.set(updatePosition,updateinfo);
            mainAdapter.notifyDataSetChanged(ThingsInfoList);
        }
        else if(requestCode==SET_WRITEDIARYTIME){
            //设置通知写日记时间
        }
    }

}

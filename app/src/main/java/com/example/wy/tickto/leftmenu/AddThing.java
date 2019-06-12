package com.example.wy.tickto.leftmenu;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.wy.tickto.MainActivity;
import com.example.wy.tickto.R;
import com.example.wy.tickto.entity.ThingInfo;
import com.example.wy.tickto.timingpush.AlarmClock;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddThing extends AppCompatActivity {

    private static final String TAG = "AddThing";
    public ThingInfo info;
    public boolean isupdate = false;

    public RadioButton rb1 = null;
    public RadioButton rb2 = null;
    public RadioButton rb3 = null;
    public EditText whatthing = null;
    public EditText note = null;
    public TextView timeText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_thing);

        Intent intent1 = getIntent();
        info = (ThingInfo) intent1.getSerializableExtra("showThingInfo");

        if(info == null) {
            isupdate = false;
        }
        else {
            isupdate = true;
        }

        rb1 = (RadioButton)findViewById(R.id.rb_important);
        rb2 = (RadioButton)findViewById(R.id.rb_medium);
        rb3 = (RadioButton)findViewById(R.id.rb_easy);
        whatthing = (EditText)findViewById(R.id.edit_thing);
        note = (EditText)findViewById(R.id.edit_note);
        timeText = (TextView) findViewById(R.id.set_time);

        timeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将timeText传入用于显示所选择的时间
                showDialogPick((TextView) v);
            }
        });

        if(isupdate){
            //修改原有的事件
            Log.e(TAG, "onClick: 进入设置界面");
            whatthing.setText(info.getWhatthing());
            note.setText(info.getNote());
            timeText.setText(info.getCal_date());
            int mark = info.getHowimportant();
            if(mark==1){
                rb1.setChecked(true);
            }else if(mark==2){
                rb2.setChecked(true);
            }else{
                rb3.setChecked(true);
            }
        }else{
            info = new ThingInfo();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_addThing);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //修改或者添加
                info.setWhatthing(whatthing.getText().toString());
                info.setNote(note.getText().toString());
                info.setCal_date(timeText.getText().toString());

                Log.d("AddThing","时间添加至数据库"+info.getCal_date());

                if(!isupdate)
                    info.setDone(false);

                if(rb1.isChecked()) {
                    info.setHowimportant(1);
                }else if(rb2.isChecked()){
                    info.setHowimportant(2);
                }else if (rb3.isChecked()){
                    info.setHowimportant(3);
                }



                if(isupdate) Toast.makeText(AddThing.this, "更新成功",Toast.LENGTH_SHORT).show();
                else Toast.makeText(AddThing.this, "添加成功",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();
                intent.putExtra("addThing",info);
                intent.putExtra("alarmTime",info.getCal_date());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
           finish();
        }

        return super.onKeyDown(keyCode, event);

    }

    //将两个选择时间的dialog放在该函数中
    private void showDialogPick(final TextView timeText) {
        final StringBuffer time = new StringBuffer();
        //获取Calendar对象，用于获取当前时间
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        Intent intent2 = getIntent();
        String cal_date_now = intent2.getStringExtra("cal_date_now");

        if (cal_date_now != null) {
            timeText.setText(cal_date_now);
        } else {
            //实例化TimePickerDialog对象
            final TimePickerDialog timePickerDialog = new TimePickerDialog(AddThing.this, new TimePickerDialog.OnTimeSetListener() {
                //选择完时间后会调用该回调函数
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    time.append(" " + hourOfDay + ":" + minute);
                    //设置TextView显示最终选择的时间
                    timeText.setText(time);
                }
            }, hour, minute, true);
            //实例化DatePickerDialog对象
            DatePickerDialog datePickerDialog = new DatePickerDialog(AddThing.this, new DatePickerDialog.OnDateSetListener() {
                //选择完日期后会调用该回调函数
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    //因为monthOfYear会比实际月份少一月所以这边要加1
                    time.append(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    //选择完日期后弹出选择时间对话框
                    timePickerDialog.show();
                }
            }, year, month, day);
            //弹出选择日期对话框
            datePickerDialog.show();
        }
    }


}

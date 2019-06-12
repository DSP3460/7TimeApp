package com.example.wy.tickto.timingpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


public class AlarmClock extends BroadcastReceiver {

    public AlarmClock(){

    }
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Intent alarmIntent = new Intent(context, ActDialog.class);
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(alarmIntent);
        Toast.makeText(context, "闹钟响了哟!", Toast.LENGTH_SHORT).show();
        Log.d("AlarmClock", "闹钟响了！");
    }

}

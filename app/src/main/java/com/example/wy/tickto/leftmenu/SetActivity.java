package com.example.wy.tickto.leftmenu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wy.tickto.R;
import com.example.wy.tickto.login.LoginActivity;
import com.example.wy.tickto.user_ins.user_instructions;

public class SetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        //用户须知
        TextView user_use = (TextView)findViewById(R.id.user_use);
        user_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetActivity.this, user_instructions.class);
                startActivity(intent);
            }
        });

        //切换用户
        TextView alter_user = (TextView)findViewById(R.id.alter_user);
        alter_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//它可以关掉所要到的界面中间的activity
                startActivity(intent);
                finish();
            }
        });

        //清除缓存
        TextView delete_cache = (TextView)findViewById(R.id.delete_cache);
        delete_cache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    SharedPreferences preferences = getSharedPreferences("loginInfo", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.commit();
                    Log.d("SetActivity","缓存清理！！！！");
                    Toast.makeText(SetActivity.this,"清除缓存成功！",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

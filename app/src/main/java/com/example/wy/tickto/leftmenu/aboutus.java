package com.example.wy.tickto.leftmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.wy.tickto.R;

public class aboutus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        TextView textView = (TextView)findViewById(R.id.textview_in_aboutus);
        String aboutme = new String("\n西南大学商贸学院\n计算机科学与技术3班\n杜==");
        textView.setText(aboutme);
    }
}

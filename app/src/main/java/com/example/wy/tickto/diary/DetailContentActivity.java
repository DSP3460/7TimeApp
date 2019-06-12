package com.example.wy.tickto.diary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wy.tickto.R;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class DetailContentActivity extends AppCompatActivity {

    //只显示标题和日期
    public List<String> data = new ArrayList<>();
    public ListView listView;
    public final int WRITEDNEWDIART = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_content);

        Intent intent1 = getIntent();
        DiaryContent info = (DiaryContent) intent1.getSerializableExtra("DiaryContent");
        final String a = info.getTitle();

        TextView textView = (TextView)findViewById(R.id.textview_detail);
        textView.setText(info.toString());

        Button delete_dia = (Button) findViewById(R.id.delete_diary);
        delete_dia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSupport.deleteAll(DiaryContent.class,"title=?",a);
                Intent intent = new Intent(DetailContentActivity.this,ShowDiaryActivity.class);
                startActivityForResult(intent,WRITEDNEWDIART);
            }
        });
    }
}

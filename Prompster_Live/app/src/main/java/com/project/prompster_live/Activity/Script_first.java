package com.project.prompster_live.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.prompster_live.R;
import com.project.prompster_live.Utils.Shared_PrefrencePrompster;

public class Script_first extends AppCompatActivity {
ImageView mice_inactive,cross,iv_text,iv_crosstext;
RelativeLayout rel_bottom,rl_visible;
ImageView iv_back;
Shared_PrefrencePrompster shared_prefrencePrompster;
String title,desc;
Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_script_first);

shared_prefrencePrompster=Shared_PrefrencePrompster.getInstance(this);
intent=getIntent();
title=intent.getStringExtra("title");
desc=intent.getStringExtra("desc");
        mice_inactive=findViewById(R.id.mice_inactive);
        iv_text=findViewById(R.id.iv_text);

        cross=findViewById(R.id.cross);
        iv_crosstext=findViewById(R.id.iv_crosstext);
iv_back=findViewById(R.id.iv_back);

        rel_bottom=findViewById(R.id.rel_bottom);
        rl_visible=findViewById(R.id.rl_visible);

        mice_inactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rel_bottom.setVisibility(View.VISIBLE);
            }
        });

        iv_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_visible.setVisibility(View.VISIBLE);
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent   intent=new Intent(Script_first.this,Script_Toolbar.class);

                intent.putExtra("TAG","3");
               intent.putExtra("title",title);
               intent.putExtra("description",desc);
                startActivity(intent);
                finish();
            }
        });

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rel_bottom.setVisibility(View.GONE);
            }
        });

        iv_crosstext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_visible.setVisibility(View.GONE);
            }
        });
    }
}

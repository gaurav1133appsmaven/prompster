package com.project.prompster_live.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.project.prompster_live.R;

public class In_app extends AppCompatActivity {
TextView tv_getstarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_app);
        tv_getstarted=findViewById(R.id.tv_getstarted);
        tv_getstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(In_app.this,Script_Toolbar.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

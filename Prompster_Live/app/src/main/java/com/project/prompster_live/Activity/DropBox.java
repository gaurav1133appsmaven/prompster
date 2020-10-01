package com.project.prompster_live.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.project.prompster_live.R;

public class DropBox extends AppCompatActivity {
ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop_box);

        iv_back=findViewById(R.id.iv_back);


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back=new Intent(DropBox.this,Script_Toolbar.class);
                startActivity(back);
                finish();

            }
        });
    }
}

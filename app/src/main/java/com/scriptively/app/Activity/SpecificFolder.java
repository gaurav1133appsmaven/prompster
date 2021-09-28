package com.scriptively.app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.scriptively.app.R;

public class SpecificFolder extends AppCompatActivity {

    TextView tv_folername;
    String scrTitle_foldertitle,FolderId;
    RecyclerView rv_data_folder;
    Intent intent_folder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_folder);

        intent_folder = getIntent();
        scrTitle_foldertitle = intent_folder.getStringExtra("scrTitle_foldertitle");
        FolderId = intent_folder.getStringExtra("FolderId");
        init();
        tv_folername.setText(scrTitle_foldertitle);
    }

    private void init() {

        tv_folername = findViewById(R.id.tv_folername);
        rv_data_folder = findViewById(R.id.rv_data_folder);


    }
}
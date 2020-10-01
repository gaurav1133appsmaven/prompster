package com.project.prompster_live.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.project.prompster_live.R;
import com.project.prompster_live.Utils.Shared_PrefrencePrompster;

public class Profile_Activity extends AppCompatActivity {
    ImageView iv_back;
    TextView tv_inapp, tv_teleprompter, tv_pname, tv_mail;
    RelativeLayout rl_logout;
    String maiil, name;
    Shared_PrefrencePrompster shared_prefrencePrompster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_);
        shared_prefrencePrompster = Shared_PrefrencePrompster.getInstance(this);
        tv_pname = findViewById(R.id.tv_pname);
        iv_back = findViewById(R.id.iv_back);
        tv_inapp = findViewById(R.id.tv_inapp);
        tv_teleprompter = findViewById(R.id.tv_teleprompter);
        rl_logout = findViewById(R.id.rl_log);
        tv_mail = findViewById(R.id.tv_mail);

        maiil = shared_prefrencePrompster.getEmail();
        name = shared_prefrencePrompster.getFirstName();

        tv_pname.setText(name);
        tv_mail.setText(maiil);

        rl_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shared_prefrencePrompster.clearPreference();
                Intent intent_logout = new Intent(Profile_Activity.this, Login_Screen.class);
                startActivity(intent_logout);
                finish();
            }
        });

        tv_teleprompter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile_Activity.this, Teleprompter_Screen.class);
                startActivity(intent);
                finish();
            }
        });


        tv_inapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile_Activity.this, In_app.class);
                startActivity(intent);
                finish();

            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile_Activity.this, Navigation.class);
                startActivity(intent);
                finish();

            }
        });
    }
}

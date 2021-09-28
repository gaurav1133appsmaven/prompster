package com.scriptively.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RelativeLayout;

import com.scriptively.app.R;

public class Signup_FirstScreen extends AppCompatActivity {
RelativeLayout rl_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup__first_screen);

        init();
    }

    private void init() {

//        rl_signup=findViewById(R.id.rl_signup);
//
//        rl_signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent signup=new Intent(Signup_FirstScreen.this,SignUp_Screen.class);
//                startActivity(signup);
//                finish();
//            }
//        });
    }
}

package com.project.prompster_live.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.project.prompster_live.R;
import com.project.prompster_live.Utils.Shared_PrefrencePrompster;

public class Splash_Activity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    Shared_PrefrencePrompster shared_prefrencePrompster;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_);

        shared_prefrencePrompster = Shared_PrefrencePrompster.getInstance(this);
        email = shared_prefrencePrompster.getEmail().toString();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                if (email.equals("")) {
                    startActivity(new Intent(Splash_Activity.this, Welcome.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                } else if (email.length() > 0) {
                    startActivity(new Intent(Splash_Activity.this, Navigation.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                }


//                /* Create an Intent that will start the Menu-Activity. */
//                Intent mainIntent = new Intent(Splash_Activity.this,Welcome.class);
//                Splash_Activity.this.startActivity(mainIntent);
//                Splash_Activity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}

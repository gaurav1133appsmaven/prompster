package com.project.prompster_live.Activity;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.prompster_live.Database.AppDatabase;
import com.project.prompster_live.Database.ScriptDBDao;
import com.project.prompster_live.DatabaseModel.ScriptDB;
import com.project.prompster_live.R;
import com.project.prompster_live.Utils.Shared_PrefrencePrompster;
import com.project.prompster_live.Utils.Util;

public class Profile_Activity extends AppCompatActivity {
    RelativeLayout iv_back;
    TextView tv_inapp, tv_teleprompter, tv_pname, tv_mail;
    RelativeLayout rl_logout,rl_apppurchase;
    String maiil, name;
    AppDatabase db;
    Shared_PrefrencePrompster shared_prefrencePrompster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_);
        shared_prefrencePrompster = Shared_PrefrencePrompster.getInstance(this);
        db = AppDatabase.getInstance(this);
        tv_pname = findViewById(R.id.tv_pname);
        iv_back = findViewById(R.id.iv_back);
        tv_inapp = findViewById(R.id.tv_inapp);
        tv_teleprompter = findViewById(R.id.tv_teleprompter);
        rl_logout = findViewById(R.id.rl_log);
        tv_mail = findViewById(R.id.tv_mail);
        rl_apppurchase = findViewById(R.id.rl_apppurchase);

        maiil = shared_prefrencePrompster.getEmail();
        name = shared_prefrencePrompster.getFirstName();

        tv_pname.setText(name);
        tv_mail.setText(maiil);

        rl_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shared_prefrencePrompster.clearPreference();
                Intent intent_logout = new Intent(Profile_Activity.this, Login_Screen.class);

                new DeleteAllAsyncTask(Profile_Activity.this).execute();
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


//        tv_inapp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Profile_Activity.this, In_app.class);
//                startActivity(intent);
//
//            }
//        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile_Activity.this, Navigation.class);
                startActivity(intent);
                finish();

            }
        });

        rl_apppurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile_Activity.this, In_app.class);
                startActivity(intent);
//                finish();
            }
        });
    }

    private class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        Context context;

        DeleteAllAsyncTask( Context context) {


            this.context = context;


        }

        @Override
        protected Void doInBackground(Void... voids) {
            db.clearAllTables();
            return null;
        }

        @Override
        protected void onPostExecute(Void voidd) {


// super.onPostExecute(integer);
        }
    }
}

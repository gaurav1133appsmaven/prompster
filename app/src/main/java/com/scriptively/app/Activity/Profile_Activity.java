package com.scriptively.app.Activity;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.scriptively.app.Activity.ui.HowToVideo;
import com.scriptively.app.Database.AppDatabase;
import com.scriptively.app.R;
import com.scriptively.app.Utils.Shared_PrefrencePrompster;

public class Profile_Activity extends AppCompatActivity {
    RelativeLayout iv_back;
    TextView tv_inapp, tv_teleprompter, tv_pname, tv_mail;
    RelativeLayout rl_logout,rl_apppurchase,rl_faq,rl_write,rl_apps;
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
        rl_faq = findViewById(R.id.rl_faq);
        rl_write = findViewById(R.id.rl_write);
        rl_apps = findViewById(R.id.rl_apps);


        maiil = shared_prefrencePrompster.getEmail();
        name = shared_prefrencePrompster.getFirstName();

        tv_pname.setText(name);
        tv_mail.setText(maiil);

        rl_apps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);

                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@scriptively.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Scriptively 1.5 Support");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                intent.putExtra(Intent.EXTRA_TITLE,"Scriptively 1.5 Support");

                startActivity(Intent.createChooser(intent, "Choose Mail App"));
            }
        });

        rl_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });

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

        rl_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Profile_Activity.this, HowToVideo.class);
                startActivity(intent);

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

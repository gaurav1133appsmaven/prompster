package com.project.prompster_live.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.TextKeyListener;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.project.prompster_live.Api.ApiClient;
import com.project.prompster_live.Api.ApiInterface;
import com.project.prompster_live.Fragments.Fragment_Board;
import com.project.prompster_live.Fragments.Fragment_Script;
import com.project.prompster_live.Fragments.Fragment_media;
import com.project.prompster_live.Pojo.AddScript;
import com.project.prompster_live.Pojo.EditScript_Pojo;
import com.project.prompster_live.R;
import com.project.prompster_live.Utils.Shared_PrefrencePrompster;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Response;

public class Script_Toolbar extends AppCompatActivity {
    InputType InputTpe;
    RelativeLayout rl_data, rl_data1, rl_data2, rl_data3, rl_media, rl_titile;
    Fragment_Script fragment_script;
    ImageView iv_import, iv_exportt, iv_profile, iv_importalphabet, iv_back, iv_export;
    Dialog show, export;
    TextView tv_import, tv_dropbox, tv_googledrive, tv_cancel, tv_cancell;
    Shared_PrefrencePrompster shared_prefrencePrompster;
    String userid, scrTitle, script_text, scrAttrText, title, desc, script_id, Tag_value, story_board_id, saved_taag;
    EditText et_tv, tv_title;
    Intent intent;
    SeekBar seekbar;
    int textSize = 1;
    int saveProgress;
    ImageView iv_left, iv_right, iv_centre, iv_red, iv_darkyellow, iv_yellow, iv_green, iv_skyblue, iv_purple, iv_blue, iv_black, iv_darkgrey, iv_gray, iv_lightgray, iv_white;
    RelativeLayout rl_visible;
    ImageView iv_crosstext, iv_active;
    RelativeLayout rl_frame;
    Dialog dialog;
    TextView tv_ok;
    String descrip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_script__toolbar);
        shared_prefrencePrompster = Shared_PrefrencePrompster.getInstance(this);

        saved_taag = shared_prefrencePrompster.getTag().toString();
        intent = new Intent();
        intent = getIntent();
        title = intent.getStringExtra("title");
        desc = intent.getStringExtra("description");
        script_id = intent.getStringExtra("script_id");
        Tag_value = intent.getStringExtra("TAG");
        try {
            story_board_id = intent.getStringExtra("storyboard_id");
        } catch (IndexOutOfBoundsException e) {

        }
        userid = shared_prefrencePrompster.getUserid().toString();
        tv_title = (EditText) findViewById(R.id.tv_title);
        rl_frame = findViewById(R.id.rl_frame);
        et_tv = (EditText) findViewById(R.id.et_tv_description);
        rl_data = findViewById(R.id.rl_data);
        rl_data1 = findViewById(R.id.rl_data1);
        rl_data2 = findViewById(R.id.rl_data2);
        rl_data3 = findViewById(R.id.rl_data3);
        rl_media = findViewById(R.id.rl_media);
        rl_titile = findViewById(R.id.rl_titile);
        iv_import = findViewById(R.id.iv_import);
        tv_import = findViewById(R.id.tv_import);
        iv_exportt = findViewById(R.id.iv_exportt);
        iv_export = findViewById(R.id.iv_export);
        tv_cancell = findViewById(R.id.tv_cancell);
        iv_profile = findViewById(R.id.iv_profile);
        iv_importalphabet = findViewById(R.id.iv_importalphabet);
        iv_back = findViewById(R.id.iv_back);
        rl_visible = findViewById(R.id.rl_visible);
        seekbar = findViewById(R.id.seekbar);
        iv_active = findViewById(R.id.iv_active);
        iv_crosstext = findViewById(R.id.iv_crosstext);


//        tv_title.setInputType(InputType.TYPE_CLASS_TEXT| InputType.TYPE_TEXT_FLAG_CAP_WORDS);


        tv_title.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS | InputType.TYPE_TEXT_FLAG_MULTI_LINE);

        iv_crosstext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_visible.setVisibility(View.GONE);
                iv_active.setVisibility(View.GONE);
            }
        });
        iv_import.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_visible.setVisibility(View.VISIBLE);
                iv_active.setVisibility(View.VISIBLE);
            }
        });
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textSize = textSize + (progress - saveProgress);
                saveProgress = progress;
                et_tv.setTextSize(textSize);
            }
        });


        iv_left = findViewById(R.id.iv_left);
        iv_right = findViewById(R.id.iv_right);
        iv_centre = findViewById(R.id.iv_centre);

        iv_red = findViewById(R.id.iv_red);
        iv_darkyellow = findViewById(R.id.iv_darkyellow);
        iv_yellow = findViewById(R.id.iv_yellow);
        iv_green = findViewById(R.id.iv_green);
        iv_skyblue = findViewById(R.id.iv_skyblue);
        iv_purple = findViewById(R.id.iv_purple);
        iv_blue = findViewById(R.id.iv_blue);
        iv_black = findViewById(R.id.iv_black);
        iv_darkgrey = findViewById(R.id.iv_darkgrey);
        iv_gray = findViewById(R.id.iv_gray);
        iv_lightgray = findViewById(R.id.iv_lightgray);
        iv_white = findViewById(R.id.iv_white);

        et_tv.setHorizontallyScrolling(false);
        et_tv.setRawInputType(InputType.TYPE_CLASS_TEXT);
        et_tv.setImeOptions(EditorInfo.IME_ACTION_DONE);
        et_tv.setRawInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_CAP_WORDS);


        tv_title.setHorizontallyScrolling(false);
        tv_title.setRawInputType(InputType.TYPE_CLASS_TEXT);
        tv_title.setImeOptions(EditorInfo.IME_ACTION_DONE);
        tv_title.setRawInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_CAP_WORDS);

        if (Tag_value.equals("1")) {
            tv_import.setVisibility(View.VISIBLE);
            rl_data.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button));

        } else if (Tag_value.equals("2")) {

            tv_import.setVisibility(View.GONE);
            rl_data.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button));


        } else {
tv_import.setVisibility(View.GONE);
        }
        ///colored click


        et_tv.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                tv_import.setVisibility(v.GONE);
                return false;
            }
        });


        tv_title.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                tv_import.setVisibility(v.GONE);
                return false;
            }
        });

        et_tv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override

            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {

                    scrTitle = tv_title.getText().toString();
                    script_text = et_tv.getText().toString();

                    if (Tag_value.equals("1")) {
                        AddScriptapi(userid, scrTitle, script_text);
                    }
                        else if (Tag_value.equals("2")) {
                        EditScriptapi(userid, script_id, story_board_id, scrTitle, script_text, "bold", "20", "28", "4", "10.0", "false", "140.0", "");
                    } else {
                        tv_import.setVisibility(View.GONE);
                    }


                }
                return false;
            }
        });


        tv_title.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override

            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {

                    scrTitle = tv_title.getText().toString();
                    script_text = et_tv.getText().toString();

                    if (Tag_value.equals("1")) {
                        AddScriptapi(userid, scrTitle, " ");
                    }
                    else if (Tag_value.equals("2")) {
                        EditScriptapi(userid, script_id, story_board_id, scrTitle, script_text, "bold", "20", "28", "4", "10.0", "false", "140.0", "");
                    } else {

                    }


                }
                return false;
            }
        });


        iv_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_tv.setTextColor(Color.RED);
            }
        });
        iv_darkyellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_tv.setTextColor(Color.YELLOW);
            }
        });
        iv_yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_tv.setTextColor(Color.YELLOW);
            }
        });
        iv_green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_tv.setTextColor(Color.GREEN);
            }
        });
        iv_skyblue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_tv.setTextColor(Color.BLUE);
            }
        });
        iv_purple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_tv.setTextColor(Color.YELLOW);
            }
        });
        iv_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_tv.setTextColor(Color.BLUE);
            }
        });
        iv_black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_tv.setTextColor(Color.BLACK);
            }
        });
        iv_darkgrey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_tv.setTextColor(Color.DKGRAY);
            }
        });
        iv_gray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_tv.setTextColor(Color.LTGRAY);
            }
        });
        iv_lightgray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_tv.setTextColor(Color.LTGRAY);
            }
        });
        iv_white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_tv.setTextColor(Color.WHITE);
            }
        });

        try {
            if (title.equals("") || title.equals(null)) {
                tv_title.setHint("Title");
            } else {
                tv_title.setText(title);
            }
            if (desc.equals("") || desc.equals(null) || desc.equals(" ")) {
                et_tv.setHint("Start writing , copy and paste text from another app or..");
            } else {
                et_tv.setText(desc);
            }

        } catch (NullPointerException e) {

        }

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Script_Toolbar.this, Navigation.class);
                shared_prefrencePrompster.setTag("");
                startActivity(intent);
                finish();
            }
        });


        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_tv.setGravity(Gravity.LEFT);
            }
        });

        iv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_tv.setGravity(Gravity.RIGHT);
            }
        });

        iv_centre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_tv.setGravity(Gravity.CENTER);
            }
        });


        iv_importalphabet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(Script_Toolbar.this, "hello", Toast.LENGTH_SHORT).show();
//                Intent intent=new Intent(Script_Toolbar.this,Text_AlignMents.class);
//                startActivity(intent);

                scrTitle = tv_title.getText().toString();
                script_text = et_tv.getText().toString();
                AddScriptapi(userid, scrTitle, script_text);


            }
        });


        iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Script_Toolbar.this, Script_first.class);
                intent.putExtra("title",title);
                intent.putExtra("desc",et_tv.getText().toString());
                startActivity(intent);

                finish();

            }
        });

        iv_exportt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(Script_Toolbar.this, "hellooo", Toast.LENGTH_SHORT).show();
                export = new Dialog(Script_Toolbar.this);


                export.setContentView(R.layout.export_layout);
                export.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                tv_cancell = export.findViewById(R.id.tv_cancell);


                tv_cancell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        export.dismiss();
                    }
                });

                export.show();

            }
        });

        iv_export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(Script_Toolbar.this, "hellooo", Toast.LENGTH_SHORT).show();
                show = new Dialog(Script_Toolbar.this);


                show.setContentView(R.layout.import_layout);
                show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                tv_dropbox = show.findViewById(R.id.tv_dropbox);
                tv_googledrive = show.findViewById(R.id.tv_googledrive);
                tv_cancel = show.findViewById(R.id.tv_cancel);


                tv_dropbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Script_Toolbar.this, DropBox.class);
                        startActivity(intent);

                    }
                });


                tv_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        show.dismiss();
                    }
                });


                tv_googledrive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Script_Toolbar.this, GoogleDrive.class);
                        startActivity(intent);
                        finish();

                    }
                });

//                TextView tv_yes=dialog.findViewById(R.id.tv_yes);
//                TextView tv_cancel=dialog.findViewById(R.id.tv_cancel);

                show.show();

            }

        });


        tv_import.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(Script_Toolbar.this, "hellooo", Toast.LENGTH_SHORT).show();
                show = new Dialog(Script_Toolbar.this);


                show.setContentView(R.layout.import_layout);
                show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                tv_dropbox = show.findViewById(R.id.tv_dropbox);
                tv_googledrive = show.findViewById(R.id.tv_googledrive);
                tv_cancel = show.findViewById(R.id.tv_cancel);


                tv_dropbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Script_Toolbar.this, DropBox.class);
                        startActivity(intent);
                        finish();

                    }
                });


                tv_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        show.dismiss();
                    }
                });


                tv_googledrive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Script_Toolbar.this, GoogleDrive.class);
                        startActivity(intent);
                        finish();

                    }
                });

//                TextView tv_yes=dialog.findViewById(R.id.tv_yes);
//                TextView tv_cancel=dialog.findViewById(R.id.tv_cancel);

                show.show();

            }

        });


        rl_media.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_frame.setVisibility(View.VISIBLE);
                rl_data2.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button2));
                rl_data.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button2));
                rl_media.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button));
//                rl_data3.setVisibility(View.VISIBLE);
//                rl_data1.setVisibility(View.GONE);

                rl_titile.setVisibility(View.GONE);
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.frame, new Fragment_media()).commit();

            }
        });


        rl_data2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_frame.setVisibility(View.VISIBLE);
                rl_media.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button2));
                rl_data2.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button));
                rl_data.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button2));


//                rl_data3.setVisibility(View.VISIBLE);
//                rl_data1.setVisibility(View.GONE);
                rl_titile.setVisibility(View.GONE);
                Fragment_Board fragment_board = new Fragment_Board();
                Bundle arguments = new Bundle();
                FragmentManager manager = getSupportFragmentManager();
                arguments.putString("script_id", script_id);
                arguments.putString("script_title", tv_title.getText().toString());
                fragment_board.setArguments(arguments);
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.frame, fragment_board).commit();

            }
        });


        rl_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rl_frame.setVisibility(View.GONE);
                rl_titile.setVisibility(View.VISIBLE);
//
                rl_media.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button2));
                rl_data2.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button2));
                rl_data.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button));

                if (saved_taag.equals("1")) {

                } else {
                    et_tv.setText(desc);
                    tv_title.setText(title);
                }
//                rl_titile.setVisibility(View.GONE);
//                rl_data3.setVisibility(View.GONE);
//                FragmentManager manager = getSupportFragmentManager();
//                FragmentTransaction transaction = manager.beginTransaction();
//                transaction.replace(R.id.frame, new Fragment_Script()).commit();
            }
        });
    }

    private void EditScriptapi(String userid, String script_id, String story_board_id, String scrTitle, String script_text, String bold, String s, String s1, String s2, String s3, String aFalse, String s4, String s5) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


        retrofit2.Call<EditScript_Pojo> call = apiService.edit_script(userid, script_id, story_board_id, scrTitle, script_text, bold, s, s1, s2, s3, aFalse, s4, s5);
        call.enqueue(new retrofit2.Callback<EditScript_Pojo>() {
            @Override
            public void onResponse(Call<EditScript_Pojo> call, Response<EditScript_Pojo> response) {

                Log.e("Edit Script url", call.request().toString());

                if (response.isSuccessful()) {

                    Log.w("Add Script response", new Gson().toJson(response));
                    if (response.body().getSuccess().toString().equals("1")) {
                        Log.e("Add Script response", new Gson().toJson(response));

//                        Toast.makeText(Script_Toolbar.this, "editscript", Toast.LENGTH_SHORT).show();

//                        Intent intent = new Intent(Script_Toolbar.this, Navigation.class);

                        desc = response.body().getData().getScrText().toString();
                        title = response.body().getData().getScrTitle().toString();
                        tv_title.setText(title);
                        if (desc.equals(" "))
                        {
                            et_tv.setHint("Start writing , copy and paste text from another app or..");
                        }
                        else
                        {
                            et_tv.setText(desc);
                        }
                        shared_prefrencePrompster.setTag("");

//                        startActivity(intent);
//                        finish();

                    } else {


                        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(Script_Toolbar.this, SweetAlertDialog.ERROR_TYPE);

                        sweetAlertDialog.setTitleText(response.body().getMessage().toString());

                        sweetAlertDialog.show();
                        Button btn = (Button) sweetAlertDialog.findViewById(R.id.confirm_button);
                        btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));

                    }

                }
            }

            @Override
            public void onFailure(Call<EditScript_Pojo> call, Throwable t) {

                Log.e("errror", String.valueOf(t));
//                dialog = new Dialog(Script_Toolbar.this);
//
//
//                dialog.setContentView(R.layout.error_api);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                tv_ok = dialog.findViewById(R.id.tv_ok);
//
//
//                tv_ok.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        dialog.dismiss();
//
//
//                    }
//                });
//                dialog.show();

            }
        });


    }

    private void AddScriptapi(String userid, String scrTitle, final String script_text) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


        retrofit2.Call<AddScript> call = apiService.add_script(userid, scrTitle, script_text, script_text, "20", "20", "28", "10.0", "false", "140.0", "");
        call.enqueue(new retrofit2.Callback<AddScript>() {
            @Override
            public void onResponse(Call<AddScript> call, Response<AddScript> response) {

                Log.e("Add Script url", call.request().toString());

                if (response.isSuccessful()) {

                    Log.w("Add Script response", new Gson().toJson(response));
                    if (response.body().getSuccess().toString().equals("1")) {
                        Log.e("Add Script response", new Gson().toJson(response));


//                        Toast.makeText(Script_Toolbar.this, "hiiiiiiii", Toast.LENGTH_SHORT).show();
//                        Intent intent =new Intent(Script_Toolbar.this,Navigation.class);

                        desc= response.body().getData().getScrText().toString();
                        title = response.body().getData().getScrTitle().toString();
                        tv_title.setText(title);
//
                        if (desc.equals(" "))
                        {
                            et_tv.setHint("Start writing , copy and paste text from another app or..");
                        }
                        else {
                            et_tv.setText(desc);
                        }
                            shared_prefrencePrompster.setTag("");
//                        startActivity(intent);
//                        finish();


                    } else {



                    }

                }
            }

            @Override
            public void onFailure(Call<AddScript> call, Throwable t) {

                Log.e("errror", String.valueOf(t));

//                dialog = new Dialog(Script_Toolbar.this);
//
//
//                dialog.setContentView(R.layout.error_api);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                tv_ok = dialog.findViewById(R.id.tv_ok);
//
//
//                tv_ok.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        dialog.dismiss();
//
//
//                    }
//                });
//                dialog.show();

            }
        });


    }
}

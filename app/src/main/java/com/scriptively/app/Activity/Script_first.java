package com.scriptively.app.Activity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraLogger;
import com.otaliastudios.cameraview.CameraView;

import com.scriptively.app.Api.ApiClient;
import com.scriptively.app.Api.ApiInterface;
import com.scriptively.app.Database.AppDatabase;
import com.scriptively.app.Database.ScriptDBDao;
import com.scriptively.app.Pojo.EditScript_Pojo;
import com.scriptively.app.R;
import com.scriptively.app.Utils.Global_Constants;
import com.scriptively.app.Utils.Shared_PrefrencePrompster;
import com.scriptively.app.Utils.Util;
import com.scriptively.app.Utils.VideoCapture;
import com.squareup.picasso.Picasso;

import android.widget.RelativeLayout.LayoutParams;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class Script_first extends AppCompatActivity {
    private static final String TAG = Script_first.class.getSimpleName();
    ImageView mice_inactive, cross, iv_text, iv_crosstext, mic, pause, pause_recording,
            voice_recording, stop_voice_recording, pause_voice_recording, iv_rec, pause_vr;
    RelativeLayout rel_small_camera, rl_manualspeed;
    RelativeLayout rel_bottom, rl_visible, rl_data, rel_bottom_down, rl_main, rel_vr, rel_record, main_relative;
    ImageView iv_back, iv_vr;
    Shared_PrefrencePrompster shared_prefrencePrompster;
    String title, desc;
    Intent intent;
    int REQUEST_MICROPHONE = 101;
    TextView et_firstscreen;
    Chronometer timer;
    static AppDatabase db;
    TextView tv_script, tv_ok, tv_path, tv_recording, tv_runon, tv_runto;
    int REQUEST_CAMERA_PERMISSION = 101;
    MediaRecorder recorder;
    SurfaceHolder holder;
    boolean recording = false;
    String pathVideo, camera_tag = "";
    private int cameraType = 1;
    ImageView iv_camera;
    Camera camera;
    SeekBar seekbar;
    String scriptTextSize;
    static int seekbar_pause = 0;
    SeekBar seekbarr_pause, seekbar_vr, seekbar_margin;
    ImageView iv_left, iv_right, iv_centre, cross_white, iv_cancel_record, video_record;
    String script, storyboard, media, user_id, script_id;
    File file;
    int line = 0, flag = 0, previousLine = 0;
    public static final int RequestPermissionCode = 7;
    ObjectAnimator anim;
    ScrollView main_scrollView;
    Toolbar toolbar;
    LinearLayout layout;
    private Animation mAnimation;
    boolean click = true;
    boolean vrr = false;
    CameraView surface_camera;
    CameraView small_camera;
    SwitchCompat chk_invert, chk_marker, chk_manual, chk_vr, switch_full_screen, switch_record_me, switch_record_me_audio, chk_mannual_record;
    public static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    public MediaRecorder mediaRecorder = new MediaRecorder();
    private Camera mCamera;
    private VideoCapture videoCapture;
    private SurfaceHolder surfaceHolder;
    Bitmap btmp = null;
    public static File des;
    private MediaPlayer mPlayer;
    private static final String LOG_TAG = "AudioRecording";
    private static String mFileName = null;
    public static final int REQUEST_AUDIO_PERMISSION_CODE = 1;
    String AudioSavePathInDevice = null, camera_open = "0", descolor, alignment;
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    Random random;
    Dialog dialog;
    private long startTime = 0L;
    private Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    int flagSlid = 0, flagcheckOn = 0;
    LayoutParams lp;
    boolean fullOn = false, smallOn = false;

    int lineCount;
    private String selectedFontFamily = "";
    private boolean restrictScrollAnimation = false;
    private int scrollOffset = 0;
    private AnimatorSet mAnimator;
    private static final int MAX_SCROLL_DURATION_MULTIPLIER = 26; // slowest multiplier
    private String promptTextSize;
    private String story_board_id = "";
    private String scriptPrimaryKey = "";


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pathVideo = "/reg_" + System.currentTimeMillis() + ".mp4";
        recorder = new MediaRecorder();
//        initRecorder();
        setContentView(R.layout.activity_script_first);
        CameraLogger.setLogLevel(CameraLogger.LEVEL_VERBOSE);
        random = new Random();
        shared_prefrencePrompster = Shared_PrefrencePrompster.getInstance(this);
        intent = getIntent();
        title = intent.getStringExtra("title");
        desc = intent.getStringExtra("desc");
        story_board_id = intent.getStringExtra(Global_Constants.STORYBOARD_ID);
        scriptPrimaryKey = intent.getStringExtra(Global_Constants.SCRIPT_PRIMARYKEY);

        scriptTextSize = intent.getStringExtra(Global_Constants.SCRIPT_TEXT_SIZE);
       seekbar_pause= Integer.parseInt(intent.getStringExtra(Global_Constants.PROMPT_SPEED));
        script_id = intent.getStringExtra("script_id");
        //Toast.makeText(this, "script id is"+script_id, Toast.LENGTH_SHORT).show();
        script = intent.getStringExtra("script");
        descolor = intent.getStringExtra("desc_color");
        selectedFontFamily = intent.getStringExtra("fontfamily");
        alignment = intent.getStringExtra("alignment");
        promptTextSize = intent.getStringExtra(Global_Constants.PROMPT_TEXT_SIZE);
        if (promptTextSize.equals("28")) {
            promptTextSize = "35.5";
        }
        user_id = shared_prefrencePrompster.getUserid();


        init();
        db = AppDatabase.getInstance(this);
        main_scrollView.getViewTreeObserver().addOnScrollChangedListener(() -> {

            if (!main_scrollView.canScrollVertically(1)) {

                if (mAnimator != null) {
                    mAnimator.cancel();
                }


                // pausePlay.setImageResource(R.drawable.ic_play_arrow_orange_24dp);
            }
        });

        et_firstscreen.setTextSize(TypedValue.COMPLEX_UNIT_SP, Float.parseFloat(promptTextSize));
        main_scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                long eventDuration = event.getEventTime() - event.getDownTime();
                Log.e("ontouch", "" + eventDuration);


                if (mic.getVisibility() == View.VISIBLE) {
                    return false;
                }

                int x = (int) event.getRawX();
                int y = (int) event.getRawY();
                if (mAnimator != null) {
                    mAnimator.cancel();
                    mAnimator.end();
                    mAnimator = null;

                }


                //  final TextView textView = (TextView) main_scrollView.getChildAt(0);
                //   final int firstVisableLineOffset = textView.getLayout().getLineForVertical(main_scrollView.getScrollY());

                if (event.getAction() == MotionEvent.ACTION_UP) {


                    if (mAnimator == null) {

                        Log.e("seekDetails", "" + seekbar_pause + " " + main_scrollView.getScrollY());
                        duration(seekbar_pause, main_scrollView.getScrollY());

                    }

                }

                return false;
            }
        });

//        anim_slideDown= AnimationUtils.loadAnimation(getApplicationContext(),
//                R.anim.anim_down);

        int textHeight = getTextViewHeight(et_firstscreen);
        int displayHeight = getDisplayHeight(Script_first.this);
//        main_scrollView.smoothScrollTo(0,textHeight);

        //  anim = ObjectAnimator.ofInt(et_firstscreen, "scrollY",  textHeight - (displayHeight) + 120);
        //   et_firstscreen.startAnimation(anim_slideDown);


        //    et_firstscreen.setMovementMethod(ScrollingMovementMethod.getInstance());
//        et_firstscreen.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//        if(anim!=null)
//        {
//                    if(anim.isRunning())
//                {
//                    anim.pause();
//                }
//                else if(anim.isPaused())
//                {
//                    anim.resume();
//                }
//        }
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_UP:
//                        // Action you you want on finger down.
//                        Log.e("onaction", "up");
//
//                        break;
//
//                    case MotionEvent.ACTION_DOWN:
//                        // Action you you want on finger down.
//                        Log.e("onaction", "down" + event.getY());
//
//                        break;
//                    case MotionEvent.ACTION_CANCEL:
//
//                        // Action you you want on finger up
//                        break;
//                }
//
//                return true;
//            }
//        });


        et_firstscreen.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), selectedFontFamily));
        if (promptTextSize != null || promptTextSize.equals("")) {
            et_firstscreen.setTextSize(TypedValue.COMPLEX_UNIT_SP, Float.parseFloat(promptTextSize));
            seekbar.setProgress((int) Double.parseDouble(promptTextSize));

        }
Log.e("seekbarPausevalue", String.valueOf(seekbar_pause));
            seekbar_vr.setProgress((int) seekbar_pause);


        mediaRecorder = new MediaRecorder();
        //    et_firstscreen.setMovementMethod(new ScrollingMovementMethod());


        mice_inactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera_open = "4";
                if (CheckPermissions()) {
                    Picasso.with(getApplicationContext()).load(R.drawable.text_style_inactive).into(iv_text);
                    Picasso.with(getApplicationContext()).load(R.drawable.status_inactive).into(iv_vr);
                    Picasso.with(getApplicationContext()).load(R.drawable.camera_inactive).into(iv_camera);
                    Picasso.with(getApplicationContext()).load(R.drawable.mic_active).into(mice_inactive);
                    rel_bottom.setVisibility(View.VISIBLE);
                    rl_visible.setVisibility(View.GONE);
                    rel_vr.setVisibility(View.GONE);
                    rel_record.setVisibility(View.GONE);

                } else {
                    RequestPermissions();
                }
            }
        });

        iv_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(getApplicationContext()).load(R.drawable.text_style_active).into(iv_text);
                Picasso.with(getApplicationContext()).load(R.drawable.status_inactive).into(iv_vr);
                Picasso.with(getApplicationContext()).load(R.drawable.camera_inactive).into(iv_camera);
                Picasso.with(getApplicationContext()).load(R.drawable.mic_inactive).into(mice_inactive);
                rl_visible.setVisibility(View.VISIBLE);
                rel_vr.setVisibility(View.GONE);
                rel_record.setVisibility(View.GONE);
                rel_bottom.setVisibility(View.GONE);
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Script_first.this, Script_Toolbar.class);


                intent.putExtra("TAG", "3");
                intent.putExtra(Global_Constants.SCRIPT_TEXT_SIZE, scriptTextSize);
                intent.putExtra("desc_color", descolor);
               intent.putExtra("alignment", alignment);
                intent.putExtra("title", title);
                intent.putExtra("description", desc);
                intent.putExtra("script", script);
                intent.putExtra(Global_Constants.PROMPT_TEXT_SIZE, promptTextSize);
                intent.putExtra(Global_Constants.PROMPT_SPEED, String.valueOf(seekbar_pause));
                intent.putExtra("desc_color", descolor);
//                try {
//                    if (alignment.equals("left")) {
//                        intent.putExtra("alignment", "left");
//                    } else if (alignment.equals("right")) {
//                        intent.putExtra("alignment", "right");
//                    } else {
//                        intent.putExtra("alignment", "center");
//                    }
//                } catch (Exception e) {
//
//                }

                small_camera.stop();
                surface_camera.stop();
                seekbar_pause = 0;
                startActivity(intent);
                finish();
            }
        });

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(getApplicationContext()).load(R.drawable.text_style_inactive).into(iv_text);
                Picasso.with(getApplicationContext()).load(R.drawable.status_inactive).into(iv_vr);
                Picasso.with(getApplicationContext()).load(R.drawable.camera_inactive).into(iv_camera);
                Picasso.with(getApplicationContext()).load(R.drawable.mic_inactive).into(mice_inactive);
                rel_bottom.setVisibility(View.GONE);
            }
        });

        iv_crosstext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(getApplicationContext()).load(R.drawable.text_style_inactive).into(iv_text);
                Picasso.with(getApplicationContext()).load(R.drawable.status_inactive).into(iv_vr);
                Picasso.with(getApplicationContext()).load(R.drawable.camera_inactive).into(iv_camera);
                Picasso.with(getApplicationContext()).load(R.drawable.mic_inactive).into(mice_inactive);
                rl_visible.setVisibility(View.GONE);
                editScriptData();
            }
        });
    }

    void editScriptData() {
        try {
            EditScriptapi(user_id, script_id, story_board_id, title, desc, desc, scriptTextSize, promptTextSize, String.valueOf(seekbar_pause), "10.0", "false", "140.0", "", "desc_color", alignment);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void EditScriptapi(String userid, String script_id, String story_board_id, String scrTitle, String script_text, String bold,
                               String scriptTextSize, String promptTextSize, String promptspeed, String s3, String aFalse, String s4, String s5, String desc_color, String alignment_tagg) throws JSONException {


        if (Util.isConnected()) {

            new Script_first.EditSriptAsync(this, scrTitle, Integer.parseInt(scriptPrimaryKey), userid, script_id, story_board_id, script_text, bold, 1, alignment_tagg, desc_color, scriptTextSize, promptTextSize, promptspeed, s3, s4, s5, aFalse).execute();
        } else {
            new Script_first.EditSriptAsync(this, scrTitle, Integer.parseInt(scriptPrimaryKey), userid, script_id, story_board_id, script_text, bold, 0, alignment_tagg, desc_color, scriptTextSize, promptTextSize, promptspeed, s3, s4, s5, aFalse).execute();
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }

    public class EditSriptAsync extends AsyncTask<Void, Void, Integer> {

        Context context;
        String title;
        int key;
        String userid;
        String addScript_id;
        String story_board_id, scrText;
        String description, alignmentTag, decColor, scriptTextsize, promptTextSize, promptSpeed, s3, s4, s5, aFalse;
        int editValue;

//        EditScriptName(Context context, String title, int key)
//        {
//            this.context = context;
//            this.key = key;
//        }

        public EditSriptAsync(Context navigation, String scrTitle, int primarykey, String userid, String addScript_id, String story_board_id,
                              String scrText, String description, int editValue, String alignmentTag, String decColor, String scriptTextsize, String promptTextSize, String promptSpeed, String s3, String s4, String s5, String aFalse) {

            this.context = navigation;
            this.key = primarykey;
            this.title = scrTitle;
            this.userid = userid;
            this.addScript_id = addScript_id;
            this.story_board_id = story_board_id;
            this.editValue = editValue;
            this.scrText = scrText;
            this.description = description;
            this.alignmentTag = alignmentTag;
            this.decColor = decColor;
            this.scriptTextsize = scriptTextsize;
            this.promptTextSize = promptTextSize;
            this.promptSpeed = promptSpeed;
            this.s3 = s3;
            this.s4 = s4;
            this.s5 = s5;
            this.aFalse = aFalse;

        }

        @Override
        protected Integer doInBackground(Void... voids) {
            ScriptDBDao scriptDBDao = db.scriptDBDao();
            Log.e("updatingScript22 ", "" + key+"   "+promptSpeed);
            return scriptDBDao.updateScript(title, editValue, description, alignmentTag, decColor, scriptTextsize, promptTextSize, key,promptSpeed);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            if (integer > 0) {
                Log.e("Success", "True" + integer);

                if (Util.isConnected()) {


                    MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
                    JSONObject mySelection = new JSONObject();
                    try {
                        mySelection.put("user_id", userid);
                        mySelection.put("script_id", script_id);
                        mySelection.put("scrTitle", title);
                        mySelection.put("scrText", scrText);
                        mySelection.put("scrAttrText", description);
                        mySelection.put("scrEditTextSize", scriptTextsize);
                        mySelection.put("scrPromptTextSize", promptTextSize);
                        mySelection.put("scrPromptSpeed", promptSpeed);
                        mySelection.put("textMargin", s3);
                        mySelection.put("marker", aFalse);
                        mySelection.put("markerX", s4);
                        mySelection.put("uuid", s5);
                        mySelection.put("scrManualScrolling", "false");
                        mySelection.put("scrCameraRecordMe", "false");
                        mySelection.put("scrShowMeFullScreen", "false");
                        mySelection.put("scrShowMeThumbnail", "false");
                        mySelection.put("scrVoiceRecordMe", "false");
                        mySelection.put("scrMirror", "false");
                        mySelection.put("scrInvert", "false");
                        if (decColor != null) {
                            mySelection.put("scrColor", decColor);
                        } else {
                            mySelection.put("scrColor", "-16777216");
                        }
                        if (alignment != null) {

                            mySelection.put("scrAlignment", alignmentTag);
                        } else {
                            mySelection.put("scrAlignment", "center");
                        }
                        Log.e("myselection", String.valueOf(mySelection));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    RequestBody body = RequestBody.create(mediaType, String.valueOf(mySelection));

                    ApiInterface apiService =
                            ApiClient.getClient().create(ApiInterface.class);

                    Call<EditScript_Pojo> call = apiService.edit_script(body);


//        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//        retrofit2.Call<EditScript_Pojo> call = apiService.edit_script(userid, script_id, story_board_id,
//        scrTitle, script_text, bold, s, s1, s2, s3, aFalse, s4, s5, "false", "false", "false", "false", "false", desc_color, "fss");
                    call.enqueue(new retrofit2.Callback<EditScript_Pojo>() {
                        @Override
                        public void onResponse(Call<EditScript_Pojo> call, Response<EditScript_Pojo> response) {

                            Log.e("Edit Script url", call.request().toString());

                            if (response.isSuccessful()) {

                                Log.w("Edit Script response", new Gson().toJson(response));
                                if (response.body().getSuccess().toString().equals("1")) {
                                    Log.e("Edit Script response", new Gson().toJson(response));

                                    desc = response.body().getData().getScrText().toString();
                                    title = response.body().getData().getScrTitle().toString();
                                    shared_prefrencePrompster.setTag("");

                                } else {


                                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(Script_first.this, SweetAlertDialog.ERROR_TYPE);

                                    sweetAlertDialog.setTitleText(response.body().getMessage().toString());
                                    if (!((Activity) context).isFinishing()) {
                                        sweetAlertDialog.show();
                                    }
                                    Button btn = (Button) sweetAlertDialog.findViewById(R.id.confirm_button);
                                    try {
                                        btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                                    } catch (Exception e) {

                                    }
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<EditScript_Pojo> call, Throwable t) {

                            Log.e("errror", String.valueOf(t));

                        }
                    });
                } else {

                    Toast.makeText(context, "Updated On LocalDatabase", Toast.LENGTH_SHORT).show();
                }

            } else {
                Log.e("Success", "False");
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void init() {
        mice_inactive = findViewById(R.id.mice_inactive);
        iv_text = findViewById(R.id.iv_text);
        et_firstscreen = findViewById(R.id.et_firstscreen);
        timer = findViewById(R.id.timer);
        tv_script = findViewById(R.id.tv_script);
        iv_camera = findViewById(R.id.iv_camera);
        iv_left = findViewById(R.id.iv_left);
        iv_right = findViewById(R.id.iv_right);
        iv_centre = findViewById(R.id.iv_centre);
        seekbar = findViewById(R.id.seekbar);
        toolbar = findViewById(R.id.toolbar);
        cross_white = findViewById(R.id.cross_white);
        iv_cancel_record = findViewById(R.id.iv_cancel_record);
        main_scrollView = findViewById(R.id.main_scrollView);
        video_record = findViewById(R.id.video_record);
        surface_camera = findViewById(R.id.meraCamera);
        small_camera = findViewById(R.id.smallCamera);
        tv_runto = findViewById(R.id.tv_runto);
        tv_runon = findViewById(R.id.tv_runon);
        chk_vr = findViewById(R.id.chk_vr);
        switch_full_screen = findViewById(R.id.switch_full_screen);
        chk_mannual_record = findViewById(R.id.chk_mannual_record);
        switch_record_me = findViewById(R.id.switch_record_me);
        switch_record_me_audio = findViewById(R.id.switch_record_me_audio);
        chk_manual = findViewById(R.id.chk_mannual);
        seekbarr_pause = findViewById(R.id.seekbarr_pause);
        seekbar_margin = findViewById(R.id.seekbar_margin);
        seekbar_vr = findViewById(R.id.seekbar_vr);
        rl_manualspeed = findViewById(R.id.rl_manualspeed);
        et_firstscreen.setText(desc);

//
//        main_scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                Log.e("ondragCalled","scroll "+scrollY);
//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    public void run() {
//                        scrollOffset=scrollOffset+scrollY;
//                        main_scrollView.smoothScrollTo(0,   scrollOffset);       // this method will contain your almost-finished HTTP calls
//                        //handler.postDelayed(this, 1000);
//                    }
//                }, 1000);
//
//
//            }
//        });
        if (descolor == null || descolor.equals(String.valueOf(Color.BLACK)) || descolor.equals("")) {
            et_firstscreen.setTextColor(Color.WHITE);
        } else {
            et_firstscreen.setTextColor(Integer.parseInt(descolor));
        }

//        try {
//            if (alignment.equals("left")) {
//                et_firstscreen.setGravity(Gravity.LEFT);
//
//            } else if (alignment.equals("right")) {
//                et_firstscreen.setGravity(Gravity.RIGHT);
//
//            } else {
//                //  et_firstscreen.setGravity(Gravity.CENTER);
//
//            }
//        } catch (Exception e) {
//
//        }

        if (title.length() > 35)
            tv_script.setText(title.substring(0, 35) + "....");
        else {
            tv_script.setText(title);
        }

//        tv_script.setText(title);
        cross = findViewById(R.id.cross);
        iv_crosstext = findViewById(R.id.iv_crosstext);
        iv_back = findViewById(R.id.iv_back);
        iv_vr = findViewById(R.id.iv_vr);
        mic = findViewById(R.id.mic);
        voice_recording = findViewById(R.id.voice_recording);
        stop_voice_recording = findViewById(R.id.stop_voice_recording);
        pause = findViewById(R.id.pause);
        pause_vr = findViewById(R.id.pause_vr);
        pause_recording = findViewById(R.id.pause_recording);
        pause_voice_recording = findViewById(R.id.pause_voice_recording);
        chk_marker = findViewById(R.id.chk_mirror);
        chk_invert = findViewById(R.id.chk_invert);
        iv_rec = findViewById(R.id.iv_rec);

        rel_bottom = findViewById(R.id.rel_bottom);
        rl_visible = findViewById(R.id.rl_visible);
        rl_data = findViewById(R.id.rl_data);
        rel_bottom_down = findViewById(R.id.rel_bottom_down);
        rl_main = findViewById(R.id.rl_main);
        rel_vr = findViewById(R.id.rel_vr);
        rel_record = findViewById(R.id.rel_record);
        rel_small_camera = findViewById(R.id.rel_small_camera);
//        rel_small_camera.setBackgroundResource(R.drawable.camera_bg);


        surface_camera.addCameraListener(new CameraListener() {
            @Override
            public void onVideoTaken(File video) {
                saveVideo(video);
            }
        });

        small_camera.addCameraListener(new CameraListener() {
            @Override
            public void onVideoTaken(File video) {
                Log.e("Videofile", "created");
                Log.e("Videofile", video.toString());
                saveVideo(video);
            }
        });

        small_camera.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int width = size.x;
                int height = size.y;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    small_camera.setEnabled(false);

                    if (flagcheckOn == 1) {
                        if (flagSlid == 0) {
                            if (rl_visible.getVisibility() == View.VISIBLE || rel_vr.getVisibility() == View.VISIBLE || rel_record.getVisibility() == View.VISIBLE || rel_bottom.getVisibility() == View.VISIBLE) {
                                ObjectAnimator animation = ObjectAnimator.ofFloat(rel_small_camera, "translationX", width / 2 + 85);
                                animation.setDuration(2000);
                                animation.start();
                                flagSlid = 1;
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        small_camera.setEnabled(true);
                                    }
                                }, 2000);
                            } else {
                                ObjectAnimator animation = ObjectAnimator.ofFloat(rel_small_camera, "translationX", width / 2 + 85);
                                animation.setDuration(2000);
                                animation.start();
                                flagSlid = 1;
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        small_camera.setEnabled(true);
                                    }
                                }, 2000);
                            }
                        } else if (flagSlid == 1) {
                            if (rl_visible.getVisibility() == View.VISIBLE) {
                                Log.e("Height.........", height + "");
                                ObjectAnimator animation = ObjectAnimator.ofFloat(rel_small_camera, "translationY", -(height / 4 - 130));
                                animation.setDuration(2000);
                                animation.start();
                                flagSlid = 2;
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        small_camera.setEnabled(true);
                                    }
                                }, 2000);
                            } else if (rel_vr.getVisibility() == View.VISIBLE) {
                                ObjectAnimator animation = ObjectAnimator.ofFloat(rel_small_camera, "translationY", -(height / 4 - 100));
                                animation.setDuration(2000);
                                animation.start();
                                flagSlid = 2;
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        small_camera.setEnabled(true);
                                    }
                                }, 2000);
                            } else if (rel_record.getVisibility() == View.VISIBLE) {
                                ObjectAnimator animation = ObjectAnimator.ofFloat(rel_small_camera, "translationY", -(height / 4 - 40));
                                animation.setDuration(2000);
                                animation.start();
                                flagSlid = 2;
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        small_camera.setEnabled(true);
                                    }
                                }, 2000);
                            } else if (rel_bottom.getVisibility() == View.VISIBLE) {
                                ObjectAnimator animation = ObjectAnimator.ofFloat(rel_small_camera, "translationY", -(height / 3 + 80));
                                animation.setDuration(2000);
                                animation.start();
                                flagSlid = 2;
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        small_camera.setEnabled(true);
                                    }
                                }, 2000);
                            } else if (toolbar.getVisibility() != View.VISIBLE) {
                                ObjectAnimator animation = ObjectAnimator.ofFloat(rel_small_camera, "translationY", -height / 2 - 250);
                                animation.setDuration(2000);
                                animation.start();
                                flagSlid = 2;
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        small_camera.setEnabled(true);
                                    }
                                }, 2000);
                            } else {
                                ObjectAnimator animation = ObjectAnimator.ofFloat(rel_small_camera, "translationY", -height / 2 - 110);
                                animation.setDuration(2000);
                                animation.start();
                                flagSlid = 2;
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        small_camera.setEnabled(true);
                                    }
                                }, 2000);
                            }
                        } else if (flagSlid == 2) {
                            ObjectAnimator animation = ObjectAnimator.ofFloat(rel_small_camera, "translationX", 0f);
                            animation.setDuration(2000);
                            animation.start();
                            flagSlid = 3;
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    small_camera.setEnabled(true);
                                }
                            }, 2000);
                        } else {
                            ObjectAnimator animation = ObjectAnimator.ofFloat(rel_small_camera, "translationY", 0f);
                            animation.setDuration(2000);
                            animation.start();
                            flagSlid = 0;
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    small_camera.setEnabled(true);
                                }
                            }, 2000);
                        }
                        // Do what you want
                        return true;
                    }
                }
                return false;
            }
        });
        //        layout = findViewById(R.id.layout);
        mic.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {
                if (!CheckPermissions()) {
                    RequestPermissions();
                    return;
                }

                flag = 1;

                rel_vr.setVisibility(View.GONE);
                rl_visible.setVisibility(View.GONE);
                rel_bottom.setVisibility(View.GONE);
                rel_record.setVisibility(View.GONE);


//voice recognition
                if (vrr == true) {
                    rl_main.setVisibility(View.GONE);
                    toolbar.setVisibility(View.GONE);

                    startVoiceRecognitionActivity();

                } else {
                    rl_main.setVisibility(View.GONE);
                    toolbar.setVisibility(View.GONE);
                    mic.setVisibility(View.GONE);
                    pause.setVisibility(View.VISIBLE);
//                    et_firstscreen.setMovementMethod(new ScrollingMovementMethod());
                    Log.e("VALUE>..............", String.valueOf(et_firstscreen.getLayout().getHeight()));
                    seekbarr_pause.setVisibility(View.VISIBLE);
                    // iv_rec.setVisibility(View.VISIBLE);
                    timer.setVisibility(View.VISIBLE);
                    timer.setBase(SystemClock.elapsedRealtime());
                    timer.start();
                    timer.setFormat("%s");

                    duration(seekbar_pause, 0);
                }


            }
        });

        iv_vr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Picasso.with(getApplicationContext()).load(R.drawable.text_style_inactive).into(iv_text);
                Picasso.with(getApplicationContext()).load(R.drawable.status_inactive).into(iv_vr);
                Picasso.with(getApplicationContext()).load(R.drawable.camera_inactive).into(iv_camera);
                Picasso.with(getApplicationContext()).load(R.drawable.mic_inactive).into(mice_inactive);
                rel_vr.setVisibility(View.VISIBLE);
                rl_visible.setVisibility(View.GONE);
                rel_bottom.setVisibility(View.GONE);
                rel_record.setVisibility(View.GONE);
            }
        });
        cross_white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rel_vr.setVisibility(View.GONE);
                editScriptData();
            }
        });
        iv_cancel_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Picasso.with(getApplicationContext()).load(R.drawable.text_style_inactive).into(iv_text);
                Picasso.with(getApplicationContext()).load(R.drawable.status_inactive).into(iv_vr);
                Picasso.with(getApplicationContext()).load(R.drawable.camera_inactive).into(iv_camera);
                Picasso.with(getApplicationContext()).load(R.drawable.mic_inactive).into(mice_inactive);
                rel_record.setVisibility(View.GONE);
                if (flagSlid == 2 || flagSlid == 3) {
                    lp = (LayoutParams) rel_small_camera.getLayoutParams();
                    lp.addRule(RelativeLayout.BELOW, toolbar.getId());
                    rel_small_camera.setLayoutParams(lp);
                }
            }
        });


        et_firstscreen.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {

                Log.e("Flag", "" + flag);

                if (flag == 1) {
                    if (click) {
                        if (mAnimator != null) {
                            mAnimator.pause();

                            //    anim.end();
//                            rl_main.setVisibility(View.GONE);
//                            toolbar.setVisibility(View.GONE);
//                            mic.setVisibility(View.GONE);
//                            pause.setVisibility(View.VISIBLE);
//                            seekbarr_pause.setVisibility(View.VISIBLE);
//                            et_firstscreen.setMovementMethod(new ScrollingMovementMethod());
//                            Log.e("VALUE>..............", String.valueOf(et_firstscreen.getLayout().getHeight()));
//
//                            int textHeight = getTextViewHeight(et_firstscreen);
//                            int displayHeight = getDisplayHeight(Script_first.this);
////                        anim = ObjectAnimator.ofInt(et_firstscreen, "scrollY", textHeight + displayHeight);
//
//                            duration(625 - seekbar_pause);
                            click = false;
                        }
                    } else {
                        if (mAnimator != null) {
                            mAnimator.resume();
//                            toolbar.setVisibility(View.GONE);
//                            mic.setVisibility(View.GONE);
//                            pause.setVisibility(View.VISIBLE);
//                            seekbarr_pause.setVisibility(View.VISIBLE);
//                            rl_main.setVisibility(View.GONE);
//                            anim.pause();
                            click = true;
                        }
                    }
                }
            }
        });


        pause.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Log.e("data", "pause");
                if (mAnimator != null) {
                    mAnimator.cancel();
                    main_scrollView.smoothScrollTo(0, 0);
                }
                flag = 0;

                toolbar.setVisibility(View.VISIBLE);
                mic.setVisibility(View.VISIBLE);
                pause.setVisibility(View.GONE);
                iv_rec.setVisibility(View.GONE);
                seekbarr_pause.setVisibility(View.GONE);
                timer.stop();
                seekbarr_pause.setProgress(0);
                timer.setVisibility(View.GONE);
                rl_main.setVisibility(View.VISIBLE);
                if (anim != null) {
                    anim.pause();
                }

            }
        });

        pause_vr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("data", "pause vr");
                //   mSpeechRecognizer.stopListening();
                mSpeechRecognizer.destroy();
                rl_main.setVisibility(View.VISIBLE);
                toolbar.setVisibility(View.VISIBLE);
                mic.setVisibility(View.VISIBLE);
                pause_vr.setVisibility(View.GONE);
                timer.setVisibility(View.GONE);
                timer.stop();
                seekbarr_pause.setProgress(0);
                if (mAnimator != null) {
                    mAnimator.cancel();
                    main_scrollView.smoothScrollTo(0, 0);
                }

            }
        });

        pause_recording.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                Log.e("data", "pause recording");
                flag = 0;
                if (camera_tag.equals("full_record")) {
                    surface_camera.stopCapturingVideo();
                } else if (camera_tag.equals("small_record")) {
                    small_camera.stopCapturingVideo();
                } else if (camera_tag.equals("record")) {
                    surface_camera.stopCapturingVideo();
                }
                video_record.setVisibility(View.VISIBLE);
                rl_main.setVisibility(View.VISIBLE);
                toolbar.setVisibility(View.VISIBLE);
                timer.setVisibility(View.GONE);
                iv_rec.setVisibility(View.GONE);
                mic.setVisibility(View.GONE);
                pause.setVisibility(View.GONE);
                //    et_firstscreen.setMovementMethod(new ScrollingMovementMethod());
                Log.e("VALUE>..............", String.valueOf(et_firstscreen.getLayout().getHeight()));
                seekbarr_pause.setVisibility(View.GONE);
                pause_recording.setVisibility(View.GONE);
                if (anim != null) {
                    anim.pause();
                }
                if (mAnimator != null) {
                    mAnimator.cancel();
                    main_scrollView.smoothScrollTo(0, 0);
                }

                timer.stop();

                dialog = new Dialog(Script_first.this);


                dialog.setContentView(R.layout.email_empty);
                dialog.setCanceledOnTouchOutside(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                tv_ok = dialog.findViewById(R.id.tv_ok);
                tv_path = dialog.findViewById(R.id.tv_path);
                tv_recording = dialog.findViewById(R.id.tv_recording);

                tv_recording.setText("Recording");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    tv_path.setText("Recording saved successfully. To view, go to the Scriptevly folder in Downloads on your device.");

                } else {
                    tv_path.setText("Recording saved successfully. To view, go to the Files app in your device inside Scriptively folder.");

                }

                tv_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                    }
                });
                dialog.show();


//                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), new File(Uri.fromFile(file).getPath()));
//                    // MultipartBody.Part is used to send also the actual filename
//                    MultipartBody.Part body = MultipartBody.Part.createFormData("redData", file.getName(), requestFile);
//                    RequestBody ruser = RequestBody.create(MediaType.parse("text/plain"),
//                            user_id);
//                    RequestBody rscr = RequestBody.create(MediaType.parse("text/plain"),
//                            script_id);
//                    RequestBody rname = RequestBody.create(MediaType.parse("text/plain"),
//                            file.getName());
//                    uploadVideo(ruser, rscr, rname, body);


            }
        });

        pause_voice_recording.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                Log.e("data", "puase voice recording");

                flag = 0;


                if (camera_tag.equals("full_record")) {
                    surface_camera.stopCapturingVideo();
                } else if (camera_tag.equals("small_record")) {
                    small_camera.stopCapturingVideo();
                } else if (camera_tag.equals("record")) {
                    surface_camera.stopCapturingVideo();
                }
                rl_main.setVisibility(View.VISIBLE);
                toolbar.setVisibility(View.VISIBLE);
                mic.setVisibility(View.GONE);
                pause.setVisibility(View.GONE);
                iv_rec.setVisibility(View.GONE);
                timer.setVisibility(View.GONE);
                timer.stop();
                if (mAnimator != null) {
                    mAnimator.cancel();
                    main_scrollView.smoothScrollTo(0, 0);
                }
                //  et_firstscreen.setMovementMethod(new ScrollingMovementMethod());
                Log.e("VALUE>..............", String.valueOf(et_firstscreen.getLayout().getHeight()));
                seekbarr_pause.setVisibility(View.GONE);
                pause_recording.setVisibility(View.GONE);
                anim.pause();

                dialog = new Dialog(Script_first.this);


                dialog.setContentView(R.layout.email_empty);
                dialog.setCanceledOnTouchOutside(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                tv_ok = dialog.findViewById(R.id.tv_ok);
                tv_path = dialog.findViewById(R.id.tv_path);
                tv_recording = dialog.findViewById(R.id.tv_recording);
                tv_recording.setText("Recording");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    tv_path.setText("Recording saved successfully. To view, go to the Scriptevly folder in Downloads on your device.");

                } else {
                    tv_path.setText("Recording saved successfully. To view, go to the Files app in your device inside Scriptively folder.");

                }


                tv_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                    }
                });
                dialog.show();

            }
        });


        video_record.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Log.e("data", "video record");

//                if (switch_full_screen.isChecked()||chk_mannual_record.isChecked()) {

                flag = 1;

                rel_vr.setVisibility(View.GONE);
                rl_visible.setVisibility(View.GONE);
                rel_bottom.setVisibility(View.GONE);
                rel_record.setVisibility(View.GONE);

                try {


                    rl_main.setVisibility(View.GONE);
                    toolbar.setVisibility(View.GONE);
                    mic.setVisibility(View.GONE);
                    pause.setVisibility(View.GONE);
                    //   et_firstscreen.setMovementMethod(new ScrollingMovementMethod());
                    Log.e("VALUE>..............", camera_tag.toString());
                    video_record.setVisibility(View.GONE);
                    seekbarr_pause.setVisibility(View.VISIBLE);
                    pause_recording.setVisibility(View.VISIBLE);
                    timer.setVisibility(View.VISIBLE);
                    iv_rec.setVisibility(View.VISIBLE);

                    int textHeight = getTextViewHeight(et_firstscreen);
                    int displayHeight = getDisplayHeight(Script_first.this);
//                    anim = ObjectAnimator.ofInt(et_firstscreen, "scrollY", textHeight + displayHeight);
                    Log.e("zzzzzz", "first2");
                    duration(seekbar_pause, 0);

                    if (camera_tag.equals("full_record")) {
                        rel_bottom_down.setBackgroundColor(Color.TRANSPARENT);
                        main_scrollView.setBackgroundColor(Color.TRANSPARENT);
                        surface_camera.startCapturingVideo();
                    } else if (camera_tag.equals("small_record")) {

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                small_camera.startCapturingVideo();
                            }
                        }, 2000);
                    } else if (camera_tag.equals("record")) {
                        rel_bottom_down.setBackgroundColor(getResources().getColor(R.color.black));
                        main_scrollView.setBackgroundColor(getResources().getColor(R.color.black));
                        surface_camera.setVisibility(View.VISIBLE);
//                            surface_camera.start();
                        surface_camera.startCapturingVideo();
                    }

                    timer.setBase(SystemClock.elapsedRealtime());
                    timer.start();
                    timer.setFormat("%s");
                    if (switch_record_me.isChecked() || switch_record_me_audio.isChecked()) {
                        iv_rec.setVisibility(View.VISIBLE);
                    }

//                    startRecordng();
//                    surface_camera.open();
                } catch (Exception e) {
                    String message = e.getMessage();
                    Log.i(null, "Problem " + message);
                    mediaRecorder.release();
                    e.printStackTrace();
                }
            }


//            }
        });


        seekbarr_pause.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                seekbarr_pause.setBackgroundColor(getResources().getColor(R.color.black));

                if (fromUser) {
                    seekbar_pause = progress;
                    mAnimator.cancel();
                    duration(progress, main_scrollView.getScrollY());
                }
//
//                if (!restrictScrollAnimation) {
//
//                    seekbar_pause = progress * 25;
//                    Log.e("SeekBar_Pause", String.valueOf(seekbar_pause));
//                    Log.e("zzzzzz", "first3");
//                    duration2(625 - seekbar_pause);
//                }
//                restrictScrollAnimation = false;


            }
        });
        //  seekbar_vr.setEnabled(false);
        seekbar_vr.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                seekbarr_pause.setBackgroundColor(getResources().getColor(R.color.black));
                restrictScrollAnimation = true;
                seekbarr_pause.setProgress(progress);
                seekbar_pause = progress;
            }
        });

        seekbar_margin.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                Log.e("Margin..........", String.valueOf(progress));

                et_firstscreen.setPadding(progress, 0, progress, 0);

            }
        });
        seekbar_vr.setProgressDrawable(getDrawable(R.drawable.disabled_seekbar));

        chk_vr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (CheckPermissions()) {
//                    Toast.makeText(getApplicationContext(), "Permissions Granted ", Toast.LENGTH_SHORT).show();
                if (isChecked == true) {
                    vrr = true;
                    seekbar_vr.setEnabled(false);
                    chk_manual.setChecked(false);
                    seekbar_vr.setProgressDrawable(getDrawable(R.drawable.disabled_seekbar));
                    //   rl_manualspeed.setVisibility(View.GONE);
//                    seekbar_vr.setEnabled(false);
                    chk_vr.getTrackDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.switch_color), PorterDuff.Mode.SRC_IN);
                    chk_vr.getThumbDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), PorterDuff.Mode.SRC_IN);
//                    seekbar_vr.setThumb(getDrawable(R.drawable.custom_thumb_white));

                } else {
                    vrr = false;
                    seekbar_vr.setProgressDrawable(getDrawable(R.drawable.custom_seekbar));
                    seekbar_vr.setEnabled(true);
                    //  rl_manualspeed.setVisibility(View.VISIBLE);
                    chk_manual.setChecked(true);
//                    seekbar_vr.setEnabled(true);
                    chk_vr.getTrackDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.tracktint), PorterDuff.Mode.SRC_IN);
                    chk_vr.getThumbDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), PorterDuff.Mode.SRC_IN);
                }
//                } else {
//                    RequestPermissions();
//                }
            }
        });

        switch_record_me.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                camera_open = "0";
                if (CheckPermissions()) {
//                    Toast.makeText(getApplicationContext(), "Permissions Granted ", Toast.LENGTH_SHORT).show();
                    if (isChecked) {
//                        camera_tag = "3";


                        if (chk_mannual_record.isChecked()) {
                            camera_tag = "small_record";
                        } else if (switch_full_screen.isChecked()) {
                            camera_tag = "full_record";
                        } else {
                            camera_tag = "record";
                        }
                        iv_rec.setVisibility(View.VISIBLE);
                        voice_recording.setVisibility(View.GONE);
                        mic.setVisibility(View.GONE);
                        video_record.setVisibility(View.VISIBLE);
                        switch_record_me_audio.setChecked(false);
                        switch_record_me.getTrackDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.switch_color), PorterDuff.Mode.SRC_IN);
                        switch_record_me.getThumbDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), PorterDuff.Mode.SRC_IN);
                    } else {
                        camera_tag = "";
                        iv_rec.setVisibility(View.INVISIBLE);
//                        surface_camera.stop();
                        mic.setVisibility(View.VISIBLE);
                        video_record.setVisibility(View.GONE);

                        switch_record_me.getTrackDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.tracktint), PorterDuff.Mode.SRC_IN);
                        switch_record_me.getThumbDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), PorterDuff.Mode.SRC_IN);

                    }
                } else {
                    RequestPermissions();
                }
            }
        });

        switch_record_me_audio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (CheckPermissions()) {
//                    Toast.makeText(getApplicationContext(), "Permissions Granted ", Toast.LENGTH_SHORT).show();
                    if (isChecked == true) {

                        mic.setVisibility(View.GONE);
                        voice_recording.setVisibility(View.VISIBLE);

                        chk_mannual_record.setChecked(false);
                        switch_full_screen.setChecked(false);
                        switch_record_me.setChecked(false);
//                        Picasso.with(getApplicationContext()).load(R.drawable.mic_active_orange).into(mice_inactive);

                        switch_record_me_audio.getTrackDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.switch_color), PorterDuff.Mode.SRC_IN);
                        switch_record_me_audio.getThumbDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), PorterDuff.Mode.SRC_IN);

                    } else {

                        mic.setVisibility(View.VISIBLE);
                        voice_recording.setVisibility(View.GONE);

//                    Picasso.with(getApplicationContext()).load(R.drawable.mic_inactive).into(mice_inactive);
                        switch_record_me_audio.getTrackDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.tracktint), PorterDuff.Mode.SRC_IN);
                        switch_record_me_audio.getThumbDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), PorterDuff.Mode.SRC_IN);

                    }
                } else {
                    RequestPermissions();
                }
            }
        });

        voice_recording.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                Log.e("data", "voice recording");
                flag = 1;
                rel_vr.setVisibility(View.GONE);
                rl_visible.setVisibility(View.GONE);
                rel_bottom.setVisibility(View.GONE);
                rel_record.setVisibility(View.GONE);
                if (CheckPermissions()) {

                    if (switch_record_me.isChecked() || switch_record_me_audio.isChecked()) {
                        iv_rec.setVisibility(View.VISIBLE);
                    }

                    rl_main.setVisibility(View.GONE);
                    toolbar.setVisibility(View.GONE);
                    mic.setVisibility(View.GONE);
                    pause.setVisibility(View.GONE);
                    pause_recording.setVisibility(View.GONE);
                    pause_vr.setVisibility(View.GONE);
                    pause_voice_recording.setVisibility(View.GONE);
                    switch_record_me_audio.setVisibility(View.GONE);
                    stop_voice_recording.setVisibility(View.VISIBLE);
                    iv_rec.setVisibility(View.VISIBLE);
                    timer.setVisibility(View.VISIBLE);

                    timer.setBase(SystemClock.elapsedRealtime());
                    timer.start();
                    timer.setFormat("%s");

                    //   et_firstscreen.setMovementMethod(new ScrollingMovementMethod());
                    Log.e("VALUE>..............", String.valueOf(et_firstscreen.getLayout().getHeight()));
                    seekbarr_pause.setVisibility(View.VISIBLE);

                    int textHeight = getTextViewHeight(et_firstscreen);
                    int displayHeight = getDisplayHeight(Script_first.this);
//                    anim = ObjectAnimator.ofInt(et_firstscreen, "scrollY", textHeight + displayHeight);
                    Log.e("zzzzzz", "first4");
                    duration(seekbar_pause, 0);

                    File filepath;
                    File dir;

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        filepath = Environment.getExternalStorageDirectory();


                        dir = new File(filepath.getAbsolutePath() + "/Download/" + "Scriptively" + "/audio/");
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                    } else {
                        filepath = Environment.getExternalStorageDirectory();
                        dir = new File(filepath.getAbsolutePath() + "/" + "Scriptively" + "/audio/");
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                    }
                    file = new File(dir, title + System.currentTimeMillis() + ".mp3");
                    AudioSavePathInDevice = file.getAbsolutePath();

                    mediaRecorder = new MediaRecorder();
                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                    mediaRecorder.setOutputFile(AudioSavePathInDevice);

                    try {
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                    } catch (IllegalStateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    Toast.makeText(Script_first.this, "Recording started", Toast.LENGTH_LONG).show();
                } else {
                    RequestPermissions();
                }
            }
        });

        stop_voice_recording.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if (mAnimator != null) {
                    mAnimator.cancel();
                    main_scrollView.smoothScrollTo(0, 0);
                }
                Log.e("data", "onstop");
                rl_main.setVisibility(View.VISIBLE);
                toolbar.setVisibility(View.VISIBLE);
                mic.setVisibility(View.GONE);
                pause.setVisibility(View.GONE);
                switch_record_me_audio.setVisibility(View.VISIBLE);
                stop_voice_recording.setVisibility(View.GONE);
                iv_rec.setVisibility(View.GONE);
                try {
                    mediaRecorder.stop();
                } catch (Exception e) {

                }
                timer.setVisibility(View.GONE);
                timer.stop();
                //       et_firstscreen.setMovementMethod(new ScrollingMovementMethod());
                Log.e("VALUE>..............", String.valueOf(et_firstscreen.getLayout().getHeight()));
                seekbarr_pause.setVisibility(View.GONE);
                if (anim != null) {
                    anim.pause();
                }

                dialog = new Dialog(Script_first.this);
                dialog.setContentView(R.layout.email_empty);
                dialog.setCanceledOnTouchOutside(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                tv_ok = dialog.findViewById(R.id.tv_ok);
                tv_path = dialog.findViewById(R.id.tv_path);
                tv_recording = dialog.findViewById(R.id.tv_recording);
                tv_recording.setText("Recording");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    tv_path.setText("Recording saved successfully. To view, go to the Scriptevly folder in Downloads on your device.");

                } else {
                    tv_path.setText("Recording saved successfully. To view, go to the Files app in your device inside Scriptively folder.");

                }

                tv_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                    }
                });
                dialog.show();


                File file = new File(AudioSavePathInDevice);


                try {
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), new File(Uri.fromFile(file).getPath()));
                    // MultipartBody.Part is used to send also the actual filename
                    MultipartBody.Part body = MultipartBody.Part.createFormData("redData", file.getName(), requestFile);
                    RequestBody ruser = RequestBody.create(MediaType.parse("text/plain"),
                            user_id);
                    RequestBody rscr = RequestBody.create(MediaType.parse("text/plain"),
                            script_id);
                    RequestBody rname = RequestBody.create(MediaType.parse("text/plain"),
                            file.getName());
                    uploadVideo(ruser, rscr, rname, body);
                } catch (Exception e) {

                }


            }
        });

        switch_full_screen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                camera_open = "2";
                if (CheckPermissions()) {

//                   Toast.makeText(getApplicationConte xt(), "Permissions Granted ", Toast.LENGTH_SHORT).show();
                    if (isChecked == true) {


                        smallOn = false;
                        fullOn = true;

                        chk_mannual_record.setChecked(false);
                        switch_record_me_audio.setChecked(false);
                        rel_small_camera.setVisibility(View.GONE);
                        small_camera.stop();
                        surface_camera.setVisibility(View.VISIBLE);
                        surface_camera.start();
                        surface_camera.startCapturingVideo();
                        rel_bottom_down.setBackgroundColor(Color.TRANSPARENT);
                        main_scrollView.setBackgroundColor(Color.TRANSPARENT);
                        if (switch_record_me.isChecked()) {
                            camera_tag = "full_record";
//                            surface_camera.stop();
                        } else {
                            camera_tag = "full";
                        }
                        voice_recording.setVisibility(View.GONE);

//                        chk_mannual_record.setChecked(false);
//                        switch_record_me_audio.setChecked(false);
//                        surface_camera.start();

//                        Picasso.with(getApplicationContext()).load(R.drawable.camera_active_orange).into(iv_camera);
                        switch_full_screen.getTrackDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.switch_color), PorterDuff.Mode.SRC_IN);
                        switch_full_screen.getThumbDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), PorterDuff.Mode.SRC_IN);

                        chk_mannual_record.getTrackDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.tracktint), PorterDuff.Mode.SRC_IN);
                        chk_mannual_record.getThumbDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), PorterDuff.Mode.SRC_IN);


                    } else {

//                        surface_camera.setVisibility(View.GONE);
//
//                       surface_camera.destroy();

                        if (fullOn) {

                            switch_full_screen.setChecked(false);
                            surface_camera.stop();
                            surface_camera.setVisibility(View.GONE);
//                            chk_mannual_record.setChecked(true);
                            fullOn = false;

                        }
//                        surface_camera.stopCapturingVideo();

//                    Picasso.with(getApplicationContext()).load(R.drawable.camera_inactive).into(iv_camera);
                        switch_full_screen.getTrackDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.tracktint), PorterDuff.Mode.SRC_IN);
                        switch_full_screen.getThumbDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), PorterDuff.Mode.SRC_IN);
                    }
                } else {
                    RequestPermissions();
                }
            }
        });

        chk_mannual_record.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                camera_open = "1";
                if (CheckPermissions()) {

//                    Toast.makeText(getApplicationContext(), "Permissions Granted ", Toast.LENGTH_SHORT).show();
                    if (isChecked == true) {

                        fullOn = false;
                        smallOn = true;

                        switch_full_screen.setChecked(false);
                        switch_record_me_audio.setChecked(false);
                        surface_camera.stop();
                        surface_camera.setVisibility(View.GONE);
                        rel_bottom_down.setBackgroundColor(Color.TRANSPARENT);
                        main_scrollView.setBackgroundColor(Color.TRANSPARENT);
                        if (switch_record_me.isChecked()) {
//                            small_camera.stop();
                            camera_tag = "small_record";
                        } else {
                            camera_tag = "small";
                        }
                        flagcheckOn = 1;
                        voice_recording.setVisibility(View.GONE);
                        rel_small_camera.setVisibility(View.VISIBLE);
//                        switch_full_screen.setChecked(false);
                        small_camera.start();
                        small_camera.startCapturingVideo();

                        chk_mannual_record.getTrackDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.switch_color), PorterDuff.Mode.SRC_IN);
                        chk_mannual_record.getThumbDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), PorterDuff.Mode.SRC_IN);

                        switch_full_screen.getTrackDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.tracktint), PorterDuff.Mode.SRC_IN);
                        switch_full_screen.getThumbDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), PorterDuff.Mode.SRC_IN);

                    } else {

                        if (smallOn) {
                            chk_mannual_record.setChecked(false);
                            small_camera.stop();
                            rel_small_camera.setVisibility(View.GONE);
//                            switch_full_screen.setChecked(true);
                            flagcheckOn = 0;
                        }
//                        if (switch_full_screen.isChecked())
//                        {
//                            camera_tag = "1";
//                        }
//                        else if(switch_record_me.isChecked()){
//                            camera_tag = "3";
//                        }
//                        else {
//                            camera_tag = "0";
//                        }

//                        rel_small_camera.setVisibility(View.GONE);
//                    Picasso.with(getApplicationContext()).load(R.drawable.camera_inactive).into(iv_camera);
                        chk_mannual_record.getTrackDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.tracktint), PorterDuff.Mode.SRC_IN);
                        chk_mannual_record.getThumbDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), PorterDuff.Mode.SRC_IN);
                    }
                } else {
                    RequestPermissions();
                }
            }
        });

        chk_manual.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked == true) {
                    chk_vr.setChecked(false);
//                    seekbar_vr.setEnabled(true);
                    chk_manual.getTrackDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.switch_color), PorterDuff.Mode.SRC_IN);
                    chk_manual.getThumbDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), PorterDuff.Mode.SRC_IN);
//                    seekbar_vr.setThumb(getDrawable(R.drawable.custom_thumb_white));
                    tv_runon.setBackground(getDrawable(R.drawable.run_one_white));
                    tv_runto.setBackground(getDrawable(R.drawable.run_two_white));
                } else {
                    chk_vr.setChecked(true);
//                    seekbar_vr.setEnabled(false);
                    chk_manual.getTrackDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.tracktint), PorterDuff.Mode.SRC_IN);
                    chk_manual.getThumbDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), PorterDuff.Mode.SRC_IN);
//                    seekbar_vr.setThumb(getDrawable(R.drawable.custom_thumb_grey));
                    tv_runon.setBackground(getDrawable(R.drawable.run_one));
                    tv_runto.setBackground(getDrawable(R.drawable.run_two));
                }
            }

        });
        chk_marker.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked == true) {
                    et_firstscreen.setScaleX(-1);
                    et_firstscreen.setScaleY(1);
                    et_firstscreen.setTranslationX(1);
                    chk_marker.getTrackDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.switch_color), PorterDuff.Mode.SRC_IN);
                    chk_marker.getThumbDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), PorterDuff.Mode.SRC_IN);
                } else {
                    et_firstscreen.setScaleX(1);
                    et_firstscreen.setScaleY(1);
                    et_firstscreen.setTranslationX(-1);
                    chk_marker.getTrackDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.tracktint), PorterDuff.Mode.SRC_IN);
                    chk_marker.getThumbDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), PorterDuff.Mode.SRC_IN);
                }
            }
        });

        chk_invert.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked == true) {
                    et_firstscreen.setScaleX(1);
                    et_firstscreen.setScaleY(-1);
                    et_firstscreen.setTranslationY(-1);
                    chk_invert.getTrackDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.switch_color), PorterDuff.Mode.SRC_IN);
                    chk_invert.getThumbDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), PorterDuff.Mode.SRC_IN);
                } else {
                    et_firstscreen.setScaleX(1);
                    et_firstscreen.setScaleY(1);
                    et_firstscreen.setTranslationY(1);
                    chk_invert.getTrackDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.tracktint), PorterDuff.Mode.SRC_IN);
                    chk_invert.getThumbDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), PorterDuff.Mode.SRC_IN);
                }
            }
        });


        iv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CheckPermissions()) {

                    camera_open = "3";
                    Picasso.with(getApplicationContext()).load(R.drawable.text_style_inactive).into(iv_text);
                    Picasso.with(getApplicationContext()).load(R.drawable.status_inactive).into(iv_vr);
                    Picasso.with(getApplicationContext()).load(R.drawable.camera_active_orange).into(iv_camera);
                    Picasso.with(getApplicationContext()).load(R.drawable.mic_inactive).into(mice_inactive);
                    rel_record.setVisibility(View.VISIBLE);
                    rl_visible.setVisibility(View.GONE);
                    rel_vr.setVisibility(View.GONE);
                    rel_bottom.setVisibility(View.GONE);
                } else {
                    RequestPermissions();
                }

            }
        });

        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_firstscreen.setGravity(Gravity.LEFT);

            }
        });
        iv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_firstscreen.setGravity(Gravity.RIGHT);

            }
        });
        iv_centre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_firstscreen.setGravity(Gravity.CENTER);

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

                promptTextSize = progress + ".0";
                et_firstscreen.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) progress);


            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_AUDIO_PERMISSION_CODE:
                if (grantResults.length > 0) {
                    boolean permissionTowrite = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean permissionTorecord = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean permissionToread = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean permissionTocamera = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    if (permissionTowrite && permissionTorecord && permissionToread && permissionTocamera) {
//                        if (camera_open.equals("0")) {
//                            Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
//
//                            if (CheckPermissions()){
////                                camera_tag ="3";
////                                surface_camera.start();
//
////                                switch_record_me.getTrackDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.switch_color), PorterDuff.Mode.SRC_IN);
////                                switch_record_me.getThumbDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), PorterDuff.Mode.SRC_IN);
//                            }
//
//                        } else if (camera_open.equals("1")){
//                            Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
//                            if (CheckPermissions()){
//                                flagcheckOn = 1;
////                                camera_tag = "2";
//                                rel_small_camera.setVisibility(View.VISIBLE);
//                                switch_full_screen.setChecked(false);
//                                small_camera.start();
//                                small_camera.startCapturingVideo();
//                                chk_mannual_record.getTrackDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.switch_color), PorterDuff.Mode.SRC_IN);
//                                chk_mannual_record.getThumbDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), PorterDuff.Mode.SRC_IN);
//                            }
//                        }
//
//                        else if (camera_open.equals("2")){
////                            camera_tag = "1";
//                            surface_camera.setVisibility(View.VISIBLE);
//                            chk_mannual_record.setChecked(false);
//                            surface_camera.start();
//                            surface_camera.startCapturingVideo();
////                        Picasso.with(getApplicationContext()).load(R.drawable.camera_active_orange).into(iv_camera);
//                            switch_full_screen.getTrackDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.switch_color), PorterDuff.Mode.SRC_IN);
//                            switch_full_screen.getThumbDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), PorterDuff.Mode.SRC_IN);
//                        }
//                        else if (camera_open.equals("3")){
//                            Picasso.with(getApplicationContext()).load(R.drawable.text_style_inactive).into(iv_text);
//                            Picasso.with(getApplicationContext()).load(R.drawable.status_inactive).into(iv_vr);
//                            Picasso.with(getApplicationContext()).load(R.drawable.camera_active_orange).into(iv_camera);
//                            Picasso.with(getApplicationContext()).load(R.drawable.mic_inactive).into(mice_inactive);
//                            rel_record.setVisibility(View.VISIBLE);
//                            rl_visible.setVisibility(View.GONE);
//                            rel_vr.setVisibility(View.GONE);
//                            rel_bottom.setVisibility(View.GONE);
//                        }
//                        else if (camera_open.equals("4")){
//                            Picasso.with(getApplicationContext()).load(R.drawable.text_style_inactive).into(iv_text);
//                            Picasso.with(getApplicationContext()).load(R.drawable.status_inactive).into(iv_vr);
//                            Picasso.with(getApplicationContext()).load(R.drawable.camera_inactive).into(iv_camera);
//                            Picasso.with(getApplicationContext()).load(R.drawable.mic_active).into(mice_inactive);
//                            rel_bottom.setVisibility(View.VISIBLE);
//                            rl_visible.setVisibility(View.GONE);
//                            rel_vr.setVisibility(View.GONE);
//                            rel_record.setVisibility(View.GONE);
//                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean CheckPermissions() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int result3 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED && result3 == PackageManager.PERMISSION_GRANTED;
    }

    private void RequestPermissions() {
        ActivityCompat.requestPermissions(Script_first.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_AUDIO_PERMISSION_CODE);
    }

    SpeechRecognizer mSpeechRecognizer;

    private void saveVideo(File video) {

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(video);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Log.e("videopath", video.getAbsolutePath());
//        String filepath = Environment.getExternalStorageDirectory().getPath();
//        File dir = new File(filepath + "/Download/" + "Scriptively" + "/video/");
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }


        File filepath;
        File dir;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            filepath = Environment.getExternalStorageDirectory();


            dir = new File(filepath.getAbsolutePath() + "/Download/" + "Scriptively" + "/video/");
            if (!dir.exists()) {
                dir.mkdirs();
            }
        } else {
            filepath = Environment.getExternalStorageDirectory();
            dir = new File(filepath.getAbsolutePath() + "/" + "Scriptively" + "/video/");
            if (!dir.exists()) {
                dir.mkdirs();
            }


        }

        file = new File(dir, title + System.currentTimeMillis() + ".mp4");
        Log.e("FilePath", file.getAbsolutePath() + "  " + file.length());

        try {
            FileOutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;

            while ((len = fileInputStream.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            fileInputStream.close();
            out.close();
        } catch (Exception e) {
            Log.e("exception caught", e.getMessage());
        }

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), new File(Uri.fromFile(file).getPath()));
        // MultipartBody.Part is used to send also the actual filename
        MultipartBody.Part body = MultipartBody.Part.createFormData("redData", file.getName(), requestFile);
        RequestBody ruser = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody rscr = RequestBody.create(MediaType.parse("text/plain"), script_id.toString());
        RequestBody rname = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        uploadVideo(ruser, rscr, rname, body);

    }

    ArrayList<String> matches;
    int offset;
    String str;
    boolean isEndOfSpeech = false;

    private void startVoiceRecognitionActivity() {

        mic.setVisibility(View.GONE);
        pause_vr.setVisibility(View.VISIBLE);
//        timer.setVisibility(View.VISIBLE);
//        timer.setBase(SystemClock.elapsedRealtime());
//        timer.start();
//        timer.setFormat("%s");
        if (mSpeechRecognizer == null)
            mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        final Intent mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, Locale.getDefault().getLanguage().trim());
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 100);

        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
        offset = 0;
        str = et_firstscreen.getText().toString();
        isEndOfSpeech = false;
        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);


        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {
                Log.e("SpeechText", "onReadyForSpeech");
            }

            @Override
            public void onBeginningOfSpeech() {
                Log.e("SpeechText", "onBeginningOfSpeech");
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSpeechRecognizer.stopListening();
                    }
                }, 10000);
            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {
                Log.e("SpeechText", bytes.toString());
            }

            @Override
            public void onEndOfSpeech() {
                Log.e("SpeechText", "onEndOfSpeech");
                isEndOfSpeech = true;
                mSpeechRecognizer.stopListening();
//                mSpeechRecognizer.cancel();
                //    mSpeechRecognizer.destroy();
                // startVoiceRecognitionActivity();
            }

            @Override
            public void onError(int i) {
                Log.e("SpeechText", "onError");
                mSpeechRecognizer.stopListening();
//                mSpeechRecognizer.cancel();
                //          mSpeechRecognizer.destroy();
//                startVoiceRecognitionActivity();
                if (!isEndOfSpeech)
                    return;
                mSpeechRecognizer.startListening(mSpeechRecognizerIntent);

            }

            @Override
            public void onResults(Bundle bundle) {
                mSpeechRecognizer.stopListening();
//                Log.e("SpeechText", "onResults");
//                matches = null;
//                //getting all the matches
//                matches = bundle
//                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
//
//                Log.e("SpeechText", matches.get(0));
//                if (matches.get(0) != null) {
//
//                    offset = str.toLowerCase().indexOf(matches.get(0));
//                    line = et_firstscreen.getLayout().getLineForOffset(offset);
//                    if (line == 0) {
//                        line = 1;
//                    }
//
//
//                    scrollView.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            int y = et_firstscreen.getLayout().getLineTop(line); // e.g. I want to scroll to line 40
//
//                            Log.e("Line..............", offset + " " + String.valueOf(line) + " " + y + "Bottom = " + et_firstscreen.getBottom());
//                            et_firstscreen.scrollTo(0, y);
//
//
//
//                            //  startVoiceRecognitionActivity();
//                        }
//                    });
                //  }
                mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
            }

            @Override
            public void onPartialResults(Bundle bundle) {
                Log.e("SpeechText", "onPartialResults");
                Log.e("PartialResult", bundle
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).get(0));
                matches = null;
                //getting all the matches
                matches = bundle
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                Log.e("SpeechTextline", matches.get(0));
                if (matches.get(0) != null) {

                    String[] parts = matches.get(0).split(" ");
                    if (parts.length < 2) {
                        return;

                    }
                    String text = parts[parts.length - 2] + " " + parts[parts.length - 1];
                    offset = str.toLowerCase().indexOf(text);
                    Log.e("SpeechTextline33333", offset + "");
                    line = et_firstscreen.getLayout().getLineForOffset(offset);
                    Log.e("SpeechTextline3333", line + "");

                    if (line <= previousLine) {
                        return;
                    }
                    previousLine = line;

//
//                    scrollView.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            int y = et_firstscreen.getLayout().getLineTop(line); // e.g. I want to scroll to line 40
//
//                            Log.e("Line..............", offset + " " + String.valueOf(line) + " " + y + "Bottom = " + et_firstscreen.getBottom());
//                            et_firstscreen.scrollTo(0, y);
//                            //  startVoiceRecognitionActivity();
//                        }
//                    });
                } else {

                }
                mSpeechRecognizer.startListening(mSpeechRecognizerIntent);

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });


//        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
//        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
//                et_firstscreen.getText());
//        try {
//            startActivityForResult(intent, 11);
//        } catch (ActivityNotFoundException a) {
//            Toast.makeText(getApplicationContext(),
//                    "speech_not_supported",
//                    Toast.LENGTH_SHORT).show();
//        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void duration(int seekbar_pause, int scrollAmount) {


        int x = 0;

        //   int speed = seekBar.getProgress();
        int speed = seekbar_pause;

        int speedMultiplier = MAX_SCROLL_DURATION_MULTIPLIER - speed;

        // we are scrolling to the bottom of #scriptText
        int y = et_firstscreen.getBottom() + main_scrollView.getPaddingBottom();
        Log.e("scroll value of y", "" + y);
        Log.e("scroll value MULTIPLIER", "" + MAX_SCROLL_DURATION_MULTIPLIER);
        Log.e("scroll value of speed", "" + seekbar_pause);

        //long duration = y * 26; // slowest
        long duration = y * speedMultiplier;

        ObjectAnimator xTranslate = ObjectAnimator.ofInt(main_scrollView, "scrollX", x);
        ObjectAnimator yTranslate;

        if (scrollAmount == 0) {
            yTranslate = ObjectAnimator.ofInt(main_scrollView, "scrollY", 0, y);
        } else {
            yTranslate = ObjectAnimator.ofInt(main_scrollView, "scrollY", scrollAmount, y);
        }


        mAnimator = new AnimatorSet();
        mAnimator.setDuration(duration);
        mAnimator.playTogether(xTranslate, yTranslate);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.start();

//        if (anim != null && anim.isRunning()) {
//            anim.cancel();
//            anim.end();
//        }
//
//
//        int textHeight = getTextViewHeight(et_firstscreen);
//        int displayHeight = getDisplayHeight(Script_first.this);
//        anim = ObjectAnimator.ofInt(et_firstscreen, "scrollY",  textHeight - (displayHeight) + 120);
//        anim.setInterpolator(new LinearInterpolator());
//        anim.setDuration(et_firstscreen.getLineCount() * seekbar_pause + 30000);
//
//
//        if (textHeight > displayHeight) {
//            anim.start();
//        }

    }



    private int getDisplayHeight(Script_first script_first) {

        int displayheight;

        WindowManager windowManager = (WindowManager) script_first.getSystemService(
                Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point screenSize = new Point();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            display.getSize(screenSize);
            displayheight = screenSize.y;
        } else {
            displayheight = display.getHeight();
        }

        return displayheight;
    }

    private int getTextViewHeight(TextView et_firstscreen) {

//        et_firstscreen.measure(0, 0);    // Need to set measure to (0, 0).
//        return et_firstscreen.getMeasuredHeight();


        return et_firstscreen.getLineCount() * et_firstscreen.getLineHeight();
    }

    private Object getOutputMediaFile(int i) {

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "MyCameraApp");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (i == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }
        return mediaFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 11: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    Log.e("SpeechText", result.get(0));
                    if (et_firstscreen.getText().toString().matches(result.get(0))) {
                        int offset = et_firstscreen.getText().toString().indexOf(result.get(0));
                        int line = et_firstscreen.getLayout().getLineForOffset(offset);
                    }
                }
            }
            break;
        }
    }

    private void informationMenu() {
        startActivity(new Intent("android.intent.action.INFOSCREEN"));
    }


    private void uploadVideo(RequestBody user_id, RequestBody script_id, RequestBody name, MultipartBody.Part fbody) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


        retrofit2.Call<ResponseBody> call = apiService.uploadVideo(user_id, script_id, name, fbody);
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                if (response.isSuccessful()) {


                    try {
                        Log.e("uploadVideo Response", response.body().string());
                    } catch (Exception e) {
                        Log.e("uploadVideo Response", response.toString());
                    }
                } else {
                    Log.e("uploadVideo Response", response.toString());
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Log.e("errror", String.valueOf(t));

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Script_first.this, Script_Toolbar.class);

        intent.putExtra("TAG", "3");
        intent.putExtra("title", title);
        intent.putExtra("description", desc);
        intent.putExtra("script", script);
        intent.putExtra("alignment", alignment);
        Log.e("PromptTextSize", "size is" + promptTextSize);
        intent.putExtra(Global_Constants.PROMPT_TEXT_SIZE, promptTextSize);
        intent.putExtra(Global_Constants.SCRIPT_TEXT_SIZE, scriptTextSize);
        intent.putExtra(Global_Constants.PROMPT_SPEED, String.valueOf(seekbar_pause));
        intent.putExtra("desc_color", descolor);
        intent.putExtra("alignment", alignment);
        startActivity(intent);

        small_camera.stop();
        surface_camera.stop();
        seekbar_pause = 0;
        finish();
    }
}




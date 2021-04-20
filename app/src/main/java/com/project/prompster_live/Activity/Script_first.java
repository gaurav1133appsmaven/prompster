 package com.project.prompster_live.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.material.snackbar.Snackbar;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraLogger;
import com.otaliastudios.cameraview.CameraOptions;
import com.otaliastudios.cameraview.CameraUtils;
import com.otaliastudios.cameraview.CameraView;

import com.project.prompster_live.Api.ApiClient;
import com.project.prompster_live.Api.ApiInterface;
import com.project.prompster_live.MainActivity;
import com.project.prompster_live.R;
import com.project.prompster_live.Utils.ScrollTextView;
import com.project.prompster_live.Utils.Shared_PrefrencePrompster;
import com.project.prompster_live.Utils.VerticalScrollview;
import com.project.prompster_live.Utils.VideoCapture;
import com.squareup.picasso.Picasso;

import android.widget.RelativeLayout.LayoutParams;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

import android.view.SurfaceHolder.Callback;
import android.hardware.Camera.Parameters;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class Script_first extends AppCompatActivity {
    ImageView mice_inactive, cross, iv_text, iv_crosstext, mic, pause, pause_recording,
            voice_recording, stop_voice_recording, pause_voice_recording, iv_rec, pause_vr;
    RelativeLayout rel_small_camera;
    RelativeLayout rel_bottom, rl_visible, rl_data, rel_bottom_down, rl_main, rel_vr, rel_record, main_relative;
    ImageView iv_back, iv_vr;
    Shared_PrefrencePrompster shared_prefrencePrompster;
    String title, desc;
    Intent intent;
    int REQUEST_MICROPHONE = 101;
    TextView et_firstscreen;
    Chronometer timer;
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
    String seekbarr;
    static int seekbar_pause = 0;
    SeekBar seekbarr_pause, seekbar_vr, seekbar_margin;
    ImageView iv_left, iv_right, iv_centre, cross_white, iv_cancel_record, video_record;
    String script, storyboard, media, user_id, script_id;
    File file;
    int line = 0, flag = 0;
    public static final int RequestPermissionCode = 7;
    ScrollView scrollView;
    ObjectAnimator anim;
    ScrollView main_scrollView;
    Toolbar toolbar;
    LinearLayout layout;
    private Animation mAnimation;
    boolean click = false;
    boolean vrr = true;
    CameraView surface_camera;
    CameraView small_camera;
    SwitchCompat chk_marker, chk_manual, chk_vr, switch_full_screen, switch_record_me, switch_record_me_audio, chk_mannual_record;
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
    String AudioSavePathInDevice = null, camera_open = "0", descolor, size, alignment;
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
        seekbarr = intent.getStringExtra("seekbar_progress");
        script_id = intent.getStringExtra("script_id");

        script = intent.getStringExtra("script");
        descolor = intent.getStringExtra("desc_color");
        size = intent.getStringExtra("size");
        alignment = intent.getStringExtra("alignment");
        user_id = shared_prefrencePrompster.getUserid();

        init();
        mediaRecorder = new MediaRecorder();

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
                intent.putExtra("title", title);
                intent.putExtra("descriptionn", desc);
                intent.putExtra("script", script);
                intent.putExtra("sizee", size);
                intent.putExtra("desc_color", descolor);
                try {
                    if (alignment.equals("left")) {
                        intent.putExtra("alignment", "left");
                    } else if (alignment.equals("right")) {
                        intent.putExtra("alignment", "right");
                    } else {
                        intent.putExtra("alignment", "center");
                    }
                } catch (Exception e) {

                }

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
            }
        });
    }


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
        scrollView = findViewById(R.id.scrollView);
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

        et_firstscreen.setText(desc);
        if (descolor == null || descolor.equals(String.valueOf(Color.BLACK)) || descolor.equals("")) {
            et_firstscreen.setTextColor(Color.WHITE);
        } else {
            et_firstscreen.setTextColor(Integer.parseInt(descolor));
        }
        et_firstscreen.setTextSize(Float.parseFloat(size));
        try {
            if (alignment.equals("left")) {
                et_firstscreen.setGravity(Gravity.LEFT);

            } else if (alignment.equals("right")) {
                et_firstscreen.setGravity(Gravity.RIGHT);

            } else {
                et_firstscreen.setGravity(Gravity.CENTER);

            }
        } catch (Exception e) {

        }

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
                Log.e("Videofile", video.toString());

                saveVideo(video);

            }
        });

        small_camera.addCameraListener(new CameraListener() {
            @Override
            public void onVideoTaken(File video) {
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

                flag = 1;

                rel_vr.setVisibility(View.GONE);
                rl_visible.setVisibility(View.GONE);
                rel_bottom.setVisibility(View.GONE);
                rel_record.setVisibility(View.GONE);

                if (vrr == true) {
                    rl_main.setVisibility(View.GONE);
                    toolbar.setVisibility(View.GONE);

                    startVoiceRecognitionActivity();

                } else {
                    rl_main.setVisibility(View.GONE);
                    toolbar.setVisibility(View.GONE);
                    mic.setVisibility(View.GONE);
                    pause.setVisibility(View.VISIBLE);
                    et_firstscreen.setMovementMethod(new ScrollingMovementMethod());
                    Log.e("VALUE>..............", String.valueOf(et_firstscreen.getLayout().getHeight()));
                    seekbarr_pause.setVisibility(View.VISIBLE);
                    iv_rec.setVisibility(View.VISIBLE);
                    timer.setVisibility(View.VISIBLE);
                    timer.setBase(SystemClock.elapsedRealtime());
                    timer.start();
                    timer.setFormat("%s");

                    int textHeight = getTextViewHeight(et_firstscreen);
                    int displayHeight = getDisplayHeight(Script_first.this);
//                    anim = ObjectAnimator.ofInt(et_firstscreen, "scrollY", textHeight + displayHeight);
                    duration(625 - seekbar_pause);
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
                        if (anim != null) {
                            rl_main.setVisibility(View.GONE);
                            toolbar.setVisibility(View.GONE);
                            mic.setVisibility(View.GONE);
                            pause.setVisibility(View.VISIBLE);
                            seekbarr_pause.setVisibility(View.VISIBLE);
                            et_firstscreen.setMovementMethod(new ScrollingMovementMethod());
                            Log.e("VALUE>..............", String.valueOf(et_firstscreen.getLayout().getHeight()));

                            int textHeight = getTextViewHeight(et_firstscreen);
                            int displayHeight = getDisplayHeight(Script_first.this);
//                        anim = ObjectAnimator.ofInt(et_firstscreen, "scrollY", textHeight + displayHeight);

                            duration(625 - seekbar_pause);
                            click = false;
                        }
                    } else {
                        if (anim != null) {
                            toolbar.setVisibility(View.GONE);
                            mic.setVisibility(View.GONE);
                            pause.setVisibility(View.VISIBLE);
                            seekbarr_pause.setVisibility(View.VISIBLE);
                            rl_main.setVisibility(View.GONE);

                            anim.pause();
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
                flag = 0;

                toolbar.setVisibility(View.VISIBLE);
                mic.setVisibility(View.VISIBLE);
                pause.setVisibility(View.GONE);
                iv_rec.setVisibility(View.GONE);
                seekbarr_pause.setVisibility(View.GONE);
                timer.stop();
                timer.setVisibility(View.GONE);
                rl_main.setVisibility(View.VISIBLE);
                anim.pause();
            }
        });

        pause_vr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSpeechRecognizer.stopListening();
                mSpeechRecognizer.destroy();
                rl_main.setVisibility(View.VISIBLE);
                toolbar.setVisibility(View.VISIBLE);
                mic.setVisibility(View.VISIBLE);
                pause_vr.setVisibility(View.GONE);
                timer.setVisibility(View.GONE);
                timer.stop();
            }
        });

        pause_recording.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {

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
                et_firstscreen.setMovementMethod(new ScrollingMovementMethod());
                Log.e("VALUE>..............", String.valueOf(et_firstscreen.getLayout().getHeight()));
                seekbarr_pause.setVisibility(View.GONE);
                pause_recording.setVisibility(View.GONE);
                anim.pause();
                timer.stop();

                dialog = new Dialog(Script_first.this);


                dialog.setContentView(R.layout.email_empty);
                dialog.setCanceledOnTouchOutside(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                tv_ok = dialog.findViewById(R.id.tv_ok);
                tv_path = dialog.findViewById(R.id.tv_path);
                tv_recording = dialog.findViewById(R.id.tv_recording);

                tv_recording.setText("Recording");
                tv_path.setText("Recording saved successfully. To view, go to the Files app in your device inside Scriptively folder.");

                tv_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                    }
                });
                dialog.show();

            }
        });

        pause_voice_recording.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
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
                et_firstscreen.setMovementMethod(new ScrollingMovementMethod());
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
                tv_path.setText("Recording saved successfully. To view, go to the Files app in your device inside Scriptively folder.");

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
                    et_firstscreen.setMovementMethod(new ScrollingMovementMethod());
                    Log.e("VALUE>..............", String.valueOf(et_firstscreen.getLayout().getHeight()));
                    video_record.setVisibility(View.GONE);
                    seekbarr_pause.setVisibility(View.VISIBLE);
                    pause_recording.setVisibility(View.VISIBLE);
                    timer.setVisibility(View.VISIBLE);
                    iv_rec.setVisibility(View.VISIBLE);

                    int textHeight = getTextViewHeight(et_firstscreen);
                    int displayHeight = getDisplayHeight(Script_first.this);
//                    anim = ObjectAnimator.ofInt(et_firstscreen, "scrollY", textHeight + displayHeight);
                    duration(625 - seekbar_pause);

                    if (camera_tag.equals("full_record")) {
                        rel_bottom_down.setBackgroundColor(Color.TRANSPARENT);
                        main_scrollView.setBackgroundColor(Color.TRANSPARENT);
                        surface_camera.startCapturingVideo();
                    } else if (camera_tag.equals("small_record")) {
                        small_camera.startCapturingVideo();
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
                seekbarr_pause.setBackgroundColor(getResources().getColor(R.color.black));
                seekbar_pause = progress * 25;
                Log.e("SeekBar_Pause", String.valueOf(seekbar_pause));
                duration(625 - seekbar_pause);

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

        chk_vr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (CheckPermissions()) {
//                    Toast.makeText(getApplicationContext(), "Permissions Granted ", Toast.LENGTH_SHORT).show();
                if (isChecked == true) {
                    vrr = true;
                    chk_manual.setChecked(false);
                    seekbar_vr.setEnabled(false);
                    chk_vr.getTrackDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.switch_color), PorterDuff.Mode.SRC_IN);
                    chk_vr.getThumbDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), PorterDuff.Mode.SRC_IN);
                    seekbar_vr.setThumb(getDrawable(R.drawable.custom_thumb_white));

                } else {
                    vrr = false;

                    chk_manual.setChecked(true);
                    seekbar_vr.setEnabled(true);
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
                    if (isChecked == true) {
//                        camera_tag = "3";

                        if (chk_mannual_record.isChecked()) {
                            camera_tag = "small_record";
                        } else if (switch_full_screen.isChecked()) {
                            camera_tag = "full_record";
                        } else {
                            camera_tag = "record";
                        }
                        voice_recording.setVisibility(View.GONE);
                        mic.setVisibility(View.GONE);
                        video_record.setVisibility(View.VISIBLE);
                        switch_record_me_audio.setChecked(false);
                        switch_record_me.getTrackDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.switch_color), PorterDuff.Mode.SRC_IN);
                        switch_record_me.getThumbDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), PorterDuff.Mode.SRC_IN);
                    } else {
                        camera_tag = "";
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
                flag = 1;
                rel_vr.setVisibility(View.GONE);
                rl_visible.setVisibility(View.GONE);
                rel_bottom.setVisibility(View.GONE);
                rel_record.setVisibility(View.GONE);
                if (CheckPermissions()) {

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

                    et_firstscreen.setMovementMethod(new ScrollingMovementMethod());
                    Log.e("VALUE>..............", String.valueOf(et_firstscreen.getLayout().getHeight()));
                    seekbarr_pause.setVisibility(View.VISIBLE);

                    int textHeight = getTextViewHeight(et_firstscreen);
                    int displayHeight = getDisplayHeight(Script_first.this);
//                    anim = ObjectAnimator.ofInt(et_firstscreen, "scrollY", textHeight + displayHeight);
                    duration(625 - seekbar_pause);

                    File filepath = Environment.getExternalStorageDirectory();
                    File dir = new File(filepath.getAbsolutePath() + "/" + "Scriptively" + "/audio/");
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    file = new File(dir, "save_" + System.currentTimeMillis() + ".mp3");
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
                et_firstscreen.setMovementMethod(new ScrollingMovementMethod());
                Log.e("VALUE>..............", String.valueOf(et_firstscreen.getLayout().getHeight()));
                seekbarr_pause.setVisibility(View.GONE);
                anim.pause();
                dialog = new Dialog(Script_first.this);


                dialog.setContentView(R.layout.email_empty);
                dialog.setCanceledOnTouchOutside(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                tv_ok = dialog.findViewById(R.id.tv_ok);
                tv_path = dialog.findViewById(R.id.tv_path);
                tv_recording = dialog.findViewById(R.id.tv_recording);
                tv_recording.setText("Recording");
                tv_path.setText("Recording saved successfully. To view, go to the Files app in your device inside Scriptively folder.");

                tv_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                    }
                });
                dialog.show();


                File file = new File(AudioSavePathInDevice);

                Log.e("AudioFilePath", Uri.fromFile(file).getPath());

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
                    seekbar_vr.setEnabled(true);
                    chk_manual.getTrackDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.switch_color), PorterDuff.Mode.SRC_IN);
                    chk_manual.getThumbDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), PorterDuff.Mode.SRC_IN);
                    seekbar_vr.setThumb(getDrawable(R.drawable.custom_thumb_white));
                    tv_runon.setBackground(getDrawable(R.drawable.run_one_white));
                    tv_runto.setBackground(getDrawable(R.drawable.run_two_white));
                } else {
                    chk_vr.setChecked(true);
                    seekbar_vr.setEnabled(false);
                    chk_manual.getTrackDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.tracktint), PorterDuff.Mode.SRC_IN);
                    chk_manual.getThumbDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), PorterDuff.Mode.SRC_IN);
                    seekbar_vr.setThumb(getDrawable(R.drawable.custom_thumb_grey));
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

                seekbarr = String.valueOf(progress);

                et_firstscreen.setTextSize(Float.valueOf(progress));
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
        try {
            FileInputStream fileInputStream = new FileInputStream(video);

            File filepath = Environment.getExternalStorageDirectory();
            File dir = new File(filepath.getAbsolutePath() + "/" + "Scriptively" + "/video/");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            file = new File(dir, "save_" + System.currentTimeMillis() + ".mp4");
            Log.e("FilePath", file.getAbsolutePath() + "  " + file.length());

            if (file.exists())
                file.delete();

            FileOutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;

            while ((len = fileInputStream.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            fileInputStream.close();
            out.close();

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

        } catch (Exception e) {
            e.printStackTrace();
        }


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
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        
        final Intent mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault());
        offset = 0;
        str = et_firstscreen.getText().toString();
        isEndOfSpeech = false;
        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);


        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

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
                Log.e("SpeechText","onEndOfSpeech");
                isEndOfSpeech = true;
                mSpeechRecognizer.stopListening();
                mSpeechRecognizer.cancel();
                mSpeechRecognizer.destroy();
                startVoiceRecognitionActivity();
            }

            @Override
            public void onError(int i) {
                Log.e("SpeechText","onError");
                mSpeechRecognizer.stopListening();
                mSpeechRecognizer.cancel();
                mSpeechRecognizer.destroy();
//                startVoiceRecognitionActivity();
                if (!isEndOfSpeech)
                    return;

            }

            @Override
            public void onResults(Bundle bundle) {

                matches = null;
                //getting all the matches
                matches = bundle
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                Log.e("SpeechText", matches.get(0));
                if (matches.get(0) != null) {
                    offset = str.toLowerCase().indexOf(matches.get(0));
                    line = et_firstscreen.getLayout().getLineForOffset(offset);


                    scrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            int y = et_firstscreen.getLayout().getLineTop(line); // e.g. I want to scroll to line 40

                            Log.e("Line..............", offset + " " + String.valueOf(line)+ " " +y+"Bottom = "+et_firstscreen.getBottom());
                            et_firstscreen.scrollTo(0, y);
                            startVoiceRecognitionActivity();
                        }
                    });
                } else {

                }
            }

            @Override
            public void onPartialResults(Bundle bundle) {

                Log.e("PartialResult", bundle
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).get(0));

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
    private void duration(int seekbar_pause) {
        try {
            anim.pause();
        } catch (NullPointerException e) {

        }

        int textHeight = getTextViewHeight(et_firstscreen);
        int displayHeight = getDisplayHeight(Script_first.this);

        Log.e("TextHeight,Display", String.valueOf(textHeight));
        Log.e("Display", String.valueOf(displayHeight));

        anim = ObjectAnimator.ofInt(et_firstscreen, "scrollY", textHeight - (displayHeight / 2));


        anim.setDuration(et_firstscreen.getLineCount() * seekbar_pause);
        anim.setInterpolator(new LinearInterpolator());
        if (textHeight > displayHeight) {
            anim.start();
        }
        Log.e("LAstLine.....", String.valueOf(et_firstscreen.getLineCount()));


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

        et_firstscreen.measure(0, 0);    // Need to set measure to (0, 0).
        return et_firstscreen.getMeasuredHeight();

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

                Log.e("uploadVideourl", call.request().toString());

                try {
                    Log.e("uploadVideo Response", response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (response.isSuccessful()) {


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
        intent.putExtra("descriptionn", desc);
        intent.putExtra("script", script);
        intent.putExtra("sizee", size);
        intent.putExtra("desc_color", descolor);
        intent.putExtra("alignment", alignment);
        startActivity(intent);

        small_camera.stop();
        surface_camera.stop();
        seekbar_pause = 0;
        finish();
    }
}




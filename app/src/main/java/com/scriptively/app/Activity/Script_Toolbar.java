package com.scriptively.app.Activity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.print.PrintManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.scriptively.app.Adapter.AZ_Adapter;
import com.scriptively.app.Adapter.RecentFontStyle;
import com.scriptively.app.Adapter.Recent_Fonts;
import com.scriptively.app.Adapter.ViewPrintAdapter;
import com.scriptively.app.Api.ApiClient;
import com.scriptively.app.Api.ApiInterface;
import com.scriptively.app.Database.AppDatabase;
import com.scriptively.app.Database.ScriptDBDao;
import com.scriptively.app.Database.StoryBoardDao;
import com.scriptively.app.DatabaseModel.ScriptDB;
import com.scriptively.app.DatabaseModel.StoryBoardDB;
import com.scriptively.app.Fragments.Fragment_Board;
import com.scriptively.app.Fragments.Fragment_Script;
import com.scriptively.app.Fragments.Fragment_media;
import com.scriptively.app.Interface.Recycler_item_click;
import com.scriptively.app.Pojo.AddScript;
import com.scriptively.app.Pojo.EditScript_Pojo;
import com.scriptively.app.Pojo.Script_Pojo;
import com.scriptively.app.R;
import com.scriptively.app.Utils.ConnectivityChangeReceiver;
import com.scriptively.app.Utils.DropboxActivity;
import com.scriptively.app.Utils.Global_Constants;
import com.scriptively.app.Utils.Shared_PrefrencePrompster;
import com.scriptively.app.Utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Script_Toolbar extends AppCompatActivity {
    InputType InputTpe;
    RelativeLayout rl_data, rl_data1, rl_data2, rl_data3, rl_media, rl_titile, iv_export, iv_export_active;
    Fragment_Script fragment_script;
    ImageView iv_import, iv_exportt, iv_profile, iv_importalphabet, iv_back, iv_cross_az, back_icon_style, iv_cancel_dictionary, iv_micinactive;
    Dialog show, export;
    TextView tv_import, tv_dropbox, tv_filess, tv_googledrive, tv_cancel, tv_cancell, tv_print, tv_email;
    Shared_PrefrencePrompster shared_prefrencePrompster;
    String userid, scrTitle, script_text, scrAttrText, title, desc, script_id, Tag_value, story_board_id, saved_taag, desc_fist;
    EditText et_tv, tv_title, et_search_dictionary;
    Intent intent;
    SeekBar seekbar;
    int textSize = 1;
    int saveProgress;
    ImageView iv_left, iv_right, iv_centre, iv_red, iv_orange, iv_yellow, iv_green,
            iv_skyblue, iv_purple, iv_blue, iv_black, iv_darkgrey, iv_gray, iv_lightgray, iv_white, back_icon;
    RelativeLayout rl_visible;
    ImageView iv_active;
    RelativeLayout rl_frame;

    Dialog dialog;
    TextView tv_ok, tv_path;
    RelativeLayout rl_ok;
    String descrip;
    String telepromptTextSize;
    String color;
    int selectedColor, primaryKey;
    String align, script_Idd, position, dictionary;
    static final int PICK_CONTACT_REQUEST = 1;
    String attribut_api;
    private PrintManager mgr = null;
    String attribut, aaa, alignment_tag, final_align;
    RecyclerView rv_recent_font, rv_all_fonts, recycler_font_style, rv_AZ;
    public static boolean flag = false;
    boolean first_flag = false;
    String addScript_id;
    String desc_color = "-16777216", alignment;
    ///////color active
    String titleeee, createdAt;
    RelativeLayout main_rl;
    View view_script, view_board;
    Object iFocus;
    TextView tv_script, tv_storyBoard, tv_media, tv_font_style, tv_fontName, tv_def_syno;
    RelativeLayout rl_backk;
    RelativeLayout rl_red, rl_orange, rl_yellow, rl_green, rl_skyblue, rl_purple, rl_blue,
            rl_darkgrey, rl_grey, rl_lightgrey, rl_white, rl_black, rl_AZ,
            rel_font_style_main, rel_font_style_, rel_font_style_style, rl_dictionary, rl_threasures;
    ImageView iv_importinactive, iv_importactive, iv_cross_fontstyle;
    String title_recent, text_recent, descolor, size, alignmentt;
    String scrTitle_foldertitle;
    String Tag_profile, back_value, dic_syn_tag = "0";
    private int request_code = 1, FILE_SELECT_CODE = 101;
    public String actualfilepath = "";
    ScrollView MyScrollView;
    int iCount;
    int index = 0;
    ScrollView scrollView1;
    File gpxfile, root;
    ACProgressFlower dialog_progress;
    String from = "";
    Random random;
    RecentFontStyle recent_font_style_adapter;
    AZ_Adapter az_adapter;
    Navigation navigation;
    Date currentDate;
    LinearLayoutManager linearLayoutManager, linearLayoutManagerFont, linearLayoutManagerFontStyle, linearLayoutManagerAZ;
    Recent_Fonts recent_adapter, all_fonts;
    List<String> fontList = new ArrayList<>();
    List<String> recent_font_list = new ArrayList<>();
    String[] str = {"Arial", "Courier", "Courier New", "Georgia", " Gill Sans", "Helvetica", "Helvetica Neue", "Palatino",
            "Times New Roman", "Verdana"};
    String[] fontFiles = {"arial.ttf", "cour.ttf", "couri.ttf", "georgia.ttf", "Gill Sans.otf", "Helvetica.ttf", "HelveticaNeue.ttf", "pala.ttf",
            "times.ttf", "verdana.ttf"};

    List<String> fontListStyle = new ArrayList<>();
    List<String> defination = new ArrayList<>();
    List<String> partOfSpeech = new ArrayList<>();
    String[] fontstyleList = {"Regular", "Bold", "BoldItalic", "Italic", "Thin", "ThinItalic", "Light", "LightItalic",
            "Medium", "MediumItalic", "UltraLight", "UltraLightItalic"};

    private Recycler_item_click mlistener;

    //LocalDatabase......................
    static AppDatabase db;
    ScriptDBDao scriptDBDao;
    ScriptDB scriptDB;
    ConnectivityChangeReceiver connectivityChangeReceiver;
    private String selectedFontFamily = "Helvetica.ttf";
    private String scrTextSize;

    //..................................
    public static CharSequence trim(CharSequence source) {

        if (source == null)
            return "";

        int i = source.length();

        // loop back to the first non-whitespace character
        while (--i >= 0 && Character.isWhitespace(source.charAt(i))) {
        }

        return source.subSequence(0, i + 1);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_script__toolbar);

        first_flag = false;
        random = new Random();
        mgr = (PrintManager) getSystemService(PRINT_SERVICE);
        shared_prefrencePrompster = Shared_PrefrencePrompster.getInstance(this);
        Script_Toolbar.this.setFinishOnTouchOutside(false);

        db = AppDatabase.getInstance(this);
        scriptDBDao = db.scriptDBDao();

        color = shared_prefrencePrompster.getColor().toString();
        saved_taag = shared_prefrencePrompster.getTag().toString();
        dialog = new Dialog(Script_Toolbar.this);
        intent = new Intent();
        intent = getIntent();
        title = intent.getStringExtra("title");
        desc = intent.getStringExtra("description");

        title_recent = intent.getStringExtra("scrTitle_recent");
        text_recent = intent.getStringExtra("scrText_recent");
        desc_color = intent.getStringExtra("desc_color");
        telepromptTextSize = intent.getStringExtra(Global_Constants.PROMPT_TEXT_SIZE);
        scrTextSize = intent.getStringExtra(Global_Constants.SCRIPT_TEXT_SIZE);
        alignment_tag = intent.getStringExtra("alignment");
        if(scrTextSize==null)
        {
            scrTextSize="35.5";
        }

//        Log.e("various sizes", "scr text"+scrTextSize.toString()+"  "+telepromptTextSize.toString());
        try {
            primaryKey = intent.getIntExtra("script_primaryKey", 0);
        } catch (Exception e) {

        }


        try {
            back_value = intent.getStringExtra("script");
        } catch (NullPointerException e) {

        }
        Tag_profile = "Script";

        script_id = intent.getStringExtra("script_id");
        Tag_value = intent.getStringExtra("TAG");

        position = intent.getStringExtra("position");

        try {
            story_board_id = intent.getStringExtra("storyboard_id");
        } catch (IndexOutOfBoundsException e) {

        }
        userid = shared_prefrencePrompster.getUserid().toString();
        tv_title = (EditText) findViewById(R.id.tv_title);
        et_search_dictionary = (EditText) findViewById(R.id.et_search_dictionary);
        rl_frame = findViewById(R.id.rl_frame);
        et_tv = (EditText) findViewById(R.id.et_tv_description);
        Typeface face = Typeface.createFromAsset(getAssets(), selectedFontFamily);
        et_tv.setTypeface(face);
        tv_title.setTypeface(face);
        rl_data = findViewById(R.id.rl_data);
        rl_data2 = findViewById(R.id.rl_data2);
        rl_media = findViewById(R.id.rl_media);
        rl_titile = findViewById(R.id.rl_titile);
        iv_import = findViewById(R.id.iv_import);
        tv_import = findViewById(R.id.tv_import);
        iv_exportt = findViewById(R.id.iv_exportt);
        iv_export = findViewById(R.id.iv_export);
        iv_export_active = findViewById(R.id.iv_export_active);
        tv_cancell = findViewById(R.id.tv_cancell);
        iv_profile = findViewById(R.id.iv_profile);
        iv_importalphabet = findViewById(R.id.iv_importalphabet);
        iv_back = findViewById(R.id.iv_back1);
        iv_cross_az = findViewById(R.id.iv_cross_az);
        back_icon_style = findViewById(R.id.back_icon_style);
        iv_cancel_dictionary = findViewById(R.id.iv_cancel_dictionary);
        iv_micinactive = findViewById(R.id.iv_micinactive);
        rl_visible = findViewById(R.id.rl_visible);
        seekbar = findViewById(R.id.seekbar);
        iv_active = findViewById(R.id.iv_active);

        tv_script = findViewById(R.id.tv_script);
        tv_storyBoard = findViewById(R.id.tv_storyBoard);
        tv_media = findViewById(R.id.tv_media);
        tv_font_style = findViewById(R.id.tv_font_style);
        tv_fontName = findViewById(R.id.tv_fontName);
        tv_def_syno = findViewById(R.id.tv_def_syno);
        view_script = findViewById(R.id.view_script);
        view_board = findViewById(R.id.view_board);
        //////color active
        rl_backk = findViewById(R.id.rl_backk);
        rl_red = findViewById(R.id.rl_red);
        rl_orange = findViewById(R.id.rl_orange);
        rl_yellow = findViewById(R.id.rl_yellow);
        rl_green = findViewById(R.id.rl_green);
        rl_skyblue = findViewById(R.id.rl_skyblue);
        rl_purple = findViewById(R.id.rl_purple);
        rl_blue = findViewById(R.id.rl_blue);
        rl_darkgrey = findViewById(R.id.rl_darkgrey);
        rl_grey = findViewById(R.id.rl_grey);
        rl_lightgrey = findViewById(R.id.rl_lightgrey);
        rl_white = findViewById(R.id.rl_white);
        rl_black = findViewById(R.id.rl_black);
        main_rl = findViewById(R.id.main_rl);
        rl_AZ = findViewById(R.id.rl_AZ);
        rel_font_style_main = findViewById(R.id.rel_font_style_main);
        rel_font_style_ = findViewById(R.id.rel_font_style_);
        rel_font_style_style = findViewById(R.id.rel_font_style_style);
        rl_threasures = findViewById(R.id.rl_threasures);
        rl_dictionary = findViewById(R.id.rl_dictionary);
        rv_recent_font = findViewById(R.id.rv_recent_font);
        rv_all_fonts = findViewById(R.id.rv_all_font);
        recycler_font_style = findViewById(R.id.recycler_font_style);
        rv_AZ = findViewById(R.id.rv_AZ);

        iv_left = findViewById(R.id.iv_left);
        iv_right = findViewById(R.id.iv_right);
        iv_centre = findViewById(R.id.iv_centre);
        iv_red = findViewById(R.id.iv_red);
        iv_orange = findViewById(R.id.iv_orange);
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
        back_icon = findViewById(R.id.back_icon);
        iv_cross_fontstyle = findViewById(R.id.iv_cross_fontstyle);

        et_tv.setVerticalScrollBarEnabled(true);
        et_tv.setMovementMethod(new ScrollingMovementMethod());
        tv_title.setRawInputType(InputType.TYPE_CLASS_TEXT);
        tv_title.setImeOptions(EditorInfo.IME_ACTION_DONE);
        tv_title.setRawInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        tv_title.setTypeface(tv_title.getTypeface(), Typeface.BOLD);
        rl_dictionary.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button));
        rl_threasures.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button2));
        linearLayoutManagerAZ = new LinearLayoutManager(getApplicationContext());
        linearLayoutManagerAZ.setOrientation(LinearLayoutManager.VERTICAL);
        rv_AZ.setLayoutManager(linearLayoutManagerAZ);

        if (scrTextSize != null) {
            et_tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, Float.parseFloat(scrTextSize));
            seekbar.setProgress((int)Double.parseDouble(scrTextSize));
        }


        mlistener = new Recycler_item_click() {
            @Override
            public void onClick(View view, int position, String type) {

                if (type.equals("all")) {
                    try {
                        boolean r = false;
                        for (int i = 0; i < recent_font_list.size(); i++) {
                            if (recent_font_list.get(i).equals(fontList.get(position))) {
                                r = true;
                                recent_font_list.remove(i);
                            }
                        }

                        if (recent_font_list.size() > 2) {
                            if (!r) {
                                recent_font_list.remove(2);
                            }
                            recent_font_list.add(0, fontList.get(position));
                        } else {
                            recent_font_list.add(0, fontList.get(position));
                        }
//                        Typeface face = Typeface.createFromAsset(getAssets(),
//                                fontList.get(position)+".ttf");
//                        et_tv.setTypeface(face);


                        recent_adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {

                }
                Typeface face = Typeface.createFromAsset(getAssets(),
                        fontFiles[position]);
                selectedFontFamily = fontFiles[position];
                et_tv.setTypeface(face);
                tv_title.setTypeface(face);
                tv_font_style.setText(fontList.get(position));

            }

            @Override
            public void onClicFonts(View view, int position) {


            }
        };

        try {
            if (alignment_tag.equals("left")) {
                et_tv.setGravity(Gravity.LEFT);
            } else if (alignment_tag.equals("right")) {
                et_tv.setGravity(Gravity.RIGHT);
            } else if (alignment_tag.equals("center")) {
                et_tv.setGravity(Gravity.CENTER);
            } else {
                et_tv.setGravity(Gravity.LEFT);
            }
        } catch (Exception e) {

        }

        try {

            if (back_value.equals("") || back_value.equals(null)) {

            } else {
                if (back_value.equals("Script")) {
                    Tag_profile = "Script";
                    view_script.setVisibility(View.GONE);
                    view_board.setVisibility(View.GONE);
                    rl_frame.setVisibility(View.GONE);
                    rl_titile.setVisibility(View.VISIBLE);

                    rl_media.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button2));
                    rl_data2.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button2));
                    rl_data.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button));
                } else if (back_value.equals("Media")) {
                    Tag_profile = "Media";
                    tv_title.setText(title);
                    view_script.setVisibility(View.GONE);
                    view_board.setVisibility(View.GONE);
                    rl_frame.setVisibility(View.VISIBLE);
                    rl_data2.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button2));
                    rl_data.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button2));
                    rl_media.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button));
                    rl_titile.setVisibility(View.GONE);
                    Fragment_media fragment_media = new Fragment_media();
                    Bundle arguments = new Bundle();
                    FragmentManager manager = getSupportFragmentManager();
                    arguments.putString("script_id", script_id);
                    arguments.putString("script_title", tv_title.getText().toString());
                    fragment_media.setArguments(arguments);
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.frame, fragment_media).commit();

                } else if (back_value.equals("Story")) {

                    Tag_profile = "Story";
                    tv_title.setText(title);
                    view_script.setVisibility(View.GONE);
                    view_board.setVisibility(View.GONE);
                    rl_frame.setVisibility(View.VISIBLE);
                    rl_media.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button2));
                    rl_data2.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button));
                    rl_data.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button2));
                    rl_titile.setVisibility(View.GONE);
                    Fragment_Board fragment_board = new Fragment_Board();
                    Bundle arguments = new Bundle();
                    FragmentManager manager = getSupportFragmentManager();
                    arguments.putString("script_id", script_id);
                    arguments.putInt("script_primaryKey", primaryKey);
                    arguments.putString("script_title", tv_title.getText().toString());
                    fragment_board.setArguments(arguments);
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.frame, fragment_board).commit();
                } else {
                }
            }
        } catch (NullPointerException e) {

        }


        et_tv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    scrTitle = tv_title.getText().toString().trim();
                    script_text = et_tv.getText().toString();


                    if (scrTitle.equals("")) {

                        dialog.setContentView(R.layout.title_validation);
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        tv_ok = dialog.findViewById(R.id.tv_okay);
                        tv_path = dialog.findViewById(R.id.tv_path);
                        tv_path.setText("Please enter a title for creating new script.");

                        tv_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                dialog.dismiss();

                            }
                        });
                        dialog.show();

                    }
                }

            }
        });

//        tv_title.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.showSoftInput(tv_title, InputMethodManager.SHOW_IMPLICIT);
//            }
//        });
        rl_dictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_def_syno.setText("Definitions");
                dic_syn_tag = "0";
                dictionary = et_search_dictionary.getText().toString();
                rl_dictionary.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button));
                rl_threasures.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button2));
                dictionaryApi(dictionary);

            }
        });
        rl_threasures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_def_syno.setText("Synonyms");
                dic_syn_tag = "1";
                dictionary = et_search_dictionary.getText().toString();
                rl_dictionary.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button2));
                rl_threasures.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button));
                synonymsApi(dictionary);
            }
        });

        iv_cross_az.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_AZ.setVisibility(View.GONE);
            }
        });

        back_icon_style.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rel_font_style_.setVisibility(View.VISIBLE);
                rel_font_style_style.setVisibility(View.GONE);
            }
        });

        iv_cancel_dictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_search_dictionary.setText("");
                tv_def_syno.setVisibility(View.GONE);
                iv_micinactive.setVisibility(View.VISIBLE);
                iv_cancel_dictionary.setVisibility(View.GONE);

                dictionary = et_search_dictionary.getText().toString();
                if (dic_syn_tag.equals("0")) {
                    dictionaryApi(dictionary);
                } else {
                    synonymsApi(dictionary);
                }
            }
        });
        et_search_dictionary.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (et_search_dictionary.getText().equals("")) {
                    tv_def_syno.setVisibility(View.GONE);
                    iv_micinactive.setVisibility(View.VISIBLE);
                    iv_cancel_dictionary.setVisibility(View.GONE);
                } else {
                    iv_micinactive.setVisibility(View.GONE);
                    iv_cancel_dictionary.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_search_dictionary.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {


                if (i == EditorInfo.IME_ACTION_DONE) {
                    dictionary = et_search_dictionary.getText().toString();
                    if (dic_syn_tag.equals("0")) {
                        dictionaryApi(dictionary);
                    } else {
                        synonymsApi(dictionary);
                    }

                }
                return false;
            }
        });


        main_rl.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                main_rl.getWindowVisibleDisplayFrame(r);
                if (main_rl.getRootView().getHeight() - (r.bottom - r.top) > 500) {

                    first_flag = true;
                    et_tv.setCursorVisible(true);

                } else {
                    et_tv.setCursorVisible(false);

                    if (first_flag) {
                        saveData();
                    }
                    first_flag = false;

                }

            }
        });

        iv_exportt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_AZ.setVisibility(View.VISIBLE);
                rl_visible.setVisibility(View.GONE);
            }
        });

        iv_cross_fontstyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_visible.setVisibility(View.GONE);
                iv_active.setVisibility(View.VISIBLE);
                iv_active.setVisibility(View.GONE);
                iv_import.setVisibility(View.VISIBLE);
//                tv_font_style.setVisibility(View.GONE);
                editScriptData();
            }
        });
        tv_font_style.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (String font : str) {
                    Log.e("Font", font);
                    fontList.add(font);
                }

                rel_font_style_main.setVisibility(View.GONE);
                rel_font_style_.setVisibility(View.VISIBLE);

                linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                linearLayoutManagerFont = new LinearLayoutManager(getApplicationContext());
                linearLayoutManagerFont.setOrientation(LinearLayoutManager.VERTICAL);
                rv_recent_font.setLayoutManager(linearLayoutManager);

                rv_all_fonts.setLayoutManager(linearLayoutManagerFont);

                recent_adapter = new Recent_Fonts(Script_Toolbar.this, recent_font_list, mlistener, "recent");
                rv_recent_font.setAdapter(recent_adapter);
                all_fonts = new Recent_Fonts(Script_Toolbar.this, fontList, mlistener, "all");
                rv_all_fonts.setAdapter(all_fonts);

            }
        });

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rel_font_style_main.setVisibility(View.VISIBLE);
                rel_font_style_.setVisibility(View.GONE);
            }
        });


        try {
            if (title.equals("") || title.equals(null)) {
                tv_title.setHint("Title");
            } else {
                if (title.length() > 28) {
                    tv_title.setText(title.substring(0, 28) + "....");
                } else
                    tv_title.setText(title);
            }
            if (desc.equals("") || desc.equals(null) || desc.equals(" ")) {
                et_tv.setHint("Start writing , copy and paste text from another app or..");
                et_tv.setTextSize(13);

            } else {


                desc = desc.replaceAll("<style([\\s\\S]+?)</style>", " ");

                if (title.equals("Welcome - Start here")) {
                    desc = desc.replaceAll("(?m)^[ \t]*\r?", " ");
                } else {
                    desc = desc.replaceAll("\n", "<br/>");

                }
                et_tv.setText(Html.fromHtml(desc));

                if (desc_color.equals("") || desc_color.equals(null)) {
                    Log.e("exception l3", "" + desc_color.toString());
                    et_tv.setTextColor(Color.BLACK);
                } else {

                    try {
                        et_tv.setTextColor(Integer.parseInt(desc_color));
                        Log.e("exception l", "" + desc_color.toString());
                    } catch (NumberFormatException e) {
                        desc_color = String.valueOf(Color.BLACK);
                    }

                }
            }

        } catch (NullPointerException e) {

        }


        iv_import.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_visible.setVisibility(View.VISIBLE);
                iv_active.setVisibility(View.VISIBLE);
            }
        });

        iv_active.setOnClickListener(new View.OnClickListener() {
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
//                textSize = textSize + (progress - saveProgress);
//                saveProgress = progress;
//

//                Toast.makeText(Script_Toolbar.this, seekbarr, Toast.LENGTH_SHORT).show();
scrTextSize=progress+".0";
                et_tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,(float)progress);

            }
        });


        if (Tag_value.equals("1")) {
            tv_import.setVisibility(View.VISIBLE);
            iv_active.setVisibility(View.GONE);
            iv_import.setVisibility(View.VISIBLE);
            rl_data.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button));

        } else if (Tag_value.equals("2")) {
            et_tv.setCursorVisible(false);
            iv_active.setVisibility(View.VISIBLE);
            iv_import.setVisibility(View.GONE);
            tv_import.setVisibility(View.GONE);
            rl_data.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button));


        } else if (Tag_value.equals("3")) {
            desc_fist = intent.getStringExtra("description");
            desc_color = intent.getStringExtra("desc_color");
            telepromptTextSize = intent.getStringExtra(Global_Constants.PROMPT_TEXT_SIZE);
            alignment_tag = intent.getStringExtra("alignment");
            tv_import.setVisibility(View.GONE);
            tv_title.setText(title);
            et_tv.setText(desc_fist);
            et_tv.setTextColor(Integer.parseInt(desc_color));
            //  et_tv.setTextSize(Float.parseFloat(telepromptTextSize));
            try {
                if (alignment_tag.equals("left")) {
                    et_tv.setGravity(Gravity.LEFT);
                } else if (alignment_tag.equals("right")) {
                    et_tv.setGravity(Gravity.RIGHT);
                } else {
                    et_tv.setGravity(Gravity.CENTER);
                }
            } catch (Exception e) {

            }
        } else if (Tag_value.equals("4")) {
            rl_data.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button));
            tv_import.setVisibility(View.GONE);

            tv_title.setText(title_recent);
            et_tv.setText(text_recent);
        } else {
            rl_data.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button));
            tv_import.setVisibility(View.GONE);
        }
        ///colored click


        et_tv.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {


//                et_tv.setSelection(et_tv.getText().toString().length());
                et_tv.setCursorVisible(true);
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


        et_tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (desc_color != null) {
                    try {
                        et_tv.setTextColor(Integer.parseInt(desc_color));
                    } catch (NumberFormatException e) {

                    }

                } else {
                    et_tv.setTextColor(Color.BLACK);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_tv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {

//                    et_tv.setCursorVisible(false);
                    editScriptData();
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
                        if (flag) {
                            //Activity is not calling for first time now

                            if (tv_title.getText().toString().equals(titleeee)) {
                                attribut = et_tv.getText().toString();
                                attribut_api = getColoredSpanned(attribut, Integer.parseInt(desc_color));


                                if (attribut_api != null) {
                                    aaa = attribut_api;

                                    try {
                                        EditScriptapi(userid, addScript_id, story_board_id, scrTitle, script_text, aaa, scrTextSize, telepromptTextSize, "4", "10.0", "false", "140.0", "", desc_color, alignment_tag);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                } else {

                                    if (desc_color != null) {
                                        aaa = getColoredSpanned(attribut, Integer.parseInt(desc_color));
                                    } else {
                                        aaa = getColoredSpanned(attribut, Color.BLACK);
                                    }
                                    try {
                                        EditScriptapi(userid, addScript_id, story_board_id, scrTitle, script_text, aaa, scrTextSize, telepromptTextSize, "4", "10.0", "false", "140.0", "", desc_color, alignment_tag);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            } else {
//                                AddScriptapi(userid, scrTitle, script_text,"");
                            }

                        } else {

                        }

//                        AddScriptapi(userid, scrTitle, " ");
                    } else if (Tag_value.equals("2")) {
                        attribut = et_tv.getText().toString();
                        attribut_api = getColoredSpanned(attribut, Integer.parseInt(desc_color));

                        if (attribut_api != null) {
                            aaa = attribut_api;
                            try {
                                EditScriptapi(userid, script_id, story_board_id, scrTitle, script_text, aaa, scrTextSize, telepromptTextSize, "4", "10.0", "false", "140.0", "", desc_color, alignment_tag);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {

                            if (desc_color != null) {
                                aaa = getColoredSpanned(attribut, Integer.parseInt(desc_color));
                            } else {
                                aaa = getColoredSpanned(attribut, Color.BLACK);
                            }
                            try {
                                EditScriptapi(userid, script_id, story_board_id, scrTitle, script_text, aaa, scrTextSize, telepromptTextSize, "4", "10.0", "false", "140.0", "", desc_color, alignment_tag);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    } else {

                    }


                }
                return false;
            }
        });


        iv_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                index = et_tv.getSelectionStart();
                rl_red.setVisibility(View.VISIBLE);
                rl_orange.setVisibility(View.GONE);
                rl_yellow.setVisibility(View.GONE);
                rl_green.setVisibility(View.GONE);
                rl_skyblue.setVisibility(View.GONE);
                rl_purple.setVisibility(View.GONE);
                rl_blue.setVisibility(View.GONE);
                rl_darkgrey.setVisibility(View.GONE);
                rl_grey.setVisibility(View.GONE);
                rl_lightgrey.setVisibility(View.GONE);
                rl_white.setVisibility(View.GONE);
                rl_black.setVisibility(View.GONE);

                attribut = et_tv.getText().toString();
                desc_color = String.valueOf(Color.RED);

                if (alignment_tag.equals("left")) {
                    attribut_api = getColoredSpanned(attribut, Color.RED);
                } else if (alignment_tag.equals("right")) {
                    attribut_api = getColoredSpanned(attribut, Color.RED);
                } else if (alignment_tag.equals("center")) {
                    attribut_api = getColoredSpanned(attribut, Color.RED);
                } else {
                    attribut_api = getColoredSpanned(attribut, Color.RED);
                }
                attribut_api = attribut_api.replaceAll("\n", "<br/>");
                et_tv.setText(Html.fromHtml(attribut_api));
                et_tv.setSelection(index);

//                et_tv.setTextColor(getResources().getColor(R.color.app_color));
//                attribut_api = getColoredSpanned(attribut, Color.RED);

            }
        });
        iv_orange.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                index = et_tv.getSelectionStart();

                rl_red.setVisibility(View.GONE);
                rl_orange.setVisibility(View.VISIBLE);
                rl_yellow.setVisibility(View.GONE);
                rl_green.setVisibility(View.GONE);
                rl_skyblue.setVisibility(View.GONE);
                rl_purple.setVisibility(View.GONE);
                rl_blue.setVisibility(View.GONE);
                rl_darkgrey.setVisibility(View.GONE);
                rl_grey.setVisibility(View.GONE);
                rl_lightgrey.setVisibility(View.GONE);
                rl_white.setVisibility(View.GONE);
                rl_black.setVisibility(View.GONE);

                attribut = et_tv.getText().toString();
                desc_color = String.valueOf(Color.parseColor(getString(R.color.orange)));
//                attribut_api = getColoredSpanned(attribut, (Color.parseColor(getString(R.color.orange))));

                if (alignment_tag.equals("left")) {
                    attribut_api = getColoredSpanned(attribut, (Color.parseColor(getString(R.color.orange))));
                } else if (alignment_tag.equals("right")) {
                    attribut_api = getColoredSpanned(attribut, (Color.parseColor(getString(R.color.orange))));
                } else if (alignment_tag.equals("center")) {
                    attribut_api = getColoredSpanned(attribut, (Color.parseColor(getString(R.color.orange))));
                } else {
                    attribut_api = getColoredSpanned(attribut, (Color.parseColor(getString(R.color.orange))));
                }

                attribut_api = attribut_api.replaceAll("\n", "<br/>");
                et_tv.setText(Html.fromHtml(attribut_api));
                et_tv.setSelection(index);


            }
        });


        iv_yellow.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                index = et_tv.getSelectionStart();
//                et_tv.setCursorVisible(false);
                rl_red.setVisibility(View.GONE);
                rl_orange.setVisibility(View.GONE);
                rl_yellow.setVisibility(View.VISIBLE);
                rl_green.setVisibility(View.GONE);
                rl_skyblue.setVisibility(View.GONE);
                rl_purple.setVisibility(View.GONE);
                rl_blue.setVisibility(View.GONE);
                rl_darkgrey.setVisibility(View.GONE);
                rl_grey.setVisibility(View.GONE);
                rl_lightgrey.setVisibility(View.GONE);
                rl_white.setVisibility(View.GONE);
                rl_black.setVisibility(View.GONE);


                attribut = et_tv.getText().toString();
                desc_color = String.valueOf(Color.parseColor(getString(R.color.yellow)));
//                attribut_api = getColoredSpanned(attribut, (Color.parseColor(getString(R.color.yellow))));

                if (alignment_tag.equals("left")) {
                    attribut_api = getColoredSpanned(attribut, (Color.parseColor(getString(R.color.yellow))));
                } else if (alignment_tag.equals("right")) {
                    attribut_api = getColoredSpanned(attribut, (Color.parseColor(getString(R.color.yellow))));
                } else if (alignment_tag.equals("center")) {
                    attribut_api = getColoredSpanned(attribut, (Color.parseColor(getString(R.color.yellow))));
                } else {
                    attribut_api = getColoredSpanned(attribut, (Color.parseColor(getString(R.color.yellow))));
                }
                attribut_api = attribut_api.replaceAll("\n", "<br/>");
                et_tv.setText(Html.fromHtml(attribut_api));
                et_tv.setSelection(index);


            }
        });
        iv_green.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                index = et_tv.getSelectionStart();
//                et_tv.setCursorVisible(false);
                rl_red.setVisibility(View.GONE);
                rl_orange.setVisibility(View.GONE);
                rl_yellow.setVisibility(View.GONE);
                rl_green.setVisibility(View.VISIBLE);
                rl_yellow.setVisibility(View.GONE);

                rl_skyblue.setVisibility(View.GONE);
                rl_purple.setVisibility(View.GONE);
                rl_blue.setVisibility(View.GONE);
                rl_darkgrey.setVisibility(View.GONE);
                rl_grey.setVisibility(View.GONE);
                rl_lightgrey.setVisibility(View.GONE);
                rl_white.setVisibility(View.GONE);
                rl_black.setVisibility(View.GONE);

                attribut = et_tv.getText().toString();
                desc_color = String.valueOf(Color.parseColor(getString(R.color.green)));
//                attribut_api = getColoredSpanned(attribut, (Color.parseColor(getString(R.color.green))));

                if (alignment_tag.equals("left")) {
                    attribut_api = getColoredSpanned(attribut, (Color.parseColor(getString(R.color.green))));
                } else if (alignment_tag.equals("right")) {
                    attribut_api = getColoredSpanned(attribut, (Color.parseColor(getString(R.color.green))));
                } else if (alignment_tag.equals("center")) {
                    attribut_api = getColoredSpanned(attribut, (Color.parseColor(getString(R.color.green))));
                } else {
                    attribut_api = getColoredSpanned(attribut, (Color.parseColor(getString(R.color.green))));
                }
                attribut_api = attribut_api.replaceAll("\n", "<br/>");
                et_tv.setText(Html.fromHtml(attribut_api));
                et_tv.setSelection(index);

            }
        });
        iv_skyblue.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                index = et_tv.getSelectionStart();
//                et_tv.setCursorVisible(false);
                rl_red.setVisibility(View.GONE);
                rl_orange.setVisibility(View.GONE);
                rl_yellow.setVisibility(View.GONE);
                rl_green.setVisibility(View.GONE);
                rl_skyblue.setVisibility(View.VISIBLE);
                rl_yellow.setVisibility(View.GONE);
                rl_purple.setVisibility(View.GONE);
                rl_blue.setVisibility(View.GONE);
                rl_darkgrey.setVisibility(View.GONE);
                rl_grey.setVisibility(View.GONE);
                rl_lightgrey.setVisibility(View.GONE);
                rl_white.setVisibility(View.GONE);
                rl_black.setVisibility(View.GONE);
                attribut = et_tv.getText().toString();
                desc_color = String.valueOf(Color.parseColor(getString(R.color.sky_blue)));
//                attribut_api = getColoredSpanned(attribut, (Color.parseColor(getString(R.color.sky_blue))));

                if (alignment_tag.equals("left")) {
                    attribut_api = getColoredSpanned(attribut, (Color.parseColor(getString(R.color.sky_blue))));
                } else if (alignment_tag.equals("right")) {
                    attribut_api = getColoredSpanned(attribut, (Color.parseColor(getString(R.color.sky_blue))));
                } else if (alignment_tag.equals("center")) {
                    attribut_api = getColoredSpanned(attribut, (Color.parseColor(getString(R.color.sky_blue))));
                } else {
                    attribut_api = getColoredSpanned(attribut, (Color.parseColor(getString(R.color.sky_blue))));
                }
                attribut_api = attribut_api.replaceAll("\n", "<br/>");
                et_tv.setText(Html.fromHtml(attribut_api));
                et_tv.setSelection(index);

            }
        });
        iv_purple.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                index = et_tv.getSelectionStart();
//                et_tv.setCursorVisible(false);
                rl_red.setVisibility(View.GONE);
                rl_orange.setVisibility(View.GONE);
                rl_yellow.setVisibility(View.GONE);
                rl_green.setVisibility(View.GONE);
                rl_skyblue.setVisibility(View.GONE);
                rl_yellow.setVisibility(View.GONE);
                rl_purple.setVisibility(View.VISIBLE);
                rl_blue.setVisibility(View.GONE);
                rl_darkgrey.setVisibility(View.GONE);
                rl_grey.setVisibility(View.GONE);
                rl_lightgrey.setVisibility(View.GONE);
                rl_white.setVisibility(View.GONE);
                rl_black.setVisibility(View.GONE);


                attribut = et_tv.getText().toString();
                desc_color = String.valueOf(Color.parseColor(getString(R.color.purple)));
//                attribut_api = getColoredSpanned(attribut, (Color.parseColor(getString(R.color.purple))));

                if (alignment_tag.equals("left")) {
                    attribut_api = getColoredSpanned(attribut, (Color.parseColor(getString(R.color.purple))));
                } else if (alignment_tag.equals("right")) {
                    attribut_api = getColoredSpanned(attribut, (Color.parseColor(getString(R.color.purple))));
                } else if (alignment_tag.equals("center")) {
                    attribut_api = getColoredSpanned(attribut, (Color.parseColor(getString(R.color.purple))));
                } else {
                    attribut_api = getColoredSpanned(attribut, (Color.parseColor(getString(R.color.purple))));
                }

                attribut_api = attribut_api.replaceAll("\n", "<br/>");
                et_tv.setText(Html.fromHtml(attribut_api));
                et_tv.setSelection(index);

            }
        });
        iv_blue.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                index = et_tv.getSelectionStart();
//                et_tv.setCursorVisible(false);
                rl_red.setVisibility(View.GONE);
                rl_orange.setVisibility(View.GONE);
                rl_yellow.setVisibility(View.GONE);
                rl_green.setVisibility(View.GONE);
                rl_skyblue.setVisibility(View.GONE);
                rl_yellow.setVisibility(View.GONE);
                rl_purple.setVisibility(View.GONE);
                rl_blue.setVisibility(View.VISIBLE);
                rl_darkgrey.setVisibility(View.GONE);
                rl_grey.setVisibility(View.GONE);
                rl_lightgrey.setVisibility(View.GONE);
                rl_white.setVisibility(View.GONE);
                rl_black.setVisibility(View.GONE);

                attribut = et_tv.getText().toString();
                desc_color = String.valueOf(Color.parseColor(getString(R.color.blue)));
//                attribut_api = getColoredSpanned(attribut,(Color.parseColor(getString(R.color.blue))));


                if (alignment_tag.equals("left")) {
                    attribut_api = getColoredSpanned(attribut, (Color.parseColor(getString(R.color.blue))));
                } else if (alignment_tag.equals("right")) {
                    attribut_api = getColoredSpanned(attribut, (Color.parseColor(getString(R.color.blue))));
                } else if (alignment_tag.equals("center")) {
                    attribut_api = getColoredSpanned(attribut, (Color.parseColor(getString(R.color.blue))));
                } else {
                    attribut_api = getColoredSpanned(attribut, (Color.parseColor(getString(R.color.blue))));
                }

                attribut_api = attribut_api.replaceAll("\n", "<br/>");
                et_tv.setText(Html.fromHtml(attribut_api));
                et_tv.setSelection(index);

            }
        });
        iv_black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = et_tv.getSelectionStart();
//                et_tv.setCursorVisible(false);
                rl_red.setVisibility(View.GONE);
                rl_orange.setVisibility(View.GONE);
                rl_yellow.setVisibility(View.GONE);
                rl_green.setVisibility(View.GONE);
                rl_skyblue.setVisibility(View.GONE);
                rl_yellow.setVisibility(View.GONE);
                rl_purple.setVisibility(View.GONE);
                rl_blue.setVisibility(View.GONE);
                rl_darkgrey.setVisibility(View.GONE);
                rl_grey.setVisibility(View.GONE);
                rl_lightgrey.setVisibility(View.GONE);
                rl_white.setVisibility(View.GONE);
                rl_black.setVisibility(View.VISIBLE);

                attribut = et_tv.getText().toString();
                desc_color = String.valueOf(Color.BLACK);
//                attribut_api = getColoredSpanned(attribut, Color.BLACK);

                if (alignment_tag.equals("left")) {
                    attribut_api = getColoredSpanned(attribut, (Color.BLACK));
                } else if (alignment_tag.equals("right")) {
                    attribut_api = getColoredSpanned(attribut, (Color.BLACK));
                } else if (alignment_tag.equals("center")) {
                    attribut_api = getColoredSpanned(attribut, (Color.BLACK));
                } else {
                    attribut_api = getColoredSpanned(attribut, (Color.BLACK));
                }

                attribut_api = attribut_api.replaceAll("\n", "<br/>");
                et_tv.setText(Html.fromHtml(attribut_api));
                et_tv.setSelection(index);

            }
        });
        iv_darkgrey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = et_tv.getSelectionStart();
//                et_tv.setCursorVisible(false);
                rl_red.setVisibility(View.GONE);
                rl_orange.setVisibility(View.GONE);
                rl_yellow.setVisibility(View.GONE);
                rl_green.setVisibility(View.GONE);
                rl_skyblue.setVisibility(View.GONE);
                rl_yellow.setVisibility(View.GONE);
                rl_purple.setVisibility(View.GONE);
                rl_blue.setVisibility(View.GONE);
                rl_darkgrey.setVisibility(View.VISIBLE);
                rl_grey.setVisibility(View.GONE);
                rl_lightgrey.setVisibility(View.GONE);
                rl_white.setVisibility(View.GONE);
                rl_black.setVisibility(View.GONE);
                attribut = et_tv.getText().toString();
                desc_color = String.valueOf(Color.DKGRAY);
//                attribut_api = getColoredSpanned(attribut, Color.DKGRAY);

                if (alignment_tag.equals("left")) {
                    attribut_api = getColoredSpanned(attribut, (Color.DKGRAY));
                } else if (alignment_tag.equals("right")) {
                    attribut_api = getColoredSpanned(attribut, (Color.DKGRAY));
                } else if (alignment_tag.equals("center")) {
                    attribut_api = getColoredSpanned(attribut, (Color.DKGRAY));
                } else {
                    attribut_api = getColoredSpanned(attribut, (Color.DKGRAY));
                }

                attribut_api = attribut_api.replaceAll("\n", "<br/>");
                et_tv.setText(Html.fromHtml(attribut_api));
                et_tv.setSelection(index);

            }
        });
        iv_gray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = et_tv.getSelectionStart();
//                et_tv.setCursorVisible(false);

                rl_red.setVisibility(View.GONE);
                rl_orange.setVisibility(View.GONE);
                rl_yellow.setVisibility(View.GONE);
                rl_green.setVisibility(View.GONE);
                rl_skyblue.setVisibility(View.GONE);
                rl_yellow.setVisibility(View.GONE);
                rl_purple.setVisibility(View.GONE);
                rl_blue.setVisibility(View.GONE);
                rl_darkgrey.setVisibility(View.GONE);
                rl_grey.setVisibility(View.VISIBLE);
                rl_lightgrey.setVisibility(View.GONE);
                rl_white.setVisibility(View.GONE);
                rl_black.setVisibility(View.GONE);


                attribut = et_tv.getText().toString();
                desc_color = String.valueOf(Color.LTGRAY);

                if (alignment_tag.equals("left")) {
                    attribut_api = getColoredSpanned(attribut, (Color.LTGRAY));
                } else if (alignment_tag.equals("right")) {
                    attribut_api = getColoredSpanned(attribut, (Color.LTGRAY));
                } else if (alignment_tag.equals("center")) {
                    attribut_api = getColoredSpanned(attribut, (Color.LTGRAY));
                } else {
                    attribut_api = getColoredSpanned(attribut, (Color.LTGRAY));
                }

                attribut_api = attribut_api.replaceAll("\n", "<br/>");
                et_tv.setText(Html.fromHtml(attribut_api));
                et_tv.setSelection(index);

            }
        });
        iv_lightgray.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                index = et_tv.getSelectionStart();
//                et_tv.setCursorVisible(false);
                rl_red.setVisibility(View.GONE);
                rl_orange.setVisibility(View.GONE);
                rl_yellow.setVisibility(View.GONE);
                rl_green.setVisibility(View.GONE);
                rl_skyblue.setVisibility(View.GONE);
                rl_yellow.setVisibility(View.GONE);
                rl_purple.setVisibility(View.GONE);
                rl_blue.setVisibility(View.GONE);
                rl_darkgrey.setVisibility(View.GONE);
                rl_grey.setVisibility(View.GONE);
                rl_lightgrey.setVisibility(View.VISIBLE);
                rl_white.setVisibility(View.GONE);
                rl_black.setVisibility(View.GONE);
                attribut = et_tv.getText().toString();
                desc_color = String.valueOf(Color.parseColor(getString(R.color.grey_light)));
                try {
                    if (alignment_tag.equals("left")) {
                        attribut_api = getColoredSpanned(attribut, (Color.parseColor(getString(R.color.grey_light))));
                    } else if (alignment_tag.equals("right")) {
                        attribut_api = getColoredSpanned(attribut, (Color.parseColor(getString(R.color.grey_light))));
                    } else if (alignment_tag.equals("center")) {
                        attribut_api = getColoredSpanned(attribut, (Color.parseColor(getString(R.color.grey_light))));
                    } else {
                        attribut_api = getColoredSpanned(attribut, (Color.parseColor(getString(R.color.grey_light))));
                    }

                    attribut_api = attribut_api.replaceAll("\n", "<br/>");
                    et_tv.setText(Html.fromHtml(attribut_api));
                    et_tv.setSelection(index);
                } catch (Exception e) {

                }
            }
        });
        iv_white.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                index = et_tv.getSelectionStart();
//                et_tv.setCursorVisible(false);
                rl_red.setVisibility(View.GONE);
                rl_orange.setVisibility(View.GONE);
                rl_yellow.setVisibility(View.GONE);
                rl_green.setVisibility(View.GONE);
                rl_skyblue.setVisibility(View.GONE);
                rl_purple.setVisibility(View.GONE);
                rl_blue.setVisibility(View.GONE);
                rl_darkgrey.setVisibility(View.GONE);
                rl_grey.setVisibility(View.GONE);
                rl_lightgrey.setVisibility(View.GONE);
                rl_white.setVisibility(View.VISIBLE);
                rl_black.setVisibility(View.GONE);

                attribut = et_tv.getText().toString();
                desc_color = String.valueOf(Color.LTGRAY);

                if (alignment_tag.equals("left")) {
                    attribut_api = getColoredSpanned(attribut, (Color.LTGRAY));
                } else if (alignment_tag.equals("right")) {
                    attribut_api = getColoredSpanned(attribut, (Color.LTGRAY));
                } else if (alignment_tag.equals("center")) {
                    attribut_api = getColoredSpanned(attribut, (Color.LTGRAY));
                } else {
                    attribut_api = getColoredSpanned(attribut, (Color.LTGRAY));
                }

                attribut_api = attribut_api.replaceAll("\n", "<br/>");
                et_tv.setText(Html.fromHtml(attribut_api));
                et_tv.setSelection(index);


            }
        });


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (getCurrentFocus() != null)
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onBackPressed();
                    }
                }, 1000);

            }
        });


        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(Script_Toolbar.this, "jsjkjsk", Toast.LENGTH_SHORT).show();
                shared_prefrencePrompster.setAlign("left");
                alignment_tag = "left";
                et_tv.setGravity(Gravity.LEFT);
            }
        });

        iv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(Script_Toolbar.this, "jsjkjsk", Toast.LENGTH_SHORT).show();
                shared_prefrencePrompster.setAlign("right");
                et_tv.setGravity(Gravity.RIGHT);
                alignment_tag = "right";
            }
        });

        iv_centre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(Script_Toolbar.this, "jsjkjsk", Toast.LENGTH_SHORT).show();
                shared_prefrencePrompster.setAlign("centre");
                et_tv.setGravity(Gravity.CENTER);
                alignment_tag = "center";
            }
        });


        iv_importalphabet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                scrTitle = tv_title.getText().toString();
                script_text = et_tv.getText().toString();
//                AddScriptapi(userid, scrTitle, script_text,"");


            }
        });


        iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Script_Toolbar.this, Script_first.class);
                intent.putExtra("title", title);
                intent.putExtra("desc", et_tv.getText().toString());
                intent.putExtra("script", Tag_profile);
                intent.putExtra("script_id", script_id);
                intent.putExtra(Global_Constants.SCRIPT_TEXT_SIZE, scrTextSize);
                intent.putExtra("fontfamily", selectedFontFamily);
                intent.putExtra("desc_color", desc_color);
                intent.putExtra("alignment", alignment_tag);
                intent.putExtra(Global_Constants.STORYBOARD_ID, story_board_id);
                intent.putExtra(Global_Constants.PROMPT_TEXT_SIZE, telepromptTextSize);
                intent.putExtra(Global_Constants.SCRIPT_PRIMARYKEY, String.valueOf(primaryKey));
                startActivity(intent);
                finish();

            }
        });


        iv_export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_export_active.setVisibility(View.VISIBLE);

//                Toast.makeText(Script_Toolbar.this, "hellooo", Toast.LENGTH_SHORT).show();
                show = new Dialog(Script_Toolbar.this);


                show.setContentView(R.layout.export_layout);
                show.setCanceledOnTouchOutside(false);
                show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                tv_print = show.findViewById(R.id.tv_print);
                tv_email = show.findViewById(R.id.tv_email);

                tv_cancell = show.findViewById(R.id.tv_cancell);

                tv_print.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        show.dismiss();
                        printPDF(view);

                    }
                });

                tv_email.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        try {
                            root = new File(Environment.getExternalStorageDirectory(), "Download/Scriptively" + "/textFile/");
                            if (!root.exists()) {
                                root.mkdirs();
                            }
                            gpxfile = new File(root, System.currentTimeMillis() + ".txt");
                            FileWriter writer = new FileWriter(gpxfile);
                            if (desc.equals("") || desc == null) {
                                writer.append(title + "\n\n" + "Start Writing Here.........");
                            } else {
                                writer.append(title + "\n\n" + Html.fromHtml(desc));
                            }

                            writer.flush();
                            writer.close();
                            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        /*Create an ACTION_SEND Intent*/
                        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                        /*This will be the actual content you wish you share.*/
                        File shareBody = new File(Uri.fromFile(gpxfile.getAbsoluteFile()).getPath());
                        Uri attachment = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", shareBody);
                        /*The type of the content is text, obviously.*/
//                        intent.setType("*/*");
                        intent.setType("vnd.android.cursor.dir/email");
                        /*Applying information Subject and Body.*/
                        String to[] = {""};
                        intent.putExtra(Intent.EXTRA_EMAIL, to);

                        intent.putExtra(android.content.Intent.EXTRA_TEXT, "Here's the latest version of" + " " + title + " " + "from Scriptively!");
                        intent.putExtra(android.content.Intent.EXTRA_STREAM, Uri.parse("" + attachment));
                        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, title + "," + " " + "created with Scriptively!");
                        /*Fire!*/

                        startActivity(Intent.createChooser(intent, title + "," + " " + "created with Scriptively!"));
//                        startActivity(Intent.createChooser(intent, title + "," + " " + "created with Scriptively!"));
                        show.dismiss();

                    }
                });

                tv_cancell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        show.dismiss();

                    }
                });


                show.show();

            }

        });

        tv_import.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(Script_Toolbar.this, "hellooo", Toast.LENGTH_SHORT).show();
                show = new Dialog(Script_Toolbar.this);


                show.setContentView(R.layout.import_layout);
                show.setCanceledOnTouchOutside(false);
                show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                tv_dropbox = show.findViewById(R.id.tv_dropbox);
                tv_filess = show.findViewById(R.id.tv_filess);
                tv_googledrive = show.findViewById(R.id.tv_googledrive);
                tv_cancel = show.findViewById(R.id.tv_cancel);

                tv_filess.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            // start runtime permission
                            Boolean hasPermission = (ContextCompat.checkSelfPermission(Script_Toolbar.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                                    == PackageManager.PERMISSION_GRANTED);
                            if (!hasPermission) {
                                Log.e("TAG", "get permision   ");
                                ActivityCompat.requestPermissions(Script_Toolbar.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, request_code);
                            } else {
                                Log.e("TAG", "get permision-- already granted ");

                                show.dismiss();
                                showFileChooser();
                            }
                        } else {
                            //readfile();
                            showFileChooser();
                        }

                    }
                });

                tv_dropbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Util.checkDropbox) {
                            //  DropboxActivity.startOAuth2Authentication(Script_Toolbar.this, getString(R.string.app_key), Arrays.asList("account_info.read", "files.content.write", "files.content.read"));

                            Intent intentt = new Intent(Script_Toolbar.this, DropBox.class);
                            intentt.putExtra("drive", "2");
                            startActivity(intentt);
                        } else {
                            DropboxActivity.startOAuth2Authentication(Script_Toolbar.this, getString(R.string.app_key), Arrays.asList("account_info.read", "files.content.write", "files.content.read"));
                            Util.checkDropbox = true;
//                            startActivity(new Intent(Navigation.this, DropBox.class));
                        }
                        show.dismiss();

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
                        show.dismiss();
                        Intent intent = new Intent(Script_Toolbar.this, GoogleDriveFiles.class);
                        intent.putExtra("drive", "1");
                        startActivity(intent);

                    }
                });

                show.show();

            }

        });


        rl_media.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                tv_script.setTextSize(14);
//                tv_storyBoard.setTextSize(14);
//                tv_media.setTextSize(15);
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (getCurrentFocus() != null)
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                Tag_profile = "Media";
                view_script.setVisibility(View.GONE);
                view_board.setVisibility(View.GONE);
                rl_frame.setVisibility(View.VISIBLE);
                rl_data2.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button2));
                rl_data.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button2));
                rl_media.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button));
//                rl_data3.setVisibility(View.VISIBLE);
//                rl_data1.setVisibility(View.GONE);

                rl_titile.setVisibility(View.GONE);
                Fragment_media fragment_media = new Fragment_media();
                Bundle arguments = new Bundle();
                FragmentManager manager = getSupportFragmentManager();
                arguments.putString("script_id", script_id);
                arguments.putString("script_title", tv_title.getText().toString());
                fragment_media.setArguments(arguments);
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.frame, fragment_media).commit();

            }
        });


        rl_data2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                Tag_profile = "Story";
                from = "frag";

                if (Util.flag) {
                    from = "";
                    view_script.setVisibility(View.GONE);
                    view_board.setVisibility(View.GONE);
                    rl_frame.setVisibility(View.VISIBLE);
                    rl_media.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button2));
                    rl_data2.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button));
                    rl_data.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button2));
                    rl_titile.setVisibility(View.GONE);
                    Fragment_Board fragment_board = new Fragment_Board();
                    Bundle arguments = new Bundle();
                    FragmentManager manager = getSupportFragmentManager();
                    arguments.putString("script_id", script_id);
                    arguments.putString("script_title", tv_title.getText().toString());
                    arguments.putInt("script_primaryKey", primaryKey);
                    fragment_board.setArguments(arguments);
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.frame, fragment_board).commit();
                } else {
                    Util.flag = true;
                }


                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (getCurrentFocus() != null)
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                scrTitle = tv_title.getText().toString().trim();
                script_text = et_tv.getText().toString().trim();


            }
        });


        rl_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tag_profile = "Script";
                view_script.setVisibility(View.GONE);
                view_board.setVisibility(View.GONE);
                rl_frame.setVisibility(View.GONE);
                rl_titile.setVisibility(View.VISIBLE);
//
                rl_media.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button2));
                rl_data2.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button2));
                rl_data.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button));


            }
        });
    }

    void editScriptData() {

        scrTitle = tv_title.getText().toString().trim();
        script_text = et_tv.getText().toString();

        if (scrTitle.equals("")) {

            dialog = new Dialog(Script_Toolbar.this);

            dialog.setContentView(R.layout.title);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            tv_ok = dialog.findViewById(R.id.tv_ok);

            tv_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();

                }
            });
            dialog.show();
        } else {
            if (Tag_value.equals("1")) {

                if (flag == true) {

                    //Activity is not calling for first time now

                    if (tv_title.getText().toString().equals(titleeee)) {
                        attribut = et_tv.getText().toString();
                        try {
                            attribut_api = getColoredSpanned(attribut, Integer.parseInt(desc_color));
                        } catch (NumberFormatException e) {

                        }

                        if (attribut_api != null) {
                            aaa = attribut_api;

                            try {

                                if (alignment_tag != null) {
                                    final_align = alignment_tag;
                                    EditScriptapi(userid, addScript_id, story_board_id, scrTitle, script_text, aaa, scrTextSize, telepromptTextSize, "4", "10.0", "false", "140.0", "", desc_color, alignment_tag);

                                } else {
                                    EditScriptapi(userid, addScript_id, story_board_id, scrTitle, script_text, aaa, scrTextSize, telepromptTextSize, "4", "10.0", "false", "140.0", "", desc_color, alignment_tag);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {
                            try {
                                if (desc_color != null) {
                                    aaa = getColoredSpanned(attribut, Integer.parseInt(desc_color));
                                } else {
                                    aaa = getColoredSpanned(attribut, Color.BLACK);
                                }
                            } catch (NumberFormatException e) {

                            }

                            try {
                                if (alignment_tag != null) {
                                    final_align = alignment_tag;
                                    EditScriptapi(userid, addScript_id, story_board_id, scrTitle, script_text, aaa, scrTextSize, telepromptTextSize, "4", "10.0", "false", "140.0", "", desc_color, alignment_tag);

                                } else {
                                    EditScriptapi(userid, addScript_id, story_board_id, scrTitle, script_text, aaa, scrTextSize, telepromptTextSize, "4", "10.0", "false", "140.0", "", desc_color, alignment_tag);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    } else {
//                                    AddScriptapi(userid, scrTitle, script_text,"");
                    }

                } else {
                    //first time calling activity
//                            flag=true;

//                                AddScriptapi(userid, scrTitle, script_text,"");
                }


            } else if (Tag_value.equals("2")) {

                attribut = et_tv.getText().toString();
                try {
                    attribut_api = getColoredSpanned(attribut, Integer.parseInt(desc_color));

                } catch (NumberFormatException e) {

                }

                if (attribut_api != null) {
                    aaa = attribut_api;

                    try {
                        if (alignment_tag != null) {
                            final_align = alignment_tag;
                            EditScriptapi(userid, script_id, story_board_id, scrTitle, script_text, aaa, scrTextSize, telepromptTextSize, telepromptTextSize, "10.0", "false", "140.0", "", desc_color, alignment_tag);

                        } else {
                            EditScriptapi(userid, script_id, story_board_id, scrTitle, script_text, aaa, scrTextSize, telepromptTextSize, telepromptTextSize, "10.0", "false", "140.0", "", desc_color, alignment_tag);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        if (desc_color != null) {
                            aaa = getColoredSpanned(attribut, Integer.parseInt(desc_color));
                        } else {
                            aaa = getColoredSpanned(attribut, Color.BLACK);
                        }
                    } catch (NumberFormatException e) {

                    }


                    try {
                        if (alignment_tag != null) {
                            final_align = alignment_tag;
                            EditScriptapi(userid, script_id, story_board_id, scrTitle, script_text, aaa, scrTextSize, telepromptTextSize, "4", "10.0", "false", "140.0", "", desc_color, alignment_tag);

                        } else {
                            EditScriptapi(userid, script_id, story_board_id, scrTitle, script_text, aaa, scrTextSize, telepromptTextSize, "4", "10.0", "false", "140.0", "", desc_color, alignment_tag);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            } else {
                tv_import.setVisibility(View.GONE);

                desc_fist = intent.getStringExtra("descriptionn");
                desc_color = intent.getStringExtra("desc_color");
                telepromptTextSize = intent.getStringExtra(Global_Constants.PROMPT_TEXT_SIZE);
                alignment_tag = intent.getStringExtra("alignment");
                tv_import.setVisibility(View.GONE);
                tv_title.setText(title);
                et_tv.setText(desc_fist);
                et_tv.setTextColor(Integer.parseInt(desc_color));
                //  et_tv.setTextSize(Float.parseFloat(telepromptTextSize));
                if (alignment_tag.equals("left")) {
                    et_tv.setGravity(Gravity.LEFT);
                } else if (alignment_tag.equals("right")) {
                    et_tv.setGravity(Gravity.RIGHT);
                } else {
                    et_tv.setGravity(Gravity.CENTER);
                }
            }

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        try {

            telepromptTextSize = intent.getStringExtra(Global_Constants.PROMPT_TEXT_SIZE);

            if (telepromptTextSize.equals("") ) {

                seekbar.setProgress(35);
                telepromptTextSize = "35.5";
            } else {
                //seekbar.setProgress(Integer.parseInt(telepromptTextSize));

                //    et_tv.setTextSize(Float.valueOf(telepromptTextSize));
            }
        } catch (NullPointerException e) {

        }
    }

    private void showFileChooser() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_CODE);
        } catch (Exception e) {
            Log.e("TAG", " choose file error " + e.toString());
        }

    }

    private void synonymsApi(String dictionary) {

        dialog_progress = new ACProgressFlower.Builder(this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Please wait...")
                .fadeColor(Color.DKGRAY).build();
        dialog_progress.show();


        Retrofit retrofit = null;
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(7000, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS).build();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.wordnik.com/v4/word.json/" + dictionary + "/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)

                .build();
        ApiInterface apiService = retrofit.create(ApiInterface.class);


        retrofit2.Call<ResponseBody> call = apiService.synonyms();
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.e("synonymsApi url", call.request().toString());
                try {

                    if (response.isSuccessful()) {

                        String data = "", wordsArray = "";

                        data = response.body().string();
                        JSONArray jsonArray = new JSONArray(data);
                        Log.e("synonymsApi...........", jsonArray.toString());

                        JSONArray jsonArray1 = jsonArray.getJSONObject(0).getJSONArray("words");
                        defination.clear();
                        partOfSpeech.clear();

                        for (int i = 0; i < jsonArray1.length(); i++) {
                            defination.add(jsonArray1.get(i).toString());
                            partOfSpeech.add("");
                        }

                        az_adapter = new AZ_Adapter(Script_Toolbar.this, defination, mlistener, dic_syn_tag, partOfSpeech);
                        rv_AZ.setAdapter(az_adapter);

                        dialog_progress.dismiss();
                    } else {
                        dialog_progress.dismiss();
                        defination.clear();
                        partOfSpeech.clear();
                        az_adapter.notifyDataSetChanged();
                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialog_progress.dismiss();
                Log.e("errror", String.valueOf(t));

            }
        });

    }

    private void dictionaryApi(String dictionary) {


        dialog_progress = new ACProgressFlower.Builder(this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Please wait...")
                .fadeColor(Color.DKGRAY).build();
        dialog_progress.show();

        Retrofit retrofit = null;
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(7000, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS).build();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.wordnik.com/v4/word.json/" + dictionary + "/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)

                .build();
        ApiInterface apiService = retrofit.create(ApiInterface.class);


        retrofit2.Call<ResponseBody> call = apiService.dictionary();
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.e("Services url", call.request().toString());
                try {

                    if (response.isSuccessful()) {

                        String data = "";

                        data = response.body().string();
                        JSONArray jsonArray = new JSONArray(data);
                        Log.e("Dictonary...........", jsonArray.toString());
                        defination.clear();
                        partOfSpeech.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            if (jsonArray.getJSONObject(i).has("text") && jsonArray.getJSONObject(i).has("partOfSpeech")) {
                                partOfSpeech.add(jsonArray.getJSONObject(i).getString("partOfSpeech"));
                                defination.add(jsonArray.getJSONObject(i).getString("text"));
                            } else if (jsonArray.getJSONObject(i).has("partOfSpeech")) {
                                partOfSpeech.add(jsonArray.getJSONObject(i).getString("partOfSpeech"));
                                defination.add("");
                            }
                        }
                        tv_def_syno.setVisibility(View.VISIBLE);
                        az_adapter = new AZ_Adapter(Script_Toolbar.this, defination, mlistener, dic_syn_tag, partOfSpeech);
                        rv_AZ.setAdapter(az_adapter);

                        dialog_progress.dismiss();

                    } else {
                        dialog_progress.dismiss();
                        defination.clear();
                        partOfSpeech.clear();
                        az_adapter.notifyDataSetChanged();
                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialog_progress.dismiss();
                Log.e("errror", String.valueOf(t));

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void printPDF(View view) {

        PrintManager printManager = (PrintManager) getSystemService(PRINT_SERVICE);
        printManager.print("print_any_view_job_name", new ViewPrintAdapter(this,
                findViewById(R.id.et_tv_description)), null);
    }

    private String getColoredSpanned(String attribut, int red) {


        String input = "<font color=" + red + ">" + attribut + "</font>";
        return input;
    }

    private void EditScriptapi(String userid, String script_id, String story_board_id, String scrTitle, String script_text, String bold,
                               String scriptTextSize, String promptTextSize, String s2, String s3, String aFalse, String s4, String s5, String desc_color, String alignment_tagg) throws JSONException {


        if (Util.isConnected()) {

            new EditSriptAsync(this, scrTitle, primaryKey, userid, script_id, story_board_id, script_text, bold, 1, alignment_tagg, desc_color, scriptTextSize, promptTextSize, s2, s3, s4, s5, aFalse).execute();
        } else {
            new EditSriptAsync(this, scrTitle, primaryKey, userid, script_id, story_board_id, script_text, bold, 0, alignment_tagg, desc_color, scriptTextSize, promptTextSize, s2, s3, s4, s5, aFalse).execute();
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }

    public void AddScriptapi(final String userid, String scrTitle, final String script_text, String new_value, String scriptKey) throws JSONException {

//        Log.e("script_text.....", script_text);
        String input = "<div style=\\\"text-align: center>" + "<font color=" + String.valueOf(Color.BLACK) + ">" + script_text + "</font>" + "</div>";
        Log.e("input.............", input);

        currentDate = new Date();
        String unix_timestamp = String.valueOf(currentDate.getTime() / 1000);
        Log.e("UnixTimeStamp......", String.valueOf(currentDate.getTime()));


        if (Util.isConnected()) {


            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
            JSONObject mySelection = new JSONObject();
            try {
                mySelection.put("user_id", userid);
                mySelection.put("scrTitle", scrTitle);
                mySelection.put("scrText", script_text);
                mySelection.put("scrAttrText", input);
                if (telepromptTextSize != null) {
                    mySelection.put("scrEditTextSize","35.5");
                } else {
                    mySelection.put("scrEditTextSize",  "35.5");
                }
                mySelection.put("scrPromptTextSize",  "35.5");
                mySelection.put("scrPromptSpeed", "35.5");
                mySelection.put("textMargin", "10.0");
                mySelection.put("marker", "false");
                mySelection.put("markerX", "140.0");
                mySelection.put("uuid", userid + "-" + unix_timestamp);
                mySelection.put("scrManualScrolling", "false");
                mySelection.put("scrCameraRecordMe", "false");
                mySelection.put("scrShowMeFullScreen", "false");
                mySelection.put("scrShowMeThumbnail", "false");
                mySelection.put("scrVoiceRecordMe", "false");
                if (desc_color != null) {
                    mySelection.put("scrColor", desc_color);

                } else {
                    mySelection.put("scrColor", String.valueOf(Color.BLACK));
                }

                mySelection.put("scrAlignment", "left");
                Log.e("myselection", String.valueOf(mySelection));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = RequestBody.create(mediaType, String.valueOf(mySelection));

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Call<AddScript> call = apiService.add_script(body);


//        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//        retrofit2.Call<AddScript> call = apiService.add_script(userid, scrTitle, script_text, input, "20", "20", "28", "10.0", "false", "140.0", "", "false", "false", "false", "false", "false", String.valueOf(Color.BLACK), "center");
            call.enqueue(new retrofit2.Callback<AddScript>() {
                @Override
                public void onResponse(Call<AddScript> call, Response<AddScript> response) {


                    Log.e("Add Script url", new Gson().toJson(call.request()));

                    if (response.isSuccessful()) {

                        Log.w("Add Script response", new Gson().toJson(response));

                        if (response.body().getSuccess().toString().equals("1")) {
                            Log.e("Add Script response", new Gson().toJson(response));
                            desc = response.body().getData().getScrAttrText().toString();

                            addScript_id = response.body().getData().getId().toString();
                            script_id = addScript_id;
                            title = response.body().getData().getScrTitle().toString();
                            titleeee = response.body().getData().getScrTitle().toString();
                            createdAt = response.body().getData().getCreatedAt().toString();

                            if (new_value.equals("new")) {
                                scriptDB = new ScriptDB(input, scrTitle, createdAt, script_id,
                                        "", response.body().getData().getScrAlignment(), "", userid, script_text,
                                        1, 1, "25", response.body().getData().getScrAttrText(),
                                        response.body().getData().getScrEditTextSize(), response.body().getData().getScrPromptTextSize());

                                new SaveAsyncTask(scriptDB, Script_Toolbar.this).execute();

                            } else {
                                new Navigation.UpdateAsyncTask(Integer.parseInt(scriptKey), Script_Toolbar.this, script_id).execute();
                                new GetstoryfalseDataAsyncTask(Integer.parseInt(scriptKey), Script_Toolbar.this, script_id).execute();
                            }

                            //   addStoryBoardapi(primaryKey,userid, script_id, "Title","new");
                            final Handler handler = new Handler(Looper.getMainLooper());
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (from.equals("frag")) {
                                        Util.flag = true;
                                        from = "";
                                        view_script.setVisibility(View.GONE);
                                        view_board.setVisibility(View.GONE);
                                        rl_frame.setVisibility(View.VISIBLE);
                                        rl_media.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button2));
                                        rl_data2.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button));
                                        rl_data.setBackground(ContextCompat.getDrawable(Script_Toolbar.this, R.drawable.script_button2));
                                        rl_titile.setVisibility(View.GONE);
                                        Fragment_Board fragment_board = new Fragment_Board();
                                        Bundle arguments = new Bundle();
                                        FragmentManager manager = getSupportFragmentManager();
                                        arguments.putString("script_id", addScript_id);
                                        arguments.putString("script_title", tv_title.getText().toString());
                                        arguments.putInt("script_primaryKey", primaryKey);
                                        fragment_board.setArguments(arguments);
                                        FragmentTransaction transaction = manager.beginTransaction();
                                        transaction.replace(R.id.frame, fragment_board).commit();
                                    }
                                }
                            }, 2000);


                        }

                    }
                }

                @Override
                public void onFailure(Call<AddScript> call, Throwable t) {

                    Log.e("errror", String.valueOf(t));

                }
            });


        } else {

            Date c = Calendar.getInstance().getTime();
            System.out.println("Current time => " + c);

            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
            String formattedDate = df.format(c);

            scriptDB = new ScriptDB(input, scrTitle, formattedDate, "",
                    "35.5", "left", "", userid, script_text, 0, 1, "25", "", scrTextSize, telepromptTextSize);

            new SaveAsyncTask(scriptDB, Script_Toolbar.this).execute();

        }
    }

    public void saveData() {
        scrTitle = tv_title.getText().toString().trim();
        script_text = et_tv.getText().toString();

        if (Tag_value.equals("1")) {

//            if (flag == true) {

            //Activity is not calling for first time now

            if (tv_title.getText().toString().equals(titleeee)) {
                attribut = et_tv.getText().toString();
                try {

                } catch (NumberFormatException e) {
                    attribut_api = getColoredSpanned(attribut, Integer.parseInt(desc_color));
                }

                if (attribut_api != null) {
                    aaa = attribut_api;

                    try {
                        EditScriptapi(userid, addScript_id, story_board_id, scrTitle, script_text, aaa, scrTextSize, telepromptTextSize, "4", "10.0", "false", "140.0", "", desc_color, alignment_tag);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        if (desc_color != null) {
                            aaa = getColoredSpanned(attribut, Integer.parseInt(desc_color));
                        } else {
                            aaa = getColoredSpanned(attribut, Color.BLACK);
                        }

                    } catch (NumberFormatException e) {

                    }

                    try {
                        EditScriptapi(userid, addScript_id, story_board_id, scrTitle, script_text, aaa, scrTextSize, telepromptTextSize, "4", "10.0", "false", "140.0", "", desc_color, alignment_tag);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            } else {

                try {
//                            if (scrAttrText.equals("")){
//                                AddScriptapi(userid, scrTitle, "Start witing, copy and paste text from another app or..");
//                            }
//                            else {
                    AddScriptapi(userid, scrTitle, script_text, "new", "");
//                            }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

//            } else {
//                //first time calling activity
////                            flag=true;
//
//                AddScriptapi(userid, scrTitle, script_text,"");
//            }

        } else if (Tag_value.equals("2")) {

            attribut = et_tv.getText().toString();
            try {
                attribut_api = getColoredSpanned(attribut, Integer.parseInt(desc_color));

            } catch (NumberFormatException e) {

            }

            if (attribut_api != null) {
                aaa = attribut_api;

                try {
                    EditScriptapi(userid, script_id, story_board_id, scrTitle, script_text, aaa, scrTextSize, telepromptTextSize, "4", "10.0", "false", "140.0", "", desc_color, alignment_tag);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                try {
                    if (desc_color != null) {
                        aaa = getColoredSpanned(attribut, Integer.parseInt(desc_color));
                    } else {
                        aaa = getColoredSpanned(attribut, Color.BLACK);
                    }
                } catch (NumberFormatException e) {

                }


                try {
                    EditScriptapi(userid, script_id, story_board_id, scrTitle, script_text, aaa, scrTextSize, telepromptTextSize, "4", "10.0", "false", "140.0", "", desc_color, alignment_tag);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        } else {
            tv_import.setVisibility(View.GONE);
        }


    }

    @Override
    public void onBackPressed() {
        try {
            super.onBackPressed();
            return;
//
//            scrTitle = tv_title.getText().toString().trim();
//            script_text = et_tv.getText().toString();
//
//            if (scrTitle.equals("")) {
//
//                dialog = new Dialog(Script_Toolbar.this);
//
//                dialog.setContentView(R.layout.title);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                tv_ok = dialog.findViewById(R.id.tv_ok);
//
//                tv_ok.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        dialog.dismiss();
//
//                    }
//                });
//                dialog.show();
//            } else {
//                if (Tag_value.equals("1")) {
//
//                    if (flag == true) {
//
//                        //Activity is not calling for first time now
//
//                        if (tv_title.getText().toString().equals(titleeee)) {
//                            attribut = et_tv.getText().toString();
//                            try {
//                                attribut_api = getColoredSpanned(attribut, Integer.parseInt(desc_color));
//                            } catch (NumberFormatException e) {
//
//                            }
//
//                            if (attribut_api != null) {
//                                aaa = attribut_api;
//
//                                try {
//
//
//                                    if (alignment_tag != null) {
//                                        final_align = alignment_tag;
//                                        EditScriptapi(userid, addScript_id, story_board_id, scrTitle, script_text, aaa, String.valueOf(et_tv.getTextSize()), telepromptTextSize, "4", "10.0", "false", "140.0", "", desc_color, alignment_tag);
//
//                                    } else {
//                                        EditScriptapi(userid, addScript_id, story_board_id, scrTitle, script_text, aaa, String.valueOf(et_tv.getTextSize()), telepromptTextSize, "4", "10.0", "false", "140.0", "", desc_color, alignment_tag);
//
//                                    }
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//                            } else {
//                                try {
//                                    if (desc_color != null) {
//                                        aaa = getColoredSpanned(attribut, Integer.parseInt(desc_color));
//                                    } else {
//                                        aaa = getColoredSpanned(attribut, Color.BLACK);
//                                    }
//                                } catch (NumberFormatException e) {
//
//                                }
//
//                                try {
//                                    if (alignment_tag != null) {
//                                        final_align = alignment_tag;
//                                        EditScriptapi(userid, addScript_id, story_board_id, scrTitle, script_text, aaa, String.valueOf(et_tv.getTextSize()), telepromptTextSize, "4", "10.0", "false", "140.0", "", desc_color, alignment_tag);
//
//                                    } else {
//                                        EditScriptapi(userid, addScript_id, story_board_id, scrTitle, script_text, aaa, String.valueOf(et_tv.getTextSize()), telepromptTextSize, "4", "10.0", "false", "140.0", "", desc_color, alignment_tag);
//
//                                    }
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//                        } else {
////                                    AddScriptapi(userid, scrTitle, script_text,"");
//                        }
//
//                    } else {
//                        //first time calling activity
////                            flag=true;
//
////                                AddScriptapi(userid, scrTitle, script_text,"");
//                    }
//
//
//                }
//                else if (Tag_value.equals("2")) {
//
//
//                    attribut = et_tv.getText().toString();
//                    try {
//                        attribut_api = getColoredSpanned(attribut, Integer.parseInt(desc_color));
//
//                    } catch (NumberFormatException e) {
//
//                    }
//
//                    if (attribut_api != null) {
//                        Log.e("fontsizen ", desc_color.toString());
//                        aaa = attribut_api;
//
//                        try {
//                            if (alignment_tag != null) {
//                                final_align = alignment_tag;
//                                EditScriptapi(userid, script_id, story_board_id, scrTitle, script_text, aaa, String.valueOf(et_tv.getTextSize()), telepromptTextSize, "4", "10.0", "false", "140.0", "", desc_color, alignment_tag);
//
//                            } else {
//                                EditScriptapi(userid, script_id, story_board_id, scrTitle, script_text, aaa, String.valueOf(et_tv.getTextSize()), telepromptTextSize, "4", "10.0", "false", "140.0", "", desc_color, alignment_tag);
//
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    } else {
//                        Log.e("fontsize    =", desc_color.toString());
//                        try {
//                            if (desc_color != null) {
//                                aaa = getColoredSpanned(attribut, Integer.parseInt(desc_color));
//                            } else {
//                                aaa = getColoredSpanned(attribut, Color.BLACK);
//                            }
//                        } catch (NumberFormatException e) {
//
//                        }
//
//
//                        try {
//                            if (alignment_tag != null) {
//                                final_align = alignment_tag;
//                                EditScriptapi(userid, script_id, story_board_id, scrTitle, script_text, aaa, String.valueOf(et_tv.getTextSize()), telepromptTextSize, "4", "10.0", "false", "140.0", "", desc_color, alignment_tag);
//
//                            } else {
//                                EditScriptapi(userid, script_id, story_board_id, scrTitle, script_text, aaa, String.valueOf(et_tv.getTextSize()), telepromptTextSize, "4", "10.0", "false", "140.0", "", desc_color, alignment_tag);
//
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                } else {
//                    tv_import.setVisibility(View.GONE);
//
//                    desc_fist = intent.getStringExtra("descriptionn");
//                    desc_color = intent.getStringExtra("desc_color");
//                    telepromptTextSize = intent.getStringExtra(Global_Constants.PROMPT_TEXT_SIZE);
//                    alignment_tag = intent.getStringExtra("alignment");
//                    tv_import.setVisibility(View.GONE);
//                    tv_title.setText(title);
//                    et_tv.setText(desc_fist);
//                    et_tv.setTextColor(Integer.parseInt(desc_color));
//                    //  et_tv.setTextSize(Float.parseFloat(telepromptTextSize));
//                    if (alignment_tag.equals("left")) {
//                        et_tv.setGravity(Gravity.LEFT);
//                    } else if (alignment_tag.equals("right")) {
//                        et_tv.setGravity(Gravity.RIGHT);
//                    } else {
//                        et_tv.setGravity(Gravity.CENTER);
//                    }
//                }
//
//            }

        } catch (NullPointerException e) {
        }

    }

    public void visibleFontStyle(String name, int position) {

        rel_font_style_style.setVisibility(View.VISIBLE);
        rel_font_style_.setVisibility(View.GONE);

        fontListStyle.clear();
        for (String fontStyle : fontstyleList) {
            Log.e("Font", fontStyle);
            fontListStyle.add(fontStyle);
        }

        tv_fontName.setText(name);

        linearLayoutManagerFontStyle = new LinearLayoutManager(getApplicationContext());
        linearLayoutManagerFontStyle.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_font_style.setLayoutManager(linearLayoutManagerFontStyle);

        recent_font_style_adapter = new RecentFontStyle(Script_Toolbar.this, fontListStyle, mlistener, "recent", position);
        recycler_font_style.setAdapter(recent_font_style_adapter);
    }

    public void changecolor(String s) {

        Typeface custom_font = Typeface.createFromAsset(getApplicationContext().getAssets(), s);
        et_tv.setTypeface(custom_font);
    }

    public void opendialog(String name) {
        Dialog dialogg = new Dialog(Script_Toolbar.this);

        dialogg.setContentView(R.layout.email_empty);
        dialogg.setCanceledOnTouchOutside(false);
        dialogg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tv_scriptDelete = dialogg.findViewById(R.id.tv_path);
        TextView tv_okay = dialogg.findViewById(R.id.tv_ok);
        tv_scriptDelete.setText("Successfully imported file" + " " + name);

        tv_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogg.dismiss();
                Intent intent = new Intent(Script_Toolbar.this, Navigation.class);
                startActivity(intent);
            }
        });

        dialogg.show();
    }

    private class SaveAsyncTask extends AsyncTask<Void, Void, Long> {

        //Prevent leak
        Context context;
        ScriptDB scriptDB;

        public SaveAsyncTask(ScriptDB scriptDB, Context context) {
            this.scriptDB = scriptDB;
            this.context = context;
        }

        @Override
        protected Long doInBackground(Void... params) {
            ScriptDBDao scriptDBDao = db.scriptDBDao();
            return scriptDBDao.insertScript(scriptDB);
        }

        @Override
        protected void onPostExecute(Long integer) {

            Log.e("Check Success", integer.toString());

            if (integer > 0) {
                new GetLastPrimaryAsyncTask(this.context).execute();
            }
        }
    }


    public class GetLastPrimaryAsyncTask extends AsyncTask<Void, Void, ScriptDB> {

        //Prevent leak
        Context context;

        public GetLastPrimaryAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected ScriptDB doInBackground(Void... params) {
            ScriptDBDao scriptDBDao = db.scriptDBDao();
            return scriptDBDao.lastPrimaryKey();
        }

        @Override
        protected void onPostExecute(ScriptDB scriptCount) {

            Log.e("Check StoryBoard", scriptCount.toString());

            if (scriptCount != null) {

                primaryKey = scriptCount.getKey();

                Fragment_Board fragment_board = new Fragment_Board();

                for (int i = 0; i < 2; i++) {

                    if (i == 0) {
                        fragment_board.addStoryBoardapi(primaryKey, userid, scriptCount.get_id(), "Introduction", "new", db);
                    } else {
                        fragment_board.addStoryBoardapi(primaryKey, userid, scriptCount.get_id(), "Objective", "new", db);
                    }
                    StoryBoardDB storyBoardDB = new StoryBoardDB(script_id, "",
                            "Start writing here", "Title", "20", 1, 1, primaryKey);
                    Log.e("datasource", "limit1");
                    //  new Fragment_Board.SaveStoryAsyncTask(storyBoardDB, getApplicationContext(),db).execute();
                }
            }
        }
    }

    public class GetstoryfalseDataAsyncTask extends AsyncTask<Void, Void, List<StoryBoardDB>> {

        //Prevent leak
        Context context;
        String scriptId;
        List<Script_Pojo> scriptList = new ArrayList<>();
        Script_Pojo script_pojo;
        int key;

        public GetstoryfalseDataAsyncTask(int key, Context context, String scriptId) {
            this.context = context;
            this.scriptId = scriptId;
            this.key = key;
        }

        @Override
        protected List<StoryBoardDB> doInBackground(Void... params) {
            StoryBoardDao storyBoardDao = db.storyBoardDao();
            return storyBoardDao.getAll(key);
        }

        @Override
        protected void onPostExecute(List<StoryBoardDB> list) {
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getStoryBoardinternetFlag() == 0) {

                        new Fragment_Board().addStoryBoardapi(list.get(i).getScriptKey(), userid, scriptId, list.get(i).getStoryboard_name(), "local", db);

                        //   new Navigation.UpdateAsyncTask(key, context,scriptId).execute();
                    }
                }
            }
        }
    }

    public class EditSriptAsync extends AsyncTask<Void, Void, Integer> {

        Context context;
        String title;
        int key;
        String userid;
        String addScript_id;
        String story_board_id, scrText;
        String description, alignmentTag, decColor, scriptTextsize, promptTextSize, s2, s3, s4, s5, aFalse;
        int editValue;

//        EditScriptName(Context context, String title, int key)
//        {
//            this.context = context;
//            this.key = key;
//        }

        public EditSriptAsync(Context navigation, String scrTitle, int primarykey, String userid, String addScript_id, String story_board_id,
                              String scrText, String description, int editValue, String alignmentTag, String decColor, String scriptTextsize, String promptTextSize, String s2, String s3, String s4, String s5, String aFalse) {

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
            this.s2 = s2;
            this.s3 = s3;
            this.s4 = s4;
            this.s5 = s5;
            this.aFalse = aFalse;

        }

        @Override
        protected Integer doInBackground(Void... voids) {
            ScriptDBDao scriptDBDao = db.scriptDBDao();
            Log.e("scr text and prompt ", ""+String.valueOf(et_tv.getTextSize())+"-----"+promptTextSize);
            return scriptDBDao.updateScript(title, editValue, description, alignmentTag, decColor, scriptTextsize, promptTextSize, key);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            if (integer > 0) {
                Log.e("Success", "True"+integer);

                if (Util.isConnected()) {
                    Log.e("sizeDimensions Are: ", String.valueOf(et_tv.getTextSize())+" "+promptTextSize);

                    MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
                    JSONObject mySelection = new JSONObject();
                    try {
                        mySelection.put("user_id", userid);
                        mySelection.put("script_id", script_id);
                        mySelection.put("scrTitle", scrTitle);
                        mySelection.put("scrText", script_text);
                        mySelection.put("scrAttrText", description);
                        mySelection.put("scrEditTextSize", scriptTextsize);
                        mySelection.put("scrPromptTextSize", promptTextSize);
                        mySelection.put("scrPromptSpeed", s2);
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
                        if (desc_color != null) {
                            mySelection.put("scrColor", decColor);
                        } else {
                            mySelection.put("scrColor", "-16777216");
                        }
                        if (alignmentTag != null) {

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

                                    desc = response.body().getData().getScrAttrText().toString();
                                    title = response.body().getData().getScrTitle().toString();
                                    tv_title.setText(title);
                                    if (desc.equals(" ")) {
                                        et_tv.setHint("Start writing , copy and paste text from another app or..");
                                    } else {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                                        }

                                    }
                                    shared_prefrencePrompster.setTag("");

                                } else {


                                    SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(Script_Toolbar.this, SweetAlertDialog.ERROR_TYPE);

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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String fullerror = "";

        if (requestCode == FILE_SELECT_CODE) {
            if (resultCode == RESULT_OK) {
                try {

                    Uri imageuri = data.getData();
                    InputStream stream = null;
                    String tempID = "", id = "";
                    Uri uri = data.getData();
                    Log.e("TAG", "file auth is " + uri);
                    Log.e("TAG", "file auth is " + uri.getAuthority().toString());
                    if (imageuri.getAuthority().equals("media")) {
                        tempID = imageuri.toString();
                        tempID = tempID.substring(tempID.lastIndexOf("/") + 1);
                        id = tempID;
                        Uri contenturi = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        String selector = MediaStore.Images.Media._ID + "=?";
                        actualfilepath = getColunmData(contenturi, selector, new String[]{id});
                    } else if (imageuri.getAuthority().equals("com.android.providers.media.documents")) {
                        tempID = DocumentsContract.getDocumentId(imageuri);
                        String[] split = tempID.split(":");
                        String type = split[0];
                        id = split[1];
                        Uri contenturi = null;
                        if (type.equals("image")) {
                            contenturi = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        } else if (type.equals("video")) {
                            contenturi = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                        } else if (type.equals("audio")) {
                            contenturi = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                        }
                        String selector = "_id=?";
                        actualfilepath = getColunmData(contenturi, selector, new String[]{id});
                    } else if (imageuri.getAuthority().equals("com.android.providers.downloads.documents")) {
                        tempID = imageuri.toString();
                        tempID = tempID.substring(tempID.lastIndexOf("/") + 1);
                        id = tempID;
                        Uri contenturi = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                        // String selector = MediaStore.Images.Media._ID+"=?";
                        actualfilepath = getColunmData(contenturi, null, null);
                    } else if (imageuri.getAuthority().equals("com.android.externalstorage.documents")) {
                        tempID = DocumentsContract.getDocumentId(imageuri);
                        String[] split = tempID.split(":");
                        String type = split[0];
                        id = split[1];
                        Uri contenturi = null;
                        if (type.equals("primary")) {
                            actualfilepath = Environment.getExternalStorageDirectory() + "/" + id;
                        }
                    }
                    File myFile = new File(actualfilepath);

                    ReadPdfFile(actualfilepath);

                } catch (Exception e) {
                    Log.e("TAG", " read errro " + e.toString());
                }
                //------------  /document/primary:kolektap/30-05-2018_Admin_1527671367030_file.xls
            }
        }

    }

    public void ReadPdfFile(String actualfilepath) throws IOException {
        try {
            String parsedText = "";
            PdfReader reader = new PdfReader(actualfilepath);
            int n = reader.getNumberOfPages();
            for (int i = 0; i < n; i++) {
                parsedText = parsedText + PdfTextExtractor.getTextFromPage(reader, i + 1).trim() + "\n"; //Extracting the content from the different pages
            }
//            System.out.println(parsedText);
            Log.e("Parsed Text", parsedText);
//            textView.setText(parsedText);
            reader.close();

            Dialog dialogg = new Dialog(Script_Toolbar.this);

            dialogg.setContentView(R.layout.email_empty);
            dialogg.setCanceledOnTouchOutside(false);
            dialogg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            TextView tv_scriptDelete = dialogg.findViewById(R.id.tv_path);
            TextView tv_okay = dialogg.findViewById(R.id.tv_ok);
            tv_scriptDelete.setText("Successfully imported files.");

            tv_okay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogg.dismiss();
                }
            });

            dialogg.show();

            String[] parts = new File(actualfilepath).getName().split("\\."); // escape .
            String part1 = parts[0];
            String part2 = parts[1];

            Log.e("part1", part1);

            Script_Toolbar script_toolbar = new Script_Toolbar();
            script_toolbar.AddScriptapi(userid, part1, parsedText, "new", "");


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String getColunmData(Uri uri, String selection, String[] selectarg) {
        String filepath = "";
        Cursor cursor = null;
        String colunm = "_data";
        String[] projection = {colunm};
        cursor = getContentResolver().query(uri, projection, selection, selectarg, null);
        if (cursor != null) {
            cursor.moveToFirst();
            Log.e("TAG", " file path is " + cursor.getString(cursor.getColumnIndex(colunm)));
            filepath = cursor.getString(cursor.getColumnIndex(colunm));
        }
        if (cursor != null)
            cursor.close();
        return filepath;
    }
}



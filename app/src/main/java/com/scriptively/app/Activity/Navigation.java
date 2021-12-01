package com.scriptively.app.Activity;

import android.Manifest;
import android.app.Dialog;
import android.app.Person;
import android.app.ProgressDialog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.MotionEventCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.scriptively.app.Adapter.Demo;
import com.scriptively.app.Adapter.MenuAdapter;
import com.scriptively.app.Adapter.ScriptsInFolder;
import com.scriptively.app.Adapter.Search_Adpapter;
import com.scriptively.app.Adapter.SpecificFolderAdapter;
import com.scriptively.app.Adapter.StoryBoard_Adapter;
import com.scriptively.app.Api.ApiClient;
import com.scriptively.app.Api.ApiInterface;
import com.scriptively.app.Database.AppDatabase;
import com.scriptively.app.Database.ScriptDBDao;
import com.scriptively.app.Database.StoryBoardDao;
import com.scriptively.app.DatabaseModel.FolderDB;
import com.scriptively.app.DatabaseModel.FolderDataDb;
import com.scriptively.app.DatabaseModel.ScriptDB;
import com.scriptively.app.DatabaseModel.StoryBoardDB;
import com.scriptively.app.Interface.Recycler_item_click;
import com.scriptively.app.Pojo.CheckSub;
import com.scriptively.app.Pojo.EditScript_Pojo;
import com.scriptively.app.Pojo.MenuPojo;
import com.scriptively.app.Pojo.MovePojo;
import com.scriptively.app.Pojo.Script;
import com.scriptively.app.Pojo.Script_Pojo;
import com.scriptively.app.Pojo.Search_Pojo;
import com.scriptively.app.Pojo.ShowStoryBoard_Datum;
import com.scriptively.app.Pojo.SingleFolderDetailDatum;
import com.scriptively.app.Pojo.SingleFolderDetailPojo;
import com.scriptively.app.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.scriptively.app.Utils.FileUtils;
import com.scriptively.app.Utils.Global_Constants;
import com.scriptively.app.Utils.Shared_PrefrencePrompster;
import com.scriptively.app.Utils.SwipeHelper;
import com.scriptively.app.Utils.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;

import java.util.Comparator;
import java.util.List;

import Swipe.RecyclerTouchListener;
import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.scriptively.app.Adapter.Search_Adpapter.list_script;


public class Navigation extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = Navigation.class.getSimpleName();
    private RecyclerTouchListener touchListener;

    static AppDatabase db;
    ScriptDBDao scriptDBDao;
    ScriptDB scriptDB;
    FolderDB folderDB;
    StoryBoardDao storyBoardDao;
    FolderDataDb folderDataDb;
    ProgressBar progressbar;
    List<ScriptDB> listScriptLocal;

    private AppBarConfiguration mAppBarConfiguration;
    public DrawerLayout mDrawerLayout;
    private int request_code = 1, FILE_SELECT_CODE = 101;
    RelativeLayout drawerView;
    RelativeLayout mainView, rel_search, rel_main_navigation, rel_navigation;
    NavigationView navigationView;
    Search_Adpapter myAdapter;
    StoryBoard_Adapter storyBoard_adapter;
    SpecificFolderAdapter myAdapterfolder;
    ScriptsInFolder scriptsInFolder;
    Search_Pojo pojo;
    RecyclerView rv_data, rv_menu;
    Dialog show;
    List<Search_Pojo> srch = new ArrayList<>();
    int position;
    TextView tv_setting, tv_okay, tv_cancel, tv_Move, tv_scriptDelete, tv_title1, tv_title2;
    Shared_PrefrencePrompster shared_prefrencePrompster;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    String userid, title, desc, script_id, foldername, storyboard_id, scriptTextSize, desc_color, createdAt, et_txtSize, prompt_txtSize,
            scrText, txtColor, welcome_desc, alignment, folder_id, relation_id;
    EditText folder_name;
    List<List<Script_Pojo>> all_script = new ArrayList<List<com.scriptively.app.Pojo.Script_Pojo>>();
    List<List<SingleFolderDetailPojo>> folder_script_data = new ArrayList<>();
    List<com.scriptively.app.Pojo.ScriptUnderFolderDatum> ScriptUnderFolderDatum = new ArrayList<>();
    List<Script_Pojo> script_pojo1 = new ArrayList<>();
    List<SingleFolderDetailDatum> folder_data = new ArrayList<>();
    String url = "http://www.adobe.com/devnet/acrobat/pdfs/pdf_open_parameters.pdf";
    public static List<Script_Pojo> script_pojo2 = new ArrayList<>();

    private Recycler_item_click mlistener;

    private List<Person> persons;
    List<Script_Pojo> scriptList = new ArrayList<>();
    List<Script_Pojo> scriptList_withoutKey = new ArrayList<>();
    FloatingActionButton fab;
    public MenuAdapter menuAdapter;
    MenuPojo menuPojo;
    TextView tv_files;
    List<MenuPojo> pojos = new ArrayList<>();
    List<String> deleteList = new ArrayList<>();

    Dialog dialog;
    TextView tv_ok, tvMessage;
    ImageView iv_importinactive, iv_importactive, iv_search;
    TextView tv_import, tv_dropbox, tv_googledrive, tv_cancell;

    public TextView tv_select, tv_select_all, tv_move, tv_done;
    public boolean check_move = false;
    ImageView inactive, iv_cancel;
    ImageView iv_menu;
    String titlecompare;
    EditText et_search;
    CoordinatorLayout coordinatorLayout;
    TextView tv_cancel_drawer;
    Intent intent;
    String scrTitle_foldertitle, Tag_value = "0", folderid, OpenParticularFolder = null;
    TextView tv_allscript;
    RecyclerView rv_folder;
    ProgressDialog dialogg;
    public String actualfilepath = "";

    ACProgressFlower dialog_progress;
    RelativeLayout simpleProgressBarr;
    SwipeRefreshLayout swipeRefreshLayout;
    private static final int CREATE_FILE = 1;
    private static final int REQUEST_CODE_SIGN_IN = 100;
    private boolean canUserClick = true;
    private int idforWelcomeScript = -1;
    private boolean isWelcomeScript = false;
    private String promptTextspeed;
    static boolean active = false;


    @Override
    protected void onResume() {
        super.onResume();
        active = true;
        if (Tag_value != null && Tag_value.equals("1")) {
//            rv_data.setVisibility(View.GONE);
//            rv_folder.setVisibility(View.VISIBLE);
//            tv_import.setVisibility(View.GONE);
            tv_select.setVisibility(View.GONE);

            if (scrTitle_foldertitle.length() > 10)
                tv_allscript.setText(scrTitle_foldertitle.substring(0, 15) + "....");
            else {
                tv_allscript.setText(scrTitle_foldertitle);
            }

            ShowscriptUnderFolderApi(userid, folderid);

        } else {

            checkSub();

        }
        //     loadRecyclerViewData();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        shared_prefrencePrompster = Shared_PrefrencePrompster.getInstance(this);
        prefs = getSharedPreferences("dropbox-sample", MODE_PRIVATE);
        editor = prefs.edit();
        userid = shared_prefrencePrompster.getUserid().toString();
        intent = new Intent();
        intent = getIntent();
        Tag_value = intent.getStringExtra("TAG");
        OpenParticularFolder = intent.getStringExtra("OpenParticularFolder");
        scrTitle_foldertitle = intent.getStringExtra("scrTitle_foldertitle");
        folderid = intent.getStringExtra("FolderId");
        Toolbar toolbar = findViewById(R.id.toolbar);
        iv_menu = findViewById(R.id.iv_menu);
        tv_cancel_drawer = findViewById(R.id.tv_cancel_drawer);
        rel_main_navigation = findViewById(R.id.rel_main_navigation);
        rel_navigation = findViewById(R.id.rel_navigation);
        progressbar = findViewById(R.id.progressBar);
        db = AppDatabase.getInstance(this);
        scriptDBDao = db.scriptDBDao();
        storyBoardDao = db.storyBoardDao();


//        toolbar.setNavigationIcon(R.drawable.menu_icon);
//        setSupportActionBar(toolbar);

        inactive = findViewById(R.id.inactive);
        tv_select = findViewById(R.id.tv_select);
        tv_select_all = findViewById(R.id.tv_select_all);
        et_search = findViewById(R.id.et_search);
        iv_cancel = findViewById(R.id.iv_cancel);
        tv_allscript = findViewById(R.id.tv_allscript);
        tv_move = findViewById(R.id.tv_move);
        tv_done = findViewById(R.id.tv_done);
        simpleProgressBarr = findViewById(R.id.simpleProgressBarr);
        swipeRefreshLayout = findViewById(R.id.swipe_container);

//        swipeRefreshLayout.setOnRefreshListener(this);


        FloatingActionButton fab = findViewById(R.id.fab);


        final LinearLayoutManager llm = new LinearLayoutManager(this);
//        rv.setLayoutManager(llm);
//        rv.setHasFixedSize(true);


        rv_data = (RecyclerView) findViewById(R.id.rv_data);
        rv_menu = (RecyclerView) findViewById(R.id.rv_menu);
        rv_folder = (RecyclerView) findViewById(R.id.rv_folder);


        rv_menu.setLayoutManager(new LinearLayoutManager(this));
        rv_menu.setHasFixedSize(true);
        //     requestPermission();

        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_search.setText("");
            }
        });

//        allscript_api(userid);

        //SWIPE........................
        SwipeHelper swipeHelper = new SwipeHelper(this) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {

                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Rename",
                        0,
                        Color.parseColor("#C7C7CB"),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(final int pos) {

                                if (OpenParticularFolder == null || OpenParticularFolder.equals("")) {
                                    show = new Dialog(Navigation.this);

                                    show.setContentView(R.layout.add_folder);
                                    show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                    tv_okay = show.findViewById(R.id.tv_okay);
                                    tv_title1 = show.findViewById(R.id.tv_title1);
                                    tv_title2 = show.findViewById(R.id.tv_title2);
                                    tv_cancel = show.findViewById(R.id.tv_cancel);
                                    folder_name = show.findViewById(R.id.et_folder_name);
                                    folder_name.setText(all_script.get(0).get(pos).getScrTitle().toString());
                                    tv_title1.setText("Scriptively!");
                                    tv_title2.setVisibility(View.VISIBLE);
                                    tv_title2.setText("Please enter new name of script");

                                    tv_cancel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            show.dismiss();
                                            myAdapter.notifyDataSetChanged();
                                        }
                                    });

                                    tv_okay.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            show.dismiss();

                                            String addScript_id, story_board_id, scrTitle, script_text, aaa = "";
                                            addScript_id = all_script.get(0).get(pos).getId().toString();
                                            scrTitle = folder_name.getText().toString();
                                            script_text = all_script.get(0).get(pos).getScrText().toString();
                                            try {
                                                aaa = all_script.get(0).get(pos).getScrAttrText().toString();
                                            } catch (Exception e) {

                                            }
                                            desc_color = all_script.get(0).get(pos).getScrColor().toString();
                                            createdAt = all_script.get(0).get(pos).getCreatedAt().toString();
                                            Log.e("prompttextspeed", all_script.get(0).get(pos).getScrPromptSpeed().toString());
                                            promptTextspeed = all_script.get(0).get(pos).getScrPromptSpeed();
//                                            try {
//                                                et_txtSize = all_script.get(0).get(pos).getScrEditTextSize().toString();
//                                            }
//                                            catch (Exception e){
//
//                                            }
                                            prompt_txtSize = all_script.get(0).get(pos).getScrPromptTextSize().toString();
                                            scrText = all_script.get(0).get(pos).getScrText().toString();


                                            story_board_id = null;

                                            myAdapter.notifyDataSetChanged();


                                            EditScriptapi(userid, addScript_id, story_board_id, scrTitle, script_text, aaa, "20", "28", "4", "10.0", "false", "140.0", "", desc_color, all_script.get(0).get(pos).getPrimarykey());


                                        }
                                    });

                                    show.show();


//                                Toast.makeText(getApplicationContext(), "You clicked like on item position " + pos, Toast.LENGTH_LONG).show();
                                } else {
                                    show = new Dialog(Navigation.this);

                                    show.setContentView(R.layout.add_folder);
                                    show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                    tv_okay = show.findViewById(R.id.tv_okay);
                                    tv_title1 = show.findViewById(R.id.tv_title1);
                                    tv_title2 = show.findViewById(R.id.tv_title2);
                                    tv_cancel = show.findViewById(R.id.tv_cancel);
                                    folder_name = show.findViewById(R.id.et_folder_name);
                                    folder_name.setText(folder_data.get(pos).getScrTitle().toString());
                                    tv_title1.setText("Scriptively!");
                                    tv_title2.setVisibility(View.VISIBLE);
                                    tv_title2.setText("Please enter new name of script");

                                    tv_cancel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            show.dismiss();
                                            myAdapterfolder.notifyDataSetChanged();
                                        }
                                    });

                                    tv_okay.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {


                                            show.dismiss();

                                            String addScript_id, story_board_id, scrTitle, script_text, aaa;
                                            addScript_id = folder_data.get(pos).getId().toString();
                                            scrTitle = folder_name.getText().toString();
                                            script_text = folder_data.get(pos).getScrText().toString();
                                            aaa = folder_data.get(pos).getScrAttrText().toString();
                                            desc_color = folder_data.get(pos).getScrColor().toString();


                                            story_board_id = null;

                                            EditScriptapi(userid, addScript_id, story_board_id, scrTitle, script_text, aaa, "20", "28", "4", "10.0", "false", "140.0", "", desc_color, pos);
                                            myAdapterfolder.notifyDataSetChanged();

                                        }
                                    });

                                    show.show();


//                                Toast.makeText(getApplicationContext(), "You clicked like on item position " + pos, Toast.LENGTH_LONG).show();
                                }


                            }
                        }
                ));

                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Delete",
                        0,
                        Color.parseColor("#FF3A30"),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(final int pos) {
                                if (OpenParticularFolder == null || OpenParticularFolder.equals("")) {
                                    script_id = all_script.get(0).get(pos).getId();

                                    dialog = new Dialog(Navigation.this);

                                    dialog.setCanceledOnTouchOutside(false);
                                    dialog.setCanceledOnTouchOutside(false);
                                    dialog.setContentView(R.layout.delete_or_not);
                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                    tv_scriptDelete = dialog.findViewById(R.id.tv_scriptDelete);
                                    tv_okay = dialog.findViewById(R.id.tv_okay);
                                    tv_cancell = dialog.findViewById(R.id.tv_cancell);
                                    tv_scriptDelete.setText("Are you sure you want to delete this script permanently?");

                                    tv_okay.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();

//
                                            myAdapter.notifyItemRemoved(pos);
                                            new DeleteAsyncTask(all_script.get(0).get(pos).getPrimarykey(), Navigation.this, pos, script_id, userid).execute();
                                            all_script.get(0).remove(pos);

                                            Runnable progressRunnable = new Runnable() {

                                                @Override
                                                public void run() {

                                                    try {


                                                    } catch (IndexOutOfBoundsException e) {

                                                    }
                                                }
                                            };

                                            Handler pdCanceller = new Handler();
                                            pdCanceller.postDelayed(progressRunnable, 3000);


                                        }
                                    });

                                    tv_cancell.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                            myAdapter.notifyDataSetChanged();
                                        }
                                    });
                                    dialog.show();
                                } else {
                                    folder_id = folder_data.get(pos).getId();
                                    relation_id = folder_data.get(pos).getRelationId();

                                    dialog = new Dialog(Navigation.this);

                                    dialog.setCanceledOnTouchOutside(false);
                                    dialog.setContentView(R.layout.delete_or_not);
                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                    tv_scriptDelete = dialog.findViewById(R.id.tv_scriptDelete);
                                    tv_okay = dialog.findViewById(R.id.tv_okay);
                                    tv_cancell = dialog.findViewById(R.id.tv_cancell);
                                    tv_scriptDelete.setText("Are you sure you want to delete this script permanently?");

                                    tv_okay.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();

                                            folder_data.remove(pos);
                                            myAdapterfolder.notifyItemRemoved(pos);
                                            delete_data_script_folder(userid, folder_id, relation_id, pos);

                                            Runnable progressRunnable = new Runnable() {

                                                @Override
                                                public void run() {

                                                    try {


                                                    } catch (IndexOutOfBoundsException e) {

                                                    }
                                                }
                                            };

                                            Handler pdCanceller = new Handler();
                                            pdCanceller.postDelayed(progressRunnable, 3000);


                                        }
                                    });

                                    tv_cancell.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                            myAdapterfolder.notifyDataSetChanged();
                                        }
                                    });
                                    dialog.show();
                                }

                            }
                        }
                ));

            }
        };
        swipeHelper.attachToRecyclerView(rv_data);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                swipeRefreshLayout.setRefreshing(true);

                // Fetching data from server
                loadRecyclerViewData();
            }
        });
//        swipeRefreshLayout.post(new Runnable() {
//
//            @Override
//            public void run() {
//
//            }
//        });
        menuPojo = new MenuPojo("Akshay");
        pojos.add(menuPojo);

        mlistener = new Recycler_item_click() {
            @Override
            public void onClick(View view, int position, String type) {
                if (!canUserClick) {
                    return;
                }

                if (Tag_value != null && Tag_value.equals("1")) {
                    tv_select_all.setVisibility(View.GONE);
                    tv_move.setVisibility(View.GONE);
                    iv_menu.setVisibility(View.VISIBLE);
                    tv_cancel_drawer.setVisibility(View.GONE);
                    tv_select.setVisibility(View.GONE);
                    Script_Pojo script_pojoss = new Script_Pojo();
                    script_pojoss.setScrTitle(folder_data.get(position).getScrTitle());
                    try {
                        boolean r = false;
                        for (int i = 0; i < script_pojo2.size(); i++) {
                            if (script_pojoss.getScrTitle().equals(script_pojo2.get(i).getScrTitle())) {
                                r = true;
                                script_pojo2.remove(i);
                            }
                        }

                        if (script_pojo2.size() >= 3) {
                            if (!r) {
                                script_pojo2.remove(2);
                            }
                            script_pojo2.add(script_pojoss);
                        } else {
                            script_pojo2.add(script_pojoss);
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    title = folder_data.get(position).getScrTitle().toString();
                    desc = folder_data.get(position).getScrAttrText().toString();
                    Log.e("desc_Fom Navigation:-..", desc);
                    welcome_desc = folder_data.get(position).getScrText().toString();
                    script_id = folder_data.get(position).getId().toString();
                    try {
                        alignment = folder_data.get(position).getScrAlignment().toString();
                    } catch (NullPointerException e) {

                    }
                    try {
                        desc_color = folder_data.get(position).getScrColor().toString();
                    } catch (Exception e) {
                        desc_color = "-16777216";
                    }
                    scriptTextSize = folder_data.get(position).getScrEditTextSize().toString();
                    try {
                        storyboard_id = folder_data.get(position).getId().toString();
                    } catch (IndexOutOfBoundsException e) {

                    }
                    Intent intent = new Intent(Navigation.this, Script_Toolbar.class);
                    intent.putExtra("title", title);
                    try {
                        intent.putExtra("alignment", alignment);
                    } catch (NullPointerException e) {

                    }
                    if (title.contains("Welcome-Start here")) {
                        intent.putExtra("description", desc);
                    } else {
                        intent.putExtra("description", desc);
                    }
                    Log.e("board scriptid", "" + script_id);
                    intent.putExtra("script_id", script_id);
                    intent.putExtra("desc_color", desc_color);
                    intent.putExtra("script_primaryKey", "");
                    intent.putExtra(Global_Constants.PROMPT_SPEED, promptTextspeed);
                    intent.putExtra(Global_Constants.SCRIPT_TEXT_SIZE, scriptTextSize);
                    try {
                        intent.putExtra("storyboard_id", storyboard_id);
                    } catch (IndexOutOfBoundsException e) {

                    }

                    intent.putExtra("TAG", "2");
                    intent.putExtra("position", String.valueOf(position));


                    shared_prefrencePrompster.setTag("2");

                    startActivityForResult(intent, 147);

                } else {
                    if (!type.equals("select")) {


                        tv_select_all.setVisibility(View.GONE);
                        tv_move.setVisibility(View.GONE);
                        iv_menu.setVisibility(View.VISIBLE);
                        tv_cancel_drawer.setVisibility(View.GONE);
                        tv_select.setVisibility(View.VISIBLE);
                        script_pojo1 = all_script.get(0);
                        Log.e("dataIsss", "" + script_pojo1.size());
                        for (int i = 0; i < script_pojo1.size(); i++) {
                            Log.e("dataIsss", script_pojo1.get(i).getId().toString());
                        }
                        Script_Pojo script_pojoss = new Script_Pojo();
                        script_pojoss.setScrTitle(all_script.get(0).get(position).getScrTitle());
                        try {
                            boolean r = false;
                            for (int i = 0; i < script_pojo2.size(); i++) {
                                if (script_pojoss.getScrTitle().equals(script_pojo2.get(i).getScrTitle())) {
                                    r = true;
                                    script_pojo2.remove(i);
                                }
                            }

                            if (script_pojo2.size() >= 3) {
                                if (!r) {
                                    script_pojo2.remove(2);
                                }
                                script_pojo2.add(script_pojoss);
                            } else {
                                script_pojo2.add(script_pojoss);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        title = script_pojo1.get(position).getScrTitle().toString();
                        try {
                            desc = script_pojo1.get(position).getScrText().toString();
                            Log.e("desc_Fom Navigation:-..", desc);
                        } catch (Exception e) {

                        }
                        welcome_desc = script_pojo1.get(position).getScrText().toString();
                        Log.e("dataIsss", "" + script_pojo1.get(position).getId());
                        script_id = script_pojo1.get(position).getId();

                        try {
                            alignment = script_pojo1.get(position).getScrAlignment().toString();
                        } catch (NullPointerException e) {

                        }
                        try {
                            desc_color = script_pojo1.get(position).getScrColor().toString();

                            if (desc_color == null || desc_color.equals("")) {
                                desc_color = "-16777216";
                            }
                        } catch (Exception e) {
                            desc_color = "-16777216";
                        }
                        try {
                            scriptTextSize = script_pojo1.get(position).getScrEditTextSize().toString();
                            Log.e("prompttextspeed2", script_pojo1.get(position).getScrPromptSpeed().toString());
                            promptTextspeed = script_pojo1.get(position).getScrPromptSpeed().toString();
                        } catch (Exception e) {

                        }
                        try {
                            storyboard_id = script_pojo1.get(position).getId().toString();
                        } catch (IndexOutOfBoundsException e) {

                        }

                        Intent intent = new Intent(Navigation.this, Script_Toolbar.class);
                        intent.putExtra("title", title);
                        try {
                            intent.putExtra("alignment", alignment);
                        } catch (NullPointerException e) {

                        }
                        if (title.contains("Welcome-Start here")) {
                            intent.putExtra("description", desc);
                        } else {
                            intent.putExtra("description", desc);
                        }
                        Log.e("primaryKey", "" + all_script.get(0).get(position).getPrimarykey());
                        intent.putExtra("script_id", script_id);
                        intent.putExtra("desc_color", desc_color);
                        intent.putExtra(Global_Constants.PROMPT_SPEED, promptTextspeed);
                        intent.putExtra(Global_Constants.SCRIPT_TEXT_SIZE, scriptTextSize);
                        intent.putExtra(Global_Constants.PROMPT_TEXT_SIZE, script_pojo1.get(position).getScrPromptTextSize());
                        intent.putExtra("script_primaryKey", all_script.get(0).get(position).getPrimarykey());
                        shared_prefrencePrompster.setLastScriptId(script_id);
                        try {

                            intent.putExtra("storyboard_id", storyboard_id);
                        } catch (IndexOutOfBoundsException e) {

                        }

                        intent.putExtra("TAG", "2");
                        intent.putExtra("position", String.valueOf(position));

                        shared_prefrencePrompster.setTag("2");

                        startActivityForResult(intent, 147);
                    } else if (type.equals("folder")) {


                        tv_select_all.setVisibility(View.GONE);
                        tv_move.setVisibility(View.GONE);
                        iv_menu.setVisibility(View.VISIBLE);
                        tv_cancel_drawer.setVisibility(View.GONE);
                        tv_select.setVisibility(View.GONE);
                        SingleFolderDetailDatum script_pojoss = new SingleFolderDetailDatum();
                        script_pojoss.setScrTitle(folder_data.get(position).getScrTitle());
                        try {
                            boolean r = false;
                            for (int i = 0; i < folder_data.size(); i++) {
                                if (script_pojoss.getScrTitle().equals(folder_data.get(i).getScrTitle())) {
                                    r = true;
                                    folder_data.remove(i);
                                }
                            }

                            if (folder_data.size() >= 3) {
                                if (!r) {
                                    folder_data.remove(2);
                                }
                                folder_data.add(script_pojoss);
                            } else {
                                folder_data.add(script_pojoss);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        title = folder_data.get(position).getScrTitle();
                        desc = folder_data.get(position).getScrAttrText();
                        Log.e("desc_Fom Navigation:-..", desc);
                        welcome_desc = folder_data.get(position).getScrText();
                        script_id = folder_data.get(position).getId().toString();
                        try {
                            alignment = folder_data.get(position).getScrAlignment();
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                            e.printStackTrace();
                        }
                        try {
                            desc_color = folder_data.get(position).getScrColor().toString();
                        } catch (Exception e) {
                            desc_color = "-16777216";
                        }
                        scriptTextSize = folder_data.get(position).getScrEditTextSize().toString();
                        try {
                            storyboard_id = folder_data.get(position).getId().toString();
                        } catch (IndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }

                        Intent intent = new Intent(Navigation.this, Script_Toolbar.class);
                        intent.putExtra("title", title);
                        try {
                            intent.putExtra("alignment", alignment);
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                        if (title.contains("Welcome-Start here")) {
                            intent.putExtra("description", desc);
                        } else {
                            intent.putExtra("description", desc);
                        }
                        intent.putExtra("script_primaryKey", "");
                        intent.putExtra("script_id", script_id);
                        intent.putExtra("desc_color", desc_color);
                        intent.putExtra(Global_Constants.PROMPT_SPEED, promptTextspeed);
                        intent.putExtra(Global_Constants.SCRIPT_TEXT_SIZE, scriptTextSize);
                        try {
                            intent.putExtra("storyboard_id", storyboard_id);
                        } catch (IndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }

                        intent.putExtra("TAG", "2");
                        intent.putExtra("position", String.valueOf(position));

                        shared_prefrencePrompster.setTag("2");

                        startActivityForResult(intent, 147);
                    }

                }
//                finish();
            }

            @Override
            public void onClicFonts(View view, int position) {

            }
        };

//        tv_Move.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDrawerLayout.openDrawer(GravityCompat.START);
//            }
//        });

        tv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list_script.clear();
                Demo.list.clear();
                tv_select_all.setVisibility(View.VISIBLE);
                tv_select.setVisibility(View.GONE);
                iv_menu.setVisibility(View.GONE);
                tv_cancel_drawer.setVisibility(View.VISIBLE);
                myAdapter = new Search_Adpapter(Navigation.this, all_script.get(0), mlistener, true, false);
                rv_data.setAdapter(myAdapter);
//                inactive.setVisibility(View.VISIBLE);
            }
        });

        tv_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_move = true;
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                check_move = false;

                for (int i = 0; i < Demo.list.size(); i++) {

                    for (int j = 0; j < list_script.size(); j++) {

                        moveScriptUnderFolderApi(userid, list_script.get(j).toString(), Demo.list.get(i).toString());
                    }
                }


            }
        });


        tv_cancel_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_select_all.setVisibility(View.GONE);
                tv_move.setVisibility(View.GONE);
                tv_done.setVisibility(View.GONE);
                iv_menu.setVisibility(View.VISIBLE);
                tv_cancel_drawer.setVisibility(View.GONE);
                tv_select.setVisibility(View.VISIBLE);
                myAdapter = new Search_Adpapter(Navigation.this, all_script.get(0), mlistener, false, false);
                rv_data.setAdapter(myAdapter);
//inactive.setVisibility(View.VISIBLE);
            }
        });
        tv_select_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_select_all.setVisibility(View.GONE);
                tv_move.setVisibility(View.VISIBLE);
                tv_select.setVisibility(View.GONE);
                iv_menu.setVisibility(View.GONE);
                tv_cancel_drawer.setVisibility(View.VISIBLE);
                tv_select.setVisibility(View.GONE);
                myAdapter = new Search_Adpapter(Navigation.this, all_script.get(0), mlistener, true, true);
                rv_data.setAdapter(myAdapter);

                for (int i = 0; i < all_script.get(0).size(); i++) {
                    list_script.add(all_script.get(0).get(i).getId());
                }

//inactive.setVisibility(View.VISIBLE);
            }
        });


        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    iv_cancel.setVisibility(View.GONE);
                } else {
                    iv_cancel.setVisibility(View.VISIBLE);
                }
                if (myAdapter != null) {
                    if (Tag_value != null && Tag_value.equals("1")) {
                        myAdapterfolder.filterFolder(s.toString());
                    } else {
                        myAdapter.filter(s.toString());
                    }
                }

            }
        });

//        tv_setting = findViewById(R.id.tv_setting);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            rv_data.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                    myAdapter.closeMenu();
                }
            });
        }


        iv_importinactive = findViewById(R.id.iv_importinactive);

        iv_importactive = findViewById(R.id.iv_importactive);


        iv_importinactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_importactive.setVisibility(View.VISIBLE);

//                Toast.makeText(Script_Toolbar.this, "hellooo", Toast.LENGTH_SHORT).show();
                show = new Dialog(Navigation.this);


                show.setContentView(R.layout.importactive_design);
                show.setCanceledOnTouchOutside(false);
                show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                tv_dropbox = show.findViewById(R.id.tv_dropbox);
                tv_files = show.findViewById(R.id.tv_files);
                tv_googledrive = show.findViewById(R.id.tv_googledrive);
                tv_cancell = show.findViewById(R.id.tv_cancell);

                tv_files.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            // start runtime permission
                            Boolean hasPermission = (ContextCompat.checkSelfPermission(Navigation.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                                    == PackageManager.PERMISSION_GRANTED);
                            if (!hasPermission) {
                                Log.e("TAG", "get permision   ");
                                ActivityCompat.requestPermissions(Navigation.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, request_code);
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


//
//                        if (Util.checkDropbox) {
//                            Intent intentt = new Intent(Navigation.this, DropBox.class);
//                            intentt.putExtra("drive", "2");
//                           Log.e("clientdata", DropboxClientFactory.getClient().toString());
//                            startActivity(intentt);
//                        } else {
//                            DropboxActivity.startOAuth2Authentication(Navigation.this, getString(R.string.app_key), Arrays.asList("account_info.read", "files.content.write", "files.content.read"));
//                            Util.checkDropbox = true;
////                            startActivity(new Intent(Navigation.this, DropBox.class));
//                        }

                        //    DropboxActivity.startOAuth2Authentication(Navigation.this, getString(R.string.app_key), Arrays.asList("account_info.read", "files.content.write", "files.content.read"));

                        startActivity(new Intent(Navigation.this, DropBox.class));

//
//                        Intent intentt = new Intent(Navigation.this, DropBox.class);
//                            intentt.putExtra("drive", "2");
//                           Log.e("clientdata", DropboxClientFactory.getClient().toString());
//                            startActivity(intentt);
                        //    DropboxActivity.startOAuth2Authentication(Navigation.this, getString(R.string.app_key), Arrays.asList("account_info.read", "files.content.write"));
//
//                        if(value) {
//                           startActivity(FilesActivity.getIntent(Navigation.this, ""));
//                       }

                        show.dismiss();

                    }
                });


                tv_cancell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        iv_importactive.setVisibility(View.GONE);
                        show.dismiss();
                    }
                });


                tv_googledrive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        show.dismiss();
                        Intent intent = new Intent(Navigation.this, GoogleDriveFiles.class);
                        intent.putExtra("drive", "1");
                        startActivity(intent);
//                        finish();

                    }
                });

//                TextView tv_yes=dialog.findViewById(R.id.tv_yes);
//                TextView tv_cancel=dialog.findViewById(R.id.tv_cancel);

                show.show();

            }

        });


//        allscript_api(userid);


        //Adapter............................................
//        show_folderapi();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mDrawerLayout = findViewById(R.id.drawer_layout);

        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        rel_main_navigation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = MotionEventCompat.getActionMasked(event);

                switch (action) {

                    case (MotionEvent.ACTION_OUTSIDE):
                        Log.d("DEBUG_TAG", "Movement occurred outside bounds " +
                                "of current screen element");
                        mDrawerLayout.openDrawer(GravityCompat.START);
                        return true;
                    default:
                        return false;
                }
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);


        toggle.setDrawerIndicatorEnabled(false);
        mDrawerLayout.addDrawerListener(toggle);
//        toggle.setHomeAsUpIndicator(R.drawable.menu);

        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        //   drawer.setDrawerListener(toggle);  Deprecated
        toggle.syncState();
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView = findViewById(R.id.nav_view);

//have commented permisson popup which opens directly when user
        //  ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);


    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
        } else {
            // openFilePicker();
        }
    }

    private void delete_data_script_folder(String userid, String folder_id, String relation_id, int pos) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        retrofit2.Call<ResponseBody> call = apiService.DeleteRelation(Integer.parseInt(userid), Integer.parseInt(folder_id), Integer.parseInt(relation_id));
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.e("ScriptUnderFolder url", new Gson().toJson(response.body()));

                if (response.isSuccessful()) {

                    if (OpenParticularFolder.equals("") || OpenParticularFolder == null) {
                        myAdapter.notifyDataSetChanged();
                    } else {
                        myAdapterfolder.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Log.e("errror", String.valueOf(t));

            }
        });

    }

    private void showFileChooser() {

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        intent.setType("*/*");
        String[] mimetypes = {"text/plain", "application/pdf"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_CODE);
        } catch (Exception e) {
            Log.e("TAG", " choose file error " + e.toString());
        }

    }

    private void moveScriptUnderFolderApi(String userid, String script_id, String move_folderid) {

//            new GetFolderscriptDataAsyncTask(Navigation.this, useridd).execute();

        dialog_progress = new ACProgressFlower.Builder(this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Please wait...")
                .fadeColor(Color.DKGRAY).build();
//        dialog_progress.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        retrofit2.Call<MovePojo> call = apiService.addScript_Folder(userid, script_id, move_folderid);
        Log.e("MoveScrip", call.request().toString());
        call.enqueue(new retrofit2.Callback<MovePojo>() {
            @Override
            public void onResponse(Call<MovePojo> call, Response<MovePojo> response) {
                dialog_progress.dismiss();
                Log.e("moveScriptUnderFolderAp", call.request().toString());

                if (response.isSuccessful()) {
                    dialog_progress.dismiss();

                    Log.w("moveScriptUnderFolderAp", new Gson().toJson(response));
                    if (response.body().getSuccess().toString().equals("1")) {
                        Log.e("moveScriptUnderFolderAp", new Gson().toJson(response));

                        Toast.makeText(Navigation.this, "Moved successfully", Toast.LENGTH_SHORT).show();
                        dialog_progress.dismiss();
                        tv_done.setVisibility(View.GONE);
                        tv_select_all.setVisibility(View.GONE);
                        tv_move.setVisibility(View.GONE);
                        tv_select.setVisibility(View.VISIBLE);
                        iv_menu.setVisibility(View.VISIBLE);
                        tv_cancel_drawer.setVisibility(View.GONE);
                        myAdapter = new Search_Adpapter(Navigation.this, all_script.get(0), mlistener, false, false);
                        rv_data.setAdapter(myAdapter);
                        invalidateOptionsMenu();

//                        myAdapter.notifyDataSetChanged();

                    } else {
                        dialog_progress.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovePojo> call, Throwable t) {
                dialog_progress.dismiss();
                Log.e("errror", String.valueOf(t));
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Util.flagFirst = true;
            super.onBackPressed();
        }
    }

//    private void show_folderapi() {
//        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//
//
//        retrofit2.Call<Show_Folder_pojo> call = apiService.show_folder(userid);
//        call.enqueue(new retrofit2.Callback<Show_Folder_pojo>() {
//            @Override
//            public void onResponse(Call<Show_Folder_pojo> call, Response<Show_Folder_pojo> response) {
//
//                Log.e("All Script url", call.request().toString());
//
//                if (response.isSuccessful()) {
//
//                    Log.w("Add Script response", new Gson().toJson(response));
//                    if (response.body().getSuccess().toString().equals("1")) {
//                        Log.e("Add Script response", new Gson().toJson(response));
//
////                        Toast.makeText(Navigation.this, "hiiiiiiii", Toast.LENGTH_SHORT).show();
//
//
//                    } else {
//
//
//                        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(Navigation.this, SweetAlertDialog.ERROR_TYPE);
//
//                        sweetAlertDialog.setTitleText(response.body().getMessage().toString());
//
//                        sweetAlertDialog.show();
//                        Button btn = (Button) sweetAlertDialog.findViewById(R.id.confirm_button);
//                        btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
//
//                    }
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Show_Folder_pojo> call, Throwable t) {
//
//                Log.e("errror", String.valueOf(t));
//
//
//            }
//        });
//
//    }

    public void allscript_api(String useridd) {

        scriptList.clear();
        all_script.clear();

        new GetscriptDataAsyncTask(Navigation.this, useridd).execute();

    }

    private void ShowscriptUnderFolderApi(String userid, String folderid) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        retrofit2.Call<SingleFolderDetailPojo> call = apiService.showSingleFolderData(userid, folderid);
        call.enqueue(new retrofit2.Callback<SingleFolderDetailPojo>() {
            @Override
            public void onResponse(Call<SingleFolderDetailPojo> call, Response<SingleFolderDetailPojo> response) {

                Log.e("ShowscriptUnderFolder", call.request().toString());

                if (response.isSuccessful()) {

                    Log.w("ShowscriptUnderFolder", new Gson().toJson(response));
                    if (response.body().getSuccess().toString().equals("1")) {
                        Log.e("ShowscriptUnderFolder", new Gson().toJson(response.body()));

                        swipeRefreshLayout.setRefreshing(false);

//                        dialog_progressress.dismiss();
//                        Toast.makeText(Navigation.this, "hiiiiiiii", Toast.LENGTH_SHORT).show();
                        folder_data.clear();
                        folder_data.addAll(response.body().getData());

                        Collections.sort(folder_data, new Comparator<SingleFolderDetailDatum>() {
                            @Override
                            public int compare(SingleFolderDetailDatum text1, SingleFolderDetailDatum text2) {
                                return text1.getScrTitle().compareToIgnoreCase(text2.getScrTitle());
                            }
                        });

                        menuAdapter = new MenuAdapter(Navigation.this, pojos);
                        rv_menu.setAdapter(menuAdapter);

                        rv_data.setVisibility(View.VISIBLE);
                        rv_data.setLayoutManager(new LinearLayoutManager(Navigation.this));
                        myAdapterfolder = new SpecificFolderAdapter(Navigation.this, folder_data, mlistener);
                        rv_data.setAdapter(myAdapterfolder);

                    } else {

                    }

                }
            }

            @Override
            public void onFailure(Call<SingleFolderDetailPojo> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
//                dialog_progress.dismiss();
                Log.e("errror", String.valueOf(t));


            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = androidx.navigation.Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    private void EditScriptapi(final String userid, String addScript_id, String story_board_id, final String scrTitle, String script_text, String aaa, final String s, String s1, String s2, String s3, String aFalse, String s4, String s5, String desc_color, final int primaryKey) {


        if (Util.isConnected()) {

            new EditScriptName(Navigation.this, scrTitle, primaryKey, userid, addScript_id, story_board_id, script_text, aaa, s, s1, s2, s3, aFalse, s4, s5, desc_color, primaryKey, 1).execute();

        } else {

            new EditScriptName(Navigation.this, scrTitle, primaryKey, userid, addScript_id, story_board_id, script_text, aaa, s, s1, s2, s3, aFalse, s4, s5, desc_color, primaryKey, 0).execute();

        }


    }

    private void delete_data_script(String userid, String script_id) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        retrofit2.Call<ResponseBody> call = apiService.DeleteScript(userid, script_id);
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.e("ScriptUnderFolder url", new Gson().toJson(response.body()));

                if (response.isSuccessful()) {

                    try {

                        if (OpenParticularFolder.equals("") || OpenParticularFolder == null) {
                            myAdapter.notifyDataSetChanged();
                        } else {
                            myAdapterfolder.notifyDataSetChanged();
                        }

                    } catch (Exception e) {

                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Log.e("errror", String.valueOf(t));

            }
        });

    }

    public void changeValue() {
        tv_move.setVisibility(View.VISIBLE);
        tv_select_all.setVisibility(View.GONE);
    }


    public void closeDrawer() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            onBackPressed();
        }
    }

    @Override
    public void onRefresh() {
        loadRecyclerViewData();
    }

    private void loadRecyclerViewData() {


        if (Tag_value != null && Tag_value.equals("1")) {
            ShowscriptUnderFolderApi(userid, folderid);
        } else {
            allscript_api(userid);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }, 2000);
        }

    }

    public String getPDFPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Log.e(TAG, " result is "+ data + "  uri  "+ data.getData()+ " auth "+ data.getData().getAuthority()+ " path "+ data.getData().getPath());
        String fullerror = "";
        if (requestCode == FILE_SELECT_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    Uri documentUri = data.getData();
                    InputStream stream = null;
                    String tempID = "", id = "";
                    Uri uri = data.getData();
                    Uri contenturi = null;
                    String fileName = null;
                    fullerror = fullerror + "file auth is " + uri.getAuthority();
                    if (documentUri.getAuthority().equals("media")) {
                        tempID = documentUri.toString();
                        tempID = tempID.substring(tempID.lastIndexOf("/") + 1);
                        id = tempID;
                        contenturi = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        String selector = MediaStore.Images.Media._ID + "=?";
                        actualfilepath = getColunmData(contenturi, selector, new String[]{id});
                        fileName = new File(actualfilepath).getName();
                    } else if (documentUri.getAuthority().equals("com.android.providers.media.documents")) {
//                        tempID = DocumentsContract.getDocumentId(documentUri);
//                        String[] split = tempID.split(":");
//                        String type = split[0];
//                        id = split[1];
//                        contenturi = null;

//                         if(type.equals("document"))
//
//                        {
//
//                            contenturi=   documentUri;
//                          //  contenturi = MediaStore.Files.Media.EXTERNAL_CONTENT_URI;
//                        }
//                        String selector = "_id=?";
//                        actualfilepath = getColunmData(contenturi, selector, new String[]{id});
                        tempID = documentUri.toString();
                        tempID = tempID.substring(tempID.lastIndexOf("/") + 1);
                        id = tempID;
                        contenturi = documentUri;
                        //        Uri contenturi = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                        File originalFile = new File(FileUtils.getRealPath(this, documentUri));
                        // String selector = MediaStore.Images.Media._ID+"=?";
                        actualfilepath = originalFile.getPath();
                        fileName = new File(actualfilepath).getName();
                    } else if (documentUri.getAuthority().equals("com.android.providers.downloads.documents")) {
                        tempID = documentUri.toString();
                        tempID = tempID.substring(tempID.lastIndexOf("/") + 1);
                        id = tempID;

                        contenturi = documentUri;
                        //        Uri contenturi = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                        File originalFile = new File(FileUtils.getRealPath(this, documentUri));
                        // String selector = MediaStore.Images.Media._ID+"=?";

                        if (documentUri.getPath().contains("msf")) {
                            actualfilepath = originalFile.getPath();
                            fileName = new File(actualfilepath).getName();
                            if (actualfilepath.contains("txt")) {
                                actualfilepath = getImagePathFromURI(this, contenturi);
                            } else {
                                actualfilepath = originalFile.getPath();
                            }


                        } else {
                            actualfilepath = originalFile.getPath();
                            fileName = new File(actualfilepath).getName();
                        }


                    } else if (documentUri.getAuthority().equals("com.android.externalstorage.documents")) {
                        tempID = DocumentsContract.getDocumentId(documentUri);
                        String[] split = tempID.split(":");
                        String type = split[0];
                        id = split[1];
                        contenturi = null;
                        if (type.equals("primary")) {
                            actualfilepath = Environment.getExternalStorageDirectory() + "/" + id;
                        }
                        fileName = new File(actualfilepath).getName();
                    }
                    File myFile = new File(actualfilepath);
                    // MessageDialog dialog = new MessageDialog(Home.this, " file details --"+actualfilepath+"\n---"+ uri.getPath() );
                    // dialog.displayMessageShow();
                    String temppath = uri.getPath();
                    if (temppath.contains("//")) {
                        temppath = temppath.substring(temppath.indexOf("//") + 1);
                    }
                    Log.e(TAG, " temppath is " + temppath);
                    fullerror = fullerror + "\n" + " file details -  " + actualfilepath + "\n --" + uri.getPath() + "\n--" + temppath;
//                    if (actualfilepath.equals("") || actualfilepath.equals(" ")) {
//                        myFile = new File(temppath);
//                    } else {
//                        myFile = new File(actualfilepath);
//                    }
                    //File file = new File(actualfilepath);
                    //Log.e(TAG, " actual file path is "+ actualfilepath + "  name ---"+ file.getName());
//                    File myFile = new File(actualfilepath);
                    Log.e(TAG, " myfile is " + myFile.getAbsolutePath());


//                    StringBuilder builder = new StringBuilder();
//                    String parsedText = "";
//
//                    PdfReader reader = null;
//                    try {
//                        reader = new PdfReader(getBytes(getContentResolver().openInputStream(contenturi)));
//                    } catch (IOException e) {
//                        Log.e("exception >>",e.getMessage());
//                        e.printStackTrace();
//                    }
//                    int n = reader.getNumberOfPages();
//                    for (int i = 0; i < n; i++) {
//                        try {
//                            parsedText = parsedText + PdfTextExtractor.getTextFromPage(reader, i + 1).trim() + "\n"; //Extracting the content from the different pages
//                        } catch (IOException e) {
//                            Log.e("exception >",e.getMessage());
//                            e.printStackTrace();
//                        }
//                    }
//                    builder.append(parsedText);
////            System.out.println(parsedText);
//                    Log.e("Parsed Text", parsedText);
//                    Script_Toolbar script_toolbar = new Script_Toolbar();
//                    try {
//                        script_toolbar.AddScriptapi(userid, myFile.getName().replaceFirst("[.][^.]+$", ""), builder.toString(), "new", "");
//
//                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                //Do something after 100ms
//                                allscript_api(userid);
//                            }
//                        }, 2000);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }

                    //========================================== byte[] ========================================================//

                    readfile(myFile, contenturi, fileName);
                    return;


                    // lyf path  - /storage/emulated/0/kolektap/04-06-2018_Admin_1528088466207_file.xls
                } catch (Exception e) {
                    Log.e(TAG, " read errro " + e.toString());
                }
                //------------  /document/primary:kolektap/30-05-2018_Admin_1527671367030_file.xls
            }
        }

    }

    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    private void pdfFileReader(File myFile) {
    }


    public static String getImagePathFromURI(Context context, Uri uri) {
        final File file = new File(context.getCacheDir(), uri.getLastPathSegment());
        try (final InputStream inputStream = context.getContentResolver().openInputStream(uri);
             OutputStream output = new FileOutputStream(file)) {
            // You may need to change buffer size. I use large buffer size to help loading large file , but be ware of
            //  OutOfMemory Exception
            final byte[] buffer = new byte[8 * 1024];
            int read;

            while ((read = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, read);
            }

            output.flush();
            return file.getPath();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String getColunmData(Uri uri, String selection, String[] selectarg) {
        String filepath = "";
        Cursor cursor = null;
        String colunm = "_data";
        String[] projection = {colunm};
        cursor = getContentResolver().query(uri, projection, selection, selectarg, null);
        if (cursor != null) {
            cursor.moveToFirst();

            Log.e(TAG, " file path is " + cursor.getString(cursor.getColumnIndex(colunm)));
            filepath = cursor.getString(cursor.getColumnIndex(colunm));
        }
        if (cursor != null)
            cursor.close();
        return filepath;
    }


    public String getColunmData1(Uri uri, String selection, String[] selectarg) {

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

            Dialog dialogg = new Dialog(Navigation.this);

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


    public void ReadTextFile(File file) throws IOException {


        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        } catch (IOException e) {


            Log.e("textviewfile", e.toString());
        }
        String result = text.toString();

        Log.e("textviewfile", result.toString());

        return;
//        String string = "";
//        StringBuilder stringBuilder = new StringBuilder();
//        BufferedReader reader = new BufferedReader(new FileReader(file));
//        while (true) {
//            try {
//                if ((string = reader.readLine()) == null) break;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            stringBuilder.append(string).append("\n");
//          //  textView.setText(stringBuilder);
//            Log.e("textviewfile",stringBuilder.toString());
//        }
//
//        Toast.makeText(getBaseContext(), stringBuilder.toString(),
//                Toast.LENGTH_LONG).show();
    }
//    public String loadFile(InputStream inputStream){
//        ByteArrayOutputStream b = new ByteArrayOutputStream();
//        byte[] bytes = new byte[4096];
//        int length = 0;
//        while(){
//            b.write(bytes, 0, length);
//        }
//        return new String(b.toByteArray(), "UTF8");
//    }

    public void readfile(File file, Uri contenturi, String fileName) {
        progressbar.setVisibility(View.VISIBLE);
        Log.e("filepath", file.getPath());

        // File file = new File(Environment.getExternalStorageDirectory(), "mytextfile.txt");


//        InputStream inputStream = assetManager.open(extPath + "file.doc");
//        String text = loadFile(inputStream);
        StringBuilder builder = new StringBuilder();
        if (file.getAbsolutePath().contains("pdf")) {
//            String parsedText = "";
//
//            PdfReader reader = null;
//            try {
//                reader = new PdfReader(file.getAbsolutePath());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            int n = reader.getNumberOfPages();
//            for (int i = 0; i < n; i++) {
//                try {
//                    parsedText = parsedText + PdfTextExtractor.getTextFromPage(reader, i + 1).trim() + "\n"; //Extracting the content from the different pages
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            builder.append(parsedText);
////            System.out.println(parsedText);
//            Log.e("Parsed Text", parsedText);
////            textView.setText(parsedText);
//            reader.close();
            String parsedText = "";

            PdfReader reader = null;
            try {
                reader = new PdfReader(getBytes(getContentResolver().openInputStream(contenturi)));
            } catch (IOException e) {
                Log.e("exception >>", e.getMessage());
                e.printStackTrace();
            }
            int n = reader.getNumberOfPages();
            for (int i = 0; i < n; i++) {
                try {
                    parsedText = parsedText + PdfTextExtractor.getTextFromPage(reader, i + 1).trim() + "\n"; //Extracting the content from the different pages
                } catch (IOException e) {
                    Log.e("exception >", e.getMessage());
                    e.printStackTrace();
                }
            }
            builder.append(parsedText);
//            System.out.println(parsedText);
            Log.e("Parsed Text", parsedText);
            Log.e("Parsed Texts", parsedText);

        } else {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    builder.append(line);
                    builder.append("\n");
                }
                br.close();
            } catch (Exception e) {
                Log.e("main", " error is " + e.toString());
            }
        }

        Script_Toolbar script_toolbar = new Script_Toolbar();
        try {
            script_toolbar.AddScriptapi(userid, fileName.replaceFirst("[.][^.]+$", ""), builder.toString(), "new", "");

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms
                    progressbar.setVisibility(View.GONE);
                    allscript_api(userid);
                }
            }, 7000);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private static class SaveAsyncTask extends AsyncTask<Void, Void, Long> {

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
        }
    }


    public class SaveStoryAsyncTask extends AsyncTask<Void, Void, List<Long>> {

        //Prevent leak
        Context context;
        StoryBoardDB storyBoardDB;
        List<Script_Pojo> script_pojos;
        List<ScriptDB> script_pojos_withkey;
        List<StoryBoardDB> storyBoardDBS;


        public SaveStoryAsyncTask(List<Script_Pojo> script_pojos, Context context) {
            this.script_pojos = script_pojos;
            this.context = context;
        }


        @Override
        protected List<Long> doInBackground(Void... params) {

            ScriptDBDao scriptDBDao = db.scriptDBDao();
            script_pojos_withkey = scriptDBDao.loadAddData();
            StoryBoardDB storyBoardDB = null;
            List<StoryBoardDB> storyBoardDBS = new ArrayList<>();
            ShowStoryBoard_Datum showStoryBoard_datum = new ShowStoryBoard_Datum();
            if (script_pojos_withkey.size() > 0) {
                Util.flagFirst = true;

                for (int i = 0; i < script_pojos_withkey.size(); i++) {

                    if (script_pojos.size() > 0) {

                        if (script_pojos.get(i).getStoryboard().size() > 0) {
                            idforWelcomeScript = script_pojos_withkey.get(i).getKey();
                            for (int j = 0; j < script_pojos.get(i).getStoryboard().size(); j++) {

                                Log.e("ScriptKey", "" + script_pojos_withkey.get(i).getKey());
                                showStoryBoard_datum = script_pojos.get(i).getStoryboard().get(j);
                                storyBoardDB = new StoryBoardDB(showStoryBoard_datum.getScriptId(), showStoryBoard_datum.getId(),
                                        showStoryBoard_datum.getStoryboardText(), showStoryBoard_datum.getStoryboardName(),
                                        "20", 1, 1, script_pojos_withkey.get(i).getKey());

                                storyBoardDBS.add(storyBoardDB);
                            }
                        }
                    }
                }
            }

            StoryBoardDao storyBoardDao = db.storyBoardDao();
            return storyBoardDao.insertAllStoryBoard(storyBoardDBS);
        }


        @Override
        protected void onPostExecute(List<Long> scriptCount) {
            rv_data.setLayoutManager(new LinearLayoutManager(Navigation.this));
            myAdapter = new Search_Adpapter(Navigation.this, all_script.get(0), mlistener, false, false);
            rv_data.setAdapter(myAdapter);


            if (Util.flagFirst) {

                for (int i = 0; i < all_script.get(0).size(); i++) {
                    if (all_script.get(0).get(i).scrTitle.toLowerCase().equals("welcome - start here")) {
                        position = i;
                        isWelcomeScript = true;
                        break;
                    }
                }
                script_pojo1.clear();
                script_pojo1 = all_script.get(0);
                openScript();
                Util.flagFirst = false;
            } else {
                Log.i("Util flag", "" + Util.flagFirst);
            }

//            Log.e("Check StoryBoard", scriptCount.toString());
//
//            if (scriptCount.get(0)>0){
//
//
//
//            }
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Void, Void, Integer> {
        ScriptDB scriptDB;
        Context context;
        int key;
        int positi;
        String script_id;
        String userid;

        DeleteAsyncTask(int key, Context context, int pos, String script_id, String userid) {

            this.scriptDB = scriptDB;
            this.key = key;
            this.positi = pos;
            this.context = context;
            this.script_id = script_id;
            this.userid = userid;

        }

        @Override
        protected Integer doInBackground(Void... voids) {
            ScriptDBDao agentDao = db.scriptDBDao();
            return agentDao.delete(key);
        }

        @Override
        protected void onPostExecute(Integer integer) {

            Log.e("Check", integer + "");

            if (integer > 0) {
                Toast.makeText(context, "Delete Successfully", Toast.LENGTH_SHORT).show();

                if (Util.isConnected()) {

                    delete_data_script(userid, script_id);


                } else {
                    deleteList.clear();
                    deleteList.add(script_id);
                }


//                allscript_api(userid);
            }
// super.onPostExecute(integer);
        }
    }

    private class GetscriptDataAsyncTask extends AsyncTask<Void, Void, List<ScriptDB>> {

        //Prevent leak
        Context context;
//        String scriptId;

        Script_Pojo script_pojo;
        String useridd;

        public GetscriptDataAsyncTask(Context context, String useridd) {
            this.context = context;
            this.useridd = useridd;
//            this.scriptId = scriptId;
        }

        @Override
        protected List<ScriptDB> doInBackground(Void... params) {
            ScriptDBDao scriptDBDao = db.scriptDBDao();
            Log.e("datalist", "" + scriptDBDao.loadAddData().size());
            for (int i = 0; i < scriptDBDao.loadAddData().size(); i++) {
                Log.e("datalistitems ", "" + scriptDBDao.loadAddData().get(i).getKey());
            }
            return scriptDBDao.loadAddData();
        }

        @Override
        protected void onPostExecute(List<ScriptDB> list) {

            scriptList.clear();


            for (int i = 0; i < list.size(); i++) {
//                id = list.get(0).uid;
                script_pojo = new Script_Pojo();

                script_pojo.setId(list.get(i).get_id());
                script_pojo.setUserId(list.get(i).getScriptuser_Id());
                script_pojo.setCreatedAt(list.get(i).getScriptcreatedAt());
                script_pojo.setScrAlignment(list.get(i).getScriptAlignment());
                script_pojo.setScrAttrText(list.get(i).getScriptDescription());
                script_pojo.setScrTitle(list.get(i).getScripttitle());
                script_pojo.setScrEditTextSize(list.get(i).getScriptTextSize());
                script_pojo.setScrColor(list.get(i).getScriptColor());
                script_pojo.setScrText(list.get(i).getScriptDescription());
                script_pojo.setPrimarykey(list.get(i).getKey());
                script_pojo.setScrPromptTextSize(list.get(i).getPrmoptTextSize());
                script_pojo.setScrPromptSpeed(list.get(i).getPromptSpeed());

                scriptList.add(script_pojo);

            }

            all_script.clear();

            all_script.add(scriptList);
            Log.e("allscriptsize ", "" + all_script.size());


            if (all_script.get(0).size() > 0) {
                Collections.sort(all_script.get(0), new Comparator<Script_Pojo>() {
                    @Override
                    public int compare(Script_Pojo text1, Script_Pojo text2) {
                        return text1.getScrTitle().compareToIgnoreCase(text2.getScrTitle());
                    }
                });
            }
            if (all_script.get(0).size() > 0) {
                menuAdapter = new MenuAdapter(Navigation.this, pojos);
                rv_menu.setAdapter(menuAdapter);
                rv_data.setLayoutManager(new LinearLayoutManager(Navigation.this));
                myAdapter = new Search_Adpapter(Navigation.this, all_script.get(0), mlistener, false, false);
                rv_data.setAdapter(myAdapter);


                //Add local data on server
                if (Util.isConnected()) {
                    new GetscriptfalseDataAsyncTask(Navigation.this).execute();
                    menuAdapter.callFalseMethod();
                    menuAdapter.callFalseEditFagMethod();
                    new ListEditFlagAsyncTask(Navigation.this).execute();
                    for (int i = 0; i < deleteList.size(); i++) {
                        delete_data_script(userid, deleteList.get(i));
                    }
                    deleteList.clear();

                    for (int i = 0; i < MenuAdapter.deleteListFolder.size(); i++) {
                        menuAdapter.delete_data_Folder(userid, MenuAdapter.deleteListFolder.get(i));
                    }
                    MenuAdapter.deleteListFolder.clear();

                    storyBoard_adapter = new StoryBoard_Adapter();
                    for (int i = 0; i < Util.deleteListStory.size(); i++) {
                        storyBoard_adapter.deleteStoryBoardapi(i, userid, Util.deleteListStory.get(i).getScriptId(), Util.deleteListStory.get(i).getStoryboardId());
                    }
                    Util.deleteListStory.clear();
                }

                if (Util.flagFirst) {

                    for (int i = 0; i < all_script.get(0).size(); i++) {
                        Log.e("firsttime", "inisdie loop" + all_script.get(0).get(i).scrTitle);
//                        if (all_script.get(0).get(i).scrTitle.toLowerCase().equals("welcome - start here")){
//                            Log.e("firsttime","inisdie loop"+script_pojo1.size());
//
//                        }
                        script_pojo1.clear();
                        script_pojo1 = all_script.get(0);
                        Log.e("firsttime", "inisdie loop" + script_pojo1.size());
                        position = i;
                        break;
                    }
                    openScript();
                    Util.flagFirst = false;
                }

            } else {

                // get First Time data
                dialog_progress = new ACProgressFlower.Builder(context)
                        .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                        .themeColor(Color.WHITE)
                        .text("Please wait")
                        .fadeColor(Color.BLACK).build();
//        dialog_progress.show();

                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                retrofit2.Call<Script> call = apiService.all_script(useridd, false, false);
                call.enqueue(new retrofit2.Callback<Script>() {
                    @Override
                    public void onResponse(Call<Script> call, Response<Script> response) {

                        Log.e("all_script url", call.request().toString());

                        if (response.isSuccessful()) {

                            Log.w("all_script response", new Gson().toJson(response));
                            if (response.body().getSuccess().toString().equals("1")) {
                                Log.e("all_script response", new Gson().toJson(response.body()));
                                dialog_progress.dismiss();
//                        Toast.makeText(Navigation.this, "hiiiiiiii", Toast.LENGTH_SHORT).show();
                                all_script.clear();
                                all_script.addAll(response.body().getData());

                                if (all_script.size() != 0) {
                                    Collections.sort(all_script.get(0), new Comparator<Script_Pojo>() {
                                        @Override
                                        public int compare(Script_Pojo text1, Script_Pojo text2) {
                                            return text1.getScrTitle().compareToIgnoreCase(text2.getScrTitle());
                                        }
                                    });
                                }
                                menuAdapter = new MenuAdapter(Navigation.this, pojos);
                                rv_menu.setAdapter(menuAdapter);

                                StoryBoardDB storyBoardDB;
                                ShowStoryBoard_Datum showStoryBoard_datum = new ShowStoryBoard_Datum();

                                try {

                                    for (int i = 0; i < all_script.get(0).size(); i++) {
                                        Log.e("sizeofspeed", all_script.get(0).get(i).getScrPromptSpeed());

                                        //has been changed for welcome script new line issue
                                        scriptDB = new ScriptDB(all_script.get(0).get(i).getScrText(), all_script.get(0).get(i).getScrTitle(), all_script.get(0).get(i).getCreatedAt(), all_script.get(0).get(i).getId(),
                                                all_script.get(0).get(i).getScrEditTextSize(), all_script.get(0).get(i).getScrAlignment(), all_script.get(0).get(i).getScrColor(), all_script.get(0).get(i).getUserId(), all_script.get(0).get(i).getScrText(), 1, 1, all_script.get(0).get(i).getScrPromptTextSize(), all_script.get(0).get(i).getScrText(), all_script.get(0).get(i).getScrEditTextSize(), all_script.get(0).get(i).getScrPromptTextSize(), all_script.get(0).get(i).getScrPromptSpeed());

                                        new SaveAsyncTask(scriptDB, Navigation.this).execute();

                                    }

//                                    rv_data.setLayoutManager(new LinearLayoutManager(Navigation.this));
//                                    myAdapter = new Search_Adpapter(Navigation.this, all_script.get(0), mlistener, false, false);
//                                    rv_data.setAdapter(myAdapter);


//
                                    new SaveStoryAsyncTask(all_script.get(0), Navigation.this).execute();
//
//                                    if (Util.flagFirst) {
//
//                                        for (int i = 0;i<all_script.get(0).size();i++)
//                                        {
//                                            if (all_script.get(0).get(i).scrTitle.toLowerCase().equals("welcome - start here")){
//                                                position = i;
//                                                break;
//                                            }
//                                        }
//                                        script_pojo1.clear();
//                                        script_pojo1 = all_script.get(0);
////                                        openScript();
//
//                                    }


                                } catch (Exception e) {

                                }
                            } else {

//                        dialog_progress.dismiss();
                                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(Navigation.this, SweetAlertDialog.ERROR_TYPE);

                                sweetAlertDialog.setTitleText(response.body().getMessage().toString());
                                sweetAlertDialog.show();
                                Button btn = (Button) sweetAlertDialog.findViewById(R.id.confirm_button);
                                btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));

                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<Script> call, Throwable t) {
                        dialog_progress.dismiss();
                        Log.e("errror", String.valueOf(t));


                    }
                });


//                all_script.get(0)

//                    Toast.makeText(getApplicationContext(), "Internet Connected", Toast.LENGTH_SHORT).show();

            }
            //      progressbar.setVisibility(View.GONE);

        }
    }

    private class GetscriptfalseDataAsyncTask extends AsyncTask<Void, Void, List<ScriptDB>> {

        //Prevent leak
        Context context;
        String scriptId;
        List<Script_Pojo> scriptList = new ArrayList<>();
        Script_Pojo script_pojo;

        public GetscriptfalseDataAsyncTask(Context context) {
            this.context = context;
            this.scriptId = scriptId;
        }

        @Override
        protected List<ScriptDB> doInBackground(Void... params) {
            ScriptDBDao scriptDBDao = db.scriptDBDao();
            return scriptDBDao.internetBased();
        }

        @Override
        protected void onPostExecute(List<ScriptDB> list) {

            Log.e("ListResponse", new Gson().toJson(list));
            for (int i = 0; i < list.size(); i++) {
                Script_Toolbar script_toolbar = new Script_Toolbar();
                try {

                    script_toolbar.AddScriptapi(userid, list.get(i).getScripttitle(), list.get(i).getScriptDescription(), "local", String.valueOf(list.get(i).getKey()));


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

//            Collections.sort(scriptList, new Comparator<Script_Pojo>() {
//                @Override
//                public int compare(Script_Pojo text1, Script_Pojo text2) {
//                    return text1.getScrTitle().compareToIgnoreCase(text2.getScrTitle());
//                }
//            });

//            for (int i=0;i<list.size();i++) {
//
//
//
//            }
        }
    }

    private class EditScriptName extends AsyncTask<Void, Void, Integer> {

        Context context;
        String title;
        int key;
        String userid;
        String addScript_id;
        String story_board_id;
        String script_text;
        String aaa;
        String s;
        String s1;
        String s2;
        String s3;
        String s4;
        String s5;
        String aFalse;
        String desc_color;
        int pos;
        int editValue;

//        EditScriptName(Context context, String title, int key)
//        {
//            this.context = context;
//            this.key = key;
//        }

        public EditScriptName(Context navigation, String scrTitle, int primarykey, String userid, String addScript_id, String story_board_id,
                              String script_text, String aaa, String s, String s1, String s2, String s3, String aFalse, String s4, String s5, String desc_color, int pos, int editValue) {

            this.context = navigation;
            this.key = primarykey;
            this.title = scrTitle;
            this.userid = userid;
            this.addScript_id = addScript_id;
            this.story_board_id = story_board_id;
            this.script_text = script_text;
            this.aaa = aaa;
            this.s = s;
            this.s1 = s1;
            this.s2 = s2;
            this.s3 = s3;
            this.s4 = s4;
            this.s5 = s5;
            this.aFalse = aFalse;
            this.desc_color = desc_color;
            this.pos = pos;
            this.editValue = editValue;

        }

        @Override
        protected Integer doInBackground(Void... voids) {
            ScriptDBDao scriptDBDao = db.scriptDBDao();
            return scriptDBDao.updateTitle(title, editValue, key);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            if (integer > 0) {
                Log.e("Success", "True");

                if (Util.isConnected()) {
//                    simpleProgressBarr.setVisibility(View.VISIBLE);

                    MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
                    JSONObject mySelection = new JSONObject();
                    try {
                        mySelection.put("user_id", userid);
                        mySelection.put("script_id", addScript_id);
                        mySelection.put("scrTitle", title);
                        mySelection.put("scrText", script_text);
                        mySelection.put("scrAttrText", aaa);
                        mySelection.put("scrEditTextSize", s);
                        mySelection.put("scrPromptTextSize", s1);
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
                        mySelection.put("scrColor", desc_color);
                        mySelection.put("scrAlignment", alignment);
                        mySelection.put("scrMirror", false);
                        mySelection.put("scrInvert", false);
                        Log.e("myselection", String.valueOf(mySelection));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    RequestBody body = RequestBody.create(mediaType, String.valueOf(mySelection));

                    ApiInterface apiService =
                            ApiClient.getClient().create(ApiInterface.class);

                    Call<EditScript_Pojo> call = apiService.edit_script(body);


//        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//        retrofit2.Call<EditScript_Pojo> call = apiService.edit_script(userid, addScript_id, story_board_id, scrTitle, script_text, aaa, s, s1, s2, s3, aFalse, s4, s5, "false", "false", "false", "false", "false", desc_color, "fss");
                    call.enqueue(new retrofit2.Callback<EditScript_Pojo>() {
                        @Override
                        public void onResponse(Call<EditScript_Pojo> call, Response<EditScript_Pojo> response) {

                            Log.e("Edit Script url", call.request().toString());

                            if (response.isSuccessful()) {

//                                simpleProgressBarr.setVisibility(View.GONE);

                                Log.w("Edit Script response", new Gson().toJson(response));
                                if (response.body().getSuccess().toString().equals("1")) {
                                    Log.e("Edit Script response", new Gson().toJson(response));

                                    if (OpenParticularFolder == null || OpenParticularFolder.equals("")) {
                                        allscript_api(userid);
                                    } else {
//                            ShowscriptUnderFolderApi(userid, folderid);
                                    }
//                      myAdapter.notifyDataSetChanged();

                                }
                                shared_prefrencePrompster.setTag("");


                            } else {

//                                simpleProgressBarr.setVisibility(View.GONE);


                            }

                        }

                        @Override
                        public void onFailure(Call<EditScript_Pojo> call, Throwable t) {
//                            simpleProgressBarr.setVisibility(View.GONE);

                            Log.e("EditScriptErrror", String.valueOf(t));

                        }
                    });
                } else {

                    allscript_api(userid);
                }

            } else {
                Log.e("Success", "False");
            }
        }

    }

    public static class UpdateAsyncTask extends AsyncTask<Void, Void, Integer> {

        //Prevent leak
        Context context;
        int key;
        String script_id;

        public UpdateAsyncTask(int key, Context context, String scriptId) {
            this.key = key;
            this.context = context;
            script_id = scriptId;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            ScriptDBDao scriptDBDao = db.scriptDBDao();
            Log.e("updatingScript", "" + key + " " + script_id);
            return scriptDBDao.update(1, key, script_id);
        }

        @Override
        protected void onPostExecute(Integer integer) {

            Log.e("Check Success", integer.toString());


        }
    }


    private class ListEditFlagAsyncTask extends AsyncTask<Void, Void, List<ScriptDB>> {

        //Prevent leak
        Context context;


        public ListEditFlagAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected List<ScriptDB> doInBackground(Void... params) {
            ScriptDBDao scriptDBDao = db.scriptDBDao();
            return scriptDBDao.editBased();
        }

        @Override
        protected void onPostExecute(List<ScriptDB> list) {

            Log.e("Check Success", list.toString());

            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    Script_Toolbar script_toolbar = new Script_Toolbar();
                    EditScriptapi(userid, list.get(i).get_id(), "", list.get(i).getScripttitle(), list.get(i).getScr_txt()
                            , list.get(i).getScriptDescription(), "20", "28", "4", "10.0", "false", "140.0", "", desc_color, list.get(i).getKey());
//                new EditScriptName(Navigation.this, list.get(i).getScripttitle(), list.get(i).getKey(), userid, list.get(i).get_id(),
//                        "", list.get(i).getScr_txt(), list.get(i).getScriptDescription(), "20", "28", "4", "10.0", "false", "140.0", "",
//                        desc_color, i, 0).execute();

                }
            }
        }
    }


    private void checkSub() {
        canUserClick = false;
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        retrofit2.Call<CheckSub> call = apiService.checkSub(userid);

        call.enqueue(new Callback<CheckSub>() {
            @Override
            public void onResponse(Call<CheckSub> call, Response<CheckSub> response) {
                canUserClick = true;
                if (response.isSuccessful()) {

                    if (response.body().getSuccess() != 0) {

                        allscript_api(userid);
                    } else {
                        dialog = new Dialog(Navigation.this);

                        dialog.setContentView(R.layout.logins_successfully);
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        tv_ok = dialog.findViewById(R.id.tv_ok);
                        tvMessage = dialog.findViewById(R.id.messageText);
                        tvMessage.setText("Please Subscribe any product for continue");

                        tv_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Navigation.this, In_app.class);
                                startActivity(intent);
                            }
                        });

                        dialog.show();

                    }
                }

            }

            @Override
            public void onFailure(Call<CheckSub> call, Throwable t) {
                canUserClick = true;
            }
        });

    }

    public void openScript() {


        Script_Pojo script_pojoss = new Script_Pojo();
        script_pojoss.setScrTitle(all_script.get(0).get(position).getScrTitle());
        try {
            boolean r = false;
            for (int i = 0; i < script_pojo2.size(); i++) {
                if (script_pojoss.getScrTitle().equals(script_pojo2.get(i).getScrTitle())) {
                    r = true;
                    script_pojo2.remove(i);
                }
            }

            if (script_pojo2.size() >= 3) {
                if (!r) {
                    script_pojo2.remove(2);
                }
                script_pojo2.add(script_pojoss);
            } else {
                script_pojo2.add(script_pojoss);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("stackEvents", "outside");
        for (int i = 0; i < script_pojo1.size(); i++) {
            Log.e("stackEvents Ids", "" + script_pojo1.get(i).id);
        }
        if (!shared_prefrencePrompster.getLastScriptId().equals("")) {
            Log.e("stackEvents", "inside");

            for (int i = 0; i < script_pojo1.size(); i++) {
                if (script_pojo1.get(i).id.equals(shared_prefrencePrompster.getLastScriptId())) {
                    Log.e("stackEvents", "true " + script_pojo1.get(i).id);
                    position = i;
                    break;
                } else {
                    Log.e("stackEvents", "false " + script_pojo1.get(i).id);
                }
            }
        }
        promptTextspeed = script_pojo1.get(position).getScrPromptSpeed();

        title = script_pojo1.get(position).getScrTitle().toString();
        try {
            desc = script_pojo1.get(position).getScrText().toString();
            Log.e("desc_Fom Navigation:-..", desc);
        } catch (Exception e) {

        }
        welcome_desc = script_pojo1.get(position).getScrText().toString();
        script_id = script_pojo1.get(position).getId().toString();
        try {
            alignment = script_pojo1.get(position).getScrAlignment().toString();
        } catch (NullPointerException e) {

        }
        try {
            desc_color = script_pojo1.get(position).getScrColor().toString();

            if (desc_color == null || desc_color.equals("")) {
                desc_color = "-16777216";
            }
        } catch (Exception e) {
            desc_color = "-16777216";
        }
        try {
            scriptTextSize = script_pojo1.get(position).getScrEditTextSize().toString();
        } catch (Exception e) {

        }
        try {
            storyboard_id = script_pojo1.get(position).getId().toString();
        } catch (IndexOutOfBoundsException e) {

        }

        Intent intent = new Intent(Navigation.this, Script_Toolbar.class);
        intent.putExtra("title", title);
        try {
            intent.putExtra("alignment", alignment);
        } catch (NullPointerException e) {

        }
        if (title.contains("Welcome-Start here")) {
            intent.putExtra("description", desc);
        } else {
            intent.putExtra("description", desc);
        }
        intent.putExtra("script_id", script_id);
        intent.putExtra("desc_color", desc_color);
        intent.putExtra(Global_Constants.PROMPT_SPEED, promptTextspeed);
        intent.putExtra(Global_Constants.SCRIPT_TEXT_SIZE, scriptTextSize);
        intent.putExtra(Global_Constants.PROMPT_TEXT_SIZE, script_pojo1.get(position).getScrPromptTextSize());
        Log.e("PrimaryKey", "" + script_pojo1.get(position).getPrimarykey());
        if (isWelcomeScript) {
            intent.putExtra("script_primaryKey", idforWelcomeScript);
            isWelcomeScript = false;
        } else {
            intent.putExtra("script_primaryKey", script_pojo1.get(position).getPrimarykey());
        }

        try {
            intent.putExtra("storyboard_id", storyboard_id);
        } catch (IndexOutOfBoundsException e) {

        }

        intent.putExtra("TAG", "2");
        intent.putExtra("position", String.valueOf(position));
        Log.e("lastscriptId", script_id);
        shared_prefrencePrompster.setLastScriptId(script_id);

        shared_prefrencePrompster.setTag("2");

        startActivityForResult(intent, 147);
    }


}


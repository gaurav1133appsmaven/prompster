package com.project.prompster_live.Activity;

import android.app.Dialog;
import android.app.Person;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.project.prompster_live.Adapter.MenuAdapter;
import com.project.prompster_live.Adapter.Search_Adpapter;
import com.project.prompster_live.Api.ApiClient;
import com.project.prompster_live.Api.ApiInterface;
import com.project.prompster_live.Interface.Recycler_item_click;
import com.project.prompster_live.Pojo.AddScript;
import com.project.prompster_live.Pojo.Add_Folder_pojo;
import com.project.prompster_live.Pojo.MenuPojo;
import com.project.prompster_live.Pojo.Script;
import com.project.prompster_live.Pojo.Script_Pojo;
import com.project.prompster_live.Pojo.Search_Pojo;
import com.project.prompster_live.Pojo.Show_Folder_pojo;
import com.project.prompster_live.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.project.prompster_live.Utils.Shared_PrefrencePrompster;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Response;


public class Navigation extends AppCompatActivity  {

    private AppBarConfiguration mAppBarConfiguration;
    DrawerLayout mDrawerLayout;
    RelativeLayout drawerView;
    RelativeLayout mainView;
    NavigationView navigationView;
    Search_Adpapter myAdapter;
    Search_Pojo pojo;
    RecyclerView rv_data,rv_menu;
    Dialog show;
    List<Search_Pojo> srch = new ArrayList<>();

    TextView tv_setting,tv_okay,tv_cancel;
    Shared_PrefrencePrompster shared_prefrencePrompster;
    String userid,title,desc,script_id,foldername,storyboard_id;
    EditText folder_name;
    List<List<Script_Pojo>> all_script = new ArrayList<List<com.project.prompster_live.Pojo.Script_Pojo>>();
        private Recycler_item_click mlistener;

    private List<Person> persons;

MenuAdapter menuAdapter;
MenuPojo menuPojo;

List<MenuPojo>pojos=new ArrayList<>();
    Dialog dialog;
    TextView tv_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        shared_prefrencePrompster = Shared_PrefrencePrompster.getInstance(this);

        userid = shared_prefrencePrompster.getUserid().toString();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.menu);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = findViewById(R.id.fab);


        LinearLayoutManager llm = new LinearLayoutManager(this);
//        rv.setLayoutManager(llm);
//        rv.setHasFixedSize(true);



        rv_data = (RecyclerView) findViewById(R.id.rv_data);
        rv_menu = (RecyclerView) findViewById(R.id.rv_menu);



        rv_menu.setLayoutManager(new LinearLayoutManager(this));
        rv_menu.setHasFixedSize(true);



        menuPojo=new MenuPojo("Akshay");
        pojos.add(menuPojo);


        menuAdapter=new MenuAdapter(this,pojos);
        rv_menu.setAdapter(menuAdapter);

//        tv_setting = findViewById(R.id.tv_setting);

        mlistener = new Recycler_item_click() {
            @Override
            public void onClick(View view, int position) {

               List<Script_Pojo> script_pojo = all_script.get(0);
               title= script_pojo.get(position).getScrTitle().toString();
               desc = script_pojo.get(position).getScrText().toString();
               script_id = script_pojo.get(position).getId().toString();
               try{
                   storyboard_id = script_pojo.get(position).getStoryboard().get(0).getId();
               }
               catch (IndexOutOfBoundsException e){

               }


               Intent intent = new Intent(Navigation.this,Script_Toolbar.class);
               intent.putExtra("title",title);
               intent.putExtra("description",desc);
               intent.putExtra("script_id",script_id);
                try{
                    intent.putExtra("storyboard_id",storyboard_id);
                }
                catch (IndexOutOfBoundsException e){

                }

                intent.putExtra("TAG","2");

                shared_prefrencePrompster.setTag("2");

                startActivity(intent);
               finish();
            }
        };

        rv_data.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new Search_Adpapter(this, all_script,mlistener);
        rv_data.setAdapter(myAdapter);
        allscript_api(userid);
        //Adapter............................................
        show_folderapi();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mDrawerLayout.addDrawerListener(toggle);   //   drawer.setDrawerListener(toggle);  Deprecated
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//        navigationView.setItemIconTintList(null);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
//                .setDrawerLayout(mDrawerLayout)
//                .build();

//        tv_setting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent rl_getstarted = new Intent(Navigation.this, Profile_Activity.class);
//                startActivity(rl_getstarted);
//                finish();
//            }
//        });

//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void show_folderapi() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


        retrofit2.Call<Show_Folder_pojo> call = apiService.show_folder(userid);
        call.enqueue(new retrofit2.Callback<Show_Folder_pojo>() {
            @Override
            public void onResponse(Call<Show_Folder_pojo> call, Response<Show_Folder_pojo> response) {

                Log.e("All Script url", call.request().toString());

                if (response.isSuccessful()) {

                    Log.w("Add Script response", new Gson().toJson(response));
                    if (response.body().getSuccess().toString().equals("1")) {
                        Log.e("Add Script response", new Gson().toJson(response));

//                        Toast.makeText(Navigation.this, "hiiiiiiii", Toast.LENGTH_SHORT).show();




                    }

                    else {


                        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(Navigation.this, SweetAlertDialog.ERROR_TYPE);

                        sweetAlertDialog.setTitleText(response.body().getMessage().toString());

                        sweetAlertDialog.show();
                        Button btn = (Button) sweetAlertDialog.findViewById(R.id.confirm_button);
                        btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));

                    }

                }
            }

            @Override
            public void onFailure(Call<Show_Folder_pojo> call, Throwable t) {

                Log.e("errror", String.valueOf(t));
//                dialog = new Dialog(Navigation.this);
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

    private void allscript_api(String useridd) {


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


        retrofit2.Call<Script> call = apiService.all_script(useridd);
        call.enqueue(new retrofit2.Callback<Script>() {
            @Override
            public void onResponse(Call<Script> call, Response<Script> response) {

                Log.e("All Script url", call.request().toString());

                if (response.isSuccessful()) {

                    Log.w("Add Script response", new Gson().toJson(response));
                    if (response.body().getSuccess().toString().equals("1")) {
                        Log.e("Add Script response", new Gson().toJson(response));

//                        Toast.makeText(Navigation.this, "hiiiiiiii", Toast.LENGTH_SHORT).show();
                        all_script.clear();
                        all_script.addAll(response.body().getData());
                        myAdapter.notifyDataSetChanged();



                    }

                    else {


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

                Log.e("errror", String.valueOf(t));
//                dialog = new Dialog(Navigation.this);
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


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.activity_main3_drawer, menu);
//        return true;
//    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = androidx.navigation.Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//        int id = menuItem.getItemId();
//
//        if (id == R.id.nav_home) {
//            Intent rl_getstarted = new Intent(Navigation.this, Script_Toolbar.class);
//
//            rl_getstarted.putExtra("title","");
//            rl_getstarted.putExtra("description","");
//            rl_getstarted.putExtra("script_id","");
//
//            startActivity(rl_getstarted);
//            finish();
////            finish();
//        } else if (id == R.id.nav_gallery) {
//            //do something
//        } else if (id == R.id.nav_slideshow) {
//            //do something
//        }
//
//        else if (id == R.id.folder) {
//
//            show = new Dialog(Navigation.this);
//
//
//            show.setContentView(R.layout.add_folder);
//            show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//            tv_okay = show.findViewById(R.id.tv_okay);
//            tv_cancel = show.findViewById(R.id.tv_cancel);
//            folder_name = show.findViewById(R.id.et_folder_name);
//
//            tv_cancel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    show.dismiss();
//                }
//            });
//
//            tv_okay.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    foldername = folder_name.getText().toString();
//                    show.dismiss();
//
//                    addFolderapi(foldername);
//                }
//            });
//
//            show.show();
//
//        }
//
//
//
//
//
//        mDrawerLayout.closeDrawer(GravityCompat.START);
//        return true;
//
//    }
//
//

    private void addFolderapi(String foldername) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


        retrofit2.Call<Add_Folder_pojo> call = apiService.add_folder(userid,foldername);
        call.enqueue(new retrofit2.Callback<Add_Folder_pojo>() {
            @Override
            public void onResponse(Call<Add_Folder_pojo> call, Response<Add_Folder_pojo> response) {

                Log.e("Add Script url", call.request().toString());

                if (response.isSuccessful()) {

                    Log.w("Add Script response", new Gson().toJson(response));
                    if (response.body().getSuccess().toString().equals("1")) {
                        Log.e("Add Script response", new Gson().toJson(response));

//                        Toast.makeText(Navigation.this, "Folder added successfully", Toast.LENGTH_SHORT).show();


                    } else {


                        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(Navigation.this, SweetAlertDialog.ERROR_TYPE);

                        sweetAlertDialog.setTitleText(response.body().getMessage().toString());

                        sweetAlertDialog.show();
                        Button btn = (Button) sweetAlertDialog.findViewById(R.id.confirm_button);
                        btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));

                    }

                }
            }

            @Override
            public void onFailure(Call<Add_Folder_pojo> call, Throwable t) {

                Log.e("errror", String.valueOf(t));
//                dialog = new Dialog(Navigation.this);
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

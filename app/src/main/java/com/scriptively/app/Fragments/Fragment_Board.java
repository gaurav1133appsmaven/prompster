package com.scriptively.app.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.scriptively.app.Adapter.StoryBoard_Adapter;
import com.scriptively.app.Api.ApiClient;
import com.scriptively.app.Api.ApiInterface;
import com.scriptively.app.Database.AppDatabase;
import com.scriptively.app.Database.StoryBoardDao;
import com.scriptively.app.DatabaseModel.ScriptDB;
import com.scriptively.app.DatabaseModel.StoryBoardDB;
import com.scriptively.app.Interface.Recycler_item_click;
import com.scriptively.app.Pojo.AddStoryBoard_pojo;
import com.scriptively.app.Pojo.ShowStoryBoard_Datum;
import com.scriptively.app.R;
import com.scriptively.app.Utils.Shared_PrefrencePrompster;
import com.scriptively.app.Utils.Util;

import java.util.ArrayList;
import java.util.List;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Response;

public class Fragment_Board extends Fragment {
    RecyclerView rv_storyboard;
    StoryBoard_Adapter storyBoard_adapter;
    Shared_PrefrencePrompster shared_prefrencePrompster;
    String userid, script_id, desc, title;
    ImageView iv_add_story;
    private Recycler_item_click mlistener;
    ACProgressFlower dialog_progress;
    List<ShowStoryBoard_Datum> storyboard_list = new ArrayList<ShowStoryBoard_Datum>();
    TextView tv_title;
    Dialog dialog;
    TextView tv_ok;
    RelativeLayout rl_addstory;
    int position,primaryKey;
    LinearLayoutManager layoutManager;
    RecyclerView.SmoothScroller smoothScroller;
    String back_value;
    Intent intent;

    String scrTitle, script_text, titleeee, addScript_id;
    static AppDatabase db;
    StoryBoardDao storyBoardDao;
    StoryBoardDB storyBoardDB;
    ScriptDB scriptDB;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_script1, container, false);

        shared_prefrencePrompster = Shared_PrefrencePrompster.getInstance(getContext());
        userid = shared_prefrencePrompster.getUserid().toString();
        //local database..................
        db = AppDatabase.getInstance(getContext());
        storyBoardDao = db.storyBoardDao();
        //............................
        intent = new Intent();
        intent = getActivity().getIntent();
        Bundle arguments = getArguments();
        script_id = arguments.getString("script_id");
        if (arguments.getString("script_title").length() > 28) {
            title = arguments.getString("script_title").substring(0, 28) + "....";
        } else
            title = arguments.getString("script_title");
        try {
            primaryKey = arguments.getInt("script_primaryKey");
        }
        catch (Exception e){

        }
//        title = arguments.getString("script_title");
        rv_storyboard = view.findViewById(R.id.rv_storyboard);
        iv_add_story = view.findViewById(R.id.iv_add_story);
        rl_addstory = view.findViewById(R.id.rl_addstory);

        dialog_progress = new ACProgressFlower.Builder(getActivity())
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Please wait...")
                .fadeColor(Color.DKGRAY).build();
        layoutManager = new LinearLayoutManager(getActivity());

        rv_storyboard.setLayoutManager(layoutManager);

        smoothScroller = new LinearSmoothScroller(getActivity()) {
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_END;
            }
        };
//        storyBoard_adapter = new StoryBoard_Adapter(getContext(), storyboard_list);
//        rv_storyboard.setAdapter(storyBoard_adapter);

     /*   rv_storyboard.smoothScrollToPosition(position);
        rv_storyboard.setNestedScrollingEnabled(false);*/
        RecyclerView.OnItemTouchListener mScrollTouchListener = new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                int action = e.getAction();
                switch (action) {
                    case MotionEvent.ACTION_MOVE:
                        rv.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {


            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        };
        rv_storyboard.addOnItemTouchListener(mScrollTouchListener);
//        rv_storyboard.setNestedScrollingEnabled(false);


        //rv_storyboard.smoothScrollToPosition(0);

        tv_title = view.findViewById(R.id.tv_title);

        Log.e("dataRole","fragment 1");
        showstoryBoardApi(userid, script_id,String.valueOf(primaryKey),db);

        try {
            if (title.equals("") || title.equals(null)) {
                tv_title.setText("Script1");
            } else {
                tv_title.setText(title);
            }


        } catch (NullPointerException e) {

        }


        rl_addstory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("filldata","button clicked");

                addStoryBoardapi(primaryKey,userid, script_id, "Title","new",db);

            }
        });



        return view;
    }



    public void addStoryBoardapi(int key,final String useridd, final String script_id, String Title,String check_new,AppDatabase db) {
        Log.e("filldata","inside function");
        if (Util.isConnected()) {
            shared_prefrencePrompster = Shared_PrefrencePrompster.getInstance(getContext());
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

Log.e("filldata",shared_prefrencePrompster.getUserid().toString());
Log.e("filldata",script_id.toString());
Log.e("filldata",Title.toString());
            JsonObject object=new JsonObject();
            object.addProperty("storyboardAttrText","20");
            object.addProperty("storyboardText","description");
            object.addProperty("storyboardName",Title);
            object.addProperty("script_id",script_id.toString());
            object.addProperty("user_id",shared_prefrencePrompster.getUserid().toString());
            Log.e("storyBoardData", new Gson().toJson(object));

            retrofit2.Call<AddStoryBoard_pojo> call = apiService.addStoryBoard(object);
            call.enqueue(new retrofit2.Callback<AddStoryBoard_pojo>() {
                @Override
                public void onResponse(Call<AddStoryBoard_pojo> call, Response<AddStoryBoard_pojo> response) {

                    try {

                        if (response.isSuccessful()) {

                            Log.e("storyBoardData", new Gson().toJson(response));
                            if (response.body().getSuccess().toString().equals("1")) {



                                if (check_new.equals("new")) {

                                    storyBoardDB = new StoryBoardDB(script_id, "",
                                            "Start writing here", Title, "20", 1, 1,key);
                                    Log.e("datasource","limit2");
                                    new SaveStoryAsyncTask(storyBoardDB, getContext(),db).execute();
                                    if(storyBoard_adapter!=null)
                                    storyBoard_adapter.notifyDataSetChanged();
                                }
                                else {
                                    new UpdateAsyncTask(key, getContext()).execute();
                                    if(storyBoard_adapter!=null)
                                    storyBoard_adapter.notifyDataSetChanged();
                                }
                                Log.e("dataRole","fragment 2");
                                showstoryBoardApi(userid, script_id,String.valueOf(primaryKey),db);

                            }
                            else {
                                Log.e("storyBoard","else part");
                            }
                        }
                    } catch (Exception e) {
                        Log.e("storyBoard","exception caught"+e.getMessage());

                    }
                }

                @Override
                public void onFailure(Call<AddStoryBoard_pojo> call, Throwable t) {

                    Log.e("storyBoard", String.valueOf(t));

                }
            });
        }
        else {
            storyBoardDB = new StoryBoardDB(script_id, "",
                    "Start writing here", Title, "20", 1, 0,key);
Log.e("datasource","limit3");
            new SaveStoryAsyncTask(storyBoardDB, getContext(),db).execute();
        }


    }

    public void showstoryBoardApi(String userid, String script_id,String script_primarykey,AppDatabase db) {

            new GetStoryDataAsyncTask(getContext(), script_id,primaryKey,db).execute();

//            Toast.makeText(getContext(), "Internet is not connected", Toast.LENGTH_SHORT).show();
    }


    private class GetStoryDataAsyncTask extends AsyncTask<Void, Void, List<StoryBoardDB>> {

        //Prevent leak
        Context context;
        String scriptId;
        List<ShowStoryBoard_Datum> showStoryBoard_data = new ArrayList<>();
        ShowStoryBoard_Datum showStoryBoard_datum;
        int script_key;
        AppDatabase db;


        public GetStoryDataAsyncTask(Context context, String scriptId,int script_PrimaryKey,AppDatabase db) {
            this.context = context;
            this.scriptId = scriptId;
            this.script_key = script_PrimaryKey;
            this.db=db;
        }

        @Override
        protected List<StoryBoardDB> doInBackground(Void... params) {

                StoryBoardDao storyBoardDao = db.storyBoardDao();
                Log.e("scriptKey data","...");
                Log.e("scriptKey",""+script_key);
                return storyBoardDao.getAll(script_key);

        }

        @Override
        protected void onPostExecute(List<StoryBoardDB> list) {
            if (list.size()>0){


            for (int i = 0; i < list.size(); i++) {
//                id = list.get(0).uid;
                showStoryBoard_datum = new ShowStoryBoard_Datum();
                showStoryBoard_datum.setScriptId(list.get(i).getScript_id());
                showStoryBoard_datum.setId(list.get(i).getStoryboardId());
                showStoryBoard_datum.setStoryboardText(list.get(i).getStoryboard_att_text());
                showStoryBoard_datum.setStoryboardName(list.get(i).getStoryboard_name());
                showStoryBoard_datum.setPrimarykey(list.get(i).getScriptKey());
                showStoryBoard_datum.setPrimarykeyStory(list.get(i).getStoryKey());
                showStoryBoard_data.add(showStoryBoard_datum);

            }

            storyboard_list.clear();
            storyboard_list.add(showStoryBoard_datum);

            storyBoard_adapter = new StoryBoard_Adapter(getContext(), showStoryBoard_data);
            try {
                rv_storyboard.smoothScrollToPosition(storyBoard_adapter.getItemCount() - 1);
                rv_storyboard.setAdapter(storyBoard_adapter);
            }
            catch (Exception e){

            }

            if (Util.isConnected()){

                for (int i = 0 ; i<list.size(); i ++){
                    if (list.get(i).getStoryBoardinternetFlag() == 0){
                        addStoryBoardapi(list.get(i).getScriptKey(),userid, scriptId, list.get(i).getStoryboard_name(), "local",db);
                    }
                }

//                new GetscriptfalseDataAsyncTask(getContext()).execute();
            }

//            storyBoard_adapter.notifyDataSetChanged();


            }
        }
    }

    public static class SaveStoryAsyncTask extends AsyncTask<Void, Void, Long> {

        //Prevent leak
        Context context;
        StoryBoardDB storyBoardDB;
        AppDatabase db;

        public SaveStoryAsyncTask(StoryBoardDB storyBoardDB, Context context,AppDatabase db) {
            this.storyBoardDB = storyBoardDB;
            this.context = context;
            this.db=db;
        }

        @Override
        protected Long doInBackground(Void... params) {
            Log.e("storyBackground","hit comes in");
            try {

                StoryBoardDao scriptDBDao = db.storyBoardDao();
                long dataReturned;
                dataReturned=scriptDBDao.insertStoryBoard(storyBoardDB);
                Log.e("StoryBoardarta",""+dataReturned);
                return dataReturned;
            }catch(Exception e){
                Log.i("dbexception",e.getMessage());
                return  null;
            }

        }

        @Override
        protected void onPostExecute(Long scriptCount) {

          Log.e("Check StoryBoard", "on postexecution db");

//                    showstoryBoardApi(userid, script_id,String.valueOf(primaryKey));

        }
    }



//    public class GetscriptfalseDataAsyncTask extends AsyncTask<Void, Void, List<StoryBoardDB>> {
//
//        //Prevent leak
//        Context context;
//        String scriptId;
//        List<Script_Pojo> scriptList = new ArrayList<>();
//        Script_Pojo script_pojo;
//
//        public GetscriptfalseDataAsyncTask(Context context) {
//            this.context = context;
//            this.scriptId = scriptId;
//        }
//
//        @Override
//        protected List<StoryBoardDB> doInBackground(Void... params) {
//            StoryBoardDao storyBoardDao = db.storyBoardDao();
//            return storyBoardDao.internetBased();
//        }
//
//        @Override
//        protected void onPostExecute(List<StoryBoardDB> list) {
//
//            Log.e("ListResponse", new Gson().toJson(list));
//            if (list.size()>0) {
//                for (int i = 0; i < list.size(); i++) {
//                    Script_Toolbar script_toolbar = new Script_Toolbar();
//
//                    addStoryBoardapi(userid, list.get(i).getScript_id(), "Title", "local");
//                    new UpdateAsyncTask(list.get(i).getStoryKey(), context).execute();
//
//                }
//            }
//        }
//    }

    private class UpdateAsyncTask extends AsyncTask<Void, Void, Integer> {

        //Prevent leak
        Context context;
        int key;

        public UpdateAsyncTask(int key, Context context) {
            this.key = key;
            this.context = context;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            StoryBoardDao storyBoardDao = db.storyBoardDao();
            return storyBoardDao.updateInternetFlag(1, key);
        }

        @Override
        protected void onPostExecute(Integer integer) {

            Log.e("Check Success", integer.toString());
        }
    }


}

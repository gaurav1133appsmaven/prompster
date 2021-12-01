package com.scriptively.app.Adapter;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.scriptively.app.Activity.Script_Toolbar;
import com.scriptively.app.Api.ApiClient;
import com.scriptively.app.Api.ApiInterface;
import com.scriptively.app.Database.AppDatabase;
import com.scriptively.app.Database.StoryBoardDao;
import com.scriptively.app.DatabaseModel.ScriptDB;
import com.scriptively.app.DatabaseModel.StoryBoardDB;
import com.scriptively.app.Fragments.Fragment_Board;
import com.scriptively.app.Interface.Recycler_item_click;
import com.scriptively.app.Pojo.DeleteListStory;
import com.scriptively.app.Pojo.Delete_Story_board_pojo;
import com.scriptively.app.Pojo.EditStoryPojo;
import com.scriptively.app.Pojo.ShowStoryBoard_Datum;
import com.scriptively.app.R;
import com.scriptively.app.Utils.Shared_PrefrencePrompster;
import com.scriptively.app.Utils.Util;

import java.util.ArrayList;
import java.util.List;

import cc.cloudist.acplibrary.ACProgressFlower;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Response;

import static android.content.Context.CLIPBOARD_SERVICE;
import static com.facebook.FacebookSdk.getApplicationContext;

public class StoryBoard_Adapter extends RecyclerView.Adapter<StoryBoard_Adapter.ViewHolder> {
    Context context;
    List<ShowStoryBoard_Datum> showstoryboardlist = new ArrayList<>();
    String title_text, description_text, user_id, script_id, storyboard_id, title, desc;
    private Recycler_item_click mlistener;
    TextView tv_cancel, tv_delete, tv_cancell, tv_okay, tv_copy, tv_intro, tv_write_text;
    Dialog dialog;
    ClipboardManager cm;
    ACProgressFlower dialog_progress;
    private ClipData myClip;
    static AppDatabase db;
    Shared_PrefrencePrompster shared_prefrencePrompster;


    public StoryBoard_Adapter(){

    }

    public StoryBoard_Adapter(Context fragment_board, List<ShowStoryBoard_Datum> storyboard_list) {

        this.context = fragment_board;
        this.showstoryboardlist = storyboard_list;
        this.mlistener = mlistener;

        db = AppDatabase.getInstance(context);
        try {
            if (Util.isConnected()) {
                new ListEditFlagAsyncTask(this.context).execute();
            }
        }
        catch (Exception e){

        }

    }


    @NonNull
    @Override
    public StoryBoard_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.story_board, parent, false);

        shared_prefrencePrompster = Shared_PrefrencePrompster.getInstance(context);
        user_id = shared_prefrencePrompster.getUserid();
        tv_write_text = v.findViewById(R.id.tv_write_text);
        // set the view's size, margins, paddings and layout parameters
        return new StoryBoard_Adapter.ViewHolder(v, mlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull final StoryBoard_Adapter.ViewHolder holder, final int position) {

        try {
            title_text = showstoryboardlist.get(position).getStoryboardName().toString();
            description_text = showstoryboardlist.get(position).getStoryboardText().toString();
            holder.tv_intro.setText(title_text);

            holder.tv_write_text.setText(description_text);

//            holder.tv_intro.setRawInputType(InputType.TYPE_CLASS_TEXT);
            holder.tv_intro.setImeOptions(EditorInfo.IME_ACTION_DONE);
            holder.tv_intro.setRawInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);

//            holder.tv_write_text.setSelectAllOnFocus(true);
        } catch (Exception e) {

        }


        holder.tv_intro.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    title = holder.tv_intro.getText().toString();
                    desc = holder.tv_write_text.getText().toString();
                    try {
//                        user_id = showstoryboardlist.get(position).getUserId().toString();

                    script_id = showstoryboardlist.get(position).getScriptId().toString();
                    storyboard_id = showstoryboardlist.get(position).getId().toString();
                    holder.rel_storyboard_bottom.setVisibility(View.GONE);

                    editStoryBoardapi(position, user_id, script_id, storyboard_id, title, desc);
                    }
                    catch (IndexOutOfBoundsException e){

                    }

                }
                return false;
            }
        });

//        holder.tv_write_text.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                tv_write_text.setCursorVisible(true);
//                return false;
//            }
//        });

        holder.tv_intro.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    title = holder.tv_intro.getText().toString();
                    desc = holder.tv_write_text.getText().toString();
                    try {
//                        user_id = showstoryboardlist.get(position).getUserId().toString();


                    script_id = showstoryboardlist.get(position).getScriptId().toString();
                    storyboard_id = showstoryboardlist.get(position).getId().toString();
                    holder.rel_storyboard_bottom.setVisibility(View.GONE);

                    editStoryBoardapi(position, user_id, script_id, storyboard_id, title, desc);
                    }catch (IndexOutOfBoundsException e){

                    }
                } else {
                    Log.e("holderposition","data captured");


                }
            }
        });

        holder.tv_write_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    title = holder.tv_intro.getText().toString();
                    desc = holder.tv_write_text.getText().toString();
                    try {

//                        user_id = showstoryboardlist.get(position).getUserId().toString();

                    script_id = showstoryboardlist.get(position).getScriptId().toString();
                    storyboard_id = showstoryboardlist.get(position).getId().toString();
                    holder.rel_storyboard_bottom.setVisibility(View.GONE);

                    editStoryBoardapi(position, user_id, script_id, storyboard_id, title, desc);
                    }
                    catch (IndexOutOfBoundsException e){

                    }
                } else {
                    if (holder.tv_write_text.getText().toString().equals("Start writing here")||holder.tv_write_text.getText().toString().equals("Start writing here...")) {
                        holder.tv_write_text.setSelectAllOnFocus(true);
                        holder.tv_write_text.selectAll();

                    } else {
                        holder.tv_write_text.setSelectAllOnFocus(false);
                    }
                }
            }
        });

        holder.tv_write_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                Log.e("ActionButton", EditorInfo.IME_ACTION_DONE + "  " + i);
                if (i == EditorInfo.IME_ACTION_DONE) {

//                    tv_write_text.setCursorVisible(false);
                    title = holder.tv_intro.getText().toString();
                    desc = holder.tv_write_text.getText().toString();
                    try {

//                        user_id = showstoryboardlist.get(position).getUserId().toString();

                    script_id = showstoryboardlist.get(position).getScriptId().toString();
                    storyboard_id = showstoryboardlist.get(position).getId().toString();
                    holder.rel_storyboard_bottom.setVisibility(View.GONE);

                    editStoryBoardapi(position, user_id, script_id, storyboard_id, title, desc);
                    }
                    catch (IndexOutOfBoundsException e){

                    }

                }
                return false;
            }
        });


//

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(context);

                dialog.setContentView(R.layout.delete_storyboard_dialog);
                dialog.setCanceledOnTouchOutside(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                desc = holder.tv_write_text.getText().toString().trim();

                tv_delete = dialog.findViewById(R.id.tv_delete);
                tv_cancel = dialog.findViewById(R.id.tv_cancel);
                tv_copy = dialog.findViewById(R.id.tv_copy);
                tv_copy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        intro= tv_intro.getText().toString();

                        dialog.dismiss();
                        cm = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
//                        String text;
//                        text = tv_write_text.getText().toString();

                        myClip = ClipData.newPlainText("text", desc);
                        cm.setPrimaryClip(myClip);

                        Toast.makeText(getApplicationContext(), "Text Copied", Toast.LENGTH_SHORT).show();


                    }
                });


                tv_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();


                tv_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(context, "hellooo", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
//                        Toast.makeText(context, "hellooo", Toast.LENGTH_SHORT).show();
                        dialog = new Dialog(context);

                        dialog.setCanceledOnTouchOutside(false);
                        dialog.setContentView(R.layout.delete_or_not);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        tv_okay = dialog.findViewById(R.id.tv_okay);
                        tv_cancell = dialog.findViewById(R.id.tv_cancell);

                        tv_okay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
//                                try {
//                                    if (showstoryboardlist.size()>1 && showstoryboardlist.size()<3) {
////                                        user_id = showstoryboardlist.get(position-1).getUserId().toString();
//                                        try {
//                                            script_id = showstoryboardlist.get(position - 1).getScriptId().toString();
//                                        }
//                                        catch (Exception e){
//
//                                        }
//                                        try{
//                                        storyboard_id = showstoryboardlist.get(position-1).getId().toString();
//                                        }
//                                        catch (Exception e){
//
//                                        }
//                                        holder.rel_storyboard_bottom.setVisibility(View.GONE);
//
//                                        notifyItemRemoved(position-1);
//                                        new DeleteStoryAsyncTask(showstoryboardlist.get(position-1).getPrimarykey(), context, position-1, script_id, user_id,storyboard_id,showstoryboardlist.get(position-1).getPrimarykeyStory()).execute();
//                                        showstoryboardlist.remove(position-1);
////                                        deleteStoryBoardapi(position-1, user_id, script_id, storyboard_id);
//                                    }
//                                    else {
//                                        user_id = showstoryboardlist.get(position).getUserId().toString();
                                        try{
                                        script_id = showstoryboardlist.get(position).getScriptId().toString();

                                        storyboard_id = showstoryboardlist.get(position).getId().toString();
                                        }
                                        catch (Exception e){

                                        }
                                        holder.rel_storyboard_bottom.setVisibility(View.GONE);

                                        notifyItemRemoved(position);
                                        new DeleteStoryAsyncTask(showstoryboardlist.get(position).getPrimarykey(), context, position, script_id, user_id,storyboard_id,showstoryboardlist.get(position).getPrimarykeyStory()).execute();
                                        showstoryboardlist.remove(position);
//                                        deleteStoryBoardapi(position, user_id, script_id, storyboard_id);
//                                    }
//                                }
//                                catch (Exception e)
//                                {
//                                    e.printStackTrace();
//                                }


                                Runnable progressRunnable = new Runnable() {

                                    @Override
                                    public void run() {

                                        try {


                                        }
                                        catch (IndexOutOfBoundsException e){

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
                            }
                        });
                        dialog.show();
                    }
                });


            }
        });

    }

    private void editStoryBoardapi(final int position, final String user_id, final String script_id, String storyboard_id, final String title, final String desc) {

        try {
            if (Util.isConnected()) {

                new EditStoryBoard(context, title, showstoryboardlist.get(position).getPrimarykey(), user_id, script_id, storyboard_id, desc, 1,showstoryboardlist.get(position).getPrimarykeyStory()).execute();

            } else {
                new EditStoryBoard(context, title, showstoryboardlist.get(position).getPrimarykey(), user_id, script_id, storyboard_id, desc, 0,showstoryboardlist.get(position).getPrimarykeyStory()).execute();
            }

        }
        catch (Exception e){

        }

    }

    public void deleteStoryBoardapi(final int positionn, String user_id, String script_id, String storyboard_id) {

        if (Util.isConnected()){

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


            retrofit2.Call<Delete_Story_board_pojo> call = apiService.deleteStoryBoard(user_id, script_id, storyboard_id);
            call.enqueue(new retrofit2.Callback<Delete_Story_board_pojo>() {
                @Override
                public void onResponse(Call<Delete_Story_board_pojo> call, Response<Delete_Story_board_pojo> response) {

                    Log.e("Add Script url", call.request().toString());

                    if (response.isSuccessful()) {

                        Log.w("Add Script response", new Gson().toJson(response));
                        if (response.body().getSuccess().toString().equals("1")) {
                            Log.e("Add Script response", new Gson().toJson(response));

                            notifyDataSetChanged();


                        } else {

                            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);

                            sweetAlertDialog.setTitleText(response.body().getMessage().toString());

                            sweetAlertDialog.show();
                            Button btn = (Button) sweetAlertDialog.findViewById(R.id.confirm_button);
                            btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));

                        }

                    }
                }

                @Override
                public void onFailure(Call<Delete_Story_board_pojo> call, Throwable t) {
                    Log.e("errror", String.valueOf(t));

                }
            });

        }
        else {

        }



    }


    @Override
    public int getItemCount() {
        if (showstoryboardlist.size() > 0) {
            return showstoryboardlist.size();
        } else {
            return 0;
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RelativeLayout rl_introo, rel_storyboard_bottom;
        EditText tv_write_text, tv_intro;
        private Recycler_item_click mlistener;
        RelativeLayout view;
        TextView tv_title;

        public ViewHolder(@NonNull View itemView, Recycler_item_click mlistener) {
            super(itemView);
            this.mlistener = mlistener;
            rl_introo = itemView.findViewById(R.id.rl_introo);
            rel_storyboard_bottom = itemView.findViewById(R.id.rel_storyboard_bottom);
            tv_intro = itemView.findViewById(R.id.tv_intro);
            tv_write_text = itemView.findViewById(R.id.tv_write_text);
            view = itemView.findViewById(R.id.view);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_intro.setHorizontallyScrolling(false);
            tv_intro.setRawInputType(InputType.TYPE_CLASS_TEXT);
            tv_intro.setImeOptions(EditorInfo.IME_ACTION_DONE);

            tv_write_text.setHorizontallyScrolling(false);
//            tv_write_text.setRawInputType(InputType.TYPE_CLASS_TEXT);
            tv_write_text.setImeOptions(EditorInfo.IME_ACTION_DONE);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            try {
//                mlistener.onClick(view, getAdapterPosition());
//            } catch (NullPointerException e) {
//
//            }


        }
    }
    private class EditStoryBoard extends AsyncTask<Void, Void, Integer> {

        Context context;
        String title;
        int key,storyKey;
        String userid;
        String addScript_id;
        String story_board_id;
        String description;
        int editValue;

//        EditScriptName(Context context, String title, int key)
//        {
//            this.context = context;
//            this.key = key;
//        }

        public EditStoryBoard(Context navigation, String scrTitle, int primarykey, String userid, String addScript_id, String story_board_id,
                              String description, int editValue,int storyKey) {

            this.context = navigation;
            this.key = primarykey;
            this.storyKey = storyKey;
            this.title = scrTitle;
            this.userid = userid;
            this.addScript_id = addScript_id;
            this.story_board_id = story_board_id;
            this.editValue = editValue;
            this.description = description;

        }

        @Override
        protected Integer doInBackground(Void... voids) {
            StoryBoardDao storyBoardDao = db.storyBoardDao();
            return storyBoardDao.updateStoryBoard(title, description,editValue, storyKey);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            if (integer > 0) {
                Log.e("Success", "True");

                if (Util.isConnected()) {
                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                    JsonObject object=new JsonObject();
                    object.addProperty("user_id",user_id);
                    object.addProperty("script_id",script_id);
                    object.addProperty("story_board_id",storyboard_id);
                    object.addProperty("storyboardName",title);
                    object.addProperty("storyboardText",desc);
                    object.addProperty("storyboardAttrText","desc");
                    Log.e("storyBoardData", new Gson().toJson(object));

                    retrofit2.Call<EditStoryPojo> call = apiService.edit_story_board(object);
                    call.enqueue(new retrofit2.Callback<EditStoryPojo>() {
                        @Override
                        public void onResponse(Call<EditStoryPojo> call, Response<EditStoryPojo> response) {

                            Log.e("Edit StoryBoard url", call.request().toString());

                            if (response.isSuccessful()) {

                                Log.w("Edit Response", new Gson().toJson(response));
                                if (response.body().getSuccess().toString().equals("1")) {
                                    Log.e("Edit Response", new Gson().toJson(response));


                                }
                            }
                            else
                            {
                                Log.e("responseError", ""+response.raw().body());
                                Log.e("responseError", ""+response.body());
                            }
                        }

                        @Override
                        public void onFailure(Call<EditStoryPojo> call, Throwable t) {

                            Log.e("errror", String.valueOf(t));

                        }
                    });

                } else {
Log.e("dataRole","Adapter");
                    new Fragment_Board().showstoryBoardApi(user_id,script_id, String.valueOf(key),db);
                }

            } else {
                Log.e("Success", "False");
            }
        }

    }

    public class ListEditFlagAsyncTask extends AsyncTask<Void, Void, List<StoryBoardDB>> {

        //Prevent leak
        Context context;


        public ListEditFlagAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected List<StoryBoardDB> doInBackground(Void... params) {
            StoryBoardDao storyBoardDao = db.storyBoardDao();
            return storyBoardDao.editBased();
        }

        @Override
        protected void onPostExecute(List<StoryBoardDB> list) {

            Log.e("Check Success", list.toString());

            if (list.size()>0) {
                for (int i = 0; i < list.size(); i++) {
                    Script_Toolbar script_toolbar = new Script_Toolbar();
                    editStoryBoardapi(i, user_id, list.get(i).getScript_id(), list.get(i).getStoryboardId(), list.get(i).getStoryboard_name()
                            , list.get(i).getStoryboard_att_text());

//                    try {
//                        new EditStoryBoard(context, list.get(i).getStoryboard_name(), list.get(i).getScriptKey(), user_id, list.get(i).getScript_id(), list.get(i).getStoryboardId(), list.get(i).getStoryboard_att_text(), 0,list.get(i).getStoryKey()).execute();
//                    }
//                    catch (Exception e){
//
//                    }

                }
            }
        }
    }
    private class DeleteStoryAsyncTask extends AsyncTask<Void, Void, Integer> {
        ScriptDB scriptDB;
        Context context;
        int key,storyKey;
        int positi;
        String script_id,storyBoardId;
        String userid;

        DeleteStoryAsyncTask(int key, Context context, int pos, String script_id, String userid,String storyBoardId,int storyKey) {

            this.scriptDB = scriptDB;
            this.key = key;
            this.storyKey = storyKey;
            this.positi = pos;
            this.context = context;
            this.script_id = script_id;
            this.storyBoardId = storyBoardId;
            this.userid = userid;

        }

        @Override
        protected Integer doInBackground(Void... voids) {
            StoryBoardDao storyBoardDao = db.storyBoardDao();
            return storyBoardDao.delete(storyKey,key);
        }

        @Override
        protected void onPostExecute(Integer integer) {

            Log.e("Check", integer + "");

            if (integer > 0) {
                Toast.makeText(context, "Delete Successfully", Toast.LENGTH_SHORT).show();
                if (Util.isConnected()) {

                    deleteStoryBoardapi(positi,userid,script_id,storyBoardId);

                }
                else {
                    DeleteListStory deleteListStoryy = new DeleteListStory();
                    deleteListStoryy.setScriptId(script_id);
                    deleteListStoryy.setStoryboardId(storyBoardId);
                    Util.deleteListStory.add(deleteListStoryy);

                }
//                allscript_api(userid);
            }
// super.onPostExecute(integer);
        }
    }
}


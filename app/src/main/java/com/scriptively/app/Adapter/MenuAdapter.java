package com.scriptively.app.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.scriptively.app.Activity.Navigation;
import com.scriptively.app.Activity.Profile_Activity;
import com.scriptively.app.Activity.Script_Toolbar;
import com.scriptively.app.Api.ApiClient;
import com.scriptively.app.Api.ApiInterface;
import com.scriptively.app.Database.AppDatabase;
import com.scriptively.app.Database.FolderDao;
import com.scriptively.app.DatabaseModel.FolderDB;
import com.scriptively.app.Pojo.Add_Folder_pojo;
import com.scriptively.app.Pojo.MenuPojo;
import com.scriptively.app.Pojo.Pojo;
import com.scriptively.app.Pojo.Recent_data;
import com.scriptively.app.Pojo.Script_Pojo;
import com.scriptively.app.Pojo.Show_Folder_Datum;
import com.scriptively.app.Pojo.Show_Folder_pojo;
import com.scriptively.app.R;
import com.scriptively.app.Utils.Shared_PrefrencePrompster;
import com.scriptively.app.Utils.SwipeHelper;
import com.scriptively.app.Utils.Util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cc.cloudist.acplibrary.ACProgressFlower;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> implements SwipeRefreshLayout.OnRefreshListener  {

    static AppDatabase db;
    FolderDB folderDB;
    Context context;
    List<MenuPojo> pojos = new ArrayList<>();
    Dialog show;
    EditText folder_name;
    TextView tv_setting, tv_okay, tv_cancel,tv_scriptDelete,tv_cancell,tv_title1, tv_title2;
    String userid, title, desc, script_id, foldername,FolderId;
    RecyclerView rl_recyclerview, rv_recent_script;
    RelativeLayout rl_recycler, rl_recent_script;
    Shared_PrefrencePrompster shared_prefrencePrompster;
    LinearLayoutManager linearLayoutManager, linearLayoutManagerr;
    ACProgressFlower dialog_progress;
    MenuPojo menuPojo;
    Demo demo;
    Dialog dialog;
    Pojo demopojo;
    List<Pojo> pojo = new ArrayList<>();
    List<Show_Folder_Datum> show_folder_data = new ArrayList<Show_Folder_Datum>();
    Recent_data recent;
    List<Recent_data> recent_list = new ArrayList<>();
    Recent_Adapter recent_adapter;
    public static List<String> deleteListFolder = new ArrayList<>();

    SwipeRefreshLayout swipe_container_folders;

    public MenuAdapter(Navigation navigation, List<MenuPojo> pojos) {
        this.context = navigation;
        this.pojos = pojos;
        db = AppDatabase.getInstance(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_navigation, parent, false);


        rl_recyclerview = v.findViewById(R.id.rl_recyclerview);

        rv_recent_script = v.findViewById(R.id.rv_recent_script);
        swipe_container_folders = v.findViewById(R.id.swipe_container_folders);

        shared_prefrencePrompster = Shared_PrefrencePrompster.getInstance(context);
        userid = shared_prefrencePrompster.getUserid().toString();

        linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rl_recyclerview.setLayoutManager(linearLayoutManager);
        demo = new Demo(context, show_folder_data);
        rl_recyclerview.setAdapter(demo);

//////recent script

        swipe_container_folders.setOnRefreshListener(this);

//        swipe_container_folders.post(new Runnable() {
//
//            @Override
//            public void run() {
//
//                swipe_container_folders.setRefreshing(true);
//
//                // Fetching data from server
////                loadFolderRecyclerViewData();
//            }
//        });
        linearLayoutManagerr = new LinearLayoutManager(context);
        linearLayoutManagerr.setOrientation(LinearLayoutManager.VERTICAL);
        rv_recent_script.setLayoutManager(linearLayoutManagerr);

        Navigation navigation = new Navigation();
        Collections.reverse(navigation.script_pojo2);
        recent_adapter = new Recent_Adapter(context, navigation.script_pojo2);
        rv_recent_script.setAdapter(recent_adapter);

/////////////
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

        rl_recyclerview.addOnItemTouchListener(mScrollTouchListener);
        rv_recent_script.addOnItemTouchListener(mScrollTouchListener);

        rl_recyclerview.setNestedScrollingEnabled(false);

        SwipeHelper swipeHelper = new SwipeHelper(context) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {

                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Rename",
                        0,
                        Color.parseColor("#C7C7CB"),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(final int pos) {

                                show = new Dialog(context);

                                show.setContentView(R.layout.add_folder);
                                show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                tv_okay = show.findViewById(R.id.tv_okay);
                                tv_title1 = show.findViewById(R.id.tv_title1);
                                tv_title2 = show.findViewById(R.id.tv_title2);
                                tv_cancel = show.findViewById(R.id.tv_cancel);
                                folder_name = show.findViewById(R.id.et_folder_name);
                                folder_name.setText(show_folder_data.get(pos).getName());
                                tv_title1.setText("Scriptively!");
                                tv_title2.setVisibility(View.VISIBLE);
                                tv_title2.setText("Please enter new name of Folder");

                                tv_cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        show.dismiss();
                                    }
                                });

                                tv_okay.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        demo.notifyDataSetChanged();
                                        FolderId = show_folder_data.get(pos).getId();
                                        foldername = folder_name.getText().toString();
                                        show.dismiss();
                                        EditFolderApi(userid,FolderId,foldername,pos,show_folder_data.get(pos).getFolder_primarykey());
                                    }
                                });

                                show.show();

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


                                FolderId = show_folder_data.get(pos).getId();

                                dialog = new Dialog(context);

                                dialog.setCanceledOnTouchOutside(false);
                                dialog.setContentView(R.layout.delete_or_not);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                tv_scriptDelete = dialog.findViewById(R.id.tv_scriptDelete);
                                tv_okay = dialog.findViewById(R.id.tv_okay);
                                tv_cancell = dialog.findViewById(R.id.tv_cancell);
                                tv_scriptDelete.setText("Are you sure you want to delete this folder permanently");

                                tv_okay.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();


                                        demo.notifyItemRemoved(pos);
                                        new DeleteFolderAsyncTask(show_folder_data.get(pos).getFolder_primarykey(),context,userid,FolderId).execute();
                                        show_folder_data.remove(pos);

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

//                                Snackbar snackbar = Snackbar.make(rv_data, "Item successfully removed from the list.", Snackbar.LENGTH_LONG);
////                                snackbar.setActionTextColor(Color.YELLOW);
//                                snackbar.show();
                            }
                        }
                ));

            }
        };
        swipeHelper.attachToRecyclerView(rl_recyclerview);

        showFolders(userid);

        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    private void EditFolderApi(String userid, String folderId, String foldername, int pos,int folderKey) {

        if(Util.isConnected()){

            new EditFolderName(context, foldername, folderKey, userid, folderId,1).execute();


        }
        else {
            new EditFolderName(context, foldername, folderKey, userid, folderId,0).execute();
        }

    }

    public void delete_data_Folder(String userid, String folderId) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


        retrofit2.Call<ResponseBody> call = apiService.DeleteFolder(userid,folderId);
        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.e("ScriptUnderFolder url",new Gson().toJson(response.body()));

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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.rl_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                byte[] imageAsBytes = Base64.decode("iVBORw0KGgoAAAANSUhEUgAAAlUAAAC7CAYAAABM12tpAAAABmJLR0QA/wD/AP+gvaeTAAAgAElEQVR4nO2de1hVVfr4P1wi85IhAoqmkNlF8godLl4GNdPUtKwG65dht6lJ0ZnUKW2avKZOF8vQnKlMnSIoa0bFrMzCG3DwlhcwUUhRRBREFFAQOL8/jP31yOFcYJ/LxvfzPDyHs9e73vfd+137nLXevc5abnv27DEgCIIgCIIgNJiSkhLcne2EIAiCIAiClikpKSEnJwfP2gO33367M/0RBEEQBEHQJF999RWAZKoEQRAEQRDUQDpVgiAIgiAIKuBpWUQQBEEQBOH6Iz09HYDKykq8vLyorKykX79+9cpLp0oQBEEQBMEEOp0OgIMHD3L33XeTmZlpVl4e/wmCIAiCINTD+fPnOXv2LAaDgaqqKrOy0qkSVOWf//wnAQEB+Pv7s2bNGpvKMzIy8Pf3t5tv+/bto1WrVnbTLwiCIDQtMjMzyc7O5q677iI9PZ3WrVublZdO1XWCv78//v7++Pn50apVK+W9mp2YoqIi5s2bxw8//EBBQQGjR4+2qTw4OJiCggLV/FGTv/zlL9x22234+/szfPhwjh49alReXl7O888/T4cOHQgKCmLOnDk21S8qKuKPf/wjnTt3tqrj169fP+UnvNbYt7d+gEWLFhEcHIy/vz/BwcFs377doh1r/fvvf/9LaGgo7du3p127dgwdOpSdO3dard9S/czMTO6//378/f25/fbbef3116mpqQH+rzNee7907NiRP/7xj/z2229G5S+++KKiLzIy0qEdeGmf5vVf/Xnn7++Pj48P7du3t2jHWv+kfTZdAgIC6N27Nz4+PoSFhdG5c2ez8tKpuk4oKCigoKCAH3/80ei9mp2Y/Px8ampquOeeexpU7soEBQWRlpZGXl4ed955J88884xR+YwZMzhz5gxZWVmkpKTw9ddfs2rVKqvru7m58cADD/Duu+9a9CU5OZni4mIefvhhq+3bW//777/Pf/7zH1avXk1BQQGbN28mKCjIoi1r/QsJCWHdunXk5+dz4sQJBg8ezOOPP261fnP1DQYDY8eOpV+/fuTl5bFz50727dvHv/71LyMdtffL3r17ad26NU8//bRR+aFDh6iurubYsWOUl5db7ZsaSPs0r//qz7uCggJGjBhBdHS0RVvW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", Base64.DEFAULT);
//                Bitmap string =  BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
//                Log.e("decodedByte.........",string.toString());

                Intent intent = new Intent(context, Profile_Activity.class);
                context.startActivity(intent);
            }
        });


        holder.rl_allscripts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Navigation.class);
                intent.putExtra("OpenParticularFolder", "");
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });

        holder.rl_script.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((Navigation)context).closeDrawer();
                Util.flag=false;
                Intent intent = new Intent(context, Script_Toolbar.class);
                intent.putExtra("TAG", "1");
                shared_prefrencePrompster.setTag("1");
                context.startActivity(intent);
            }
        });

        holder.rl_recent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.rl_recent_script.setVisibility(View.VISIBLE);
            }
        });
        holder.rl_folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show = new Dialog(context);
                show.setContentView(R.layout.add_folder);
                show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                tv_okay = show.findViewById(R.id.tv_okay);
                tv_cancel = show.findViewById(R.id.tv_cancel);
                folder_name = show.findViewById(R.id.et_folder_name);

                folder_name.setRawInputType(InputType.TYPE_CLASS_TEXT);
                folder_name.setImeOptions(EditorInfo.IME_ACTION_DONE);
                folder_name.setRawInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);

                tv_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        show.dismiss();
                    }
                });

                tv_okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        show.dismiss();
                        foldername = folder_name.getText().toString();

                        addFolderapi(foldername,"new","");

                    }
                });

                show.show();

            }

        });

    }

    private void addFolderapi(String foldername,String newValue,String folder_primaryKey) {

        if (Util.isConnected()){
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            retrofit2.Call<Add_Folder_pojo> call = apiService.add_folder(userid, foldername);
            call.enqueue(new retrofit2.Callback<Add_Folder_pojo>() {
                @Override
                public void onResponse(Call<Add_Folder_pojo> call, Response<Add_Folder_pojo> response) {

                    Log.e("Add Script url", call.request().toString());

                    if (response.isSuccessful()) {

                        Log.w("Add Script response", new Gson().toJson(response));
                        if (response.body().getSuccess().toString().equals("1")) {
                            Log.e("Add Script response", new Gson().toJson(response));
//                            dialog_progress.dismiss();
//                        Toast.makeText(context, "Folder added successfully", Toast.LENGTH_SHORT).show();

                            if (newValue.equals("new")){
                                folderDB = new FolderDB(response.body().getData().getId(), response.body().getData().getUserId(),
                                        response.body().getData().getName(), response.body().getData().getCreatedAt(),1,1);

                                new SaveFolderAsyncTask(folderDB, context).execute();
                                demo.notifyDataSetChanged();

                            }
                            else{
                                new UpdateFolderAsyncTask(Integer.parseInt(folder_primaryKey), Integer.parseInt(response.body().getData().getId()) ,context).execute();
                                demo.notifyDataSetChanged();
                            }
//                            showFolders(userid);

                        } else {

//                            dialog_progress.dismiss();
                            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);

                            sweetAlertDialog.setTitleText(response.body().getMessage().toString());

                            sweetAlertDialog.show();
                            Button btn = (Button) sweetAlertDialog.findViewById(R.id.confirm_button);
                            btn.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));

                        }

                    }
                }

                @Override
                public void onFailure(Call<Add_Folder_pojo> call, Throwable t) {
//                    dialog_progress.dismiss();
                    Log.e("errror", String.valueOf(t));

                }
            });
        }
        else{

//            dialog_progress.dismiss();
            Date c = Calendar.getInstance().getTime();
            System.out.println("Current time => " + c);

            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
            String formattedDate = df.format(c);

            folderDB = new FolderDB("", userid,
                    foldername, formattedDate,0,1);

            new SaveFolderAsyncTask(folderDB, context).execute();




        }

    }

    private void showFolders(String useridd) {

        new GetfolderDataAsyncTask(context, useridd).execute();

    }

    @Override
    public int getItemCount() {
        return pojos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_script;
        RecyclerView rl_recyclerview, rv_recent_script;
        RelativeLayout rl_settings, rl_script, rl_folder, rl_allscripts, rl_recent, rl_recent_script;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_script = itemView.findViewById(R.id.tv_script);
            rl_settings = itemView.findViewById(R.id.rl_settings);
            rl_script = itemView.findViewById(R.id.rl_script);
            rl_folder = itemView.findViewById(R.id.rl_folder);
            rl_recycler = itemView.findViewById(R.id.rl_recycler);
            rl_recyclerview = itemView.findViewById(R.id.rl_recyclerview);
            rl_allscripts = itemView.findViewById(R.id.rl_allscripts);
            rl_recent = itemView.findViewById(R.id.rl_recent);
            rl_recent_script = itemView.findViewById(R.id.rl_recent_script);
            rv_recent_script = itemView.findViewById(R.id.rv_recent_script);
            swipe_container_folders = itemView.findViewById(R.id.swipe_container_folders);

        }

    }
    @Override
    public void onRefresh() {

        // Fetching data from server
//        loadFolderRecyclerViewData();
    }

//    private void loadFolderRecyclerViewData() {
//
//        swipe_container_folders.setRefreshing(false);
//
//        showFolders(userid);
//
//    }

    // Local Database for folders ..................

    private class GetfolderDataAsyncTask extends AsyncTask<Void, Void, List<FolderDB>> {

        //Prevent leak
        Context context;
//        String scriptId;

        Show_Folder_Datum show_folder_datum;
        String useridd;

        public GetfolderDataAsyncTask(Context context, String useridd) {
            this.context = context;
            this.useridd = useridd;
//            this.scriptId = scriptId;
        }

        @Override
        protected List<FolderDB> doInBackground(Void... params) {
            FolderDao folderDao = db.folderDao();
            return folderDao.loadAllFolders();
        }

        @Override
        protected void onPostExecute(List<FolderDB> list) {

            show_folder_data.clear();

            for (int i = 0 ; i<list.size(); i++){
                show_folder_datum = new Show_Folder_Datum();
                show_folder_datum.setId(String.valueOf(list.get(i).getFolder_Id()));
                show_folder_datum.setUserId(list.get(i).getUserId());
                show_folder_datum.setName(list.get(i).getName());
                show_folder_datum.setCreatedAt(list.get(i).getFoldercreatedAt());
                show_folder_datum.setFolder_primarykey(list.get(i).getFolder_key());
//                show_folder_datum.setModifiedAt(list.get(i).getModified_at());

                show_folder_data.add(show_folder_datum);
            }

            if (show_folder_data.size()>0){
                rl_recycler.setVisibility(View.VISIBLE);
                demo.notifyDataSetChanged();
            }

            else
            {
                //First Time Local for Folders

                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                retrofit2.Call<Show_Folder_pojo> call = apiService.show_folder(useridd);
                call.enqueue(new retrofit2.Callback<Show_Folder_pojo>() {
                    @Override
                    public void onResponse(Call<Show_Folder_pojo> call, Response<Show_Folder_pojo> response) {

                        Log.e("Add Script url", call.request().toString());

                        if (response.isSuccessful()) {

                            Log.w("Add Script response", new Gson().toJson(response));
                            if (response.body().getSuccess().toString().equals("1")) {
                                Log.e("Add Script response", new Gson().toJson(response));

                                if (response.body().getData().size() > 0) {
                                    rl_recycler.setVisibility(View.VISIBLE);
                                    show_folder_data.clear();
                                    show_folder_data.addAll(response.body().getData());

                                    for (int i = 0; i < show_folder_data.size(); i++) {

                                        folderDB = new FolderDB(show_folder_data.get(i).getId(), show_folder_data.get(i).getUserId(),
                                                show_folder_data.get(i).getName(), show_folder_data.get(i).getCreatedAt(),1,1);

                                        new SaveFolderAsyncTask(folderDB, context).execute();

                                    }

                                    demo.notifyDataSetChanged();
                                } else {
                                    rl_recycler.setVisibility(View.GONE);
                                }

                            } else {

                                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
                                sweetAlertDialog.setTitleText(response.body().getMessage().toString());
                                sweetAlertDialog.show();
                                Button btn = (Button) sweetAlertDialog.findViewById(R.id.confirm_button);
                                btn.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));

                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<Show_Folder_pojo> call, Throwable t) {

                        Log.e("errror", String.valueOf(t));

                    }
                });

            }

        }
    }

    private  class SaveFolderAsyncTask extends AsyncTask<Void, Void, Long> {

        //Prevent leak
        Context context;
        FolderDB folderDB;

        public SaveFolderAsyncTask(FolderDB folderDB, Context context) {
            this.folderDB = folderDB;
            this.context = context;
        }

        @Override
        protected Long doInBackground(Void... params) {
            FolderDao folderDao = db.folderDao();
            return folderDao.insertFolder(folderDB);
        }

        @Override
        protected void onPostExecute(Long integer) {
            Log.e("Check Success", integer.toString());

                showFolders(userid);

        }
    }


    private class UpdateFolderAsyncTask extends AsyncTask<Void, Void, Integer> {

        //Prevent leak
        Context context;
        int key;
        int folderId;

        public UpdateFolderAsyncTask(int key, int folderId ,Context context) {
            this.key = key;
            this.context = context;
            this.folderId = folderId;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            FolderDao folderDao = db.folderDao();
            return folderDao.updateFolderInternetFlag(1, folderId ,key);
        }

        @Override
        protected void onPostExecute(Integer integer) {

            Log.e("Check Success", integer.toString());
        }
    }

    public void callFalseMethod()
    {
        new GetFolderfalseDataAsyncTask(context).execute();
    }

    public class GetFolderfalseDataAsyncTask extends AsyncTask<Void, Void, List<FolderDB>> {

        //Prevent leak
        Context context;
        String scriptId;
        List<Script_Pojo> scriptList = new ArrayList<>();
        Script_Pojo script_pojo;
        int key;

        public GetFolderfalseDataAsyncTask(Context context) {
            this.context = context;
            this.scriptId = scriptId;
            this.key = key;
        }

        @Override
        protected List<FolderDB> doInBackground(Void... params) {
            FolderDao folderDao = db.folderDao();
            return folderDao.internetBased();
        }

        @Override
        protected void onPostExecute(List<FolderDB> list) {

            Log.e("ListResponse", new Gson().toJson(list));
            if (list.size()>0) {
                for (int i = 0; i < list.size(); i++) {

                    addFolderapi(list.get(i).getName(),"local",String.valueOf(list.get(i).getFolder_key()));

                }
            }
        }
    }

    public class EditFolderName extends AsyncTask<Void, Void, Integer> {

        Context context;
        String title;
        int key;
        String userid;
        String folderId;
        int editFlagFolder;

        public EditFolderName(Context navigation, String folderName, int primarykey, String userid, String folderId,int editFlagFolder) {

            this.context = navigation;
            this.key = primarykey;
            this.title = folderName;
            this.folderId = folderId;
            this.userid = userid;
            this.editFlagFolder = editFlagFolder;

        }

        @Override
        protected Integer doInBackground(Void... voids) {
            FolderDao folderDao = db.folderDao();
            return folderDao.updateFolderTitle(title, editFlagFolder, key);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            if (integer > 0) {
                Log.e("Success", "True");

                if (Util.isConnected()) {
                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                    retrofit2.Call<ResponseBody> call = apiService.editFolder(userid,folderId,title);
                    call.enqueue(new retrofit2.Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            Log.e("EditFolderApi Response",response.toString());

                            if (response.isSuccessful()) {
                                showFolders(userid);
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                            Log.e("EditFolderApierrror", String.valueOf(t));

                        }
                    });

                } else {

                    showFolders(userid);
                }

            } else {
                Log.e("Success", "False");
            }
        }

    }

    public void callFalseEditFagMethod()
    {
        new ListEditFlagFolderAsyncTask(context).execute();
    }

    public class ListEditFlagFolderAsyncTask extends AsyncTask<Void, Void, List<FolderDB>> {

        //Prevent leak
        Context context;


        public ListEditFlagFolderAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected List<FolderDB> doInBackground(Void... params) {
            FolderDao folderDao = db.folderDao();
            return folderDao.editFolderBased();
        }

        @Override
        protected void onPostExecute(List<FolderDB> list) {

            Log.e("Check Success", list.toString());

            for (int i = 0; i < list.size(); i++) {

                EditFolderApi(userid,list.get(i).getFolder_Id(),list.get(i).getName(),i,list.get(i).getFolder_key());
//                new EditFolderName(context, list.get(i).getName(), list.get(i).getFolder_key(), userid, list.get(i).getFolder_Id(),0).execute();

            }
        }
    }



    private class DeleteFolderAsyncTask extends AsyncTask<Void, Void, Integer>
    {
        FolderDB folderDB;
        Context context;
        int key;
        int positi;
        String folderId;
        String userid;

        DeleteFolderAsyncTask(int key, Context context, String userId, String folderId) {

            this.key = key;
            this.context = context;
            this.folderId = folderId;
            this.userid = userId;

        }

        @Override
        protected Integer doInBackground(Void... voids) {
            FolderDao folderDao = db.folderDao();
            return folderDao.delete(key);
        }

        @Override
        protected void onPostExecute(Integer integer) {

            Log.e("Check", integer + "");

            if (integer > 0) {
                Toast.makeText(context, "Delete Successfully", Toast.LENGTH_SHORT).show();

                if (Util.isConnected()) {

                    delete_data_Folder(userid,folderId);


                } else {
                    deleteListFolder.clear();
                    deleteListFolder.add(folderId);
                }


//                allscript_api(userid);
            }
// super.onPostExecute(integer);
        }
    }
}



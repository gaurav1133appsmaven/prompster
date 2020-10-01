package com.project.prompster_live.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.project.prompster_live.Activity.Navigation;
import com.project.prompster_live.Activity.Profile_Activity;
import com.project.prompster_live.Activity.Script_Toolbar;
import com.project.prompster_live.Api.ApiClient;
import com.project.prompster_live.Api.ApiInterface;
import com.project.prompster_live.Pojo.Add_Folder_pojo;
import com.project.prompster_live.Pojo.MenuPojo;
import com.project.prompster_live.Pojo.Pojo;
import com.project.prompster_live.Pojo.Show_Folder_Datum;
import com.project.prompster_live.Pojo.Show_Folder_pojo;
import com.project.prompster_live.R;
import com.project.prompster_live.Utils.RecyclerItemTouchHelper;
import com.project.prompster_live.Utils.Shared_PrefrencePrompster;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Response;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    Context context;
    List<MenuPojo> pojos=new ArrayList<>();
    Dialog show;
    EditText folder_name;
    TextView tv_setting,tv_okay,tv_cancel;
    String userid,title,desc,script_id,foldername;
    RecyclerView rl_recyclerview;
    RelativeLayout rl_recycler;
    Shared_PrefrencePrompster shared_prefrencePrompster;


    MenuPojo menuPojo;
    Demo demo;
    Pojo demopojo;
    List<Pojo>pojo=new ArrayList<>();
    List<Show_Folder_Datum>show_folder_data=new ArrayList<Show_Folder_Datum>();
    public MenuAdapter(Navigation navigation, List<MenuPojo> pojos) {
        this.context=navigation;
        this.pojos=pojos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_navigation, parent, false);
        rl_recyclerview = v.findViewById(R.id.rl_recyclerview);

        shared_prefrencePrompster = Shared_PrefrencePrompster.getInstance(context);
        userid =  shared_prefrencePrompster.getUserid().toString();



        rl_recyclerview.setLayoutManager(new LinearLayoutManager(context));
        demo = new Demo(context,show_folder_data);
        rl_recyclerview.setAdapter(demo);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rl_recyclerview);

        showFolders(userid);



        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
holder.rl_settings.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent  intent=new Intent(context, Profile_Activity.class);
        context.startActivity(intent);
        ((Activity)context).finish();
    }
});


        holder.rl_script.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent=new Intent(context, Script_Toolbar.class);
                intent.putExtra("TAG","1");
                shared_prefrencePrompster.setTag("1");

                context.startActivity(intent);
            }
        });
        holder.rl_folderss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show = new Dialog(context);
//
//
            show.setContentView(R.layout.add_folder);
            show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

           tv_okay = show.findViewById(R.id.tv_okay);
            tv_cancel = show.findViewById(R.id.tv_cancel);
            folder_name = show.findViewById(R.id.et_folder_name);

            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    show.dismiss();
                }
            });

            tv_okay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    foldername = folder_name.getText().toString();
                    show.dismiss();

                    addFolderapi(foldername);



                }
            });

            show.show();

//                Toast.makeText(context, "helloooo", Toast.LENGTH_SHORT).show();


        }

        });


    }

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

//                        Toast.makeText(context, "Folder added successfully", Toast.LENGTH_SHORT).show();
                        showFolders(userid);


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
            public void onFailure(Call<Add_Folder_pojo> call, Throwable t) {

                Log.e("errror", String.valueOf(t));

            }
        });

    }

    private void showFolders(String useridd) {

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

                        if (response.body().getData().size()>0){
                            rl_recycler.setVisibility(View.VISIBLE);
//                            Toast.makeText(context, "Folder added successfully", Toast.LENGTH_SHORT).show();
                            show_folder_data.clear();
                            show_folder_data.addAll(response.body().getData());
                            demo.notifyDataSetChanged();
                        }
                        else {
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

    @Override
    public int getItemCount() {
        return pojos.size();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        if (viewHolder instanceof Demo.ViewHolder) {
            // get the removed item name to display it in snack bar
            String name = show_folder_data.get(viewHolder.getAdapterPosition()).getName();

            // backup of removed item for undo purpose
            final  Show_Folder_Datum deletedItem = show_folder_data.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            demo.removeItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
//            Snackbar snackbar = Snackbar
//                    .make(coordinatorLayout, name + " removed from cart!", Snackbar.LENGTH_LONG);
//            snackbar.setAction("UNDO", new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    // undo is selected, restore the deleted item
//                    demo.restoreItem(deletedItem, deletedIndex);
//                }
//            });
//            snackbar.setActionTextColor(Color.YELLOW);
//            snackbar.show();
        }


    }

    public class ViewHolder  extends RecyclerView.ViewHolder{
        TextView tv_akshay;
        RecyclerView rl_recyclerview;
        RelativeLayout rl_settings,rl_script,rl_folderss;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_akshay=itemView.findViewById(R.id.tv_akshay);
            rl_settings=itemView.findViewById(R.id.rl_settings);
            rl_script=itemView.findViewById(R.id.rl_script);
            rl_folderss=itemView.findViewById(R.id.rl_folderss);
            rl_recycler=itemView.findViewById(R.id.rl_recycler);
            rl_recyclerview=itemView.findViewById(R.id.rl_recyclerview);
        }
    }
}

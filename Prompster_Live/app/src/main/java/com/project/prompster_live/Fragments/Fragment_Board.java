package com.project.prompster_live.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.project.prompster_live.Activity.Login_Screen;
import com.project.prompster_live.Activity.Reset_Password;
import com.project.prompster_live.Activity.Script_Toolbar;
import com.project.prompster_live.Adapter.StoryBoard_Adapter;
import com.project.prompster_live.Api.ApiClient;
import com.project.prompster_live.Api.ApiInterface;
import com.project.prompster_live.Interface.Recycler_item_click;
import com.project.prompster_live.Pojo.AddStoryBoard_pojo;
import com.project.prompster_live.Pojo.Forgotpassword_Pojo;
import com.project.prompster_live.Pojo.ShowStoryBoard_Datum;
import com.project.prompster_live.Pojo.ShowStoryBoard_pojo;
import com.project.prompster_live.R;
import com.project.prompster_live.Utils.Shared_PrefrencePrompster;

import java.util.ArrayList;
import java.util.List;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Response;

public class Fragment_Board extends Fragment {
    RecyclerView rv_storyboard;
    StoryBoard_Adapter storyBoard_adapter;
    Shared_PrefrencePrompster shared_prefrencePrompster;
    String userid, script_id,desc,title;
    ImageView iv_add_story;
    private Recycler_item_click mlistener;
    ACProgressFlower dialog_progress;
    List<ShowStoryBoard_Datum> storyboard_list = new ArrayList<ShowStoryBoard_Datum>();
TextView tv_title;
    Dialog dialog;
    TextView tv_ok;
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
        Bundle arguments = getArguments();
        script_id = arguments.getString("script_id");
        title = arguments.getString("script_title");
        rv_storyboard = view.findViewById(R.id.rv_storyboard);
        iv_add_story = view.findViewById(R.id.iv_add_story);
        rv_storyboard.setLayoutManager(new LinearLayoutManager(getActivity()));
        storyBoard_adapter = new StoryBoard_Adapter(getContext(), storyboard_list);
        rv_storyboard.setAdapter(storyBoard_adapter);
        tv_title=view.findViewById(R.id.tv_title);
        showstoryBoardApi(userid, script_id);

        try{
            if (title.equals("")||title.equals(null)){
                tv_title.setHint("Script1");
            }
            else
            {
                tv_title.setText(title);
            }




        }
        catch (NullPointerException e){

        }

        iv_add_story.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_progress= new ACProgressFlower.Builder(getActivity())
                        .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                        .themeColor(Color.WHITE)
                        .text("Please wait...")
                        .fadeColor(Color.DKGRAY).build();
                dialog_progress.show();
                addStoryBoardapi();

            }
        });


        return view;
    }

    private void addStoryBoardapi() {


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        retrofit2.Call<AddStoryBoard_pojo> call = apiService.addStoryBoard(userid, script_id, "Introduction", "Start writing here", "20");
        call.enqueue(new retrofit2.Callback<AddStoryBoard_pojo>() {
            @Override
            public void onResponse(Call<AddStoryBoard_pojo> call, Response<AddStoryBoard_pojo> response) {
                try {


                    if (response.isSuccessful()) {

                        Log.w("response", new Gson().toJson(response));
                        if (response.body().getSuccess().toString().equals("1")) {
//                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            dialog_progress.dismiss();

                            showstoryBoardApi(userid, script_id);

                        } else {
                            dialog_progress.dismiss();
//                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<AddStoryBoard_pojo> call, Throwable t) {

                Log.e("Filure", String.valueOf(t));
//                Toast.makeText(getContext(), "Error in api", Toast.LENGTH_SHORT).show();

//                dialog = new Dialog(getActivity());
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

    private void showstoryBoardApi(String userid, String script_id) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        retrofit2.Call<ShowStoryBoard_pojo> call = apiService.showStoryBoard(userid, script_id);
        call.enqueue(new retrofit2.Callback<ShowStoryBoard_pojo>() {
            @Override
            public void onResponse(Call<ShowStoryBoard_pojo> call, Response<ShowStoryBoard_pojo> response) {
                try {


                    if (response.isSuccessful()) {

                        Log.w("response", new Gson().toJson(response));
                        if (response.body().getSuccess().toString().equals("1")) {
//                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            storyboard_list.clear();
                            storyboard_list.addAll(response.body().getData());
                            storyBoard_adapter.notifyDataSetChanged();


                        } else {

                            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE);
                            sweetAlertDialog.setTitleText(response.body().getMessage().toString());
                            sweetAlertDialog.show();
                            Button btn = (Button) sweetAlertDialog.findViewById(R.id.confirm_button);
                            btn.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.colorPrimary));

//                            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<ShowStoryBoard_pojo> call, Throwable t) {

                Log.e("Filure", String.valueOf(t));
//                Toast.makeText(getContext(), "Error in api", Toast.LENGTH_SHORT).show();
            }
        });


    }
}

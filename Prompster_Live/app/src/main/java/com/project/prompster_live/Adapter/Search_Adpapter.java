package com.project.prompster_live.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.prompster_live.Activity.Navigation;
import com.project.prompster_live.Activity.Script_Toolbar;
import com.project.prompster_live.Interface.Recycler_item_click;
import com.project.prompster_live.Pojo.Script;
import com.project.prompster_live.Pojo.Script_Pojo;
import com.project.prompster_live.Pojo.Search_Pojo;
import com.project.prompster_live.R;

import java.util.ArrayList;
import java.util.List;


public class Search_Adpapter extends RecyclerView.Adapter<Search_Adpapter.ViewHolder> {
    Context context;
    List<List<Script_Pojo>> srch = new ArrayList<>();
    String title,description;
    private Recycler_item_click mlistener;

    public Search_Adpapter(Navigation main3Activity, List<List<Script_Pojo>> srch, Recycler_item_click mlistener) {
        this.context = main3Activity;
        this.srch = srch;
        this.mlistener =mlistener;
    }

    @NonNull
    @Override
    public Search_Adpapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adoter_layout, parent, false);


        // set the view's size, margins, paddings and layout parameters
        return new Search_Adpapter.ViewHolder(v,mlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull Search_Adpapter.ViewHolder holder, int position) {

//        for (int i=0; i<srch.get(position).size(); i++) {
        try {
            title = srch.get(0).get(position).getScrTitle().toString();
            description = srch.get(0).get(position).getScrText().toString();
            holder.tv_welcome.setText(title);
        }
        catch (IndexOutOfBoundsException e){

        }
//        }

    }


    @Override
    public int getItemCount() {

        if (srch.size() == 0) {
            return srch.size();
        } else {
            return srch.get(0).size();
        }

    }

//    public  class myholder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        TextView tv_welcome;
//        RelativeLayout rl_data;
//        public myholder(@NonNull View itemView) {
//            super(itemView);
//            tv_welcome=itemView.findViewById(R.id.tv_welcome);
//            rl_data = itemView.findViewById(R.id.rl_data);
//            itemView.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View view) {
//            switch (view.getId()){
//                case R.id.rl_data:
//
//
//
//                    break;
//            }
//
//        }
//    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_welcome;
        private Recycler_item_click mlistener;
        RelativeLayout rl_dataa;
        public ViewHolder(@NonNull View itemView, Recycler_item_click mlistener) {
            super(itemView);
            this.mlistener = mlistener;
            tv_welcome=itemView.findViewById(R.id.tv_welcome);
            rl_dataa = itemView.findViewById(R.id.rl_dataa);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            mlistener.onClick(view, getAdapterPosition());
        }
    }
    }

package com.project.prompster_live.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.prompster_live.Activity.Script_Toolbar;
import com.project.prompster_live.Interface.Recycler_item_click;
import com.project.prompster_live.R;
import com.project.prompster_live.Utils.Util;

import java.util.ArrayList;
import java.util.List;

public class Recent_Fonts extends RecyclerView.Adapter<Recent_Fonts.ViewHolder> {

    Context context;
    String type;
    List<String> data=new ArrayList<>();
    private Recycler_item_click mlistener;

    public Recent_Fonts(Context context, List<String> recent_list, Recycler_item_click mlistener,String type ) {

        this.context = context;
        this.data = recent_list;
        this.mlistener = mlistener;
        this.type = type;

    }

    @NonNull
    @Override
    public Recent_Fonts.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_fonts, parent, false);


        // set the view's size, margins, paddings and layout parameters
        return new Recent_Fonts.ViewHolder(v,mlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull final Recent_Fonts.ViewHolder holder, final int position) {

        if (type.equals("all")){
            holder.iv_image.setVisibility(View.INVISIBLE);
        }
        else {
            if (position == 0){
                holder.iv_image.setVisibility(View.VISIBLE);
            }
            else {
                holder.iv_image.setVisibility(View.INVISIBLE);
            }

        }

        Log.e("Data",data.get(position));
        holder.tv_data_font.setText(data.get(position));

        holder.tv_data_font.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("all")){
                    mlistener.onClick(v, position,type);
                }
                else {
                    mlistener.onClick(v, position,type);
                }
            }
        });

        holder.iv_image_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    ((Script_Toolbar)context).visibleFontStyle(data.get(position),position);
            }
        });
    }

    @Override
    public int getItemCount() {
      return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_data_font;
        ImageView iv_image,iv_image_info;
        private Recycler_item_click mlistener;

        public ViewHolder(@NonNull View itemView, Recycler_item_click mlistener) {
            super(itemView);
            this.mlistener = mlistener;
            tv_data_font=itemView.findViewById(R.id.tv_data_font);
            iv_image=itemView.findViewById(R.id.iv_image);
            iv_image_info=itemView.findViewById(R.id.iv_image_info);

        }


    }

}

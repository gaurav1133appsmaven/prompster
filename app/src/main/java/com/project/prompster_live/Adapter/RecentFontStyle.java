package com.project.prompster_live.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
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
import com.project.prompster_live.Utils.Global_Constants;

import java.util.ArrayList;
import java.util.List;

public class RecentFontStyle extends RecyclerView.Adapter<RecentFontStyle.ViewHolder> {

    Activity context;
    String type;
    List<String> fontListStyle = new ArrayList<>();
    private Recycler_item_click mlistener;
    Global_Constants global_constants;
    int pos;



    public RecentFontStyle(Activity context, List<String> recent_list, Recycler_item_click mlistener, String type ,int pos) {

        this.context = context;
        this.fontListStyle = recent_list;
        this.mlistener = mlistener;
        this.type = type;
        this.pos = pos;

    }

    @NonNull
    @Override
    public RecentFontStyle.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_font_style, parent, false);

        global_constants = new Global_Constants();
        // set the view's size, margins, paddings and layout parameters
        return new RecentFontStyle.ViewHolder(v,mlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecentFontStyle.ViewHolder holder, final int position) {

        if (position<3) {
            Typeface custom_font = Typeface.createFromAsset(context.getAssets(), Global_Constants.arr[pos][position]);
            holder.tv_regular.setTypeface(custom_font);
        }
//        }
//        else if (position == 1){
//            holder.tv_regular.setTypeface(global_constants.arial_bold(context));
//        }
//        else if (position == 2){
//            holder.tv_regular.setTypeface(global_constants.arial_bold_italic(context));
//        }
//
//        else if (position == 3){
//            holder.tv_regular.setTypeface(global_constants.arial_italic(context));
//        }
//
//        else if (position == 4){
//            holder.tv_regular.setTypeface(global_constants.courier_regular(context));
//        }
//
//        else if (position == 5){
//            holder.tv_regular.setTypeface(global_constants.courier_bold(context));
//        }
//
//        else if (position == 6){
//            holder.tv_regular.setTypeface(global_constants.courier_bold_italic(context));
//        }
//
//        else if (position == 7){
//            holder.tv_regular.setTypeface(global_constants.courier_new_regular(context));
//        }
//
//        else if (position == 8){
//            holder.tv_regular.setTypeface(global_constants.georgia_regular(context));
//        }
//
//        else if (position == 9){
//            holder.tv_regular.setTypeface(global_constants.georgia_bold(context));
//        }
//
//        else if (position == 10){
//            holder.tv_regular.setTypeface(global_constants.georgia_bold_italic(context));
//        }
//
//        else if (position == 11){
//            holder.tv_regular.setTypeface(global_constants.gill_sans_bold(context));
//        }

        holder.tv_regular.setText(fontListStyle.get(position));
        holder.tv_regular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position<3) {
                    ((Script_Toolbar) context).changecolor(Global_Constants.arr[pos][position]);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return fontListStyle.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_regular;
        ImageView iv_regular;
        private Recycler_item_click mlistener;

        public ViewHolder(@NonNull View itemView, Recycler_item_click mlistener) {
            super(itemView);
            this.mlistener = mlistener;
            tv_regular=itemView.findViewById(R.id.tv_regular);
            iv_regular=itemView.findViewById(R.id.iv_regular);

        }


    }

}

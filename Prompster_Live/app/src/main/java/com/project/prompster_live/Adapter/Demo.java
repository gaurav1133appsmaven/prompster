package com.project.prompster_live.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.prompster_live.Pojo.ShowStoryBoard_Datum;
import com.project.prompster_live.Pojo.Show_Folder_Datum;
import com.project.prompster_live.R;

import java.util.ArrayList;
import java.util.List;

public class Demo extends RecyclerView.Adapter<Demo.ViewHolder> {
    Context context;
    List<Show_Folder_Datum> show_folder_datum = new ArrayList<>();
    String fldersName;

    public Demo(Context context, List<Show_Folder_Datum> show_folder_data) {
        this.context= context;
        this.show_folder_datum= show_folder_data;
    }


    @NonNull
    @Override
    public Demo.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.demo, parent, false);


        // set the view's size, margins, paddings and layout parameters
        return new Demo.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Demo.ViewHolder holder, int position) {
        try {
            fldersName = show_folder_datum.get(position).getName().toString();
            holder.tv_folders.setText(fldersName);
        }
        catch (IndexOutOfBoundsException e){

        }

    }

    @Override
    public int getItemCount() {

        if (show_folder_datum.size()>0){
            return show_folder_datum.size();
        }

        else {
            return 0;
        }
    }
    public void removeItem(int position) {
        show_folder_datum.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Show_Folder_Datum item, int position) {
        show_folder_datum.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }


    public  class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_folders;
        public  RelativeLayout viewForeground;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

          tv_folders = itemView.findViewById(R.id.tv_folders);
            viewForeground = itemView.findViewById(R.id.viewForeground);


        }
    }
}

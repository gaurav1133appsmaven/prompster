package com.scriptively.app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.scriptively.app.Activity.Navigation;
import com.scriptively.app.Pojo.Show_Folder_Datum;
import com.scriptively.app.R;

import java.util.ArrayList;
import java.util.List;


// side menu folder adapter
public class Demo extends RecyclerView.Adapter<Demo.ViewHolder> {
    Context context;
    List<Show_Folder_Datum> show_folder_datum = new ArrayList<>();
    String fldersName;
    String title;
    public static List list = new ArrayList();
    public static String move_folderid;


    public Demo(Context context, List<Show_Folder_Datum> show_folder_data) {
        this.context = context;
        this.show_folder_datum = show_folder_data;
    }


    @NonNull
    @Override
    public Demo.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.demo, parent, false);

        // set the view's size, margins, paddings and layout parameters
        return new Demo.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final Demo.ViewHolder holder, final int position) {
        try {
//            fldersName = show_folder_datum.get(position).getName().toString();

            if (show_folder_datum.get(position).getName().length() > 15) {
                fldersName = show_folder_datum.get(position).getName().substring(0, 15) + "....";
            } else
                fldersName = show_folder_datum.get(position).getName();

            holder.tv_folders.setText(fldersName);


//            holder.tv_folders.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (((Navigation) context).check_move) {
//                        holder.tv_folders.setClickable(false);
//                        holder.rel_tick.setClickable(true);
//                    }
//                    else {
//                        holder.rel_tick.setClickable(false);
//                        Intent intent = new Intent(context, Navigation.class);
//                        intent.putExtra("scrTitle_foldertitle", show_folder_datum.get(position).getName());
//                        intent.putExtra("FolderId", show_folder_datum.get(position).getId());
//
//                        intent.putExtra("TAG", "1");
//                        intent.putExtra("OpenParticularFolder", "1");
//                        context.startActivity(intent);
//                    }
//
//                }
//            });


            holder.rel_tick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((Navigation) context).check_move) {
                        holder.tv_folders.setClickable(false);
                        holder.rel_tick.setClickable(true);
                        if (holder.iv_tick.getVisibility() == View.VISIBLE) {
                            list.remove(show_folder_datum.get(position).getId());
                            holder.iv_tick.setVisibility(View.GONE);
                        } else {
                            list.add(show_folder_datum.get(position).getId());
                            holder.iv_tick.setVisibility(View.VISIBLE);
                            ((Navigation) context).tv_move.setVisibility(View.GONE);
                            ((Navigation) context).tv_done.setVisibility(View.VISIBLE);

                            move_folderid = show_folder_datum.get(position).getId();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    holder.iv_tick.setVisibility(View.GONE);
                                }
                            }, 5000);
                        }
                    }
                    else {
                        holder.rel_tick.setClickable(false);
                        Intent intent = new Intent(context, Navigation.class);
                        intent.putExtra("scrTitle_foldertitle", show_folder_datum.get(position).getName());
                        intent.putExtra("FolderId", show_folder_datum.get(position).getId());

                        intent.putExtra("TAG", "1");
                        intent.putExtra("OpenParticularFolder", "1");
                        context.startActivity(intent);
                    }
                }
            });


        } catch (IndexOutOfBoundsException e) {

        }


    }

    @Override
    public int getItemCount() {

        if (show_folder_datum.size() > 0) {
            return show_folder_datum.size();
        } else {
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


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_folders;
        public RelativeLayout viewForeground;
        RelativeLayout rel_tick;
        public ImageView iv_tick;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_folders = itemView.findViewById(R.id.tv_folders);
            viewForeground = itemView.findViewById(R.id.viewForeground);
            rel_tick = itemView.findViewById(R.id.rel_tick);
            iv_tick = itemView.findViewById(R.id.iv_tick);

        }

    }
}


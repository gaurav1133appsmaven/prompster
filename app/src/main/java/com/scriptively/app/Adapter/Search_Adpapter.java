package com.scriptively.app.Adapter;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.scriptively.app.Activity.Navigation;
import com.scriptively.app.Interface.Recycler_item_click;
import com.scriptively.app.Pojo.ScriptUnderFolderDatum;
import com.scriptively.app.Pojo.Script_Pojo;
import com.scriptively.app.Pojo.SingleFolderDetailDatum;
import com.scriptively.app.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

//home screen scripts adapter
public class Search_Adpapter extends RecyclerView.Adapter<Search_Adpapter.ViewHolder> {
    Context context;
    List<Script_Pojo> srch;
    List<Script_Pojo> list=new ArrayList<>();
    String title, description, time, size;
    private Recycler_item_click mlistener;
    int videoColumnIndex, kb_size;

    static boolean select = false;
    static boolean selectAll = false;
    Navigation navigation;
   public static List list_script = new ArrayList();
    List<ScriptUnderFolderDatum> scriptUnderFolderDatumList;
    List<ScriptUnderFolderDatum> listScript=new ArrayList<>();
    List<SingleFolderDetailDatum> folder_data = new ArrayList<>();
    int i;
    public Search_Adpapter(){}

    public Search_Adpapter(Navigation navigation, List<ScriptUnderFolderDatum> scriptUnderFolderData, Recycler_item_click mlistener, boolean b, boolean b1,int y) {
        this.context = navigation;
        this.scriptUnderFolderDatumList = scriptUnderFolderData;
        this.listScript.addAll(scriptUnderFolderData);
        this.mlistener = mlistener;
        this.select = select;
        this.selectAll = selectAll;
//        navigation = new Navigation();

    }

    public Search_Adpapter(Navigation main3Activity, List<Script_Pojo> srch, Recycler_item_click mlistener,boolean select,boolean selectAll) {
        this.context = main3Activity;
        this.srch = srch;
        this.list.addAll(srch);
        this.mlistener = mlistener;
        this.select = select;
        this.selectAll = selectAll;
        navigation = new Navigation();

    }

    public Search_Adpapter(Navigation navigation, List<SingleFolderDetailDatum> folder_data, Recycler_item_click mlistener) {

        this.context = navigation;
        this.folder_data = folder_data;
    }


    @NonNull
    @Override
    public Search_Adpapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adoter_layout, parent, false);


        // set the view's size, margins, paddings and layout parameters
        return new Search_Adpapter.ViewHolder(v, mlistener);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull final Search_Adpapter.ViewHolder holder, final int position) {

//        for (int i=0; i<srch.get(position).size(); i++) {
        try {
            title = srch.get(position).getScrTitle().toString();
            description = srch.get(position).getScrText().toString();
            time = srch.get(position).getCreatedAt();

            StringTokenizer tk = new StringTokenizer(time);

            String date = tk.nextToken();  // <---  yyyy-mm-dd
            String time = tk.nextToken();

            SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yy");

            Date datenew=sdf.parse(date);

            sdf=new SimpleDateFormat("MM/dd/yy");

            holder.tv_time.setText(sdf.format(datenew));
//            System.out.println(sdf.format(date));


            if (title.equals("") || title.equals(null)) {

            }
            else {

                if (title.length() > 40)
                    holder.tv_welcome.setText(title.substring(0, 35) + "...");
                else {
                    holder.tv_welcome.setText(title);
                }

//                holder.tv_welcome.setText(title);
            }



            kb_size = description.getBytes().length;

            double m = kb_size / 1024;
            double n = 1;

            Log.e("MB_Value",m+" "+n);

            if (m >= n) {

                 i = Math.toIntExact(Math.round(m));

                holder.tv_size.setText(" " + "-" + " " + i + "KB");
            } else {
                holder.tv_size.setText(" " + "-" + " " + kb_size + "KB");
            }


            holder.inactive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Navigation)context).changeValue();
                    holder.inactive.setVisibility(View.GONE);
                    holder.active.setVisibility(View.VISIBLE);
                    list_script.add(srch.get(position).getId());
                }
            });
            holder.active.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.active.setVisibility(View.GONE);
                    holder.inactive.setVisibility(View.VISIBLE);
                    list_script.remove(srch.get(position).getId());
                }
            });

        } catch (IndexOutOfBoundsException | ParseException e) {

        }




    }


    @Override
    public int getItemCount() {

//        if (srch.size() == 0) {
            return srch.size();
//        } else {
//            return srch.get(0).size();
//        }

    }



    public void filter(String s) {
        srch.clear();
        if (s.toString().length() == 0) {
            srch.addAll(list);
        } else {

            for (Script_Pojo wp : list) {
                if (wp.getScrTitle().toLowerCase().contains(s.toString().toLowerCase())) {
                    srch.add(wp);
                }
            }
        } notifyDataSetChanged();

    }



    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_welcome, tv_time, tv_size;
        private Recycler_item_click mlistener;
        RelativeLayout rl_dataa;
        public ImageView inactive,active;

        public ViewHolder(@NonNull View itemView, Recycler_item_click mlistener) {
            super(itemView);
            this.mlistener = mlistener;
            tv_welcome = itemView.findViewById(R.id.tv_welcome);
            rl_dataa = itemView.findViewById(R.id.rl_dataa);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_size = itemView.findViewById(R.id.tv_size);
            inactive = itemView.findViewById(R.id.inactive);
            active = itemView.findViewById(R.id.active);

            if (select) {

                if (selectAll)
                    active.setVisibility(View.VISIBLE);
                else
                    inactive.setVisibility(View.VISIBLE);
            }
            else
                inactive.setVisibility(View.GONE);


            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {


            if (select) {
                mlistener.onClick(view, getAdapterPosition(), "select");
            }
            else {
                mlistener.onClick(view, getAdapterPosition(), "");
            }

        }
    }
}

package com.scriptively.app.Adapter;

import android.content.Context;
import android.os.Build;
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
import com.scriptively.app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ScriptsInFolder extends RecyclerView.Adapter<ScriptsInFolder.MyViewHolder>{

    Context context;
    List<ScriptUnderFolderDatum> scriptUnderFolderDatumList;
    List<ScriptUnderFolderDatum> listScript=new ArrayList<>();
    String title, description, time, size;
    private Recycler_item_click mlistener;
    int videoColumnIndex, kb_size;
    public ImageView inactive;
    boolean select = false;
    boolean selectAll = false;
    Navigation navigation;

    public ScriptsInFolder(Navigation navigation, List<ScriptUnderFolderDatum> scriptUnderFolderData, Recycler_item_click mlistener, boolean b, boolean b1) {
        this.context = navigation;
        this.scriptUnderFolderDatumList = scriptUnderFolderData;
        this.listScript.addAll(scriptUnderFolderData);
        this.mlistener = mlistener;
        this.select = select;
        this.selectAll = selectAll;
//        navigation = new Navigation();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adoter_layout, parent, false);

        inactive = v.findViewById(R.id.inactive);
        // set the view's size, margins, paddings and layout parameters
        return new MyViewHolder(v, mlistener);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
//        try {
            title = scriptUnderFolderDatumList.get(position).getScrTitle().toString();
            description = scriptUnderFolderDatumList.get(position).getScrText().toString();
            time = scriptUnderFolderDatumList.get(position).getCreatedAt();

            StringTokenizer tk = new StringTokenizer(time);

            String date = tk.nextToken();  // <---  yyyy-mm-dd
            String time = tk.nextToken();


            if (title.equals("") || title.equals(null)) {

            } else {
                holder.tv_welcome.setText(title);
            }
            holder.tv_time.setText(date);


            kb_size = description.getBytes().length;

            double m = kb_size / 1024;

            if (m > 1) {

                int i = Math.toIntExact(Math.round(m));

                holder.tv_size.setText(" " + "-" + " " + i + "mb");
            } else {
                holder.tv_size.setText(" " + "-" + " " + kb_size + "kb");
            }



//        } catch (IndexOutOfBoundsException e) {
//
//        }
    }

    @Override
    public int getItemCount() {

        if (scriptUnderFolderDatumList.size()>0){
            return scriptUnderFolderDatumList.size();

        }
        else {
            return 0;
        }
    }
    public void filter(String s) {
        scriptUnderFolderDatumList.clear();
        if (s.toString().length() == 0) {
            scriptUnderFolderDatumList.addAll(listScript);
        } else {

            for (ScriptUnderFolderDatum wp : listScript) {
                if (wp.getScrTitle().toLowerCase().contains(s.toString().toLowerCase())) {
                    scriptUnderFolderDatumList.add(wp);
                }
            }
        } notifyDataSetChanged();

    }

    public class
    MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        TextView tv_welcome, tv_time, tv_size;
        private Recycler_item_click mlistener;
        RelativeLayout rl_dataa;
        ImageView inactive,active;

        public MyViewHolder(@NonNull View itemView,Recycler_item_click mlistener) {
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


            mlistener.onClick(view, getAdapterPosition(), "");
        }
    }
}

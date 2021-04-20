package com.project.prompster_live.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.prompster_live.Activity.Script_Toolbar;
import com.project.prompster_live.Pojo.Recent_data;
import com.project.prompster_live.Pojo.Script_Pojo;
import com.project.prompster_live.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Recent_Adapter extends RecyclerView.Adapter<Recent_Adapter.ViewHolder> {

Context context;
String title,text;
List<Script_Pojo>  data=new ArrayList<>();

    public Recent_Adapter(Context context, List<Script_Pojo> recent_list) {

        this.context=context;
        this.data=recent_list;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_adapter, parent, false);


        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        if (data.get(position).getScrTitle().length() > 15) {
            title = data.get(position).getScrTitle().substring(0, 15) + "....";
        } else
            title = data.get(position).getScrTitle();
        holder.tv_data.setText(title);
        holder.tv_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Script_Toolbar.class);
                intent.putExtra("scrTitle_recent",data.get(position).getScrTitle());
                intent.putExtra("scrText_recent",data.get(position).getScrText());
                intent.putExtra("TAG","4");
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_data;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_data=itemView.findViewById(R.id.tv_data);
        }
    }
}

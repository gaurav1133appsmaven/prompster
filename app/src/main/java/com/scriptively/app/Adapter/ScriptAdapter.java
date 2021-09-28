package com.scriptively.app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.scriptively.app.R;

public class ScriptAdapter extends RecyclerView.Adapter<ScriptAdapter.ScriptViewHolder> {
    String[] list;
    Context context;
   public ScriptAdapter(Context context, String[] list)
    {
        this.list=list;
        this.context=context;

    }


    @NonNull
    @Override
    public ScriptViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View v=  LayoutInflater.from(parent.getContext()).inflate(R.layout.script_item,parent,false);

        return new ScriptViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ScriptViewHolder holder, int position) {
       holder.tv.setText(list[position]);

    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    public class ScriptViewHolder extends RecyclerView.ViewHolder
    {
      public  TextView tv;


        public ScriptViewHolder(@NonNull View itemView) {

            super(itemView);
            tv= itemView.findViewById(R.id.textScript);
        }
    }
}

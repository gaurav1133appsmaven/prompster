package com.scriptively.app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.scriptively.app.Activity.GoogleDriveFiles;
import com.scriptively.app.Interface.Recycler_item_click;
import com.scriptively.app.R;

import java.util.ArrayList;
import java.util.List;

public class GoogleDriveFilesAdapter  extends RecyclerView.Adapter<GoogleDriveFilesAdapter.ViewHolder>{

    Context context;
    List<String> mFiles = new ArrayList<>();
    Recycler_item_click recycler_item_click;
    public GoogleDriveFilesAdapter(GoogleDriveFiles googleDriveFiles, List<String> mFiles, Recycler_item_click mListener) {

        this.context = googleDriveFiles;
        this.mFiles = mFiles;
        this.recycler_item_click = mListener;
    }

    @NonNull
    @Override
    public GoogleDriveFilesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.files_item, parent, false);


        // set the view's size, margins, paddings and layout parameters
        return new GoogleDriveFilesAdapter.ViewHolder(v,recycler_item_click);
    }

    @Override
    public void onBindViewHolder(@NonNull GoogleDriveFilesAdapter.ViewHolder holder, int position) {

        holder.text.setText(mFiles.get(position));
    }

    @Override
    public int getItemCount() {
        return mFiles == null ? 0 : mFiles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text,tv_import;
        Recycler_item_click recycler_item_click;
        public ViewHolder(@NonNull View itemView, Recycler_item_click recycler_item_click) {
            super(itemView);
            this.recycler_item_click = recycler_item_click;
            text = itemView.findViewById(R.id.text);
            tv_import = itemView.findViewById(R.id.tv_import);
            tv_import.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recycler_item_click.onClick(v,getAdapterPosition(),"");
                }
            });

        }
    }
}

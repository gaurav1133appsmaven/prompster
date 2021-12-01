package com.scriptively.app.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.scriptively.app.Activity.DropBox;
import com.scriptively.app.R;

public class DropboxAdapter extends RecyclerView.Adapter<DropboxAdapter.ViewHolder> {


    public DropboxAdapter(DropBox dropBox) {



    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dropbox_adapter, parent, false);

        // set the view's size, margins, paddings and layout parameters
        return new DropboxAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

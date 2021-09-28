package com.scriptively.app.Adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.scriptively.app.Interface.Recycler_item_click;
import com.scriptively.app.R;

import java.util.ArrayList;
import java.util.List;

//dictionary data adapter
public class AZ_Adapter extends RecyclerView.Adapter<AZ_Adapter.ViewHolder> {

    Context context;
    List<String> data=new ArrayList<>();
    List<String> partOfSpeech=new ArrayList<>();
    private Recycler_item_click mlistener;
    SpannableString spannableString;
    String dic_syn_tag;
    ForegroundColorSpan foregroundColorSpan,foregroundColorSpandic;



    public AZ_Adapter(Context context, List<String> definition, Recycler_item_click mlistener, String dic_syn_tag,List<String> partOfSpeech) {

        this.context = context;
        this.data = definition;
        this.partOfSpeech = partOfSpeech;
        this.mlistener = mlistener;
        this.dic_syn_tag = dic_syn_tag;

    }

    @NonNull
    @Override
    public AZ_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.az_layout, parent, false);


        // set the view's size, margins, paddings and layout parameters
        return new AZ_Adapter.ViewHolder(v,mlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull final AZ_Adapter.ViewHolder holder, final int position) {
        String result = partOfSpeech.get(position)+" "+ data.get(position);
        spannableString = new SpannableString(result);

//        int count  = spannableString.toString().indexOf(" ");

        foregroundColorSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.color_main));
        foregroundColorSpandic = new ForegroundColorSpan(context.getResources().getColor(R.color.black));
        try {
            spannableString.setSpan(foregroundColorSpan, 0, partOfSpeech.get(position).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            if (dic_syn_tag.equals("0")) {

                holder.dicText.setText(spannableString);

            } else {

                holder.dicText.setText(result);
            }
        }
        catch (Exception e){

        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

       TextView dicText;
        private Recycler_item_click mlistener;

        public ViewHolder(@NonNull View itemView, Recycler_item_click mlistener) {
            super(itemView);
            this.mlistener = mlistener;
            dicText = itemView.findViewById(R.id.tv_dicText);

        }


    }

}

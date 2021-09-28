package com.scriptively.app.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.scriptively.app.R;
import com.scriptively.app.Utils.Shared_PrefrencePrompster;

public class Fragment_media extends Fragment {
String media_titl,userid,script_id,title;
Shared_PrefrencePrompster shared_prefrencePrompster;
TextView tv_media_title;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_media,container, false);
        shared_prefrencePrompster = Shared_PrefrencePrompster.getInstance(getContext());
        userid = shared_prefrencePrompster.getUserid().toString();

        tv_media_title=view.findViewById(R.id.tv_media_title);


        Bundle arguments = getArguments();

      //  title = arguments.getString("script_title");

        tv_media_title.setText(title);
        return  view;
    }
}

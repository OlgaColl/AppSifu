package com.example.olgacoll.sifu;

/**
 * Created by olgacoll on 15/5/17.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

public class ConfigFragment extends Fragment{

    public static final String TAG = "ConfigFragment";
    Switch switch1;

    public ConfigFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_config, container, false);

        switch1 = (Switch) view.findViewById(R.id.switch1);
        switch1
        return view;
    }

}
package com.example.olgacoll.sifu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class ConfigFragment extends Fragment{

    public static final String TAG = "ConfigFragment";
    Switch onOffSwitch;

    public ConfigFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_config, container, false);
        initComponents(view);
        onOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Log.v("Switch State=", ""+isChecked);
            }
        });
        return view;
    }

    private void initComponents(View view) {
        onOffSwitch = (Switch) view.findViewById(R.id.switch1);
    }

    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarCenterTitle("Configuración");
        ((MainActivity) getActivity()).getNavigationVisible(true);
        ((MainActivity) getActivity()).getSupportActionBar().show();
    }
}
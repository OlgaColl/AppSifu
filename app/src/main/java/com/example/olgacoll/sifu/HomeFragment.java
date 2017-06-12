package com.example.olgacoll.sifu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeFragment extends Fragment {

    public static final String TAG = "HomeFragment";
    TextView textViewInfo;
    ImageView imageViewConfig;
    Button buttonReport, buttonRequest;
    View.OnClickListener listener;

    public HomeFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);
        initHome();
        initComponents(view);
        onPrepareListener();
        imageViewConfig.setOnClickListener(listener);
        buttonReport.setOnClickListener(listener);
        buttonRequest.setOnClickListener(listener);
        return view;
    }

    private void initComponents(View view) {
        textViewInfo = (TextView) view.findViewById(R.id.textViewMain);
        imageViewConfig = (ImageView) view.findViewById(R.id.ic_action_config);
        buttonReport = (Button) view.findViewById(R.id.buttonReport);
        buttonRequest = (Button) view.findViewById(R.id.buttonRequest);
    }

    private void onPrepareListener(){
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Fragment();
                switch (v.getId()) {
                    case R.id.buttonReport:
                        fragment = new ReportFragment();
                        break;
                    case R.id.buttonRequest:
                        fragment = new RequestFragment();
                        break;
                    case R.id.ic_action_config:
                        fragment = new ConfigFragment();
                }
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            }
        };
    }

    private void initHome(){
        ((MainActivity) getActivity()).getNavigationVisible(false);
        ((MainActivity) getActivity()).getSupportActionBar().hide();
    }

    public void onResume(){
        super.onResume();
        initHome();
    }
}
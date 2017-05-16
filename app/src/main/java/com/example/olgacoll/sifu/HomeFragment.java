package com.example.olgacoll.sifu;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by olgacoll on 11/5/17.
 */

public class HomeFragment extends Fragment {

    TextView textViewInfo;
    Button buttonReport, buttonRequest;
    View.OnClickListener listener;
    ReportFragment reportFragment;
    public static final String TAG = "HomeFragment";

    public HomeFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);
        initComponents(view);
        onPrepareListener();
        //setupToolbar();
        buttonReport.setOnClickListener(listener);
        buttonRequest.setOnClickListener(listener);
        return view;

    }

    private void initComponents(View view) {
        textViewInfo = (TextView) view.findViewById(R.id.textViewMain);
        buttonReport = (Button) view.findViewById(R.id.buttonReport);
        buttonRequest = (Button) view.findViewById(R.id.buttonRequest);
    }

    private void onPrepareListener() {
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Fragment();

                switch (v.getId()) {
                    case R.id.buttonRequest:
                        fragment = new RequestFragment();
                        break;
                    case R.id.buttonReport:
                        fragment = new ReportFragment();
                        break;
                }
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            }
        };
    }
}
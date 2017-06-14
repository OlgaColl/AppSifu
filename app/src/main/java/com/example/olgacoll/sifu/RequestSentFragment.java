package com.example.olgacoll.sifu;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by olgacoll on 14/6/17.
 */

public class RequestSentFragment extends Fragment {

    public static final String TAG = "RequestSentFragment";
    TextView titleRequestSent, infoRequestSent;
    Button buttonRequestSent;
    View.OnClickListener listener;

    public RequestSentFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_requestsent, container, false);
        initRequestSent();
        initComponents(view);
        initFont();
        onPrepareListener();
        buttonRequestSent.setOnClickListener(listener);
        return view;
    }

    private void initComponents(View view){
        titleRequestSent = (TextView) view.findViewById(R.id.titleRequestSent);
        infoRequestSent = (TextView) view.findViewById(R.id.textViewRequestSent);
        buttonRequestSent = (Button) view.findViewById(R.id.buttonRequestSent);
    }

    private void initFont(){
        Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Lato-Regular.ttf");
        Typeface faceBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Lato-Bold.ttf");
        titleRequestSent.setTypeface(faceBold);
        infoRequestSent.setTypeface(face);
        buttonRequestSent.setTypeface(face);
    }

    private void onPrepareListener(){
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Fragment();
                switch (v.getId()) {
                    case R.id.buttonRequestSent:
                        fragment = new HomeFragment();
                        break;
                }
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            }
        };
    }

    private void initRequestSent(){
        ((MainActivity) getActivity()).getNavigationVisible(false);
        ((MainActivity) getActivity()).getSupportActionBar().hide();
    }

    public void onResume(){
        super.onResume();
        initRequestSent();
    }
}
package com.example.olgacoll.sifu;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
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

public class UrgentIncidenceFragment extends Fragment {

    public static final String TAG = "UrgentIncidenceFragment";
    TextView titleUrgentIncidenceSent, infoUrgentIncidenceSent;
    Button buttonCallUrgenceIncidence, buttonHomeUrgentIncidenceSent;
    View.OnClickListener listener;

    public UrgentIncidenceFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_urgentincidence, container, false);
        initRequestSent();
        initComponents(view);
        initFont();
        onPrepareListener();
        buttonCallUrgenceIncidence.setOnClickListener(listener);
        buttonHomeUrgentIncidenceSent.setOnClickListener(listener);
        return view;
    }

    private void initComponents(View view){
        titleUrgentIncidenceSent = (TextView) view.findViewById(R.id.titleIncidenceSent);
        infoUrgentIncidenceSent = (TextView) view.findViewById(R.id.textViewIncidenceSent);
        buttonCallUrgenceIncidence = (Button) view.findViewById(R.id.buttonIncidenceSent);
        buttonHomeUrgentIncidenceSent = (Button) view.findViewById(R.id.buttonIncidenceSent);
    }

    private void initFont(){
        Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Lato-Regular.ttf");
        Typeface faceBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Lato-Bold.ttf");
        titleUrgentIncidenceSent.setTypeface(faceBold);
        infoUrgentIncidenceSent.setTypeface(face);
        buttonCallUrgenceIncidence.setTypeface(face);
        buttonHomeUrgentIncidenceSent.setTypeface(face);
    }

    private void onPrepareListener(){
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Fragment();
                switch (v.getId()) {
                    case R.id.buttonCallUrgenceIncidence:
                        callPhone();
                        break;
                    case R.id.buttonHomeUrgenceIncidence:
                        fragment = new HomeFragment();
                        break;
                }
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            }
        };
    }

    private void callPhone(){
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "680296461"));
        startActivity(intent);
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
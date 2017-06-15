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

public class IncidenceSentFragment extends Fragment {

    public static final String TAG = "IncidenceSentFragment";
    TextView titleIncidenceSent, infoIncidenceSent;
    Button buttonIncidenceSent;
    View.OnClickListener listener;

    public IncidenceSentFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_incidencesent, container, false);
        initRequestSent();
        initComponents(view);
        initFont();
        onPrepareListener();
        buttonIncidenceSent.setOnClickListener(listener);
        return view;
    }

    private void initComponents(View view){
        titleIncidenceSent = (TextView) view.findViewById(R.id.titleIncidenceSent);
        infoIncidenceSent = (TextView) view.findViewById(R.id.textViewIncidenceSent);
        buttonIncidenceSent = (Button) view.findViewById(R.id.buttonIncidenceSent);
    }

    private void initFont(){
        Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Lato-Regular.ttf");
        Typeface faceBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Lato-Bold.ttf");
        titleIncidenceSent.setTypeface(faceBold);
        infoIncidenceSent.setTypeface(face);
        buttonIncidenceSent.setTypeface(face);
    }

    private void onPrepareListener(){
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Fragment();
                switch (v.getId()) {
                    case R.id.buttonIncidenceSent:
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
package com.example.olgacoll.sifu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

    public static final String TAG = "HomeFragment";

    public HomeFragment(){

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

    private void initComponents(View view){
        textViewInfo = (TextView)view.findViewById(R.id.textViewMain);
        buttonReport = (Button)view.findViewById(R.id.buttonReport);
        buttonRequest = (Button)view.findViewById(R.id.buttonRequest);
    }

    private void onPrepareListener(){
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.buttonReport:
                        //initReport();
                        break;
                    case R.id.buttonRequest:
                        //initRequest();
                        break;
                }
            }
        };
    }

    /*private void initHome(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }*/

    /*private void initReport(){
        Intent intent = new Intent(this, ReportActivity.class);
        startActivity(intent);
    }

    private void initRequest(){
        Intent intent = new Intent(this, RequestActivity.class);
        startActivity(intent);
    }*/

    //private void initInfo(){
        /*FragmentManager fragmentManager = getFragmentManager();

        //Paso 2: Crear una nueva transacción
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        //Paso 3: Crear un nuevo fragmento y añadirlo
        InfoActivity fragment = new InfoActivity();
        //transaction.add(R.id.fragmentMain, fragment);

        //Paso 4: Confirmar el cambio
        transaction.commit();*/
        //Intent intent = new Intent(this, InfoActivity.class);
        //startActivity(intent);
        //setContentView(R.layout.activity_info);
    //}

    /*private void initConfig(){
        Intent intent = new Intent(this, ConfigActivity.class);
        startActivity(intent);
    }*/
}
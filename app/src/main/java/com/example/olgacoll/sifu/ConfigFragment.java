package com.example.olgacoll.sifu;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

public class ConfigFragment extends Fragment{

    public static final String TAG = "ConfigFragment";
    TextView textViewNotifications;
    Switch onOffSwitch;
    int check;
    DBConfig dbConfig;
    SQLiteDatabase sqlConfig;
    FirebaseAnalytics firebaseAnalytics;

    public ConfigFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_config, container, false);
        initComponents(view);
        initFont();
        onOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    onOffSwitch.getThumbDrawable().setColorFilter(Color.rgb(241, 139, 35), PorterDuff.Mode.MULTIPLY);
                    showMessage("Notificaciones activadas.");
                    firebaseAnalytics.setAnalyticsCollectionEnabled(true);
                    check = 1;
                }else{
                    onOffSwitch.getThumbDrawable().setColorFilter(Color.rgb(229, 229, 229), PorterDuff.Mode.MULTIPLY);
                    showMessage("Las notificaciones han sido desactivadas.");
                    check = 0;
                    firebaseAnalytics.setAnalyticsCollectionEnabled(false);
                }
                saveData();
            }
        });
        return view;
    }

    public void saveData(){
        this.sqlConfig = this.dbConfig.getWritableDatabase();
        if (this.sqlConfig != null) {
            this.sqlConfig.execSQL("UPDATE settings SET active = ('" + check + "')");
        }
        this.sqlConfig.close();
        showData();
    }

    public void showData(){
        Cursor c;
        sqlConfig = dbConfig.getReadableDatabase();
        c = sqlConfig.rawQuery("SELECT * FROM settings", null);
        if(c.moveToFirst()) {
            do{
                int active = c.getInt(0);
                System.out.println("Active: " + active);
            }while(c.moveToNext());
            c.close();
        }
    }

    public void initialSwitch() {
        Cursor c;
        sqlConfig = dbConfig.getReadableDatabase();
        c = sqlConfig.rawQuery("SELECT * FROM settings", null);
        if (c.moveToFirst()) {
            check = c.getInt(0);
            System.out.println("Initial active: " + check);
            c.close();
        }

        if (check == 1) {
            onOffSwitch.setChecked(true);
            onOffSwitch.getThumbDrawable().setColorFilter(Color.rgb(241, 139, 35), PorterDuff.Mode.MULTIPLY);
        } else {
            onOffSwitch.setChecked(false);
            onOffSwitch.getThumbDrawable().setColorFilter(Color.rgb(229, 229, 229), PorterDuff.Mode.MULTIPLY);
        }
    }

    private void initComponents(View view) {
        textViewNotifications = (TextView) view.findViewById(R.id.textViewNotifications);
        onOffSwitch = (Switch) view.findViewById(R.id.switch1);
        this.dbConfig = new DBConfig(this.getActivity().getApplicationContext(), "settings", null, 1);
        onOffSwitch.getThumbDrawable().setColorFilter(Color.rgb(241, 139, 35), PorterDuff.Mode.MULTIPLY);
        firebaseAnalytics = FirebaseAnalytics.getInstance(getActivity().getApplicationContext());

        initialSwitch();
    }

    private void initFont(){
        Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Lato-Regular.ttf");
        textViewNotifications.setTypeface(face);
    }

    //Check true/false
    public boolean checkConfig() {
        boolean flag;
        if(check == 1){
            flag = true;
        }else{
            flag = false;
        }
        System.out.println("Flag: " + flag);
        return flag;
    }

    private void showMessage(String str){
        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
    }

    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarCenterTitle("Configuración");
        ((MainActivity) getActivity()).getNavigationVisible(true);
        ((MainActivity) getActivity()).getSupportActionBar().show();
    }
}
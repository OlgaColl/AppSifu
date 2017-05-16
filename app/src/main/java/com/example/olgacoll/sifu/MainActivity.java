package com.example.olgacoll.sifu;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
            Fragment container = new Fragment();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    container = new HomeFragment();
                    //mTextMessage.setText(R.string.title_home);
                    break;
                case R.id.navigation_report:
                    //mTextMessage.setText(R.string.title_notifications);
                    container = new ReportFragment();
                    break;
                case R.id.navigation_request:
                    //mTextMessage.setText(R.string.title_notifications);
                    container = new RequestFragment();
                    break;
                /*case R.id.navigation_info:
                    //mTextMessage.setText(R.string.title_dashboard);
                    container = new InfoFragment();
                    break;*/
            }

            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.replace(R.id.container, container);
            transaction.addToBackStack(null); //Para que al pulsar back vuelva atrás el Fragment y no la Activity
            transaction.commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragment();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    //Go to config fragment
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        Fragment container = new Fragment();
        switch (item.getItemId()) {
            case R.id.action_config:
                Toast.makeText(this, "Menu Item 1 selected", Toast.LENGTH_SHORT).show();
                container = new ConfigFragment();
                break;
        }

        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        transaction.replace(R.id.container, container);
        //Para que al pulsar back vuelva atrás el Fragment y no la Activity
        transaction.addToBackStack(null);
        transaction.commit();
        return true;
    }

    private void initFragment() {
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        Fragment container = new HomeFragment();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(R.id.container, container);
        //Para que al pulsar back vuelva atrás el Fragment y no la Activity
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

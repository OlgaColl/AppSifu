package com.example.olgacoll.sifu;

import android.app.ActionBar;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView titleActionBar;

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
        titleActionBar = (TextView)findViewById(R.id.textViewTitleActionBar);
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

            //PER BACK BUTTON
            /*case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;*/
            case R.id.action_config:
                container = new ConfigFragment();
                break;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }

        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        transaction.replace(R.id.container, container);
        transaction.addToBackStack(null); //Para que al pulsar back vuelva atrás el Fragment y no la Activity
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

    //Para cambiar el titulo del actionBar, centrar titulo, icono?
    public void setActionBarCenterTitle(String title) {
        getSupportActionBar().setTitle(title);

        /*getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        titleActionBar.setText(title);*/
    }


}

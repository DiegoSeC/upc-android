package com.barberia.upc.barberupc;

import android.support.v4.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        actionBar = getSupportActionBar();

        actionBar.setTitle(R.string.title_dashboard);
        loadFragment(new DashboardFragment());

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            Log.d("CLICKED", "Changing fragment");

            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    actionBar.setTitle(R.string.title_dashboard);
                    fragment = new DashboardFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_reservation:
                    actionBar.setTitle(R.string.title_reservation);
                    fragment = new ReservationFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_hair_cut:
                    actionBar.setTitle(R.string.title_hair_cuts);
                    fragment = new HairCutFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_profile:
                    actionBar.setTitle(R.string.title_profile);
                    fragment = new ProfileFragment();
                    loadFragment(fragment);
                    return true;
            }

            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_view, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}

package com.pivotalsoft.property;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.pivotalsoft.property.Fragments.DashBoardFragment;
import com.pivotalsoft.property.Fragments.SecondFragment;
import com.pivotalsoft.property.Fragments.ThirdFragment;

public class BottamNavgationActivity extends AppCompatActivity {

    String Role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottam_navgation);

        // To retrieve value from shared preference in another activity
        SharedPreferences sp = getSharedPreferences("MyPref", Context.MODE_PRIVATE); // 0 for private mode
        Role = sp.getString("role", null);

        BottomNavigationView navigation1 = (BottomNavigationView) findViewById(R.id.navBuyer);
        navigation1.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //BottomNavigationViewHelper.removeShiftMode(navigation1);

        BottomNavigationView navigation2 = (BottomNavigationView) findViewById(R.id.navSeller);
        navigation2.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // BottomNavigationViewHelper.removeShiftMode(navigation2);

        if (Role.equals("Buyer")){

            navigation1.setVisibility(View.VISIBLE);
            navigation2.setVisibility(View.GONE);


        }else {

            navigation2.setVisibility(View.VISIBLE);
            navigation1.setVisibility(View.GONE);

        }



        // changeFragment(new HomeFragment());

        // default fragment after launch
        getSupportActionBar().setTitle("Home");
        getSupportActionBar().show();
        DashBoardFragment homeFragment = new DashBoardFragment();
        FragmentManager manager1 =getSupportFragmentManager();
        manager1.beginTransaction().replace(R.id.content_layout,homeFragment,homeFragment.getTag()).commit();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_first:
                    //changeFragment(new HomeFragment());
                    getSupportActionBar().setTitle("Home");
                    getSupportActionBar().show();
                    DashBoardFragment homeFragment = new DashBoardFragment();
                    FragmentManager manager1 = getSupportFragmentManager();
                    manager1.beginTransaction().replace(R.id.content_layout, homeFragment, homeFragment.getTag()).commit();

                    return true;
                case R.id.navigation_second:
                    // changeFragment(new SearchFragment());
                    if (Role.equals("Buyer")) {
                        getSupportActionBar().setTitle("Search");
                        getSupportActionBar().show();
                    }else {
                        getSupportActionBar().setTitle("My Properties");
                        getSupportActionBar().show();
                    }

                    SecondFragment searchFragment = new SecondFragment();
                    FragmentManager manager3 = getSupportFragmentManager();
                    manager3.beginTransaction().replace(R.id.content_layout, searchFragment, searchFragment.getTag()).commit();

                    return true;
                case R.id.navigation_third:
                    // changeFragment(new ProfileFragment());

                    if (Role.equals("Buyer")) {
                        getSupportActionBar().setTitle("My Favorites");
                        getSupportActionBar().show();
                    }else {
                        getSupportActionBar().setTitle("My Leads");
                        getSupportActionBar().show();
                    }

                    ThirdFragment applicationFragment = new ThirdFragment();
                    FragmentManager manager5 = getSupportFragmentManager();
                    manager5.beginTransaction().replace(R.id.content_layout, applicationFragment, applicationFragment.getTag()).commit();
                    return true;


            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {
        //super.onBackPressed();


        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(BottamNavgationActivity.this);
        } else {
            builder = new AlertDialog.Builder(BottamNavgationActivity.this);
        }
        builder.setTitle("Confirm Exit ")
                .setMessage("Do you want to exit app?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        // moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                //.setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}

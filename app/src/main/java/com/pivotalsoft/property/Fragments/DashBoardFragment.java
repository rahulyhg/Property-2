package com.pivotalsoft.property.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.pivotalsoft.property.Adapter.AddsAdaptes;
import com.pivotalsoft.property.Constants.Constants;
import com.pivotalsoft.property.ProfileActivity;
import com.pivotalsoft.property.R;
import com.pivotalsoft.property.Response.AddsInfoItem;

import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashBoardFragment extends Fragment {
    AddsInfoItem addsInfo=new AddsInfoItem();
    Gson gson;
    private static ViewPager mPager;
    CircleIndicator indicator;
    private static int currentPage = 0;
    View rootView;
    public DashBoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_dash_board, container, false);
        setHasOptionsMenu(true);

        init();
        getAddsInformation();
        return rootView;
    }

    private void init() {

        mPager = (ViewPager)rootView. findViewById(R.id.pager);

        indicator = (CircleIndicator) rootView.findViewById(R.id.indicator);
        indicator.setViewPager(mPager);


        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {

                if (addsInfo.getAddata()!=null) {
                    if (currentPage == addsInfo.getAddata().size() - 1) {
                        currentPage = 0;
                    }
                    mPager.setCurrentItem(currentPage++, true);

                }
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 10000, 10000);

        getAddsInformation();

    }

    // response from gson lib
    private void getAddsInformation(){

        StringRequest stringRequest =new StringRequest(Request.Method.GET, Constants.AD_INFO_URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // String responsestr = response;
                gson = new Gson();
                addsInfo = gson.fromJson(response,AddsInfoItem.class);

              // mPager.setAdapter(adapter);

                if (getActivity()!=null){

                    AddsAdaptes adapter = new AddsAdaptes(getContext(), addsInfo.getAddata());
                    mPager.setAdapter(adapter);
                    //dynamic adapter
                    adapter.registerDataSetObserver(indicator.getDataSetObserver());
                    adapter.notifyDataSetChanged();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // TODO Add your menu entries here
        inflater.inflate(R.menu.menu_home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                Intent pivotal = new Intent(getContext(), ProfileActivity.class);
                pivotal.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(pivotal);
                break;


        }
        return true;

    }

}

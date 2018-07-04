package com.pivotalsoft.property;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.pivotalsoft.property.Adapter.CustomPagerAdapter;
import com.pivotalsoft.property.Response.BuyerSearchItem;
import com.pivotalsoft.property.Response.SliderItem;

import java.util.ArrayList;

public class PropertyImagesActivity extends AppCompatActivity {
    private CustomPagerAdapter mCustomPagerAdapter;
    private ArrayList<SliderItem> sliderItems = new ArrayList<>();
    ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_images);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Photos");

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mCustomPagerAdapter=new CustomPagerAdapter(PropertyImagesActivity.this,sliderItems);
        mViewPager.setAdapter(mCustomPagerAdapter);

        imagesLoad();
    }

    private void imagesLoad(){

        SliderItem addCardItem = new SliderItem("1","1",R.drawable.background);
        sliderItems.add(addCardItem);

        addCardItem = new SliderItem("2","2",R.drawable.house_image);
        sliderItems.add(addCardItem);

        mCustomPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //finish();
                onBackPressed();
                break;

        }
        return true;
    }
}

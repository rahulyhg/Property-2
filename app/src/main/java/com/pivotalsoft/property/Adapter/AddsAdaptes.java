package com.pivotalsoft.property.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.pivotalsoft.property.Constants.Constants;
import com.pivotalsoft.property.R;
import com.pivotalsoft.property.Response.AddsInfoItem;


import java.util.List;

public class AddsAdaptes extends PagerAdapter {
    private List<AddsInfoItem.AddataBean> images;
    private LayoutInflater inflater;
    private Context context;

    public AddsAdaptes(Context context, List<AddsInfoItem.AddataBean> images) {
        this.context = context;
        this.images=images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View myImageLayout = inflater.inflate(R.layout.adds_card, view, false);

        AddsInfoItem.AddataBean addCardItem =images.get(position);
        ImageView myImage = (ImageView) myImageLayout.findViewById(R.id.thumbnail);

        try {
            Glide.with(context).load(Constants.IMAGE_AD_URL+addCardItem.getAdpic()).into(myImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        myImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // Toast.makeText(context,"imagecilcked"+position,Toast.LENGTH_LONG).show();
/*
                Intent intent =new Intent(context, AddsJobDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("jobid",addCardItem.getJobid());
                context.startActivity(intent);*/
            }
        });
        view.addView(myImageLayout, 0);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
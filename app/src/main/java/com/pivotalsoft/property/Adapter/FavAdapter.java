package com.pivotalsoft.property.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pivotalsoft.property.PropertyDetailsActivity;
import com.pivotalsoft.property.R;
import com.pivotalsoft.property.Response.FavItem;

import java.util.List;

/**
 * Created by USER on 1/11/2018.
 */

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.MyViewHolder> {

    private Context mContext;
    private List<FavItem> buyerSearchItems;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtPrice,txtMobile,txtTitle;
        ImageView imgHouse;
        LinearLayout parentLayout;

        public MyViewHolder(View view) {
            super(view);
            txtPrice = (TextView) view.findViewById(R.id.txtPrice);
            txtMobile = (TextView) view.findViewById(R.id.txtmobile);
            txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            imgHouse = (ImageView) view.findViewById(R.id.imgProperty);
            parentLayout=(LinearLayout)view.findViewById(R.id.parentLayout);

        }
    }


    public FavAdapter(Context mContext, List<FavItem> coursesItemList) {
        this.mContext = mContext;
        this.buyerSearchItems = coursesItemList;
    }

    @Override
    public FavAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fav_item, parent, false);

        return new FavAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FavAdapter.MyViewHolder holder, final int position) {
        final FavItem album = buyerSearchItems.get(position);
        holder.txtPrice.setText(album.getPrice());
        holder.txtMobile.setText(album.getMobile());
        holder.txtTitle.setText(album.getTitle());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(mContext,PropertyDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

        try {
            Glide.with(mContext).load(album.getImage()).into(holder.imgHouse);
        } catch (Exception e) {
            e.printStackTrace();
        }



    }


    @Override
    public int getItemCount() {
        return buyerSearchItems.size();
    }



}


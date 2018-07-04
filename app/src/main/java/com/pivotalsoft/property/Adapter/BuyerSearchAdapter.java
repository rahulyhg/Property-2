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
import com.pivotalsoft.property.Response.BuyerSearchItem;

import java.util.List;

/**
 * Created by USER on 1/9/2018.
 */

public class BuyerSearchAdapter extends RecyclerView.Adapter<BuyerSearchAdapter.MyViewHolder> {

    private Context mContext;
    private List<BuyerSearchItem> buyerSearchItems;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtCost,txtAddress,txtSqft;
        LinearLayout parentLayout;
        ImageView imgHouse;

        public MyViewHolder(View view) {
            super(view);
            txtCost = (TextView) view.findViewById(R.id.txtPrice);
            txtAddress = (TextView) view.findViewById(R.id.txtAddress);
            txtSqft = (TextView) view.findViewById(R.id.txtSqft);
            imgHouse = (ImageView) view.findViewById(R.id.imgProperty);
            parentLayout=(LinearLayout)view.findViewById(R.id.parentLayout);

        }
    }


    public BuyerSearchAdapter(Context mContext, List<BuyerSearchItem> coursesItemList) {
        this.mContext = mContext;
        this.buyerSearchItems = coursesItemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.buyer_search_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final BuyerSearchItem album = buyerSearchItems.get(position);
        holder.txtCost.setText(album.getCost());
        holder.txtSqft.setText(album.getSqft());
        holder.txtAddress.setText(album.getAddress());
        try {
            Glide.with(mContext).load(album.getImage()).into(holder.imgHouse);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(mContext,PropertyDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return buyerSearchItems.size();
    }



}

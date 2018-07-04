package com.pivotalsoft.property.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pivotalsoft.property.PropertyDetailsActivity;
import com.pivotalsoft.property.R;
import com.pivotalsoft.property.Response.FavItem;
import com.pivotalsoft.property.Response.SellerPropertyItem;

import java.util.List;

/**
 * Created by USER on 1/12/2018.
 */

public class SellerPropertyAdapter extends RecyclerView.Adapter<SellerPropertyAdapter.MyViewHolder> {

    private Context mContext;
    private List<SellerPropertyItem> sellerPropertyItems;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtPrice,txtMobile,txtTitle;
        LinearLayout parentLayout;
        ImageView overflow, imgHouse;

        public MyViewHolder(View view) {
            super(view);
            txtPrice = (TextView) view.findViewById(R.id.txtPrice);
            txtMobile = (TextView) view.findViewById(R.id.txtmobile);
            txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            imgHouse = (ImageView) view.findViewById(R.id.imgProperty);
            parentLayout=(LinearLayout)view.findViewById(R.id.parentLayout);
            overflow=(ImageView)view.findViewById(R.id.overflow);

        }
    }


    public SellerPropertyAdapter(Context mContext, List<SellerPropertyItem> sellerPropertyItems) {
        this.mContext = mContext;
        this.sellerPropertyItems = sellerPropertyItems;
    }

    @Override
    public SellerPropertyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.property_item, parent, false);

        return new SellerPropertyAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SellerPropertyAdapter.MyViewHolder holder, final int position) {
        final SellerPropertyItem album = sellerPropertyItems.get(position);
        holder.txtPrice.setText(album.getPrice());
        holder.txtMobile.setText(album.getMobile());
        holder.txtTitle.setText(album.getTitle());
        try {
            Glide.with(mContext).load(album.getImage()).into(holder.imgHouse);
        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showPopupMenu(holder.overflow);

                PopupMenu popup = new PopupMenu(mContext, view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.menu_seller, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.action_add_edit:


                                return true;

                            case R.id.action_delete:

                                return true;
                            default:
                        }

                        return false;
                    }
                });

                popup.show();
            }
        });


    }


    @Override
    public int getItemCount() {
        return sellerPropertyItems.size();
    }



}



package com.pivotalsoft.property.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
import com.pivotalsoft.property.Constants.Constants;
import com.pivotalsoft.property.R;
import com.pivotalsoft.property.Response.SellerLeadsItem;
import com.pivotalsoft.property.Response.SellerPropertyItem;

import java.util.List;

/**
 * Created by USER on 1/12/2018.
 */

public class SellerLeadsAdapter extends RecyclerView.Adapter<SellerLeadsAdapter.MyViewHolder> {

    private Context mContext;
    private List<SellerLeadsItem> sellerPropertyItems;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtName,txtMobile,txtEmail;
        ImageView imgUser;
        LinearLayout parentLayout;


        public MyViewHolder(View view) {
            super(view);
            txtName = (TextView) view.findViewById(R.id.txtName);
            txtMobile = (TextView) view.findViewById(R.id.txtmobile);
            txtEmail = (TextView) view.findViewById(R.id.txtEmail);
            imgUser = (ImageView) view.findViewById(R.id.imgUser);
            parentLayout=(LinearLayout)view.findViewById(R.id.parentLayout);


        }
    }


    public SellerLeadsAdapter(Context mContext, List<SellerLeadsItem> sellerPropertyItems) {
        this.mContext = mContext;
        this.sellerPropertyItems = sellerPropertyItems;
    }

    @Override
    public SellerLeadsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.leads_item, parent, false);

        return new SellerLeadsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SellerLeadsAdapter.MyViewHolder holder, final int position) {
        final SellerLeadsItem album = sellerPropertyItems.get(position);
        holder.txtEmail.setText(album.getEmail());
        holder.txtMobile.setText(album.getMobile());
        holder.txtName.setText(album.getName());

        try {
            Glide.with(mContext).load(album.getImage()).into(holder.imgUser);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public int getItemCount() {
        return sellerPropertyItems.size();
    }



}



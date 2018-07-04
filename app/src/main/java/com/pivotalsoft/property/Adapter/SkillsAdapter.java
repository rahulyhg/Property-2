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

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.pivotalsoft.property.EditCertificatesActivity;
import com.pivotalsoft.property.R;
import com.pivotalsoft.property.Response.SkillsItem;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Gangadhar on 11/26/2017.
 */

public class SkillsAdapter extends RecyclerView.Adapter<SkillsAdapter.MyViewHolder> {

    private Context mContext;
    private List<SkillsItem> coursesItemList;
    String firstLetter;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtWorked, txtCompany,txtDate;
        public ImageView thumbnail;
        public LinearLayout parentLayout;


        public MyViewHolder(View view) {
            super(view);
            txtWorked = (TextView) view.findViewById(R.id.txtWorkedas);
           // txtCompany = (TextView) view.findViewById(R.id.txtCompany);
            txtDate = (TextView) view.findViewById(R.id.txtDate);
            thumbnail = (ImageView) view.findViewById(R.id.imageView);
            parentLayout = (LinearLayout) view.findViewById(R.id.parentLayout);
        }
    }


    public SkillsAdapter(Context mContext, List<SkillsItem> coursesItemList) {
        this.mContext = mContext;
        this.coursesItemList = coursesItemList;
    }

    @Override
    public SkillsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.document_card, parent, false);

        return new SkillsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SkillsAdapter.MyViewHolder holder, final int position) {
        final SkillsItem album = coursesItemList.get(position);
        holder.txtWorked.setText(album.getInstitute());
//        holder.txtCompany.setText(album.getCourse());
        holder.txtDate.setText(album.getStartyear()+" To "+album.getEndyear());

        // loading album cover using Glide library
        //  Glide.with(mContext).load(album.getImage()).into(holder.thumbnail);



        if (album.getInstitute().equals("")){

            firstLetter="H";
        }
        else {

            String test = album.getInstitute();
            firstLetter=test.substring(0,1).toUpperCase();
        }
        /*String startDate = formateDateFromstring("yyyy-MM-dd", "dd,MMM,yyyy", album.getDate());
        Log.e("startDateformat",""+startDate);

        StringTokenizer tokens = new StringTokenizer(startDate, ",");
        String day = tokens.nextToken();
        String month = tokens.nextToken();// this will contain " they taste good"
        String year = tokens.nextToken();// this will contain "Fruit"

        Log.e("startDatetoken",""+day+"\n"+month);

        holder.txtDay.setText(day);
        holder.txtMonth.setText(month);
        holder.txtYear.setText(year);*/


        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color1 = generator.getRandomColor();

        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .width(60)  // width in px
                .height(60) // height in px
                .endConfig()
                .buildRound(firstLetter, color1);

        holder.thumbnail.setImageDrawable(drawable);


        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkillsItem skillsItem=coursesItemList.get(position);
                Intent details =new Intent(mContext,EditCertificatesActivity.class);
                details.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                /*details.putExtra("institute",skillsItem.getInstitute());
                details.putExtra("course",skillsItem.getCourse());
                details.putExtra("startyear",skillsItem.getStartyear());
                details.putExtra("endyear",skillsItem.getEndyear());
                details.putExtra("skillid",skillsItem.getSkillid());*/
                mContext.startActivity(details);
            }
        });


    }


    @Override
    public int getItemCount() {
        return coursesItemList.size();
    }


    public static String formateDateFromstring(String inputFormat, String outputFormat, String inputDate){

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) {
            // LOGE(TAG, "ParseException - dateFormat");
        }

        return outputDate;

    }
}
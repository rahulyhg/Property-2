package com.pivotalsoft.property;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class PropertyDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imgProperty;
    Button btnContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("");

        imgProperty=(ImageView)findViewById(R.id.imgProperty);
        imgProperty.setOnClickListener(this);

        btnContact=(Button)findViewById(R.id.btnContact);
        btnContact.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_property, menu);//Menu Resource, Menu

         MenuItem itemFav = menu.findItem(R.id.action_fav);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //finish();
                onBackPressed();
                break;

            case R.id.action_share:

                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.pivotalsoft.user.hikestreet&hl=en");
                startActivity(Intent.createChooser(share, "Share Hikestreet app"));

                break;

            case R.id.action_fav:
                boolean isFavourite = readStae();

                if (isFavourite) {
                    item.setIcon(R.drawable.ic_favorite_buyer);
                    isFavourite = false;
                    saveStae(isFavourite);

                } else {
                    item.setIcon(R.drawable.ic_favorite_border);
                    isFavourite = true;
                    saveStae(isFavourite);

                }
                break;
        }
        return true;
    }

    private void saveStae(boolean isFavourite) {
        SharedPreferences aSharedPreferenes = this.getSharedPreferences(
                "Mypref", Context.MODE_PRIVATE);
        SharedPreferences.Editor aSharedPreferenesEdit = aSharedPreferenes
                .edit();
        aSharedPreferenesEdit.putBoolean("State", isFavourite);
        aSharedPreferenesEdit.apply();
    }

    private boolean readStae() {
        SharedPreferences aSharedPreferenes = this.getSharedPreferences(
                "Mypref", Context.MODE_PRIVATE);
        return aSharedPreferenes.getBoolean("State", true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.imgProperty:
                Intent intent =new Intent(PropertyDetailsActivity.this,PropertyImagesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                break;

            case R.id.btnContact:
                Intent intent1 =new Intent(PropertyDetailsActivity.this,ContactAdviserActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent1);
                break;

        }

    }
}

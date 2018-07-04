package com.pivotalsoft.property;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class FilterActivity extends AppCompatActivity implements View.OnClickListener {
    private final int REQUEST_CODE_PLACEPICKER = 1;
    TextView txtRangePrice,txtRangeSqft;
    EditText etLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Filter");

        txtRangePrice=(TextView)findViewById(R.id.txtRangePrice);
        txtRangeSqft=(TextView)findViewById(R.id.txtRangeSqft);
        etLocation=(EditText) findViewById(R.id.etLocation);
        etLocation.setOnClickListener(this);

        RangeSeekBar rangeSeekbar = (RangeSeekBar) findViewById(R.id.rangeSeekbar);
        rangeSeekbar.setNotifyWhileDragging(true);
        rangeSeekbar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {

                txtRangePrice.setText( minValue + " - " + maxValue);

               // Toast.makeText(getApplicationContext(), "Min Value- " + minValue + " & " + "Max Value- " + maxValue, Toast.LENGTH_SHORT).show();
            }
        });

        RangeSeekBar rangeSeekbar1 = (RangeSeekBar) findViewById(R.id.rangeSeekbar2);
        rangeSeekbar1.setNotifyWhileDragging(true);
        rangeSeekbar1.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {

                txtRangeSqft.setText( minValue + " - " + maxValue);

                // Toast.makeText(getApplicationContext(), "Min Value- " + minValue + " & " + "Max Value- " + maxValue, Toast.LENGTH_SHORT).show();
            }
        });
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.etLocation:

                startPlacePickerActivity();
                break;
        }
    }

    // google place picker
    private void startPlacePickerActivity() {
        PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
        // this would only work if you have your Google Places API working

        try {
            Intent intent = intentBuilder.build(this);
            startActivityForResult(intent, REQUEST_CODE_PLACEPICKER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displaySelectedPlaceFromPlacePicker(Intent data) {
        Place placeSelected = PlacePicker.getPlace(data, this);
        Log.e("placeSelected",""+placeSelected);

        String name = placeSelected.getName().toString();

        // places latitude
        LatLng qLoc = placeSelected.getLatLng();
        Double lat =qLoc.latitude;
        Log.e("lat", "Place: " + lat);
        //latitude = String.valueOf(lat);

        // places longitude
        Double lon =qLoc.longitude;
        Log.e("lon", "Place: " + lon);
        //longitude =String.valueOf(lon);

        Geocoder gcd=new Geocoder(FilterActivity.this, Locale.ENGLISH);
        List<Address> addresses;

        try {
            addresses=gcd.getFromLocation(lat,lon,1);
            if(addresses.size()>0)

            {
                String address = addresses.get(0).getAddressLine(0);
                String locality = addresses.get(0).getLocality().toString();
                String subLocality = addresses.get(0).getSubLocality().toString();
                String AddressLine = addresses.get(0).getAddressLine(0).toString();
                //locTextView.setText(cityname);

                if (locality!=null) {
                    etLocation.setText(locality);
                }
                else {
                    Toast.makeText(FilterActivity.this,"No data Found for this ... Please Choose Another City",Toast.LENGTH_SHORT).show();
                }
                // }
                Log.e("locality",""+locality);
                Log.e("SubLocality",""+subLocality);
                Log.e("AddressLine",""+AddressLine);
            }

        } catch (IOException e) {
            e.printStackTrace();

        }


      /*  TextView enterCurrentLocation = (TextView) findViewById(R.id.textView9);
        enterCurrentLocation.setText(name + ", " + address);*/
    }


    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == REQUEST_CODE_PLACEPICKER){
                displaySelectedPlaceFromPlacePicker(data);
            }
        }
    }
}

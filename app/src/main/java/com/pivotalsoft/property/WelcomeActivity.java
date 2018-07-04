package com.pivotalsoft.property;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class WelcomeActivity extends Activity implements View.OnClickListener {

    private final int REQUEST_CODE_PLACEPICKER = 1;
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    String lattitude,longitude;
    TextView txtJob,txtHire,txtCounsultant,txtLocation;
    LinearLayout buyer,seller,agent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        txtHire=(TextView)findViewById(R.id.txtHire);
        txtJob=(TextView)findViewById(R.id.txtJob);
        txtCounsultant=(TextView)findViewById(R.id.txtCounsultant);

        buyer=(LinearLayout)findViewById(R.id.buyer) ;
        seller=(LinearLayout)findViewById(R.id.seller) ;
        agent=(LinearLayout)findViewById(R.id.agent) ;

        buyer.setOnClickListener(this);
        seller.setOnClickListener(this);
        agent.setOnClickListener(this);



        txtLocation=(TextView)findViewById(R.id.txtLocation);
        txtLocation.setOnClickListener(this);


       // check self permissions
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();
        }


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.txtLocation:

              startPlacePickerActivity();
                break;

            case R.id.buyer:

                hireDiloge();
                break;

            case R.id.seller:

                jobDiloge();
                break;

            case R.id.agent:

                counsultantDiloge();
                break;
        }

    }

    private void hireDiloge(){

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(WelcomeActivity.this);
        } else {
            builder = new AlertDialog.Builder(WelcomeActivity.this);
        }
        builder.setTitle("Role : BUYER")
                .setMessage("Are you sure you want to continue?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        Intent hire =new Intent(WelcomeActivity.this,PhoneActivity.class);
                        hire.putExtra("ROLE","Buyer");
                        hire.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(hire);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                //.setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void jobDiloge(){

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(WelcomeActivity.this);
        } else {
            builder = new AlertDialog.Builder(WelcomeActivity.this);
        }
        builder.setTitle("Role : SELLER")
                .setMessage("Are you sure you want to continue?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        Intent job =new Intent(WelcomeActivity.this,PhoneActivity.class);
                        job.putExtra("ROLE","Seller");
                        job.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(job);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                //.setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    private void counsultantDiloge(){

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(WelcomeActivity.this);
        } else {
            builder = new AlertDialog.Builder(WelcomeActivity.this);
        }
        builder.setTitle("Role : AGENT")
                .setMessage("Are you sure you want to continue?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        Intent hire =new Intent(WelcomeActivity.this,PhoneActivity.class);
                        hire.putExtra("ROLE","Agent");
                        hire.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(hire);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                //.setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();


        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(WelcomeActivity.this);
        } else {
            builder = new AlertDialog.Builder(WelcomeActivity.this);
        }
        builder.setTitle("Confirm Exit ")
                .setMessage("Do you want to exit app?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        //moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                //.setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

   // get location
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(WelcomeActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (WelcomeActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(WelcomeActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Location location2 = locationManager.getLastKnownLocation(LocationManager. PASSIVE_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                Geocoder gcd=new Geocoder(WelcomeActivity.this, Locale.ENGLISH);
                List<Address> addresses;

                try {
                    addresses=gcd.getFromLocation(latti,longi,1);
                    if(addresses.size()>0)

                    {
                        String address = addresses.get(0).getAddressLine(0);
                        String locality = addresses.get(0).getLocality().toString();
                        String subLocality = addresses.get(0).getSubLocality().toString();
                        String AddressLine = addresses.get(0).getAddressLine(0).toString();
                        //locTextView.setText(cityname);

                        if (locality!=null) {
                            txtLocation.setText(locality);
                        }
                        else {
                            Toast.makeText(WelcomeActivity.this,"No data Found for this ... Please Choose Another City",Toast.LENGTH_SHORT).show();
                        }
                        // }
                        Log.e("locality",""+locality);
                        Log.e("SubLocality",""+subLocality);
                        Log.e("AddressLine",""+AddressLine);
                    }

                } catch (IOException e) {
                    e.printStackTrace();

                }



            } else  if (location1 != null) {
                double latti = location1.getLatitude();
                double longi = location1.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                Geocoder gcd=new Geocoder(WelcomeActivity.this, Locale.ENGLISH);
                List<Address> addresses;

                try {
                    addresses=gcd.getFromLocation(latti,longi,1);
                    if(addresses.size()>0)

                    {
                        String address = addresses.get(0).getAddressLine(0);
                        String locality = addresses.get(0).getLocality().toString();
                        String subLocality = addresses.get(0).getSubLocality().toString();
                        String AddressLine = addresses.get(0).getAddressLine(0).toString();
                        //locTextView.setText(cityname);

                        if (locality!=null) {
                            txtLocation.setText(locality);
                        }
                        else {
                            Toast.makeText(WelcomeActivity.this,"No data Found for this ... Please Choose Another City",Toast.LENGTH_SHORT).show();
                        }
                        // }
                        Log.e("locality",""+locality);
                        Log.e("SubLocality",""+subLocality);
                        Log.e("AddressLine",""+AddressLine);
                    }

                } catch (IOException e) {
                    e.printStackTrace();

                }



            } else  if (location2 != null) {
                double latti = location2.getLatitude();
                double longi = location2.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                Geocoder gcd=new Geocoder(WelcomeActivity.this, Locale.ENGLISH);
                List<Address> addresses;

                try {
                    addresses=gcd.getFromLocation(latti,longi,1);
                    if(addresses.size()>0)

                    {
                        String address = addresses.get(0).getAddressLine(0);
                        String locality = addresses.get(0).getLocality().toString();
                        String subLocality = addresses.get(0).getSubLocality().toString();
                        String AddressLine = addresses.get(0).getAddressLine(0).toString();
                        //locTextView.setText(cityname);

                        if (locality != null) {
                            txtLocation.setText(locality);
                        }
                        else {
                            Toast.makeText(WelcomeActivity.this,"No data Found for this ... Please Choose Another City",Toast.LENGTH_SHORT).show();
                        }
                        // }
                        Log.e("locality",""+locality);
                        Log.e("SubLocality",""+subLocality);
                        Log.e("AddressLine",""+AddressLine);
                    }

                } catch (IOException e) {
                    e.printStackTrace();

                }


            }else{

                Toast.makeText(this,"Unble to Trace your location",Toast.LENGTH_SHORT).show();

            }
        }
    }

    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
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

        Geocoder gcd=new Geocoder(WelcomeActivity.this, Locale.ENGLISH);
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
                    txtLocation.setText(locality);
                }
                else {
                    Toast.makeText(WelcomeActivity.this,"No data Found for this ... Please Choose Another City",Toast.LENGTH_SHORT).show();
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


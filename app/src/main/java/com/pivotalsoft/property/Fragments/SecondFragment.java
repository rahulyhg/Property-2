package com.pivotalsoft.property.Fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.vision.text.Line;
import com.pivotalsoft.property.Adapter.BuyerSearchAdapter;
import com.pivotalsoft.property.Adapter.SellerPropertyAdapter;
import com.pivotalsoft.property.AddPropertyActivity;
import com.pivotalsoft.property.FilterActivity;
import com.pivotalsoft.property.PropertyDetailsActivity;
import com.pivotalsoft.property.PropertyImagesActivity;
import com.pivotalsoft.property.R;
import com.pivotalsoft.property.Response.BuyerSearchItem;
import com.pivotalsoft.property.Response.SellerPropertyItem;
import com.pivotalsoft.property.WelcomeActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment implements View.OnClickListener {
    private final int REQUEST_CODE_PLACEPICKER = 1;
    private static final int REQUEST_LOCATION = 1;
    LinearLayout buyerLayout,sellerLayout;
    TextView txtLocation;

    private RecyclerView recyclerBuyer,recyclerViewSeller;
    // buyer
    private BuyerSearchAdapter buyerSearchAdapter;
    private List<BuyerSearchItem> buyerSearchItems = new ArrayList<>();
    // seller
    private SellerPropertyAdapter sellerPropertyAdapter;
    private List<SellerPropertyItem> sellerPropertyItems = new ArrayList<>();

    final CharSequence[] propertytype = {"Apartments", "Plots/Lands", "Ind House"};
    final CharSequence[] catagoery = {"Sale", "Rent"};

    String spproperty, spcategory;

    View rootView;

    public SecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_second, container, false);

        // To retrieve value from shared preference in another activity
        SharedPreferences sp = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE); // 0 for private mode
       String Role = sp.getString("role", null);

        buyerLayout = (LinearLayout) rootView.findViewById(R.id.buyerLayout);
        sellerLayout =(LinearLayout)rootView.findViewById(R.id.sellerLayout);


        if (Role.equalsIgnoreCase("Buyer")) {
            sellerLayout.setVisibility(View.GONE);
            buyerLayout.setVisibility(View.VISIBLE);
            buyerLayoutData();

        } else {
            buyerLayout.setVisibility(View.GONE);
            sellerLayout.setVisibility(View.VISIBLE);
            sellerLayoutData();
        }

        return rootView;
    }

    private void buyerLayoutData() {

        txtLocation = (TextView) rootView.findViewById(R.id.txtLocation);
        txtLocation.setOnClickListener(this);

        Spinner spin = (Spinner) rootView.findViewById(R.id.spinner1);
        final Spinner spin2 = (Spinner) rootView.findViewById(R.id.spinner2);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, propertytype);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter bb = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, catagoery);
        bb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin2.setAdapter(bb);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Toast.makeText(getContext(),qualificationList[i] ,Toast.LENGTH_LONG).show();
                spproperty = String.valueOf(propertytype[i]);
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);

                if (i == 1) {
                    spin2.setEnabled(false);
                } else {
                    spin2.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getContext(),experienceList[i] ,Toast.LENGTH_LONG).show();
                spcategory = String.valueOf(catagoery[i]);
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        recyclerBuyer = (RecyclerView) rootView.findViewById(R.id.recyclerBuyer);
        LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerBuyer.setLayoutManager(mLayoutManager1);
        recyclerBuyer.setItemAnimator(new DefaultItemAnimator());
        buyerSearchAdapter = new BuyerSearchAdapter(getContext(), buyerSearchItems);
        recyclerBuyer.setAdapter(buyerSearchAdapter);

        FloatingActionButton floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fabBuyer);
        floatingActionButton.setOnClickListener(this);

        prepareBuyerDate();
    }
    private void prepareBuyerDate() {

        BuyerSearchItem addCardItem = new BuyerSearchItem("$380,000", "2bd 1ba 950sqft", "123 Mountain Rd Unit 6 Eastmont, Walnut Creel,","https://i.pinimg.com/736x/47/b9/7e/47b97e62ef6f28ea4ae2861e01def86c--my-dream-house-dream-big.jpg");
        buyerSearchItems.add(addCardItem);

        addCardItem = new BuyerSearchItem("$580,000", "2bd 1ba 950sqft", "123 Mountain Rd Unit 6 Eastmont, Walnut Creel,","https://i.ytimg.com/vi/mb4irevK_Is/maxresdefault.jpg");
        buyerSearchItems.add(addCardItem);

        addCardItem = new BuyerSearchItem("$980,000", "2bd 1ba 950sqft", "123 Mountain Rd Unit 6 Eastmont, Walnut Creel,","https://www.eface.in/wp-content/uploads/2013/02/best-indian-house-models-Photo10.jpg");
        buyerSearchItems.add(addCardItem);

        addCardItem = new BuyerSearchItem("$780,000", "2bd 1ba 950sqft", "123 Mountain Rd Unit 6 Eastmont, Walnut Creel,","http://gkdes.com/wp-content/uploads/2017/06/exterior-houses-design-home-design-image-gallery-in-exterior-houses-design-house-decorating.jpg");
        buyerSearchItems.add(addCardItem);

        buyerSearchAdapter.notifyDataSetChanged();
    }

    private void sellerLayoutData(){

        recyclerViewSeller = (RecyclerView) rootView.findViewById(R.id.recyclerSeller);
        LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewSeller.setLayoutManager(mLayoutManager1);
        recyclerViewSeller.setItemAnimator(new DefaultItemAnimator());
        sellerPropertyAdapter = new SellerPropertyAdapter(getContext(), sellerPropertyItems);
        recyclerViewSeller.setAdapter(sellerPropertyAdapter);

        FloatingActionButton floatingActionButtonseller = (FloatingActionButton) rootView.findViewById(R.id.fabSeller);
        floatingActionButtonseller.setOnClickListener(this);

        prepareSellerDate();

    }

    private void prepareSellerDate() {

        SellerPropertyItem addCardItem = new SellerPropertyItem("$380,000", "2bd 1ba 950sqft", "123 Mountain Rd Unit 6 Eastmont, Walnut Creel,","https://i.pinimg.com/736x/47/b9/7e/47b97e62ef6f28ea4ae2861e01def86c--my-dream-house-dream-big.jpg");
        sellerPropertyItems.add(addCardItem);

        addCardItem = new SellerPropertyItem("$580,000", "2bd 1ba 950sqft", "123 Mountain Rd Unit 6 Eastmont, Walnut Creel,","https://i.ytimg.com/vi/mb4irevK_Is/maxresdefault.jpg");
        sellerPropertyItems.add(addCardItem);

        addCardItem = new SellerPropertyItem("$980,000", "2bd 1ba 950sqft", "123 Mountain Rd Unit 6 Eastmont, Walnut Creel,","https://www.eface.in/wp-content/uploads/2013/02/best-indian-house-models-Photo10.jpg");
        sellerPropertyItems.add(addCardItem);

        addCardItem = new SellerPropertyItem("$780,000", "2bd 1ba 950sqft", "123 Mountain Rd Unit 6 Eastmont, Walnut Creel,","http://gkdes.com/wp-content/uploads/2017/06/exterior-houses-design-home-design-image-gallery-in-exterior-houses-design-house-decorating.jpg");
        sellerPropertyItems.add(addCardItem);

        sellerPropertyAdapter.notifyDataSetChanged();
    }

    // google place picker
    private void startPlacePickerActivity() {
        PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
        // this would only work if you have your Google Places API working

        try {
            Intent intent = intentBuilder.build(getActivity());
            startActivityForResult(intent, REQUEST_CODE_PLACEPICKER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displaySelectedPlaceFromPlacePicker(Intent data) {
        Place placeSelected = PlacePicker.getPlace(data, getActivity());
        Log.e("placeSelected", "" + placeSelected);

        String name = placeSelected.getName().toString();

        // places latitude
        LatLng qLoc = placeSelected.getLatLng();
        Double lat = qLoc.latitude;
        Log.e("lat", "Place: " + lat);
        //latitude = String.valueOf(lat);

        // places longitude
        Double lon = qLoc.longitude;
        Log.e("lon", "Place: " + lon);
        //longitude =String.valueOf(lon);

        Geocoder gcd = new Geocoder(getContext(), Locale.ENGLISH);
        List<Address> addresses;

        try {
            addresses = gcd.getFromLocation(lat, lon, 1);
            if (addresses.size() > 0)

            {
                String address = addresses.get(0).getAddressLine(0);
                String locality = addresses.get(0).getLocality().toString();
                String subLocality = addresses.get(0).getSubLocality().toString();
                String AddressLine = addresses.get(0).getAddressLine(0).toString();
                //locTextView.setText(cityname);

                if (locality != null) {
                    txtLocation.setText(locality);
                } else {
                    Toast.makeText(getContext(), "No data Found for this ... Please Choose Another City", Toast.LENGTH_SHORT).show();
                }
                // }
                Log.e("locality", "" + locality);
                Log.e("SubLocality", "" + subLocality);
                Log.e("AddressLine", "" + AddressLine);
            }

        } catch (IOException e) {
            e.printStackTrace();

        }


      /*  TextView enterCurrentLocation = (TextView) findViewById(R.id.textView9);
        enterCurrentLocation.setText(name + ", " + address);*/
    }

    //handling the image chooser activity result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == REQUEST_CODE_PLACEPICKER) {
                displaySelectedPlaceFromPlacePicker(data);
            }
        }
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.txtLocation:

                startPlacePickerActivity();
                break;

            case R.id.fabBuyer:

                Intent intent = new Intent(getContext(), FilterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;

            case R.id.fabSeller:

                Intent intent1 = new Intent(getContext(), AddPropertyActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                break;
        }
    }
}

package com.pivotalsoft.property.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pivotalsoft.property.Adapter.BuyerSearchAdapter;
import com.pivotalsoft.property.Adapter.FavAdapter;
import com.pivotalsoft.property.Adapter.SellerLeadsAdapter;
import com.pivotalsoft.property.R;
import com.pivotalsoft.property.Response.BuyerSearchItem;
import com.pivotalsoft.property.Response.FavItem;
import com.pivotalsoft.property.Response.SellerLeadsItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThirdFragment extends Fragment {

    private RecyclerView recyclerFav,recyclerViewSeller;
    private FavAdapter favAdapter;
    private List<FavItem> favItemList =new ArrayList<>();

    private SellerLeadsAdapter sellerLeadsAdapter;
    private List<SellerLeadsItem> sellerLeadsItems =new ArrayList<>();
    View rootView;
    public ThirdFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_third, container, false);

        // To retrieve value from shared preference in another activity
        SharedPreferences sp = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE); // 0 for private mode
        String Role = sp.getString("role", null);

        recyclerFav = (RecyclerView) rootView.findViewById(R.id.recyclerBuyer);
        recyclerViewSeller = (RecyclerView) rootView.findViewById(R.id.recyclerSeller);

        if (Role.equalsIgnoreCase("Buyer")) {
            recyclerViewSeller.setVisibility(View.GONE);
            recyclerFav.setVisibility(View.VISIBLE);
            LinearLayoutManager mLayoutManager1= new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
            recyclerFav.setLayoutManager(mLayoutManager1);
            recyclerFav.setItemAnimator(new DefaultItemAnimator());
            favAdapter=new FavAdapter(getContext(),favItemList);
            recyclerFav.setAdapter(favAdapter);

            prepareBuyerDate();

        } else {
            recyclerFav.setVisibility(View.GONE);
            recyclerViewSeller.setVisibility(View.VISIBLE);
            LinearLayoutManager mLayoutManager1= new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
            recyclerViewSeller.setLayoutManager(mLayoutManager1);
            recyclerViewSeller.setItemAnimator(new DefaultItemAnimator());
            sellerLeadsAdapter=new SellerLeadsAdapter(getContext(),sellerLeadsItems);
            recyclerViewSeller.setAdapter(sellerLeadsAdapter);

            prepareSellerDate();
        }



        return rootView;
    }
    private void prepareBuyerDate(){

        FavItem addCardItem = new FavItem("Fully Furnished Flat","879456123","$ 3000","https://i.pinimg.com/736x/47/b9/7e/47b97e62ef6f28ea4ae2861e01def86c--my-dream-house-dream-big.jpg");
        favItemList.add(addCardItem);

        addCardItem = new FavItem("Fully Furnished Flat2","879456123","$ 5000","https://i.ytimg.com/vi/mb4irevK_Is/maxresdefault.jpg");
        favItemList.add(addCardItem);

        addCardItem =  new FavItem("Fully Furnished Flat","879456123","$ 8000","https://www.eface.in/wp-content/uploads/2013/02/best-indian-house-models-Photo10.jpg");
        favItemList.add(addCardItem);

        addCardItem =  new FavItem("Fully Furnished Flat","879456123","$ 4000","http://gkdes.com/wp-content/uploads/2017/06/exterior-houses-design-home-design-image-gallery-in-exterior-houses-design-house-decorating.jpg");
        favItemList.add(addCardItem);

        favAdapter.notifyDataSetChanged();
    }

    private void prepareSellerDate(){

        SellerLeadsItem addCardItem = new SellerLeadsItem("Suresh","879456123","suresh@gmail.com","https://d30y9cdsu7xlg0.cloudfront.net/png/17241-200.png");
        sellerLeadsItems.add(addCardItem);

        addCardItem = new SellerLeadsItem("Mahesh","879456123","mahesh@gmail.com","https://pbs.twimg.com/profile_images/838349703653830656/4lZUA9c4_400x400.jpg");
        sellerLeadsItems.add(addCardItem);

        addCardItem =  new SellerLeadsItem("Vinay","879456123","vinay@gmail.com","http://www.inkbrothers.com.au/uploads/3/0/2/3/3023284/people_orig.png");
        sellerLeadsItems.add(addCardItem);

        addCardItem =  new SellerLeadsItem("swaroop","879456123","swaroop@gmail.com","https://cdn.iconscout.com/public/images/icon/free/png-512/avatar-user-teacher-312a499a08079a12-512x512.png");
        sellerLeadsItems.add(addCardItem);

        sellerLeadsAdapter.notifyDataSetChanged();
    }


}

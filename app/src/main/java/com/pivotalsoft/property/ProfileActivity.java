package com.pivotalsoft.property;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.pivotalsoft.property.Adapter.DocumentsAdapter;
import com.pivotalsoft.property.Adapter.SkillsAdapter;
import com.pivotalsoft.property.App.AppController;
import com.pivotalsoft.property.Constants.Constants;
import com.pivotalsoft.property.Response.DocumentsItem;
import com.pivotalsoft.property.Response.SkillsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    Dialog dialog;
    private FirebaseAuth mAuth;
    CircleImageView imageProfile;
    String message,consultantName;
    private ProgressDialog pDialog;

    private RecyclerView recyclerSkills,recyclerDocuments;

    private SkillsAdapter skillsAdapter;
    private List<SkillsItem> skillsItemList= new ArrayList<>();

    private DocumentsAdapter documentsAdapter;
    private List<DocumentsItem> documentsItemList= new ArrayList<>();

    ImageButton imgCandidate,imagebuttonAboutme,ibPersonalInfo;
    TextView txtFullName,txtAboutme,txtNewSkills,txtNewDocuments,txtRole,
            txtLocation,txtEmail,txtMobile,txtExperience,txtQulaification,txtdob,txtLanguages;
    String role,userid,mobileno,fullname,about,profilepic,location,emailid,experience,qualification,dob,languages,gender,latitude,longitude;
    String PERSONAL_INFO_URL,SKILL_URL,DOC_URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Profile");
        mAuth = FirebaseAuth.getInstance();

        // Progress dialog
        pDialog = new ProgressDialog(ProfileActivity.this);
        pDialog.setCancelable(false);

        // To retrieve value from shared preference in another activity
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE); // 0 for private mode
        mobileno = sp.getString("mobileno", null);
        userid = sp.getString("userid", null);
        role = sp.getString("role", null);
        Log.e("ROle",""+role);
        String status = sp.getString("status", null);
        String usercode =sp.getString("usercode",null);


        txtNewSkills=(TextView)findViewById(R.id.txtSkills);
        txtNewSkills.setOnClickListener(this);

        txtNewDocuments=(TextView)findViewById(R.id.txtDocuments);
        txtNewDocuments.setOnClickListener(this);

        // first card

        imgCandidate=(ImageButton)findViewById(R.id.imageButtonCandidate);
        imgCandidate.setOnClickListener(this);

        ibPersonalInfo=(ImageButton)findViewById(R.id.ibPersonalInfo);
        ibPersonalInfo.setOnClickListener(this);



        PERSONAL_INFO_URL= Constants.GET_PERSONAL_DATA_URL+"2";
        Log.e("URL2",""+PERSONAL_INFO_URL);


        DOC_URL=Constants.DOCUMENTS_URL+"2";
        SKILL_URL=Constants.SKILLS_URL+"2";


        preparePersonalInfoData();

        imageProfile=(CircleImageView)findViewById(R.id.imageProfile);
        txtLocation=(TextView)findViewById(R.id.txtLocation);
        txtEmail=(TextView)findViewById(R.id.txtEmail);
        txtMobile=(TextView)findViewById(R.id.txtMobile);
        txtExperience=(TextView)findViewById(R.id.txtExperience);

        txtdob=(TextView)findViewById(R.id.txtDob);

        txtFullName=(TextView)findViewById(R.id.txtFullName);
        txtRole =(TextView)findViewById(R.id.txtRole);
        txtRole.setText(role);

        //Second card

        recyclerSkills = (RecyclerView)findViewById(R.id.recyclerSkills);
        recyclerDocuments = (RecyclerView)findViewById(R.id.recyclerDocuments);



        GridLayoutManager mLayoutManager3 = new GridLayoutManager(ProfileActivity.this, 1);
        recyclerSkills.setLayoutManager(mLayoutManager3);
        recyclerSkills.setItemAnimator(new DefaultItemAnimator());


        GridLayoutManager mLayoutManager4 = new GridLayoutManager(ProfileActivity.this, 1);
        recyclerDocuments.setLayoutManager(mLayoutManager4);
        recyclerDocuments.setItemAnimator(new DefaultItemAnimator());


        prepareSkillsData();
        prepareDocumentsData();


    }

    private void preparePersonalInfoData() {

        showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                PERSONAL_INFO_URL, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                Log.d("", response.toString());

                Log.e("URL",""+PERSONAL_INFO_URL);

                try {
                    // Parsing json object response
                    // response will be a json object
                    //experienceItemList.clear();

                    //Get the instance of JSONArray that contains JSONObjects
                    JSONArray jsonArray1 = response.optJSONArray("onedata");

                    //Iterate the jsonArray and print the info of JSONObjects
                    for (int i = 0; i < jsonArray1.length(); i++) {

                        JSONObject jsonObject = jsonArray1.getJSONObject(i);

                        String personalid = jsonObject.getString("personalid");
                        fullname = jsonObject.getString("fullname");
                        about = jsonObject.getString("about");
                        location = jsonObject.getString("location");
                        latitude = jsonObject.getString("latitude");
                        longitude = jsonObject.getString("longitude");
                        emailid = jsonObject.getString("emailid");
                        dob = jsonObject.getString("dob");
                        experience = jsonObject.getString("experience");
                        qualification = jsonObject.getString("qualification");
                        gender = jsonObject.getString("gender");
                        languages = jsonObject.getString("languages");
                        profilepic = Constants.IMAGE_PROFILE_URL+jsonObject.getString("profilepic");
                        String userid = jsonObject.getString("userid");


                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("fullName", fullname);  // Saving string
                        Log.e("FULLNAME",""+fullname);
                        // Save the changes in SharedPreferences
                        editor.apply(); // commit changes

                        txtFullName.setText(fullname);
                        txtLocation.setText(location);
                        txtEmail.setText(emailid);
                        txtMobile.setText(mobileno);
                        txtExperience.setText(experience);
                        txtdob.setText(dob);


                        try {
                            Glide.with(ProfileActivity.this).load(profilepic).into(imageProfile);
                        }catch (Exception e){
                            e.printStackTrace();
                        }



                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ProfileActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("", "Error: " + error.getMessage());
                // Toast.makeText(OffersActivity.this, "no data Found. check once data is enable or not..", Toast.LENGTH_LONG).show();
                // hide the progress dialog
                hidepDialog();
            }
        });

      /*  //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(OffersActivity.this);

        //Adding request to the queue
        requestQueue.add(jsonObjReq);*/

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){


            case R.id.imageButtonCandidate:

                Intent intent1 =new Intent(ProfileActivity.this,AddPersonalInformationActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent1.putExtra("fullname",fullname);
                intent1.putExtra("about",about);
                intent1.putExtra("location",location);
                intent1.putExtra("latitude",latitude);
                intent1.putExtra("longitude",longitude);
                intent1.putExtra("emailid",emailid);
                intent1.putExtra("experience",experience);
                intent1.putExtra("qualification",qualification);
                intent1.putExtra("dob",dob);
                intent1.putExtra("languages",languages);
                intent1.putExtra("gender",gender);
                intent1.putExtra("profilepic",profilepic);
                startActivity(intent1);
                break;






            case R.id.ibPersonalInfo:

                Intent intent =new Intent(ProfileActivity.this,AddPersonalInformationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("fullname",fullname);
                intent.putExtra("about",about);
                intent.putExtra("location",location);
                intent.putExtra("latitude",latitude);
                intent.putExtra("longitude",longitude);
                intent.putExtra("emailid",emailid);
                intent.putExtra("experience",experience);
                intent.putExtra("qualification",qualification);
                intent.putExtra("dob",dob);
                intent.putExtra("languages",languages);
                intent.putExtra("gender",gender);
                intent.putExtra("profilepic",profilepic);
                startActivity(intent);

                break;



            case R.id.txtSkills:

                Intent intentSkills =new Intent(ProfileActivity.this,AddCertificatesActivity.class);
                intentSkills.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentSkills);

                break;

            case R.id.txtDocuments:

                Intent intentDocuments =new Intent(ProfileActivity.this,AddDocumentsActivity.class);
                intentDocuments.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentDocuments);

                break;


        }



    }




    // education data
    private void prepareSkillsData() {

        showpDialog();



        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                SKILL_URL, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                Log.d("", response.toString());

                Log.e("URL",""+SKILL_URL);

                try {
                    // Parsing json object response
                    // response will be a json object
                    skillsItemList.clear();

                    //Get the instance of JSONArray that contains JSONObjects
                    JSONArray jsonArray1 = response.optJSONArray("skilldata");


                    //Iterate the jsonArray and print the info of JSONObjects
                    for (int i = 0; i < jsonArray1.length(); i++) {

                        JSONObject jsonObject = jsonArray1.getJSONObject(i);

                        String skillid = jsonObject.getString("skillid");
                        String institute = jsonObject.getString("institute");
                        String course = jsonObject.getString("course");
                        String startyear = jsonObject.getString("startyear");
                        String endyear = jsonObject.getString("endyear");
                        String userid = jsonObject.getString("userid");

                        skillsItemList.add(new SkillsItem(skillid,institute,course,startyear,endyear,userid));
                        skillsAdapter = new SkillsAdapter(ProfileActivity.this, skillsItemList);
                        recyclerSkills.setAdapter(skillsAdapter);
                        skillsAdapter.notifyDataSetChanged();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ProfileActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("", "Error: " + error.getMessage());
                // Toast.makeText(OffersActivity.this, "no data Found. check once data is enable or not..", Toast.LENGTH_LONG).show();
                // hide the progress dialog
                hidepDialog();
            }
        });



        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }

    // education data
    private void prepareDocumentsData() {

        showpDialog();



        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                DOC_URL, null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
                Log.d("", response.toString());

                Log.e("URL",""+DOC_URL);

                try {
                    // Parsing json object response
                    // response will be a json object
                    documentsItemList.clear();

                    //Get the instance of JSONArray that contains JSONObjects
                    JSONArray jsonArray1 = response.optJSONArray("documentdata");


                    //Iterate the jsonArray and print the info of JSONObjects
                    for (int i = 0; i < jsonArray1.length(); i++) {

                        JSONObject jsonObject = jsonArray1.getJSONObject(i);

                        String documentid = jsonObject.getString("documentid");
                        String documenttype = jsonObject.getString("documenttype");
                        String documentno = jsonObject.getString("documentno");
                        String documenturl = Constants.IMAGE_DOCUMENTS_URL+jsonObject.getString("documenturl");
                        Log.e("docurl",""+documenturl);
                        String userid = jsonObject.getString("userid");

                        documentsItemList.add(new DocumentsItem(documentid,documenttype,documentno,documenturl,userid));
                        documentsAdapter = new DocumentsAdapter(ProfileActivity.this, documentsItemList);
                        recyclerDocuments.setAdapter(documentsAdapter);
                        documentsAdapter.notifyDataSetChanged();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ProfileActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("", "Error: " + error.getMessage());
                // Toast.makeText(OffersActivity.this, "no data Found. check once data is enable or not..", Toast.LENGTH_LONG).show();
                // hide the progress dialog
                hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    private void signOut() {
        mAuth.signOut();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);//Menu Resource, Menu

       // MenuItem item = menu.findItem(R.id.action_usercode);

       /* if (role.equals("Recruiter")){
            item.setVisible(false);
        }
        else {
            item.setVisible(true);
        }*/
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_signout:
                signOut();

                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();

                Intent i = new Intent(ProfileActivity.this,WelcomeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(i);

                break;


            case R.id.action_share:

                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.pivotalsoft.user.hikestreet&hl=en");
                startActivity(Intent.createChooser(share, "Share Property app"));

                break;

            case android.R.id.home:
                onBackPressed();
                break;


        }
        return true;

    }






}


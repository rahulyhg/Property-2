package com.pivotalsoft.property;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.pivotalsoft.property.Constants.Constants;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class EditDocumentActivity extends AppCompatActivity implements View.OnClickListener {
    final int RADIOBUTTON_ALERTDIALOG = 1;
    final boolean checked_state[] = {false, false, false};
    final CharSequence[] day_radio = {"Aadhaar Card", "PAN Card", "Passport", "Driving License"};
    //Image request code
    private int PICK_IMAGE_REQUEST1 = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    //Bitmap to get image from gallery
    private Bitmap bitmap;

    //Uri to store the image uri
    private Uri filePath1;

    private ProgressDialog pDialog;
    EditText etDocumentType, etDocumentNo;
    String documenttype, documentno, documentid, userid;
    Button uploadButton;
    ImageView imageDocument;
    TextInputLayout txtdocumentNo;
    FloatingActionButton fab, fabDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_certifcate);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Edit Documents");

        requestStoragePermission();

        // To retrieve value from shared preference in another activity
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE); // 0 for private mode
        userid = sp.getString("userid", null);
        Log.e("Userid", "" + userid);

        documenttype = getIntent().getStringExtra("documenttype");
        documentno = getIntent().getStringExtra("documentno");
        String document = getIntent().getStringExtra("documenturl");
        Log.e("IMAGE", "" + document);
        documentid = getIntent().getStringExtra("documentid");

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        etDocumentType = (EditText) findViewById(R.id.etDocumentType);
        etDocumentType.setText(documenttype);
        etDocumentType.setInputType(InputType.TYPE_NULL);
        etDocumentType.requestFocus();
        etDocumentType.setOnClickListener(this);

        etDocumentNo = (EditText) findViewById(R.id.etCourse);
        etDocumentNo.setText(documentno);

        uploadButton = (Button) findViewById(R.id.uploadButton);
        uploadButton.setOnClickListener(this);

        fab = (FloatingActionButton) findViewById(R.id.buttonSave);
        fab.setOnClickListener(this);

        fabDelete = (FloatingActionButton) findViewById(R.id.buttonDelete);
        fabDelete.setOnClickListener(this);

        imageDocument = (ImageView) findViewById(R.id.imageDocument);

        try {
            Glide.with(this).load(document).into(imageDocument);
        } catch (Exception e) {
            e.printStackTrace();
        }

        txtdocumentNo = (TextInputLayout) findViewById(R.id.courseWrapper);
        txtdocumentNo.setHint(documenttype + " Number*");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.etDocumentType:
                //triggering the radio alertdialog
                 showDialog(RADIOBUTTON_ALERTDIALOG);
                // documentDilogue();
                break;

            case R.id.uploadButton:
                showFileChooser1(PICK_IMAGE_REQUEST1);
                imageDocument.setVisibility(View.VISIBLE);
                break;

            case R.id.buttonSave:
                saveDate();
                break;

            case R.id.buttonDelete:
                delete();
                break;

            default:
                break;
        }

    }


    /*triggered by showDialog method. onCreateDialog creates a dialog*/
    @Override
    public Dialog onCreateDialog(int id) {
        switch (id) {

            case RADIOBUTTON_ALERTDIALOG:

                AlertDialog.Builder builder2 = new AlertDialog.Builder(EditDocumentActivity.this)
                        .setTitle("Certificate Type*")
                        .setSingleChoiceItems(day_radio, -1, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
// TODO Auto-generated method stub
                                // Toast.makeText(getApplicationContext(),"The selected" + day_radio[which], Toast.LENGTH_LONG).show();

                                etDocumentType.setText(day_radio[which]);

                                uploadButton.setVisibility(View.VISIBLE);
                                txtdocumentNo.setVisibility(View.VISIBLE);
                                txtdocumentNo.setHint(day_radio[which] + " Number*");

//dismissing the dialog when the user makes a selection.
                                dialog.dismiss();
                            }
                        });
                AlertDialog alertdialog2 = builder2.create();
                return alertdialog2;
        }
        return null;

    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
// TODO Auto-generated method stub

        switch (id) {

            case RADIOBUTTON_ALERTDIALOG:
                AlertDialog prepare_radio_dialog = (AlertDialog) dialog;
                ListView list_radio = prepare_radio_dialog.getListView();
                for (int i = 0; i < list_radio.getCount(); i++) {
                    list_radio.setItemChecked(i, false);
                }
                break;

        }
    }


    private void saveDate() {

        documenttype = etDocumentType.getText().toString().trim();
        documentno = etDocumentNo.getText().toString().trim();

        if (!documenttype.isEmpty() && !documentno.isEmpty()) {

            //uploadCoverImageMultipart();

        } else {
            Toast.makeText(getApplicationContext(), "Please enter your details!", Toast.LENGTH_LONG).show();
        }
    }

    /*
      * This is the method responsible for image upload
      * We need the full image path and the name for the image in this method
      * */
/*public void uploadCoverImageMultipart() {


        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Uploading.....");
        showDialog();

        //getting the actual path of the image
        String path1 = getPath(filePath1);



        Log.e("Path",""+path1+"\n"+path1);


        //Uploading code
        try {
        String uploadId = UUID.randomUUID().toString();



        //Creating a multi part request
        new MultipartUploadRequest(this, uploadId, Constants.UPDATE_DOCUMENTS_URL)
        .addFileToUpload(path1, "imagePath") //Adding file
        .addParameter("documenttype",documenttype)//Adding text parameter to the request
        .addParameter("documentno",documentno)//Adding text parameter to the request
        .addParameter("userid",userid)//Adding text parameter to the request
        .addParameter("documentid",documentid)//Adding text parameter to the request

        .setNotificationConfig(new UploadNotificationConfig())
        .setMaxRetries(2)
        .startUpload(); //Starting the upload

        new Handler().postDelayed(new Runnable() {
@Override
public void run() {

        Intent pivotal = new Intent(EditDocumentActivity.this, ProfileActivity.class);
        pivotal.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(pivotal);
        Toast.makeText(EditDocumentActivity.this,"Document Updated Successfully",Toast.LENGTH_LONG).show();

        hideDialog();
        }
        }, 5000);




        } catch (Exception exc) {
        Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }


        }*/
//method to show file chooser
    private void showFileChooser1(int req_code) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select file to upload "), req_code);
    }


    //handling the image chooser activity result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath1 = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath1);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] imageInByte = stream.toByteArray();
                int size = imageInByte.length / 1024;
                Log.e("LENTGH", "" + size);

                if (size < 200) {

                    imageDocument.setImageBitmap(bitmap);
                } else {

                    Toast.makeText(EditDocumentActivity.this, "please select File size must be 200 kb  or below.", Toast.LENGTH_LONG).show();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //method to get the file path from uri
    public String getPath(Uri uri) {
        Cursor cursor = this.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = this.getContentResolver().query(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;

       /* String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);*/
    }


    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission


        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }


    private void delete() {

        String DELETE_URL = Constants.DOCUMENTS_URL + documentid;
        Log.e("deleteurl", "" + DELETE_URL);
        StringRequest dr = new StringRequest(Request.Method.DELETE, DELETE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.e("response", "" + response);

                        Intent delete = new Intent(EditDocumentActivity.this, ProfileActivity.class);
                        delete.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(delete);
                        Toast.makeText(EditDocumentActivity.this, "Document Delete Successfully", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error.
                        Log.e("error", "" + error);
                    }
                }
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(dr);
    }


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
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


}




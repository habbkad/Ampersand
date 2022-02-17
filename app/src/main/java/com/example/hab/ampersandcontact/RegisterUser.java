package com.example.hab.ampersandcontact;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class RegisterUser extends AppCompatActivity {


    private Button button;
    private EditText userFullname;
    private EditText userPassword;
    private EditText userEmail;
    private EditText userTwitter;
    private EditText userLinkedin;
    private  EditText userRole;
    private EditText userPhonenumber;
    private ImageButton userpic;
    private static final int PICK_IMAGE_REQUEST=1;
    private Uri mImageUri;

    String picURL;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        userEmail=findViewById(R.id.userEmail);
        userFullname=findViewById(R.id.userFullname);
        userLinkedin=findViewById(R.id.userLinkedin);
        userRole=findViewById(R.id.userRole);
        userPassword=findViewById(R.id.userPassword);
        userPhonenumber=findViewById(R.id.userPhonenumber);
        userTwitter=findViewById(R.id.userTwitteracc);
        userpic=findViewById(R.id.userImage);




        button=findViewById(R.id.button);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        userpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
            }
               /* UploadTask uploadTask;
                StorageReference storageRef = storage.getReference();
               StorageReference imagesRef = storageRef.child("images");
                StorageReference fileReference = mstorageRef.child(System.currentTimeMillis()
                        + "." + getFileExtention(mImageUri));
                // mProgressBar.setVisibility(View.VISIBLE);
                mUploadTask = fileReference.putFile(mImageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                                String uploadId = mDatabaseRef.push().getKey();
                                String userId = currentFirebaseUser.getUid();*/

    });
    }


    public void registerUser(){


        final String email=userEmail.getText().toString().trim();
        final String fullName=userFullname.getText().toString().trim();
        final String linkedIn=userLinkedin.getText().toString().trim();
        final String Role=userRole.getText().toString().trim();
        final String phoneNo=userPhonenumber.getText().toString().trim();
        final String twitter=userTwitter.getText().toString().trim();
        final String password=userPassword.getText().toString().trim();



        final String url="https://ampersand-contact-exchange-api.herokuapp.com/api/v1/register";

    RequestQueue queue= Volley.newRequestQueue(this);

   /*JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
            (Request.Method.POST, url,null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {

                    Log.i("Habb123", response.toString());
                    Toast.makeText(RegisterUser.this, "success", Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO: Handle error
                    Toast.makeText(RegisterUser.this, "Unsuccess", Toast.LENGTH_LONG).show();
        error.printStackTrace();
                    Log.i("Error", error.toString());
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        //This indicates that the request has either time out or there is no connection
                        Toast.makeText(getApplicationContext(),"Check your internet and try again!", Toast.LENGTH_LONG).show();

                    } else if (error instanceof AuthFailureError) {
                        //Error indicating that there was an Authentication Failure while performing the request
                        Toast.makeText(getApplicationContext(), "This Email is already registered, please use a different one", Toast.LENGTH_LONG).show();

                    } else if (error instanceof ServerError) {
                        //Indicates that the server responded with a error response
                        Toast.makeText(getApplicationContext(), "Server error! Try again later", Toast.LENGTH_LONG).show();

                    } else if (error instanceof NetworkError) {
                        //Indicates that there was network error while performing the request
                        Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_LONG).show();

                    } else // Indicates that the server response could not be parsed
                        if (error instanceof ParseError)
                            Toast.makeText(getApplicationContext(), "Parse Error", Toast.LENGTH_LONG).show();

                }
            })



    {

        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String ,String> postparams= new HashMap<>();
            postparams.put("email", "tsenju42@gmail.com");
            postparams.put("password", "Asamawu123");
            postparams.put("firstName", "hab");
            postparams.put("lastName", "kad");
            postparams.put("photo", "hm");
            postparams.put("role","Manager");
            postparams.put("phoneNumber", "020220020");
            postparams.put("twitter", "habkad");
            postparams.put("linkedIn", "https://www.linkedin.com/in/habb122");
            postparams.put("website", "www.ampersandllc.com");

            return postparams;

        }



    };



// Access the RequestQueue through your singleton class.
      RequestQueue requestQueue= Volley.newRequestQueue(this);
    jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
            30000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
    ));
      requestQueue.add(jsonObjectRequest);

      jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
              30000,
              DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
              DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
      ));
      */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);



        StringRequest postRequest=new StringRequest(Request.Method.POST, url,
              new Response.Listener<String>() {
                  @Override
                  public void onResponse(String response) {


                      try {
                          JSONObject jsonObject=new JSONObject(response);
                          JSONObject jsonObject1=jsonObject.getJSONObject("user");
                          String id=jsonObject1.getString("_id");

                      } catch (JSONException e) {
                          e.printStackTrace();
                      }


                      Log.i("Habb123", response.toString());
                      Toast.makeText(RegisterUser.this, "success", Toast.LENGTH_LONG).show();
                      Intent intent=new Intent(RegisterUser.this,LoginUser.class);
                      startActivity(intent);
                      finish();

                  }
              },
              new Response.ErrorListener() {
                  @Override
                  public void onErrorResponse(VolleyError error) {

                      Toast.makeText(RegisterUser.this, "Unsuccess", Toast.LENGTH_LONG).show();
                      error.printStackTrace();
                      Log.i("Error", error.toString());
                      if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                          //This indicates that the request has either time out or there is no connection
                          Toast.makeText(getApplicationContext(),"Check your internet and try again!", Toast.LENGTH_LONG).show();

                      } else if (error instanceof AuthFailureError) {
                          //Error indicating that there was an Authentication Failure while performing the request
                          Toast.makeText(getApplicationContext(), "This Email is already registered, please use a different one", Toast.LENGTH_LONG).show();

                      } else if (error instanceof ServerError) {
                          //Indicates that the server responded with a error response
                          Toast.makeText(getApplicationContext(), "Server error! Try again later", Toast.LENGTH_LONG).show();

                      } else if (error instanceof NetworkError) {
                          //Indicates that there was network error while performing the request
                          Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_LONG).show();

                      } else // Indicates that the server response could not be parsed
                          if (error instanceof ParseError)
                              Toast.makeText(getApplicationContext(), "Parse Error", Toast.LENGTH_LONG).show();

                  }


    }){


          @Override
          protected Map<String, String> getParams() {
              Map<String ,String> postparams= new HashMap<>();
              postparams.put("email", email);
              postparams.put("password", password);
              postparams.put("firstName", fullName);
             // postparams.put("lastName", "kad");
           //   postparams.put("photo", "hm");
              postparams.put("role",Role);
              postparams.put("phoneNumber", phoneNo);
              postparams.put("twitter", twitter);
              postparams.put("linkedIn", linkedIn);
              postparams.put("photo", imageString);


          //   postparams.put("website", "www.ampersandllc.com");

              return postparams;
          }
      };

      queue.add(postRequest);




    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();

            try {
                //getting image from gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                //Setting image to ImageView
                userpic.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}

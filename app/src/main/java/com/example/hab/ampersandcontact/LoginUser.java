package com.example.hab.ampersandcontact;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginUser extends AppCompatActivity {

  private   Button userSignin;
    private EditText loginEmail;
    private EditText loginPassword;
    SharedPreferences.Editor edit;
    private TextView toSignUp;

    String URL="https://ampersand-contact-exchange-api.herokuapp.com/api/v1/login";
    SharedPreferences.Editor editor;
    SharedPreferences prefs;
    String loginID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        editor= getSharedPreferences("token",MODE_PRIVATE).edit();


        userSignin=findViewById(R.id.signIn);
        loginEmail=findViewById(R.id.loginEmail);
        loginPassword=findViewById(R.id.loginPassword);
        toSignUp=findViewById(R.id.toSigUp);

        userSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               userLogin();           }
        });
        prefs = getSharedPreferences("token", MODE_PRIVATE);
        loginID = prefs.getString("_id", null);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        editor.clear();
        editor.apply();

        toSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginUser.this,RegisterUser.class);
                startActivity(intent);

            }
        });

        if (loginID != null){

            Intent intent=new Intent(LoginUser.this,MainActivity.class);
            startActivity(intent);
        }

    }


    public void userLogin(){

        final String email=loginEmail.getText().toString().trim();
        final String password=loginPassword.getText().toString().trim();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                Toast.makeText(LoginUser.this, "Successful login", Toast.LENGTH_LONG).show();
                Log.i("Suckess",response.toString());

                try {
                    JSONObject jsonObject1=new JSONObject(response);

                    JSONObject jsonObject=jsonObject1.getJSONObject("user");

                    String userId = jsonObject.getString("_id");
                    String userEmail=jsonObject.getString("email");
                    String phoneNumber=jsonObject.getString("phoneNumber");
                    String userRole=jsonObject.getString("role");
                    String userlinkedIn=jsonObject.getString("linkedIn");
                    String userTwitter=jsonObject.getString("twitter");
                    String userPhoto=jsonObject.getString("photo");
                    String userName=jsonObject.getString("firstName");


                    Toast.makeText(LoginUser.this, userId, Toast.LENGTH_LONG).show();



                    editor.putString("_id",userId);
                    editor.putString("email",userEmail);
                    editor.putString("phoneNumber",phoneNumber);
                    editor.putString("role",userRole);
                    editor.putString("linkedIn",userlinkedIn);
                    editor.putString("twitter",userTwitter);
                    editor.putString("photo",userPhoto);
                    editor.putString("name",userName);
                    editor.putString("website","https://ampersand-contact-exchange-api.herokuapp.com/api/v1/profile/"+userId);

                    editor.apply();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.i("Suckess",response.toString());
                Intent intent=new Intent(LoginUser.this,MainActivity.class);
                startActivity(intent);
                finish();


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginUser.this, "Unsucessful", Toast.LENGTH_LONG).show();
                        error.printStackTrace();
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
            protected Map<String, String> getParams() {
                Map<String,String> params=new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }

        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        requestQueue.add(stringRequest);
    }

        public void registerUser(){

            final String email=loginEmail.getText().toString().trim();
            final String password=loginPassword.getText().toString().trim();

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("email", email);
                jsonObject.put("password", password);


                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL
                        , null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            JSONObject jsonObject = response.getJSONObject("user");

                            String userId = jsonObject.getString("_id");
                            String userEmail = jsonObject.getString("email");
                            String phoneNumber = jsonObject.getString("phoneNumber");
                            String userRole = jsonObject.getString("role");
                            String userlinkedIn = jsonObject.getString("linkedIn");
                            String userTwitter = jsonObject.getString("twitter");
                            String userPhoto = jsonObject.getString("photo");
                            String userName = jsonObject.getString("firstName");

                            Toast.makeText(LoginUser.this, userId, Toast.LENGTH_LONG).show();

                            SharedPreferences.Editor editor = getSharedPreferences("token", MODE_PRIVATE).edit();
                            editor.putString("_id", userId);
                            editor.putString("email", userEmail);
                            editor.putString("phoneNumber", phoneNumber);
                            editor.putString("role", userRole);
                            editor.putString("linkedIn", userlinkedIn);
                            editor.putString("twitter", userTwitter);
                            editor.putString("photo", userPhoto);
                            editor.putString("name", userName);
                            editor.apply();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        Log.i("Suckess", response.toString());
                        Intent intent = new Intent(LoginUser.this, MainActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(LoginUser.this, "Unsucessful", Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            //This indicates that the request has either time out or there is no connection
                            Toast.makeText(getApplicationContext(), "Check your internet and try again!", Toast.LENGTH_LONG).show();

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

                }) {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> heaers=new HashMap<>();
                        heaers.put("Content-Type","application/json");
                        heaers.put("Accept","application/json");
                        return heaers;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(this);
                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                        50000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                ));
                requestQueue.add(jsonObjectRequest);
            }catch (JSONException e){
                e.printStackTrace();
            }
    }


}

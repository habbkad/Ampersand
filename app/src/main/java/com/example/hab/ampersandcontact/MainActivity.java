package com.example.hab.ampersandcontact;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    SharedPreferences prefs;
    String loginID;


    private ImageView qr_Im,userPro;

    private IntentIntegrator qrScan;
    private Button scanQr,btnSignIn;
    private SharedPreferences.Editor editor;

    private TextView userName,userRole;
    public final static int WHITE = 0xFFFFFFFF;
    public final static int BLACK = 0xFF000000;
    public final static int WIDTH = 400;
    public final static int HEIGHT = 400;
    public final static String STR = "https://ampersand-contact-exchange-api.herokuapp.com/api/v1/profile/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        userName=findViewById(R.id.userName);
        userRole=findViewById(R.id.userRole);
        userPro=findViewById(R.id.userPic);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        editor= getSharedPreferences("id",MODE_PRIVATE).edit();


        prefs = getSharedPreferences("token", MODE_PRIVATE);
        loginID = prefs.getString("_id", null);
        String fullname = prefs.getString("name", null);
        String role = prefs.getString("role", null);
        String linkedIn = prefs.getString("linkedIn", null);
        String email = prefs.getString("email", null);
        String phoneNom = prefs.getString("phoneNumber", null);
        String twitter = prefs.getString("twitter", null);
        String photo = prefs.getString("photo", null);
        String website = prefs.getString("website", null);


        editor=prefs.edit();
        editor.apply();
        scanQr=findViewById(R.id.scan);
        try {
            URL url=new URL(photo);
            Uri myUri = Uri.parse(url.toString());

            Picasso.with(getApplicationContext()).load(myUri).placeholder(R.mipmap.ic_launcher).fit().into(userPro);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        userName.setText(fullname);
        userRole.setText(role);



        scanQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qrImageScan();
            }
        });
        qrScan = new IntentIntegrator(this);



        qr_Im = findViewById(R.id.qr_view);
        try {
            Bitmap bitmap = encodeAsBitmap(fullname + ", " + role + ", " + phoneNom
                    + ", " + photo + ", " + linkedIn + ", " + twitter + ", " + website + ", "
                    + email, BarcodeFormat.QR_CODE, 400, 400);
            qr_Im.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }








        //shared pref


        Toast.makeText(this, loginID, Toast.LENGTH_SHORT).show();
       // Log.i("IDDDD", loginID);

        //  getDetails();

//        if (loginID==null){
//
//                    Intent intent=new Intent(MainActivity.this,LoginUser.class);
//                    startActivity(intent);
//                    finish();
//                }





    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            editor.clear();
            editor.apply();
            Intent intent=new Intent(MainActivity.this,GettingStarted.class);
            startActivity(intent);
            finish();

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(getApplicationContext(), RegisterUser.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(MainActivity.this, LoginUser.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void getDetails() {
        prefs = getSharedPreferences("token", MODE_PRIVATE);
        String ID = prefs.getString("_id", null);
        final String token = prefs.getString("token", null);
        String URL = "https://ampersand-contact-exchange-api.herokuapp.com/api/v1/profile/" + ID;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("BOOM", response.toString());
                        Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this, "Unsuccess", Toast.LENGTH_LONG).show();
                error.printStackTrace();
                Log.i("Error", error.toString());
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
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                headers.put("x-access-token", token);
                return headers;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        requestQueue.add(stringRequest);
    }

    Bitmap encodeAsBitmap(String str, BarcodeFormat qrCode, int i, int i1) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, null);

        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }

        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }


    public void qrImageScan() {
        qrScan.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result.getContents() != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Scanned", Toast.LENGTH_LONG).show();
            String QRresult = result.getContents();
            String[] parts = QRresult.split(",");

            String name = parts[0];
            String role = parts[1];
            String phone = parts[2];
            String photo = parts[3];
            String linkedin = parts[4];
            String twitter = parts[5];
            String website = parts[6];
            String email = parts[7];

            Intent intent = new Intent(MainActivity.this, contactprofile.class);
            intent.putExtra("name", name);
            intent.putExtra("role", role);
            intent.putExtra("phone", phone);
            intent.putExtra("photo", photo);
            intent.putExtra("linkedin", linkedin);
            intent.putExtra("twitter", twitter);
            intent.putExtra("website", website);
            intent.putExtra("email", email);
            startActivity(intent);
        }
    }
        else{
            super.onActivityResult(requestCode, resultCode, data);
    }



    }

}



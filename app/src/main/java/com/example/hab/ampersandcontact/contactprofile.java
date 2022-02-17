package com.example.hab.ampersandcontact;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class contactprofile extends AppCompatActivity {

    private ImageView proPic;
    private TextView proSite,proPhone,proEmail,proName,proRole;
    private ImageButton proTwitter,proLink;
    String twitterc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactprofile);

        proPic=findViewById(R.id.Pic);
        proSite=findViewById(R.id.website);
        proPhone=findViewById(R.id.phoneNo);
        proEmail=findViewById(R.id.email);
        proLink=findViewById(R.id.linkedin);
        proTwitter=findViewById(R.id.twitter);
        proName=findViewById(R.id.uname);
        proRole=findViewById(R.id.role);

        String name=getIntent().getStringExtra("name");
        String role=getIntent().getStringExtra("role");
        String phone=getIntent().getStringExtra("phone");
        String photo=getIntent().getStringExtra("photo");
        final String linkedin=getIntent().getStringExtra("linkedin");
         twitterc=getIntent().getStringExtra("twitter");
        String website=getIntent().getStringExtra("website");
        String email=getIntent().getStringExtra("email");

        if (photo != null){

            try {
                URL myurl=new URL(photo);

                Uri myUri = Uri.parse(myurl.toString());

                Picasso.with(getApplicationContext()).load(myUri).placeholder(R.mipmap.ic_launcher).fit().into(proPic);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }





        }

        proRole.setText(role);
        proName.setText(name);
        proPhone.setText(phone);
        proSite.setText(website);
        proEmail.setText(email);

        proTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTwitter(getApplicationContext());
            }
        });

        proLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLinkedInPage(linkedin);
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contactprofile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.addContact) {

            Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
// Sets the MIME type to match the Contacts Provider
            intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);


            /*
             * Inserts new data into the Intent. This data is passed to the
             * contacts app's Insert screen
             */
            intent.putExtra(ContactsContract.Intents.Insert.NAME,proName.getText());
// Inserts an email address
            intent.putExtra(ContactsContract.Intents.Insert.EMAIL,proEmail.getText())
/*
 * In this example, sets the email type to be a work email.
 * You can set other email types as necessary.
 */
                    .putExtra(ContactsContract.Intents.Insert.EMAIL_TYPE,
                            ContactsContract.CommonDataKinds.Email.TYPE_WORK)
// Inserts a phone number
                    .putExtra(ContactsContract.Intents.Insert.PHONE,proPhone.getText())
/*
 * In this example, sets the phone type to be a work phone.
 * You can set other phone types as necessary.
 */
                    .putExtra(ContactsContract.Intents.Insert.PHONE_TYPE,
                            ContactsContract.CommonDataKinds.Phone.TYPE_WORK);

            startActivity(intent);
                  }

        return super.onOptionsItemSelected(item);
    }



        public void openTwitter(Context context){

            Intent intent = null;
            try {
                // get the Twitter app if possible
                context.getPackageManager().getPackageInfo("com.twitter.android", 0);
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=<"+twitterc+">"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            } catch (Exception e) {
                // no Twitter app, revert to browser
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/<place_user_name_here>"));
            }
            startActivity(intent);
        }

    public void openLinkedInPage(String linkedId) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("linkedin://add/%@" + linkedId));
        final PackageManager packageManager = getApplicationContext().getPackageManager();
        final List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.isEmpty()) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.linkedin.com/profile/view?id=" + linkedId));
        }
        startActivity(intent);
    }

}

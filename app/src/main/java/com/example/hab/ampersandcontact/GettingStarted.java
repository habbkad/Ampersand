package com.example.hab.ampersandcontact;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

public class GettingStarted extends AppCompatActivity {

    SharedPreferences prefs;
    String loginID;
    private TextView signIn;
    private TextView register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_started);

        signIn=findViewById(R.id.sigin);
        register=findViewById(R.id.register);

        prefs = getSharedPreferences("token", MODE_PRIVATE);
        loginID = prefs.getString("_id", null);

        if (loginID !=null){

            Intent intent= new Intent(GettingStarted.this,MainActivity.class);
            startActivity(intent);
        }

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text="SIGN IN";
                SpannableString content = new SpannableString(text);
                content.setSpan(new UnderlineSpan(), 0, text.length(), 0);
                signIn.setText(content);
                Intent intent= new Intent(GettingStarted.this,LoginUser.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent intent= new Intent(GettingStarted.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}

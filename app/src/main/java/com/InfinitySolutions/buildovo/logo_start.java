package com.InfinitySolutions.buildovo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class logo_start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo_start);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent toHomeScreen=new Intent(logo_start.this,login_activity.class);
                startActivity(toHomeScreen);
                finish();
            }
        },3500);

    }
}

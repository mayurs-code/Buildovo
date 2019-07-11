package com.InfinitySolutions.buildovo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.InfinitySolutions.buildovo.ui.login.LoginActivitySecond;

public class logo_start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo_start);
        ImageView logo=(ImageView) findViewById(R.id.image_logo_view);
//        Animation scaleOne=new ScaleAnimation(0.6f,1f,0.6f,1f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
//        scaleOne.setFillAfter(true);
//        scaleOne.setDuration(1000);
//        logo.startAnimation(scaleOne);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toHomeScreen=new Intent(logo_start.this, LoginActivitySecond.class);
                startActivity(toHomeScreen);
            }
        });



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent toHomeScreen=new Intent(logo_start.this,LoginActivitySecond.class);
                startActivity(toHomeScreen);
            }
        },1000);

    }
}

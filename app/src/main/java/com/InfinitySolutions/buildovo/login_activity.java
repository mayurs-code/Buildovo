package com.InfinitySolutions.buildovo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class login_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        Button login= (Button)findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeIntent();
            }
        });

        TextView skip_text=(TextView)findViewById(R.id.skip_text_view);
        skip_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeIntent();
            }
        });
    }
    public void homeIntent()
    {
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);

        finish();
    }
}

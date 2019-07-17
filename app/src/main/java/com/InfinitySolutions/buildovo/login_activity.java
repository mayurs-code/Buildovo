package com.InfinitySolutions.buildovo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

public class login_activity extends AppCompatActivity {

    public userData[] userdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);



        TextView skip_text=(TextView)findViewById(R.id.skip_text_view);
        skip_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeIntent();
            }
        });
        final EditText contact_number=(EditText) findViewById(R.id.contact_number);
        contact_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()!=10)
                {
                    contact_number.setTextColor(getResources().getColor(R.color.red));
                }
                else {
                    contact_number.setTextColor(getResources().getColor(R.color.green));
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        {
            RequestQueue queue = Volley.newRequestQueue(this);
            String url ="https://bldvtest.herokuapp.com/api/user";

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(response);
                            Gson gson=new Gson();
                            userdata=gson.fromJson(response,userData[].class);

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }


            });

// Add the request to the RequestQueue.
            queue.add(stringRequest);

        }//Request Queue
    }


    public void login_click(View view) throws JSONException {
        Button login= (Button)findViewById(R.id.login_button);
        final EditText contact_number=(EditText) findViewById(R.id.contact_number);

        EditText email=(EditText) findViewById(R.id.user_name_text);
        EditText pass=(EditText) findViewById(R.id.user_password_text);

        TextView text91_login=(TextView)findViewById(R.id.number91);












        if(text91_login.getVisibility()==View.GONE)
        {
            String userEmail= email.getText().toString().trim().toLowerCase();
            String password=pass.getText().toString();
            JSONObject user_cred= new JSONObject();
            user_cred.put("email",userEmail);
            user_cred.put("password",password);
            {
                System.out.println("WORK................");
                RequestQueue queue = Volley.newRequestQueue(this);

                String url = "https://bldvtest.herokuapp.com/api/user/login/";

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.POST, url, user_cred, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("Response Got",response.toString());
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error
                                System.out.println(error.networkResponse.toString());

                            }
                        });

                queue.add(jsonObjectRequest);



            } //request post json

        }
        else {

            if (contact_number.getCurrentTextColor() == getResources().getColor(R.color.red)) {
                Toast.makeText(this, "Invalid Number", Toast.LENGTH_SHORT).show();

            } else {

                for (userData i : userdata) {
                    System.out.println(i.getUser().getContact() + "***" + contact_number.getText());
                    if (i.getUser().getContact().equals(contact_number.getText().toString())) {
                        homeIntent(i);
                    }
                }

            }
        }



    }



    public void alternate_click(View view)
    {
        TextView alternate=(TextView)findViewById(R.id.alternate_idpass);
        TextView text91=(TextView)findViewById(R.id.number91);
        EditText contactno=(EditText) findViewById(R.id.contact_number);
        EditText user=(EditText) findViewById(R.id.user_name_text);
        EditText pass=(EditText) findViewById(R.id.user_password_text);

        if(text91.getVisibility()==View.GONE)
        {
            text91.setVisibility(View.VISIBLE);
            contactno.setVisibility(View.VISIBLE);
            user.setVisibility(View.GONE);
            pass.setVisibility(View.GONE);
        }
        else {
            user.setVisibility(View.VISIBLE);
            pass.setVisibility(View.VISIBLE);
            text91.setVisibility(View.GONE);
            contactno.setVisibility(View.GONE);
        }

    }

    public void homeIntent()
    {
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);

        finish();
    }
    public void homeIntent(userData x)
    {
        Intent i=new Intent(this,MainActivity.class);
        i.putExtra("logged_in_user",x);
        startActivity(i);

        finish();
    }
}

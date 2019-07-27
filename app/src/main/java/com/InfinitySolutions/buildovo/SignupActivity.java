package com.InfinitySolutions.buildovo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.InfinitySolutions.buildovo.ui.login.LoginActivitySecond;
import com.InfinitySolutions.buildovo.ui.login.LoginViewModel;
import com.InfinitySolutions.buildovo.ui.login.LoginViewModelFactory;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

public class SignupActivity extends AppCompatActivity {


    private LoginViewModel signInViewModel;
    private Context signInContext;

    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        {
            signInViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                    .get(LoginViewModel.class);
            signInContext = this;
        }
        getlocation();
        on_nextClickSignup();
        {
            MaterialButton buttonCurrentLocation = findViewById(R.id.currentSignLocationButton);
            buttonCurrentLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final ContentLoadingProgressBar pbar = findViewById(R.id.progressBar_signup);
                    final TextView descriptionAddress = findViewById(R.id.description_address);
                    final TextView Address = findViewById(R.id.address);
                    Address.setText(descriptionAddress.getText());

                    //pbar.setIndeterminate(true);
                    descriptionAddress.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            pbar.setIndeterminate(false);

                        }
                    });


                }
            });

        }//current location


    }

    public void getlocation()
    {

        {
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.ENGLISH);
                    try {
                        List<Address> listAddress = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        if (listAddress != null && listAddress.size() > 0) {
                            TextView location_header = findViewById(R.id.description_address);

                            String address = "";
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            Log.i("Address PROJECT", listAddress.get(0).toString());
                            address = listAddress.get(0).getAddressLine(0);
                            location_header.setText(address);
                            //Log.i("Address PROJECT",address.substring(0,address.indexOf(',')));


                        }
                    } catch (Exception e) {

                    }


                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            };
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }//location Service

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);


            }
        }
    }//location Permission


    public void volley_userDetails(String name,String email,String password,String address,String contact)
    {
        JSONObject user_cred= new JSONObject();
        System.out.println("LOGIN CLICKED");

        try {
            user_cred.put("email",email);
            user_cred.put("password",password);
            user_cred.put("address",address);
            user_cred.put("contact",contact);
            user_cred.put("name",name);
            System.out.println(user_cred.toString());




        } catch (JSONException e) {
            e.printStackTrace();
        }

        {
            System.out.println("WORK................");
            RequestQueue queue = Volley.newRequestQueue(signInContext);

            String url = "https://bldvtest.herokuapp.com/api/signup/user/";

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, url, user_cred, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.d("Response Got",response.getJSONObject("user").toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Gson gson =new Gson();
                            Toast.makeText(signInContext, ""+response.toString(), Toast.LENGTH_SHORT).show();
                            System.out.println("EXERC"+response.toString());
                            login_activity();



                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error
                            //System.out.println("eRROR"+error.networkResponse.toString());

                        }
                    });

            queue.add(jsonObjectRequest);



        } //request post json
    }

    public void login_activity(){
        Intent i=new Intent(this, LoginActivitySecond.class);
        startActivity(i);
        finish();
    }



    public void on_nextClickSignup() {

        final TextInputEditText username = findViewById(R.id.username_signup);
        final TextInputEditText password = findViewById(R.id.password_signup);
        final TextInputEditText email = findViewById(R.id.email);
        final TextInputEditText contact = findViewById(R.id.contact);
        final TextInputLayout usernameLayout = findViewById(R.id.usernamelLayout);
        final TextInputLayout passwordLayout = findViewById(R.id.paswordlLayout);
        final TextInputLayout emailLayout = findViewById(R.id.emailLayout);
        final TextInputLayout contactLayout = findViewById(R.id.contactLayout);
        final TextInputEditText address = findViewById(R.id.address);
        final TextInputLayout addressLayout = findViewById(R.id.addressLayout);
        final MaterialButton backButton = findViewById(R.id.signupbackButton);
        final MaterialButton nextButton = findViewById(R.id.signupnextButton);
        final MaterialButton currentLocation = findViewById(R.id.currentSignLocationButton);
        final TextView descriptionSignup = findViewById(R.id.description_signup);
        final TextView descriptionAddress = findViewById(R.id.description_address);
        final ContentLoadingProgressBar pbar = findViewById(R.id.progressBar_signup);
        final MaterialCheckBox checkBox = findViewById(R.id.checkBoxTerms);
        nextButton.setEnabled(true);
//        username.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                pbar.setProgress(0);
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                pbar.setProgress(charSequence.length()/2);
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int progress = pbar.getProgress();

                if (progress < 50 && progress >= 25) {
                    String emailTemp = email.getText().toString();


                    backButton.setVisibility(View.GONE);
                    pbar.setProgress(20);
                    usernameLayout.setVisibility(View.VISIBLE);
                    passwordLayout.setVisibility(View.GONE);
                    emailLayout.setVisibility(View.GONE);
                    contactLayout.setVisibility(View.GONE);
                    descriptionSignup.setText("Want to edit your name?");


                } else if (progress < 75 && progress >= 50) {


                    pbar.setProgress(30);
                    passwordLayout.setVisibility(View.VISIBLE);
                    emailLayout.setVisibility(View.VISIBLE);
                    contactLayout.setVisibility(View.GONE);
                    descriptionSignup.setText("Enter an email and create password");




                }else if (progress < 100 && progress >= 75) {


                    pbar.setProgress(60);
                    passwordLayout.setVisibility(View.GONE);
                    emailLayout.setVisibility(View.GONE);
                    contactLayout.setVisibility(View.VISIBLE);
                    addressLayout.setVisibility(View.GONE);
                    checkBox.setVisibility(View.GONE);
                    currentLocation.setVisibility(View.GONE);
                    descriptionAddress.setVisibility(View.GONE);
                    nextButton.setText("NEXT");
                    descriptionSignup.setText("Enter a valid number");




                }


            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int progress = pbar.getProgress();

                if (progress < 25) {
                    if (username.getText().toString().trim().length() >= 4) {
                        pbar.setProgress(25);
                        usernameLayout.setVisibility(View.GONE);
                        backButton.setVisibility(View.VISIBLE);
                        passwordLayout.setVisibility(View.VISIBLE);
                        emailLayout.setVisibility(View.VISIBLE);
                        descriptionSignup.setText("Please enter an email and create password");

                    } else {
                        Toast.makeText(signInContext, "minimum username size is 4", Toast.LENGTH_SHORT).show();
                    }
                } else if (progress < 50 && progress >= 25) {
                    String emailTemp = email.getText().toString().trim();

                    if (password.getText().length() >= 8 && emailTemp.contains("@") && emailTemp.charAt(emailTemp.length() - 4) == '.') {
                        pbar.setProgress(50);
                        passwordLayout.setVisibility(View.GONE);
                        emailLayout.setVisibility(View.GONE);
                        contactLayout.setVisibility(View.VISIBLE);
                        descriptionSignup.setText("Enter your contact number");

                    } else {
                        Toast.makeText(signInContext, "minimum password size is 8 or invalid password", Toast.LENGTH_SHORT).show();
                    }
                } else if (progress < 75 && progress >= 50) {

                    if (contact.getText().length() >=10) {
                        pbar.setProgress(75);
                        contactLayout.setVisibility(View.GONE);
                        addressLayout.setVisibility(View.VISIBLE);
                        currentLocation.setVisibility(View.VISIBLE);
                        descriptionAddress.setVisibility(View.VISIBLE);
                        nextButton.setText("signup");
                        checkBox.setVisibility(View.VISIBLE);
                        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    nextButton.setEnabled(true);
                                    pbar.setProgress(100);

                                } else nextButton.setEnabled(true);

                            }
                        });
                        nextButton.setEnabled(true);


                        descriptionSignup.setText("Tell us where you are...");

                    } else {
                        Toast.makeText(signInContext, "Invalid number", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(progress==100)
                {
                    volley_userDetails(username.getText().toString(),email.getText().toString(),password.getText().toString(),address.getText().toString(),contact.getText().toString());

                }


            }
        });
    }
}
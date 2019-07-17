package com.InfinitySolutions.buildovo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Context;
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

import com.InfinitySolutions.buildovo.ui.login.LoginViewModel;
import com.InfinitySolutions.buildovo.ui.login.LoginViewModelFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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
        on_nextClickSignup();
        {
            MaterialButton buttonCurrentLocation =(MaterialButton) findViewById(R.id.currentSignLocationButton);
            buttonCurrentLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final ContentLoadingProgressBar pbar = (ContentLoadingProgressBar) findViewById(R.id.progressBar_signup);
                    final TextView descriptionAddress = (TextView) findViewById(R.id.description_address);
                    pbar.setIndeterminate(true);
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

                    getlocation();
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
                            TextView location_header = (TextView) findViewById(R.id.description_address);

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



    public void on_nextClickSignup() {

        final TextInputEditText username = (TextInputEditText) findViewById(R.id.username);
        final TextInputEditText password = (TextInputEditText) findViewById(R.id.password);
        final TextInputEditText email = (TextInputEditText) findViewById(R.id.email);
        final TextInputEditText contact = (TextInputEditText) findViewById(R.id.contact);
        final TextInputLayout usernameLayout = (TextInputLayout) findViewById(R.id.usernamelLayout);
        final TextInputLayout passwordLayout = (TextInputLayout) findViewById(R.id.paswordlLayout);
        final TextInputLayout emailLayout = (TextInputLayout) findViewById(R.id.emailLayout);
        final TextInputLayout contactLayout = (TextInputLayout) findViewById(R.id.contactLayout);
        final TextInputEditText address = (TextInputEditText) findViewById(R.id.address);
        final TextInputLayout addressLayout = (TextInputLayout) findViewById(R.id.addressLayout);
        final MaterialButton backButton = (MaterialButton) findViewById(R.id.signupbackButton);
        final MaterialButton nextButton = (MaterialButton) findViewById(R.id.signupnextButton);
        final MaterialButton currentLocation = (MaterialButton) findViewById(R.id.currentSignLocationButton);
        final TextView descriptionSignup = (TextView) findViewById(R.id.description_signup);
        final TextView descriptionAddress = (TextView) findViewById(R.id.description_address);
        final ContentLoadingProgressBar pbar = (ContentLoadingProgressBar) findViewById(R.id.progressBar_signup);
        final MaterialCheckBox checkBox = (MaterialCheckBox) findViewById(R.id.checkBoxTerms);
        nextButton.setEnabled(true);
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                pbar.setProgress(0);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                pbar.setProgress(charSequence.length()/2);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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

                                } else nextButton.setEnabled(true);

                            }
                        });
                        nextButton.setEnabled(true);


                        descriptionSignup.setText("Tell us where you are...");

                    } else {
                        Toast.makeText(signInContext, "Invalid number", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
    }
}

package com.InfinitySolutions.buildovo;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    MaterialSearchBar map_search_field;
    TextView searched_place;
    LatLng maps_coordinates;
    LatLng current_coordinates;
    EditText typed_address;

    LocationManager locationManager;
    LocationListener locationListener;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0, locationListener);


            }
        }
    }//location Permission

    public void setCurrent_coordinates(LatLng current_coordinates) {
        this.current_coordinates = current_coordinates;
    }

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setCurrent_coordinates(new LatLng(0,0));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        Button current_location= (Button)findViewById(R.id.on_set_current_location);
        current_location.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {

                Log.i("button","Hearing");
                mMap.clear();
                Log.i("Coordinates",current_coordinates.toString());
                mMap.addMarker(new MarkerOptions().position(current_coordinates).title("Current Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(current_coordinates));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current_coordinates,15));
                TextView location_header =(TextView)findViewById(R.id.searched_textField);
                location_header.setText(get_Address(current_coordinates));

            }
        });




        map_search_field= (MaterialSearchBar) findViewById(R.id.map_search_editText);
        searched_place=(TextView)findViewById(R.id.searched_textField);
        typed_address=(EditText)findViewById(R.id.type_address);


        typed_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searched_place.setText(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });// when text is changed in add address



//        map_search_field.addTextChangeListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        } );

        map_search_field.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                String location=text.toString();
                geoLocate(location);
            }

            @Override
            public void onButtonClicked(int buttonCode) {


            }
        });




    }




    void geoLocate(String s)
    {
        Geocoder geocoder=new Geocoder(MapsActivity.this);
        List<Address> list=new ArrayList<>();
        try{
            list=geocoder.getFromLocationName(s,1);
        }
        catch (Exception e){

        }
        if(list.size()>0)
        {
            Address address =list.get(0);
            LatLng latLng=new LatLng(address.getLatitude(),address.getLongitude());
            maps_coordinates=latLng;
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(latLng).title(address.getLocality()));

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));
            searched_place.setText(address.getAddressLine(0));

        }
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    public String get_Address(LatLng latLng)
    {
        Geocoder geocoder=new Geocoder(getApplicationContext(), Locale.ENGLISH);
        try {
            List<Address> listAddress = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if(listAddress!=null && listAddress.size()>0)

            {

                String address="";
                address=listAddress.get(0).getAddressLine(0);
                return address;
            }

        }
        catch (IOException E)
        {

        }
        return "NO ADDRESS FOUND ;)";
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;





        {
            locationManager =(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
            locationListener =new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    Geocoder geocoder=new Geocoder(getApplicationContext(), Locale.ENGLISH);
                    try {
                        List<Address> listAddress=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                        if(listAddress!=null && listAddress.size()>0)

                        {
                            LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());

                            setCurrent_coordinates(latLng);


                            Log.i("ACCESS TO LOCATION",location.toString());


                        }
                    }
                    catch (Exception e)
                    {

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
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
            {

                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
            else
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0, locationListener);
            }
        }//location Service



        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng).title(get_Address(latLng)));

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,18));
                maps_coordinates=latLng;

                TextView location_header =(TextView)findViewById(R.id.searched_textField);
                location_header.setText(get_Address(latLng));






            }
        });



        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

}

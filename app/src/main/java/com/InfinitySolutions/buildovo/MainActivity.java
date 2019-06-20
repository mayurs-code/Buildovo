package com.InfinitySolutions.buildovo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static int SPLASH_TIMEOUT=1000;

    public int autoLocation=1;
    RecyclerView recyclerView;
    ServiceAdapters adapter;
    List<ServicesCard> cardList;
    LocationManager locationManager;
    LocationListener locationListener;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10000,100, locationListener);


            }
        }
    }//location Permission

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        {
            BottomNavigationView navigationView =(BottomNavigationView) findViewById(R.id.navigationView);;

        }//bottom navigation bar
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
                            TextView location_header =(TextView)findViewById(R.id.location_text_view);
                            String address="";
                            Log.i("Address PROJECT",listAddress.get(0).toString());
                            address=listAddress.get(0).getAddressLine(0);
                            if (autoLocation==1)
                                location_header.setText(address.substring(0,address.indexOf(',')));
                            Log.i("Address PROJECT",address.substring(0,address.indexOf(',')));
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
        {

            cardList =new ArrayList<>();
            recyclerView= (RecyclerView)findViewById(R.id.recycler_services);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));




            cardList.add(
                    new ServicesCard(
                            1,
                            "Service 001",
                            "Description 001",
                            "Short Description 001",
                            1000.0,
                            "Book",R.drawable.home_icon
                    )
            );
            cardList.add(
                    new ServicesCard(
                            1,
                            "Service 002",
                            "Description 001",
                            "Short Description 001",
                            1000.0,
                            "Book",R.drawable.home_icon
                    )
            );
            cardList.add(
                    new ServicesCard(
                            1,
                            "Service 003",
                            "Description 001",
                            "Short Description 001",
                            1000.0,
                            "Book",R.drawable.home_icon
                    )
            );
            cardList.add(
                    new ServicesCard(
                            1,
                            "Service 004",
                            "Description 001",
                            "Short Description 001",
                            1000.0,
                            "Book",R.drawable.home_icon
                    )
            );
            cardList.add(
                    new ServicesCard(
                            1,
                            "Service 005",
                            "Description 001",
                            "Short Description 001",
                            1000.0,
                            "Book",R.drawable.home_icon
                    )
            );





            adapter=new ServiceAdapters(this,cardList);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListner(new ServiceAdapters.OnItemClickListner() {
            @Override
            public void onItemClick(int position) {
                item_clicked(position);

            }
        });

        }//card view initialization

    }



    public void item_clicked(int position)
    {

        cardList.get(position).setTitle(cardList.get(position).getTitle()+" CLICKED");
        adapter.notifyItemChanged(position);


    }



    public void on_Location_click(View view)
    {
        LinearLayout advertize_layout=(LinearLayout)findViewById(R.id.location_Linear_layout);
        Intent i=new Intent(this,MapsActivity.class);
        startActivity(i);

        finish();

    }

    public void create_advertise_button(View view)// creating new header advertise button
    {
        ImageView sample_button =(ImageView)findViewById(R.id.ad_button_first);
        LinearLayout advertize_layout=(LinearLayout)findViewById(R.id.advertize_linear_layout);
        ImageView new_button=new ImageView(this);
        new_button.setScaleType(ImageView.ScaleType.CENTER_CROP);
        new_button.setImageDrawable(getDrawable(R.drawable.login_back));
        new_button.setLayoutParams(sample_button.getLayoutParams());
        advertize_layout.addView(new_button,0);
    }
}

package com.InfinitySolutions.buildovo;

import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static int SPLASH_TIMEOUT = 1000;
    Intent intent=getIntent();
//    userData user=(userData) intent.getSerializableExtra("logged_in_user");


    coreServices coreservices[];
    RecyclerView recyclerView;
    ServiceAdapters sadapters;
    List<ServicesCard> cardList;
    public int autoLocation = 1;
    LocationManager locationManager;
    LocationListener locationListener;
    JSONObject cardStorage;
    Context rest = this;
    SharedPreferences sharedPreferences;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);


            }
        }
    }//location Permission



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = this.getSharedPreferences("com.InfinitySolutions.buildovo", Context.MODE_PRIVATE);

        System.out.println("CARD STORAGE  " + sharedPreferences.getString("cards_data", "0"));
        System.out.println();




        {


        }//testing purpose


        {
            BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigationView);
            navigationView.setOnNavigationItemSelectedListener(this);
            navigationView.getMenu().getItem(1).setChecked(false);
            navigationView.getMenu().getItem(2).setChecked(false);
            navigationView.getMenu().getItem(0).setChecked(true);

        }//bottom navigation bar
        {
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.ENGLISH);
                    try {
                        List<Address> listAddress = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        if (listAddress != null && listAddress.size() > 0) {
                            TextView location_header = (TextView) findViewById(R.id.location_text_view);
                            String address = "";
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            Log.i("Address PROJECT", listAddress.get(0).toString());
                            address = listAddress.get(0).getAddressLine(0);
                            if (autoLocation == 1)
                                location_header.setText(address.substring(0, address.indexOf(',')));
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
        {

            cardList = new ArrayList<>();
            recyclerView = (RecyclerView) findViewById(R.id.recycler_services);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));


            {
                cardList.add(
                        new ServicesCard(
                                1,
                                "Service 001",
                                "Description 001",
                                "Short Description 001",
                                1000.0,
                                "Book", R.drawable.home_icon
                        )
                );

            }//add sample data


        }//card view initialization
        {
            create_Cateogories(getDrawable(R.drawable.all_material_bw), "Construction");
            create_Cateogories(getDrawable(R.drawable.repairsl_bw), "Repair");
            create_Cateogories(getDrawable(R.drawable.special_bw), "Professional");
            create_Cateogories(getDrawable(R.drawable.transport_bw), "Carriers");
            ImageButton b = (ImageButton) findViewById(R.id.imageButton_first_cateogory);
            b.setMaxWidth(0);
            b.setVisibility(View.GONE);

        }//Create core cateogories


    }

    public void requestGet()
    {
        {
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);
            String url ="https://bldvtest.herokuapp.com/api/core-services";


            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Gson gson=new Gson();
                            coreservices=gson.fromJson(response,coreServices[].class);


                            // Display the first 500 characters of the response string.
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });

// Add the request to the RequestQueue.
            queue.add(stringRequest);


        }//core_services_response
    }


    public void fill_cards(List<ServicesCard> cards_list, String title, RecyclerView recyclerView, ServiceAdapters adapter, int drawable) {
        cards_list.add(new ServicesCard(
                1,
                title,
                "Description 001",
                "Short Description 001",
                1000.0,
                "Book",
                drawable
        ));

        adapter = new ServiceAdapters(this, cardList);
        recyclerView.setAdapter(adapter);
        sadapters = adapter;


        sadapters.setOnItemClickListner(new ServiceAdapters.OnItemClickListner() {
            @Override
            public void onItemClick(int position) {
                item_clicked(position);


            }
        });
    }


    public void collapse_ad(View view) {
        HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
        ImageButton ib = (ImageButton) findViewById(R.id.collapse_ads);

        ViewGroup.LayoutParams vlp = hsv.getLayoutParams();
        if (vlp.height == 0) {
            ib.setRotation(0);
            vlp.height = 500;
        } else {
            ib.setRotation(180);
            vlp.height = 0;
        }
        hsv.setLayoutParams(vlp);

    }

    public void create_Cateogories(Drawable image, String text) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.service_cateogories_linear);
        LinearLayout layoutVertical = new LinearLayout(this);
        layoutVertical.setOrientation(LinearLayout.VERTICAL);
        TextView category_text = new TextView(this);
        category_text.setTextSize(10);
        category_text.setText(text);
        category_text.setGravity(Gravity.CENTER);
        ImageButton cateogory = new ImageButton(this);
        cateogory.setId(layout.getChildCount() + 100);
        cateogory.setLayoutParams(((ImageButton) findViewById(R.id.imageButton_first_cateogory)).getLayoutParams());
        cateogory.setImageDrawable(image);
        cateogory.setBackgroundColor((Color.TRANSPARENT));
        cateogory.setScaleType(ImageView.ScaleType.CENTER_CROP);
        cateogory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                switch (view.getId()) {


                    case 101: {
                        cardList = new ArrayList<>();
                        reset_all_Cateogories();
                        ImageButton b = (ImageButton) findViewById(view.getId());
                        b.setImageDrawable(getDrawable(R.drawable.all_material_co));
                        {
                            ArrayList<String> subCat = new ArrayList<>();
                            subCat.add("Construction Materials");
                            subCat.add("Sanitary and Hardwares");
                            subCat.add("Electricals and Electronics");
                            subCat.add("Paints and POP");
                            subCat.add("Plumbing");
                            subCat.add("Hardware Fixtures");


                            {
                                Gson gson = new Gson();
                                String data_gson = gson.toJson(subCat);
                                //sharedPreferences.edit().remove("cards_data");
                                sharedPreferences.edit().putString("cards_data", data_gson).apply();
                                System.out.println("CARD STORAGE  " + sharedPreferences.getString("cards_data", "0"));
                            }//store data


                            for (String i : subCat) {
                                fill_cards(cardList, i, recyclerView, sadapters, R.drawable.all_material_bw);

                            }


//
//                            sadapters.setOnItemClickListner(new ServiceAdapters.OnItemClickListner() {
//                                @Override
//                                public void onItemClick(int position) {
//                                    item_clicked(position);
//
//                                }
//                            });


                        }//add sub categories

                        Log.i("drawing", "WORKING");
                        break;

                    }
                    case 102: {
                        cardList = new ArrayList<>();
                        reset_all_Cateogories();
                        ImageButton b = (ImageButton) findViewById(view.getId());
                        b.setImageDrawable(getDrawable(R.drawable.repairsl_co));
                        {
                            ArrayList<String> subCat = new ArrayList<>();
                            subCat.add("Technicians");
                            subCat.add("Plumbers");
                            subCat.add("Carpenters");
                            subCat.add("Painters");
                            subCat.add("Welders");
                            subCat.add("Contractors");

                            {
                                Gson gson = new Gson();
                                String data_gson = gson.toJson(subCat);
                                //sharedPreferences.edit().remove("cards_data");
                                sharedPreferences.edit().putString("cards_data", data_gson).apply();
                                System.out.println("CARD STORAGE  " + sharedPreferences.getString("cards_data", "0"));
                            }//store data




                            for (String i : subCat) {
                                fill_cards(cardList, i, recyclerView, sadapters, R.drawable.repairsl_bw);
                            }
                        }//add sub categories

                        break;

                    }
                    case 103: {
                        cardList = new ArrayList<>();
                        reset_all_Cateogories();
                        ImageButton b = (ImageButton) findViewById(view.getId());
                        b.setImageDrawable(getDrawable(R.drawable.special_co));
                        {
                            ArrayList<String> subCat = new ArrayList<>();
                            subCat.add("Plan and Desigining");
                            subCat.add("Interior and exterior Desigining");
                            subCat.add("Site Engineers");

                            {
                                Gson gson = new Gson();
                                String data_gson = gson.toJson(subCat);
                                //sharedPreferences.edit().remove("cards_data");
                                sharedPreferences.edit().putString("cards_data", data_gson).apply();
                                System.out.println("CARD STORAGE  " + sharedPreferences.getString("cards_data", "0"));
                            }//store data


                            for (String i : subCat)
                                fill_cards(cardList, i, recyclerView, sadapters, R.drawable.special_bw);
                        }//add sub categories
                        break;

                    }
                    case 104: {
                        cardList = new ArrayList<>();
                        reset_all_Cateogories();
                        ImageButton b = (ImageButton) findViewById(view.getId());
                        b.setImageDrawable(getDrawable(R.drawable.transport_co));
                        {
                            ArrayList<String> subCat = new ArrayList<>();
                            subCat.add("Light duty vehicle");
                            subCat.add("Medium duty vehicle");
                            subCat.add("Heavy duty vehicle");
                            subCat.add("Construction Vehicle");
                            subCat.add("JCB's and Road Rollers");


                            {
                                Gson gson = new Gson();
                                String data_gson = gson.toJson(subCat);
                                //sharedPreferences.edit().remove("cards_data");
                                sharedPreferences.edit().putString("cards_data", data_gson).apply();
                                System.out.println("CARD STORAGE  " + sharedPreferences.getString("cards_data", "0"));
                            }//store data


                            for (String i : subCat)
                                fill_cards(cardList, i, recyclerView, sadapters, R.drawable.transport_bw);
                        }//add sub categories
                        break;

                    }

                }

            }
        });

        layoutVertical.addView(cateogory);
        layoutVertical.addView(category_text);
        layout.addView(layoutVertical);


    }

    public void reset_all_Cateogories() {

        ImageButton b1 = (ImageButton) findViewById((int) 101);
        ImageButton b2 = (ImageButton) findViewById((int) 102);
        ImageButton b3 = (ImageButton) findViewById((int) 103);
        ImageButton b4 = (ImageButton) findViewById((int) 104);
        b1.setImageDrawable(getDrawable(R.drawable.all_material_bw));
        b2.setImageDrawable(getDrawable(R.drawable.repairsl_bw));
        b3.setImageDrawable(getDrawable(R.drawable.special_bw));
        b4.setImageDrawable(getDrawable(R.drawable.transport_bw));


    }


    public void item_clicked(int position) {

        cardList.get(position).setTitle(cardList.get(position).getTitle() + " CLICKED");

        String Title = cardList.get(position).getTitle();
        sadapters.notifyItemChanged(position);
        change_activity();


    }

    public void change_activity() {
        Intent i = new Intent(this, ServiceClickedActivity.class);
        startActivity(i);

    }


    public void on_Location_click(View view) {

        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);

    }

    public void create_advertise_button(View view)// creating new header advertise button
    {
        ImageView sample_button = (ImageView) findViewById(R.id.ad_button_first);
        LinearLayout advertize_layout = (LinearLayout) findViewById(R.id.advertize_linear_layout);
        ImageView new_button = new ImageView(this);
        new_button.setScaleType(ImageView.ScaleType.CENTER_CROP);
        new_button.setImageDrawable(getDrawable(R.drawable.starter_logo2512));
        new_button.setLayoutParams(sample_button.getLayoutParams());
        advertize_layout.addView(new_button, 0);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        int id = menuItem.getItemId();

        switch (id) {
            case R.id.navigation_home: {
//                Intent i=new Intent(this,MainActivity.class);
//                startActivity(i);
                break;
            }
            case R.id.navigation_store: {

                Intent i = new Intent(this, StoreActivity.class);
                startActivity(i);
                break;

            }
            case R.id.navigation_services: {

                break;

            }
        }


        return false;
    }
}

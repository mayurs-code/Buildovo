package com.InfinitySolutions.buildovo;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class StoreActivity extends FragmentActivity implements OnMapReadyCallback, BottomNavigationView.OnNavigationItemSelectedListener {

    private GoogleMap mMap;
    RecyclerView recyclerView;
    StoreAdapters adapter;

    List<StoreCard> cardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        {

            cardList =new ArrayList<>();
            recyclerView= (RecyclerView)findViewById(R.id.recycler_stores);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));


            {
                cardList.add(
                        new StoreCard(
                                1,
                                "JIC",
                                "Description 001",
                                "Short Description 001",
                                "10.5", R.drawable.home_icon,
                                new LatLng(23.1469648,79.9334553)

                        )
                );

                cardList.add(
                        new StoreCard(
                                1,
                                "JEC",
                                "Description 001",
                                "Short Description 001",
                                "10.5", R.drawable.home_icon,
                                new LatLng(23.1918808,79.9848548)

                        )
                );
                cardList.add(
                        new StoreCard(
                                1,
                                "HOME",
                                "Description 001",
                                "Short Description 001",
                                "10.5", R.drawable.home_icon,
                                new LatLng(23.180081,79.9124623)

                        )
                );
                cardList.add(
                        new StoreCard(
                                1,
                                "Global",
                                "Description 001",
                                "Short Description 001",
                                "10.5", R.drawable.home_icon,
                                new LatLng(23.1998052,79.8791966)

                        )
                );

            }//add sample data





            adapter=new StoreAdapters(this,cardList);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListner(new StoreAdapters.OnItemClickListner() {
                @Override
                public void onItemClick(int position) {
                    item_clicked(position);

                }
            });

        }//card view initialization


        BottomNavigationView bottomNavigationView=(BottomNavigationView)findViewById(R.id.navigationView_store);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.getMenu().getItem(0).setChecked(false);
        bottomNavigationView.getMenu().getItem(2).setChecked(false);
        bottomNavigationView.getMenu().getItem(1).setChecked(true);
    }
    public void item_clicked(int position)
    {

        cardList.get(position).setTitle(cardList.get(position).getTitle()+" CLICKED");
        adapter.notifyItemChanged(position);


    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        int id = menuItem.getItemId();

        switch (id) {
            case R.id.navigation_home: {

                Intent i = new Intent(this, MainActivity.class);
                super.onBackPressed();
                break;
            }
            case R.id.navigation_store: {
                break;

            }
            case R.id.navigation_services: {
                break;

            }
        }
        return false;
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
    public void fill_map(GoogleMap map,List<StoreCard> cards)
    {
        for (StoreCard card:cards)
        {
            map.addMarker(new MarkerOptions().position(card.getLocation()).title(card.getTitle()));
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        fill_map(mMap,cardList);




    }
}

package com.InfinitySolutions.buildovo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.InfinitySolutions.buildovo.fullActivities.AskingActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {

    boolean loggedin_user=false;
    Context homeContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        homeContext=this;
        initializeRecyclerAdvertizeView();
        cardClickListner();
        bottomNavHandler();
        onSideNavClick();

        {
            loggedin_user=getIntent().getBooleanExtra("loggedin",false);
            //ifLoggedIn(loggedin_user);
            if(loggedin_user)
            {
                userData userdata=(userData) getIntent().getSerializableExtra("userData");
                {



                }//set name in side nav
            }

        }//intent data initialize

    }

    public void bottomNavHandler()
    {
        BottomNavigationView navigationView= findViewById(R.id.navigationView_home);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                int id = item.getItemId();

                switch (id) {
                    case R.id.navigation_home: {
//                Intent i=new Intent(this,MainActivity.class);
//                startActivity(i);
                        break;
                    }
                    case R.id.navigation_store: {

                        Intent i = new Intent(homeContext, StoreActivity.class);
                        startActivity(i);
                        break;

                    }
                    case R.id.messager_service: {
                        Intent i = new Intent(homeContext, AskingActivity.class);
                        startActivity(i);

                        break;

                    }
                }


                return false;
            }
        });
    }

    public void cardClickListner()
    {
        MaterialCardView card1= findViewById(R.id.core_service1_cardview);
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                material_activity();
            }
        });

    }



    RecyclerView advertizeRecyclerView;
    List<Advertize_data_class> advertizeDataClassList;
    AdvertizeAdapter advertizeAdapter;
    public void initializeRecyclerAdvertizeView()
    {
        advertizeDataClassList=new ArrayList<>();
        advertizeRecyclerView = findViewById(R.id.recycler_advertize_view);
        advertizeRecyclerView.setHasFixedSize(true);
            advertizeRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        advertizeDataClassList=add_advertize_card(advertizeDataClassList,"Discount Mayur",R.drawable.ad01_trial);
        advertizeDataClassList=add_advertize_card(advertizeDataClassList,"Discount Mayur",R.drawable.ad02_trial);
        advertizeDataClassList=add_advertize_card(advertizeDataClassList,"Discount Mayur",R.drawable.ad03_trial);
        System.out.println(advertizeDataClassList.size());
        advertizeAdapter=new AdvertizeAdapter(this,advertizeDataClassList);
        advertizeRecyclerView.setAdapter(advertizeAdapter);

    }//fill advertizements
    public List<Advertize_data_class> add_advertize_card(List<Advertize_data_class> advertizeDataClassList, String discription,int imageDrawable)
    {
        advertizeDataClassList.add(new Advertize_data_class(imageDrawable,discription));
        return advertizeDataClassList;
    }

    @SuppressLint("WrongConstant")
    public void openDrawer(View view){
        DrawerLayout drawerLayout= findViewById(R.id.drawer_layout_home);
        drawerLayout.openDrawer(Gravity.START);
    }

    public void ifLoggedIn(Boolean loggedin_user)
    {
        if(loggedin_user)
        {
            userData userdata=(userData) getIntent().getSerializableExtra("userData");
            {

            }//set name in side nav
        }
    }
    public void material_activity(){
        Intent i=new Intent(this,MaterialsActivity.class);
        startActivity(i);
    }

    public void profile_activity(){
        Intent i=new Intent(this,Profile_activity.class);
        startActivity(i);
    }

    public void onSideNavClick()
    {
        NavigationView sideNav= findViewById(R.id.side_nav_home);
        sideNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.profile_sidebar)
                {
                    profile_activity();
                }if(item.getItemId()==R.id.logout_sidebar)
                {
                    onBackPressed();
                }
                return false;
            }
        });
    }




}

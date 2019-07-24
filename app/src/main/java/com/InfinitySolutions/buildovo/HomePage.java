package com.InfinitySolutions.buildovo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {

    boolean loggedin_user=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        initializeRecyclerAdvertizeView();
        cardClickListner();

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

    public void cardClickListner()
    {
        MaterialCardView card1=(MaterialCardView)findViewById(R.id.core_service1_cardview);
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
        advertizeRecyclerView = (RecyclerView) findViewById(R.id.recycler_advertize_view);
        advertizeRecyclerView.setHasFixedSize(true);
            advertizeRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        advertizeDataClassList=add_advertize_card(advertizeDataClassList,"Discount Mayur",R.drawable.starter_logo2);
        advertizeDataClassList=add_advertize_card(advertizeDataClassList,"Discount Mayur",R.drawable.starter_logo2);
        advertizeDataClassList=add_advertize_card(advertizeDataClassList,"Discount Mayur",R.drawable.starter_logo2);
        System.out.println(advertizeDataClassList.size());
        advertizeAdapter=new AdvertizeAdapter(this,advertizeDataClassList);
        advertizeRecyclerView.setAdapter(advertizeAdapter);

    }//fill advertizements
    public List<Advertize_data_class> add_advertize_card(List<Advertize_data_class> advertizeDataClassList, String discription,int imageDrawable)
    {
        advertizeDataClassList.add(new Advertize_data_class(imageDrawable,discription));
        return advertizeDataClassList;
    }

    public void openDrawer(View view){
        DrawerLayout drawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout_home);
        //drawerLayout.openDrawer(Gravity.START);
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




}

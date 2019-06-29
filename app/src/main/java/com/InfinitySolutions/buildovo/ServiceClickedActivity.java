package com.InfinitySolutions.buildovo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ServiceClickedActivity extends AppCompatActivity {


    RecyclerView recyclerViewServices;
    RecyclerView recyclerViewProducts;
    ServiceAdaptersTile sadapters;
    ProductAdapter padapters;
    List<ProductsCard> productsCardList;
    List<ServicesCard> serviceCardList;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_clicked);
        {
            sharedPreferences = this.getSharedPreferences("com.InfinitySolutions.buildovo", Context.MODE_PRIVATE);


            serviceCardList = new ArrayList<>();
            recyclerViewServices = (RecyclerView) findViewById(R.id.recycler_view_tile);
            recyclerViewServices.setHasFixedSize(true);
            recyclerViewServices.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            JSONObject jobj;
            String s = sharedPreferences.getString("cards_data", "-1");
            System.out.println("got cards data" + s);


            Gson gson = new Gson();
            ArrayList<String> x = gson.fromJson(s, ArrayList.class);
            System.out.println("x found data" + x);

            for (String i : x)
            fill_cards(serviceCardList,i, recyclerViewServices,sadapters,R.drawable.transport_bw);


//            {
//                serviceCardList.add(
//                        new ServicesCard(
//                                1,
//                                "Service 001",
//                                "Description 001",
//                                "Short Description 001",
//                                1000.0,
//                                "Book", R.drawable.home_icon
//                        )
//                );
//                serviceCardList.add(
//                        new ServicesCard(
//                                1,
//                                "Service 001",
//                                "Description 001",
//                                "Short Description 001",
//                                1000.0,
//                                "Book", R.drawable.home_icon
//                        )
//                );
//                serviceCardList.add(
//                        new ServicesCard(
//                                1,
//                                "Service 001",
//                                "Description 001",
//                                "Short Description 001",
//                                1000.0,
//                                "Book", R.drawable.home_icon
//                        )
//                );
//
//            }//add sample data

            sadapters = new ServiceAdaptersTile(this, serviceCardList);
            recyclerViewServices.setAdapter(sadapters);
            sadapters = sadapters;


            sadapters.setOnItemClickListner(new ServiceAdaptersTile.OnItemClickListner() {
                @Override
                public void onItemClick(int position) {
                    item_clicked(position);


                }
            });

        }//adding upper categories
        {
            productsCardList =new ArrayList<>();
            recyclerViewProducts = (RecyclerView) findViewById(R.id.recycler_view_products);
            recyclerViewProducts.setHasFixedSize(true);
            recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));


            productsCardList.add(new ProductsCard(
                    R.drawable.cart_24px,
                    "Set Title",
                    "Sub Title",
                    "UNITS",

                    true,
                    500.0



            ));

            padapters=new ProductAdapter(this,productsCardList);
            recyclerViewProducts.setAdapter(padapters);

            padapters.setOnItemClickListner(new ProductAdapter.OnItemClickListner() {
                @Override
                public void onItemClick(int position) {

                }
            });

        }


    }

    public void fill_cards(List<ServicesCard> cards_list, String title, RecyclerView recyclerView, ServiceAdaptersTile adapter, int drawable) {
        cards_list.add(new ServicesCard(
                1,
                title,
                "Description 001",
                "Short Description 001",
                1000.0,
                "Book",
                drawable
        ));

        adapter = new ServiceAdaptersTile(this, serviceCardList);
        recyclerView.setAdapter(adapter);
        sadapters = adapter;


//        sadapters.setOnItemClickListner(new ServiceAdaptersTile().OnItemClickListner()
//        {
//            @Override
//            public void onItemClick(int position) {
//                item_clicked(position);
//
//
//
//            }
//        });
    }

    public void change_activity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

        finish();
    }


    public void item_clicked(int position) {

        serviceCardList.get(position).setTitle(serviceCardList.get(position).getTitle() + " CLICKED");

        String Title = serviceCardList.get(position).getTitle();
        sadapters.notifyItemChanged(position);


    }
}

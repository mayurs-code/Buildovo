package com.InfinitySolutions.buildovo;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;

public class StoringPrefrences extends Application {
    private static StoringPrefrences instance;
    Gson gson;

    @Override
    public void onCreate()
    {
        super.onCreate();
        instance=this;
        SharedPreferences data=this.getSharedPreferences("com.InfinitySolutions.buildovo", Context.MODE_PRIVATE);
        gson=new Gson();



    }


    public StoringPrefrences(String x){
        gson=new Gson();

        switch (x)
        {
            case "Construction Materials":
            {
                ArrayList<String> store=new ArrayList<>();
                store.add("Sand,Pit Sand Unit,-cubic m/kg");
                store.add("Aggregate,40 mm  12mm Unit ,-cubic m/kg");
                store.add("Cement,OPC Grade 53, PPC Unit,-Bags/Kg");
                store.add("Sand,Pit Sand Unit,-cubic m/kg");
                store.add("Sand,Pit Sand Unit,-cubic m/kg");
                store.add("Sand,Pit Sand Unit,-cubic m/kg");
                store.add("Sand,Pit Sand Unit,-cubic m/kg");

            }
        }

    }



    public static StoringPrefrences getInstance(){
        return instance;
    }

}


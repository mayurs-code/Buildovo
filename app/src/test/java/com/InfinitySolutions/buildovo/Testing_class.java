package com.InfinitySolutions.buildovo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Testing_class {


    public static void main(String args[]) throws JSONException {
        System.out.println("Hello World");
        Testing_class tc=new Testing_class();

        tc.func();




    }

    public void func() throws JSONException {

        ArrayList<String> stringArrayList=new ArrayList<>();

        stringArrayList.add("Soperd");
        stringArrayList.add("Alpedo");
        stringArrayList.add("Cypex");
        stringArrayList.add("Pexir");
        stringArrayList.add("Kerops");


        System.out.println(stringArrayList);

    }
}

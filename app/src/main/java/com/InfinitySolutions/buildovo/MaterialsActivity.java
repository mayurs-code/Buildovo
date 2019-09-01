package com.InfinitySolutions.buildovo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MaterialsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materials_activity);
        initializeMaterialCards();
        initializeActionBar();
//        volleyMaterialsRequest();




    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.construction_material_toolbar, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));
        }
        return super.onCreateOptionsMenu(menu);
    }

    public void initializeActionBar()
    {
        Toolbar toolbar= findViewById(R.id.toolbar_construction);

        setSupportActionBar(toolbar);
        ActionBar ab=getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

    }
    public void volleyMaterialsRequest()
    {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://bldvtest.herokuapp.com/api/material/construction-material";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Gson gson=new Gson();
                        JSONArray jobj=null;
                        try {
//                             jobj =new JSONObject("{ rezero:"+ response+"}");
                             jobj =new JSONArray( response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            for (int i=0;i<(jobj.length());i++)
                            {
                                JSONObject x=jobj.getJSONObject(i);
                                Log.i("Tag Red", "onResponse: "+ x);
                                Log.i("Tag inside.", "onResponse: "+ x.getString("name"));
                                Log.i("Tag inside.", "onResponse: "+ x.getString("name"));
                                detailsList=initializeFronJson(x,detailsList);
                                materialRecyclerView.setAdapter(materialAdapter);




                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MaterialsActivity.this, "That didn't work!", Toast.LENGTH_SHORT).show();
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

    List<MaterialCardDetails> initializeFronJson(JSONObject x,List<MaterialCardDetails> detailsList) throws JSONException {
        String name=x.getString("onResponse");
        System.out.println(x.getJSONArray("brands").toString());
        String desc=x.getString("description");
        String id=x.getString("_id");
        JSONObject varients=x.getJSONArray("varients").getJSONObject(0);
        String price=varients.getString("mrp");
        detailsList=addMaterialCard(detailsList,R.drawable.ad02_trial,name,desc,price,"xxx","20KG","IN STOCK");
        return detailsList;





    }




    RecyclerView materialRecyclerView;
    List<MaterialCardDetails> detailsList;
    MaterialAdapter materialAdapter;

    public void initializeMaterialCards()
    {
        detailsList=new ArrayList<>();

        materialRecyclerView= findViewById(R.id.material_recycler);
        materialRecyclerView.setHasFixedSize(true);
        materialRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        String x="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus nunc dolor, interdum nec mattis at, tincidunt ac mi. Duis dictum enim nec elit commodo vestibulum at ut nulla. Maecenas eleifend risus eu mauris egestas dignissim";
        detailsList=addMaterialCard(detailsList,R.drawable.ad02_trial,"Cement",x,"Rs. 600","5 dealers offering this","20KG","IN STOCK");
        detailsList=addMaterialCard(detailsList,R.drawable.ad02_trial,"Cement",x,"Rs. 700","5 dealers offering this","20KG","IN STOCK");
        detailsList=addMaterialCard(detailsList,R.drawable.ad02_trial,"Cement",x,"Rs. 800","5 dealers offering this","20KG","IN STOCK");
        //volleyMaterialsRequest();
        System.out.println(detailsList.size());
        materialAdapter=new MaterialAdapter(this,detailsList);
        materialAdapter.setOnItemClickListner(new MaterialAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(int position) {
                product_activity();
            }
        });
        materialRecyclerView.setAdapter(materialAdapter);

    }

    public List<MaterialCardDetails> addMaterialCard(List<MaterialCardDetails> detailsList, int imageDrawable, String title, String description, String price, String dealer, String quantity, String stock) {
        detailsList.add(new MaterialCardDetails(
                imageDrawable,
                title, "TOKEN ZERO"
                , description
                , price,
                "5KG"
                , quantity
                , "In Stock", dealer
        ));
        return detailsList;
    }

    public void product_activity()
    {
        Intent i=new Intent(this,ProductActivity.class);
        startActivity(i);
    }


}

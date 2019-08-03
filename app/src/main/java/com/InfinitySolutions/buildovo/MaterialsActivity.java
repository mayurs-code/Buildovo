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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MaterialsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materials_activity);
        initializeMaterialCards();
        initializeActionBar();




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

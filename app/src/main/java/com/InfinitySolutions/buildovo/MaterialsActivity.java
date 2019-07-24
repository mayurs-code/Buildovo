package com.InfinitySolutions.buildovo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MaterialsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materials_activity);
        initializeMaterialCards();


    }


    RecyclerView materialRecyclerView;
    List<MaterialCardDetails> detailsList;
    MaterialAdapter materialAdapter;

    public void initializeMaterialCards()
    {
        detailsList=new ArrayList<>();
        materialRecyclerView=(RecyclerView)findViewById(R.id.material_recycler);
        materialRecyclerView.setHasFixedSize(true);
        materialRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        detailsList=addMaterialCard(detailsList,R.drawable.construction_materials,"Cement","CEMENT ","Rs. 500","5 dealers offering this","20KG","IN STOCK");
        detailsList=addMaterialCard(detailsList,R.drawable.construction_materials,"Cement","CEMENT ","Rs. 500","5 dealers offering this","20KG","IN STOCK");
        detailsList=addMaterialCard(detailsList,R.drawable.construction_materials,"Cement","CEMENT ","Rs. 500","5 dealers offering this","20KG","IN STOCK");
        System.out.println(detailsList.size());
        materialAdapter=new MaterialAdapter(this,detailsList);
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
                , true, dealer
        ));
        return detailsList;
    }


}

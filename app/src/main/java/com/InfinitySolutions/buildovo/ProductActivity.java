package com.InfinitySolutions.buildovo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class ProductActivity extends AppCompatActivity {
    Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        initializeActionBar();
        clickers();


    }

    public void clickers()
    {
        TextView varientChanger= findViewById(R.id.variant_product);
        TextView mainPrice= findViewById(R.id.product_price_main);
        TextView totalPrice= findViewById(R.id.total_price_product);
        varientChanger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initializeVariants();
            }
        });


    }



    public void initializeVariants()
    {
        final Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.dialog_variant);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setTitle("AXDF");

        final TextView varientChanger= findViewById(R.id.variant_product);
        varientChanger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
        final TextView mainPrice= findViewById(R.id.product_price_main);
        ListView listView = dialog.findViewById(R.id.product_listview);
        ArrayList <String> variants=new ArrayList<String>();
        variants=addVariants(variants,"1.5mm  250sqft : 3600");
        variants=addVariants(variants,"1.5mm  600sqft : 4800");
        variants=addVariants(variants,"2.5mm  500Kg : 5000");
        variants=addVariants(variants,"2.5mm  1600Kg : 15000");
        final ArrayList<String> finalVariants = variants;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                StringTokenizer st=new StringTokenizer(finalVariants.get(i), ":");
                varientChanger.setText(st.nextToken());
                mainPrice.setText(st.nextToken());

                dialog.dismiss();

            }
        });








        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.list_sample, variants);

        listView.setAdapter(adapter);
        dialog.show();


    }
    public ArrayList<String> addVariants(ArrayList<String> list,String x)
    {
        list.add(x);
        return list;

    }


    public void initializeActionBar()
    {
        Toolbar toolbar= findViewById(R.id.toolbar_product);

        setSupportActionBar(toolbar);
        ActionBar ab=getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

    }
}

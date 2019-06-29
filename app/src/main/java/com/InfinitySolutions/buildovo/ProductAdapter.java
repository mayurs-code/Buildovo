package com.InfinitySolutions.buildovo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context mCtx;
    private List<ProductsCard> productsList;

    private ProductAdapter.OnItemClickListner mClickListner;


    public interface OnItemClickListner{
        void onItemClick(int position);

    }

    public void setOnItemClickListner(ProductAdapter.OnItemClickListner listner){
        mClickListner=listner;
    }

    public ProductAdapter(Context mCtx, List<ProductsCard> productsList) {
        this.mCtx = mCtx;
        this.productsList =  productsList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(mCtx);
        View view=inflater.inflate(R.layout.product_card_view_layout,null,true);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.WRAP_CONTENT));
        ProductViewHolder productViewHolder=new ProductViewHolder(view,mClickListner);

        return new ProductViewHolder(view,mClickListner);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        ProductsCard products=productsList.get(position);
        holder.textview_title.setText(products.getTitle());
        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(products.getImage(),null));
        holder.textView_description.setText(products.getDescription());
        holder.availability.setText(products.getAvailability()?"Available":"Not Available");

    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView textview_title, textView_description;
        Button availability;

        public ProductViewHolder(@NonNull View itemView, final ProductAdapter.OnItemClickListner listner) {
            super(itemView);

            imageView=itemView.findViewById(R.id.product_image_view);
            textview_title=itemView.findViewById(R.id.product_text_title);
            textView_description=itemView.findViewById(R.id.product_text_description);
            availability=itemView.findViewById(R.id.product_text_availability);



            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (listner!=null){

                        int position=getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION){
                            listner.onItemClick(position);
                        }
                    }

                }
            });

        }
    }


}

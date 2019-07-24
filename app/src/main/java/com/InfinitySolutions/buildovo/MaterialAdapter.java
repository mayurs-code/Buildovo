package com.InfinitySolutions.buildovo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MaterialAdapter extends RecyclerView.Adapter<MaterialAdapter.MaterialViewHolder> {
    private Context mctx;
    private List<MaterialCardDetails> materialCardDetails;
    private MaterialAdapter.OnItemClickListner itemClickListner;

    public interface OnItemClickListner {
        void onItemClick(int position);

    }

    public void setOnItemClickListner(MaterialAdapter.OnItemClickListner listner) {
        itemClickListner = listner;

    }

    public MaterialAdapter(Context mctx, List<MaterialCardDetails> materialDetails) {
        this.mctx = mctx;
        this.materialCardDetails = materialDetails;
    }


    @NonNull
    @Override
    public MaterialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mctx);
        View view = inflater.inflate(R.layout.materialdetails, null, true);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        MaterialViewHolder materialViewHolder = new MaterialViewHolder(view, itemClickListner);

        return new MaterialViewHolder(view, itemClickListner);
    }

    @Override
    public void onBindViewHolder(@NonNull MaterialViewHolder holder, int position) {
        MaterialCardDetails materialCardDetails_temp =materialCardDetails.get(position);
        holder.quantity.setText(materialCardDetails_temp.getQuantity());
        holder.totalPrice.setText(materialCardDetails_temp.getPrice());
        holder.inStock.setText(materialCardDetails_temp.getAvailability().toString());
        holder.productTitle.setText(materialCardDetails_temp.getTitle());
        holder.product_image.setImageDrawable(mctx.getResources().getDrawable(materialCardDetails_temp.getImage(),null));
        holder.productDescription.setText(materialCardDetails_temp.getDescription());
        holder.dealers.setText(materialCardDetails_temp.getDealers());
        holder.price.setText(materialCardDetails_temp.getPrice());

    }

    @Override
    public int getItemCount() {
        return materialCardDetails.size();
    }


    class MaterialViewHolder extends RecyclerView.ViewHolder {

        ImageView product_image, openHidden;
        TextView productTitle, productDescription, price, dealers, totalPrice, inStock;
        EditText quantity;
        LinearLayout layout_more;

        public MaterialViewHolder(@NonNull View itemView, final MaterialAdapter.OnItemClickListner clickListner) {
            super(itemView);

            product_image = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);
            productDescription = itemView.findViewById(R.id.product_description);
            price = itemView.findViewById(R.id.total_price_min);
            dealers = itemView.findViewById(R.id.dealers_available);
            totalPrice = itemView.findViewById(R.id.total_price);
            inStock = itemView.findViewById(R.id.stock_availability);
            quantity = itemView.findViewById(R.id.quantity_editText);
            layout_more = itemView.findViewById(R.id.hiding_layout);
            openHidden = itemView.findViewById(R.id.expanding_imageView);
            openHidden.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (layout_more.getVisibility() == View.GONE) {
                        layout_more.setVisibility(View.VISIBLE);
                    } else {
                        layout_more.setVisibility(View.GONE);
                    }
                }
            });

            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (clickListner != null) {

                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            clickListner.onItemClick(position);
                        }
                    }

                }
            });
        }
    }
}

package com.InfinitySolutions.buildovo;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdvertizeAdapter extends RecyclerView.Adapter<AdvertizeAdapter.AdvertizeViewHolder> {

    private Context mCtx;
    private List<Advertize_data_class> advertize_list;
    private AdvertizeAdapter.OnItemClickListner mClickListner;

    public AdvertizeAdapter(Context mCtx, List<Advertize_data_class> advertize_list) {
        this.mCtx = mCtx;
        this.advertize_list = advertize_list;
    }

    @NonNull
    @Override
    public AdvertizeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.advertize_card_view_layout, parent, false);
      //view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,RecyclerView.LayoutParams.WRAP_CONTENT));
        AdvertizeViewHolder advertizeViewHolder = new AdvertizeViewHolder(view, mClickListner);
        return new AdvertizeViewHolder(view, mClickListner);

    }

    @Override
    public void onBindViewHolder(@NonNull AdvertizeViewHolder holder, int position) {
        Advertize_data_class advertize_data_class = advertize_list.get(position);
        holder.textView_discount.setText(advertize_data_class.getDescription());
        holder.imageView_advertize.setImageDrawable(mCtx.getResources().getDrawable(advertize_data_class.getImage(), null));
        holder.imageView_advertize.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    @Override
    public int getItemCount() {
        return advertize_list.size();
    }


    public interface OnItemClickListner {
        void onItemClick(int position);

    }


    public class AdvertizeViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView_advertize;
        TextView textView_discount;

        public AdvertizeViewHolder(@NonNull View itemView, final AdvertizeAdapter.OnItemClickListner listner) {
            super(itemView);
            imageView_advertize = itemView.findViewById(R.id.advertize_image_view_parent);
            textView_discount = itemView.findViewById(R.id.discount_text);
            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listner != null) {

                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listner.onItemClick(position);
                        }
                    }

                }
            });
        }
    }
}




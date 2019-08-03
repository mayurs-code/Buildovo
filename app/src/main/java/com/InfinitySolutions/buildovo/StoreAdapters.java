package com.InfinitySolutions.buildovo;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StoreAdapters extends RecyclerView.Adapter<StoreAdapters.StoreViewHolder> {

    private Context mCtx;
    private List<StoreCard> StoreList;

    private StoreAdapters.OnItemClickListner mClickListner;



    public interface OnItemClickListner{
        void onItemClick(int position);

    }


    public void setOnItemClickListner(StoreAdapters.OnItemClickListner listner){
        mClickListner=listner;
    }


    public StoreAdapters(Context mCtx, List<StoreCard> StoreList) {
        this.mCtx = mCtx;
        this.StoreList = StoreList;
    }

    @NonNull
    @Override
    public StoreAdapters.StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(mCtx);
        View view =inflater.inflate(R.layout.store_card_view_layout,null,true);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        StoreAdapters.StoreViewHolder holder= new StoreAdapters.StoreViewHolder(view,mClickListner);
        return new StoreAdapters.StoreViewHolder(view,mClickListner);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreAdapters.StoreViewHolder holder, int position) {
        StoreCard Store=StoreList.get(position);

        holder.textview_title.setText(Store.getTitle());
        holder.textView_description.setText(Store.getDescription());
        holder.textView_descriptionShort.setText(Store.getDescriptionShort());
        holder.textView_distance.setText(Store.getDistance()+"distance km");


        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(Store.getImage(),null));


    }

    @Override
    public int getItemCount() {
        return StoreList.size();
    }

    class StoreViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textview_title, textView_description, textView_descriptionShort, textView_distance;


        public StoreViewHolder(@NonNull View itemView, final StoreAdapters.OnItemClickListner listner) {
            super(itemView);

            imageView= itemView.findViewById(R.id.store_image_view);
            textview_title=itemView.findViewById(R.id.store_text_title);
            textView_description=itemView.findViewById(R.id.store_text_description);
            textView_descriptionShort=itemView.findViewById(R.id.store_text_descriptionShort);
            textView_distance=itemView.findViewById(R.id.store_text_distance);

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
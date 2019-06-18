package com.InfinitySolutions.buildovo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ServiceAdapters extends RecyclerView.Adapter<ServiceAdapters.ServiceViewHolder> {

    private Context mCtx;
    private List<ServicesCard> serviceList;

    private OnItemClickListner mClickListner;

    public interface OnItemClickListner{
        void onItemClick(int position);

    }


    public void setOnItemClickListner(OnItemClickListner listner){
        mClickListner=listner;
    }


    public ServiceAdapters(Context mCtx, List<ServicesCard> serviceList) {
        this.mCtx = mCtx;
        this.serviceList = serviceList;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(mCtx);
        View view =inflater.inflate(R.layout.pallate_card_view_layout,null,true);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        ServiceViewHolder holder= new ServiceViewHolder(view,mClickListner);
        return new ServiceViewHolder(view,mClickListner);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        ServicesCard service=serviceList.get(position);

        holder.textview_title.setText(service.getTitle());
        holder.textView_description.setText(service.getDescription());
        holder.textView_descriptionShort.setText(service.getDescriptionShort());
        holder.textView_price.setText(service.getPrice()+".Rs");
        holder.textView_rating.setText(""+service.getButton_string());

        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(service.getImage(),null));


    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    class ServiceViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textview_title, textView_description, textView_descriptionShort, textView_price, textView_rating;


        public ServiceViewHolder(@NonNull View itemView, final OnItemClickListner listner) {
            super(itemView);

            imageView= itemView.findViewById(R.id.service_image_view);
            textview_title=itemView.findViewById(R.id.service_text_title);
            textView_description=itemView.findViewById(R.id.service_text_description);
            textView_descriptionShort=itemView.findViewById(R.id.service_text_descriptionShort);
            textView_price=itemView.findViewById(R.id.service_text_priceTag);
            textView_rating=itemView.findViewById(R.id.service_text_rating);

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

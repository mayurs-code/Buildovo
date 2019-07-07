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

public class ServiceAdaptersTile extends RecyclerView.Adapter<ServiceAdaptersTile.ServiceViewHolder> {

    private Context mCtx;
    private List<ServicesCard> serviceList;

    private ServiceAdaptersTile.OnItemClickListner mClickListner;

    public interface OnItemClickListner{
        void onItemClick(int position);

    }


    public void setOnItemClickListner(ServiceAdaptersTile.OnItemClickListner listner){
        mClickListner=listner;
    }


    public ServiceAdaptersTile(Context mCtx, List<ServicesCard> serviceList) {
        this.mCtx = mCtx;
        this.serviceList = serviceList;
    }

    @NonNull
    @Override
    public ServiceAdaptersTile.ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(mCtx);
        View view1 =inflater.inflate(R.layout.pallate_card_view_tile_layout,parent,false);
        //view1.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        ServiceAdaptersTile.ServiceViewHolder holder1= new ServiceAdaptersTile.ServiceViewHolder(view1,mClickListner);
        return new ServiceAdaptersTile.ServiceViewHolder(view1,mClickListner);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceAdaptersTile.ServiceViewHolder holder, int position) {
        ServicesCard service=serviceList.get(position);

        holder.textview_title.setText(service.getTitle());

        holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(service.getImage(),null));


    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    class ServiceViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textview_title;


        public ServiceViewHolder(@NonNull View itemView, final ServiceAdaptersTile.OnItemClickListner listner) {
            super(itemView);

            imageView= itemView.findViewById(R.id.service_image_view_tile);
            textview_title=itemView.findViewById(R.id.service_text_title_tile);
            itemView.setClickable(true);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listner!=null){

                        int position=getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION){
                            listner.onItemClick(position);System.out.println("SUCCESS WITH CLICK");
                        }
                    }
                }
            });

        }


    }
}
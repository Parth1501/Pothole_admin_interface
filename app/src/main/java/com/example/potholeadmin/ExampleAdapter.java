package com.example.potholeadmin;




import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.Inflater;

public class ExampleAdapter  extends RecyclerView.Adapter<ExampleAdapter.exampleHolder>  {

    List<String> date;
    List<Double> longitude;
    List<Double> latitude;
    List<String> status;
    private OnNewsClickListener mListener;
    public interface OnNewsClickListener {
        void onNewsClick(int i);
    }
    public void setOnNewsClickListener(OnNewsClickListener listener) {
        mListener=listener;
    }

    public ExampleAdapter(List<String> date, List<Double> longitude, List<Double> latitude,List<String> status) {
        this.date = date;
        this.longitude = longitude;
        this.latitude = latitude;
        this.status=status;
    }

    @NonNull
    @Override
    public exampleHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());
        View view=inflater.inflate(R.layout.potholedata,viewGroup,false);

        return new exampleHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull exampleHolder exampleHolder, int i) {
        //String news_link=info_link.get(i);
        exampleHolder.longitude_data.setText(longitude.get(i).toString());
        exampleHolder.latitude_data.setText(latitude.get(i).toString());
        exampleHolder.date_data.setText(date.get(i));
        if(status.get(i).equals("NO")) {
            exampleHolder.status_data.setTextColor(Color.RED);
        }
        else{
            exampleHolder.status_data.setTextColor(Color.GREEN);
        }
        exampleHolder.status_data.setText(status.get(i));
    }

    @Override
    public int getItemCount() {
        return longitude.size();
    }

    public static class exampleHolder extends RecyclerView.ViewHolder  {
        TextView longitude_data,latitude_data,date_data,status_data;

        public exampleHolder(@NonNull View itemView, final OnNewsClickListener listener) {
            super(itemView);
            longitude_data=itemView.findViewById(R.id.longitude_data);
            latitude_data=itemView.findViewById(R.id.latitude_data);
            date_data=itemView.findViewById(R.id.date_data);
            status_data=itemView.findViewById(R.id.status_data);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null) {
                        int i=getAdapterPosition();

                        listener.onNewsClick(i);
                    }
                }
            });


        }


    }

}

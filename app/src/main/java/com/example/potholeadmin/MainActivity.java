package com.example.potholeadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView examplelist;
    List<String> date;
    List<Double> longitude;
    List<Double> latitude;
    List<String> status;
    DatabaseReference ref;
    ExampleAdapter madapter;
    Data data;
    private boolean once=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        date = new ArrayList<>();
        longitude = new ArrayList<>();
        latitude = new ArrayList<>();
        status=new ArrayList<>();
        data = new Data();
        if(!once) {
            fetchData();
            examplelist = findViewById(R.id.recycle);
            examplelist.setLayoutManager(new LinearLayoutManager(this));

        }
        once=true;
        /*System.out.println("----------------------------------------"+date.size());
        for(int i=0;i<date.size();i++) {
            System.out.println("----------------------------------------");
            System.out.println(date.get(i));
        }*/

    }



    private void fetchData() {


        ref = FirebaseDatabase.getInstance().getReference();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Data data = new Data();
                    data.setDate(ds.getValue(Data.class).getDate());
                    data.setLongitude(ds.getValue(Data.class).getLongitude());
                    data.setLatitude(ds.getValue(Data.class).getLatitude());
                    data.setStatus(ds.getValue(Data.class).getStatus());
                    System.out.println("----------------------------------------");
                    date.add(data.getDate());
                    longitude.add(data.getLongitude());
                    latitude.add(data.getLatitude());
                    status.add(data.getStatus());

                }
                /*Collections.reverse(date);
                Collections.reverse(longitude);
                Collections.reverse(latitude);*/
                madapter = new ExampleAdapter(date, longitude, latitude,status);
                examplelist.setAdapter(madapter);
                madapter.setOnNewsClickListener(new ExampleAdapter.OnNewsClickListener() {
                    @Override
                    public void onNewsClick(int i) {
                        String base_url = "https://www.google.com/maps/place/";
                        System.out.println("-----------------CLICK----------------------------");
                        Double lat = latitude.get(i);
                        Double lon = longitude.get(i);
                        String temp_date=date.get(i);
                        String temp_status=status.get(i);
                        String lon_lat_str = convert(lat, lon);
                        String url=base_url+lon_lat_str+"/@"+lat+","+lon+",17z";
                        Uri gmmIntentUri = Uri.parse(url);
                        Intent intent=new Intent(getApplicationContext(),Information.class);
                        /*Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);*/
                        intent.putExtra("latitude", lat.toString());
                        intent.putExtra("longitude", lon.toString());
                        intent.putExtra("date",temp_date);
                        intent.putExtra("status",temp_status);
                        intent.putExtra("url",url);
                        startActivity(intent);

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private String convert(Double lat, Double lon) {

        StringBuilder builder = new StringBuilder();


        String latitudeDegrees = Location.convert(Math.abs(lat), Location.FORMAT_SECONDS);
        String[] latitudeSplit = latitudeDegrees.split(":");
        builder.append(latitudeSplit[0]);
        builder.append("°");
        builder.append(latitudeSplit[1]);
        builder.append("'");
        builder.append(latitudeSplit[2]);
        builder.append("\"");

        if (lat < 0) {
            builder.append("S");
        } else {
            builder.append("N");
        }
        builder.append("+");


        String longitudeDegrees = Location.convert(Math.abs(lon), Location.FORMAT_SECONDS);
        String[] longitudeSplit = longitudeDegrees.split(":");
        builder.append(longitudeSplit[0]);
        builder.append("°");
        builder.append(longitudeSplit[1]);
        builder.append("'");
        builder.append(longitudeSplit[2]);
        builder.append("\"");
        if (lon < 0) {
            builder.append("W");
        } else {
            builder.append("E");
        }

        return builder.toString();

    }
}

package com.example.potholeadmin;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Information extends AppCompatActivity {
    String status;
    Intent intent;
    String latitude;
    String longitude;
    String date;
    String url;
    TextView longitude_data, latitud_data, date_data, status_data;
    DatabaseReference ref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.informaion);
        intent = getIntent();
        fetchIntent();
        setLayout();

    }

    private void setLayout() {
        longitude_data = findViewById(R.id.longitude_data);
        latitud_data = findViewById(R.id.latitude_data);
        status_data = findViewById(R.id.status_data);
        date_data = findViewById(R.id.date_data);

        longitude_data.setText(longitude);
        latitud_data.setText(latitude);
        date_data.setText(date);
        if (status.equals("NO")) {
            status_data.setTextColor(Color.RED);
        } else {
            status_data.setTextColor(Color.GREEN);
        }
        status_data.setText(status);


    }

    private void fetchIntent() {
        latitude = (intent.getStringExtra("latitude"));
        longitude = (intent.getStringExtra("longitude"));
        date = intent.getStringExtra("date");
        url = intent.getStringExtra("url");
        status = intent.getStringExtra("status");
    }

    public void navigation(View view) {
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public void isRepaired(View view) {
        if (status.equals("YES")) {
            Toast.makeText(this, "ALREADY REPAIRED", Toast.LENGTH_SHORT).show();
        } else {

            AlertDialog.Builder b = new AlertDialog.Builder(Information.this);
            b.setMessage("Are you sure Pothole is repaired?");
            b.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {
                    ref = FirebaseDatabase.getInstance().getReference();
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {


                                String lan = ds.getValue(Data.class).getLongitude().toString();
                                String lat = ds.getValue(Data.class).getLatitude().toString();

                                if (lan.equals(longitude) && lat.equals(latitude)) {
                                    status = "YES";
                                    setLayout();
                                    ref.child(ds.getKey()).child("status").setValue("YES");
                                }

                            }
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });
            b.setNegativeButton("CANCEL", null);
            b.show();


        }
    }

    public void delete(View view) {

        if (status.equals("NO")) {
            AlertDialog.Builder b = new AlertDialog.Builder(Information.this);
            b.setMessage("You can not delete record until potholes is repaired.");
            b.setPositiveButton("OK", null);
            b.show();

        } else {
            AlertDialog.Builder b = new AlertDialog.Builder(Information.this);
            b.setMessage("Are you sure want to delete record?");
            b.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {
                    ref = FirebaseDatabase.getInstance().getReference();
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {


                                String lan = ds.getValue(Data.class).getLongitude().toString();
                                String lat = ds.getValue(Data.class).getLatitude().toString();

                                if (lan.equals(longitude) && lat.equals(latitude)) {
                                    ref.child(ds.getKey()).removeValue();
                                }

                            }
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });
            b.setNegativeButton("CANCEL", null);
            b.show();

        }
    }
}

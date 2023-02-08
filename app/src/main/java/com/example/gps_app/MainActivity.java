package com.example.gps_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    TextView tv_lat, tv_lon, tv_altitude, tv_accuracy, tv_speed;
    EditText numbertex,editmsg;
    Button enviarbutton;


    LocationRequest locationRequest;
    FusedLocationProviderClient fusedLocationProviderClient;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Date currentime= Calendar.getInstance().getTime();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }
        numbertex = findViewById(R.id.Numero);
        enviarbutton = findViewById(R.id.send);
        tv_lat=findViewById(R.id.tv_lat);
        tv_lon=findViewById(R.id.tv_lon);
        tv_altitude=findViewById(R.id.tv_altitude);
        tv_accuracy=findViewById(R.id.tv_accuracy);
        tv_speed=findViewById(R.id.tv_speed);

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        tv_lat.setText(String.valueOf(location.getLatitude()));
                        tv_altitude.setText(String.valueOf(location.getAltitude()));
                        tv_lon.setText(String.valueOf(location.getLongitude()));
                        tv_accuracy.setText(String.valueOf(location.getAccuracy()));
                        tv_speed.setText(String.valueOf(currentime));
                    }
                });
        enviarbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(numbertex.getText().toString(),null,"Latitud: "+tv_lat.getText().toString()+" Longitud: "+tv_lon.getText().toString()+ "TimeStamp: "+tv_speed.getText().toString()+ "Altitud: "+tv_altitude.getText().toString(),null,null);
            }
        });
    }
    }

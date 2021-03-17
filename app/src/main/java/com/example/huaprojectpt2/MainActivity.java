package com.example.huaprojectpt2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.time.Instant;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    Intent serviceInt; //intent for service
    public DbHelper helper;
    TextView chargeState; //battery state
    ProgressBar progress; //progress bar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //register the broadcast receivers
        MainActivity.this.registerReceiver(myReceiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        //initialize db
        helper = new DbHelper(MainActivity.this);
        chargeState = findViewById(R.id.textView3);
        progress = findViewById(R.id.progressBar);


    }

    @Override //when being destroyed, the activity stops the service first (there are no other processes)
    protected void onDestroy() {
        super.onDestroy();
        if (serviceInt != null) { //if there is service, stop it
            stopService(serviceInt); Log.i("MainActivity","Service destroyed");
        }
        Log.i("MainActivity","Activity destroyed"); //Log message for debug
    }

    //Here we implemented Broadcast Receiver's onReceive to check phone's connectivity state
    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            if (plugged == BatteryManager.BATTERY_PLUGGED_AC || plugged == BatteryManager.BATTERY_PLUGGED_USB || plugged == BatteryManager.BATTERY_PLUGGED_WIRELESS){
                chargeState.setText("Connected");
                if (serviceInt != null) { //if there is service, stop it
                    stopService(serviceInt);
                    progress.setVisibility(View.INVISIBLE); //remove the progress bar
                }
            } else {
                chargeState.setText("Disconnected");
                checkLocationPermissions(); //request for permission if not already done
                Toast.makeText(MainActivity.this,"Getting service..", Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.VISIBLE); //make progress bar visible
                serviceInt = new Intent(getApplicationContext(),LocationService.class); //get intent for our service
                startService(serviceInt); //start service
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==7){
            for(int i=0; i< permissions.length; i++){
                if(grantResults[i] == PackageManager.PERMISSION_GRANTED ){
                    //do nothing
                    //app waits till phone's battery state changes
                }
            }
        }
    }

    public void checkLocationPermissions(){ //request permission if not any
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},7);
            Toast.makeText(MainActivity.this,"Can't get location, need permission", Toast.LENGTH_LONG).show();
        }
    }


}
package com.example.huaprojectpt2;

import android.Manifest;
import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.IBinder;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

public class LocationService extends Service {

    public LocationManager locationManager;
    public DbHelper helper;
    LocationListener listener;

    @Override
    public void onCreate() { //start service
        super.onCreate();
        //initializing location manager and database helper
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        helper = new DbHelper(getApplicationContext());
        Toast.makeText(getApplicationContext(),"Service created!",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() { //stop service
        super.onDestroy();
        if(listener!=null) {  //make sure the listener is not null
            locationManager.removeUpdates(listener); //stop receiving location updates when destroying the service
        }
        Toast.makeText(getApplicationContext(),"Service stopped!",Toast.LENGTH_LONG).show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(),"Service started!",Toast.LENGTH_LONG).show();
        //do the job
        showMyLocation();
        return Service.START_REDELIVER_INTENT;  //resurrect the service if it is killed with the same intent
    }

    public void showMyLocation() {
        Toast.makeText(getApplicationContext(),"doing work",Toast.LENGTH_LONG).show();
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //permission is checked in mainActivity
            // here we plainly check if the permissions are granted
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this.listener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {

                //Store our values
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                long timestamp = System.currentTimeMillis() / 1000L; //this stores the current timestamp in unix Epoch

                //Calling our content provider to do the job
                ContentResolver resolver = LocationService.this.getContentResolver();
                ContentValues values = new ContentValues();
                values.put("latitude", latitude);
                values.put("longitude", longitude);
                values.put("unix_timestamp", timestamp);
                //pass the values to our Content Provider
                Uri result = resolver.insert(Uri.parse(LocationContentProvider.CONTENT_URI + "/location"), values);

            }
        });
    }


}

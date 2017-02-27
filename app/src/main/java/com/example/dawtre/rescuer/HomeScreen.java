package com.example.dawtre.rescuer;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.Locale;

public class HomeScreen extends AppCompatActivity implements LocationListener {
    private DatabaseHelper db;
    private LocationManager locationManager;
    private Location _location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        db = new DatabaseHelper(this);

        try {
            db.createDatabase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        _location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    public void buttonMorseClicked(View v)
    {
        Intent mor = new Intent(this, MorseActivity.class);
        startActivity(mor);
    }

    public void buttonGPSClicked(View v)
    {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        { return; }

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", location.getLatitude(), location.getLongitude());
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

    public void buttonSendSMSClicked(View v)
    {
        Cursor res = db.getSMSData();

        String message = null;
        String phoneNumber = null;

        while (res.moveToNext())
        {
            message = res.getString(1);
            phoneNumber = res.getString(2);
        }

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        { return; }

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        message += " Latitude: " + location.getLatitude() + " Longitude: " + location.getLongitude();

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);

        Toast.makeText(getBaseContext(), "Message sended!", Toast.LENGTH_LONG).show();
    }

    public void buttonPersonalDataClicked(View v)
    {
        Intent dan = new Intent( this, PersonalDataActivity.class);
        startActivity(dan);
    }

    public void buttonSettingsClicked(View v)
    {
        Intent ust = new Intent( this, SettingsActivity.class);
        startActivity(ust);
    }

    @Override
    public void onProviderDisabled(String provider)
    {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
        Toast.makeText(getBaseContext(), "GPS is turned off",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String provider)
    {
        Toast.makeText(getBaseContext(), "GPS is turned on",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void onLocationChanged(Location location)
    {
        _location = location;
        // TODO Auto-generated method stub
    }
}
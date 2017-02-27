package com.example.dawtre.rescuer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void buttonPersonalDataSettingsClicked (View v)
    {
        Intent per = new Intent(this, PersonalDataSettingsActivity.class);
        startActivity(per);
    }

    public void buttonSMSSettingsClicked(View v)
    {
        Intent sms = new Intent( this, SMSActivity.class);
        startActivity( sms );
    }
}
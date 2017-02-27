package com.example.dawtre.rescuer;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

public class SMSActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private EditText editTextMessage, editTextPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextMessage = (EditText) findViewById(R.id.editTextMessage);
        editTextPhoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);

        db = new DatabaseHelper(this);

        try
        {
            db.openDatabase();
        }
        catch(SQLException sqle)
        { }

        SetEditTexts();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void buttonSaveSMSDataClicked( View v)
    {
        boolean isInserted = db.insertSMSData(editTextMessage.getText().toString(),
                editTextPhoneNumber.getText().toString());

        if(isInserted)
        {
            Toast.makeText(SMSActivity.this, "Data saved!", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(SMSActivity.this, "Data not saved!", Toast.LENGTH_LONG).show();
        }
    }

    private void SetEditTexts()
    {
        Cursor res = db.getSMSData();

        while(res.moveToNext()) {
            editTextMessage.setText(res.getString(1));
            editTextPhoneNumber.setText(res.getString(2));
        }
    }
}
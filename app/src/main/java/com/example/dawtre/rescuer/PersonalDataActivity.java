package com.example.dawtre.rescuer;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.sql.SQLException;

public class PersonalDataActivity extends AppCompatActivity {

    private TextView textViewName, textViewSurname, textViewPESEL, textViewBirthday, textViewBloodType, textViewAddress, textViewPhone;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new DatabaseHelper(this);

        try
        {
            db.openDatabase();
        }
        catch(SQLException sqle)
        { }

        SetTextViews();
    }

    private void SetTextViews()
    {
        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewSurname = (TextView) findViewById(R.id.textViewSurname);
        textViewPESEL = (TextView) findViewById(R.id.textViewPESEL);
        textViewBirthday = (TextView) findViewById(R.id.textViewBirthday);
        textViewBloodType = (TextView) findViewById(R.id.textViewBloodType);
        textViewAddress = (TextView) findViewById(R.id.textViewAddress);
        textViewPhone = (TextView) findViewById(R.id.textViewPhone);

        Cursor res = db.getPersonalData();

        while(res.moveToNext()) {
            textViewName.setText(res.getString(1));
            textViewSurname.setText(res.getString(2));
            textViewPESEL.setText(res.getString(3));
            textViewBirthday.setText(res.getString(4));
            textViewBloodType.setText(res.getString(5));
            textViewAddress.setText(res.getString(6));
            textViewPhone.setText(res.getString(7));
        }
    }
}

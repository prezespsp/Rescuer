package com.example.dawtre.rescuer;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

public class PersonalDataSettingsActivity extends AppCompatActivity {

    private EditText editTextName, editTextSurname, editTextPESEL,editTextBirthday, editTextBloodType, editTextAddress, editTextPhoneToRelative;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        GetControls();
        db = new DatabaseHelper(this);

        try
        {
            db.openDatabase();
        }
        catch(SQLException sqle)
        { }

        SetEditTexts();
    }

    private void GetControls()
    {
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextSurname = (EditText) findViewById(R.id.editTextSurname);
        editTextPESEL = (EditText) findViewById(R.id.editTextPESEL);
        editTextBirthday = (EditText) findViewById(R.id.editTextBirthday);
        editTextBloodType = (EditText) findViewById(R.id.editTextBloodType);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        editTextPhoneToRelative = (EditText) findViewById(R.id.editTextPhoneToRelative);
    }

    public void buttonSaveClicked(View v)
    {
        boolean isInserted = db.insertPersonalData(editTextName.getText().toString(),
                                                    editTextSurname.getText().toString(),
                                                    editTextPESEL.getText().toString(),
                                                    editTextBirthday.getText().toString(),
                                                    editTextBloodType.getText().toString(),
                                                    editTextAddress.getText().toString(),
                                                    editTextPhoneToRelative.getText().toString());

        if(isInserted)
        {
            Toast.makeText(PersonalDataSettingsActivity.this, "Data saved!", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(PersonalDataSettingsActivity.this, "Data not saved!", Toast.LENGTH_LONG).show();
        }
    }

    private void SetEditTexts()
    {
        Cursor res = db.getPersonalData();

        while(res.moveToNext()) {
            editTextName.setText(res.getString(1));
            editTextSurname.setText(res.getString(2));
            editTextPESEL.setText(res.getString(3));
            editTextBirthday.setText(res.getString(4));
            editTextBloodType.setText(res.getString(5));
            editTextAddress.setText(res.getString(6));
            editTextPhoneToRelative.setText(res.getString(7));
        }
    }
}
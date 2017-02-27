package com.example.dawtre.rescuer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.io.IOException;
import java.sql.SQLException;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String DB_PATH = "/data/data/com.example.dawtre.rescuer/databases/";
    private static final String DATABASE_NAME = "rescuer.db";
    private SQLiteDatabase db;
    //private final Context myContext;

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
        //this.myContext = context;
    }

    public void createDatabase() throws IOException
    {
        boolean dbExist = checkDatabase();

        if(dbExist)
        {
            // do nothing - database already exists
        }
        else
        {
            db = this.getWritableDatabase();

            db.execSQL("CREATE TABLE personal_data (ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "NAME TEXT NOT NULL,"
                    + "SURNAME TEXT NOT NULL,"
                    + "PESEL TEXT NOT NULL,"
                    + "BIRTHDAY TEXT NOT NULL,"
                    + "BLOOD_TYPE TEXT NOT NULL,"
                    + "ADDRESS TEXT NOT NULL,"
                    + "PHONE_TO_RELATIVE TEXT NOT NULL)");

            db.execSQL("CREATE TABLE sms_data (ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "MESSAGE TEXT NOT NULL,"
                    + "PHONE_NUMBER TEXT NOT NULL)");
        }
    }

    private boolean checkDatabase()
    {
        SQLiteDatabase checkDB = null;

        try
        {
            String myPath = DB_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }
        catch(SQLiteException e)
        {
            // database doesn't exist yet
        }

        if (checkDB != null)
        {
            checkDB.close();
        }

        return checkDB != null ? true : false;
    }

    public void openDatabase() throws SQLException
    {
        String myPath = DB_PATH + DATABASE_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, 0);
    }

    public synchronized void close()
    {
        if(db != null)
            db.close();

        super.close();
    }

    public boolean insertPersonalData(String name, String surname, String pesel, String birthday, String bloodType, String address, String phoneToRelative)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("NAME", name);
        contentValues.put("SURNAME", surname);
        contentValues.put("PESEL", pesel);
        contentValues.put("BIRTHDAY", birthday);
        contentValues.put("BLOOD_TYPE", bloodType);
        contentValues.put("ADDRESS", address);
        contentValues.put("PHONE_TO_RELATIVE", phoneToRelative);

        if(ifPersonalDataExists())
        {
            db.update("personal_data", contentValues, "ID = ?", new String[] { "1" });
            return true;
        }
        else
        {
            long result = db.insert("personal_data", null, contentValues);

            if (result != -1)        // if error db.insert returns -1
            { return true; }
            else
            { return false; }
        }
    }

    public Cursor getPersonalData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM personal_data LIMIT 1", null);
        return res;
    }

    private boolean ifPersonalDataExists()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM personal_data LIMIT 1", null);
        return res.getCount() != 0 ? true : false;
    }

    public boolean insertSMSData(String message, String phoneNumber)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("MESSAGE", message);
        contentValues.put("PHONE_NUMBER", phoneNumber);

        if(ifSMSDataExists())
        {
            db.update("sms_data", contentValues, "ID = ?", new String[] { "1" });
            return true;
        }
        else
        {
            long result = db.insert("sms_data", null, contentValues);

            if (result != -1)        // if error db.insert returns -1
            { return true; }
            else
            { return false; }
        }
    }

    private boolean ifSMSDataExists()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM sms_data LIMIT 1", null);
        return res.getCount() != 0 ? true : false;
    }

    public Cursor getSMSData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM sms_data LIMIT 1", null);
        return res;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {}

}
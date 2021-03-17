package com.example.huaprojectpt2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    /*
    This class is responsible for the database connection and retrieving info from our database
     */

    public static String DB_NAME = "LocationsDB";
    public static String TABLE_NAME = "Locations";
    public static String FIELD_1 = "id";
    public static String FIELD_2 = "longitude";
    public static String FIELD_3 = "latitude";
    public static String FIELD_4 = "unix_timestamp";
    private String SQL_QUERY = "CREATE TABLE "+TABLE_NAME+" ("+FIELD_1+" INTEGER PRIMARY KEY AUTOINCREMENT,"+FIELD_2+" DOUBLE, "+FIELD_3+" DOUBLE, "+FIELD_4+" LONG)";

    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insertInfo(LocationDAO location){
        ContentValues values = new ContentValues();
        values.put(FIELD_2,location.getLatitude());
        values.put(FIELD_3,location.getLongitude());
        values.put(FIELD_4,location.getUnix_timestamp());
        long id = this.getWritableDatabase().insert(TABLE_NAME,null,values);
        return id;
    }


}
package com.example.huaprojectpt2;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LocationContentProvider extends ContentProvider {

    /*
    This is the Content Provider class for storing data in database from our MainActivity.
     */

    private static UriMatcher uriMatcher;
    private DbHelper dbHelper;
    private static final String AUTHORITY = "com.example.huaprojectpt2";
    public static final String CONTENT_URI = "content://"+AUTHORITY;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"location",1);
    }
    @Override
    public boolean onCreate() {
        dbHelper = new DbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Uri result = null;
        switch(uriMatcher.match(uri)){
            case 1:
                //create a location object
                LocationDAO location = new LocationDAO(values.getAsDouble("longitude"),values.getAsDouble("latitude"),values.getAsLong("unix_timestamp"));
                long id = dbHelper.insertInfo(location);
                Toast.makeText(getContext(), "in insert", Toast.LENGTH_LONG).show();
                result= Uri.parse(AUTHORITY+"/location"+id);
                break;

        }
        return result;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}

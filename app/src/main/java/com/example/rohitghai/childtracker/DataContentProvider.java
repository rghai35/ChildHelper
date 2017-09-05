package com.example.rohitghai.childtracker;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class DataContentProvider extends ContentProvider
{
    DBHelper helper;

    SQLiteDatabase sqLiteDatabase;

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        String tableName = uri.getLastPathSegment();

        long id = sqLiteDatabase.insert(tableName,null,values);

        Uri tempUri = Uri.parse("dummyuri/" + id);

        return tempUri;
    }

    @Override
    public boolean onCreate()
    {
        helper = new DBHelper(getContext(),Util.DB_NAME,null,Util.DB_VERSION);

        sqLiteDatabase = helper.getWritableDatabase();

        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder)
    {
        String tabName = uri.getLastPathSegment();

        Cursor cursor = sqLiteDatabase.query(tabName,projection,null,null,null,null,null);

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    class DBHelper extends SQLiteOpenHelper
    {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
        {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase)
        {
            sqLiteDatabase.execSQL(Util.CREATE_TAB_QUERY);  //Create The Table
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}

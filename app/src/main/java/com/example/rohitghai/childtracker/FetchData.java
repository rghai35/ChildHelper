package com.example.rohitghai.childtracker;

import android.content.ContentResolver;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class FetchData extends AppCompatActivity
{

    ListView listView;
    ContentResolver resolver;

    ArrayList<Data> dataList;
    DataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_data);

        this.setTitle("Database");

        listView = (ListView)findViewById(R.id.listview);

        resolver = getContentResolver();

        retrieveData();
    }

    void retrieveData()
    {
        dataList = new ArrayList<Data>();

        String[] projection = {Util.COL_ID,Util.COL_DATE,Util.COL_TIME,Util.COL_LATITUDE,Util.COL_LONGITUDE,Util.COL_LOCATION,Util.COL_SPEED};

        Cursor cursor = resolver.query(Util.USER_URI,projection,null,null,null);

        if(cursor!=null)
        {
            int i = 0;
            String d = "";
            String t = "";
            Double lng = 0.0;
            Double ltd = 0.0;
            String l = "";
            Float s = 0.0f;


            try
            {
                while (cursor.moveToNext())
                {
                i = cursor.getInt(cursor.getColumnIndex(Util.COL_ID));
                d = cursor.getString(cursor.getColumnIndex(Util.COL_DATE));
                t = cursor.getString(cursor.getColumnIndex(Util.COL_TIME));
                lng = cursor.getDouble(cursor.getColumnIndex(Util.COL_LONGITUDE));
                ltd = cursor.getDouble(cursor.getColumnIndex(Util.COL_LATITUDE));
                l = cursor.getString(cursor.getColumnIndex(Util.COL_LOCATION));
                s = cursor.getFloat(cursor.getColumnIndex(Util.COL_SPEED));

                Data data = new Data(i, d, t, lng, ltd, l, s);
                dataList.add(data);
                }
            }
            catch (Exception e)
            {
                System.out.println("hello " + e);
                Toast.makeText(this, " " + e, Toast.LENGTH_LONG).show();
            }


            adapter = new DataAdapter(this,R.layout.data_item,dataList);
            listView.setAdapter(adapter);
        }
    }
}

package com.example.rohitghai.childtracker;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class SpeedService extends Service implements LocationListener
{
    LocationManager lm;
    float speed;

    SharedPreferences preferences;
    SharedPreferences preferencesRegistration;
    //SharedPreferences.Editor editor;
    //SharedPreferences demo;

    //SharedPreferences demoBefore;
    //SharedPreferences demoAfter;

    //SharedPreferences.Editor editorB;
    //SharedPreferences.Editor editorA;

    float speed1;
    float speed2;

    float speedkmph;

    String mobile;
    String message;
    String childName;

    SmsManager sm;

    double latitude;
    double longitude;

    int counter = 0;

    int sendSmsOnce = 0;

    Data data;

    Geocoder gcd;

    StringBuffer buffer;

    ContentResolver resolver;

    @Override
    public IBinder onBind(Intent intent)
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {

        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        sm = SmsManager.getDefault();

        data = new Data();

        gcd = new Geocoder(this);

        resolver = getContentResolver();

        preferences = getSharedPreferences("Speed",MODE_PRIVATE);
        preferencesRegistration = getSharedPreferences("RegistrationDetails",MODE_PRIVATE);

        //demo = getSharedPreferences("demo",MODE_PRIVATE);
        //editor = demo.edit();

        //demoBefore = getSharedPreferences("demoBefore",MODE_PRIVATE);
        //editorB = demoBefore.edit();

        //demoAfter = getSharedPreferences("demoAfter",MODE_PRIVATE);
        //editorA = demoAfter.edit();

        speed1 = (float)preferences.getInt("keySpeed1",0);
        speed2 = (float)preferences.getInt("keySpeed2",0);

        Toast.makeText(this, "Speed 2: " + speed2, Toast.LENGTH_SHORT).show();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this, "Please Grant Permissions In Settings", Toast.LENGTH_SHORT).show();
        }

        else
        {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);
        }


        //Toast.makeText(this, "Location Service Created", Toast.LENGTH_SHORT).show();

        return super.onStartCommand(intent, flags, startId);
        //return Service.START_STICKY;
    }

    @Override
    public void onLocationChanged(Location location)
    {
        speed = location.getSpeed();

        speedkmph = (speed * 3600)/1000;

        //Toast.makeText(this, "Latitude: " + latitude + " ,Speed: " + speedkmph, Toast.LENGTH_SHORT).show();


        //editor.putString("keySpeed"+counter, " "+speedkmph + " ," + latitude);
        //editor.commit();

        counter++;

        //editorA.putString("key"+counter, " " + speedkmph + " " + speed2 + " " + new Date());
        //editorA.commit();

        if(speedkmph >= speed1)
        {
            int dt = new Date().getDate();
            int month = new Date().getMonth() + 1;
            //String monthName=(String)android.text.format.DateFormat.format("MMMM", new Date());
            int year = new Date().getYear() + 1900;

            String date = dt + "-" + month + "-" + year;

            data.setDate(date);

            /*int hour = new Date().getHours();
            int minute = new Date().getMinutes();
            int second = new Date().getSeconds();
            String time = hour + ":" + minute + ":" + second;*/

            Date dateTime = new Date();

            String TimeFormat = "hh:mm a";
            SimpleDateFormat formatter = new SimpleDateFormat(TimeFormat);
            String time = formatter.format(dateTime);

            data.setTime(time);

            latitude = location.getLatitude();
            longitude = location.getLongitude();

            data.setLatitude(latitude);
            data.setLongitude(longitude);

            try
            {
                List<Address> adrsList = gcd.getFromLocation(latitude,longitude,1);
                Address address;

                if(adrsList != null && adrsList.size()>0)
                {
                    address = adrsList.get(0);

                    buffer = new StringBuffer();

                    for(int i=0;i<address.getMaxAddressLineIndex();i++)
                    {
                        buffer.append(address.getAddressLine(i) + "\n");
                    }

                    data.setLocation(buffer.toString());
                }

                else
                {
                    data.setLocation("No Nearby Location Found");
                }

            }

            catch (IOException e)
            {
                e.printStackTrace();
                Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
            }

            data.setSpeed(speedkmph);

            insertData();

            if(speedkmph >= speed2)
            {
                if(sendSmsOnce == 0)
                {
                    mobile = preferencesRegistration.getString("keyMobile", "9417538648");
                    childName = preferencesRegistration.getString("keyChildName", "Def");

                    //editorB.putString("key"+counter, " " + speedkmph + " " + speed2 + " " + new Date());
                    //editorB.commit();

                    message = "Your Child, " + childName + " Is Travellng At The Speed of " + speedkmph + " km/hr. He Is Currently At " + buffer.toString();
                    sm.sendTextMessage(mobile, null, message, null, null);

                    sendSmsOnce++;
                }

            /*else
            {
                Toast.makeText(this, "Message Already Send", Toast.LENGTH_SHORT).show();
            }*/
            }

        }

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle)
    {

    }

    @Override
    public void onProviderEnabled(String s)
    {

    }

    @Override
    public void onProviderDisabled(String s)
    {

    }

    void insertData()
    {
        ContentValues values = new ContentValues();

        values.put(Util.COL_DATE,data.getDate());
        values.put(Util.COL_TIME,data.getTime());
        values.put(Util.COL_LATITUDE,data.getLatitude());
        values.put(Util.COL_LONGITUDE,data.getLongitude());
        values.put(Util.COL_LOCATION,data.getLocation());
        values.put(Util.COL_SPEED,data.getSpeed());

        Uri uri = resolver.insert(Util.USER_URI,values);

        Toast.makeText(this, " Data Inserted:" + uri.getLastPathSegment(), Toast.LENGTH_SHORT).show();
    }
}



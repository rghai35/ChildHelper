package com.example.rohitghai.childtracker;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class HelpActivity extends AppCompatActivity implements View.OnClickListener, LocationListener
{
    TextView tvChildhelper;
    TextView tvChildLocation;
    EditText etMessage;
    CheckBox cbParents;
    Button btnHelpMe;

    Typeface tf1;

    LocationManager lm;
    ProgressDialog pd;

    double childLatitude;
    double childLongitude;

    Geocoder gcd;

    StringBuffer buffer;

    SharedPreferences preferencesRegistration;
    SharedPreferences preferencesAddress1;
    SharedPreferences preferencesAddress2;
    SharedPreferences preferencesAddress3;

    String EditTextMessage;
    String messageSend;
    String childName;

    SmsManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        tvChildhelper = (TextView)findViewById(R.id.childhelper);
        tvChildLocation = (TextView)findViewById(R.id.childlocation);
        etMessage = (EditText)findViewById(R.id.message);
        cbParents = (CheckBox)findViewById(R.id.parents);
        btnHelpMe = (Button)findViewById(R.id.helpme);

        tf1 = Typeface.createFromAsset(getAssets(), "LobsterTwo-BoldItalic.otf");
        tvChildhelper.setTypeface(tf1);

        pd = new ProgressDialog(this);
        pd.setMessage("Fetching Address....");

        gcd = new Geocoder(this);

        preferencesRegistration = this.getSharedPreferences("RegistrationDetails",this.MODE_PRIVATE);
        preferencesAddress1 = this.getSharedPreferences("Address1",this.MODE_PRIVATE);
        preferencesAddress2 = this.getSharedPreferences("Address2", this.MODE_PRIVATE);
        preferencesAddress3 = this.getSharedPreferences("Address3",this.MODE_PRIVATE);

        childName = preferencesRegistration.getString("keyChildName","");

        sm = SmsManager.getDefault();


        lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this, "Please Grant Permissions In Settings", Toast.LENGTH_SHORT).show();
        }

        else
        {
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 1, this);
            pd.show();
        }

        btnHelpMe.setOnClickListener(this);
    }


    @Override
    public void onLocationChanged(Location location)
    {
        childLatitude = location.getLatitude();
        childLongitude = location.getLongitude();

        try
        {
            List<Address> adrsList = gcd.getFromLocation(childLatitude,childLongitude,1);
            Address address;

            if(adrsList != null && adrsList.size()>0)
            {
                address = adrsList.get(0);

                buffer = new StringBuffer();

                for(int i=0;i<address.getMaxAddressLineIndex();i++)
                {
                    buffer.append(address.getAddressLine(i) + "\n");
                }

                tvChildLocation.setText(tvChildLocation.getText() + " " + buffer.toString());

            }

            else
            {
                Toast.makeText(this, "No Nearby Location Found", Toast.LENGTH_SHORT).show();
            }

        }

        catch (IOException e)
        {
            e.printStackTrace();
            Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
        }

        pd.dismiss();
        lm.removeUpdates(this);

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    @Override
    public void onClick(View view)
    {
        EditTextMessage = etMessage.getText().toString().trim();

        double parentLatd = Double.valueOf(preferencesAddress1.getString("Latitude1",""));
        double parentLong = Double.valueOf(preferencesAddress1.getString("Longitude1",""));
        double relative1Latd = Double.valueOf(preferencesAddress2.getString("Latitude2",""));
        double relative1Long = Double.valueOf(preferencesAddress2.getString("Longitude2",""));
        double relative2Latd = Double.valueOf(preferencesAddress3.getString("Latitude3",""));
        double relative2Long = Double.valueOf(preferencesAddress3.getString("Longitude3",""));

        String parentMobile = preferencesAddress1.getString("keyMobile1","");
        String relative1Mobile = preferencesAddress2.getString("keyMobile2","");
        String relative2Mobile = preferencesAddress3.getString("keyMobile3","");

        Toast.makeText(this, parentLatd + " " + parentLong + " " + relative1Latd + " " + relative1Long + " " + relative2Latd + " " + relative2Long + " " + parentMobile + " " + relative1Mobile + " " + relative2Mobile, Toast.LENGTH_LONG).show();

        //FETCH LONGITUDE, LATITUDE, MOBILE FROM ADDRESS1,2,3 ....

        String isMessage;

        if(EditTextMessage.isEmpty())
        {
            isMessage = "Message: No Message For You";
        }

        else
        {
            isMessage = "Message: " + EditTextMessage;
        }


        if(cbParents.isChecked())
        {
            messageSend = "Your Child, " + childName + " Is In Trouble. He Is Currently At Location - " + buffer.toString() + isMessage;

            Toast.makeText(this, messageSend, Toast.LENGTH_LONG).show();
            //sm.sendTextMessage(mobile, null, isMessage, null, null);
            //Toast.makeText(HelpActivity.this, "Your Details Has Been Sent To Your Parents. You Will Be Helped Soon", Toast.LENGTH_SHORT).show();
        }

        else
        {
            messageSend = childName + " Is In Trouble. He Is Currently At Location - " + buffer.toString() + isMessage;

            Toast.makeText(this, messageSend, Toast.LENGTH_LONG).show();

            //sm.sendTextMessage(mobile, null, isMessage, null, null);
            //Toast.makeText(HelpActivity.this, "Your Details Has Been Sent To Your Nearby Relatives. You Will Be Helped Soon", Toast.LENGTH_SHORT).show();

        }
    }
}

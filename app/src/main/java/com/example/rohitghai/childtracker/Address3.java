package com.example.rohitghai.childtracker;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;


public class Address3 extends Fragment implements View.OnClickListener, LocationListener
{
    View view;
    Context context;

    EditText etAddress3;
    EditText etCity3;
    EditText etState3;
    EditText etMobile3;
    Button btnFetch3;
    Button btnSubmit3;
    Button btnEdit3;

    LocationManager lm3;
    double latitude3;
    double longitude3;

    ProgressDialog pd3;

    Geocoder gcd;

    StringBuffer buffer;

    String postalCode;

    String Address3;
    String City3;
    String State3;
    String PostalCode3;
    String Mobile3;

    SharedPreferences preferencesAddress3;
    SharedPreferences.Editor editor;

    public Address3()
    {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        etAddress3 = (EditText) view.findViewById(R.id.address3);
        etCity3 = (EditText) view.findViewById(R.id.city3);
        etState3 = (EditText) view.findViewById(R.id.state3);
        etMobile3 = (EditText) view.findViewById(R.id.mobile3);
        btnFetch3 = (Button) view.findViewById(R.id.fetch3);
        btnSubmit3 = (Button) view.findViewById(R.id.submit3);
        btnEdit3 = (Button) view.findViewById(R.id.edit3);

        pd3 = new ProgressDialog(getContext());
        pd3.setMessage("Fetching Address....");

        gcd = new Geocoder(getContext());

        preferencesAddress3 = getContext().getSharedPreferences("Address3",getContext().MODE_PRIVATE);
        editor = preferencesAddress3.edit();

        if(preferencesAddress3.contains("keyAddress3") && preferencesAddress3.contains("keyCity3") && preferencesAddress3.contains("keyState3"))
        {
            btnFetch3.setEnabled(false);
            btnSubmit3.setEnabled(false);
            btnEdit3.setVisibility(View.VISIBLE);

            etAddress3.setText(preferencesAddress3.getString("keyAddress3",""));
            etCity3.setText(preferencesAddress3.getString("keyCity3",""));
            etState3.setText(preferencesAddress3.getString("keyState3",""));
            etMobile3.setText(preferencesAddress3.getString("keyMobile3",""));

            etAddress3.setFocusable(false);
            etAddress3.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
            etAddress3.setClickable(false); // user navigates with wheel and selects widget

            etCity3.setFocusable(false);
            etCity3.setFocusableInTouchMode(false);
            etCity3.setClickable(false);

            etState3.setFocusable(false);
            etState3.setFocusableInTouchMode(false);
            etState3.setClickable(false);

            etMobile3.setFocusable(false);
            etMobile3.setFocusableInTouchMode(false);
            etMobile3.setClickable(false);

        }

        else
        {
            btnEdit3.setVisibility(View.GONE);
        }

        btnEdit3.setOnClickListener(this);
        btnSubmit3.setOnClickListener(this);
        btnFetch3.setOnClickListener(this);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_address3, container, false);

        return view;
    }

    @Override
    public void onClick(View view)
    {
        int id = view.getId();

        if(id == R.id.fetch3)
        {
            FetchAddress3();
        }

        if(id == R.id.submit3)
        {

            if(etAddress3.getText().toString().equals(""))
            {
                etAddress3.setError("Please Enter Address");
            }

            else if(etCity3.getText().toString().equals(""))
            {
                etCity3.setError("Please Enter City");
            }

            else if(etState3.getText().toString().equals(""))
            {
                etState3.setError("Please Enter State");
            }

            else if(etMobile3.getText().toString().equals(""))
            {
                etMobile3.setError("Please Enter Mobile Number");
            }

            else if(etMobile3.getText().toString().trim().length()<6 || etMobile3.getText().toString().trim().length()>13)
            {
                etMobile3.setError("Please Enter Valid Mobile Number");
            }

            else
            {
                Address3 = etAddress3.getText().toString().trim();
                City3 = etCity3.getText().toString().trim();
                State3 = etState3.getText().toString().trim();
                PostalCode3 = postalCode;
                Mobile3 = etMobile3.getText().toString().trim();


                editor.putString("Latitude3", String.valueOf(latitude3));  //To retrieve Use Double.valueOf("Latitude3");
                editor.putString("Longitude3", String.valueOf(longitude3));
                editor.putString("keyAddress3", Address3);
                editor.putString("keyCity3", City3);
                editor.putString("keyState3", State3);
                editor.putString("keyPostalCode3", PostalCode3);
                editor.putString("keyMobile3", Mobile3);

                editor.commit();

                Toast.makeText(getContext(), "Data Successfully Saved", Toast.LENGTH_SHORT).show();

                btnFetch3.setEnabled(false);
                btnSubmit3.setEnabled(false);

                etAddress3.setFocusable(false);
                etAddress3.setFocusableInTouchMode(false);
                etAddress3.setClickable(false);

                etCity3.setFocusable(false);
                etCity3.setFocusableInTouchMode(false);
                etCity3.setClickable(false);

                etState3.setFocusable(false);
                etState3.setFocusableInTouchMode(false);
                etState3.setClickable(false);

                etMobile3.setFocusable(false);
                etMobile3.setFocusableInTouchMode(false);
                etMobile3.setClickable(false);

                btnEdit3.setVisibility(view.VISIBLE);
            }
        }

        if(id == R.id.edit3)
        {
            etAddress3.setFocusable(true);
            etAddress3.setFocusableInTouchMode(true);
            etAddress3.setClickable(true);

            etCity3.setFocusable(true);
            etCity3.setFocusableInTouchMode(true);
            etCity3.setClickable(true);

            etState3.setFocusable(true);
            etState3.setFocusableInTouchMode(true);
            etState3.setClickable(true);


            etMobile3.setFocusable(true);
            etMobile3.setFocusableInTouchMode(true);
            etMobile3.setClickable(true);

            btnFetch3.setEnabled(true);
            btnSubmit3.setEnabled(true);

            btnEdit3.setVisibility(view.GONE);
        }
    }

    public void FetchAddress3()
    {
        context = getContext();
        lm3 = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(getContext(), "Please Grant Permissions In Settings", Toast.LENGTH_SHORT).show();
        }

        else
        {
            lm3.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5, 5, Address3.this);
            pd3.show();
        }
    }

    @Override
    public void onLocationChanged(Location location)
    {
        latitude3 = location.getLatitude();
        longitude3 = location.getLongitude();

        String city = "";
        String state = "";

        try
        {
            List<Address> adrsList = gcd.getFromLocation(latitude3,longitude3,1);
            Address address;

            if(adrsList != null && adrsList.size()>0)
            {
                address = adrsList.get(0);

                city = adrsList.get(0).getLocality();
                state = adrsList.get(0).getAdminArea();
                postalCode = adrsList.get(0).getPostalCode();

                buffer = new StringBuffer();

                for(int i=0;i<address.getMaxAddressLineIndex();i++)
                {
                    buffer.append(address.getAddressLine(i) + "\n");
                }

                int start = buffer.toString().trim().indexOf(city);
                int end = buffer.toString().trim().indexOf(state) + state.length();

                buffer.delete(start,end);
                etAddress3.setText(buffer.toString());

            }

            else
            {
                Toast.makeText(context, "No Nearby Location Found", Toast.LENGTH_SHORT).show();
            }

        }

        catch (IOException e)
        {
            e.printStackTrace();
            Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
        }

        etCity3.setText(city);
        etState3.setText(state);

        pd3.dismiss();
        lm3.removeUpdates(this);
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
}

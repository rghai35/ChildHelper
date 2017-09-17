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


public class Address2 extends Fragment implements View.OnClickListener, LocationListener
{
    View view;
    Context context;

    EditText etAddress2;
    EditText etCity2;
    EditText etState2;
    EditText etMobile2;
    Button btnFetch2;
    Button btnSubmit2;
    Button btnEdit2;

    LocationManager lm2;
    double latitude2;
    double longitude2;

    ProgressDialog pd2;

    Geocoder gcd;

    StringBuffer buffer;

    String postalCode;

    String Address2;
    String City2;
    String State2;
    String PostalCode2;
    String Mobile2;

    SharedPreferences preferencesAddress2;
    SharedPreferences.Editor editor;

    public Address2()
    {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        etAddress2 = (EditText) view.findViewById(R.id.address2);
        etCity2 = (EditText) view.findViewById(R.id.city2);
        etState2 = (EditText) view.findViewById(R.id.state2);
        etMobile2 = (EditText) view.findViewById(R.id.mobile2);
        btnFetch2 = (Button) view.findViewById(R.id.fetch2);
        btnSubmit2 = (Button) view.findViewById(R.id.submit2);
        btnEdit2 = (Button) view.findViewById(R.id.edit2);

        pd2 = new ProgressDialog(getContext());
        pd2.setMessage("Fetching Address....");

        gcd = new Geocoder(getContext());

        preferencesAddress2 = getContext().getSharedPreferences("Address2",getContext().MODE_PRIVATE);
        editor = preferencesAddress2.edit();

        if(preferencesAddress2.contains("keyAddress2") && preferencesAddress2.contains("keyCity2") && preferencesAddress2.contains("keyState2"))
        {
            btnFetch2.setEnabled(false);
            btnSubmit2.setEnabled(false);
            btnEdit2.setVisibility(View.VISIBLE);

            etAddress2.setText(preferencesAddress2.getString("keyAddress2",""));
            etCity2.setText(preferencesAddress2.getString("keyCity2",""));
            etState2.setText(preferencesAddress2.getString("keyState2",""));
            etMobile2.setText(preferencesAddress2.getString("keyMobile2",""));

            etAddress2.setFocusable(false);
            etAddress2.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
            etAddress2.setClickable(false); // user navigates with wheel and selects widget

            etCity2.setFocusable(false);
            etCity2.setFocusableInTouchMode(false);
            etCity2.setClickable(false);

            etState2.setFocusable(false);
            etState2.setFocusableInTouchMode(false);
            etState2.setClickable(false);

            etMobile2.setFocusable(false);
            etMobile2.setFocusableInTouchMode(false);
            etMobile2.setClickable(false);

        }

        else
        {
            btnEdit2.setVisibility(View.GONE);
        }


        btnEdit2.setOnClickListener(this);
        btnSubmit2.setOnClickListener(this);
        btnFetch2.setOnClickListener(this);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_address2, container, false);

        return view;
    }

    @Override
    public void onClick(View view)
    {
        int id = view.getId();

        if(id == R.id.fetch2)
        {
            FetchAddress2();
        }

        if(id == R.id.submit2)
        {

            if(etAddress2.getText().toString().equals(""))
            {
                etAddress2.setError("Please Enter Address");
            }

            else if(etCity2.getText().toString().equals(""))
            {
                etCity2.setError("Please Enter City");
            }

            else if(etState2.getText().toString().equals(""))
            {
                etState2.setError("Please Enter State");
            }

            else if(etMobile2.getText().toString().equals(""))
            {
                etMobile2.setError("Please Enter Mobile Number");
            }

            else if(etMobile2.getText().toString().trim().length()<6 || etMobile2.getText().toString().trim().length()>13)
            {
                etMobile2.setError("Please Enter Valid Mobile Number");
            }

            else
            {
                Address2 = etAddress2.getText().toString().trim();
                City2 = etCity2.getText().toString().trim();
                State2 = etState2.getText().toString().trim();
                PostalCode2 = postalCode;
                Mobile2 = etMobile2.getText().toString().trim();


                editor.putString("Latitude2", String.valueOf(latitude2));  //To retrieve Use Double.valueOf("Latitude2");
                editor.putString("Longitude2", String.valueOf(longitude2));
                editor.putString("keyAddress2", Address2);
                editor.putString("keyCity2", City2);
                editor.putString("keyState2", State2);
                editor.putString("keyPostalCode2", PostalCode2);
                editor.putString("keyMobile2", Mobile2);

                editor.commit();

                Toast.makeText(getContext(), "Data Successfully Saved", Toast.LENGTH_SHORT).show();

                btnFetch2.setEnabled(false);
                btnSubmit2.setEnabled(false);

                etAddress2.setFocusable(false);
                etAddress2.setFocusableInTouchMode(false);
                etAddress2.setClickable(false);

                etCity2.setFocusable(false);
                etCity2.setFocusableInTouchMode(false);
                etCity2.setClickable(false);

                etState2.setFocusable(false);
                etState2.setFocusableInTouchMode(false);
                etState2.setClickable(false);

                etMobile2.setFocusable(false);
                etMobile2.setFocusableInTouchMode(false);
                etMobile2.setClickable(false);

                btnEdit2.setVisibility(view.VISIBLE);
            }
        }

        if(id == R.id.edit2)
        {
            FetchAddress2();

            etAddress2.setFocusable(true);
            etAddress2.setFocusableInTouchMode(true);
            etAddress2.setClickable(true);

            etCity2.setFocusable(true);
            etCity2.setFocusableInTouchMode(true);
            etCity2.setClickable(true);

            etState2.setFocusable(true);
            etState2.setFocusableInTouchMode(true);
            etState2.setClickable(true);


            etMobile2.setFocusable(true);
            etMobile2.setFocusableInTouchMode(true);
            etMobile2.setClickable(true);

            btnFetch2.setEnabled(true);
            btnSubmit2.setEnabled(true);

            btnEdit2.setVisibility(view.GONE);
        }
    }

    public void FetchAddress2()
    {
        context = getContext();
        lm2 = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(getContext(), "Please Grant Permissions In Settings", Toast.LENGTH_SHORT).show();
        }

        else
        {
            lm2.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5, 5, Address2.this);
            pd2.show();
            pd2.setCancelable(false);
        }
    }

    @Override
    public void onLocationChanged(Location location)
    {
        latitude2 = location.getLatitude();
        longitude2 = location.getLongitude();

        String city = "";
        String state = "";

        try
        {
            List<Address> adrsList = gcd.getFromLocation(latitude2,longitude2,1);
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
                etAddress2.setText(buffer.toString());

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

        etCity2.setText(city);
        etState2.setText(state);

        pd2.dismiss();
        lm2.removeUpdates(this);
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

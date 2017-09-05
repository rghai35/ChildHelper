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


public class Address1 extends Fragment implements View.OnClickListener, LocationListener
{
    View view;
    Context context;

    EditText etAddress1;
    EditText etCity1;
    EditText etState1;
    EditText etMobile1;
    Button btnFetch1;
    Button btnSubmit1;
    Button btnEdit1;

    LocationManager lm1;
    double latitude1;
    double longitude1;

    ProgressDialog pd1;

    Geocoder gcd;

    StringBuffer buffer;

    String postalCode;

    String Address1;
    String City1;
    String State1;
    String PostalCode1;
    String Mobile1;

    SharedPreferences preferencesAddress1;
    SharedPreferences.Editor editor;

    public Address1()
    {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        etAddress1 = (EditText) view.findViewById(R.id.address1);
        etCity1 = (EditText) view.findViewById(R.id.city1);
        etState1 = (EditText) view.findViewById(R.id.state1);
        etMobile1 = (EditText) view.findViewById(R.id.mobile1);
        btnFetch1 = (Button) view.findViewById(R.id.fetch1);
        btnSubmit1 = (Button) view.findViewById(R.id.submit1);
        btnEdit1 = (Button) view.findViewById(R.id.edit1);

        pd1 = new ProgressDialog(getContext());
        pd1.setMessage("Fetching Address....");

        gcd = new Geocoder(getContext());

        preferencesAddress1 = getContext().getSharedPreferences("Address1",getContext().MODE_PRIVATE);
        editor = preferencesAddress1.edit();

        if(preferencesAddress1.contains("keyAddress1") && preferencesAddress1.contains("keyCity1") && preferencesAddress1.contains("keyState1"))
        {
            btnFetch1.setEnabled(false);
            btnSubmit1.setEnabled(false);
            btnEdit1.setVisibility(View.VISIBLE);

            etAddress1.setText(preferencesAddress1.getString("keyAddress1",""));
            etCity1.setText(preferencesAddress1.getString("keyCity1",""));
            etState1.setText(preferencesAddress1.getString("keyState1",""));
            etMobile1.setText(preferencesAddress1.getString("keyMobile1",""));

            etAddress1.setFocusable(false);
            etAddress1.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
            etAddress1.setClickable(false); // user navigates with wheel and selects widget

            etCity1.setFocusable(false);
            etCity1.setFocusableInTouchMode(false);
            etCity1.setClickable(false);

            etState1.setFocusable(false);
            etState1.setFocusableInTouchMode(false);
            etState1.setClickable(false);

            etMobile1.setFocusable(false);
            etMobile1.setFocusableInTouchMode(false);
            etMobile1.setClickable(false);

        }

        else
        {
            btnEdit1.setVisibility(View.GONE);
        }

        btnEdit1.setOnClickListener(this);
        btnSubmit1.setOnClickListener(this);
        btnFetch1.setOnClickListener(this);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_address1, container, false);

        return view;
    }

    @Override
    public void onClick(View view)
    {
        int id = view.getId();

        if(id == R.id.fetch1)
        {
            FetchAddress1();
        }

        if(id == R.id.submit1)
        {

            if(etAddress1.getText().toString().equals(""))
            {
                etAddress1.setError("Please Enter Address");
            }

            else if(etCity1.getText().toString().equals(""))
            {
                etCity1.setError("Please Enter City");
            }

            else if(etState1.getText().toString().equals(""))
            {
                etState1.setError("Please Enter State");
            }

            else if(etMobile1.getText().toString().equals(""))
            {
                etMobile1.setError("Please Enter Mobile Number");
            }

            else if(etMobile1.getText().toString().trim().length()<6 || etMobile1.getText().toString().trim().length()>13)
            {
                etMobile1.setError("Please Enter Valid Mobile Number");
            }

            else
                {
                    Address1 = etAddress1.getText().toString().trim();
                    City1 = etCity1.getText().toString().trim();
                    State1 = etState1.getText().toString().trim();
                    PostalCode1 = postalCode;
                    Mobile1 = etMobile1.getText().toString().trim();


                    editor.putString("Latitude1", String.valueOf(latitude1));  //To retrieve Use Double.valueOf("Latitude1");
                    editor.putString("Longitude1", String.valueOf(longitude1));
                    editor.putString("keyAddress1", Address1);
                    editor.putString("keyCity1", City1);
                    editor.putString("keyState1", State1);
                    editor.putString("keyPostalCode1", PostalCode1);
                    editor.putString("keyMobile1", Mobile1);

                    editor.commit();

                    Toast.makeText(getContext(), "Data Successfully Saved", Toast.LENGTH_SHORT).show();

                    btnFetch1.setEnabled(false);
                    btnSubmit1.setEnabled(false);

                    etAddress1.setFocusable(false);
                    etAddress1.setFocusableInTouchMode(false);
                    etAddress1.setClickable(false);

                    etCity1.setFocusable(false);
                    etCity1.setFocusableInTouchMode(false);
                    etCity1.setClickable(false);

                    etState1.setFocusable(false);
                    etState1.setFocusableInTouchMode(false);
                    etState1.setClickable(false);

                    etMobile1.setFocusable(false);
                    etMobile1.setFocusableInTouchMode(false);
                    etMobile1.setClickable(false);

                    btnEdit1.setVisibility(view.VISIBLE);
            }
        }

        if(id == R.id.edit1)
        {
            etAddress1.setFocusable(true);
            etAddress1.setFocusableInTouchMode(true);
            etAddress1.setClickable(true);

            etCity1.setFocusable(true);
            etCity1.setFocusableInTouchMode(true);
            etCity1.setClickable(true);

            etState1.setFocusable(true);
            etState1.setFocusableInTouchMode(true);
            etState1.setClickable(true);


            etMobile1.setFocusable(true);
            etMobile1.setFocusableInTouchMode(true);
            etMobile1.setClickable(true);

            btnFetch1.setEnabled(true);
            btnSubmit1.setEnabled(true);

            btnEdit1.setVisibility(view.GONE);
        }
    }

    public void FetchAddress1()
    {
        context = getContext();
        lm1 = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(getContext(), "Please Grant Permissions In Settings", Toast.LENGTH_SHORT).show();
        }

        else
        {
            lm1.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5, 5, Address1.this);
            pd1.show();
        }
    }

    @Override
    public void onLocationChanged(Location location)
    {
        latitude1 = location.getLatitude();
        longitude1 = location.getLongitude();

        String city = "";
        String state = "";

        try
        {
            List<Address> adrsList = gcd.getFromLocation(latitude1,longitude1,1);
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
                etAddress1.setText(buffer.toString());

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

        etCity1.setText(city);
        etState1.setText(state);

        pd1.dismiss();
        lm1.removeUpdates(this);
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

package com.example.rohitghai.childtracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditProfile extends AppCompatActivity implements View.OnClickListener
{
    TextView tvEditProfile;
    EditText etNameEdit;
    EditText etEmailEdit;
    EditText etMobileEdit;
    EditText etChildNameEdit;
    Button saveEdit;

    SharedPreferences preferencesRegistration;
    SharedPreferences.Editor editor;

    String nameEdit;
    String emailEdit;
    String mobileEdit;
    String childNameEdit;

    Typeface tf1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getSupportActionBar().hide();

        tvEditProfile = (TextView)findViewById(R.id.editprofile);
        etNameEdit = (EditText) findViewById(R.id.nameedit);
        etEmailEdit = (EditText) findViewById(R.id.emailedit);
        etMobileEdit = (EditText)findViewById(R.id.mobileedit);
        etChildNameEdit = (EditText)findViewById(R.id.childnameedit);
        saveEdit = (Button)findViewById(R.id.saveedit);

        tf1 = Typeface.createFromAsset(getAssets(), "LobsterTwo-BoldItalic.otf");
        tvEditProfile.setTypeface(tf1);

        preferencesRegistration = getSharedPreferences("RegistrationDetails",MODE_PRIVATE);
        editor = preferencesRegistration.edit();

        nameEdit = preferencesRegistration.getString("keyName","");
        emailEdit = preferencesRegistration.getString("keyEmail","");
        mobileEdit = preferencesRegistration.getString("keyMobile","");
        childNameEdit = preferencesRegistration.getString("keyChildName","");

        etNameEdit.setText(nameEdit);
        etEmailEdit.setText(emailEdit);
        etMobileEdit.setText(mobileEdit);
        etChildNameEdit.setText(childNameEdit);

        saveEdit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view)
    {
        int id = view.getId();

        if(id == R.id.saveedit)
        {
            if(etNameEdit.getText().toString().equals(""))
            {
                etNameEdit.setError("Please Enter Name");
            }

            else if(etEmailEdit.getText().toString().equals(""))
            {
                etEmailEdit.setError("Please Enter Email");
            }

            else if(etMobileEdit.getText().toString().equals(""))
            {
                etMobileEdit.setError("Please Enter Mobile No.");
            }

            else if(etChildNameEdit.getText().toString().equals(""))
            {
                etChildNameEdit.setError("Please Enter Child Name");
            }

            else if(!etEmailEdit.getText().toString().trim().contains("@") || !etEmailEdit.getText().toString().trim().contains("."))
            {
                etEmailEdit.setError("Please Enter Valid Email");
            }

            else if(etMobileEdit.getText().toString().trim().length()<6 || etMobileEdit.getText().toString().trim().length()>13)
            {
                etMobileEdit.setError("Please Enter Valid Mobile Number");
            }

            else
            {
                nameEdit = etNameEdit.getText().toString().trim();
                emailEdit = etEmailEdit.getText().toString().trim();
                mobileEdit = etMobileEdit.getText().toString().trim();
                childNameEdit = etChildNameEdit.getText().toString().trim();

                editor.putString("keyName", nameEdit);
                editor.putString("keyEmail", emailEdit);
                editor.putString("keyMobile", mobileEdit);
                editor.putString("keyChildName", childNameEdit);

                editor.commit();

                Toast.makeText(this, "Details Successfull Saved", Toast.LENGTH_SHORT).show();
                finish();
            }

        }

    }
}

package com.example.rohitghai.childtracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.preference.Preference;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnClickListener
{
    TextView tvRegister;
    EditText etName;
    EditText etEmail;
    EditText etPassword;
    EditText etRetypePassword;
    EditText etMobile;
    EditText etChildName;
    Button btnRegister;

    String name;
    String email;
    String password;
    String mobile;
    String childName;


    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    Typeface tf1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        initViews();

    }

    void initViews()
    {
        tvRegister = (TextView)findViewById(R.id.registerhere);
        etName = (EditText)findViewById(R.id.name);
        etEmail = (EditText)findViewById(R.id.email);
        etPassword = (EditText)findViewById(R.id.password);
        etRetypePassword = (EditText)findViewById(R.id.retypepassword);
        etMobile = (EditText)findViewById(R.id.mobile);
        etChildName = (EditText)findViewById(R.id.childname);
        btnRegister = (Button)findViewById(R.id.register);

        tf1 = Typeface.createFromAsset(getAssets(), "Billabong.ttf");
        tvRegister.setTypeface(tf1);

        preferences = getSharedPreferences("RegistrationDetails",MODE_PRIVATE);
        editor = preferences.edit();


        btnRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View view)
    {
        int id = view.getId();

        if(id == R.id.register)
        {
            if(etName.getText().toString().equals(""))
            {
                etName.setError("Please Enter Name");
            }

            else if(etEmail.getText().toString().equals(""))
            {
                etEmail.setError("Please Enter Email");
            }

            else if(etPassword.getText().toString().equals(""))
            {
                etPassword.setError("Please Enter Password");
            }

            else if(etRetypePassword.getText().toString().equals(""))
            {
                etRetypePassword.setError("Please Enter Retype Password");
            }

            else if(etMobile.getText().toString().equals(""))
            {
                etMobile.setError("Please Enter Mobile No.");
            }

            else if(etChildName.getText().toString().equals(""))
            {
                etChildName.setError("Please Enter Child Name");
            }

            else if(!etPassword.getText().toString().trim().equals(etRetypePassword.getText().toString().trim()))
            {
                etRetypePassword.setError("Retype Password Doesn't Match");
            }

            else if(!etEmail.getText().toString().trim().contains("@") || !etEmail.getText().toString().trim().contains("."))
            {
                etEmail.setError("Please Enter Valid Email");
            }

            else if(etMobile.getText().toString().trim().length()<6 || etMobile.getText().toString().trim().length()>13)
            {
                etMobile.setError("Please Enter Valid Mobile Number");
            }

            else if(etPassword.getText().toString().trim().length()<5)
            {
                etPassword.setError("Password Must Have 5 or More Characters");
            }

            else
            {
                name = etName.getText().toString().trim();
                email = etEmail.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                mobile = etMobile.getText().toString().trim();
                childName = etChildName.getText().toString().trim();

                editor.putString("keyName",name);
                editor.putString("keyEmail",email);
                editor.putString("keyPassword",password);
                editor.putString("keyMobile",mobile);
                editor.putString("keyChildName",childName);

                editor.commit();

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Registration Done");
                builder.setIcon(R.drawable.ok);
                builder.setMessage("You Are Successfully Registered \nPlease Login to Continue");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();

                    }
                });

                builder.setCancelable(false);
                AlertDialog dialog = builder.create();
                dialog.show();

            }

        }
    }

    @Override
    public void onBackPressed()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Registration Cancelled");
        builder.setIcon(R.drawable.alert);
        builder.setMessage("You Are Not Registered!! \nYour Data Will Be Lost \nAre You Sure You Want to Exit");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                finish();
            }
        });

        builder.setNegativeButton("Cancel", null);

        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}

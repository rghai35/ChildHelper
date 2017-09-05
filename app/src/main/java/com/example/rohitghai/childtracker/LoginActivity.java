package com.example.rohitghai.childtracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{
    EditText etEmail;
    EditText etPasssword;
    Button btnLogin;
    TextView tvForgotPassword;
    CheckBox chbxRememberMe;
    ImageView imgbtnSpeedometer;

    String email;
    String password;
    String mobile;
    String message;
    Boolean rememberMe;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    SmsManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        preferences = getSharedPreferences("RegistrationDetails",MODE_PRIVATE);
        editor = preferences.edit();

        etEmail = (EditText)findViewById(R.id.email);

        rememberMe = preferences.getBoolean("keyRememberMe", false);
        chbxRememberMe = (CheckBox)findViewById(R.id.rememberme);

        if (rememberMe == true)
        {
             etEmail.setText(preferences.getString("keyEmail", ""));
             chbxRememberMe.setChecked(true);
        }

        initViews();
    }

    void initViews()
    {
        etPasssword = (EditText)findViewById(R.id.password);
        btnLogin = (Button)findViewById(R.id.login);
        tvForgotPassword = (TextView)findViewById(R.id.forgotpassword);
        imgbtnSpeedometer = (ImageView)findViewById(R.id.speedometer);

        sm = SmsManager.getDefault();

        btnLogin.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
        imgbtnSpeedometer.setOnClickListener(this);

    }

    @Override
    public void onClick(View view)
    {
        int id = view.getId();

        if(id == R.id.login)
        {
        if(etEmail.getText().toString().trim().equals(""))
        {
            etEmail.setError("Please Enter Email");
        }

        else if(etPasssword.getText().toString().trim().equals(""))
        {
            etPasssword.setError("Please Enter Password");
        }

        else if(!etEmail.getText().toString().trim().contains("@") || !etEmail.getText().toString().trim().contains("."))
        {
            etEmail.setError("Please Enter Valid Email");
        }

        else
        {
            email = preferences.getString("keyEmail", "");
            password = preferences.getString("keyPassword", "");

            if (chbxRememberMe.isChecked())
            {
                editor.putBoolean("keyRememberMe", true);
                editor.commit();
            }
            else
            {
                editor.remove("keyRememberMe");
                editor.commit();
            }

            if (etEmail.getText().toString().trim().equals(email) && etPasssword.getText().toString().toString().equals(password))
            {

                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
            else
            {
                Toast.makeText(this, "Email Or Password Doesn't Match", Toast.LENGTH_SHORT).show();
            }
        }
        }

        if(id == R.id.forgotpassword)
        {
            email = preferences.getString("keyEmail", "");

            if (etEmail.getText().toString().trim().equals(email))
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Message Confirmation");
                builder.setIcon(R.drawable.messageconfirmation);
                builder.setMessage("Your Password Will Be Sent At Your Registered Mobile Number");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener()

                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)

                    {
                        password = preferences.getString("keyPassword", "");
                        mobile = preferences.getString("keyMobile", "");

                        message = "Your Password is: " + password;

                        sm.sendTextMessage(mobile, null, message, null, null);

                        Toast.makeText(LoginActivity.this, "Your Password Has Been Send To Your registered Mobile Number", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("NO", null);
                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }

                else
                {
                    Toast.makeText(LoginActivity.this, "Please Enter Registered Email", Toast.LENGTH_SHORT).show();
                }
        }

        if(id == R.id.speedometer)
        {
            Toast.makeText(this, "Soon You Will Be Helped", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(LoginActivity.this,HelpActivity.class);
            startActivity(intent);
        }
    }
}

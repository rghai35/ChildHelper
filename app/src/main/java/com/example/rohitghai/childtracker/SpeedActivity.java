package com.example.rohitghai.childtracker;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class SpeedActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener, View.OnClickListener
{
    LinearLayout llLinearLayout1;
    NumberPicker npSpeed1;
    TextView tvDisplaySpeed1;
    Button btnNext;

    LinearLayout llLinearLayout2;
    NumberPicker npSpeed2;
    TextView tvDisplaySpeed2;
    Button btnSubmit;

    LinearLayout llLinearLayout3;
    TextView tvSpeed1Is;
    TextView tvSpeed2Is;
    Button btnEdit;

    int speed1;
    int speed2;

    int getSpeed1;
    int getSpeed2;

    SharedPreferences preferences;
    SharedPreferences preferencesRegistration;
    SharedPreferences.Editor editor;

    Button btnOk;
    Button btnCancel;
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed);

        ActionBar bar = this.getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#cee7fc")));
        bar.setTitle(Html.fromHtml("<font color = '#000000'>Set Speed </font>"));

        llLinearLayout1 = (LinearLayout)findViewById(R.id.linerlayout1);
        llLinearLayout2 = (LinearLayout)findViewById(R.id.linerlayout2);
        llLinearLayout3 = (LinearLayout)findViewById(R.id.linerlayout3);

        preferences = getSharedPreferences("Speed",MODE_PRIVATE);
        preferencesRegistration = getSharedPreferences("RegistrationDetails",MODE_PRIVATE);
        editor = preferences.edit();

        tvSpeed1Is = (TextView)findViewById(R.id.speed1is);
        tvSpeed2Is = (TextView)findViewById(R.id.speed2is);


        if(preferences.contains("keySpeed1") && preferences.contains("keySpeed2"))
        {
            llLinearLayout1.setVisibility(View.GONE);
            llLinearLayout2.setVisibility(View.GONE);
            llLinearLayout3.setVisibility(View.VISIBLE);

            int get1 = preferences.getInt("keySpeed1",0);
            tvSpeed1Is.setText("Speed 1: " + get1 + " km/h");

            int get2 = preferences.getInt("keySpeed2",0);
            tvSpeed2Is.setText("Speed 2: " + get2 + " km/h");

        }

            initViews();
    }

    void initViews()
    {
        tvDisplaySpeed1 = (TextView)findViewById(R.id.displayspeed1);
        npSpeed1 = (NumberPicker) findViewById(R.id.speed1);
        btnNext = (Button)findViewById(R.id.next);

        tvDisplaySpeed2 = (TextView)findViewById(R.id.displayspeed2);
        npSpeed2 = (NumberPicker) findViewById(R.id.speed2);
        btnSubmit = (Button)findViewById(R.id.submit);


        btnEdit = (Button)findViewById(R.id.edit);

        String speed[] = new String[20];
        int start = 10;

        for(int i = 0 ; i < 20 ; i++)
        {
            speed[i] = start + "";
            start = start + 10;
        }

        npSpeed1.setMaxValue(20);
        npSpeed1.setMinValue(1);
        npSpeed1.setDisplayedValues(speed);
        npSpeed1.setWrapSelectorWheel(true);

        npSpeed2.setMaxValue(20);
        npSpeed2.setMinValue(1);
        npSpeed2.setDisplayedValues(speed);
        npSpeed2.setWrapSelectorWheel(true);


        npSpeed1.setOnValueChangedListener(this);
        npSpeed2.setOnValueChangedListener(this);

        btnNext.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        btnEdit.setOnClickListener(this);


    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1)
    {
        int id = numberPicker.getId();

        if(id==R.id.speed1)
        {
            tvDisplaySpeed1.setText("Speed 1 : " + (i1 * 10) + " km/h");
            speed1 = i1 * 10;
        }

        if(id==R.id.speed2)
        {
            tvDisplaySpeed2.setText("Speed 2 : " + (i1 * 10) + " km/h");
            speed2 = i1 * 10;
        }

    }

    @Override
    public void onClick(View view)
    {
        int id = view.getId();

        if(id == R.id.next)
        {
            if(speed1 == 0)
            {
                Toast.makeText(this, "Please Select Speed", Toast.LENGTH_SHORT).show();
            }

            else if(speed1 == 200)
            {
                Toast.makeText(this, "Speed 1 Cannot Be Maximum Speed", Toast.LENGTH_SHORT).show();
            }

            else
            {
                editor.putInt("keySpeed1", speed1);
                editor.commit();

                llLinearLayout1.setVisibility(View.GONE);
                llLinearLayout2.setVisibility(View.VISIBLE);
            }

        }

        if(id == R.id.submit)
        {
            if(speed2 == 0)
            {
                Toast.makeText(this, "Please Select Speed", Toast.LENGTH_SHORT).show();
            }
            else if(speed2 <= speed1)
            {
                Toast.makeText(this, "Speed 2 Must Be Greater Than Speed 1", Toast.LENGTH_SHORT).show();
            }

            else
            {
                editor.putInt("keySpeed2", speed2);
                editor.commit();

                getSpeed1 = preferences.getInt("keySpeed1", 0);
                tvSpeed1Is.setText("Speed 1: " + getSpeed1 + " km/h");

                getSpeed2 = preferences.getInt("keySpeed2", 0);
                tvSpeed2Is.setText("Speed 2: " + getSpeed2 + " km/h");

                llLinearLayout2.setVisibility(View.GONE);
                llLinearLayout3.setVisibility(View.VISIBLE);

                Intent intentService = new Intent(SpeedActivity.this,SpeedService.class);
                startService(intentService);
            }

        }

        if(id == R.id.edit)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirmation");
            builder.setIcon(R.drawable.confirmation);
            builder.setMessage("Are You Sure You Want To Edit ?");
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    final Dialog dialog = new Dialog(SpeedActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
                    dialog.setContentView(R.layout.password_dialog);
                    dialog.setTitle("Password Confirmation");
                    dialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,R.drawable.confirmpassword);
                    dialog.setCancelable(false);

                    etPassword = dialog.findViewById(R.id.password);
                    btnOk = dialog.findViewById(R.id.ok);
                    btnCancel = dialog.findViewById(R.id.cancel);

                    btnOk.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            String matchPassword = preferencesRegistration.getString("keyPassword","");

                            if(etPassword.getText().toString().trim().equals(matchPassword))
                            {
                                dialog.hide();
                                editor.remove("keySpeed1");
                                editor.remove("keySpeed2");
                                editor.commit();

                                llLinearLayout3.setVisibility(View.GONE);
                                llLinearLayout1.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                Toast.makeText(SpeedActivity.this, "Password Doesn't Match", Toast.LENGTH_SHORT).show();
                                dialog.hide();
                            }
                        }
                    });


                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view)
                        {
                            dialog.hide();
                        }
                    });

                    dialog.show();

                }
            });

            builder.setNegativeButton("NO",null);

            builder.setCancelable(false);
            AlertDialog dialog = builder.create();
            dialog.show();

        }
    }
}

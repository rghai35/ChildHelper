package com.example.rohitghai.childtracker;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener
{
    EditText etOldPassword;
    EditText etNewPassword;
    Button btnChangePassword;

    SharedPreferences preferencesRegistration;
    SharedPreferences.Editor editor;

    SharedPreferences preferences;

    ImageButton imgbtnViewDatabase;
    TextView tvViewDatabaseText;
    View vwDatabaseUnderline;

    ImageButton imgbtnStart;
    ImageButton imgbtnStop;

    boolean start = true;
    boolean stop = false;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preferencesRegistration = getSharedPreferences("RegistrationDetails",MODE_PRIVATE);
        editor = preferencesRegistration.edit();

        preferences = getSharedPreferences("Speed",MODE_PRIVATE);

        imgbtnViewDatabase = (ImageButton)findViewById(R.id.viewdatabaseimage);
        tvViewDatabaseText = (TextView)findViewById(R.id.viewdatabasetext);
        vwDatabaseUnderline = (View)findViewById(R.id.viewdatabaseunderline);

        imgbtnStart = (ImageButton)findViewById(R.id.start);
        imgbtnStop = (ImageButton)findViewById(R.id.stop);

        if(!preferences.contains("keySpeed1") && !preferences.contains("keySpeed2"))
        {
            Toast.makeText(this, "Please Set Speed From The Navigation Drawer", Toast.LENGTH_SHORT).show();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        imgbtnViewDatabase.setOnClickListener(this);
        tvViewDatabaseText.setOnClickListener(this);
        vwDatabaseUnderline.setOnClickListener(this);

        imgbtnStart.setOnClickListener(this);
        imgbtnStop.setOnClickListener(this);
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Log Out");
            builder.setIcon(R.drawable.ok);
            builder.setMessage("You Are Sure You Want To Log Out");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();

                }
            });

            builder.setNegativeButton("No",null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_information)
        {
            Intent intent = new Intent(MainActivity.this,InformationActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_speed)
        {
            Intent intent = new Intent(MainActivity.this,SpeedActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_address)
        {
            Intent intent = new Intent(MainActivity.this,AddressActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_edit_profile)
        {
            Intent intent = new Intent(MainActivity.this,EditProfile.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_change_password)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirmation");
            builder.setIcon(R.drawable.confirmation);
            builder.setMessage("Are You Sure You Want To Change Password ?");
            builder.setPositiveButton("YES",
                    new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    final Dialog dialog = new Dialog(MainActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
                    dialog.setContentView(R.layout.change_password_dialog);
                    dialog.setTitle(Html.fromHtml("<font color = '#ffffff'>Change Password</font>"));
                    dialog.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,R.drawable.changepasswordicon);
                    //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));  //For Transparent Background

                    dialog.getWindow().setBackgroundDrawableResource(R.drawable.changepassworddialog);

                    //int dividerId = dialog.getContext().getResources().getIdentifier("android:id/titleDivider",null,null);   //Changing Divider Line Color Under Title
                    //View divider = dialog.findViewById(dividerId);
                    //divider.setBackgroundColor(getResources().getColor(R.color.white));

                    etOldPassword = dialog.findViewById(R.id.oldpassword);
                    etNewPassword = dialog.findViewById(R.id.newpassword);
                    btnChangePassword = dialog.findViewById(R.id.changepassword);
                    btnChangePassword.getBackground().setAlpha(64);

                    btnChangePassword.setOnClickListener(
                            new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            String matchPassword = preferencesRegistration.getString("keyPassword","");

                            if(etOldPassword.getText().toString().trim().equals(""))
                            {
                                etOldPassword.setError("Please Enter Old Password");
                            }

                            else if(etNewPassword.getText().toString().trim().equals(""))
                            {
                                etNewPassword.setError("Please Enter New Password");
                            }


                            else
                            {
                                if(etOldPassword.getText().toString().trim().equals(matchPassword))
                                {
                                    dialog.hide();
                                    editor.putString("keyPassword", etNewPassword.getText().toString().trim());
                                    editor.commit();

                                    Toast.makeText(MainActivity.this, "Password Successfully Changed ", Toast.LENGTH_SHORT).show();
                                }

                                else
                                {
                                    Toast.makeText(MainActivity.this, "Old Password Doesn't Match", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    });

                    dialog.show();

                }
            });

            builder.setNegativeButton("NO",null);

            AlertDialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();
        }
        else if (id == R.id.nav_logout)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Log Out");
            builder.setIcon(R.drawable.ok);
            builder.setMessage("You Are Sure You Want To Log Out");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    Intent intentService = new Intent(MainActivity.this,SpeedService.class);
                    stopService(intentService);

                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();

                }
            });

            builder.setNegativeButton("No",null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else if (id == R.id.nav_share)
        {
            Intent intentShare = new Intent(Intent.ACTION_SEND);
            intentShare.setType("text/plain");

            String shareSubject = "Child Tracker App";
            String shareBody = "This App Is Under Development";

            intentShare.putExtra(Intent.EXTRA_SUBJECT,shareSubject);
            intentShare.putExtra(Intent.EXTRA_TEXT,shareBody);

            startActivity(Intent.createChooser(intentShare,"Share Using"));
        }
        else if (id == R.id.nav_rate_us)
        {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view)
    {
        int id = view.getId();

        if(id == R.id.viewdatabaseimage || id == R.id.viewdatabasetext || id == R.id.viewdatabaseunderline)
        {
            Intent intent = new Intent(MainActivity.this,FetchData.class);
            startActivity(intent);
        }

        if(id == R.id.start)
        {
            if(start)
            {
                imgbtnStart.setImageResource(R.drawable.startpressed);
                imgbtnStop.setImageResource(R.drawable.stopunpressed);
                Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
                start = false;
                stop = false;
            }

            else
            {
                imgbtnStart.setImageResource(R.drawable.startunpressed);
                imgbtnStop.setImageResource(R.drawable.stoppressed);
                Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show();
                start = true;
                stop = true;
            }

        }

        if(id == R.id.stop)
        {
            if(stop)
            {
                imgbtnStop.setImageResource(R.drawable.stopunpressed);
                imgbtnStart.setImageResource(R.drawable.startpressed);
                Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
                stop = false;
                start = false;
            }

            else
            {
                imgbtnStop.setImageResource(R.drawable.stoppressed);
                imgbtnStart.setImageResource(R.drawable.startunpressed);
                Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show();
                stop = true;
                start = true;
            }

        }
    }
}

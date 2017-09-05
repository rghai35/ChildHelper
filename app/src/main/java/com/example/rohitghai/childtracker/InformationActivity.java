package com.example.rohitghai.childtracker;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class InformationActivity extends AppCompatActivity
{
    TextView tvTitle;
    TextView tvDetails1;
    TextView tvDetails2;

    Typeface tf1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        ActionBar bar = this.getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff0000")));
        bar.setTitle("About The App");

        tvTitle = (TextView)findViewById(R.id.title);
        tf1 = Typeface.createFromAsset(getAssets(), "Billabong.ttf");
        tvTitle.setTypeface(tf1);

        tvDetails1 = (TextView)findViewById(R.id.details1);
        tvDetails2 = (TextView)findViewById(R.id.details2);

        tvDetails1.setText("This module of the application acts as an helper if the child is in trouble. If at any time, child is in trouble " +
                "he/she just have to press a single button and a text message including his/her location will be send to his/her parents or the " +
                "nearby relatives. After installing the application parents have to fill their location, relative's location and thier mobile " +
                "number and all the details will be saved in the database.");

        tvDetails1.setTextIsSelectable(false);
        tvDetails1.setFocusable(false);

        tvDetails2.setText("This module of the application tracks the speed of the child. Many a times parents are worried when they give " +
                "their child a new vehicle. It may be a two-wheeler (activa, bike etc.) or a four-wheeler (car). They are always in tension " +
                "that their child might drive very fast which can lead to an accident. Now parents do not have to worry and take tension. " +
                "This application will do that job. It will track the speed of the child and records it in the database. Moreover, if the " +
                "child drives more fast, this application will send a text message to the parents. Parents just have to install this app in " +
                "their child's smartphone and set the values of speed1 and speed2 according to their choice. Speed1 is that speed which when " +
                "crossed by child then records will start to be inserted in the database. Speed2 is that speed which when crossed by child" +
                "then a text message will be send to parent's mobile number.");

        tvDetails2.setTextIsSelectable(false);
        tvDetails2.setFocusable(false);
        //tvDetails2.setTextAlignment(View.T);


    }
}

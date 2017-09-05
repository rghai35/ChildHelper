package com.example.rohitghai.childtracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Rohit Ghai on 19-08-2017.
 */

public class DataAdapter extends ArrayAdapter<Data>
{
    Context context;
    int resource;
    ArrayList<Data> dataList;

    TextView tvMonth;
    TextView tvDate;
    TextView tvTime;
    TextView tvLocation;
    TextView tvSpeed;

    ImageView imgRoundRed;
    ImageView imgRoundBlue;

    SharedPreferences preferences;

    float speed1;
    float speed2;

    public DataAdapter(Context context, int resource, ArrayList<Data> objects)
    {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        dataList = objects;

    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View view = null;

        view = LayoutInflater.from(context).inflate(resource,parent,false);

        tvMonth = (TextView)view.findViewById(R.id.month);
        tvDate = (TextView)view.findViewById(R.id.date);
        tvTime = (TextView)view.findViewById(R.id.time);
        tvLocation = (TextView)view.findViewById(R.id.location);
        tvSpeed = (TextView)view.findViewById(R.id.speed);

        imgRoundRed = (ImageView)view.findViewById(R.id.roundred);
        imgRoundBlue = (ImageView)view.findViewById(R.id.roundblue);

        preferences = context.getSharedPreferences("Speed",Context.MODE_PRIVATE);

        Data data = dataList.get(position);

        DateFormat formatter;                                //Converting date From String To Date Type
        Date date = new Date();
        formatter = new SimpleDateFormat("dd-MM-yyyy");

        try
        {
            date = formatter.parse(data.getDate());
            //Toast.makeText(context, " " + date + " " + date.getDate(), Toast.LENGTH_SHORT).show();
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }


        String month = new SimpleDateFormat("MMM").format(date).toUpperCase();   //Converting Month From Number To Text

        float speedFloat = (Float) data.getSpeed();                     //Converting Speed From Float To Int
        int speedInt = Math.round(speedFloat);

        speed1 = (int)preferences.getInt("keySpeed1",0);
        speed2 = (int)preferences.getInt("keySpeed2",0);

        if(speedInt>=speed1 && speedFloat<speed2)                   //Checking Speed if speedInt >= speed1 then Blue Color
        {
            imgRoundRed.setVisibility(view.GONE);
            imgRoundBlue.setVisibility(view.VISIBLE);
            tvSpeed.setText(("" + speedInt));
        }

        if(speedInt>=speed2)                                         //Checking Speed if speedInt >= speed2 then Red Color
        {
            imgRoundBlue.setVisibility(view.GONE);
            imgRoundRed.setVisibility(view.VISIBLE);
            tvSpeed.setText(("" + speedInt));
        }


        tvMonth.setText(month);
        tvDate.setText("" + date.getDate());
        tvTime.setText(data.getTime());
        tvLocation.setText(data.getLocation());

        return view;
    }
}

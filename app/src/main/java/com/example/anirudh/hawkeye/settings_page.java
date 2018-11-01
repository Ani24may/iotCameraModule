package com.example.anirudh.hawkeye;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class settings_page extends AppCompatActivity{

        private Toolbar toolbar;
    private Button btn1;
    private Button btn2;
    private TextView t1;
    private TextView t2;
    private TextView current;
    private Button btn3;
    static final int dialog_id1=0;
    static final int dialog_id2=1;
    int hour1;
    int hour2;
    int min1;
    int min2;
    String s1;
    String s2;
    String s3;
    String s4;
    String f1;
    String f2;
    int cur;
    int curmin;
    int start1;
    int end1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_page);
        toolbar=(Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn1=(Button)findViewById(R.id.btnstart);
        btn2=(Button)findViewById(R.id.btnend);
        btn3=(Button)findViewById(R.id.btnset);
        t1=(TextView)findViewById(R.id.txtstrt);
        t2=(TextView)findViewById(R.id.txtend);
        current=(TextView)findViewById(R.id.cur);
        Calendar rightNow = Calendar.getInstance();
        cur = rightNow.get(Calendar.HOUR_OF_DAY);
        curmin=rightNow.get(Calendar.MINUTE);
        f1=Integer.toString(cur);
        f2=Integer.toString(curmin);
        current.setText("YOUR CURRENT TIME IS "+f1+":"+f2);
        Log.d("current hour in settin",Integer.toString(cur));


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(dialog_id1);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(dialog_id2);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp=getSharedPreferences("time", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sp.edit();
                editor.putInt("starthour",start1);
                editor.putInt("endhour",end1);
                editor.putInt("currenthour",cur);
                Log.d("start hour in setting",Integer.toString(start1));
                Log.d("end hour in setting",Integer.toString(end1));
                Log.d("cuurent hour in setting",Integer.toString(cur));
                editor.commit();

            }
        });

    }

    @Override
    protected Dialog onCreateDialog(int id)
    {
            if(id==dialog_id1)
                return new TimePickerDialog(settings_page.this,TimePickerListener1,hour1,min1,false);
        else if(id==dialog_id2)
                return new TimePickerDialog(settings_page.this,TimePickerListener2,hour2,min2,false);
        else
            return null;
    }


        protected TimePickerDialog.OnTimeSetListener TimePickerListener1= new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                timePicker.setIs24HourView(true);
              s1=Integer.toString(i);
                s2=Integer.toString(i1);
                start1=i;
                t1.setText(s1+":"+s2);
                Log.d("start hour in settings",Integer.toString(i));


            }
        };

        protected TimePickerDialog.OnTimeSetListener TimePickerListener2 = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                timePicker.setIs24HourView(true);
                s3=Integer.toString(i);
                s4=Integer.toString(i1);
                end1=i;
                t2.setText(s3+":"+s4);

                Log.d("end hour in settings",Integer.toString(i));


            }
        };



}

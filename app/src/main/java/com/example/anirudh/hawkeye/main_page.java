package com.example.anirudh.hawkeye;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class main_page extends AppCompatActivity{

    private Toolbar toolbar;

    private static final String def="Nothing";

    private TextView txtuser;
    private TextView txtpswd;
    private TextView port;
    private String data="/pandey/android";
    private String str=null;
    private Socket s=null;
    //private Button clck;
    private String user;
    private String pswd;
    //private tcp_client tc=null;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private terminal t;
    private grid g;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        //port=(TextView)findViewById(R.id.port);
        //txtuser=(TextView)findViewById(R.id.textView5);
        //txtpswd=(TextView)findViewById(R.id.textView6);

        toolbar=(Toolbar)findViewById(R.id.app_bar);
        //clck=(Button)findViewById(R.id.click);
        if(toolbar!=null)
        {
            setSupportActionBar(toolbar);
        }

        t=new terminal();
        g=new grid();
        fragmentManager=getSupportFragmentManager();
        transaction=fragmentManager.beginTransaction();
        /*try {
            new Thread() {

                public void run()
                {
                    try {
                        s=new Socket("10.55.2.195",2223);
                        OutputStreamWriter osw = new OutputStreamWriter (s.getOutputStream());
                        osw.write(data);
                        osw.flush();
                        BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
                        str=br.readLine();
                        //port.setText(str);
                       // osw.close();
                        s.close();

                        //s.close();
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }*/

        transaction.add(R.id.main_page, t, "terminal");
        transaction.add(R.id.main_page,g,"grid");
        transaction.commit();
            /*if(str==null)
            {
             terminal ter=(terminal)fragmentManager.findFragmentByTag("terminal");
                ter.setTextView("error");


            }*/

        
        /*clck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent i=new Intent(main_page.this,view_image.class);
                i.putExtra("key",str);
                startActivity(i);
            }
        });*/







        SharedPreferences shp=getSharedPreferences("myData", Context.MODE_PRIVATE);
        user=shp.getString("username",def);
         pswd=shp.getString("password",def);

        /*if(user.equals(def) || pswd.equals(def))
        {
            Toast.makeText(this,"No data found",Toast.LENGTH_SHORT).show();
            txtuser.setText("none");
            txtpswd.setText("none");
            //txtuser.setVisibility(View.GONE);
            //txtpswd.setVisibility(View.GONE);


        }
        else
        {
            txtuser.setText(user);
            txtpswd.setText(pswd);
        }*/




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        if(id==(R.id.menu))
        {
            startActivity(new Intent(this,settings_page.class));
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResume() //we have implemented here onResume beacuse just after commit() method may be Fragment is not ready.
    {
        super.onResume();
         /*if(str==null)
            {
             terminal ter=(terminal)fragmentManager.findFragmentByTag("terminal");
                ter.setTextView("error");


            }*/

    }
}

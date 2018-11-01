package com.example.anirudh.hawkeye;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

/**
 * Created by anirudh on 11/9/17.
 */

public class grid extends Fragment implements AdapterView.OnItemClickListener {


    private String data="/pandey/android";
    private String str=null;
    //private int port;
    private Socket s=null;
   // private String fetchport=null;



    private GridView mygridview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.grid_layout,container,false);
        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        SharedPreferences sdd=getActivity().getSharedPreferences("checkNoti",Context.MODE_PRIVATE);
        boolean b=sdd.getBoolean("ttyl",true);
        mygridview=(GridView)getActivity().findViewById(R.id.gridview);
        mygridview.setAdapter(new myGridAdapter(getActivity(), b));
        mygridview.setOnItemClickListener(this);
        Bundle extra=getActivity().getIntent().getExtras();
        if(extra!=null)
        {
            str=extra.getString("myudpport");
        }


      /*try {
            new Thread() {

                public void run()
                {
                    try {
                        Log.d("con : ","connecting");
                        //final ProgressDialog pd = new ProgressDialog(getActivity());
                        //pd.setMessage("Connecting...");
                        //pd.show();
                        s=new Socket("192.168.2.5",2223);
                        Log.d("con : ","connected");
                        OutputStreamWriter osw = new OutputStreamWriter (s.getOutputStream());
                        osw.write(data);
                        osw.flush();
                        Log.d("con : ","username given to server");
                        DataInputStream dis = new DataInputStream(s.getInputStream());
                        byte[] data = new byte[5];
                        dis.read(data);
                        str= new String(data);
                        str = str.trim();
                        Log.d("port : ",str);
                        //port=dis.readInt();
                        //Toast.makeText(getActivity(),Integer.toString(port),Toast.LENGTH_SHORT).show();
                        //Log.d("port : ",Integer.toString(port));
                        //port.setText(str);
                        // osw.close();
                        s.close();

                        //linlaHeaderProgress.setVisibility(View.GONE);
                        //osw.close();
                        //Thread.currentThread().interrupt();

                        //s.close();
                    }
                    catch(Exception e)
                    {
                        //Toast.makeText(getActivity(),"could not connect to main server",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }.start();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }*/



        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("status: ", "onStart() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("status: ", "onStop() called");
        //try {
          //  s.close();
        //}
        //catch(Exception e)
        //{
          //  e.printStackTrace();
        //}
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("status: ", "onDestroy() called");
        /*try {
            s.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }*/
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d("status: ", "onPause() called");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d("status: ", "onResume() called");
       /* new Thread() {

            public void run() {
                try {
                    Log.d("con : ", "connecting");
                    s = new Socket("192.168.2.3", 2223);
                    s.setReuseAddress(true);
                    Log.d("con : ", "connected");
                    OutputStreamWriter osw = new OutputStreamWriter(s.getOutputStream());
                    osw.write(data);
                    osw.flush();
                    Log.d("con : ", "username given to server");
                    DataInputStream dis = new DataInputStream(s.getInputStream());
                    byte[] data = new byte[5];
                    dis.read(data);

                    str = new String(data);
                    str = str.trim();
                    Log.d("port : ", str);
                    //port=dis.readInt();
                    //Toast.makeText(getActivity(),Integer.toString(port),Toast.LENGTH_SHORT).show();
                    //Log.d("port : ",Integer.toString(port));
                    //port.setText(str);
                    // osw.close();
                    s.close();
                    Thread.currentThread().interrupt();

                    //s.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();*/

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent ii=new Intent(getActivity(),view_image.class);
      if(str==null)
        {
            Toast.makeText(getActivity(),"unable to fetch the udp port",Toast.LENGTH_SHORT).show();
        }
        else {
           ii.putExtra("udport", str);
            startActivity(ii);
        }
    }
}

class img
{
    int imageViewId;
    String camName;

    img(int i,String s)
    {
        this.imageViewId=i;
        this.camName=s;
    }
}


class myGridAdapter extends BaseAdapter
{


    ArrayList<img> imgArrayList;
    Context context;
    boolean b;

    myGridAdapter(Context context,boolean abc)
    {
        this.context=context;
        b=abc;
        imgArrayList =new ArrayList<>();

        int images=R.drawable.cam;

        //for(int i=0;i<10;i++)
        //{
            img ii=new img(images,"cam"+1);
            imgArrayList.add(ii);
        //}

    }

    @Override
    public int getCount() {
        return imgArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return imgArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class viewHolder
    {
        ImageView img;
        TextView camname;
        TextView alert;


        viewHolder(View v)
        {
            img=(ImageView)v.findViewById(R.id.camimage);
            camname=(TextView)v.findViewById(R.id.camName);
            alert=(TextView)v.findViewById(R.id.alertIcon);
            //if(b==true) {
              //  alert.setVisibility(View.GONE);
            //}



        }
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View row=view;
        viewHolder holder=null;

        if(row==null)
        {
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
             row=inflater.inflate(R.layout.single_grid_layout,viewGroup,false);
            holder=new viewHolder(row);
            row.setTag(holder);
        }
        else
        {
            holder=(viewHolder)row.getTag();

        }
        img temp=imgArrayList.get(i);
        holder.img.setImageResource(temp.imageViewId);
        holder.camname.setText(temp.camName);
        if(b==false) {
            holder.alert.setVisibility(View.GONE);
        }
        else
        {
            holder.alert.setVisibility(View.VISIBLE);
        }




        return row;
    }
}

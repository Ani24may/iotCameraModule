package com.example.anirudh.hawkeye;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by anirudh on 31/8/17.
 * this is iot-based-camera-module
 */

public class login extends Fragment{

    private Button button;
    private EditText username;
    private EditText password;
    private BroadcastReceiver mNetworkReceiver;


    private String data;
    private Socket s=null;
    private String str=null;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.login,container,false);

        button=(Button)view.findViewById(R.id.save);
        username=(EditText)view.findViewById(R.id.ed1);
        password=(EditText)view.findViewById(R.id.ed2);
        mNetworkReceiver = new MyReciever();
        getActivity().registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        getContext().startService(new Intent(getContext(),MyService.class));


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(username.getText().toString().length()==0| password.getText().toString().length()==0)
                {
                   username.setError("Username is required");
                    password.setError("Password is required");
                }
                else {
                    SharedPreferences ss=getActivity().getSharedPreferences("myData",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=ss.edit();
                    editor.putString("username",username.getText().toString());
                    editor.putString("password",password.getText().toString());
                    editor.commit();
                    //Toast.makeText(getActivity(), "Login sucessfully done", Toast.LENGTH_SHORT).show();

                    new Thread() {

                        public void run()
                        {
                            try {
                                Log.d("con : ","connecting");
                                s=new Socket("192.168.2.5",2223);
                                Log.d("con : ","connected");
                                OutputStreamWriter osw = new OutputStreamWriter (s.getOutputStream());
                                data="/"+username.getText().toString()+"/android/"+password.getText().toString();
                                osw.write(data);
                                osw.flush();
                                Log.d("con : ","username given to server");
                                DataInputStream dis = new DataInputStream(s.getInputStream());
                                byte[] data1 = new byte[5];
                                dis.read(data1);
                                str= new String(data1);
                                str = str.trim();
                                Log.d("port : ",str);
                                s.close();
                            }
                            catch(Exception e)
                            {

                                e.printStackTrace();
                            }
                        }
                    }.start();
                    Intent intent = new Intent(getActivity(), main_page.class);

                    if(str==null)
                    {
                        //Toast.makeText(getActivity(),"you are not register",Toast.LENGTH_SHORT).show();
                    }
                    else {

                        intent.putExtra("myudpport",str);
                        startActivity(intent);
                        getActivity().finish();
                    }
                }

            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }
    protected void unregisterNetworkChanges() {
        try {
            getActivity().unregisterReceiver(mNetworkReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterNetworkChanges();
    }
}

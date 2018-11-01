package com.example.anirudh.hawkeye;

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
 */

public class register extends Fragment {

    private Button button;
    private EditText username;
    private EditText pswd;
    private EditText confirm;
    private Socket s;
    private String data;
    private String str;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view=inflater.inflate(R.layout.register,container,false);

        button=(Button)view.findViewById(R.id.rgstr);
        username=(EditText)view.findViewById(R.id.ed1);
        pswd=(EditText)view.findViewById(R.id.ed2);
        confirm=(EditText)view.findViewById(R.id.ed3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(pswd.getText().toString().equals(confirm.getText().toString())))
                {
                    confirm.setError("password mismatch");
                    Log.d("status",pswd.getText().toString());
                    Log.d("status",confirm.getText().toString());
                }
                else
                {
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
                                data="/"+username.getText().toString()+"/register/"+confirm.getText().toString();
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
                    if(str=="AR")
                    {
                        Toast.makeText(getActivity(),"You are already register", Toast.LENGTH_SHORT).show();
                    }
                    if(str=="NR")
                    {
                        Toast.makeText(getActivity(),"You are not registerd,try after some time", Toast.LENGTH_SHORT).show();
                    }
                    if(str=="SR")
                    {
                        Toast.makeText(getActivity(),"You are Ssucesfully registere,please proceed to LOGIN", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return view;
    }
}

package com.example.anirudh.hawkeye;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketAddress;


public class view_image extends AppCompatActivity {

    private ImageView img;
    private final String tag="tag";
    private byte[] buff;
    private Bitmap bitmap;
    private DatagramSocket sock;
    private int count = 0;
    private int i=0;
    //public fetchvideo ff;
    private String port;
    //private TextView tt;
    private boolean run=true;
    private String fetchport=null;
    private int udpport;
    private boolean activityVisible=true;
    private String stt="end";
    Thread t;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.view_image);
        if (savedInstanceState != null) bitmap = (Bitmap) savedInstanceState.getParcelable("bitmap");
        img = (ImageView) findViewById(R.id.imgview);
       // tt=(TextView)findViewById(R.id.t1);
        Bundle extra=this.getIntent().getExtras();
        if(extra!=null)
        {
            fetchport=extra.getString("udport");
        }
        udpport=Integer.parseInt(fetchport);
        //recvthread rcv=new recvthread(img,sock,udpport);
        //rcv.start();

        t=new Thread(new Runnable() {
            @Override
            public void run() {
                con();
            }
        });
        t.start();


        /*new  Thread() {

            public void run() {


               con();


            }
       }.start();*/

    }

    public void con()
    {
        try

        {
            sock=new DatagramSocket(null);
            SocketAddress socketAddr=new InetSocketAddress(udpport);
            sock.setReuseAddress(true);
            sock.bind(socketAddr);
            //ff= new fetchvideo(sock);
            //ff.execute();
            while(true)
            {
                byte[] receivedata = new byte[60000];
                DatagramPacket recv_packet = new DatagramPacket(receivedata, receivedata.length);
                sock.receive(recv_packet);
                //Log.i(tag, "packet recievd " + count);
                buff = recv_packet.getData();
                bitmap = BitmapFactory.decodeByteArray(buff, 0, buff.length);
                //Log.i(tag, "after decodebytearray");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] b = baos.toByteArray();
                //String temp = Base64.encodeToString(b, Base64.DEFAULT);
                final Bitmap bitmap=BitmapFactory.decodeByteArray(b, 0, b.length);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        img.setImageBitmap(bitmap);
                    }
                });
                //break;
            }


        } catch (
                Exception e)

        {
            Log.e(tag, e.getMessage());
        }

    }









    /*public class fetchvideo extends AsyncTask<Void,String,Void>
    {



        DatagramSocket sock;
        public fetchvideo(DatagramSocket sock)
        {
            this.sock=sock;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                //Log.i(tag, "doInBackground start");

                while (true)

                {
                    byte[] receivedata = new byte[60000];

                    DatagramPacket recv_packet = new DatagramPacket(receivedata, receivedata.length);
                    sock.receive(recv_packet);
                    //Log.i(tag, "packet recievd " + count);
                    buff = recv_packet.getData();
                    bitmap = BitmapFactory.decodeByteArray(buff, 0, buff.length);
                    //Log.i(tag, "after decodebytearray");

                    ByteArrayOutputStream baos=new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
                    byte [] b=baos.toByteArray();
                    String temp= Base64.encodeToString(b, Base64.DEFAULT);

                    publishProgress(temp);

                    //count++;
                }
            }
            catch(Exception ex)
            {
                Log.e(tag,"caught in catch");
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            byte [] encodeByte=Base64.decode(values[0],Base64.DEFAULT);
            final Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            if(activityVisible==true) {
                img.setImageBitmap(bitmap);
            }
            else
            {

            }

            //tv.setText("showing text view " +i);
            i++;
            //Log.d(tag,"onProgressUpdate");
        }

        @Override
        protected void onCancelled(Void aVoid) {
            super.onCancelled(aVoid);
        }



        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }*/


    @Override
    public void onStart()
    {
        super.onStart();
        //new fetchvideo().execute();
        Log.d(tag,"onStart called");

    }

    @Override
    public void onPause()
    {
        super.onPause();
        //activityVisible=false;
        Log.d(tag,"onPause called");

    }

    @Override
    public void onResume()
    {
        super.onResume();
        //con();

        //img.setImageBitmap(bitmap);
        //new fetchvideo().execute();
        //ff.execute();

        Log.d(tag,"onResume called");
    }

    @Override
    public void onStop()
    {
        super.onStop();

        Log.d(tag,"onStop called");
    }

    @Override
    public void onDestroy()
    {

        //run=false;
        //if(t.isAlive()==true)
        //{
          //  run=false;
        //}
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] b=stt.getBytes();
                try {
                    DatagramPacket dp = new DatagramPacket(b, b.length, InetAddress.getByName("192.168.2.3"), udpport);
                    sock.send(dp);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                //sock.close();
            }
        }).start();*/


        //Intent ii=new Intent(this,main_page.class);
        //finish();
        //startActivity(ii);

        Log.d(tag,"onDestroy called");
        super.onDestroy();
    }

    public void onSaveInstanceState(Bundle saveInstanceState)
    {
        super.onSaveInstanceState(saveInstanceState);
        saveInstanceState.putParcelable("bitmap",bitmap);
    }

}

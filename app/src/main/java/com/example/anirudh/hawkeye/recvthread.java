package com.example.anirudh.hawkeye;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * Created by anirudh on 14/10/17.
 */

public class recvthread extends Thread {
    ImageView img;
    DatagramSocket sock=null;
    int port;
    byte[] buff;
    Bitmap bitmap;
    final String tag="tag";

    recvthread(ImageView ii,DatagramSocket s,int p)
    {
        img=ii;
        sock=s;
        port=p;

    }

    public void run()
    {
        try

        {
            //sock = new DatagramSocket(2223);//kam korise dnt change
            sock=new DatagramSocket(null);
            SocketAddress socketAddr=new InetSocketAddress(port);
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

                //runOnUiThread(new Runnable() {
                   // @Override
                    //public void run() {
                        img.setImageBitmap(bitmap);
                    //}
                //});
            }

        } catch (
                Exception e)

        {
            Log.e(tag, e.getMessage());
        }

    }

}

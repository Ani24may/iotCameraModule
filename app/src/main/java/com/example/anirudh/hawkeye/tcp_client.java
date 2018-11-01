package com.example.anirudh.hawkeye;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by anirudh on 6/9/17.
 */

public class tcp_client implements Runnable {

    private String data;
    private Socket s;
    private String ip="192.168.2.3";
    public  tcp_client(String str) throws  Exception
    {
        data=str;
        s=new Socket(ip,2223);
    }



    @Override
    public void run() {

    }
}

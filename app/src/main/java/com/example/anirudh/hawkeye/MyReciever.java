package com.example.anirudh.hawkeye;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by anirudh on 21/10/17.
 */

public class MyReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try
        {
            if (isOnline(context)) {
                //dialog(true);

                context.startService(new Intent(context,MyService.class));
                Toast.makeText(context,"connected to internet",Toast.LENGTH_SHORT).show();
                Log.d("anirudh: ", "Online Connect Intenet ");

            } else {
                //dialog(false);
                context.stopService(new Intent(context,MyService.class));
                //mm.undo();
                Toast.makeText(context,"fialed connected to internet",Toast.LENGTH_SHORT).show();
                Log.d("anirudh: ", "Conectivity Failure !!! ");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }


    private boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }
}

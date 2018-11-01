package com.example.anirudh.hawkeye;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class tab_activity extends FragmentActivity {

    public ViewPager viewPager;
    public boolean isFirst;
    private static final String def1="N/A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_activity);
        if (getIntent().getExtras() != null) {
            Bundle b = getIntent().getExtras();
            boolean cameFromNotification = b.getBoolean("check");
            if (cameFromNotification==true)
            {
                Toast.makeText(this,"this has been came from notification",Toast.LENGTH_SHORT).show();
            }
        }

        viewPager = (ViewPager) findViewById(R.id.pager);


                FragmentManager fm = getSupportFragmentManager();
                viewPager.setAdapter(new myAdapter(fm));

    }
}

class myAdapter extends FragmentStatePagerAdapter
{

    public myAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;

        if(position==0)
        {
            fragment=new login();
        }
        if(position==1)
        {
            fragment= new register();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {


        if(position==0)
        {
            return "LOGIN";
        }
        if(position==1)
        {
            return "REGISTER";
        }

        return null;
    }
}

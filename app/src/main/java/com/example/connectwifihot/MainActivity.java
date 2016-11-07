package com.example.connectwifihot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

    private static Wifiadmin mWifiadmin;
    private String SSID ="";
    private static String PWD = "wen911021";
    private TextView textView;
    private int wifiIndex;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent startIntent=new Intent(this,MyService.class);
        //textView=(TextView)findViewById(R.id.text);
       // String bs=mWifiadmin.getBSSID();
        //textView.setText(bs);
        startService(startIntent);

    }
    public void onDestroy(){
        super.onDestroy();
        //mWifiadmin.closeWifi();
        SSID= mWifiadmin.getSSID();
       mWifiadmin.cancelConfig(SSID);
    }
    }


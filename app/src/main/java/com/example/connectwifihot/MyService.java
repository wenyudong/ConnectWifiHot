package com.example.connectwifihot;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

public class MyService extends Service{

    @Nullable
    private static Wifiadmin mWifiadmin;
    private String SSID ="";
    private static String PWD = "wen911021";
    private TextView textView;
    private int wifiIndex;
    private Context context;
    private BroadReceiverMonitor broadReceiverMonitor;

    public IBinder onBind(Intent intent) {
        return null;
    }
    public void onCreate(){
        super.onCreate();
        //broadReceiverMonitor=new BroadReceiverMonitor();
        //broadReceiverMonitor.BindBroadcastMethod();
        IntentFilter filter=new IntentFilter( WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(mReceiver,filter);
    }
    private BroadcastReceiver mReceiver = new BroadcastReceiver (){
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if(wifiInfo.isConnected()){
                WifiManager wifiManager = (WifiManager) context
                        .getSystemService(Context.WIFI_SERVICE);
                String wifiSSID = wifiManager.getConnectionInfo()
                        .getSSID();
                Toast.makeText(context, wifiSSID+"连接成功", Toast.LENGTH_SHORT).show();
            }
        }

    };

    public int onStartCommand(Intent intent,int flags,int startId){
        mWifiadmin = new Wifiadmin(this);
        mWifiadmin.openWifi();
       // SSID= mWifiadmin.getSSID();
        SSID=mWifiadmin.getBSSIDScanResult();
        mWifiadmin.addNetwork(mWifiadmin.CreateWifiInfo(SSID,PWD,3));
       // mWifiadmin.isWifiConnected(context);
        return super.onStartCommand(intent,flags,startId);
    }
    public void onDestroy(){
        super.onDestroy();
        mWifiadmin.removeConfig(mWifiadmin.CreateWifiInfo(SSID,PWD,3));
    }
}

package com.example.connectwifihot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import android.widget.Toast;

/**
 * Created by dong on 2016/9/9.
 */
public class BroadReceiverMonitor {
    public void BindBroadcastMethod() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);//wifi是否打开监听与是否连接上wifi
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);//连接网络改变监听
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);//连接监听
        registerReceiver(WifiSetMethod, filter);//注册广播监听
    }

    private void registerReceiver(BroadcastReceiver wifiSetMethod, IntentFilter filter) {
    }


    /**
     * 接收到广播信号响应时间的方法
     */
    private BroadcastReceiver WifiSetMethod = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
                int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
                switch (wifiState) {
                    case WifiManager.WIFI_STATE_DISABLED:
                        Toast.makeText(context, "wifi已经关闭", Toast.LENGTH_SHORT).show();
                        break;
                    case WifiManager.WIFI_STATE_DISABLING:
                        Toast.makeText(context, "wifi正在关闭", Toast.LENGTH_SHORT).show();
                        break;
                    case WifiManager.WIFI_STATE_ENABLED:
                        Toast.makeText(context, "wifi已经打开", Toast.LENGTH_SHORT).show();
                        break;
                    case WifiManager.WIFI_STATE_ENABLING:
                        Toast.makeText(context, "wifi正在打开", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            } else if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
                Parcelable wifiNetState = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if (wifiNetState != null) {
                    Toast.makeText(context, "有可用的无线网络", Toast.LENGTH_SHORT).show();
                }
            } else if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                NetworkInfo info = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
                if (info != null) {
                    Toast.makeText(context,
                            "已经连接上网络SubtypeName:" + info.getSubtypeName()
                                    + "已经连接上网络Type:" + info.getType()
                                    + "已经连接上网络State:" + info.getState()
                                    + "已经连接上网络info:" + info.getExtraInfo()
                            , Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "网络连接已经断开", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };


}

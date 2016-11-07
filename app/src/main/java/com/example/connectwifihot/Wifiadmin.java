package com.example.connectwifihot;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by dong on 2016/8/19.
 */
public class Wifiadmin {
    private final static String TAG = "Wifiadmin";
    // 定义WifiManager对象
    private static WifiManager mWifiManager;
    // 定义WifiInfo对象
    private WifiInfo mWifiInfo;
    // 扫描出的网络连接列表
    private WifiInfo info;
    private  List<ScanResult> mWifiList;
    // 网络连接列表
    private static List<WifiConfiguration> mWifiConfiguration;
    // 定义一个WifiLock,能够阻止wifi进入睡眠状态，使wifi一直处于活跃状态
    WifiManager.WifiLock mWifiLock;
    private StringBuilder stringBuilder = new StringBuilder();
    private int paramInt;
    public static String[] str;
    private static ProgressDialog progressDialog;
    private static Context context;
    private String SSID;
    private WifiConfiguration mConfig;
    private List<String> Hotpot;
    private List<ScanResult> listResult;
    private ScanResult mScanResult;
    private StringBuffer mStringBuffer = new StringBuffer();
    public static final int WIFI_STATE_DISABLING = 0;
    public static final int WIFI_STATE_DISABLED = 1;
    public static final int WIFI_STATE_ENABLING = 2;
    public static final int WIFI_STATE_ENABLED = 3;
    public static final int WIFI_STATE_FAILED = 4;

    // 构造器
    public Wifiadmin(Context context) {
        // 取得WifiManager对象
        mWifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        // 取得WifiInfo对象
        mWifiInfo = mWifiManager.getConnectionInfo();
    }

    /**
     * 是否存在网络信息
     *
     * @return
     */

    // 打开WIFI
    public void openWifi() {
        if (!mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(true);
        }
    }

    // 关闭WIFI
    public void closeWifi() {
        if (mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(false);
        }
    }

    // 检查当前WIFI状态
    public void checkState() {

        if (mWifiManager.getWifiState() == 0) {
           Toast.makeText(context, "wifi正在关闭",Toast.LENGTH_SHORT).show();
        } else if (mWifiManager.getWifiState() == 1) {
            Toast.makeText(context, "wifi已经关闭",Toast.LENGTH_SHORT).show();
        } else if (mWifiManager.getWifiState() == 2) {
            Toast.makeText(context, "wifi正在打开",Toast.LENGTH_SHORT).show();
        } else if (mWifiManager.getWifiState() == 3) {
            Toast.makeText(context, "wifi已经打开",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "wifi没有获得状态",Toast.LENGTH_SHORT).show();;
        }

    }

 public String BSSID(){
     WifiInfo info=mWifiManager.getConnectionInfo();
     String BSSID=null;
     if(info.getBSSID()!=null){
         BSSID=info.getBSSID();
     }
    return BSSID;
 }
public int connectwifi(){
    WifiInfo info = mWifiManager.getConnectionInfo();
    if (info.getBSSID() != null) {
        int strength = WifiManager.calculateSignalLevel(info.getRssi(), 5);

			int speed = info.getLinkSpeed();
		    String units = WifiInfo.LINK_SPEED_UNITS;
			String ssid = info.getSSID();
           return strength;

    }
    return 0;
}

    // 锁定WifiLock
    public void acquireWifiLock() {
        mWifiLock.acquire();
    }

    // 解锁WifiLock
    public void releaseWifiLock() {
        // 判断时候锁定
        if (mWifiLock.isHeld()) {
            mWifiLock.acquire();
        }
    }

    // 创建一个WifiLock
    public void creatWifiLock() {
        mWifiLock = mWifiManager.createWifiLock("");
    }

    // 得到配置好的网络
    public List<WifiConfiguration> getConfiguration() {
        return mWifiConfiguration;
    }

    // 指定配置好的网络进行连接
    public void connectConfiguration(int index) {
        // 索引大于配置好的网络索引返回
        if (index >= mWifiConfiguration.size()) {
            return;
        }
        // 连接配置好的指定ID的网络
        mWifiManager.enableNetwork(mWifiConfiguration.get(index).networkId,
                true);
    }


    public  static String getSSID() {

        Date date=new Date();
        String a=String.format("%te", date);

        String b=String.format("%tY", date);

        String c=String.format("%tj", date);
        String d=String.format("%tm", date);

        String e=String.format("%td", date);
        String f=String.format("%ty", date);
        String h=String.format("%tH", date);

        String ssid;
        return ssid=a+b+c+d+e+f+h;
    }

    public void startScan(Context context) {
        mWifiManager.startScan();
        // 得到扫描结果
        mWifiList = mWifiManager.getScanResults();
        // 得到配置好的网络连接
        mWifiConfiguration = mWifiManager.getConfiguredNetworks();
        if (mWifiList == null) {
            if(mWifiManager.getWifiState()==3){
                Toast.makeText(context,"当前区域没有无线网络", Toast.LENGTH_SHORT).show();
            }else if(mWifiManager.getWifiState()==2){
                Toast.makeText(context,"WiFi正在开启，请稍后重新点击扫描", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context,"WiFi没有开启，无法扫描", Toast.LENGTH_SHORT).show();
            }
        }

    }

    // 得到网络列表
    public List<ScanResult> getWifiList() {
        return mWifiList;
    }

    // 查看扫描结果
    public StringBuilder lookUpScan() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mWifiList.size(); i++) {
            stringBuilder
                    .append("Index_" + new Integer(i + 1).toString() + ":");
            // 将ScanResult信息转换成一个字符串包
            // 其中把包括：BSSID、SSID、capabilities、frequency、level
            stringBuilder.append((mWifiList.get(i)).toString());
            stringBuilder.append("/n");
        }
        return stringBuilder;
    }
    public void scan() {
        mWifiManager.startScan();
        listResult = mWifiManager.getScanResults();
        if (listResult != null) {
            Log.i(TAG, "当前区域存在无线网络，请查看扫描结果");
        } else {
            Log.i(TAG, "当前区域没有无线网络");
        }
    }

    public String getBSSIDScanResult() {

        String ssid=null;
        // 开始扫描网络
        scan();
        listResult = mWifiManager.getScanResults();
        if (listResult != null) {
            for (int i = 0; i < listResult.size(); i++) {
                mScanResult = listResult.get(i);
                if(mScanResult.BSSID.equals("20:5d:47:79:62:c0")){
                     ssid=listResult.get(i).SSID;
                }else{
                    ssid=null;
                }

            }
        }
        Log.i(TAG, ssid);
        return ssid;
    }



 public static void isWifiConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取状态
        NetworkInfo.State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState();
        //判断wifi已连接的条件
        if (wifi == NetworkInfo.State.CONNECTED) {
            Toast.makeText(context, "连接到热点", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(context, "未连接到热点", Toast.LENGTH_SHORT).show();
        }
    }
     public Boolean isContainSSID() {
         mWifiManager.startScan();
         mWifiList = mWifiManager.getScanResults();
         for (ScanResult result : mWifiList) {
             if((result.SSID).matches("\\p{Digit}"));
         }
         return true;
     }
        // 添加一个网络并连接
        public void addNetwork(final WifiConfiguration wcg) {
            int wcgID = 0;
            if (isContainSSID() == true) {
                wcgID = mWifiManager.addNetwork(wcg);
                mWifiManager.enableNetwork(wcgID, true);
            }
            final boolean b = mWifiManager.enableNetwork(wcgID, true);
            MyTimerCheck timerCheck = new MyTimerCheck() {

                public void doTimerCheckWork() {
                   // int wcgID;
                    if (!b) {
                        addNetwork(wcg);
                    }
                }

                public void doTimeOutWork() {


                    this.exit();

                }

            };
            timerCheck.start(1000, 1000);

        }


        // 得到MAC地址
        public String getMacAddress() {
            return (mWifiInfo == null) ? "NULL" : mWifiInfo.getMacAddress();
        }

        // 得到接入点的BSSID
        public String getBSSID() {
            return (mWifiInfo == null) ? "NULL" : mWifiInfo.getBSSID();
        }

        // 得到IP地址
        public Serializable getIPAddress() {
            return (mWifiInfo == null) ? 0 : intToIp(mWifiInfo.getIpAddress());
        }
    public String getInfo()
    {
        WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        info = wifi.getConnectionInfo();
        String maxText = info.getMacAddress();
        String ipText = intToIp(info.getIpAddress());
        String status = "";
        if (wifi.getWifiState() == WifiManager.WIFI_STATE_ENABLED)
        {
            status = "WIFI_STATE_ENABLED";
        }
        String ssid = info.getSSID();
        int networkID = info.getNetworkId();
        int speed = info.getLinkSpeed();
        return "mac：" + maxText + "\n\r"
                + "ip：" + ipText + "\n\r"
                + "wifi status :" + status + "\n\r"
                + "ssid :" + ssid + "\n\r"
                + "net work id :" + networkID + "\n\r"
                + "connection speed:" + speed + "\n\r"
                ;
    }
    public  String intToIp(int i) {
            return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
                    + "." + ((i >> 24) & 0xFF);
        }
//   清除连接网络的配置信息
     public void removeConfig(WifiConfiguration wcg){
         int wcgID=mWifiManager.addNetwork(wcg);
         disconnectWifi(wcgID);
         mWifiManager.removeNetwork(wcgID);
     }
    // 断开指定ID的网络
        public void disconnectWifi(int netId) {
            mWifiManager.disableNetwork(netId);
            mWifiManager.disconnect();
            info = null;
        }
        public void cancelConfig(String SSID){
            List<WifiConfiguration> existingConfigs = mWifiManager.getConfiguredNetworks();
            for (WifiConfiguration existingConfig : existingConfigs) {
                if (existingConfig.SSID.matches("\\p{Digit}")||existingConfig!=null) {
                   mWifiManager.removeNetwork(existingConfig.networkId);
                }
            }
        }
        public WifiConfiguration CreateWifiInfo(String SSID, String Password, int Type) {
            WifiConfiguration config = new WifiConfiguration();
            config.allowedAuthAlgorithms.clear();
            config.allowedGroupCiphers.clear();
            config.allowedKeyManagement.clear();
            config.allowedPairwiseCiphers.clear();
            config.allowedProtocols.clear();
            config.SSID = "\"" + SSID + "\"";
            WifiConfiguration tempConfig = this.IsExsits(SSID);
            if (tempConfig != null) {
                mWifiManager.removeNetwork(tempConfig.networkId);
            }

            if (Type == 1) //WIFICIPHER_NOPASS
            {
                config.wepKeys[0] = "";
                config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                config.wepTxKeyIndex = 0;
            }
            if (Type == 2) //WIFICIPHER_WEP
            {
                config.hiddenSSID = true;
                config.wepKeys[0] = "\"" + Password + "\"";
                config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
                config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
                config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
                config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
                config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                config.wepTxKeyIndex = 0;
            }
            if (Type == 3) //WIFICIPHER_WPA
            {
                config.preSharedKey = "\"" + "wen911021" + "\"";
                config.hiddenSSID = true;
                config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
                config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
                config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
                config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
                //config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
                config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
                config.status = WifiConfiguration.Status.ENABLED;
            }

            return config;
        }


        private WifiConfiguration IsExsits(String ssid) {
            List<WifiConfiguration> existingConfigs = mWifiManager.getConfiguredNetworks();
            for (WifiConfiguration existingConfig : existingConfigs) {
                if (existingConfig.SSID.equals("\"" + ssid + "\"")) {
                    return existingConfig;
                }
            }
            return null;
        }

    }

package com.example.practice14;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    WifiManager wifiManager;
    WifiReceiver wifiReceiver;
    List<WifiInfo> wifiInfos;
    ListView wifiListView;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermissions(MainActivity.this, this);
        wifiListView = (ListView) findViewById(R.id.WifiListView);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            // If wifi disabled then enable it.
            Toast.makeText(this, "wifi is disabled... \nPlease making it enabled", Toast.LENGTH_LONG).show();
            wifiManager.setWifiEnabled(true);
        }
        wifiReceiver = new WifiReceiver();
        IntentFilter intentFilter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        intentFilter.addAction(WifiManager.RSSI_CHANGED_ACTION);
        registerReceiver(wifiReceiver, intentFilter);

        wifiManager.startScan();
        wifiInfos = new ArrayList<>();

        customAdapter = new CustomAdapter(wifiInfos, getApplicationContext());
        wifiListView.setAdapter(customAdapter);
    }

    public static void checkPermissions(Activity activity, Context context) {
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                Manifest.permission.ACCESS_FINE_LOCATION
        };
        if (!hasPermissions(context, PERMISSIONS)) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS, PERMISSION_ALL);
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    class WifiReceiver extends BroadcastReceiver {
        public void onReceive(Context c, Intent intent) {
            int state = wifiManager.getWifiState();
            int maxLevel = 5;
            if (state == WifiManager.WIFI_STATE_ENABLED) {
                wifiInfos.clear();
                for (ScanResult result : wifiManager.getScanResults()) {
                    // The level of each wifiNetwork from 0-5.
                    // calculateSignalLevel is deprecated. Callers should use calculateSignalLevel(int) instead.
                    int level = WifiManager.calculateSignalLevel(result.level, maxLevel);
                    WifiInfo wifiInfo = new WifiInfo();
                    wifiInfo.setSSID(result.SSID);
                    wifiInfo.setRSSI(result.level);
                    wifiInfo.setLevel(level);
                    wifiInfo.setBSSID(result.BSSID);
                    wifiInfos.add(wifiInfo);
                }
                customAdapter.notifyDataSetChanged();
            }
        }
    }
}

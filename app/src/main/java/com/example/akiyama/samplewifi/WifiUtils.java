package com.example.akiyama.samplewifi;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Akiyama on 2017/07/22.
 */

public class WifiUtils {

    private Context mContext;
    private WifiManager mWifiManager;

    public WifiUtils(Context context) {
        mContext = context;
        // WifiManagerのインスタンスを取得
        mWifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
    }

    public void start() {
        scanStart();
    }

    public List<ScanResult> get() {

        // Scan 結果取得
        return getWifiList();
    }

    private void showWifiStatusError() {
        Toast.makeText(mContext, "Wifi Scanが有効ではありません", Toast.LENGTH_SHORT).show();
    }

    private void scanStart() {
        // スキャン開始
        mWifiManager.startScan();
    }

    private void scanStop() {
        // スキャン停止 ではなく特定の WiFi
        mWifiManager.disconnect();
    }

    private List<ScanResult> getWifiList() {
        // 結果を取得し返す
        return mWifiManager.getScanResults();
    }

    private boolean checkWifiStatus() {
        return mWifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED;
    }

    // 現在のWiFiを表示


}

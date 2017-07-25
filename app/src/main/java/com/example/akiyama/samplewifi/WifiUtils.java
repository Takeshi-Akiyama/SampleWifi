package com.example.akiyama.samplewifi;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
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

    /**
     * WiFiスキャンを実施
     */
    public void startScan() {
        mWifiManager.startScan();
    }


    /**
     * WiFi接続先リストを取得
     * @return List<ScanResult>
     */
    public List<ScanResult> getWifiList() {

        // Scan 結果取得
        return mWifiManager.getScanResults();
    }

    /**
     * 現在接続しているWifiの情報を取得
     *
     * @return WifiInfo
     */
    public WifiInfo getCurrentConnection() {
        return mWifiManager.getConnectionInfo();
    }

    /**
     * WiFiが有効でないことをToast表示
     */
    public void showWifiError() {
        Toast.makeText(mContext, "Wifiが有効ではありません", Toast.LENGTH_SHORT).show();
    }


    private boolean checkWifiStatus() {
        return mWifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED;
    }


}

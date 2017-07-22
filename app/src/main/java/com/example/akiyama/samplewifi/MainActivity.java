package com.example.akiyama.samplewifi;

import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // 許可されたパーミッションの種類を識別する番号
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;

    // 位置情報を管理するクラス
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // RunTime Permissionチェック
        checkRuntimePermission();

        // WifiManagerのインスタンスを取得
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);

        // WiFiステータスが有効であること
        if (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {

            // スキャン開始
            wifiManager.startScan();

            // スキャン結果をリストに取得
            List<ScanResult> scanResultList = wifiManager.getScanResults();

            // WifiArrayAdappterにスキャン結果を設定
            WifiArrayAdapter adapter = new WifiArrayAdapter(this, R.layout.item_wifistatus, scanResultList);

            // ListViewにWifiArrayAdapterを設定
            ListView listView = (ListView) findViewById(R.id.listview);
            listView.setAdapter(adapter);
        }
    }

    /**
     * WiFiを利用するためのRuntimePermissionをチェック
     */
    private void checkRuntimePermission() {

        // Android 6.0以上の場合
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            // 位置情報の取得が許可されているかチェック
            if (checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // 権限があればその旨Toast表示
                Toast.makeText(this, "許可されてます", Toast.LENGTH_SHORT).show();
            } else {
                // なければ権限を求めるダイアログを表示
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // ACCESS_COARSE_LOCATIONの権限を求めるコード
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "許可されました", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "拒否されました", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

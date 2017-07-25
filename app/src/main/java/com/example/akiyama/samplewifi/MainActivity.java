package com.example.akiyama.samplewifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // 許可されたパーミッションの種類を識別する番号
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;

    private WifiUtils mWifiUtils;

    // 位置情報を管理するクラス
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // RunTime Permissionチェック
        checkRuntimePermission();

        // WiFI utils 呼び出し
        mWifiUtils = new WifiUtils(this);

        // Intent Filterには独自定義したタグを使用
        // android.net.wifi.WIFI_STATE_CHANGED のは場合に他のアプリやOS起因によるScanが行われた場合に
        // registerReceiverが起動しボタンを押していないのに結果を取得していた
        registerReceiver(mScanResultsReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        // Start Button
        Button btnStart = (Button) findViewById(R.id.btn_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mWifiUtils.start();
            }
        });
    }

    private void getWifiList() {

        List<ScanResult> scanResults = mWifiUtils.get();

        // WifiArrayAdappterにスキャン結果を設定
        WifiArrayAdapter adapter = new WifiArrayAdapter(getApplicationContext(), R.layout.item_wifistatus,
                scanResults);

        // ListViewにWifiArrayAdapterを設定
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);

    }


    /**
     * WiFiを利用するためのRuntimePermissionをチェック
     */
    private void checkRuntimePermission() {

        // Android 6.0以上の場合
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            // 位置情報の取得が許可されているかチェック
            if (checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                // 権限があればその旨Toast表示
                Toast.makeText(this, "許可されてます", Toast.LENGTH_SHORT).show();
            } else {
                // なければ権限を求めるダイアログを表示
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
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

    private final BroadcastReceiver mScanResultsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            System.out.println("onReceiver呼び出し getAction:"
                    + intent.getAction()
                    + " getPackage:"
                    + intent.getPackage());

            getWifiList();

            // onReceive() は何度も呼ばれるので、
            // 1度で終了させたい場合はここで unregister する
            try {
                unregisterReceiver(this);
            } catch (IllegalArgumentException e) {
                // 既に登録解除されている場合
                // 事前に知るための API は用意されていない
            }
        }
    };
}



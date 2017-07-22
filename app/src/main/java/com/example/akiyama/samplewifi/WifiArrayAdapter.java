package com.example.akiyama.samplewifi;

import android.content.Context;
import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;

import java.util.List;

/**
 * Created by Akiyama on 2017/07/22.
 */

public class WifiArrayAdapter extends ArrayAdapter {

    /**
     * コンテキスト
     */
    private Context mContext;
    /**
     * リソース
     */
    private int mResource;
    /**
     * リスト(ScanResultモデル)
     */
    private List<ScanResult> mScanResultList;
    /**
     * インフレーター
     */
    private LayoutInflater mInflater;

    /**
     * コンストラクタ
     *
     * @param context  コンテキスト
     * @param resource リソース
     * @param list     リスト(ScanResultモデル)
     */
    public WifiArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ScanResult> list) {
        super(context, resource, list);
        mContext = context;
        mResource = resource;
        mScanResultList = list;

        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view;

        // convertViewに指定があった場合
        if (convertView != null) {
            view = convertView;

            // contenViewに指定がなければインフレターを使用
        } else {
            view = mInflater.inflate(mResource, null);
        }

        // ArrayListからオブジェクトを取得
        ScanResult scanResult = mScanResultList.get(position);

        // ウィジェットを設定
        TextView tvSsid = (TextView) view.findViewById(R.id.txt_ssid);
        TextView tvBssid = (TextView) view.findViewById(R.id.txt_bssid);
        TextView tvCapabilities = (TextView) view.findViewById(R.id.txt_capabilities);
        TextView tvFrequency = (TextView) view.findViewById(R.id.txt_frequency);
        TextView tvLevel = (TextView) view.findViewById(R.id.txt_level);

        // テキストを設定
        tvSsid.setText(scanResult.SSID);
        tvBssid.setText(scanResult.BSSID);
        tvCapabilities.setText(scanResult.capabilities);
        tvFrequency.setText(String.format("%d", scanResult.frequency));
        tvLevel.setText(Integer.toString(scanResult.level));

        // 色を設定
        tvSsid.setTextColor(Color.BLUE);
        tvBssid.setTextColor(Color.BLACK);
        tvCapabilities.setTextColor(Color.BLACK);
        tvFrequency.setTextColor(Color.BLACK);
        tvLevel.setTextColor(Color.BLACK);

        return view;
    }
}

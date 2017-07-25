package com.example.akiyama.samplewifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by takeshi-a on 2017/07/25.
 */

public class WifiBroadcastReceiver extends BroadcastReceiver {

    private MainActivity mMainActivity;

    public WifiBroadcastReceiver(MainActivity mainActivity) {
        mMainActivity = mainActivity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        System.out.println("onReceiver呼び出し getAction:"
                + intent.getAction()
                + " getPackage:"
                + intent.getPackage());

        mMainActivity.getWifiList();

        // onReceive() は何度も呼ばれるので、
        // 1度で終了させたい場合はここで unregister する
        try {
            context.unregisterReceiver(this);
        } catch (IllegalArgumentException e) {
            // 既に登録解除されている場合
            // 事前に知るための API は用意されていない
        }

    }
}

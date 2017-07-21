package com.example.administrator.intent_is_ok;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import static android.os.Build.VERSION_CODES.N;

/**
 * 掌握网络是否连接以及网络类型的判断,掌握无网络情况下，跳转设置网络设置界面
 * 1.自定义广播类
 * 2.完成注册
 * 3.编写网络判断的工具类
 * 4.使用网络判断的工具类,判断当前用户手机的网络情况
 * 5.无网络时，跳转网络设置界面
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//注册广播
        MyRecever recever = new MyRecever();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.bwei.net");
        registerReceiver(recever, filter);

        //使用自己编写的工具类，判断网络是否连接成功
        boolean available = NetWorkUtils.isNetWorkAvailable(MainActivity.this);
        if (available) {
            Toast.makeText(MainActivity.this, "网络连接成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
        }
        //判断是否是wifi
        boolean wifi = NetWorkUtils.isWifi(MainActivity.this);
        if (wifi) {
            Toast.makeText(MainActivity.this, "WIFI连接成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "WIFI连接失败", Toast.LENGTH_SHORT).show();
        }
        //判断是否是手机流量
        boolean mobile = NetWorkUtils.isMobile(MainActivity.this);
        if (mobile) {
            Toast.makeText(MainActivity.this, "手机网络连接成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "请检查是否连接网络", Toast.LENGTH_SHORT).show();
            //无网络时，跳转网络设置界面
            Intent intent = new Intent("com.bwei.net");
            intent.putExtra("net", "请连接网络");
            sendBroadcast(intent);
            Intent it = new Intent("android.settings.WIFI_SETTINGS");
            startActivity(it);

        }


    }

    private class MyRecever extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.bwei.net")) {
                String net = intent.getStringExtra("net");
                Toast.makeText(MainActivity.this, "接收广播成功" + net, Toast.LENGTH_SHORT).show();
            }
        }
    }

}

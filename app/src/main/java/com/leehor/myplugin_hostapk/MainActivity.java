package com.leehor.myplugin_hostapk;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.leehor.myplugincore.PluginManager;
import com.leehor.myplugincore.ProxyActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button loadPlugin = findViewById(R.id.loadPlugin);
        Button jumpPlugin = findViewById(R.id.jumpPlugin);

        loadPlugin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadPath();
            }
        });
        jumpPlugin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpActivity();
            }
        });
    }

    private void LoadPath() {
        //动态运行时权限
        //判断是否获取了权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
            // 如果用户在过去拒绝了权限请求，并在权限请求系统对话框中选择了 Don't ask again 选项，此方法将返回 false。
            // 如果设备规范禁止应用具有该权限，此方法也会返回 false。
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }
        } else {
            PluginManager.getInstance().setContext(MainActivity.this);
            PluginManager.getInstance().LoadPath(Environment.getExternalStorageDirectory().getAbsolutePath() + "/myplugin_apk-debug.apk");
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PluginManager.getInstance().setContext(MainActivity.this);
        PluginManager.getInstance().LoadPath(Environment.getExternalStorageDirectory().getAbsolutePath() + "/myplugin_apk-debug.apk");
        // String leehor = getExternalFilesDir("leehor").getAbsolutePath();
        //Log.d("路径",leehor);
        // PluginManager.getInstance().LoadPath( leehor+ "/myplugin_apk-debug.apk");

    }

    private void jumpActivity() {
        //不管跳转到那个插件Apk，都要先跳转到代理页面
        Intent intent = new Intent().setClass(this, ProxyActivity.class);
        //拿到第三方插件Apk的清单文件上的activity（我们只需要拿到启动页activity）
        String apkMainActivity = PluginManager.getInstance().getPackageInfo().activities[0].name;
        intent.putExtra("className", apkMainActivity);
        startActivity(intent);
    }
}

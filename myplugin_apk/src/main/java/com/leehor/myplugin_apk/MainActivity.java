package com.leehor.myplugin_apk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.leehor.myplugincore.BasePluginActivity;


public class MainActivity extends BasePluginActivity {
    TextView pluginApk_tv1;
    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);
        pluginApk_tv1=that.findViewById(R.id.pluginApk_tv1);
        pluginApk_tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(that,TwoActivity.class);
                startActivity(intent);
            }
        });
    }
}

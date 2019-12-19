package com.leehor.myplugin_apk;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.leehor.myplugincore.BasePluginActivity;

public class TwoActivity extends BasePluginActivity {

    private TextView tv2;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_two);
        tv2 = that.findViewById(R.id.plugin_tv2);
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // onBackPressed();
                Intent intent=new Intent(that,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Log.d("插件","退出");
        super.onBackPressed();
        that.finish();
    }
}

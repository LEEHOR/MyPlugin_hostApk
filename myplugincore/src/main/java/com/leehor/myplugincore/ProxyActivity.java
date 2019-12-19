package com.leehor.myplugincore;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * author : Leehor
 * date   : 2019/12/1911:28
 * version: 1.0
 * desc   :代理Activity
 */
public class ProxyActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String className = getIntent().getStringExtra("className");
        try {
            //apk中的activity
            Class<?> aClass = PluginManager.getInstance().getDexClassLoader().loadClass(className);
            //activity的对象
            Object newInstance = aClass.newInstance();
            //是否符合我们定制的标准
            if (newInstance instanceof PluginInterface){
                    PluginInterface pluginInterface= (PluginInterface) newInstance;
                    //将提身activity的上下文传入到第三方activity
                    pluginInterface.attach(this);
                    Bundle bundle=new Bundle();
                    pluginInterface.onCreate(bundle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Resources getResources() {
        return PluginManager.getInstance().getResources();
    }

    @Override
    public ClassLoader getClassLoader() {
        return PluginManager.getInstance().getDexClassLoader();
    }

    /**
     * 不管条状到第三方插件apk那个窗体中，都要先跳转到代理activity
     * @param intent
     */
    @Override
    public void startActivity(Intent intent) {
        String className = intent.getStringExtra("className");
        Intent intent1=new Intent(this,ProxyActivity.class);
        intent1.putExtra("className",className);
        super.startActivity(intent1);
    }
}

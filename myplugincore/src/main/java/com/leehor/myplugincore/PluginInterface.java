package com.leehor.myplugincore;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

/**
 * author : Leehor
 * date   : 2019/12/1911:44
 * version: 1.0
 * desc   :所有外置Apk的页面必须实现的接口类
 * 这个类是规范外置Aok的activity的标准类
 * 面向接口开发
 */
public interface PluginInterface {
    public void attach(Activity activity);

    public void onCreate(Bundle saveInstanceState);

    public void onStart();

    public void onResume();

    public void onPause();

    public void onStop();

    public void onDestroy();

    public void onSaveInstanceState(Bundle outState);

    public boolean onTouchEvent(MotionEvent motionEvent);

    public void onBackPressed();

}

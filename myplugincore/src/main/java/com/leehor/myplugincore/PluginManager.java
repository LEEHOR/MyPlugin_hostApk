package com.leehor.myplugincore;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * author : Leehor
 * date   : 2019/12/199:39
 * version: 1.0
 * desc   :插件Apk的加载类，用来加载迪第三方apk的资源
 */
public class PluginManager {
    private static PluginManager pluginManager;

    private PluginManager() {
    }

    //插件中的资源对象
    private Resources resources;
    //类加载器 加载第三方apk的类
    private DexClassLoader dexClassLoader;
    //上下文
    private Context context;
    //插件apk的包信息类
    private PackageInfo packageInfo;

    //单例
    public static PluginManager getInstance() {
        if (pluginManager == null) {
            pluginManager = new PluginManager();
        }
        return pluginManager;
    }

    //设置上下文
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * 用于加载第三方插件Apk
     *
     * @param path 第三方插件apk sd存储地址
     */
    public void LoadPath(String path) {
        //获取当前宿主Apk私有的存储路径
        File dex = context.getDir("dex", Context.MODE_PRIVATE);
        /*初始化类加载器*/
        //dexPath：目标类所在的APK或者jar包，/.../xxx.jar
        //optimizedDirectory：从APK或者jar解压出来的dex文件存放路径
        //libraryPath：native库路径，可以为null
        //parent：父类装载器，一般为当前类的装载器
        dexClassLoader = new DexClassLoader(path, dex.getAbsolutePath(), null, context.getClassLoader());
        /*初始化包管理器*/
        PackageManager packageManager = context.getPackageManager();
        /*通过包管理器获取插件apk包信息*/
        packageInfo = packageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);

        try {
            AssetManager assetManager=AssetManager.class.newInstance();
            Method addAssetPath = AssetManager.class.getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, path);

            //初始化resouce对象
            resources= new Resources(assetManager,context.getResources().getDisplayMetrics(),
                    context.getResources().getConfiguration());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Resources getResources() {
        return resources;
    }

    public DexClassLoader getDexClassLoader() {
        return dexClassLoader;
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }
}

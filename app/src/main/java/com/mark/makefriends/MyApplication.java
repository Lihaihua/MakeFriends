package com.mark.makefriends;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.mark.makefriends.im.MessageHandler;
import com.mark.makefriends.support.dao.DBOpenHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import cn.bmob.newim.BmobIM;
import cn.bmob.v3.Bmob;

/**
 * Created by Administrator on 2016/4/24.
 */
public class MyApplication extends Application{
    private static final String TAG = "MyApplication";
    //private static final String appId = "c7b3d285e74c0ca37950c20b10f8c85a";
    public static MyApplication app;
    @Override
    public void onCreate(){
        super.onCreate();
        app = this;

        if (getApplicationInfo().packageName.equals(getMyProcessName(this))){
            Log.i(TAG, "init Bmob sdk!");
            //Bmob sdk初始化
            //Bmob.initialize(this,appId);
            //NewIM初始化,初始化方法包含了BmobSDK的初始化步骤，故无需再初始化BmobSDK
            BmobIM.init(this);
            //注册消息接收器
            BmobIM.registerDefaultMessageHandler(new MessageHandler());
        }

    }

    /**
     * 获得当前运行的进程名
     * @return
     */
    public static String getMyProcessName(Context context){
        ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : activityManager.getRunningAppProcesses()){
            if (appProcessInfo.pid == android.os.Process.myPid()){
                return appProcessInfo.processName;
            }
        }
        return null;
    }
}

package com.mark.makefriends;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * Created by Administrator on 2016/4/24.
 */
public class MyApplication extends Application{
    private static final String appId = "c7b3d285e74c0ca37950c20b10f8c85a";
    @Override
    public void onCreate(){
        super.onCreate();
        Bmob.initialize(this,appId);
    }
}

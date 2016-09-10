package com.mark.makefriends;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.mark.makefriends.bean.Person;
import com.mark.makefriends.im.MessageHandler;
import com.mark.makefriends.support.Location;
import com.mark.makefriends.support.dao.IUser;
import com.mark.makefriends.support.dao.UserDao;
import com.mark.makefriends.utils.MyApp;
import com.squareup.leakcanary.LeakCanary;

import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2016/4/24.
 */
public class MyApplication extends Application{
    private static final String TAG = "MyApplication";
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

        //初始化定位
        Location.INSTANCE.initLocation();

        //初始化Fresco
        Fresco.initialize(this);

        //查询Bmob云端 person表
        queryPersonTable();

        Log.i(TAG, "SHA1: " + MyApp.getSha1(this));

        //初始化LeakCanary
        LeakCanary.install(this);
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

    /**
     * 批量查询Bmob云端 person表
     */
    private void queryPersonTable(){
        BmobQuery<Person> query = new BmobQuery<Person>();
        query.findObjects(this, new FindListener<Person>() {
            @Override
            public void onSuccess(List<Person> list) {
                IUser user = new UserDao(app);
                for (Person person : list){
                    Object[] params = {person.getObjectId(), person.getUser().getObjectId()};
                    user.addPersonUser(params);
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }
}

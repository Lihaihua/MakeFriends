package com.mark.makefriends;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.mark.makefriends.bean.Person;
import com.mark.makefriends.im.MessageHandler;
import com.mark.makefriends.support.dao.IUser;
import com.mark.makefriends.support.dao.UserDao;
import com.mark.makefriends.utils.MyApp;

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
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = new AMapLocationClientOption();

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
        initLocation();

        //查询Bmob云端 person表
        queryPersonTable();
    }

    private void initLocation(){
        locationClient = new AMapLocationClient(app);
        setLocationOption();
        locationClient.setLocationListener(locationListener);
        startLocation();
    }

    private void startLocation(){
        locationClient.startLocation();
    }

    private void stopLocation(){
        locationClient.stopLocation();
    }

    private void destroyLocation(){
        if (locationClient != null){
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }

    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (MyApp.parseLocation(aMapLocation)){
                stopLocation();
            }
        }
    };

    private void setLocationOption(){
        // 设置是否需要显示地址信息
        locationOption.setNeedAddress(true);
        /**
         * 设置是否优先返回GPS定位结果，如果30秒内GPS没有返回定位结果则进行网络定位
         * 注意：只有在高精度模式下的单次定位有效，其他方式无效
         */
        locationOption.setGpsFirst(true);
        // 设置是否开启缓存
        locationOption.setLocationCacheEnable(true);
        //设置是否等待设备wifi刷新，如果设置为true,会自动变为单次定位，持续定位时不要使用
        locationOption.setOnceLocationLatest(true);
        // 设置发送定位请求的时间间隔,最小值为1000，如果小于1000，按照1000算
        locationOption.setInterval(-1);//设置定位一次
        // 设置网络请求超时时间
        locationOption.setHttpTimeOut(30000);
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

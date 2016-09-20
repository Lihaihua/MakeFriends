package com.mark.makefriends.support;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.mark.makefriends.MyApplication;
import com.mark.makefriends.bean.Person;
import com.mark.makefriends.support.dao.IUser;
import com.mark.makefriends.support.dao.UserDao;
import com.mark.makefriends.utils.MyApp;

import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2016/9/8.
 */
public enum  Location {
    INSTANCE;
    private static final String TAG = "Location";
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = new AMapLocationClientOption();

    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (MyApp.parseLocation(aMapLocation)){
                stopLocation();
            }
        }
    };

    public void initLocation(){
        locationClient = new AMapLocationClient(MyApplication.getInstance());
        setLocationOption();
        locationClient.setLocationListener(locationListener);
        startLocation();
    }

    public void startLocation(){
        locationClient.startLocation();
    }

    private void stopLocation(){
        locationClient.stopLocation();
    }

    public void destroyLocation(){
        if (locationClient != null){
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }

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

    public void updateUserCity(String city){
        IUser user = new UserDao(MyApplication.getInstance());
        String userObjId = "";
        if (MyApp.getCurrentUser() != null){
            userObjId = MyApp.getCurrentUser().getObjectId();
        }

        String[] seleStr = {userObjId};
        String personObjId = user.selectPersonObjIdByUserObjId(seleStr);

        Person person = new Person();
        person.setValue("location", city);
        person.update(MyApplication.getInstance(), personObjId, new UpdateListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }
}

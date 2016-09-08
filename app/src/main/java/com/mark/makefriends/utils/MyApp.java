package com.mark.makefriends.utils;

import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.mark.makefriends.MyApplication;
import com.mark.makefriends.bean.Person;
import com.mark.makefriends.bean.User;
import com.mark.makefriends.event.LocationEvent;
import com.mark.makefriends.support.BusProvider;
import com.mark.makefriends.ui.LoginActivity;

import cn.bmob.push.lib.util.LogUtil;
import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2016/5/14.
 */
public class MyApp {
    private static final String TAG = "MyApp";
    public static String userObjId;
    public static String personObjId;
    public static double latitude;
    public static double longitude;

    public static String getUserObjId() {
        return userObjId;
    }

    public static String setUserObjId(String ObjId){
        return userObjId = ObjId;
    }

    public static User getCurrentUser() {
        return BmobUser.getCurrentUser(MyApplication.app, User.class);
    }

    public static String getPersonObjId() {
        return personObjId;
    }

    public static void setPersonObjId(String personObjId) {
        MyApp.personObjId = personObjId;
    }

    public static double getLatitude() {
        return latitude;
    }

    public static void setLatitude(double latitude) {
        MyApp.latitude = latitude;
    }

    public static double getLongitude() {
        return longitude;
    }

    public static void setLongitude(double longitude) {
        MyApp.longitude = longitude;
    }

    /**
     * 解析定位结果
     * @param location
     */
    public static boolean parseLocation(AMapLocation location){
        if (location == null){
            return false;
        }

        if (location.getErrorCode() == 0){
            Log.i(TAG, "定位成功! " + "城市: " + location.getCity() + " 纬度: " + location.getLatitude() + " 经度: " + location.getLongitude());
            //setCity(location.getCity());
            setLatitude(location.getLatitude());
            setLongitude(location.getLongitude());
            BusProvider.getInstance().post(new LocationEvent(location.getCity()));
            return true;
        }else {
            Log.i(TAG, "定位失败! 错误码: " + location.getErrorCode() + " 错误信息: " + location.getErrorInfo() + " 错误描述: " + location.getLocationDetail());
            return false;
        }
    }

}

package com.mark.makefriends.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.mark.makefriends.MyApplication;
import com.mark.makefriends.bean.User;
import com.mark.makefriends.event.LocationEvent;
import com.mark.makefriends.support.BusProvider;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

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

    public static String getSha1(Context context){
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length()-1);

        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return null;
    }

}

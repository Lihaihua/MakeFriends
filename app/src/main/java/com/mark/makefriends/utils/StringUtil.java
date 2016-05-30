package com.mark.makefriends.utils;

/**
 * Created by Administrator on 2016/5/14.
 */
public class StringUtil {

    public static String addStr2BeforeDotOfStr1(String str1, String str2){
        String[] a = str1.split("\\.");
        return a[0] + "_" + str2 + "." + a[1];
    }
}

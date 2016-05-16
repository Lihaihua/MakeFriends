package com.mark.makefriends.until;

/**
 * Created by Administrator on 2016/5/14.
 */
public class StringUntil {

    public static String addStr2BeforeDotOfStr1(String str1, String str2){
        String[] a = str1.split("\\.");
        return a[0] + "_" + str2 + "." + a[1];
    }
}

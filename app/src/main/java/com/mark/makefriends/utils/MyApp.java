package com.mark.makefriends.utils;

import com.mark.makefriends.MyApplication;
import com.mark.makefriends.bean.User;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2016/5/14.
 */
public class MyApp {
    public static String userObjId;

    public static String getUserObjId() {
        return userObjId;
    }

    public static String setUserObjId(String ObjId){
        return userObjId = ObjId;
    }

    public static User getCurrentUser() {
        return BmobUser.getCurrentUser(MyApplication.app, User.class);
    }

}

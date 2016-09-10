package com.mark.makefriends.utils;

import android.content.Context;

/**
 * Created by Administrator on 2016/9/10.
 */
public class ToastUtil {

   public static void showToast(Context ctx, String s, final int gravity){
        android.widget.Toast toast = android.widget.Toast.makeText(ctx, s, android.widget.Toast.LENGTH_SHORT);
        toast.setGravity(gravity, 0, 0);
        toast.show();
    }

}

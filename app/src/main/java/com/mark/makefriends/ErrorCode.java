package com.mark.makefriends;

/**
 * Created by Administrator on 2016/5/3.
 */
public class ErrorCode {
    public static final int ERROR_PHONE_EMAIL_FORMAT = 301;//格式不正确：手机号码、邮箱地址
    public static final int ERROR_USER_ALREADY_HAVE = 202;//用户已存在
    public static final int ERROR_EMAIL_ALREADY_USED = 203;//该Email已经注册过
    public static final int ERROR_NOT_REGISTER = 101;//没有注册
    public static final int ERROR_PHONE_NUM_ALREADY_HAVE = 209;//手机号已被注册
    public static final int ERROR_NETWORK_NOT_NORMAL = 9010;//网络异常（超时）
}

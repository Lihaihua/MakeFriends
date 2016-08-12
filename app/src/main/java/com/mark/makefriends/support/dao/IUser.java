package com.mark.makefriends.support.dao;

import android.graphics.Bitmap;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Administrator on 2016/6/12.
 */
public interface IUser {
    public boolean addHostUser(Object[] params);
    public boolean delHostUser(Object[] params);
    public boolean updateHostUser(Object[] params);
    //使用 Map<String, String> 做一个封装，比如说查询数据库的时候返回的单条记录
    public Map<String, String> selectSingleRcd(String[] selectionArgs);
    //使用 List<Map<String, String>> 做一个封装，比如说查询数据库的时候返回的多条记录
    public List<Map<String, String>> selectMultiRcd(String[] selectionArgs);
    public Bitmap getHostUserHead(String[] selectionArgs);
    public long getTableRowCount();//获得表中的总行数

    public boolean addUser(Object[] params);
    public List<String> selectUserObjId();
    public void insertUserPhotoByUserId(Object[] params);

}

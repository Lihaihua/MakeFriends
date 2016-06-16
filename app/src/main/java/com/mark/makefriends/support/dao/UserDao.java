package com.mark.makefriends.support.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Administrator on 2016/6/12.
 */
public class UserDao implements User{
    private String addSql = "insert into user(name, password, sex, age, city) values(?, ?, ?, ?, ?)";
    private String updateSql = "update user set name = ?, password = ?, sex = ?, age = ?, city = ? where id = ?";
    private String selectSql = "select * from user where id = ?";
    private String selectSum = "select count(*) from user";

    private DBOpenHelper helper = null;
    public UserDao(Context context){
        helper = new DBOpenHelper(context);
    }

    @Override
    public boolean addUser(Object[] params) {
        boolean flag = false;
        SQLiteDatabase database = null;
        try{
            database = helper.getWritableDatabase();
            database.execSQL(addSql, params);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (database != null){
                database.close();
            }
        }
        return flag;
    }

    @Override
    public boolean delUser(Object[] params) {
        return false;
    }

    @Override
    public boolean updateUser(Object[] params) {
        boolean flag = false;
        SQLiteDatabase database = null;
        try{
            database = helper.getWritableDatabase();
            database.execSQL(updateSql);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (database != null){
                database.close();
            }
        }
        return flag;
    }

    //根据Id号来查询，查询的每一行数据返回用 Map 集合来存储
    @Override
    public Map<String, String> selectSingleRcd(String[] selectionArgs) {
        Map<String, String> map = new HashMap<String, String>();
        SQLiteDatabase database = null;
        try {
            database = helper.getReadableDatabase();
            //声明一个游标，这个是行查询的操作，支持原生SQL语句的查询
            Cursor cursor = database.rawQuery(selectSql, selectionArgs); //ID所在行查询
            int colums = cursor.getColumnCount();//获得数据库的列的个数
            //cursor.moveToNext() 移动到下一条记录
            while (cursor.moveToNext()){
                for (int i = 0; i < colums; i++){
                    String cols_name = cursor.getColumnName(i);//提取列的名称
                    String cols_value = cursor.getString(cursor.getColumnIndex(cols_name));//根据列的名称提取列的值
                    //数据库中有写记录是允许有空值的,所以这边需要做一个处理
                    if (cols_value == null){
                        cols_value = "";
                    }
                    map.put(cols_name, cols_value);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (database != null){
                database.close();
            }
        }
        return map;
    }

    @Override
    public List<Map<String, String>> selectMultiRcd(String[] selectionArgs) {
        return null;
    }

    @Override
    public Bitmap getUserHead(String[] selectionArgs) {
        SQLiteDatabase database = null;
        Bitmap bmp = null;
        try {
            database = helper.getReadableDatabase();
            //声明一个游标，这个是行查询的操作，支持原生SQL语句的查询
            Cursor cursor = database.rawQuery(selectSql, selectionArgs); //ID所在行查询
            int colums = cursor.getColumnCount();//获得数据库的列的个数
            //cursor.moveToNext() 移动到下一条记录
            while (cursor.moveToNext()) {
                for (int i = 0; i < colums; i++) {
                    String cols_name = cursor.getColumnName(i);//提取列的名称
                    if ("image".equals(cols_name)) {
                        byte[] cols_value = cursor.getBlob(cursor.getColumnIndex(cols_name));
                        bmp = BitmapFactory.decodeByteArray(cols_value, 0, cols_value.length);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (database != null){
                database.close();
            }
        }
        return bmp;
    }

    @Override
    public long getTableRowCount() {
        SQLiteDatabase database = null;
        long count = 0;
        try {
            database = helper.getReadableDatabase();
            SQLiteStatement statement = database.compileStatement(selectSum);
            count = statement.simpleQueryForLong();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (database != null){
                database.close();
            }
        }

        return count;
    }


}

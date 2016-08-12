package com.mark.makefriends.support.dao;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * Created by Administrator on 2016/6/8.
 */
public class DBOpenHelper extends SQLiteOpenHelper{

    private static String name = "makefriends.db";
    private String createHostUserTable = "Create table host_user (id integer primary key autoincrement, name varchar(64), password varchar(64), sex integer, age integer, city varchar(64));";
    private String createUserTable = "Create table user (objId varchar(64) primary key, name varchar(64), sex integer, age integer, city varchar(64), image varchar(255))";
    private static int version = 1;//更新数据库的版本号，此时会执行onUpgrade()方法

    public DBOpenHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createHostUserTable);
        db.execSQL(createUserTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

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
    private String createTbSql = "Create table user (id integer primary key autoincrement, name varchar(64), password varchar(64), sex integer, age integer, city varchar(64));";
    private static int version = 1;//更新数据库的版本号，此时会执行onUpgrade()方法

    public DBOpenHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTbSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

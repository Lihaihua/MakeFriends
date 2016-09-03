package com.mark.makefriends.support.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/6/8.
 */
public class DBOpenHelper extends SQLiteOpenHelper{

    private static String name = "makefriends.db";
    private String createHostUserTable = "Create table host_user (id integer primary key autoincrement, name varchar(64), password varchar(64), sex integer, age integer, city varchar(64));";
    private String createUserTable = "Create table user (id integer primary key autoincrement, userId varchar(64), name varchar(64), sex integer, age integer, city varchar(64), image varchar(255))";
    private String createUserPersonTable = "Create table user_person_id (id integer primary key autoincrement, personObjId varchar(64), userObjId varchar(64))";
    private String createPersonTable = "Create table person (id integer primary key autoincrement, personId varchar(64), nick varchar(64), location varchar(64), gender integer, avatar varchar(64), age integer)";
    private static int version = 1;//更新数据库的版本号，此时会执行onUpgrade()方法

    public DBOpenHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createHostUserTable);
        db.execSQL(createUserTable);
        db.execSQL(createUserPersonTable);
        db.execSQL(createPersonTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

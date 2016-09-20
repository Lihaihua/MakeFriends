package com.mark.makefriends.support.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.mark.makefriends.bean.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/12.
 */
public class UserDao implements IUser {
    private String insertHostUser = "insert into host_user(name, password, sex, age, city) values(?, ?, ?, ?, ?)";
    private String updateHostUser = "update host_user set name = ?, password = ?, sex = ?, age = ?, city = ? where id = ?";
    private String selectAllHostUserById = "select * from host_user where id = ?";
    private String selectHostUserCount = "select count(*) from host_user";
    //private String insertUser = "insert into user(objId, name, sex, age, city) values(?, ?, ?, ?, ?)";
    private String insertUser = "insert into user(userId, name) values(?, ?)";
    private String selectUserObjId = "select userId from user";
    private String inserUserPhotoByUserId = "insert into user(image) values(?) where objId = ?";

    private String insertPersonUser = "insert into user_person_id(personObjId, userObjId) values(?, ?)";
    private String selectPeronUserTableByUserObjId = "select personObjId from user_person_id where userObjId = ?";
    private String selectPeronUserTableByPersonObjId = "select userObjId from user_person_id where personObjId = ?";

    private String insertPerson = "insert into person(personId, nick, location, gender, avatar, age) values(?, ?, ?, ?, ?, ?)";
    private String selectAllPerson = "select * from person";

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
            database.execSQL(insertUser, params);
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
    public List<String> selectUserObjId() {
        List<String> userObjId = new ArrayList<String>();
        SQLiteDatabase database = null;
        Cursor cursor = null;
        try{
            database = helper.getReadableDatabase();
            cursor = database.rawQuery(selectUserObjId, null);
            while (cursor.moveToNext()){
                userObjId.add(cursor.getString(0));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (database != null){
                cursor.close();
                database.close();
            }
        }
        return userObjId;
    }

    @Override
    public void insertUserPhotoByUserId(Object[] params) {
        SQLiteDatabase database = null;
        try{
            database = helper.getWritableDatabase();
            database.execSQL(inserUserPhotoByUserId, params);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (database != null){
                database.close();
            }
        }
    }

    @Override
    public boolean addPersonUser(Object[] params) {
        boolean flag = false;
        SQLiteDatabase database = null;
        try{
            database = helper.getWritableDatabase();
            database.execSQL(insertPersonUser, params);
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
    public String selectPersonObjIdByUserObjId(String[] selectionArgs) {
        String personObjId = "";
        SQLiteDatabase database = null;
        Cursor cursor = null;
        try{
            database = helper.getReadableDatabase();
            cursor = database.rawQuery(selectPeronUserTableByUserObjId, selectionArgs);
            int colums = cursor.getColumnCount();
            while (cursor.moveToNext()){
                for (int i = 0; i < colums; i++){
                    String cols_name = cursor.getColumnName(i);//提取列的名称
                    if ("personObjId".equals(cols_name)){
                        String cols_value = cursor.getString(cursor.getColumnIndex(cols_name));//根据列的名称提取列的值
                        personObjId = cols_value;
                    }
                }
            }
            return personObjId;

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (database != null){
                cursor.close();
                database.close();
            }
        }
        return null;
    }

    @Override
    public String selectUserObjIdByPersonObjId(String[] selectionArgs) {
        String userObjId = "";
        SQLiteDatabase database = null;
        Cursor cursor = null;
        try{
            database = helper.getReadableDatabase();
            cursor = database.rawQuery(selectPeronUserTableByPersonObjId, selectionArgs);
            int colums = cursor.getColumnCount();
            while (cursor.moveToNext()){
                for (int i = 0; i < colums; i++){
                    String cols_name = cursor.getColumnName(i);//提取列的名称
                    if ("userObjId".equals(cols_name)){
                        String cols_value = cursor.getString(cursor.getColumnIndex(cols_name));//根据列的名称提取列的值
                        userObjId = cols_value;
                    }
                }
            }
            return userObjId;

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (database != null){
                cursor.close();
                database.close();
            }
        }
        return null;
    }

    @Override
    public boolean addHostUser(Object[] params) {
        boolean flag = false;
        SQLiteDatabase database = null;
        try{
            database = helper.getWritableDatabase();
            database.execSQL(insertHostUser, params);
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
    public boolean delHostUser(Object[] params) {
        return false;
    }

    @Override
    public boolean updateHostUser(Object[] params) {
        boolean flag = false;
        SQLiteDatabase database = null;
        try{
            database = helper.getWritableDatabase();
            database.execSQL(updateHostUser);
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
        Cursor cursor = null;
        try {
            database = helper.getReadableDatabase();
            //声明一个游标，这个是行查询的操作，支持原生SQL语句的查询
            cursor = database.rawQuery(selectAllHostUserById, selectionArgs); //ID所在行查询
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
                cursor.close();
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
    public Bitmap getHostUserHead(String[] selectionArgs) {
        SQLiteDatabase database = null;
        Cursor cursor = null;
        Bitmap bmp = null;
        try {
            database = helper.getReadableDatabase();
            //声明一个游标，这个是行查询的操作，支持原生SQL语句的查询
            cursor = database.rawQuery(selectAllHostUserById, selectionArgs); //ID所在行查询
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
                cursor.close();
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
            SQLiteStatement statement = database.compileStatement(selectHostUserCount);
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

    @Override
    public boolean addPerson(Object[] params){
        boolean flag = false;
        SQLiteDatabase database = null;
        try {
            database = helper.getWritableDatabase();
            database.execSQL(insertPerson, params);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(database != null){
                database.close();
            }
        }
        return flag;
    }

    @Override public List<Person> selectAllPerson(){
        List<Person> personList = new ArrayList<Person>();
        SQLiteDatabase database = null;
        Cursor cursor = null;
        try{
            database = helper.getReadableDatabase();
            cursor = database.rawQuery(selectAllPerson, null);
            while (cursor.moveToNext()){
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String location = cursor.getString(2);
                Integer gender = cursor.getInt(3);
                String avatar = cursor.getString(4);
                Integer age = cursor.getInt(5);

                Person person = new Person();
                person.setObjectId(id);
                person.setNick(name);
                person.setLocation(location);
                person.setGender(gender);
                person.setAvatar(avatar);
                person.setAge(age);
                personList.add(person);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (database != null){
                cursor.close();
                database.close();
            }
        }
        return personList;
    }

}

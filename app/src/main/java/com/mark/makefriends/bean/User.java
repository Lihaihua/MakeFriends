package com.mark.makefriends.bean;
import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2016/4/24.
 */
public class User extends BmobUser {
    private Integer gender;
    private Integer age;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }
}

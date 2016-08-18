package com.mark.makefriends.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by Administrator on 2016/8/16.
 */
public class BmobChatUser extends BmobUser{
    private String nick;//昵称
    private Integer gender;//性别
    private String avatar;//头像
    private BmobRelation contacts;//好友联系人
    private String location;//位置城市
    private Integer age;//年龄

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public BmobRelation getContacts() {
        return contacts;
    }

    public void setContacts(BmobRelation contacts) {
        this.contacts = contacts;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

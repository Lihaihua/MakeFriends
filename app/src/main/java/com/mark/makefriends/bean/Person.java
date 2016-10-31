package com.mark.makefriends.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by Administrator on 2016/8/22.
 */
public class Person extends BmobObject{
    private BmobUser user;
    private String nick;//昵称
    private Integer gender;//性别
    private String avatar;//头像
    private BmobFile avatarFile;
    private BmobRelation contacts;//好友联系人
    private BmobRelation keep;//被某人收藏
    private String location;//位置城市
    private Integer age;//年龄
    private String sign;//签名

    public BmobUser getUser() {
        return user;
    }

    public void setUser(BmobUser user) {
        this.user = user;
    }

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

    public BmobFile getAvatarFile() {
        return avatarFile;
    }

    public void setAvatarFile(BmobFile avatarFile) {
        this.avatarFile = avatarFile;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public BmobRelation getKeep() {
        return keep;
    }

    public void setKeep(BmobRelation keep) {
        this.keep = keep;
    }
}

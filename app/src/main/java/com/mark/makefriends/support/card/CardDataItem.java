package com.mark.makefriends.support.card;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * 卡片数据装载对象
 *
 * @author xmuSistone
 */
public class CardDataItem {
    String imagePath;
    String userName;
    String location;
    Integer age;
    int likeNum;
    int imageNum;

    String userObjId;
    Integer gender;//性别
    BmobRelation contacts;//好友联系人
    String sign;//签名
}

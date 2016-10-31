package com.mark.makefriends.support.card;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mark.makefriends.MyApplication;
import com.mark.makefriends.R;
import com.mark.makefriends.bean.Person;
import com.mark.makefriends.support.card.CardSlidePanel.CardSwitchListener;
import com.mark.makefriends.support.dao.IUser;
import com.mark.makefriends.support.dao.UserDao;
import com.mark.makefriends.ui.MainActivity;
import com.mark.makefriends.utils.MyApp;
import com.mark.makefriends.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 卡片Fragment
 *
 * @author xmuSistone
 */
@SuppressLint({"HandlerLeak", "NewApi", "InflateParams"})
public class CardFragment extends Fragment {
    private static final String TAG = "CardFragment";
    private CardSwitchListener cardSwitchListener;

    private List<CardDataItem> dataList = new ArrayList<CardDataItem>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.card_layout, null);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        CardSlidePanel slidePanel = (CardSlidePanel) rootView
                .findViewById(R.id.image_slide_panel);
        cardSwitchListener = new CardSwitchListener() {

            @Override
            public void onShow(int index) {
                Log.d("CardFragment", "正在显示-" + dataList.get(index).userName);
            }

            @Override
            public void onCardVanish(int index, int type) {
                Log.d("CardFragment", "正在消失-" + dataList.get(index).userName + " 消失type=" + type);

                CardDataItem cardDataItem = dataList.get(index);

                if (type == 0){
                    ToastUtil.showToast(getActivity(), "不喜欢", Gravity.BOTTOM);
                } else if (type == 1){
                    String userObjId = cardDataItem.userObjId;
                    String nick = cardDataItem.userName;
                    Integer age = cardDataItem.age;
                    Integer gender = cardDataItem.gender;
                    String location = cardDataItem.location;
                    String img = cardDataItem.imagePath;
                    String sign = cardDataItem.sign;

                    Person person_i_like = new Person();
                    person_i_like.setObjectId(userObjId);
                    person_i_like.setNick(nick);
                    person_i_like.setAge(age);
                    person_i_like.setGender(gender);
                    person_i_like.setLocation(location);
                    person_i_like.setAvatar(img);
                    person_i_like.setSign(sign);

                    updatePersonContacts(person_i_like);
                    ToastUtil.showToast(getActivity(), "喜欢", Gravity.BOTTOM);
                }
            }

            @Override
            public void onItemClick(View cardView, int index) {
                Log.d("CardFragment", "卡片点击-" + dataList.get(index).userName);
            }
        };
        slidePanel.setCardSwitchListener(cardSwitchListener);

        prepareDataList();
        slidePanel.fillData(dataList);
    }

    private void updatePersonContacts(Person person_be_like){
        BmobRelation relation = new BmobRelation();
        relation.add("当前person");

        person_be_like.setKeep(relation);
        person_be_like.update(getActivity().getApplicationContext(), new UpdateListener() {
            @Override
            public void onSuccess() {
                Log.i(TAG, "updatePersonContacts success");
            }

            @Override
            public void onFailure(int i, String s) {
                Log.i(TAG, "updatePersonContacts fail");
            }
        });
    }

    private List<Person> getAllPersonInfoFromDB(){
        IUser iUser = new UserDao(getActivity());
        return iUser.selectAllPerson();
    }

    private void prepareDataList() {
        IUser user = new UserDao(MyApplication.getInstance());

        List<Person> data = getAllPersonInfoFromDB();
        int num = data.size();
        for (int i = 0; i < num; i++){
            CardDataItem dataItem = new CardDataItem();
            Person person = data.get(i);
            dataItem.userName = person.getNick();
            dataItem.imagePath = person.getAvatar();
            dataItem.likeNum = (int)(Math.random()*10);
            dataItem.imageNum = (int)(Math.random()*6);
            dataItem.location = person.getLocation();

            String personObjectId = person.getObjectId();
            String[] seleStr = {personObjectId};
            String userObjId = user.selectUserObjIdByPersonObjId(seleStr);

            dataItem.userObjId = userObjId;
            dataItem.age = person.getAge();
            dataItem.contacts = person.getContacts();
            dataItem.gender = person.getGender();
            dataItem.sign = person.getSign();
            dataList.add(dataItem);
        }
    }

}

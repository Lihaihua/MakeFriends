package com.mark.makefriends.ui;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mark.makefriends.R;
import com.mark.makefriends.adapter.MyAdapter;
import com.mark.makefriends.bean.Person;
import com.mark.makefriends.bean.User;
import com.mark.makefriends.support.CircularImage;
import com.mark.makefriends.support.dao.IUser;
import com.mark.makefriends.support.dao.UserDao;
import com.mark.makefriends.utils.BitmapUtil;
import com.mark.makefriends.utils.MyApp;
import com.mark.mylibrary.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2016/5/14.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    TextView nickName;
    TextView edit;
    LinearLayout yue;
    LinearLayout message;
    LinearLayout friends;
    LinearLayout setting;
    CircularImage userHead;

    private int i;
    private SwipeFlingAdapterView flingContainer;
    private Button leftBtn;
    private Button rightBtn;
    private Button centerBtn;
    private List<Map<String, Object>> mData;

    private Activity mActivity;
    private Uri imageUri;

    private List<User> userList;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);

        mActivity = this;

        initDrawerView();
        initSwipeView();

        QueryUserTable();
    }

    private void getAllPerson(){
        BmobQuery<User> query = new BmobQuery<User>();
        query.findObjects(this, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {
                userList = list;
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }


    /**
     * 获得指定id的用户头像
     */
    public void getUserHead(){
        final User user = MyApp.getCurrentUser();
        BmobQuery<Person> query = new BmobQuery<Person>();
        query.addWhereEqualTo("user", user);
        query.findObjects(this, new FindListener<Person>() {
            @Override
            public void onSuccess(List<Person> list) {
                for (Person person : list){
                   person.getAvatarFile().download(mActivity, new DownloadFileListener() {
                        @Override
                        public void onSuccess(String savePath) {
                            imageUri = BitmapUtil.getImageUri(savePath);
                            if (imageUri == null){
                                userHead.setImageResource(R.drawable.logo);
                            }else {
                                userHead.setImageURI(imageUri);
                            }
                        }

                        @Override
                        public void onFailure(int i, String s) {

                        }
                    });
                }

            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    private void initDrawerView(){
        getUserHead();
        toolbar = (Toolbar)findViewById(R.id.toolBar);
        toolbar.setTitle(R.string.yue);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        actionBarDrawerToggle.syncState();

        userHead = (CircularImage)findViewById(R.id.user_head);
        userHead.setImageResource(R.drawable.logo);

        nickName = (TextView)findViewById(R.id.nick_name);
        edit = (TextView)findViewById(R.id.edit);
        yue = (LinearLayout)findViewById(R.id.yue);
        message = (LinearLayout)findViewById(R.id.message);
        friends = (LinearLayout)findViewById(R.id.friends);
        setting = (LinearLayout)findViewById(R.id.setting);

        userHead.setOnClickListener(this);
        nickName.setOnClickListener(this);
        edit.setOnClickListener(this);
        yue.setOnClickListener(this);
        message.setOnClickListener(this);
        friends.setOnClickListener(this);
        setting.setOnClickListener(this);
    }

    private void initSwipeView(){
        flingContainer = (SwipeFlingAdapterView)findViewById(R.id.frame1);
        leftBtn = (Button)findViewById(R.id.left);
        rightBtn = (Button)findViewById(R.id.right);
        centerBtn = (Button)findViewById(R.id.center);
        leftBtn.setOnClickListener(this);
        rightBtn.setOnClickListener(this);
        centerBtn.setOnClickListener(this);

        mData = getData();

        final MyAdapter myAdapter = new MyAdapter(this, mData);
        flingContainer.setAdapter(myAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                mData.remove(0);
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                makeToast(MainActivity.this, "不喜欢");
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                makeToast(MainActivity.this, "喜欢");
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("img", R.drawable.ic_launcher);
                map.put("info", "eeeeeee " + i);
                mData.add(map);
                i++;
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
            }
        });

        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                makeToast(MainActivity.this, "Clicked");
            }
        });

    }

    static void makeToast(Context ctx, String s){
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }

    private List<Map<String, Object>> getData() {

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;

        map = new HashMap<String, Object>();
        map.put("img", R.drawable.ic_launcher);
        map.put("info", "跆拳道");
        list.add(map);

        map.put("img", R.drawable.ic_launcher);
        map.put("info", "跆拳道1");
        list.add(map);

        map.put("img", R.drawable.ic_launcher);
        map.put("info", "跆拳道2");
        list.add(map);

        return list;
    }

    public void right(){
        flingContainer.getTopCardListener().selectRight();
    }

    public void left(){
        flingContainer.getTopCardListener().selectLeft();
    }

    private void QueryUserTable(){
        BmobQuery<User> query = new BmobQuery<User>();
        //query.setLimit(100);
        query.findObjects(mActivity, new FindListener<User>(){

            @Override
            public void onSuccess(List<User> list) {
                IUser userDao = new UserDao(MainActivity.this);
                for (User user : list){
                    String userId = user.getObjectId();
                    String userName = user.getUsername();
                    //int sex = user.getGender();
                    //int age = user.getAge();
                    //Object[] params = {objId, userName, sex, age};
                    Object[] params = {userId, userName};
                    userDao.addUser(params);
                }
                return;
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(mActivity,s,Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_head:
                break;
            case R.id.nick_name:
                break;
            case R.id.edit:
                break;
            case R.id.yue:
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.message:
                break;
            case R.id.friends:
                break;
            case R.id.setting:
                break;
            case R.id.left:
                left();
                break;
            case R.id.right:
                right();
                break;
            case R.id.center:
                break;
            default:
                break;
        }
    }
}

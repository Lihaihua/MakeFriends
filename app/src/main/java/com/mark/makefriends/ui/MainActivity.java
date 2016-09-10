package com.mark.makefriends.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mark.makefriends.R;
import com.mark.makefriends.adapter.MyAdapter;
import com.mark.makefriends.bean.Person;
import com.mark.makefriends.bean.User;
import com.mark.makefriends.event.LocationEvent;
import com.mark.makefriends.support.BusProvider;
import com.mark.makefriends.support.CircularImage;
import com.mark.makefriends.support.Location;
import com.mark.makefriends.support.dao.IUser;
import com.mark.makefriends.support.dao.UserDao;
import com.mark.makefriends.support.otto.Subscribe;
import com.mark.makefriends.utils.BitmapUtil;
import com.mark.makefriends.utils.MyApp;
import com.mark.makefriends.utils.ToastUtil;
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

    private static final String TAG = "MainActivity";
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

        BusProvider.getInstance().regist(this);

        mActivity = this;

        initDrawerView();
        initSwipeView();
    }

    @Override
    public void onResume(){
        super.onResume();
        Location.INSTANCE.startLocation();
    }

    @Override
    public void onDestroy(){
        BusProvider.getInstance().unregist(this);
        super.onDestroy();
    }

    @Subscribe
    public void onLocationEvent(LocationEvent event){
        Location.INSTANCE.updateUserCity(event.city);
    }

    private List<Person> getAllPersonInfoFromDB(){
        IUser iUser = new UserDao(MainActivity.this);
        return iUser.selectAllPerson();
    }


    /**
     * 获得指定id的用户头像
     */
    private void getUserHead(){
        final User user = MyApp.getCurrentUser();
        BmobQuery<Person> query = new BmobQuery<Person>();
        query.addWhereEqualTo("user", user);
        query.findObjects(this, new FindListener<Person>() {
            @Override
            public void onSuccess(List<Person> list) {
                for (Person person : list){
                    if (person.getAvatarFile() == null){
                        return;
                    }
                   person.getAvatarFile().download(mActivity, new DownloadFileListener() {
                        @Override
                        public void onSuccess(String savePath) {
                            imageUri = BitmapUtil.getImageUri(savePath);
                            if (imageUri == null){
                                userHead.setImageResource(R.drawable.nohead);
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
        userHead.setImageResource(R.drawable.nohead);

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

        mData = getAllData();

        final MyAdapter myAdapter = new MyAdapter(getApplicationContext(), mData);
        flingContainer.setAdapter(myAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                if (mData.size() > 0) {
                    mData.remove(0);
                }

                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                ToastUtil.showToast(MainActivity.this, "不喜欢", Gravity.BOTTOM);
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                ToastUtil.showToast(MainActivity.this, "喜欢", Gravity.BOTTOM);
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                Log.i(TAG, "itemsInAdapter: " + itemsInAdapter);
                // Ask for more data here
                myAdapter.notifyDataSetChanged();
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

            }
        });

    }

    private List<Map<String, Object>> getAllData() {
        final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        List<Person> list_person = getAllPersonInfoFromDB();
        for (Person person : list_person){

            //Log.i(TAG, person.getObjectId() + " " + person.getNick() + " " + person.getLocation() + " " + person.getGender() + " " + person.getAvatar() + " " + person.getAge());

            String nick = person.getNick();
            String img_url = person.getAvatar();
            String location = person.getLocation();
            Integer gender = 1;
            Integer age = 18;
            String sign = "总有一天会遇到,阳光灿烂的你!";

            if (img_url != null){
                Uri uri = Uri.parse(img_url);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("img", uri);
                map.put("nick", nick);
                map.put("gender", gender);
                map.put("age", age);
                map.put("location", location);
                map.put("sign", sign);
                list.add(map);
            }

        }

        return list;
    }

    public void right(){
        flingContainer.getTopCardListener().selectRight();
    }

    public void left(){
        flingContainer.getTopCardListener().selectLeft();
    }

    /**
     * 响应手机返回键，任务进入后台
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK){
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
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

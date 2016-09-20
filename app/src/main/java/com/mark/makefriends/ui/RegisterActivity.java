package com.mark.makefriends.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mark.makefriends.ErrorCode;
import com.mark.makefriends.MyApplication;
import com.mark.makefriends.R;
import com.mark.makefriends.bean.Person;
import com.mark.makefriends.support.dao.IUser;
import com.mark.makefriends.support.dao.UserDao;
import com.mark.makefriends.utils.MyApp;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2016/4/26.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    private static final int RequestCode = 1;
    private View ll_register;
    private View ll_back;
    private TextView title;
    private EditText userName;
    private EditText password;
    private EditText email;
    private Button registerBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ll_register = (View)findViewById(R.id.ll_register);
        ll_register.setOnTouchListener(this);
        ll_back = (View)findViewById(R.id.ll_back);
        title = (TextView)findViewById(R.id.tv_title);
        title.setText("注册");
        ll_back.setOnClickListener(this);

        userName = (EditText)findViewById(R.id.et_user_name);
        password = (EditText)findViewById(R.id.et_password);
        email = (EditText)findViewById(R.id.et_email);
        registerBtn = (Button)findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(this);

        MyApplication.getInstance().addActivity(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                finish();
                break;
            case R.id.registerBtn:
                showProgressDialog("正在注册...");
                final BmobUser bu = new BmobUser();
                bu.setUsername(userName.getText().toString());
                bu.setEmail(email.getText().toString());
                bu.setPassword(password.getText().toString());
                bu.signUp(this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        MyApp.setUserObjId(bu.getObjectId());

                        dismissProgressDialog();
                        Toast.makeText(getApplicationContext(), "注册成功!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(), CompleteUserInfoActivity.class);
                        startActivity(intent);

                        //注册成功后将用户名密码存储在本地数据库
                        IUser user = new UserDao(RegisterActivity.this);
                        String name = userName.getText().toString();
                        String passwd =  password.getText().toString();
                        Object[] params = {name, passwd, 0, 0, "暂无"};
                        user.addHostUser(params);

                        //建立_User和Person的一一关联
                        final Person person = new Person();
                        person.setUser(bu);
                        person.save(getApplicationContext(), new SaveListener() {
                            @Override
                            public void onSuccess() {
                                //更新本地user_person_id表
                                String userObjId = bu.getObjectId();
                                String perObjId = person.getObjectId();
                                String[] params = {perObjId, userObjId};
                                IUser user = new UserDao(RegisterActivity.this);
                                user.addPersonUser(params);
                             }

                            @Override
                            public void onFailure(int i, String s) {

                            }
                        });

                        finish();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        switch (i){
                            case ErrorCode.ERROR_PHONE_EMAIL_FORMAT:
                                Toast.makeText(getApplicationContext(), "注册失败，手机或邮箱格式不正确!", Toast.LENGTH_SHORT).show();
                                break;
                            case ErrorCode.ERROR_USER_ALREADY_HAVE:
                                Toast.makeText(getApplicationContext(), "该昵称已被注册!", Toast.LENGTH_SHORT).show();
                                break;
                            case ErrorCode.ERROR_EMAIL_ALREADY_USED:
                                Toast.makeText(getApplicationContext(), "该邮箱已经注册过!", Toast.LENGTH_SHORT).show();
                                break;
                            case ErrorCode.ERROR_PHONE_NUM_ALREADY_HAVE:
                                Toast.makeText(getApplicationContext(), "该手机号已被注册!", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                        dismissProgressDialog();
                    }
                });
                break;
            default:
                break;
        }
    }
}

package com.mark.makefriends.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mark.makefriends.ErrorCode;
import com.mark.makefriends.R;
import com.mark.makefriends.support.CircularImage;
import com.mark.makefriends.support.dao.User;
import com.mark.makefriends.support.dao.UserDao;
import com.mark.makefriends.utils.BitmapUtil;

import java.util.Map;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";
    private View ll_login;
    private View ll_back;
    private TextView title;
    private EditText userName;
    private EditText password;
    private Button loginBtn;
    private TextView register;
    private Activity mActivity;
    public static CircularImage cover_user_photo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mActivity = this;

        ll_login = (View)findViewById(R.id.ll_login);
        ll_back = (View)findViewById(R.id.ll_back);
        ll_back.setVisibility(View.GONE);
        title = (TextView)findViewById(R.id.tv_title);
        title.setText(getResources().getText(R.string.app_name));
        userName = (EditText)findViewById(R.id.user_name);
        password = (EditText)findViewById(R.id.password);
        loginBtn = (Button)findViewById(R.id.login);
        register = (TextView)findViewById(R.id.register);

        ll_login.setOnTouchListener(this);
        loginBtn.setOnClickListener(this);
        register.setOnClickListener(this);

        cover_user_photo = (CircularImage) findViewById(R.id.cover_user_photo);
        cover_user_photo.setImageResource(R.drawable.loginlogo);

    }

    @Override
    public void onResume(){
        super.onResume();
        getUserName();
    }

    private void getUserName(){
        User user = new UserDao(LoginActivity.this);
        long count = user.getTableRowCount();
        String[] seleStr = {Integer.toString((int)count)};
        Map<String,String> us = user.selectSingleRcd(seleStr);
        userName.setText(us.get("name"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                if(userName.getText().toString().equals("") || password.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "用户名密码不能为空!", Toast.LENGTH_SHORT).show();
                    break;
                }

                showProgressDialog("正在登录...");
                final BmobUser bu2 = new BmobUser();
                bu2.setUsername(userName.getText().toString());
                bu2.setPassword(password.getText().toString());
                bu2.login(this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Intent i = new Intent();
                        i.setClass(mActivity, MainActivity.class);
                        startActivity(i);
                        dismissProgressDialog();

                        //Toast.makeText(getApplicationContext(), "登录成功!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        if(i == ErrorCode.ERROR_NOT_REGISTER){
                            Toast.makeText(getApplicationContext(), "登录失败,请先注册！", Toast.LENGTH_SHORT).show();
                        }
                        else if (i == ErrorCode.ERROR_NETWORK_NOT_NORMAL){
                            Toast.makeText(getApplicationContext(), "网络异常(超时)！", Toast.LENGTH_SHORT).show();
                        }
                        dismissProgressDialog();
                    }
                });
                break;

            case R.id.register:
                Intent intent = new Intent();
                intent.setClass(this, RegisterActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

}

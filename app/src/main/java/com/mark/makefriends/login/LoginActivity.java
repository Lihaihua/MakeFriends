package com.mark.makefriends.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mark.makefriends.R;
import com.mark.makefriends.navigationtabbar.HorizontalNtbActivity;
import com.mark.makefriends.support.CircularImage;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends Activity implements View.OnClickListener{
    private static final String TAG = "MainActivity";
    private EditText userName;
    private EditText password;
    private Button loginBtn;
    private TextView register;
    private Activity mActivity;
    CircularImage cover_user_photo1,cover_user_photo2,cover_user_photo3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mActivity = this;

        userName = (EditText)findViewById(R.id.user_name);
        password = (EditText)findViewById(R.id.password);
        loginBtn = (Button)findViewById(R.id.login);
        register = (TextView)findViewById(R.id.register);

        loginBtn.setOnClickListener(this);
        register.setOnClickListener(this);

        cover_user_photo1 = (CircularImage) findViewById(R.id.cover_user_photo1);
        cover_user_photo2 = (CircularImage) findViewById(R.id.cover_user_photo2);
        cover_user_photo3 = (CircularImage) findViewById(R.id.cover_user_photo3);
        cover_user_photo1.setImageResource(R.drawable.pic1);
        cover_user_photo2.setImageResource(R.drawable.pic2);
        cover_user_photo3.setImageResource(R.drawable.pic3);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                if(userName.getText().toString().equals("") || password.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "用户名密码不能为空!", Toast.LENGTH_SHORT).show();
                    break;
                }
                BmobUser bu2 = new BmobUser();
                bu2.setUsername(userName.getText().toString());
                bu2.setPassword(password.getText().toString());
                bu2.login(this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Intent i = new Intent();
                        i.setClass(mActivity, HorizontalNtbActivity.class);
                        startActivity(i);
                        //Toast.makeText(getApplicationContext(), "登录成功!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        if(i == 101){
                            Toast.makeText(getApplicationContext(), "登录失败,请先注册！", Toast.LENGTH_SHORT).show();
                        }

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

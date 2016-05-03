package com.mark.makefriends.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mark.makefriends.ErrorCode;
import com.mark.makefriends.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

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
    private EditText phone;
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

        userName = (EditText)findViewById(R.id.userName);
        password = (EditText)findViewById(R.id.password);
        phone = (EditText)findViewById(R.id.phone);
        registerBtn = (Button)findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                finish();
                break;
            case R.id.registerBtn:
                showProgressDialog("正在注册...");
                BmobUser bu = new BmobUser();
                bu.setUsername(userName.getText().toString());
                bu.setPassword(password.getText().toString());
                bu.setMobilePhoneNumber(phone.getText().toString());
                bu.signUp(this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        dismissProgressDialog();
                        Toast.makeText(getApplicationContext(), "注册成功!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(), UploadImageActivity.class);
                        startActivity(intent);
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

package com.mark.makefriends.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mark.makefriends.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016/4/26.
 */
public class RegisterActivity extends Activity implements View.OnClickListener{

    private static final int RequestCode = 1;
    private EditText userName;
    private EditText password;
    private EditText phone;
    private Button registerBtn;
    //private ImageView role_head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName = (EditText)findViewById(R.id.userName);
        password = (EditText)findViewById(R.id.password);
        phone = (EditText)findViewById(R.id.phone);
        registerBtn = (Button)findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerBtn:
                BmobUser bu = new BmobUser();
                bu.setUsername(userName.getText().toString());
                bu.setPassword(password.getText().toString());
                bu.setMobilePhoneNumber(phone.getText().toString());
                bu.signUp(this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(), "注册成功!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(), UploadImageActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        if(i == 301){
                            Toast.makeText(getApplicationContext(), "注册失败，手机或邮箱格式不正确！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            default:
                break;
        }
    }

}

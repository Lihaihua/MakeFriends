package com.mark.makefriends.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mark.makefriends.MyApplication;
import com.mark.makefriends.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.ResetPasswordByEmailListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2016/7/26.
 */
public class FindPasswordActivity extends BaseActivity implements View.OnClickListener{
    private EditText etEmail;
    private Button btFindPassword;
    private View ll_back;
    private TextView title;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);

        ll_back = (View)findViewById(R.id.ll_back);
        title = (TextView)findViewById(R.id.tv_title);
        title.setText("修改密码");
        ll_back.setOnClickListener(this);

        etEmail = (EditText)findViewById(R.id.et_email);
        btFindPassword = (Button)findViewById(R.id.btn_find_password);
        btFindPassword.setOnClickListener(this);

        MyApplication.getInstance().addActivity(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                finish();
                break;
            case R.id.btn_find_password:
                final String email = etEmail.getText().toString();
                BmobUser.resetPasswordByEmail(this, email, new ResetPasswordByEmailListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(), "请求成功,请到邮箱确认修改密码。", Toast.LENGTH_LONG).show();
                        finish();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(getApplicationContext(), "请求失败! "+ s, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            default:
                break;
        }
    }
}

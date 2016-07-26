package com.mark.makefriends.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mark.makefriends.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.ResetPasswordByEmailListener;

/**
 * Created by Administrator on 2016/7/26.
 */
public class FindPasswordActivity extends BaseActivity implements View.OnClickListener{
    private EditText etEmail;
    private Button btFindPassword;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);

        etEmail = (EditText)findViewById(R.id.et_email);
        btFindPassword = (Button)findViewById(R.id.btn_find_password);
        btFindPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_find_password:
                final String email = etEmail.getText().toString();
                BmobUser.resetPasswordByEmail(this, email, new ResetPasswordByEmailListener() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFailure(int i, String s) {

                    }
                });
                break;
            default:
                break;
        }
    }
}

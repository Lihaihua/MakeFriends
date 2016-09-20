package com.mark.makefriends.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mark.makefriends.MyApplication;
import com.mark.makefriends.R;
import com.mark.makefriends.support.MyEnsureDialog;

/**
 * Created by Administrator on 2016/9/18.
 */
public class SettingsActivity extends BaseActivity implements View.OnClickListener{
    private View mAbout;
    private View mLoginOut;
    private MyEnsureDialog aboutDialog;
    private MyEnsureDialog loginOutDialog;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mAbout = (View)findViewById(R.id.rl_about);
        mLoginOut = (View)findViewById(R.id.rl_login_out);

        mAbout.setOnClickListener(this);
        mLoginOut.setOnClickListener(this);

        MyApplication.getInstance().addActivity(this);
    }

    public static void skipTo(Context context){
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_about:
                aboutDialog = new MyEnsureDialog(SettingsActivity.this, " 关于", "约吧 v1.0 by Lee", "取消", "确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        aboutDialog.dismiss();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        aboutDialog.dismiss();
                    }
                });
                aboutDialog.show();
                break;
            case R.id.rl_login_out:
                loginOutDialog = new MyEnsureDialog(SettingsActivity.this, "退出", "你确定要退出约吧", "取消", "确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loginOutDialog.dismiss();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyApplication.getInstance().exit();
                    }
                });
                loginOutDialog.show();
                break;
            default:
                break;
        }
    }
}

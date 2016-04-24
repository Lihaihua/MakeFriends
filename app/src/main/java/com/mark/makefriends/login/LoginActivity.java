package com.mark.makefriends.login;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mark.makefriends.R;
import com.mark.makefriends.bean.Person;

import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends Activity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

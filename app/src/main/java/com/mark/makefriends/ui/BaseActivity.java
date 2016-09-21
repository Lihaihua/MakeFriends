package com.mark.makefriends.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.mark.makefriends.R;

/**
 * Created by Administrator on 2016/5/3.
 */
public class BaseActivity extends Activity implements View.OnTouchListener{
    private ProgressDialog pd;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.ll_login || v.getId() == R.id.ll_register){
            //收起软键盘
            if (event.getAction() == MotionEvent.ACTION_DOWN &&
                    getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null){
                InputMethodManager mInputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return false;
    }

    public void showProgressDialog(String word) {
        if (pd == null) {
            pd = new ProgressDialog(this);
            pd.setCancelable(false);
        }
        pd.setMessage(word);
        if (!pd.isShowing()) {
            pd.show();
        }
    }

    public void dismissProgressDialog() {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
            pd = null;
        }
    }

    public void startActivity(Class<? extends Activity> target, Bundle bundle){
        Intent intent = new Intent();
        intent.setClass(this, target);
        if (bundle != null){
            intent.putExtra(getPackageName(), bundle);
        }
        startActivity(intent);
    }

}

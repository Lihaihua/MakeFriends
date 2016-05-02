package com.mark.makefriends.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mark.makefriends.R;

/**
 * Created by Administrator on 2016/5/2.
 */
public class SelectPicActivity extends Activity implements View.OnClickListener {
    private Button CameraBtn;
    private Button AlbumBtn;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pic);

        CameraBtn = (Button)findViewById(R.id.btn_camera);
        AlbumBtn = (Button)findViewById(R.id.btn_album);
        CameraBtn.setOnClickListener(this);
        AlbumBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_camera:
                break;
            case R.id.btn_album:
                break;
            default:
                break;
        }
        finish();
    }
}

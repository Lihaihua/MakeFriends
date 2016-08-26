package com.mark.makefriends.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mark.makefriends.R;

/**
 * Created by Administrator on 2016/8/26.
 */
public class FrescoTestActivity extends Activity{
    private SimpleDraweeView simpleDraweeView;
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_fresco_test);
        simpleDraweeView = (SimpleDraweeView)findViewById(R.id.img);

        String img_url = getIntent().getStringExtra("img_url");
        setImage(img_url);
    }

    private void setImage(String url){
        Uri uri = Uri.parse(url);
        simpleDraweeView.setImageURI(uri);
    }
}

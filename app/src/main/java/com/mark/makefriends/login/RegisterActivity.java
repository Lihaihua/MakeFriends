package com.mark.makefriends.login;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mark.makefriends.R;

import java.io.File;
import java.io.FileNotFoundException;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by Administrator on 2016/4/26.
 */
public class RegisterActivity extends Activity implements View.OnClickListener{

    private static final int RequestCode = 1;
    private EditText userName;
    private EditText password;
    private EditText phone;
    private EditText email;
    private Button registerBtn;
    private ImageView role_head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        role_head = (ImageView)findViewById(R.id.role_head);
        userName = (EditText)findViewById(R.id.userName);
        password = (EditText)findViewById(R.id.password);
        phone = (EditText)findViewById(R.id.phone);
        email = (EditText)findViewById(R.id.email);
        registerBtn = (Button)findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(this);
        role_head.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerBtn:
                BmobUser bu = new BmobUser();
                bu.setUsername(userName.getText().toString());
                bu.setPassword(password.getText().toString());
                bu.setMobilePhoneNumber(phone.getText().toString());
                bu.setEmail(email.getText().toString());
                bu.signUp(this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(), "注册成功!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(), LoginActivity.class);
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
            case R.id.role_head:
                showSelectUploadPicDialog();
                break;
        }
    }

    private void showSelectUploadPicDialog(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, RequestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            Uri uri = data.getData();
            ContentResolver cr = this.getContentResolver();
            try{
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                role_head.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            upLoadFile( getRealFilePath(this,uri) );

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getRealFilePath( final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    private void upLoadFile(String filePath){
        BmobFile bmobFile = new BmobFile(new File(filePath));
        bmobFile.uploadblock(this, new UploadFileListener() {

            @Override
            public void onSuccess() {
                //bmobFile.getFileUrl(context)--返回的上传文件的完整地址
                Toast.makeText(getApplicationContext(), "头像上传成功！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）

            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(getApplicationContext(), "头像上传失败！", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

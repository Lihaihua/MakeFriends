package com.mark.makefriends.login;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.mark.makefriends.R;
import com.mark.makefriends.support.ImageCompress;
import com.mark.makefriends.support.PhotoUtil;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by Administrator on 2016/5/2.
 */
public class UploadImageActivity extends Activity implements View.OnClickListener{
    private ImageView roleHead;
    private ImageButton maleBtn;
    private ImageButton femaleBtn;
    private Button checkBtn;

    private static final int REQUEST_IMAGE_CAMERA = 1;
    private static final int REQUEST_IMAGE_ALBUM = 2;
    private Uri uri;  //图片保存uri
    private File scaledFile;
    private String filePath;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        roleHead = (ImageView)findViewById(R.id.role_head);
        maleBtn = (ImageButton)findViewById(R.id.maleBtn);
        femaleBtn = (ImageButton)findViewById(R.id.femaleBtn);
        checkBtn = (Button)findViewById(R.id.checkBtn);

        roleHead.setOnClickListener(this);
        checkBtn.setOnClickListener(this);
        maleBtn.setOnClickListener(this);
        femaleBtn.setOnClickListener(this);
    }

    /**
     * 打开相机
     */
    public void openCamera(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        uri = PhotoUtil.createImageFile();
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {//相机被卸载时不会崩溃
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                    uri);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAMERA);
        }
    }

    /**
     * 打开相册
     */
    public void openAlbum() {
        Intent takePictureIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {// 相机被卸载时不会崩溃
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_ALBUM);
        }
    }

    /**
     * 压缩拍照得到的图片
     */
    private void dealTakePhoto() {
        scaledFile = ImageCompress.scal(uri);
        Bitmap bitmap = BitmapFactory.decodeFile(scaledFile.getAbsolutePath());
        roleHead.setImageBitmap(bitmap);

        filePath = uri.getPath();
    }
    /**
     * 压缩相册得到的图片
     */
    private void dealAlbum(Intent data) {
        uri = data.getData();
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, proj, null, null,null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        System.out.println("path:"+path);
        File imageFile = new File(path);
        uri = Uri.fromFile(imageFile);
        scaledFile = ImageCompress.scal(uri);
        Bitmap bitmap = BitmapFactory.decodeFile(scaledFile.getAbsolutePath());
        roleHead.setImageBitmap(bitmap);

        filePath = uri.getPath();
    }

    /**
     * 回调函数
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAMERA && resultCode == RESULT_OK) {
            dealTakePhoto();
        } else if (requestCode == REQUEST_IMAGE_ALBUM && resultCode == RESULT_OK) {
            uri = data.getData();
            if(uri!=null){
                System.out.println(uri.getPath());
                dealAlbum(data);
            }else{
                System.out.println("uri为空");
            }
        } else {

        }
    }

    //上传头像
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

//    public String getRealFilePath( final Context context, final Uri uri ) {
//        if ( null == uri ) return null;
//        final String scheme = uri.getScheme();
//        String data = null;
//        if ( scheme == null )
//            data = uri.getPath();
//        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
//            data = uri.getPath();
//        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
//            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
//            if ( null != cursor ) {
//                if ( cursor.moveToFirst() ) {
//                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
//                    if ( index > -1 ) {
//                        data = cursor.getString( index );
//                    }
//                }
//                cursor.close();
//            }
//        }
//        return data;
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.role_head:
                openCamera();
//                Intent i = new Intent();
//                i.setClass(this, SelectPicActivity.class);
//                startActivity(i);
                break;
            case R.id.maleBtn:
                maleBtn.setSelected(true);
                femaleBtn.setSelected(false);
                break;
            case R.id.femaleBtn:
                maleBtn.setSelected(false);
                femaleBtn.setSelected(true);
                break;
            case R.id.checkBtn:
                upLoadFile(filePath);
                break;
            default:
                break;
        }
    }
}

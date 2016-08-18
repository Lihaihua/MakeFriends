package com.mark.makefriends.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mark.makefriends.R;
import com.mark.makefriends.bean.Photo;
import com.mark.makefriends.bean.User;
import com.mark.makefriends.support.ImageCompress;
import com.mark.makefriends.support.PhotoUtil;
import com.mark.makefriends.utils.MyApp;
import com.soundcloud.android.crop.Crop;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import android.hardware.Camera;

/**
 * Created by Administrator on 2016/5/2.
 */
public class CompleteUserInfoActivity extends BaseActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener{
    private Activity mActivity;
    private View ll_back;
    private TextView title;
    private ImageView roleHead;
    private ImageButton maleBtn;
    private ImageButton femaleBtn;
    private gender sex;
    private Button checkBtn;
    private View fl_select_pic_type;
    private TextView tv_age;
    private SeekBar seekBar;

    private Button CameraBtn;
    private Button AlbumBtn;

    private static final int REQUEST_IMAGE_CAMERA = 1;
    private static final int REQUEST_IMAGE_ALBUM = 2;
    private Uri uri;  //图片保存uri
    private File scaledFile;
    private String filePath;
    private Bitmap bmp;//旋转后的图像
    private Bitmap bitmap;

    public enum gender{MALE, FEMALE}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_user_info);

        mActivity = this;

        ll_back = (View)findViewById(R.id.ll_back);
        title = (TextView)findViewById(R.id.tv_title);
        title.setText("上传照片");
        ll_back.setOnClickListener(this);

        roleHead = (ImageView)findViewById(R.id.role_head);
        maleBtn = (ImageButton)findViewById(R.id.maleBtn);
        femaleBtn = (ImageButton)findViewById(R.id.femaleBtn);
        checkBtn = (Button)findViewById(R.id.checkBtn);
        fl_select_pic_type = (View)findViewById(R.id.fl_select_pic_type);

        CameraBtn = (Button)findViewById(R.id.btn_camera);
        AlbumBtn = (Button)findViewById(R.id.btn_album);
        CameraBtn.setOnClickListener(this);
        AlbumBtn.setOnClickListener(this);

        tv_age = (TextView)findViewById(R.id.tv_age);
        seekBar = (SeekBar)findViewById(R.id.seekBar);

        roleHead.setOnClickListener(this);
        checkBtn.setOnClickListener(this);
        maleBtn.setOnClickListener(this);
        femaleBtn.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);
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

    private int count;
    /**
     * 压缩拍照得到的图片
     */
    private void dealTakePhoto() {
        int cameraId = -1;
        Camera.CameraInfo info = new Camera.CameraInfo();
        scaledFile = ImageCompress.scal(uri);
        bitmap = BitmapFactory.decodeFile(scaledFile.getAbsolutePath());

        if (bitmap.getWidth() > bitmap.getHeight()){
            Matrix matrix = new Matrix();
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT){
                matrix.postRotate(-90);
            }else if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK){
                matrix.postRotate(90);
            }

            bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
        roleHead.setImageBitmap(bmp);
    }
    /**
     * 压缩相册得到的图片
     */
    private void dealAlbum(Intent data) {
//        uri = data.getData();
//        String[] proj = { MediaStore.Images.Media.DATA };
//        Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        String path = cursor.getString(column_index);
//        System.out.println("path:"+path);
//        File imageFile = new File(path);
//        uri = Uri.fromFile(imageFile);
//        scaledFile = ImageCompress.scal(uri);
//        bitmap = BitmapFactory.decodeFile(scaledFile.getAbsolutePath());
//        roleHead.setImageBitmap(bitmap);
//
//        //filePath = uri.getPath();
//        filePath = scaledFile.getAbsolutePath();
        Uri uri = Crop.getOutput(data);
        scaledFile = ImageCompress.scal(uri);
        filePath = scaledFile.getAbsolutePath();
        bitmap = BitmapFactory.decodeFile(filePath);
        roleHead.setImageBitmap(bitmap);
    }

    private void beginCrop(Uri source){
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent data){
        if (resultCode == RESULT_OK){
            dealAlbum(data);
            //roleHead.setImageURI(Crop.getOutput(data));
        }else if (resultCode == Crop.RESULT_ERROR){
            Toast.makeText(this, Crop.getError(data).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 回调函数
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK){
            beginCrop(data.getData());
        }else if (requestCode == Crop.REQUEST_CROP){
            handleCrop(resultCode, data);
        }

//        if (requestCode == REQUEST_IMAGE_CAMERA && resultCode == RESULT_OK) {
//            dealTakePhoto();
//        } else if (requestCode == REQUEST_IMAGE_ALBUM && resultCode == RESULT_OK) {
//            uri = data.getData();
//            if(uri!=null){
//                System.out.println(uri.getPath());
//                dealAlbum(data);
//            }else{
//                System.out.println("uri为空");
//            }
//        } else {
//
//        }
    }

    private void updateUserData(User user, UpdateListener listener){
        user.setObjectId(MyApp.getCurrentUser().getObjectId());
        user.update(this,listener);
    }

    //上传头像
    private void upLoadFile(final String filePath){
        if (filePath == null){
            Toast.makeText(getApplicationContext(), "未选择头像!", Toast.LENGTH_SHORT).show();
            dismissProgressDialog();
            return;
        }
        final BmobFile bmobFile = new BmobFile(new File(filePath));
        bmobFile.uploadblock(this, new UploadFileListener() {

            @Override
            public void onSuccess() {
                //返回上传文件的完整地址
                String url = bmobFile.getFileUrl(CompleteUserInfoActivity.this);
                User user1 = new User();
                user1.setAvatar(url);
                updateUserData(user1, new UpdateListener() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFailure(int i, String s) {

                    }
                });

                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "照片上传成功！", Toast.LENGTH_SHORT).show();

                //添加用户和头像一对一关联
                User user = MyApp.getCurrentUser();
                Photo photo = new Photo();
                photo.setImage(bmobFile);
                photo.setOwner(user);
                photo.save(mActivity, new SaveListener() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFailure(int i, String s) {

                    }
                });

                finish();
            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）

            }

            @Override
            public void onFailure(int code, String msg) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "头像上传失败！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Bitmap scalePic(Bitmap bitmap){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        //设置想要的大小
        int newWidth = 240;
        int newHeight = 240;
        //计算缩放比例
        float scaleWidth = ((float)newWidth) / width;
        float scaleHeight = ((float)newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_back:
                finish();
                break;
            case R.id.role_head:
                roleHead.setImageDrawable(null);
                //fl_select_pic_type.setVisibility(View.VISIBLE);
                Crop.pickImage(this);
                break;
            case R.id.maleBtn:
                sex = gender.MALE;
                maleBtn.setSelected(true);
                femaleBtn.setSelected(false);
                break;
            case R.id.femaleBtn:
                sex = gender.FEMALE;
                maleBtn.setSelected(false);
                femaleBtn.setSelected(true);
                break;
            case R.id.checkBtn:
                showProgressDialog("正在上传头像...");
                upLoadFile(filePath);
                break;
            case R.id.btn_camera:
                openCamera();
                fl_select_pic_type.setVisibility(View.GONE);
                break;
            case R.id.btn_album:
                openAlbum();
                fl_select_pic_type.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        tv_age.setText(Integer.toString(progress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}

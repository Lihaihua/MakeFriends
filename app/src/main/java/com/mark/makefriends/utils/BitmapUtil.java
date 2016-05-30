package com.mark.makefriends.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;

import java.io.IOException;

/**
 * Created by Administrator on 2016/5/30.
 */
public class BitmapUtil {
    //得到图片的方向
    public static int getOrientation(final Uri photoUri){
        ExifInterface exifInterface = null;
        try {
            exifInterface = new ExifInterface(photoUri.toString());
        }catch (IOException e){
            e.printStackTrace();
        }
        return exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
    }

    //通过photo的uri来得到图片的角度，从而判断是否需要进行旋转操作
    public static int getPhotoDegreeByUri(Uri uri){
        int degree = 0;
        int orientation = BitmapUtil.getOrientation(uri);
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90){
            degree = 90;
        }else if (orientation == ExifInterface.ORIENTATION_ROTATE_180){
            degree = 180;
        }else if (orientation == ExifInterface.ORIENTATION_ROTATE_270){
            degree = 270;
        }
        return degree;
    }

    //旋转图像
    public static Bitmap rotateBitmap(Bitmap bmp, int degree){
        Matrix matrix = new Matrix();
        matrix.postScale(1f, 1f);
        //向左旋转45度，参数为正则向右旋转
        matrix.postRotate(-45);
        //bmp.getWidth(), 500分别表示重绘后的位图宽高
        return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), 500, matrix, true);
    }
}

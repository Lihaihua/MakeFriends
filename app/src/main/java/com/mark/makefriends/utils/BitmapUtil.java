package com.mark.makefriends.utils;

import android.content.ContentProvider;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2016/5/30.
 */
public class BitmapUtil {
    //读取图像的旋转的角度
    public static int getBitmapDegree(String path){
        int degree = 0;
        try{
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation){
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return degree;
    }

    //旋转图像
    public static Bitmap rotateBitmapByDegree(Bitmap bmp, int degree){
        Bitmap returnBmp = null;
        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
        }catch (OutOfMemoryError e){
        }
        if (returnBmp == null){
            returnBmp = bmp;
        }
        if (bmp != returnBmp){
            bmp.recycle();
        }
        return returnBmp;
    }

    public static Uri getImageUri(String filePath){
        return Uri.fromFile(new File(filePath));
    }

    public static byte[] bitmap2Byte(Bitmap bitmap){
        int size = bitmap.getWidth()*bitmap.getHeight()*4;
        ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
        int options = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        while (baos.toByteArray().length / 1024 >500){
            baos.reset();
            options -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }

        byte[] imagedata = baos.toByteArray();
        return imagedata;
    }

    public static Bitmap Byte2Bitmap(byte[] b){
        if (b.length != 0){
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        }else {
            return null;
        }
    }
}

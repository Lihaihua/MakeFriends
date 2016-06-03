package com.mark.makefriends.support;

import android.hardware.Camera;
import android.os.Build;

/**
 * Created by Administrator on 2016/3/28.
 */
public class CheckCameraType {
    private static int mCameraCount;

    private static boolean checkCameraFacing(final int facing) {
        if (getSdkVersion() < Build.VERSION_CODES.GINGERBREAD) {
            return false;
        }
        final int cameraCount = Camera.getNumberOfCameras();
        mCameraCount = cameraCount;
        Camera.CameraInfo info = new Camera.CameraInfo();
        for (int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i, info);
            if (facing == info.facing) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasBackFacingCamera() {
        final int CAMERA_FACING_BACK = 0;
        return checkCameraFacing(CAMERA_FACING_BACK);
    }

    public static boolean hasFrontFacingCamera() {
        final int CAMERA_FACING_BACK = 1;
        return checkCameraFacing(CAMERA_FACING_BACK);
    }

    public static int getCameraCount() {
        return mCameraCount;
    }

    public static int getSdkVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }
}

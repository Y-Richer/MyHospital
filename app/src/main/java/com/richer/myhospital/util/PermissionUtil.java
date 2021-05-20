package com.richer.myhospital.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionUtil {

    Activity activity;
    public static final int PERMISSION_REQUEST_CODE_LOCATION = 0;

    public PermissionUtil(Activity activity) {
        this.activity = activity;
    }

    public void requestPermission() {
        //ContextCompat.checkSelfPermission 如果应用具有此权限，方法将返回 PackageManager.PERMISSION_GRANTED
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //ActivityCompat.shouldShowRequestPermissionRationale 用户之前拒绝返回true, 用户拒绝并选择Don't ask again返回false

            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSION_REQUEST_CODE_LOCATION);
        }
    }

}

package com.example.drawingboard;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionUtil {

    //检查多个权限，返回true表示已完全启用权限，返回false表示未完全启用权限
    public static boolean checkPermission(Activity activity,String[] permissions,int requestCode){

        //Android 6.0之后开始采用动态权限管理
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            int check = PackageManager.PERMISSION_GRANTED;

            //逐条判断权限是否开启
            for(String permission : permissions){
                check = ContextCompat.checkSelfPermission(activity,permission);
                //遇到一条未开启的权限，退出
                if(check != PackageManager.PERMISSION_GRANTED){
                    break;
                }
            }

            Log.d("ting",(check != PackageManager.PERMISSION_GRANTED) ? "权限关闭" : "权限开启");

            //未开启权限，则请求系统弹窗，让用户选择是否立即开启权限
            if(check != PackageManager.PERMISSION_GRANTED){
                Log.d("ting","弹窗，获取权限");
                ActivityCompat.requestPermissions(activity,permissions,requestCode);
                Log.d("ting","弹窗已经执行");
                return false;
            }
        }
        return true;
    }

    //检查权限结果数组，返回true表示都已经获取授权，返回false表示至少有一个未获得权限
    public static boolean checkGrant(int[] grantResults) {

        if(grantResults != null){
            //遍历权限结果数组中的每条选择结果
            for(int grant : grantResults){
                //未获得授权
                if(grant != PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}

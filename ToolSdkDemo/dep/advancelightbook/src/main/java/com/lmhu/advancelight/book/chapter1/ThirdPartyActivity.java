package com.lmhu.advancelight.book.chapter1;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lmhu.advancelight.book.R;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class ThirdPartyActivity extends AppCompatActivity {
    private Button bt_call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_party);
        bt_call=(Button)findViewById(R.id.bt_call);
        bt_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // ThirdPartyActivityPermissionsDispatcher.callWithPermissionCheck(ThirdPartyActivity.this);
            }
        });
    }





/*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        //    默认是没有此类的，需要编译下才会有此类 此方法编译生成【Activity】+PermissionsDispatcher
       // ThirdPartyActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
*/



    @NeedsPermission(Manifest.permission.CALL_PHONE)
//在需要获取权限的地方注释
    void call() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + "10086");
        intent.setData(data);
        try {
            startActivity(intent);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }



    @OnShowRationale(Manifest.permission.CALL_PHONE)
//提示用户为何要开启此权限
    void showWhy(final PermissionRequest request){
        new AlertDialog.Builder(this)
                .setMessage("提示用户为何要开启此权限")
                .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        request.proceed(); //再次执行权限请求
                    }
                }).show();
    }



    @OnPermissionDenied(Manifest.permission.CALL_PHONE)
//用户选择拒绝时的提示
    void showDenied() {
        Toast.makeText(this, "用户选择拒绝时的提示", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(Manifest.permission.CALL_PHONE)
//用户选择不再询问后的提示
    void showNotAsk() {
        new AlertDialog.Builder(this)
                .setMessage("该功能需要访问电话的权限，不开启将无法正常工作！")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
    }



}

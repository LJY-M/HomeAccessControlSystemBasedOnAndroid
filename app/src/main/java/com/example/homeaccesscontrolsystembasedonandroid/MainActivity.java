package com.example.homeaccesscontrolsystembasedonandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    String TAG = "  MainActivity";

    private Button OpenDoorButton;
    private Button ApplyAccessButton;
    private Button ManageLoginButton;

    private EditText ManageLoginEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "This is MainActivity.");

        OpenDoorButton = findViewById(R.id.main_button_open_door);
        OpenDoorButton.setOnClickListener(this);
        ApplyAccessButton = findViewById(R.id.main_button_apply_access);
        ApplyAccessButton.setOnClickListener(this);
        ManageLoginButton = findViewById(R.id.main_button_manage_login);
        ManageLoginButton.setOnClickListener(this);

        String[] PERMISSIONS = {
                "android.permission.CAMERA",
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE",
                "android.permission.INTERNET",
                "android.permission.RECEIVE_SMS",
                "android.permission.READ_SMS",
                "android.permission.READ_PHONE_STATE"};
        int REQUEST_CODE_CONTACT = 101;
        for(String str : PERMISSIONS) {
            if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(PERMISSIONS, REQUEST_CODE_CONTACT);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int select = v.getId();
        switch (select) {
            case R.id.main_button_open_door:
                Log.i(TAG, "Clicked Button Open Door.");
                Intent intentToFaceRecognize = new Intent(MainActivity.this, RegisterAndRecognizeActivity.class);
                intentToFaceRecognize.putExtra("openMode", "unauthorized");
                startActivity(intentToFaceRecognize);
                break;
            case R.id.main_button_apply_access:
                Log.i(TAG, "Clicked Button Apply Access.");
                Intent intentToApplyAccessActivity = new Intent(MainActivity.this, ApplyAccessActivity.class);
                startActivity(intentToApplyAccessActivity);
                break;
            case  R.id.main_button_manage_login:
                Log.i(TAG, "Clicked Button Manage Login.");

                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View optionView= inflater.inflate(R.layout.management_login,null);

                ManageLoginEdit = optionView.findViewById(R.id.management_login_password);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("课程操作").setView(optionView)
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                System.out.println("点击确认");
                                String loginPassword = ManageLoginEdit.getText().toString();
                                if (loginPassword.equals("0000")) {
                                    Log.i(TAG, "管理员登录");
                                    Intent intentToManagementActivity = new Intent(MainActivity.this, ManagementActivity.class);
                                    startActivity(intentToManagementActivity);
                                }
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                System.out.println("点击取消");
                            }
                        });
                builder.create().show();
                break;
        }
    }
}

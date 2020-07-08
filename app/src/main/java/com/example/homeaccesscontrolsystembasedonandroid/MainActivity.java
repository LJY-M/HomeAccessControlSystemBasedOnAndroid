package com.example.homeaccesscontrolsystembasedonandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    String TAG = "  MainActivity";

    private Button OpenDoorButton;
    private Button ApplyAccessButton;
    private Button ManageLoginButton;

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
                "android.permission.READ_SMS"};
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
                break;
            case R.id.main_button_apply_access:
                Log.i(TAG, "Clicked Button Apply Access.");
                Intent intentToApplyAccessActivity = new Intent(MainActivity.this, ApplyAccessActivity.class);
                startActivity(intentToApplyAccessActivity);
                break;
            case  R.id.main_button_manage_login:
                Log.i(TAG, "Clicked Button Manage Login.");
                break;
        }
    }
}

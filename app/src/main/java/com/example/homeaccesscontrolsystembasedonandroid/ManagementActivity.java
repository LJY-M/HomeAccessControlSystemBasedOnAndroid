package com.example.homeaccesscontrolsystembasedonandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManagementActivity extends AppCompatActivity implements View.OnClickListener{

    private Button arcFaceButton;
    private Button userInfoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);

        arcFaceButton = findViewById(R.id.management_arc_face_button);
        arcFaceButton.setOnClickListener(this);
        userInfoButton = findViewById(R.id.management_user_info_button);
        userInfoButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int select = v.getId();
        switch (select) {
            case R.id.management_arc_face_button:
                Intent intentToArcFaceActivity = new Intent(ManagementActivity.this, ArcFaceActivity.class);
                startActivity(intentToArcFaceActivity);
                break;
            case R.id.management_user_info_button:
                Intent intentToUserInfoSettingActivity = new Intent(ManagementActivity.this, UserInfoSettingActivity.class);
                startActivity(intentToUserInfoSettingActivity);
                break;
        }
    }
}

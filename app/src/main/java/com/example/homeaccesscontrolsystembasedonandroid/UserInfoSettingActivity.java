package com.example.homeaccesscontrolsystembasedonandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.homeaccesscontrolsystembasedonandroid.util.ConfigUtil;

public class UserInfoSettingActivity extends AppCompatActivity implements View.OnClickListener{

    private Context mContext;

    private EditText phoneNumberEditText;
    private EditText emailAddressEditText;
    private EditText adminPasswordEditText;
    private Button userInfoCommitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_setting);

        phoneNumberEditText = findViewById(R.id.user_info_setting_phone_number);
        emailAddressEditText = findViewById(R.id.user_info_setting_email_address);
        adminPasswordEditText = findViewById(R.id.user_info_setting_admin_password);
        userInfoCommitButton = findViewById(R.id.user_info_setting_commit);
        userInfoCommitButton.setOnClickListener(this);

        mContext = getApplicationContext();
    }

    @Override
    public void onClick(View v) {
        int select = v.getId();
        switch (select) {
            case R.id.user_info_setting_commit:
                String phoneNumberString = phoneNumberEditText.getText().toString();
                String emailAddressString = emailAddressEditText.getText().toString();
                String adminPasswordString = adminPasswordEditText.getText().toString();
                ConfigUtil.setPhoneNumber(mContext, phoneNumberString);
                ConfigUtil.setEmailAddress(mContext, emailAddressString);
                ConfigUtil.setAdminPassword(mContext, adminPasswordString);

                Toast.makeText(this, "设置已提交", Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        phoneNumberEditText.setText(ConfigUtil.getPhoneNumber(mContext));
        emailAddressEditText.setText(ConfigUtil.getEmailAddress(mContext));
        adminPasswordEditText.setText(ConfigUtil.getAdminPassword(mContext));
    }
}

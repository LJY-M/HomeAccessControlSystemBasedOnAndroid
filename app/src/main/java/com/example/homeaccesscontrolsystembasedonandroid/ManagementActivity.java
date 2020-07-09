package com.example.homeaccesscontrolsystembasedonandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManagementActivity extends AppCompatActivity implements View.OnClickListener{

    private Button arcFaceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);

        arcFaceButton = findViewById(R.id.management_arc_face_button);
        arcFaceButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int select = v.getId();
        switch (select) {
            case R.id.management_arc_face_button:
                Intent intentToArcFaceActivity = new Intent(ManagementActivity.this, ArcFaceActivity.class);
                startActivity(intentToArcFaceActivity);
                break;
        }
    }
}

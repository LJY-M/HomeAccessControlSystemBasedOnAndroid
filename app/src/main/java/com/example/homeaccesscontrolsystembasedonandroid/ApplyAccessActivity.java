package com.example.homeaccesscontrolsystembasedonandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ApplyAccessActivity extends AppCompatActivity implements View.OnClickListener{

    String TAG = "  ApplyAccessActivity";

    private ImageView personImageView;
    private Button addImageButton;
    private EditText remarkEditText;
    private Button commitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_access);

        Log.i(TAG, "This is ApplyAccessActivity.");

        personImageView = findViewById(R.id.apply_access_image_view);
        addImageButton = findViewById(R.id.apply_access_add_image);
        addImageButton.setOnClickListener(this);
        remarkEditText = findViewById(R.id.apply_access_remark_edit);
        commitButton = findViewById(R.id.apply_access_commit_apply);
        commitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int select = v.getId();
        switch (select) {
            case R.id.apply_access_add_image:
                Log.i(TAG, "Clicked Button Add Image.");
                break;
            case R.id.apply_access_commit_apply:
                Log.i(TAG, "Clicked Button Commit Apply.");
                String remarkString = remarkEditText.getText().toString();
                if (remarkString.isEmpty()) {
                    Toast.makeText(this, "备注内容不能为空！", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "备注内容为空.");
                    break;
                }
                break;
        }
    }
}

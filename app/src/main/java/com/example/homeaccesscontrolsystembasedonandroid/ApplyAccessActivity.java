package com.example.homeaccesscontrolsystembasedonandroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ApplyAccessActivity extends AppCompatActivity implements View.OnClickListener{

    String TAG = "  ApplyAccessActivity";

    private ImageView personImageView;
    private Button addImageButton;
    private EditText remarkEditText;
    private Button commitButton;

    String fileName = Environment.getExternalStorageDirectory().getPath()+"/DCIM/Camera/"+ "123456.jpg";

    final int TAKE_PICTURE = 1;

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

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
//        解决方案：https://blog.csdn.net/guiping_ding/article/details/78502290

    }

    @Override
    public void onClick(View v) {
        int select = v.getId();
        switch (select) {
            case R.id.apply_access_add_image:
                Log.i(TAG, "Clicked Button Add Image.");
                Intent captureImage = new Intent("android.media.action.IMAGE_CAPTURE");
                Uri uri = Uri.fromFile(new File(fileName));
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(captureImage, TAKE_PICTURE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PICTURE) {
            if (resultCode == RESULT_OK) {
//                Bitmap bm = (Bitmap) data.getExtras().get("data");
                Bitmap bm = BitmapFactory.decodeFile(fileName);
                personImageView.setImageBitmap(bm);//想图像显示在ImageView视图上，private ImageView img;
                personImageView.setBackgroundResource(0);

                Log.i(TAG,fileName);
                File myCaptureFile = new File(fileName);
                if(myCaptureFile.exists()){
                    myCaptureFile.delete();
                }
                try {
//                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
                    FileOutputStream bos = new FileOutputStream(myCaptureFile);
                    /* 采用压缩转档方法 */
                    bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);

                    /* 调用flush()方法，更新BufferStream */
                    bos.flush();

                    /* 结束OutputStream */
                    bos.close();
                    //MediaStore.Images.Media.insertImage(this.getContentResolver(), myCaptureFile.getAbsolutePath(), "123456.jpg", null);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + fileName)));
            }
        }
    }
}

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

import com.example.homeaccesscontrolsystembasedonandroid.util.ConfigUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public class ApplyAccessActivity extends AppCompatActivity implements View.OnClickListener{

    String TAG = "  ApplyAccessActivity";

    private ImageView personImageView;
    private Button addImageButton;
    private EditText remarkEditText;
    private Button commitButton;

    private String[] receiverAddress;

    String fileName = Environment.getExternalStorageDirectory().getPath()+"/DCIM/Camera/"+ "123456.jpg";

    final int TAKE_PICTURE = 1;

    private int imageFlag = 0;

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

        receiverAddress = new String[1];
        receiverAddress[0] = ConfigUtil.getEmailAddress(ApplyAccessActivity.this);

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
                final String remarkString = remarkEditText.getText().toString();
                if (imageFlag == 0) {
                    Toast.makeText(this, "图片不能为空！", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "图片为空.");
                    break;
                }
                if (remarkString.isEmpty()) {
                    Toast.makeText(this, "备注内容不能为空！", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "备注内容为空.");
                    break;
                }
                Toast.makeText(this, "正在发送申请，请稍等片刻。", Toast.LENGTH_LONG).show();
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            EmailSender sender = new EmailSender();
                            //设置服务器地址和端口，网上搜的到
                            sender.setProperties("smtp.qq.com", "465");
                            //分别设置发件人，邮件标题和文本内容
                            sender.setMessage("2958146072@qq.com", "有一份访客申请等待处理", remarkString);
                            //设置收件人的邮箱

//                            sender.setReceiver(new String[]{"ljy_miao@foxmail.com"});
                            sender.setReceiver(receiverAddress);

                            String filPath = "/storage/emulated/0/DCIM/Camera/123456.JPG";
                            File file = new File(filPath);
                            if(file.exists()) {
                                Log.d(TAG,"file.exists()------>>>>>>");
                                //添加附件
                                //这个附件的路径是我手机里的啊，要发你得换成你手机里正确的路径
                                sender.addAttachment(filPath);

                            } else {
                                Log.d(TAG,"file.notexists()------>>>>>>");
                            }

                            //发送邮件,sender.setMessage("你的163邮箱账号", "Email//Sender", "Java Mail ！");这里面两个邮箱账号要一致
                            sender.sendEmail("smtp.qq.com", "2958146072@qq.com", "bgscscijdzhadgcg");

                        } catch (AddressException e) {
                            e.printStackTrace();
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

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
                imageFlag = 1;

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

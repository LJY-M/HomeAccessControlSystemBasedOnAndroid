package com.example.homeaccesscontrolsystembasedonandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class CommandReceiver extends BroadcastReceiver {

    String TAG = "  CommandReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        String commander = "15071345606";

        String openCommand = "open";
        String refuseCommand = "refuse";

        //pdus短信单位pdu
        //解析短信内容
        Object[] pdus = (Object[]) intent.getExtras().get("pdus");
        for (Object pdu : pdus) {
            //封装短信参数的对象
            SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdu);
            String number = sms.getOriginatingAddress();
            String body = sms.getMessageBody();
            //写自己的处理逻辑

            Log.i(TAG, number);
            Log.i(TAG, body);

            if (number.contains(commander)) {
                Log.i(TAG,"Successfully receiving instructions from the commander!");
                if (body.contains(openCommand)) {
                    Log.i(TAG, "Access authorized");
                    Toast.makeText(context, "您的申请已通过！户主马上回来，请进屋稍等片刻。", Toast.LENGTH_LONG).show();
                }
                else if (body.contains(refuseCommand)) {
                    Log.i(TAG, "Access denied");
                    Toast.makeText(context, "您的申请未通过！请联系户主：15071345606。", Toast.LENGTH_LONG).show();
                }
            }

        }
    }

}

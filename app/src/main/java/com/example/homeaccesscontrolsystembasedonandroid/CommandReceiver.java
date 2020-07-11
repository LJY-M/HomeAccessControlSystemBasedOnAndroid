package com.example.homeaccesscontrolsystembasedonandroid;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.homeaccesscontrolsystembasedonandroid.util.ConfigUtil;

public class CommandReceiver extends BroadcastReceiver {

    String TAG = "  CommandReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

//        String commander = "15071345606";
        String commander = ConfigUtil.getPhoneNumber(context);

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
                Log.i(TAG,"Successfully receiving instructions from the commander!" + " :" + commander);

                String recordTimeString = VisitorsRecordContract.VisitorsRecordEntry.DEFAULT_TIME;
                String applicationResultString = VisitorsRecordContract.VisitorsRecordEntry.DEFAULT_RESULT;

                VisitorsRecordDBHelper mVisitorsRecordDBHelper = new VisitorsRecordDBHelper(context);

                String[] queryColumns = new String[] {
                        VisitorsRecordContract.VisitorsRecordEntry.COLUMN_REMARK_INFO,
                        VisitorsRecordContract.VisitorsRecordEntry.COLUMN_RECORDS_TIME,
                        VisitorsRecordContract.VisitorsRecordEntry.COLUMN_APPLICATION_RESULT
                };

                Cursor cursor = mVisitorsRecordDBHelper.getReadableDatabase().query(
                        VisitorsRecordContract.VisitorsRecordEntry.TABLE_NAME,
                        queryColumns,
                        null,
                        null,
                        null,
                        null,
                        VisitorsRecordContract.VisitorsRecordEntry.COLUMN_RECORDS_TIME + " desc"
                );
                if (cursor.moveToFirst()) {
                    recordTimeString = cursor.getString( cursor.getColumnIndex(
                            VisitorsRecordContract.VisitorsRecordEntry.COLUMN_RECORDS_TIME));
                    applicationResultString = cursor.getString( cursor.getColumnIndex(
                            VisitorsRecordContract.VisitorsRecordEntry.COLUMN_APPLICATION_RESULT));
                }
                if (!applicationResultString.equals(VisitorsRecordContract.VisitorsRecordEntry.DEFAULT_RESULT)) {
                    Log.i(TAG, "Access Handled");
                    Toast.makeText(context, "最新的申请已处理！请重新发送申请。", Toast.LENGTH_LONG).show();
                    break;
                }

                if (body.contains(openCommand)) {
                    Log.i(TAG, "Access authorized");

                    ContentValues values = new ContentValues();
                    values.put(VisitorsRecordContract.VisitorsRecordEntry.COLUMN_APPLICATION_RESULT, "authorized");
                    mVisitorsRecordDBHelper.getWritableDatabase()
                            .update(VisitorsRecordContract.VisitorsRecordEntry.TABLE_NAME,
                                    values,
                                    VisitorsRecordContract.VisitorsRecordEntry.COLUMN_RECORDS_TIME + " = ?",
                                    new String[]{recordTimeString});

                    Toast.makeText(context, "您的申请已通过！户主马上回来，请进屋稍等片刻。", Toast.LENGTH_LONG).show();
                }
                else if (body.contains(refuseCommand)) {
                    Log.i(TAG, "Access denied");

                    ContentValues values = new ContentValues();
                    values.put(VisitorsRecordContract.VisitorsRecordEntry.COLUMN_APPLICATION_RESULT, "denied");
                    mVisitorsRecordDBHelper.getWritableDatabase()
                            .update(VisitorsRecordContract.VisitorsRecordEntry.TABLE_NAME,
                                    values,
                                    VisitorsRecordContract.VisitorsRecordEntry.COLUMN_RECORDS_TIME + " = ?",
                                    new String[]{recordTimeString});

                    Toast.makeText(context, "您的申请未通过！请联系户主：15071345606。", Toast.LENGTH_LONG).show();
                }
            }

        }
    }

}

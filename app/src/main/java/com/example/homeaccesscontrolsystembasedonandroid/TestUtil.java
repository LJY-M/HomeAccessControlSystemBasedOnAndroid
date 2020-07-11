package com.example.homeaccesscontrolsystembasedonandroid;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class TestUtil {

    public static void getAllVisitorsRecord(SQLiteDatabase db) {

        System.out.println("Now in the TestUtil.getAllVisitorsRecord");

        String[] queryColumns = new String[] {
                VisitorsRecordContract.VisitorsRecordEntry.COLUMN_REMARK_INFO,
                VisitorsRecordContract.VisitorsRecordEntry.COLUMN_RECORDS_TIME,
                VisitorsRecordContract.VisitorsRecordEntry.COLUMN_APPLICATION_RESULT
        };

        Cursor cursor = db.query(
                VisitorsRecordContract.VisitorsRecordEntry.TABLE_NAME,
                queryColumns,
                null,
                null,
                null,
                null,
                VisitorsRecordContract.VisitorsRecordEntry.COLUMN_RECORDS_TIME
        );

        ArrayList<VisitorsRecord> visitorsRecordList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                visitorsRecordList.add(new VisitorsRecord(
                        cursor.getString(cursor.getColumnIndex(VisitorsRecordContract.VisitorsRecordEntry.COLUMN_REMARK_INFO)),
                        cursor.getString(cursor.getColumnIndex(VisitorsRecordContract.VisitorsRecordEntry.COLUMN_RECORDS_TIME)),
                        cursor.getString(cursor.getColumnIndex(VisitorsRecordContract.VisitorsRecordEntry.COLUMN_APPLICATION_RESULT))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();

        for (VisitorsRecord visitorsRecord : visitorsRecordList) {
            System.out.println(visitorsRecord.toString());
        }

    }
}

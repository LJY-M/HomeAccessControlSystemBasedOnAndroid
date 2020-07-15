package com.example.homeaccesscontrolsystembasedonandroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class VisitorsRecordDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "visitorsRecord1.db";
    private static final int DATABASE_VERSION = 3;

    public VisitorsRecordDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_RESIDENT_TABLE = "CREATE TABLE "
                + VisitorsRecordContract.VisitorsRecordEntry.TABLE_NAME + " ("
                + VisitorsRecordContract.VisitorsRecordEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + VisitorsRecordContract.VisitorsRecordEntry.COLUMN_VISITOR_IMAGE + " BLOB NOT NULL, "
                + VisitorsRecordContract.VisitorsRecordEntry.COLUMN_REMARK_INFO + " TEXT NOT NULL, "
                + VisitorsRecordContract.VisitorsRecordEntry.COLUMN_RECORDS_TIME + " TEXT NOT NULL, "
                + VisitorsRecordContract.VisitorsRecordEntry.COLUMN_APPLICATION_RESULT + " TEXT NOT NULL "
                + "); ";
        sqLiteDatabase.execSQL(SQL_CREATE_RESIDENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + VisitorsRecordContract.VisitorsRecordEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}

package com.example.homeaccesscontrolsystembasedonandroid;

import android.provider.BaseColumns;

public class VisitorsRecordContract {

    public static final class VisitorsRecordEntry implements BaseColumns {
        public static final String TABLE_NAME = "visitorsRecord";
        public static final String COLUMN_VISITOR_IMAGE = "visitorImage";
        public static final String COLUMN_REMARK_INFO = "remarkInfo";
        public static final String COLUMN_RECORDS_TIME = "recordsTime";
        public static final String COLUMN_APPLICATION_RESULT = "applicationResult";

        public static final String DEFAULT_RESULT = "default";
        public static final String DEFAULT_TIME = "default";
    }
}

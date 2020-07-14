package com.example.homeaccesscontrolsystembasedonandroid.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.arcsoft.face.enums.DetectFaceOrientPriority;

public class ConfigUtil {
    private static final String APP_NAME = "ArcFaceDemo";
    private static final String TRACKED_FACE_COUNT = "trackedFaceCount";
    private static final String FT_ORIENT = "ftOrientPriority";
    private static final String MAC_PRIORITY = "macPriority";

    private static final String PHONE_NUMBER = "phoneNumber";
    private static final String EMAIL_ADDRESS = "emailAddress";
    private static final String ADMIN_PASSWORD = "adminPassword";

    public static boolean setAdminPassword(Context context, String adminPassword) {
        if (context == null) {
            return false;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.edit()
                .putString(ADMIN_PASSWORD, adminPassword)
                .commit();
    }

    public static String getAdminPassword(Context context) {
        if (context == null) {
            return "";
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(ADMIN_PASSWORD, "0000");
    }

    public static boolean setPhoneNumber(Context context, String phoneNumber) {
        if (context == null) {
            return false;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.edit()
                .putString(PHONE_NUMBER, phoneNumber)
                .commit();
    }

    public static String getPhoneNumber(Context context) {
        if (context == null) {
            return "";
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PHONE_NUMBER, "");
    }

    public static boolean setEmailAddress(Context context, String emailAddress) {
        if (context == null) {
            return false;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.edit()
                .putString(EMAIL_ADDRESS, emailAddress)
                .commit();
    }

    public static String getEmailAddress(Context context) {
        if (context == null) {
            return "";
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(EMAIL_ADDRESS, "");
    }

    public static boolean setTrackedFaceCount(Context context, int trackedFaceCount) {
        if (context == null) {
            return false;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.edit()
                .putInt(TRACKED_FACE_COUNT, trackedFaceCount)
                .commit();
    }

    public static int getTrackedFaceCount(Context context) {
        if (context == null) {
            return 0;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(TRACKED_FACE_COUNT, 0);
    }

    public static boolean setFtOrient(Context context, DetectFaceOrientPriority ftOrient) {
        if (context == null) {
            return false;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.edit()
                .putString(FT_ORIENT, ftOrient.name())
                .commit();
    }

    public static DetectFaceOrientPriority getFtOrient(Context context) {
        if (context == null) {
            return DetectFaceOrientPriority.ASF_OP_270_ONLY;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE);
        return DetectFaceOrientPriority.valueOf(sharedPreferences.getString(FT_ORIENT, DetectFaceOrientPriority.ASF_OP_270_ONLY.name()));
    }
}

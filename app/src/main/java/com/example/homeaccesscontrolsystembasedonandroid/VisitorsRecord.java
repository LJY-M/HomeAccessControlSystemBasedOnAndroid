package com.example.homeaccesscontrolsystembasedonandroid;

import android.graphics.Bitmap;

public class VisitorsRecord {

    private Bitmap visitorImage;
    private String remarkInfo;
    private String recordsTime;
    private String applicationResult;

    public VisitorsRecord(String remarkInfo, String recordsTime, String applicationResult) {
        this.remarkInfo = remarkInfo;
        this.recordsTime = recordsTime;
        this.applicationResult = applicationResult;
    }

    public VisitorsRecord(Bitmap visitorImage, String remarkInfo, String recordsTime, String applicationResult) {
        this.visitorImage = visitorImage;
        this.remarkInfo = remarkInfo;
        this.recordsTime = recordsTime;
        this.applicationResult = applicationResult;
    }

    @Override
    public String toString() {
        return "VisitorsRecord{" +
//                "visitorImage=" + visitorImage +
                ", remarkInfo='" + remarkInfo + '\'' +
                ", recordsTime='" + recordsTime + '\'' +
                ", applicationResult='" + applicationResult + '\'' +
                '}';
    }

    public Bitmap getVisitorImage() {
        return visitorImage;
    }

    public void setVisitorImage(Bitmap visitorImage) {
        visitorImage = visitorImage;
    }

    public String getRemarkInfo() {
        return remarkInfo;
    }

    public void setRemarkInfo(String remarkInfo) {
        this.remarkInfo = remarkInfo;
    }

    public String getRecordsTime() {
        return recordsTime;
    }

    public void setRecordsTime(String recordsTime) {
        this.recordsTime = recordsTime;
    }

    public String getApplicationResult() {
        return applicationResult;
    }

    public void setApplicationResult(String applicationResult) {
        this.applicationResult = applicationResult;
    }
}

package com.hyt.moseaclass.model;

public class CourseSection {

    private int sId;

    private int sOrderId;

    private String sName;

    private String sUrl;

    public CourseSection(int sId, int sOrderId, String sName, String sUrl) {
        this.sId = sId;
        this.sOrderId = sOrderId;
        this.sName = sName;
        this.sUrl = sUrl;
    }

    public int getsId() {
        return sId;
    }

    public void setsId(int sId) {
        this.sId = sId;
    }

    public int getsOrderId() {
        return sOrderId;
    }

    public void setsOrderId(int sOrderId) {
        this.sOrderId = sOrderId;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsUrl() {
        return sUrl;
    }

    public void setsUrl(String sUrl) {
        this.sUrl = sUrl;
    }
}

package com.hyt.moseaclass.data;

public class LearningCourse {

    private int id;
    private int cid;
    private int pid;


    private String cName;
    private String cCover;
    private String uName;
    private String pTitle;
    private String lTime;

    public LearningCourse(int id, int cid, String cName, String cCover, String uName, String lTime) {
        this.id = id;
        this.cid = cid;
        this.cName = cName;
        this.cCover = cCover;
        this.uName = uName;
        this.lTime = lTime;
    }

    public LearningCourse(int id, int cid, int pid, String cName, String cCover, String uName, String pTitle, String lTime) {
        this.id = id;
        this.cid = cid;
        this.pid = pid;
        this.cName = cName;
        this.cCover = cCover;
        this.uName = uName;
        this.pTitle = pTitle;
        this.lTime = lTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcCover() {
        return cCover;
    }

    public void setcCover(String cCover) {
        this.cCover = cCover;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getpTitle() {
        return pTitle;
    }

    public void setpTitle(String pTitle) {
        this.pTitle = pTitle;
    }

    public String getlTime() {
        return lTime;
    }

    public void setlTime(String lTime) {
        this.lTime = lTime;
    }
}

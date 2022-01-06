package com.hyt.moseaclass.data.entity;

public class LearningCourse {

    private int id;
    private int cid;
    private int catalogueId;
    private int orderId;


    private String cName;
    private String cCover;
    private String cDesc;
    private String uName;
    private String pTitle;

    public LearningCourse(int id, int cid, int catalogueId, int orderId, String cName, String cCover, String uName, String cDesc) {
        this.id = id;
        this.cid = cid;
        this.catalogueId = catalogueId;
        this.orderId = orderId;
        this.cName = cName;
        this.cCover = cCover;
        this.uName = uName;
        this.cDesc = cDesc;
    }

    public LearningCourse(int id, int cid, String cName, String cCover, String uName, String cDesc) {
        this.id = id;
        this.cid = cid;
        this.cName = cName;
        this.cCover = cCover;
        this.uName = uName;
        this.cDesc = cDesc;
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

    public int getCatalogueId() {
        return catalogueId;
    }

    public void setCatalogueId(int catalogueId) {
        this.catalogueId = catalogueId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    public String getcDesc() {
        return cDesc;
    }

    public void setcDesc(String cDesc) {
        this.cDesc = cDesc;
    }

    @Override
    public String toString() {
        return "LearningCourse{" +
                "id=" + id +
                ", cid=" + cid +
                ", catalogueId=" + catalogueId +
                ", orderId=" + orderId +
                ", cName='" + cName + '\'' +
                ", cCover='" + cCover + '\'' +
                ", cDesc='" + cDesc + '\'' +
                ", uName='" + uName + '\'' +
                ", pTitle='" + pTitle + '\'' +
                '}';
    }
}

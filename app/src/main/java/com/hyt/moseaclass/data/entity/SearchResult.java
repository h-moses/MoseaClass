package com.hyt.moseaclass.data.entity;

import org.jetbrains.annotations.NotNull;

public class SearchResult {

    private int cid;

    private String cName;

    private String cDesc;

    private String cCover;

    public SearchResult(int cid, String cName, String cDesc, String cCover) {
        this.cid = cid;
        this.cName = cName;
        this.cDesc = cDesc;
        this.cCover = cCover;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcDesc() {
        return cDesc;
    }

    public void setcDesc(String cDesc) {
        this.cDesc = cDesc;
    }

    public String getcCover() {
        return cCover;
    }

    public void setcCover(String cCover) {
        this.cCover = cCover;
    }

    @NotNull
    @Override
    public String toString() {
        return "SearchResult{" +
                "cid=" + cid +
                ", cName='" + cName + '\'' +
                ", cDesc='" + cDesc + '\'' +
                ", cCover='" + cCover + '\'' +
                '}';
    }
}

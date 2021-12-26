package com.hyt.moseaclass.data.entity;

import java.util.ArrayList;
import java.util.List;

public class CourseChapter {

    private int cId;

    private String cName;

    private List<CourseSection> sectionList = new ArrayList<>();

    public CourseChapter(int cId, String cName) {
        this.cId = cId;
        this.cName = cName;
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public List<CourseSection> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<CourseSection> sectionList) {
        this.sectionList = sectionList;
    }
}

package com.hyt.moseaclass.data.entity;

/*
 * 课程评价
 * */
public class CourseComment {

    private int id;
    private int cId;
    private int uId;
    private int rating;
    private String uName;
    private String uAvatar;
    private String comment;
    private String cDate;

    public CourseComment(int id, int cId, int uId, int rating, String uName, String uAvatar, String comment, String cDate) {
        this.id = id;
        this.cId = cId;
        this.uId = uId;
        this.rating = rating;
        this.uName = uName;
        this.uAvatar = uAvatar;
        this.comment = comment;
        this.cDate = cDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuAvatar() {
        return uAvatar;
    }

    public void setuAvatar(String uAvatar) {
        this.uAvatar = uAvatar;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getcDate() {
        return cDate;
    }

    public void setcDate(String cDate) {
        this.cDate = cDate;
    }

    @Override
    public String toString() {
        return "CourseComment{" +
                "id=" + id +
                ", cId=" + cId +
                ", uId=" + uId +
                ", rating=" + rating +
                ", uName='" + uName + '\'' +
                ", uAvatar='" + uAvatar + '\'' +
                ", comment='" + comment + '\'' +
                ", cDate='" + cDate + '\'' +
                '}';
    }
}

package com.hyt.moseaclass.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "userInfo")
public class UserInfo {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer id;
    @ColumnInfo(name = "u_id")
    private Integer uid;
    @ColumnInfo(name = "u_name")
    private String uName;
    @ColumnInfo(name = "u_phone")
    private String uPhone;
    @ColumnInfo(name = "u_avatar")
    private String uAvatar;
    @ColumnInfo(name = "u_login")
    private Integer isLogin;

    public UserInfo(int uid, String uName, String uPhone, String uAvatar) {
        this.uid = uid;
        this.uName = uName;
        this.uPhone = uPhone;
        this.uAvatar = uAvatar;
        this.isLogin = 0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUName() {
        return uName;
    }

    public void setUName(String uName) {
        this.uName = uName;
    }

    public String getUPhone() {
        return uPhone;
    }

    public void setUPhone(String uPhone) {
        this.uPhone = uPhone;
    }

    public String getUAvatar() {
        return uAvatar;
    }

    public void setUAvatar(String uAvatar) {
        this.uAvatar = uAvatar;
    }

    public Integer getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(Integer login) {
        this.isLogin = login;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", uid=" + uid +
                ", uName='" + uName + '\'' +
                ", uPhone='" + uPhone + '\'' +
                ", uAvatar='" + uAvatar + '\'' +
                ", isLogin=" + isLogin +
                '}';
    }
}

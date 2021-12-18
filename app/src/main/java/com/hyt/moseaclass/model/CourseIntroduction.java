package com.hyt.moseaclass.model;

import android.net.Uri;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

public class CourseIntroduction {
    private int cid;

    private String cName;
    private String cImage;
    private String cInstructor;

    public CourseIntroduction(int cid, String cName, String cImage, String cInstructor) {
        this.cid = cid;
        this.cName = cName;
        this.cImage = cImage;
        this.cInstructor = cInstructor;
    }

    public CourseIntroduction(String cName, String cImage, String cInstructor) {
        this.cName = cName;
        this.cImage = cImage;
        this.cInstructor = cInstructor;
    }

    @BindingAdapter({"imageUrl"})
    public static void setImageUrl(ImageView view, String uri) {
        Picasso.get().load(Uri.parse(uri)).into(view);
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

    public String getcImage() {
        return cImage;
    }

    public void setcImage(String cImage) {
        this.cImage = cImage;
    }

    public String getcInstructor() {
        return cInstructor;
    }

    public void setcInstructor(String cInstructor) {
        this.cInstructor = cInstructor;
    }

    @Override
    public String toString() {
        return "CourseIntroduction{" +
                "cid=" + cid +
                ", cName='" + cName + '\'' +
                ", cImage='" + cImage + '\'' +
                ", cInstructor='" + cInstructor + '\'' +
                '}';
    }
}

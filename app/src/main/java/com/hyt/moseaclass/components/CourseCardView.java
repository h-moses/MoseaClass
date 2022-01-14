package com.hyt.moseaclass.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hyt.moseaclass.databinding.ViewCourseCardBinding;

/*
 * 课程卡片
 * */
public class CourseCardView extends RelativeLayout {

    private final Context mContext;


    public CourseCardView(@NonNull Context context) {
        super(context, null, 0);
        this.mContext = context;
        init();
    }

    public CourseCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public CourseCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    private void init() {
        ViewCourseCardBinding.inflate(LayoutInflater.from(mContext), this, true);
    }

}

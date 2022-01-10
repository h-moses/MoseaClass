package com.hyt.moseaclass.components;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hyt.moseaclass.data.entity.CourseIntroduction;
import com.hyt.moseaclass.databinding.ViewCourseCardBinding;
import com.hyt.moseaclass.state.UserContext;
import com.hyt.moseaclass.ui.course.CourseIntroductionActivity;
import com.hyt.moseaclass.utils.SharedPreferenceUtils;
import com.squareup.picasso.Picasso;

public class CourseCardView extends RelativeLayout {

    private static final String TAG = CourseCardView.class.getSimpleName();
    private final Context mContext;

    private ViewCourseCardBinding binding;

    private CourseIntroduction introduction;

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
        binding = ViewCourseCardBinding.inflate(LayoutInflater.from(mContext), this, true);
    }

    public void setData(int id, String uri, String title, String teacher, String desc) {
        introduction = new CourseIntroduction(id, title, uri, teacher, desc);
        Picasso.get().load(uri).into(binding.courseImage);
        binding.courseTitle.setText(title);
        binding.courseTeacher.setText(teacher);
    }

}

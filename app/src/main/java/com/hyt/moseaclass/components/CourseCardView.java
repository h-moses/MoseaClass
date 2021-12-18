package com.hyt.moseaclass.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;

import com.hyt.moseaclass.databinding.ViewCourseCardBinding;
import com.hyt.moseaclass.model.CourseIntroduction;
import com.squareup.picasso.Picasso;

public class CourseCardView extends RelativeLayout {

    private final Context mContext;

    private ViewCourseCardBinding binding;


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

    public void setData(String uri, String title, String teacher) {
        Picasso.get().load(uri).into(binding.courseImage);
        binding.courseTitle.setText(title);
        binding.courseTeacher.setText(teacher);
    }
}

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

import java.util.Objects;

public class CourseCardView extends RelativeLayout {

    private final Context mContext;

    private ViewCourseCardBinding binding;

    private ObservableField<CourseIntroduction> introduction;

    public CourseCardView(@NonNull Context context, ViewGroup parent) {
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
        this.introduction = new ObservableField<>();
    }

    public void setData(String uri, String title, String teacher) {
        Objects.requireNonNull(introduction.get()).setcImage(uri);
        Objects.requireNonNull(introduction.get()).setcName(title);
        Objects.requireNonNull(introduction.get()).setcInstructor(teacher);
    }
}

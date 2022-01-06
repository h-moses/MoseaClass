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
import com.hyt.moseaclass.ui.course.CourseIntroductionActivity;
import com.hyt.moseaclass.utils.SharedPreferenceUtils;
import com.squareup.picasso.Picasso;

public class CourseCardView extends RelativeLayout implements View.OnClickListener {

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
        binding.courseCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), CourseIntroductionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("cid", introduction.getCid());
        bundle.putString("title", introduction.getcName());
        bundle.putString("cover", introduction.getcImage());
        bundle.putString("uName", introduction.getcInstructor());
        bundle.putString("desc", introduction.getcDesc());
        SharedPreferenceUtils.clear(getContext(), SharedPreferenceUtils.COURSE_FILE);
        SharedPreferenceUtils.setInteger(getContext(), SharedPreferenceUtils.COURSE_FILE,"cid", introduction.getCid());
        SharedPreferenceUtils.setString(getContext(),SharedPreferenceUtils.COURSE_FILE, "title", introduction.getcName());
        SharedPreferenceUtils.setString(getContext(),SharedPreferenceUtils.COURSE_FILE, "desc", introduction.getcDesc());
        intent.putExtra("data",bundle);
        mContext.startActivity(intent);
    }
}

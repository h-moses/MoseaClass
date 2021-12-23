package com.hyt.moseaclass.components;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hyt.moseaclass.data.CourseIntroduction;
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
//        Bundle bundle = new Bundle();
//        bundle.putInt("id", introduction.getCid());
//        bundle.putString("desc", introduction.getcDesc());
//        bundle.putString("image", introduction.getcImage());
//        bundle.putString("name", introduction.getcName());
//        bundle.putString("teacher", introduction.getcInstructor());
//        intent.putExtra("course", bundle);
        SharedPreferenceUtils.clear(getContext());
        SharedPreferenceUtils.setInteger(getContext(), "id", introduction.getCid());
        SharedPreferenceUtils.setString(getContext(), "name", introduction.getcName());
        SharedPreferenceUtils.setString(getContext(), "image", introduction.getcImage());
        SharedPreferenceUtils.setString(getContext(), "teacher", introduction.getcInstructor());
        SharedPreferenceUtils.setString(getContext(), "desc", introduction.getcDesc());
        mContext.startActivity(intent);
    }
}

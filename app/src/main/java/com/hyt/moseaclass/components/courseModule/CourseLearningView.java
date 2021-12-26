package com.hyt.moseaclass.components.courseModule;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.hyt.moseaclass.databinding.ViewCourseLearningBinding;

public class CourseLearningView extends ConstraintLayout {


    private final Context mContext;
    private ViewCourseLearningBinding binding;

    public CourseLearningView(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    public CourseLearningView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = null;
        binding = ViewCourseLearningBinding.inflate(LayoutInflater.from(mContext), this, true);
    }

    public CourseLearningView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = null;
    }

//    public void setData(String url, String name, String instructor, String process) {
//        Picasso.get().load(Uri.parse(url)).into(binding.learningCourseImage);
//        binding.learningCourseName.setText(name);
//        binding.learningCourseTeacher.setText(instructor);
//        String format = String.format(Locale.CHINA, PROCESS_TEMPLATE, process);
//        CharSequence charSequence;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//            charSequence = Html.fromHtml(format, Html.FROM_HTML_MODE_LEGACY);
//        } else {
//            charSequence = Html.fromHtml(format);
//        }
//        binding.learningCourseProcess.setText(charSequence);
//    }

}

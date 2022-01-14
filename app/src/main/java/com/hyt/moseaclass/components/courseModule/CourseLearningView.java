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

    public CourseLearningView(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    public CourseLearningView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = null;
        ViewCourseLearningBinding.inflate(LayoutInflater.from(mContext), this, true);
    }

    public CourseLearningView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = null;
    }
}

package com.hyt.moseaclass.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.hyt.moseaclass.databinding.ViewCourseAlbumBinding;
import com.hyt.moseaclass.model.CourseIntroduction;
import com.hyt.moseaclass.utils.ViewUtils;

import java.util.List;

public class CourseAlbumView extends LinearLayout {

    private final Context mContext;

    private ViewCourseAlbumBinding binding;

    private GridLayout courseGrid;

    private TextView albumName;

    public CourseAlbumView(Context context, List<CourseIntroduction> list) {
        super(context, null, 0);
        this.mContext = context;
        init();
    }

    public CourseAlbumView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        this.mContext = context;
        init();
    }

    public CourseAlbumView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        binding = ViewCourseAlbumBinding.inflate(LayoutInflater.from(mContext), this, true);
        albumName = binding.categoryName;
        albumName.setText("热门推荐");
        courseGrid = binding.courseGrid;
    }

    public void setCourseAlbumData(List<CourseIntroduction> list) {
        ViewUtils.initGridLayout(mContext, (ViewGroup) getParent(), courseGrid, 2, 4, list);
    }
}

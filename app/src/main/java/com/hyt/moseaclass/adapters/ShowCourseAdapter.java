package com.hyt.moseaclass.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hyt.moseaclass.data.entity.CourseIntroduction;
import com.hyt.moseaclass.databinding.ViewCourseCardBinding;
import com.hyt.moseaclass.state.UserContext;
import com.hyt.moseaclass.ui.course.CourseIntroductionActivity;
import com.hyt.moseaclass.utils.SharedPreferenceUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/*
 * 课程卡片适配器
 * */
public class ShowCourseAdapter extends RecyclerView.Adapter<ShowCourseAdapter.ViewHolder> {

    private final Context mContext;
    private ViewCourseCardBinding binding;
    private List<CourseIntroduction> courseIntroductions = new ArrayList<>();

    public ShowCourseAdapter(Context mContext, List<CourseIntroduction> courseIntroductions) {
        this.mContext = mContext;
//        清空列表
        this.courseIntroductions.clear();
//        添加所有数据
        this.courseIntroductions.addAll(courseIntroductions);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ViewCourseCardBinding.inflate(LayoutInflater.from(mContext), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        获取对应位置的数据
        CourseIntroduction courseIntroduction = courseIntroductions.get(position);
        if (!courseIntroduction.getcImage().equals("")) { // 课程有封面
            Picasso.get().load(courseIntroduction.getcImage()).into(holder.getBinding().courseImage);
        }
        binding.courseTitle.setText(courseIntroduction.getcName());
        binding.courseTeacher.setText(courseIntroduction.getcInstructor());
//        卡片的点击事件
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                跳转至详情页面
                Intent intent = new Intent(mContext, CourseIntroductionActivity.class);
                Bundle bundle = new Bundle();
//                携带课程信息
                bundle.putInt("cid", courseIntroduction.getCid());
                bundle.putString("title", courseIntroduction.getcName());
                bundle.putString("cover", courseIntroduction.getcImage());
                bundle.putString("uName", courseIntroduction.getcInstructor());
                bundle.putString("desc", courseIntroduction.getcDesc());
//                将部分课程信息设置为共享数据
                SharedPreferenceUtils.clear(mContext, SharedPreferenceUtils.COURSE_FILE);
                SharedPreferenceUtils.setInteger(mContext, SharedPreferenceUtils.COURSE_FILE, UserContext.KEY_CID, courseIntroduction.getCid());
                SharedPreferenceUtils.setString(mContext, SharedPreferenceUtils.COURSE_FILE, "title", courseIntroduction.getcName());
                SharedPreferenceUtils.setString(mContext, SharedPreferenceUtils.COURSE_FILE, "desc", courseIntroduction.getcDesc());
                intent.putExtra("data", bundle);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return courseIntroductions.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ViewCourseCardBinding binding;

        public ViewHolder(@NonNull ViewCourseCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ViewCourseCardBinding getBinding() {
            return binding;
        }
    }
}

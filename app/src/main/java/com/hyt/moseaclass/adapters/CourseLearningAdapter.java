package com.hyt.moseaclass.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hyt.moseaclass.data.entity.LearningCourse;
import com.hyt.moseaclass.databinding.ViewCourseLearningBinding;
import com.hyt.moseaclass.state.UserContext;
import com.hyt.moseaclass.ui.course.CourseIntroductionActivity;
import com.hyt.moseaclass.utils.SharedPreferenceUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/*
 * 已学习课程的适配器
 * */
public class CourseLearningAdapter extends RecyclerView.Adapter<CourseLearningAdapter.ViewHolder> {

    private final Context mContext;
    private List<LearningCourse> learningCourseList = new ArrayList<>();

    public CourseLearningAdapter(List<LearningCourse> learningCourseList, Context mContext) {
        this.learningCourseList = learningCourseList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewCourseLearningBinding binding = ViewCourseLearningBinding.inflate(LayoutInflater.from(mContext), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        取出数据并显示在页面上
        Picasso.get().load(Uri.parse(learningCourseList.get(position).getcCover())).into(holder.getBinding().learningCourseImage);
        holder.getBinding().learningCourseName.setText(learningCourseList.get(position).getcName());
        holder.getBinding().learningCourseTeacher.setText(learningCourseList.get(position).getuName());
//        课程卡片的点击事件
        holder.getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
//                将下列数据设置为共享
                SharedPreferenceUtils.clear(mContext, SharedPreferenceUtils.COURSE_FILE);
                SharedPreferenceUtils.setInteger(mContext, SharedPreferenceUtils.COURSE_FILE, "id", learningCourseList.get(position).getId());
                SharedPreferenceUtils.setInteger(mContext, SharedPreferenceUtils.COURSE_FILE, UserContext.KEY_CID, learningCourseList.get(position).getCid());
                SharedPreferenceUtils.setInteger(mContext, SharedPreferenceUtils.COURSE_FILE, "catalogue_id", learningCourseList.get(position).getCatalogueId());
                SharedPreferenceUtils.setInteger(mContext, SharedPreferenceUtils.COURSE_FILE, "order_id", learningCourseList.get(position).getOrderId());
                SharedPreferenceUtils.setString(mContext, SharedPreferenceUtils.COURSE_FILE, "title", learningCourseList.get(position).getcName());
                SharedPreferenceUtils.setString(mContext, SharedPreferenceUtils.COURSE_FILE, "desc", learningCourseList.get(position).getcDesc());
//                intent携带下列数据
                bundle.putInt("cid", learningCourseList.get(position).getCid());
                bundle.putString("title", learningCourseList.get(position).getcName());
                bundle.putString("cover", learningCourseList.get(position).getcCover());
                bundle.putString("desc", learningCourseList.get(position).getcDesc());
                bundle.putString("uName", learningCourseList.get(position).getuName());
                Intent intent = new Intent(mContext, CourseIntroductionActivity.class);
                intent.putExtra("data", bundle);
                intent.putExtra("isLearning", true);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return learningCourseList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ViewCourseLearningBinding binding;

        public ViewHolder(@NonNull ViewCourseLearningBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ViewCourseLearningBinding getBinding() {
            return binding;
        }
    }
}

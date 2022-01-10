package com.hyt.moseaclass.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hyt.moseaclass.data.entity.CourseIntroduction;
import com.hyt.moseaclass.databinding.ViewCourseCardBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ShowCourseAdapter extends RecyclerView.Adapter<ShowCourseAdapter.ViewHolder> {

    private final Context mContext;
    private ViewCourseCardBinding binding;
    private List<CourseIntroduction> courseIntroductions = new ArrayList<>();

    public ShowCourseAdapter(Context mContext, List<CourseIntroduction> courseIntroductions) {
        this.mContext = mContext;
        this.courseIntroductions.clear();
        this.courseIntroductions.addAll(courseIntroductions);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ViewCourseCardBinding.inflate(LayoutInflater.from(mContext),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(courseIntroductions.get(position).getcImage()).into(holder.getBinding().courseImage);
        binding.courseTitle.setText(courseIntroductions.get(position).getcName());
        binding.courseTeacher.setText(courseIntroductions.get(position).getcInstructor());
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

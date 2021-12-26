package com.hyt.moseaclass.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hyt.moseaclass.data.entity.LearningCourse;
import com.hyt.moseaclass.databinding.ViewCourseLearningBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CourseLearningAdapter extends RecyclerView.Adapter<CourseLearningAdapter.ViewHolder> {
    private static final String PROCESS_TEMPLATE = "已学到<font color = '#00CC7E'>%s</font>";
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
        Picasso.get().load(Uri.parse(learningCourseList.get(position).getcCover())).into(holder.getBinding().learningCourseImage);
        holder.getBinding().learningCourseName.setText(learningCourseList.get(position).getcName());
        holder.getBinding().learningCourseTeacher.setText(learningCourseList.get(position).getuName());
//        if (learningCourseList.get(position).getpTitle() != null) {
//            String format = String.format(Locale.CHINA, PROCESS_TEMPLATE, learningCourseList.get(position).getpTitle());
//            CharSequence charSequence;
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//                charSequence = Html.fromHtml(format, Html.FROM_HTML_MODE_LEGACY);
//            } else {
//                charSequence = Html.fromHtml(format);
//            }
//            holder.getBinding().learningCourseProcess.setText(charSequence);
//        }
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

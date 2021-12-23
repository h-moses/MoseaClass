package com.hyt.moseaclass.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hyt.moseaclass.data.CourseComment;
import com.hyt.moseaclass.databinding.ViewCommentBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private final Context mContext;
    private final List<CourseComment> commentList = new ArrayList<>();
    private ViewCommentBinding binding;


    public CommentAdapter(Context mContext, List<CourseComment> commentList) {
        this.mContext = mContext;
        this.commentList.addAll(commentList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ViewCommentBinding.inflate(LayoutInflater.from(mContext), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Picasso.get().load(Uri.parse(commentList.get(position).getuAvatar())).into(binding.rivUserAvatar);
        binding.tvUserName.setText(commentList.get(position).getuName());
        binding.tvCommentDate.setText("2021-12-22");
        binding.tvUserComment.setText(commentList.get(position).getComment());
        binding.rbUserRating.setRating((float) (commentList.get(position).getRating() / 2));
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ViewCommentBinding binding;

        public ViewHolder(@NonNull ViewCommentBinding binding) {
            super(binding.getRoot());
        }

        public ViewCommentBinding getBinding() {
            return binding;
        }
    }
}

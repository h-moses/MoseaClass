package com.hyt.moseaclass.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hyt.moseaclass.R;
import com.hyt.moseaclass.components.OptionView;
import com.hyt.moseaclass.data.entity.TestQuestion;
import com.hyt.moseaclass.databinding.ItemAnswerBinding;

import java.util.ArrayList;
import java.util.List;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {

    private List<TestQuestion> questionList = new ArrayList<>();
    @DrawableRes
    private final int[] singleOptions = {R.drawable.optiona, R.drawable.optionb, R.drawable.optionc, R.drawable.optiond};
    @DrawableRes
    private final int[] judgeOptions = {R.drawable.ic_correct, R.drawable.ic_fault};
    private final Context mContext;

    public AnswerAdapter(Context mContext,List<TestQuestion> questions) {
        this.mContext = mContext;
        this.questionList = questions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAnswerBinding binding = ItemAnswerBinding.inflate(LayoutInflater.from(mContext),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getBinding().questionTitle.setText(questionList.get(position).getQuestion());
        List<String> options = questionList.get(position).getOptions();
        if (questionList.get(position).getType().equals("single")) {
            for (int i = 0; i < options.size(); i++) {
                OptionView optionView = new OptionView(mContext);
                optionView.setData(singleOptions[i], options.get(i));
                holder.getBinding().optionsLayout.addView(optionView);
            }
        } else {
            for (int i = 0; i < options.size(); i++) {
                OptionView optionView = new OptionView(mContext);
                optionView.setData(judgeOptions[i], options.get(i));
                holder.getBinding().optionsLayout.addView(optionView);
            }
        }
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemAnswerBinding binding;

        public ViewHolder(@NonNull ItemAnswerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ItemAnswerBinding getBinding() {
            return binding;
        }
    }
}

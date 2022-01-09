package com.hyt.moseaclass.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.hyt.moseaclass.R;
import com.hyt.moseaclass.data.entity.TestQuestion;
import com.hyt.moseaclass.databinding.ItemAnswerBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {

    private static final String TAG = AnswerAdapter.class.getSimpleName();
    private final Map<String, Integer> singleMap = new HashMap<>();
    private final Map<String, Integer> judgeMap = new HashMap<>();
    private List<TestQuestion> questionList = new ArrayList<>();

    private final Context mContext;

    private int totalScore = 0;

    public AnswerAdapter(Context mContext,List<TestQuestion> questions) {
        this.mContext = mContext;
        this.questionList = questions;
        singleMap.put("A",0);
        singleMap.put("B",1);
        singleMap.put("C",2);
        singleMap.put("D",3);
        judgeMap.put("正确",0);
        judgeMap.put("错误",1);
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
                RadioButton radioButton = new RadioButton(mContext);
                radioButton.setText(options.get(i));
                radioButton.setTextColor(ContextCompat.getColorStateList(mContext, R.color.radio_btn_text_selector));
                holder.getBinding().optionsGroup.addView(radioButton);
            }
        } else {
            for (int i = 0; i < options.size(); i++) {
                RadioButton radioButton = new RadioButton(mContext);
                radioButton.setText(options.get(i));
                radioButton.setTextColor(ContextCompat.getColorStateList(mContext,R.color.radio_btn_text_selector));
                holder.getBinding().optionsGroup.addView(radioButton);
            }
        }
        holder.getBinding().optionsGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getChildCount() == 2) {
                    if (i == radioGroup.getChildAt(judgeMap.get(questionList.get(position).getAnswer())).getId()) {
                        totalScore += questionList.get(position).getScore();
                    }
                } else if (radioGroup.getChildCount() == 4) {
                    if (i == radioGroup.getChildAt(singleMap.get(questionList.get(position).getAnswer())).getId()) {
                        totalScore += questionList.get(position).getScore();
                    }
                }
                Log.e(TAG, "onCheckedChanged: " + totalScore);
            }
        });
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public int calcScore() {
        return totalScore;
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

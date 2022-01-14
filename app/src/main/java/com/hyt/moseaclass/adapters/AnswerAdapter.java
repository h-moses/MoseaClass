package com.hyt.moseaclass.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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

/*
 * 答题适配器
 * */
public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {

    //    答案和选项索引对应字典
    private final Map<String, Integer> singleMap = new HashMap<>();
    private final Map<String, Integer> judgeMap = new HashMap<>();

    private final Context mContext;
    //    题目数组
    private List<TestQuestion> questionList = new ArrayList<>();
    //    获得总分
    private int totalScore = 0;

    public AnswerAdapter(Context mContext, List<TestQuestion> questions) {
        this.mContext = mContext;
        this.questionList = questions;
//        将答案转换为索引
        singleMap.put("A", 0);
        singleMap.put("B", 1);
        singleMap.put("C", 2);
        singleMap.put("D", 3);
        judgeMap.put("正确", 0);
        judgeMap.put("错误", 1);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAnswerBinding binding = ItemAnswerBinding.inflate(LayoutInflater.from(mContext), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getBinding().questionTitle.setText(questionList.get(position).getQuestion());
        List<String> options = questionList.get(position).getOptions();
//        使用RadioGroup动态添加单选项
        for (int i = 0; i < options.size(); i++) {
            RadioButton radioButton = new RadioButton(mContext);
            radioButton.setText(options.get(i));
            radioButton.setTextColor(ContextCompat.getColorStateList(mContext, R.color.radio_btn_text_selector));
            holder.getBinding().optionsGroup.addView(radioButton);
        }

//        单选组的选中变化事件
        holder.getBinding().optionsGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getChildCount() == 2) { // 该题为判断题，只有两个选项
                    if (i == radioGroup.getChildAt(judgeMap.get(questionList.get(position).getAnswer())).getId()) { // 答案匹配,则添加分数
                        totalScore += questionList.get(position).getScore();
                    }
                } else if (radioGroup.getChildCount() == 4) { // 该题为单选题，四个选项
                    if (i == radioGroup.getChildAt(singleMap.get(questionList.get(position).getAnswer())).getId()) {
                        totalScore += questionList.get(position).getScore();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    //    返回总分数
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

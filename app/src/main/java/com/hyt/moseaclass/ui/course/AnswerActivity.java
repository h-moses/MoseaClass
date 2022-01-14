package com.hyt.moseaclass.ui.course;

import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hyt.moseaclass.R;
import com.hyt.moseaclass.adapters.AnswerAdapter;
import com.hyt.moseaclass.data.entity.TestQuestion;
import com.hyt.moseaclass.databinding.ActivityAnswerBinding;

import java.util.List;
import java.util.Objects;

/*
 * 答题页面
 * */
public class AnswerActivity extends AppCompatActivity {

    private List<TestQuestion> questionList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityAnswerBinding binding = ActivityAnswerBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

//        设置应用栏
        setSupportActionBar(binding.answerToolbar);
//        显示标题
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
//        显示返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        处理intent
        handleIntent();
//        修改返回按钮颜色
        setCustomNavigationIcon(R.color.white);

//        设置适配器
        AnswerAdapter answerAdapter = new AnswerAdapter(this, questionList);
        binding.answerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.answerRecyclerView.setAdapter(answerAdapter);

//        提交按钮添加点击事件
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                获取答题总分
                int score = answerAdapter.calcScore();
//                构建显示对话框
                new AlertDialog.Builder(view.getContext()).setTitle("测试得分").setMessage("恭喜你，本次测试得分为".concat(String.valueOf(score))).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
//                不可再次提交
                binding.btnSubmit.setEnabled(false);
//                修改按钮样式
                binding.btnSubmit.setText("已提交");
                binding.btnSubmit.setTextColor(view.getResources().getColor(R.color.gray_text));
                binding.btnSubmit.setBackgroundColor(view.getResources().getColor(R.color.gray_back));
            }
        });
    }

    private void handleIntent() {
        questionList = (List<TestQuestion>) getIntent().getSerializableExtra("data");
    }

    /*
     * 根据状态栏的展开折叠修改返回按钮的颜色
     * */
    private void setCustomNavigationIcon(int color) {
        Drawable upArrow = ContextCompat.getDrawable(this, androidx.appcompat.R.drawable.abc_ic_ab_back_material);
        if (upArrow != null) {
            upArrow.setColorFilter(ContextCompat.getColor(this, color), PorterDuff.Mode.SRC_ATOP);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setHomeAsUpIndicator(upArrow);
            }
        }
    }

}

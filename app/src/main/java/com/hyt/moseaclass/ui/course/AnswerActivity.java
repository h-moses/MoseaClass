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

public class AnswerActivity extends AppCompatActivity {

    private static final String TAG = AnswerActivity.class.getSimpleName();
    private List<TestQuestion> questionList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityAnswerBinding binding = ActivityAnswerBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        setSupportActionBar(binding.answerToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        handleIntent();
        setCustomNavigationIcon(R.color.white);

        AnswerAdapter answerAdapter = new AnswerAdapter(this, questionList);
        binding.answerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.answerRecyclerView.setAdapter(answerAdapter);

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int score = answerAdapter.calcScore();
                new AlertDialog.Builder(view.getContext()).setTitle("测试得分").setMessage("恭喜你，本次测试得分为".concat(String.valueOf(score))).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
                binding.btnSubmit.setEnabled(false);
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

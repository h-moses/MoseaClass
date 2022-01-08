package com.hyt.moseaclass.ui.course;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

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

        binding.answerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.answerRecyclerView.setAdapter(new AnswerAdapter(this,questionList));
    }

    private void handleIntent() {
        questionList = (List<TestQuestion>) getIntent().getSerializableExtra("data");
    }
}

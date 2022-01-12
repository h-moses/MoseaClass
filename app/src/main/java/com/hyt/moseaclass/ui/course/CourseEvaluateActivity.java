package com.hyt.moseaclass.ui.course;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hyt.moseaclass.databinding.ActivityEvaluateBinding;
import com.hyt.moseaclass.state.UserContext;
import com.hyt.moseaclass.utils.OkHttpUtils;
import com.hyt.moseaclass.utils.SharedPreferenceUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class CourseEvaluateActivity extends AppCompatActivity {

    private String courseName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent();

        ActivityEvaluateBinding binding = ActivityEvaluateBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        binding.courseName.setText(courseName);

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float rating = binding.evaluateRate.getRating();
                String content = binding.evaluateContent.getText().toString();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("user_id", SharedPreferenceUtils.getInteger(view.getContext(),SharedPreferenceUtils.LOGIN_STATE, UserContext.KEY_UID, Integer.MIN_VALUE));
                    jsonObject.put("course_id",SharedPreferenceUtils.getInteger(view.getContext(),SharedPreferenceUtils.COURSE_FILE,UserContext.KEY_CID,Integer.MIN_VALUE));
                    jsonObject.put("total_star",rating*2);
                    jsonObject.put("content",content);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONObject postjson = OkHttpUtils.postjson("http://101.133.173.40:8090/edusys/course/addCourseEvaluation", jsonObject.toString());
                if (postjson != null) {
                    Toast.makeText(view.getContext(),"评价成功！",Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(view.getContext(),"评价失败！",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void handleIntent() {
        courseName = getIntent().getStringExtra("courseName");
    }
}

package com.hyt.moseaclass.ui.course;

import android.os.Bundle;
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

/*
 * 课程评价活动
 * */
public class CourseEvaluateActivity extends AppCompatActivity {

    //    课程名称
    private String courseName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        处理intent
        handleIntent();

        ActivityEvaluateBinding binding = ActivityEvaluateBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

//        设置要评价的课程名
        binding.courseName.setText(courseName);

//        提交按钮的点击事件
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                获取评价的星级和内容
                float rating = binding.evaluateRate.getRating();
                String content = binding.evaluateContent.getText().toString();
                JSONObject jsonObject = new JSONObject();
                try {
//                    放入jsonobject中
                    jsonObject.put("user_id", SharedPreferenceUtils.getInteger(view.getContext(), SharedPreferenceUtils.LOGIN_STATE, UserContext.KEY_UID, Integer.MIN_VALUE));
                    jsonObject.put("course_id", SharedPreferenceUtils.getInteger(view.getContext(), SharedPreferenceUtils.COURSE_FILE, UserContext.KEY_CID, Integer.MIN_VALUE));
                    jsonObject.put("total_star", rating * 2);
                    jsonObject.put("content", content);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                发送网络请求
                JSONObject postjson = OkHttpUtils.postjson("http://101.133.173.40:8090/edusys/course/addCourseEvaluation", jsonObject.toString());
                if (postjson != null) {
                    Toast.makeText(view.getContext(), "评价成功！", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(view.getContext(), "评价失败！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /*
     * 获取课程名
     * */
    private void handleIntent() {
        courseName = getIntent().getStringExtra("courseName");
    }
}

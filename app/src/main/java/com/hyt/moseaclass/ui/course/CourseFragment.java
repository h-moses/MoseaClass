package com.hyt.moseaclass.ui.course;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hyt.moseaclass.adapters.CourseLearningAdapter;
import com.hyt.moseaclass.data.entity.LearningCourse;
import com.hyt.moseaclass.databinding.FragmentCourseBinding;
import com.hyt.moseaclass.utils.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.FormBody;

public class CourseFragment extends Fragment {

    private static final String COURSE_COUNT_TEMPLATE = "课程(%d)";
    private static final String TAG = CourseFragment.class.getSimpleName();

    private FragmentCourseBinding binding;

    private List<LearningCourse> learningCourseList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            initData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCourseBinding.inflate(inflater, container, false);
        binding.tvCourseLearningCount.setText(String.format(Locale.CHINA, COURSE_COUNT_TEMPLATE, learningCourseList.size()));
        binding.learningCourseRecyclerView.setAdapter(new CourseLearningAdapter(learningCourseList, getContext()));
        binding.learningCourseRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return binding.getRoot();
    }


    private void initData() throws JSONException {
        learningCourseList = new ArrayList<>();
        LearningCourse learningCourse = null;
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("uid", "1");
        JSONArray array = OkHttpUtils.post("http://101.133.173.40:8090/edusys/course/getLearntCourse?", builder.build());
        Log.e(TAG, "initData: " + array.length());
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            int id = jsonObject.getInt("id");
            int cid = jsonObject.getJSONObject("course").getInt("cid");
            String cName = jsonObject.getJSONObject("course").getString("title");
            String cover = jsonObject.getJSONObject("course").getString("cover");
//            int uid = jsonObject.getJSONObject("course").getJSONObject("user").getInt("uid");
            String uName = jsonObject.getJSONObject("course").getJSONObject("user").getString("nick_name");
            String learn_time = jsonObject.getString("learn_time");
            learningCourse = new LearningCourse(id, cid, cName, cover, uName, learn_time);
//            JSONObject current_video = jsonObject.getJSONObject("current_video");
//            if (current_video != null) {
//                int pid = current_video.getInt("id");
//                String pTitle = current_video.getString("title");
//                learningCourse = new LearningCourse(id, cid, pid, cName, cover, uName, pTitle, learn_time);
//            } else {
//
//            }
            learningCourseList.add(learningCourse);
        }
    }
}

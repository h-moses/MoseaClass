package com.hyt.moseaclass.ui.course;

import android.os.Bundle;
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
import com.hyt.moseaclass.state.UserContext;
import com.hyt.moseaclass.utils.OkHttpUtils;
import com.hyt.moseaclass.utils.SharedPreferenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.FormBody;

public class CourseFragment extends Fragment {

    //    动态字符串模板，参加课程的数量
    private static final String COURSE_COUNT_TEMPLATE = "课程(%d)";

    private FragmentCourseBinding binding;

    //    已参加课程列表
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


    /*
     * 网络请求数据
     * */
    private void initData() throws JSONException {
        learningCourseList = new ArrayList<>();
        LearningCourse learningCourse = null;
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("uid", String.valueOf(SharedPreferenceUtils.getInteger(requireContext(), SharedPreferenceUtils.LOGIN_STATE, UserContext.KEY_UID, Integer.MIN_VALUE)));
        JSONArray array = OkHttpUtils.post("http://101.133.173.40:8090/edusys/course/getLearntCourse?", builder.build());
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            int id = jsonObject.getInt("id");
            int cid = jsonObject.getJSONObject("course").getInt("cid");
            String cName = jsonObject.getJSONObject("course").getString("title");
            String cover = jsonObject.getJSONObject("course").getString("cover");
            String desc = jsonObject.getJSONObject("course").getString("des");
            String uName = jsonObject.getJSONObject("course").getJSONObject("user").getString("nick_name");
            boolean aNull = jsonObject.isNull("current_video");
            if (!aNull) {
                JSONObject current_video = jsonObject.getJSONObject("current_video");
                int order_id = current_video.getInt("order_id");
                int catalogue_id = current_video.getInt("catalogue_id");
                learningCourse = new LearningCourse(id, cid, catalogue_id, order_id, cName, cover, uName, desc);
            } else {
                learningCourse = new LearningCourse(id, cid, cName, cover, uName, desc);
            }
            learningCourseList.add(learningCourse);
        }
    }
}

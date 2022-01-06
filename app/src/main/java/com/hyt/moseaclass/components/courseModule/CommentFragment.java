package com.hyt.moseaclass.components.courseModule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hyt.moseaclass.adapters.CommentAdapter;
import com.hyt.moseaclass.data.entity.CourseComment;
import com.hyt.moseaclass.databinding.FragmentCommentBinding;
import com.hyt.moseaclass.utils.OkHttpUtils;
import com.hyt.moseaclass.utils.SharedPreferenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;

public class CommentFragment extends Fragment {

    private static final String TAG = CommentFragment.class.getSimpleName();
    private FragmentCommentBinding binding;
    private List<CourseComment> commentList;

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
        binding = FragmentCommentBinding.inflate(inflater, container, false);
        binding.commentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.commentRecyclerView.setAdapter(new CommentAdapter(getContext(), commentList));
        return binding.getRoot();
    }


    private void initData() throws JSONException {
        commentList = new ArrayList<>();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("cid", String.valueOf(SharedPreferenceUtils.getInteger(requireContext(), SharedPreferenceUtils.COURSE_FILE, "cid", Integer.MIN_VALUE)));
        JSONArray array = OkHttpUtils.post("http://101.133.173.40:8090/edusys/course/getCourseEvaluation?", builder.build());
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            int id = jsonObject.getInt("id");
            int course_id = jsonObject.getInt("course_id");
            int total_star = jsonObject.getInt("total_star");
            String content = jsonObject.getString("content");
            String create_time = jsonObject.getString("create_time");
            JSONObject user = jsonObject.getJSONObject("user");
            int uid = user.getInt("uid");
            String nick_name = user.getString("nick_name");
            String avatar = user.getString("avatar");
            CourseComment courseComment = new CourseComment(id, course_id, uid, total_star, nick_name, avatar, content, create_time);
            commentList.add(courseComment);
        }
    }
}

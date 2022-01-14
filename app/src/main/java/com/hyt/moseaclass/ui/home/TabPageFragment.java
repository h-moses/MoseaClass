package com.hyt.moseaclass.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.hyt.moseaclass.adapters.ShowCourseAdapter;
import com.hyt.moseaclass.components.BannerView;
import com.hyt.moseaclass.data.entity.CourseIntroduction;
import com.hyt.moseaclass.databinding.FragmentTabBinding;
import com.hyt.moseaclass.utils.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;

public class TabPageFragment extends Fragment {

    //    轮播图资源
    private final List<String> bannerImages = new ArrayList<>();
    //    课程信息
    private final List<CourseIntroduction> courseIntroductions = new ArrayList<>();
    //    上下文信息
    private final Context mContext;
    private FragmentTabBinding binding;
    private String tabTitle;
    private BannerView bannerView;

    public TabPageFragment(Context context, int tabTitle) {
        this.mContext = context;
        this.tabTitle = mContext.getResources().getString(tabTitle);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        初始化数据
        try {
            initData();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTabBinding.inflate(inflater, container, false);
        bannerView = binding.advertisingBanner;
//        设置图片资源
        bannerView.setBannerData(bannerImages);
//        设置布局
        binding.courseList.setLayoutManager(new GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false));
//        设置适配器，传入数据
        binding.courseList.setAdapter(new ShowCourseAdapter(requireContext(), courseIntroductions));
        return binding.getRoot();
    }

    /*
     * 网络请求获取数据
     * */
    private void initData() throws JSONException {
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("type", "stu_home");
        JSONArray result = OkHttpUtils.post("http://101.133.173.40:8090/edusys/banner/getAll", formBody.build());
        for (int i = 0; i < result.length(); i++) {
            JSONObject jsonObject = result.getJSONObject(i);
            bannerImages.add(jsonObject.getString("cover"));
        }

        JSONArray jsonArray = OkHttpUtils.get("http://101.133.173.40:8090/edusys/course/getAll");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int cid = jsonObject.getInt("cid");
            String title = jsonObject.getString("title");
            String cover = jsonObject.getString("cover");
            String desc = jsonObject.getString("des");
            String string = jsonObject.getJSONObject("user").getString("nick_name");
            CourseIntroduction introduction = new CourseIntroduction(cid, title, cover, string, desc);
            courseIntroductions.add(introduction);
        }
    }

}

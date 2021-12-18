package com.hyt.moseaclass.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hyt.moseaclass.components.BannerView;
import com.hyt.moseaclass.components.CourseAlbumView;
import com.hyt.moseaclass.components.indicator.CircleIndicator;
import com.hyt.moseaclass.databinding.FragmentTabBinding;
import com.hyt.moseaclass.model.CourseIntroduction;
import com.hyt.moseaclass.utils.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;

public class TabPageFragment extends Fragment {

    private static final String TAG = TabPageFragment.class.getSimpleName();
    private final List<String> bannerImages = new ArrayList<>();
    private final List<CourseIntroduction> courseIntroductions = new ArrayList<>();
    private final Context mContext;
    private FragmentTabBinding binding;
    private int tabTitle;
    private BannerView bannerView;

    public TabPageFragment(Context context, int tabTitle) {
        this.mContext = context;
        this.tabTitle = tabTitle;
    }

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
        binding = FragmentTabBinding.inflate(inflater, container, false);
        bannerView = binding.advertisingBanner;
        bannerView.setIndicator(new CircleIndicator(getContext()));
        bannerView.setBannerData(bannerImages);
//        CourseAlbumView view = new CourseAlbumView(getContext(),courseIntroductions);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
//        binding.getRoot().addView(view, params);
        CourseAlbumView courseAlbum = binding.courseAlbum;
        courseAlbum.setCourseAlbumData(courseIntroductions);
        return binding.getRoot();
    }

    private void initData() throws JSONException {
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("type", "stu_home");
        JSONArray result = OkHttpUtils.post("http://101.133.173.40:8090/edusys/banner/getAll", formBody.build());
        for (int i = 0; i < result.length(); i++) {
            JSONObject jsonObject = result.getJSONObject(i);
            bannerImages.add(jsonObject.getString("cover"));
        }

        JSONArray jsonArray = OkHttpUtils.get("http://101.133.173.40:8090/edusys/course/getAll");
        for (int i = 0; i < result.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int cid = jsonObject.getInt("cid");
            String title = jsonObject.getString("title");
            String cover = jsonObject.getString("cover");
            String string = jsonObject.getJSONObject("user").getString("nick_name");
            CourseIntroduction introduction = new CourseIntroduction(cid, title, cover, string);
            Log.e(TAG, "initData: " + introduction.toString());
            courseIntroductions.add(introduction);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.advertisingBanner.stopPlay();
    }
}

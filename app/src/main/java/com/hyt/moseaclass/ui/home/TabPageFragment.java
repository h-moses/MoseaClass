package com.hyt.moseaclass.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hyt.moseaclass.components.BannerView;
import com.hyt.moseaclass.components.indicator.CircleIndicator;
import com.hyt.moseaclass.databinding.FragmentTabBinding;
import com.hyt.moseaclass.utils.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;

public class TabPageFragment extends Fragment {

    private static final String TAG = TabPageFragment.class.getSimpleName();
    private final List<String> imageUrl = new ArrayList<>();
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
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTabBinding.inflate(inflater, container, false);
        bannerView = binding.bannerView;
        bannerView.setIndicator(new CircleIndicator(getContext()));
        bannerView.setBannerData(imageUrl);
        return binding.getRoot();
    }

    private void initData() {
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("type", "stu_home");
        JSONArray result = OkHttpUtils.post("http://101.133.173.40:8090/edusys/banner/getAll", formBody.build());
        for (int i = 0; i < result.length(); i++) {
            try {
                JSONObject jsonObject = result.getJSONObject(i);
                imageUrl.add(jsonObject.getString("cover"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

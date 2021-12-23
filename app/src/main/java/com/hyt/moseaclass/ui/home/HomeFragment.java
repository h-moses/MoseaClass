package com.hyt.moseaclass.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.hyt.moseaclass.adapters.SectionsPagerAdapter;
import com.hyt.moseaclass.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        initSearchView(binding.homeSearchView);
        initTabLayout();

        return binding.getRoot();
    }

    private void initSearchView(SearchView searchView) {
        searchView.setQueryHint("Vue");
        searchView.setIconifiedByDefault(false);
        searchView.setQueryRefinementEnabled(true);
//        去除下划线
        searchView.findViewById(androidx.appcompat.R.id.search_plate).setBackground(null);
        searchView.findViewById(androidx.appcompat.R.id.submit_area).setBackground(null);
//        修改提示文字颜色
        TextView textView = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        textView.setHintTextColor(Color.rgb(153, 153, 153));
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
    }

    private void initTabLayout() {
        binding.viewPager.setAdapter(new SectionsPagerAdapter(getContext(), getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
//        解决tab切换时的轮播图报错
        binding.viewPager.setOffscreenPageLimit(1);
        binding.homeTabs.setupWithViewPager(binding.viewPager);
    }
}
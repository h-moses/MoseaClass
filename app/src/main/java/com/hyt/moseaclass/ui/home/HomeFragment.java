package com.hyt.moseaclass.ui.home;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
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

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        initSearchView(binding.homeSearchView);
        initTabLayout();

        return binding.getRoot();
    }

    /*
     * 初始化搜索框
     * */
    private void initSearchView(SearchView searchView) {
//        提示查询
        searchView.setQueryHint("Vue");
//        默认不以图表显示
        searchView.setIconifiedByDefault(false);
        searchView.setQueryRefinementEnabled(true);
//        去除下划线
        searchView.findViewById(androidx.appcompat.R.id.search_plate).setBackground(null);
        searchView.findViewById(androidx.appcompat.R.id.submit_area).setBackground(null);
//        修改提示文字颜色
        TextView textView = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        textView.setHintTextColor(Color.rgb(153, 153, 153));
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

//        获取SearchView并设置可搜索的配置
        SearchManager searchManager = (SearchManager) requireContext().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().getComponentName()));

//        添加监听器
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                执行搜索，设置intent的搜索活动，添加搜索信息
                Intent intent = new Intent(requireActivity(), SearchActivity.class);
//                携带查询数据
                intent.putExtra(SearchManager.QUERY, query);
//                设置ACTION_SEARCH
                intent.setAction(Intent.ACTION_SEARCH);
//                启动Activity
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    /*
     * 初始化标签布局
     * */
    private void initTabLayout() {
//        设置适配器
        binding.viewPager.setAdapter(new SectionsPagerAdapter(getContext(), getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
        binding.viewPager.setOffscreenPageLimit(7);
//        给布局设置viewpager，实现页面切换
        binding.homeTabs.setupWithViewPager(binding.viewPager);
    }
}
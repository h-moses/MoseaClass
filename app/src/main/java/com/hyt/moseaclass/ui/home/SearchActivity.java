package com.hyt.moseaclass.ui.home;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hyt.moseaclass.adapters.SearchAdapter;
import com.hyt.moseaclass.data.entity.SearchResult;
import com.hyt.moseaclass.databinding.ActivitySearchBinding;
import com.hyt.moseaclass.utils.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.FormBody;

public class SearchActivity extends AppCompatActivity {

    private static final String TITLE_TEMPLATE = "共有%d条与%s相关的内容";
    private final List<SearchResult> resultList = new ArrayList<>();
    private String query;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handleIntent();

        com.hyt.moseaclass.databinding.ActivitySearchBinding binding = ActivitySearchBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        initSearchView(binding.searchSearchView);

        binding.resultList.setLayoutManager(new LinearLayoutManager(this));
        binding.resultList.setAdapter(new SearchAdapter(this,resultList));
    }


    private void initSearchView(SearchView searchView) {
        searchView.setQuery(query,false);
        searchView.setFocusable(true);
        searchView.setIconifiedByDefault(false);
        searchView.setQueryRefinementEnabled(true);
//        去除下划线
        searchView.findViewById(androidx.appcompat.R.id.search_plate).setBackground(null);
        searchView.findViewById(androidx.appcompat.R.id.submit_area).setBackground(null);
//        修改提示文字颜色
        TextView textView = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        textView.setHintTextColor(Color.rgb(153, 153, 153));
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //                执行搜索，设置intent的搜索活动，添加搜索信息
                doSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    /*
    * 获取搜索关键词，进行搜索
    * */
    private void handleIntent() {
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
            doSearch(query);
        }
    }


    /*
    * 网络请求，根据关键字模糊搜索
    * */
    private void doSearch(String query) {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("key", query);
        JSONArray array = OkHttpUtils.post("http://101.133.173.40:8090/edusys/course/searchCourse", builder.build());
        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject jsonObject = array.getJSONObject(i);
                int cid = jsonObject.getInt("cid");
                String title = jsonObject.getString("title");
                String des = jsonObject.getString("des");
                String cover = jsonObject.getString("cover");
                SearchResult searchResult = new SearchResult(cid, title, des, cover);
                resultList.add(searchResult);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}

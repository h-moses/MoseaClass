package com.hyt.moseaclass.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hyt.moseaclass.data.entity.SearchResult;
import com.hyt.moseaclass.databinding.ItemSearchCourseBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/*
 * 搜索适配器
 * */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    //    上下文信息
    private final Context mContext;
    //    数据列表
    private final List<SearchResult> resultList = new ArrayList<>();

    /*
     * 构造器，获取上下文和数据
     * */
    public SearchAdapter(Context context, List<SearchResult> list) {
        this.mContext = context;
        this.resultList.clear();
        this.resultList.addAll(list);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        使用视图绑定
        ItemSearchCourseBinding binding = ItemSearchCourseBinding.inflate(LayoutInflater.from(mContext), parent, false);
//        创建ViewHolder
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        获取对应索引的课程并显示信息
        Picasso.get().load(resultList.get(position).getcCover()).into(holder.getBinding().itemCover);
        holder.getBinding().itemTitle.setText(resultList.get(position).getcName());
        holder.getBinding().itemDesc.setText(resultList.get(position).getcDesc());
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemSearchCourseBinding binding;

        public ViewHolder(@NonNull ItemSearchCourseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ItemSearchCourseBinding getBinding() {
            return binding;
        }
    }
}

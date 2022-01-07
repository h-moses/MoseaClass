package com.hyt.moseaclass.adapters;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hyt.moseaclass.components.RoundImageView;
import com.hyt.moseaclass.data.entity.DynamicInfo;
import com.hyt.moseaclass.databinding.ItemDynamicBinding;
import com.hyt.moseaclass.utils.OkHttpUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DynamicAdapter extends RecyclerView.Adapter<DynamicAdapter.ViewHolder> {

    private static final String TAG = DynamicAdapter.class.getSimpleName();
    private final Context mContext;
    private ItemDynamicBinding binding;
    private final List<DynamicInfo> dynamicInfoList = new ArrayList<>();

    public DynamicAdapter(Context mContext) {
        this.mContext = mContext;
        try {
            initData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initData() throws JSONException {
        JSONArray jsonArray = OkHttpUtils.get("http://101.133.173.40:8090/edusys/find/getAllShare");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int id = jsonObject.getInt("id");
            String publisher = jsonObject.getJSONObject("user").getString("nick_name");
            String avatar = jsonObject.getJSONObject("user").getString("avatar");
            String group = "";
            if (!jsonObject.isNull("group")) {
                group = jsonObject.getString("group");
            }
            String content = jsonObject.getString("content");
            String create_time = jsonObject.getString("create_time");
            JSONArray imgs = jsonObject.getJSONArray("imgs");
            List<String> list = new ArrayList<>();
            for (int j = 0; j < imgs.length(); j++) {
                list.add(imgs.getString(j));
            }
            DynamicInfo dynamicInfo = new DynamicInfo(id, publisher,avatar,group,content,create_time,list);
            dynamicInfoList.add(dynamicInfo);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemDynamicBinding.inflate(LayoutInflater.from(mContext), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(dynamicInfoList.get(position).getPublisherImage()).into(holder.getBinding().dynamicAvatar);
        holder.getBinding().dynamicUsername.setText(dynamicInfoList.get(position).getPublisher());
        holder.getBinding().dynamicGroup.setText(dynamicInfoList.get(position).getGroup());
        holder.getBinding().dynamicTime.setText(dynamicInfoList.get(position).getPublishTime());
        holder.getBinding().dynamicContent.setText(dynamicInfoList.get(position).getContent());
        List<String> imgList = dynamicInfoList.get(position).getImgList();
        holder.getBinding().dynamicImageGrid.setColumnCount(Math.min(imgList.size(), 3));
        holder.getBinding().dynamicImageGrid.setRowCount((int) Math.ceil(imgList.size() / 3.0));
        for (int i = 0; i < imgList.size(); i++) {
            ImageView imageView = new ImageView(mContext);
            Picasso.get().load(imgList.get(i)).into(imageView);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 300;
            params.height = 300;
            params.rowSpec = GridLayout.spec(i / 3, 1, 1f);
            params.columnSpec = GridLayout.spec(i % 3, 1, 1f);
            holder.getBinding().dynamicImageGrid.addView(imageView, params);
        }
    }

    @Override
    public int getItemCount() {
        return dynamicInfoList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemDynamicBinding binding;

        public ViewHolder(@NonNull ItemDynamicBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ItemDynamicBinding getBinding() {
            return binding;
        }
    }
}

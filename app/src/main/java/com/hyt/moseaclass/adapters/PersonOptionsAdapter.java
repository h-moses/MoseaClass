package com.hyt.moseaclass.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hyt.moseaclass.R;
import com.hyt.moseaclass.databinding.ViewPersonOptionBinding;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

/*
 * 选项适配器
 * */
public class PersonOptionsAdapter extends RecyclerView.Adapter<PersonOptionsAdapter.ViewHolder> {

    private final Context mContext;
    private final List<String> nameList = Arrays.asList("我的课程", "我的讨论");
    @DrawableRes
    private final List<Integer> iconList = Arrays.asList(R.drawable.ic_live, R.drawable.ic_discussion);

    public PersonOptionsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewPersonOptionBinding binding = ViewPersonOptionBinding.inflate(LayoutInflater.from(mContext), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(iconList.get(position)).into(holder.getBinding().optionsIcon);
        holder.binding.optionsName.setText(nameList.get(position));
    }

    @Override
    public int getItemCount() {
        return Math.min(nameList.size(), iconList.size());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ViewPersonOptionBinding binding;

        public ViewHolder(@NonNull ViewPersonOptionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ViewPersonOptionBinding getBinding() {
            return binding;
        }
    }
}

package com.hyt.moseaclass.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.hyt.moseaclass.databinding.ViewOptionBinding;
import com.squareup.picasso.Picasso;

public class OptionView extends ConstraintLayout {

    private ViewOptionBinding binding;

    public OptionView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public OptionView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public OptionView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(Context context) {
        binding = ViewOptionBinding.inflate(LayoutInflater.from(context), this, true);
    }

    public void setData(int resId, String content) {
        Picasso.get().load(resId).into(binding.optionIcon);
        binding.optionContent.setText(content);
    }
}

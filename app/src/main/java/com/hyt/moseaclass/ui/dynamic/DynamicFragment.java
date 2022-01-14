package com.hyt.moseaclass.ui.dynamic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hyt.moseaclass.adapters.DynamicAdapter;
import com.hyt.moseaclass.databinding.FragmentDynamicBinding;

public class DynamicFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        com.hyt.moseaclass.databinding.FragmentDynamicBinding binding = FragmentDynamicBinding.inflate(inflater, container, false);
//        动态列表设置为线性布局
        binding.dynamicList.setLayoutManager(new LinearLayoutManager(requireContext()));
//        动态列表设置适配器
        binding.dynamicList.setAdapter(new DynamicAdapter(requireContext()));
        return binding.getRoot();
    }

}

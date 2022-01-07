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
import com.hyt.moseaclass.utils.OkHttpUtils;

import org.json.JSONArray;

public class DynamicFragment extends Fragment {

    private FragmentDynamicBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDynamicBinding.inflate(inflater, container, false);
        binding.dynamicList.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.dynamicList.setAdapter(new DynamicAdapter(requireContext()));
        return binding.getRoot();
    }

}

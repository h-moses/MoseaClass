package com.hyt.moseaclass.components.courseModule;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hyt.moseaclass.adapters.TestAdapter;
import com.hyt.moseaclass.databinding.FragmentTestBinding;

public class TestFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        com.hyt.moseaclass.databinding.FragmentTestBinding binding = FragmentTestBinding.inflate(inflater, container, false);
        binding.fragmentTitle.setTypeface(Typeface.DEFAULT_BOLD);
        binding.testList.setAdapter(new TestAdapter(requireContext()));
        binding.testList.setLayoutManager(new LinearLayoutManager(requireContext()));
        return binding.getRoot();
    }

}

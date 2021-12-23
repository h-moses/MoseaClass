package com.hyt.moseaclass.components.courseModule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hyt.moseaclass.databinding.FragmentCoursewareBinding;

public class CoursewareFragment extends Fragment {

    private FragmentCoursewareBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCoursewareBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}

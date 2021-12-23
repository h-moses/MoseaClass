package com.hyt.moseaclass.components.courseModule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hyt.moseaclass.databinding.FragmentIntroductionBinding;
import com.hyt.moseaclass.utils.SharedPreferenceUtils;

public class IntroductionFragment extends Fragment {

    private FragmentIntroductionBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentIntroductionBinding.inflate(inflater, container, false);
        binding.tvIntroductionName.setText(SharedPreferenceUtils.getString(getContext(), "name", ""));
        binding.tvIntroductionDesc.setText(SharedPreferenceUtils.getString(getContext(), "desc", ""));
        return binding.getRoot();
    }
}

package com.hyt.moseaclass.ui.person;

import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hyt.moseaclass.databinding.FragmentPersonBinding;

public class PersonFragment extends Fragment {

    private FragmentPersonBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPersonBinding.inflate(inflater,container,false);
        //        将应用栏与背景色融为一体，去除高程
        StateListAnimator stateListAnimator = new StateListAnimator();
        stateListAnimator.addState(new int[0], ObjectAnimator.ofFloat(binding.personAppbar, "elevation", 0.1f));
        binding.personAppbar.setStateListAnimator(stateListAnimator);
        return binding.getRoot();
    }
}

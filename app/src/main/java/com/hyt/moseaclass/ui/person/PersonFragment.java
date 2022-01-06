package com.hyt.moseaclass.ui.person;

import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hyt.moseaclass.adapters.PersonOptionsAdapter;
import com.hyt.moseaclass.data.UserInfoViewModel;
import com.hyt.moseaclass.data.entity.UserInfo;
import com.hyt.moseaclass.databinding.FragmentPersonBinding;
import com.hyt.moseaclass.state.UserContext;
import com.hyt.moseaclass.utils.SharedPreferenceUtils;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class PersonFragment extends Fragment {

    private static final String TAG = PersonFragment.class.getSimpleName();
    private FragmentPersonBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPersonBinding.inflate(inflater, container, false);
        //        将应用栏与背景色融为一体，去除高程
        StateListAnimator stateListAnimator = new StateListAnimator();
        stateListAnimator.addState(new int[0], ObjectAnimator.ofFloat(binding.personAppbar, "elevation", 0.1f));
        binding.personAppbar.setStateListAnimator(stateListAnimator);

        binding.personRecyclerView.setAdapter(new PersonOptionsAdapter(getContext()));
        binding.personRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

         UserInfoViewModel viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(UserInfoViewModel.class);
        if (UserContext.getInstance().getIsLogin(requireContext())) {
            LiveData<UserInfo> userInfoLiveData = viewModel.getUserInfoLiveData(SharedPreferenceUtils.getInteger(requireContext(), SharedPreferenceUtils.LOGIN_STATE, UserContext.KEY_UID, 0));
            if (userInfoLiveData != null) {
                userInfoLiveData.observe(getViewLifecycleOwner(), userInfo -> {
                    if (userInfo == null) {
                        return;
                    }
                    Picasso.get().load(userInfo.getUAvatar()).into(binding.profileAvatar);
                    binding.profileName.setText(userInfo.getUName());
                });
            }
        }
        binding.btnLogout.setOnClickListener(view -> UserContext.getInstance().setLogoutState(requireContext()));
        return binding.getRoot();
    }
}

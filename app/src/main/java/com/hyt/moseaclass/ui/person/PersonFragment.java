package com.hyt.moseaclass.ui.person;

import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hyt.moseaclass.adapters.PersonOptionsAdapter;
import com.hyt.moseaclass.data.UserInfoViewModel;
import com.hyt.moseaclass.data.entity.UserInfo;
import com.hyt.moseaclass.databinding.FragmentPersonBinding;
import com.hyt.moseaclass.state.UserContext;
import com.hyt.moseaclass.ui.login.LoginActivity;
import com.hyt.moseaclass.utils.SharedPreferenceUtils;
import com.squareup.picasso.Picasso;

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

        binding.profileName.setText("立即登录");
        Picasso.get().load(Uri.parse("http://edu-image.nosdn.127.net/6e66dbdc55464a44889c6a25428b2b4b.png?imageView&quality=100")).into(binding.profileAvatar);

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
        } else {
            binding.btnLogout.setVisibility(View.GONE);
            binding.profileLearningTime.setVisibility(View.GONE);
        }
        binding.btnLogout.setOnClickListener(view -> {
            UserContext.getInstance().setLogoutState(requireContext());
            binding.profileName.setText("立即登录");
            binding.profileLearningTime.setVisibility(View.GONE);
            Picasso.get().load(Uri.parse("http://edu-image.nosdn.127.net/6e66dbdc55464a44889c6a25428b2b4b.png?imageView&quality=100")).into(binding.profileAvatar);
            binding.btnLogout.setVisibility(View.GONE);
        });

        binding.profileCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!UserContext.getInstance().getIsLogin(requireContext())) {
                    Intent intent = new Intent(requireContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        return binding.getRoot();
    }
}

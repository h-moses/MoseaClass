package com.hyt.moseaclass.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.hyt.moseaclass.MainActivity;
import com.hyt.moseaclass.data.entity.UserInfo;
import com.hyt.moseaclass.databinding.ActivityLoginBinding;
import com.hyt.moseaclass.utils.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private UserInfoViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(LayoutInflater.from(getApplicationContext()));
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(UserInfoViewModel.class);
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    userLogin(binding.etUsername.getText().toString(), binding.etPassword.getText().toString(), "1");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void userLogin(String phone, String pwd, String role) throws JSONException {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("phone", phone);
        builder.add("pwd", pwd);
        builder.add("role", role);
        JSONObject jsonObject = OkHttpUtils.postObj("http://101.133.173.40:8090/edusys/user/login", builder.build());
        if (jsonObject != null) {
            int uid = jsonObject.getInt("uid");
            String nick_name = jsonObject.getString("nick_name");
            String phone1 = jsonObject.getString("phone");
            String avatar = jsonObject.getString("avatar");
            UserInfo userInfo = new UserInfo(uid, nick_name, phone1, avatar);
            userInfo.setIsLogin(1);
            viewModel.getUserInfoRepository().insertUser(userInfo);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this,"该用户不存在",Toast.LENGTH_SHORT).show();
        }
    }
}

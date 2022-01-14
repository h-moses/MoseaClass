package com.hyt.moseaclass.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.hyt.moseaclass.MainActivity;
import com.hyt.moseaclass.data.UserInfoViewModel;
import com.hyt.moseaclass.data.entity.UserInfo;
import com.hyt.moseaclass.databinding.ActivityLoginBinding;
import com.hyt.moseaclass.state.UserContext;
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
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(UserInfoViewModel.class);

//        登录按钮的点击事件
        binding.btnLogin.setOnClickListener(v -> {
            try {
                userLogin(binding.etUsername.getText().toString(), binding.etPassword.getText().toString(), "1");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        if (UserContext.getInstance().getIsLogin(this)) { // 用户已登录，直接跳转到首页
            goMainActivity();
        }
    }

    /*
     * 用户登录时的数据处理
     * */
    private void userLogin(String phone, String pwd, String role) throws JSONException {
//        将用户输入的数据添加到form-data中
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("phone", phone);
        builder.add("pwd", pwd);
        builder.add("role", role);
//        发送请求
        JSONObject jsonObject = OkHttpUtils.postObj("http://101.133.173.40:8090/edusys/user/login", builder.build());
        if (jsonObject != null) { // 若不为空，则用户输入正确
//             存储下列信息
            int uid = jsonObject.getInt("uid");
            String nick_name = jsonObject.getString("nick_name");
            String phone1 = jsonObject.getString("phone");
            String avatar = jsonObject.getString("avatar");
            UserInfo userInfo = new UserInfo(uid, nick_name, phone1, avatar);
//
            userInfo.setIsLogin(1);
//            将用户信息插入数据库
            viewModel.getUserInfoRepository().insertUser(userInfo);
//            设置登录状态
            UserContext.getInstance().setLoginState(this, uid);
//            跳转到主界面
            goMainActivity();
        } else {
            Toast.makeText(this, "该用户不存在", Toast.LENGTH_SHORT).show();
        }
    }

    /*
     * 跳转至登录界面
     * */
    private void goMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

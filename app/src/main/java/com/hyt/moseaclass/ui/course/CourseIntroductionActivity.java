package com.hyt.moseaclass.ui.course;

import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.android.material.appbar.AppBarLayout;
import com.hyt.moseaclass.R;
import com.hyt.moseaclass.adapters.CourseRelativityPagerAdapter;
import com.hyt.moseaclass.data.entity.CourseIntroduction;
import com.hyt.moseaclass.databinding.ActivityCourseIntroductionBinding;
import com.hyt.moseaclass.state.UserContext;
import com.hyt.moseaclass.utils.OkHttpUtils;
import com.hyt.moseaclass.utils.SharedPreferenceUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import okhttp3.FormBody;


public class CourseIntroductionActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private static final String TAG = CourseIntroductionActivity.class.getSimpleName();
    private ActivityCourseIntroductionBinding binding;
    private CourseIntroduction introduction;

    private boolean isJoin = false;
    private int cid;
    private int uid;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent();
        binding = ActivityCourseIntroductionBinding.inflate(LayoutInflater.from(this), null, false);
        setContentView(binding.getRoot());
        setSupportActionBar(binding.courseToolbar);
        // 设置状态栏覆盖到应用之上，不占固定位置
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        显示返回按钮
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//        将应用栏与背景色融为一体，去除高程
        StateListAnimator stateListAnimator = new StateListAnimator();
        stateListAnimator.addState(new int[0], ObjectAnimator.ofFloat(binding.introductionAppbar, "elevation", 0.1f));
        binding.introductionAppbar.setStateListAnimator(stateListAnimator);

        binding.introductionViewpager.setAdapter(new CourseRelativityPagerAdapter(this, getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
//        默认导航至课件页面
        binding.introductionViewpager.setCurrentItem(1);
        binding.introductionTabs.setupWithViewPager(binding.introductionViewpager);

        try {
            initState();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (isJoin) {
            switchBtnState("退出学习", R.drawable.btn_quit_course);
        } else {
            switchBtnState("加入学习", R.drawable.login_btn_back);
        }

        if (introduction != null) {
            Picasso.get().load(Uri.parse(introduction.getcImage())).into(binding.tbExpand.introductionImage);
            binding.tbExpand.introductionName.setText(introduction.getcName());
            binding.tbExpand.introductionDepart.setText(introduction.getcInstructor());
        }

        binding.introductionAppbar.addOnOffsetChangedListener(this);

        binding.btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!UserContext.getInstance().getIsLogin(getApplicationContext())) {
                    Toast.makeText(getApplicationContext(), "用户未登录，无法加入课程！", Toast.LENGTH_SHORT).show();
                } else {
                    if (isJoin) {
                        UserContext.getInstance().getmState().quitCourse(getApplicationContext(), uid, cid);
//                        switchBtnState("加入学习",R.drawable.login_btn_back);
                    } else {
                        UserContext.getInstance().getmState().joinCourse(getApplicationContext(), uid, cid);
                        switchBtnState("退出学习", R.drawable.btn_quit_course);
                    }
                }
            }
        });

        if (UserContext.getInstance().getIsLogin(this) && isJoin) {
            new AlertDialog.Builder(this).setMessage("学了这么久，不如来评价一下这个课程吧~~").setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(getApplicationContext(), CourseEvaluateActivity.class);
                    intent.putExtra("courseName", introduction.getcName());
                    startActivity(intent);
                }
            }).setNegativeButton("暂不评价", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).create().show();
        }
    }


    private void switchBtnState(String text, int resId) {
        binding.btnJoin.setText(text);
        binding.btnJoin.setBackgroundResource(resId);
    }

    private void handleIntent() {
        Bundle data = getIntent().getBundleExtra("data");
        if (data != null) {
            introduction = new CourseIntroduction(data.getInt("cid"), data.getString("title"), data.getString("cover"), data.getString("uName"), data.getString("desc"));
        }
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int dist = Math.abs(verticalOffset);
        int totalScrollRange = appBarLayout.getTotalScrollRange();
        if (dist <= totalScrollRange * 0.5) {
            binding.collapseToolbarTitle.setVisibility(View.GONE);
            setCustomNavigationIcon(R.color.white);
        } else {
            binding.collapseToolbarTitle.setVisibility(View.VISIBLE);
            binding.collapseToolbarTitle.setText(introduction.getcName());
            setCustomNavigationIcon(R.color.black);
        }
    }

    /*
     * 根据状态栏的展开折叠修改返回按钮的颜色
     * */
    private void setCustomNavigationIcon(int color) {
        Drawable upArrow = ContextCompat.getDrawable(this, androidx.appcompat.R.drawable.abc_ic_ab_back_material);
        if (upArrow != null) {
            upArrow.setColorFilter(ContextCompat.getColor(this, color), PorterDuff.Mode.SRC_ATOP);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setHomeAsUpIndicator(upArrow);
            }
        }
    }


    private void initState() throws JSONException {
        uid = SharedPreferenceUtils.getInteger(this, SharedPreferenceUtils.LOGIN_STATE, UserContext.KEY_UID, Integer.MIN_VALUE);
        cid = SharedPreferenceUtils.getInteger(this, SharedPreferenceUtils.COURSE_FILE, UserContext.KEY_CID, Integer.MIN_VALUE);
        if (uid < 0) {
            return;
        }
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("uid", String.valueOf(uid));
        builder.add("cid", String.valueOf(cid));
        JSONObject jsonObject = OkHttpUtils.postObj("http://101.133.173.40:8090/edusys/course/getUserCourseRelation", builder.build());
        isJoin = jsonObject.getBoolean("flag");
        SharedPreferenceUtils.setBoolean(this,SharedPreferenceUtils.COURSE_FILE,UserContext.KEY_JOIN,isJoin);
    }
}

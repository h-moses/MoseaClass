package com.hyt.moseaclass.ui.course;

import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.android.material.appbar.AppBarLayout;
import com.hyt.moseaclass.R;
import com.hyt.moseaclass.adapters.CourseRelativityPagerAdapter;
import com.hyt.moseaclass.databinding.ActivityCourseIntroductionBinding;
import com.hyt.moseaclass.model.CourseIntroduction;
import com.hyt.moseaclass.utils.SharedPreferenceUtils;
import com.squareup.picasso.Picasso;

import java.util.Objects;


public class CourseIntroductionActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private static final String TAG = CourseIntroductionActivity.class.getSimpleName();
    private ActivityCourseIntroductionBinding binding;
    private CourseIntroduction introduction;

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
        binding.introductionTabs.setupWithViewPager(binding.introductionViewpager);

        Picasso.get().load(Uri.parse(introduction.getcImage())).into(binding.tbExpand.introductionImage);
        binding.tbExpand.introductionName.setText(introduction.getcName());
        binding.tbExpand.introductionDepart.setText(introduction.getcInstructor());
        binding.introductionAppbar.addOnOffsetChangedListener(this);
    }

    private void handleIntent() {
//        Bundle course = getIntent().getBundleExtra("course");
//        assert course != null;
        introduction = new CourseIntroduction(SharedPreferenceUtils.getInteger(this, "id", Integer.MIN_VALUE), SharedPreferenceUtils.getString(this,"name",""), SharedPreferenceUtils.getString(this, "image", ""), SharedPreferenceUtils.getString(this,"teacher", ""), SharedPreferenceUtils.getString(this,"desc",""));
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
}

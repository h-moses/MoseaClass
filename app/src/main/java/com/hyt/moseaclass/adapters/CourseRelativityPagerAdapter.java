package com.hyt.moseaclass.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.hyt.moseaclass.R;
import com.hyt.moseaclass.components.courseModule.CommentFragment;
import com.hyt.moseaclass.components.courseModule.CoursewareFragment;
import com.hyt.moseaclass.components.courseModule.IntroductionFragment;
import com.hyt.moseaclass.components.courseModule.TestFragment;

public class CourseRelativityPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_introduction, R.string.tab_courseware, R.string.tab_comment, R.string.tab_test};
    private static final String TAG = CourseRelativityPagerAdapter.class.getSimpleName();

    private final Context mContext;

    public CourseRelativityPagerAdapter(Context mContext, @NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (0 == position) {
            return new IntroductionFragment();
        } else if (1 == position) {
            return new CoursewareFragment(mContext);
        } else if (2 == position) {
            return new CommentFragment();
        } else if (3 == position) {
            return new TestFragment();
        } else {
            return new IntroductionFragment();
        }
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }
}

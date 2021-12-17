package com.hyt.moseaclass.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.hyt.moseaclass.R;
import com.hyt.moseaclass.ui.home.TabPageFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_recommend, R.string.tab_cs, R.string.tab_national_excellent, R.string.tab_postgraduate_exam, R.string.tab_foreign_languages, R.string.tab_psychology, R.string.tab_final_exam};
    private static final String TAG = SectionsPagerAdapter.class.getSimpleName();
    private final Context mContext;

    public SectionsPagerAdapter(Context mContext, FragmentManager manager, int behavior) {
        super(manager, behavior);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return new TabPageFragment(mContext, TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }


    /*
     * 显示tab标签文字
     * */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }
}

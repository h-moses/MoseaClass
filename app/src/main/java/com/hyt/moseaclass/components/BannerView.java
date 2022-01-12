package com.hyt.moseaclass.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.hyt.moseaclass.R;
import com.hyt.moseaclass.components.indicator.BaseIndicator;
import com.hyt.moseaclass.components.indicator.CircleIndicator;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static android.view.Gravity.BOTTOM;
import static android.view.Gravity.CENTER_HORIZONTAL;

public class BannerView extends FrameLayout {

    //触发轮播的消息标志位
    private final int PLAY = 0x123;
    /**
     * 轮播图ViewPager
     */
    private ViewPager mBannerViewPager;
    /**
     * 轮播指示器
     */
    private BaseIndicator mIndicator;
    /**
     * 是否自动滑动
     */
    private boolean mIsAutoScroll;
    /**
     * 默认页面之间自动切换的时间间隔
     */
    private long mDelayTime;
    /**
     * 轮播图地址集合
     */
    private List<String> mBannerUrlList;
    /**
     * 轮播内部Adapter,实现无限轮播
     */
    private InnerPagerAdapter mAdapter;
    /**
     * 当前真正的下标，初始值为MAX的一半，以解决初始无法左滑的问题
     */
    private int mCurrentIndex = Integer.MAX_VALUE / 2;
    /**
     * 图片之间的边距
     */
    private int mPageMargin;
    /**
     * 是否启用边距模式（同时显示部分左右Item）
     */
    private boolean mIsMargin;
    /**
     * Banner圆角
     */
    private float mBannerRadius;
    //播放标志
    private boolean isPlay = false;
    //标志是否是手势操作造成的滑动
    private boolean mGestureScroll = false;
    /**
     * 轮播计时器
     */
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == PLAY && mIsAutoScroll) {
                mBannerViewPager.setCurrentItem(mCurrentIndex);
                if (isPlay) {
                    play();
                }
            }
        }
    };
    /**
     * ViewPager滑动监听
     */
    ViewPager.OnPageChangeListener mPageListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            //此处返回的position是大数
            if (position == 0 || mBannerUrlList.isEmpty()) {
                return;
            }
            setScrollPosition(position);
            int smallPos = position % mBannerUrlList.size();
            mIndicator.setCurrentPosition(smallPos);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            onPageScrollStateChange(state);
        }
    };

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        handleStyleable(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        if (mIsMargin) {
            setClipChildren(false);
        }
        initBannerViewPager(context, attrs);
        initIndicatorView(context);
    }

    private void handleStyleable(Context context, AttributeSet attrs, int defStyle) {
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.BannerView, defStyle, 0);
        try {
//            获取可编辑样式数据
            mIsAutoScroll = ta.getBoolean(R.styleable.BannerView_banner_auto_scroll, true);
            mPageMargin = ta.getDimensionPixelSize(R.styleable.BannerView_banner_page_margin, 0);
            mDelayTime = ta.getInteger(R.styleable.BannerView_banner_toggle_duration, 3000);
            mBannerRadius = ta.getDimensionPixelSize(R.styleable.BannerView_banner_radius, 0);
            if (mPageMargin > 0) {
                mIsMargin = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ta.recycle();
        }
    }

    /**
     * 初始化ViewPager
     *
     * @param context
     * @param attrs
     */
    private void initBannerViewPager(Context context, AttributeSet attrs) {
        mBannerViewPager = new ViewPager(context, attrs);
        LayoutParams bannerParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (mIsMargin) {
//            设置边距
            bannerParams.setMargins(mPageMargin, dp2px(16), mPageMargin, dp2px(16));
        }
        addView(mBannerViewPager, bannerParams);
        mBannerViewPager.addOnPageChangeListener(mPageListener);
        mBannerUrlList = new ArrayList<>();
        mAdapter = new InnerPagerAdapter();
        mBannerViewPager.setAdapter(mAdapter);
//        实现初始左滑动
        mBannerViewPager.setCurrentItem(Integer.MAX_VALUE / 2);
        if (mIsMargin) {
//            设置当前页的任意一侧应该保留的页面数量
            mBannerViewPager.setOffscreenPageLimit(5);
//            页面之间的边距为外边距的一半，这样左右显示预览的一部分
            mBannerViewPager.setPageMargin(mPageMargin / 2);
//            子view可以超过父view
            mBannerViewPager.setClipChildren(false);
        }
    }

    /**
     * 初始化指示器
     *
     * @param context
     */
    private void initIndicatorView(Context context) {
        mIndicator = new CircleIndicator(context);
        LayoutParams indicatorParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, dp2px(60));
        indicatorParams.gravity = BOTTOM | CENTER_HORIZONTAL;
        addView(mIndicator, indicatorParams);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height;
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        if (hMode == MeasureSpec.UNSPECIFIED || hMode == MeasureSpec.AT_MOST) { // 未明确或最大限制
            height = dp2px(200);
        } else { // 精确设置
            height = hSize;
        }
//        修改为精确设置
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
                MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 设置Banner图片地址数据
     *
     * @param bannerData
     */
    public void setBannerData(List<String> bannerData) {
        mBannerUrlList.clear();
        mBannerUrlList.addAll(bannerData);
//        通知数据集变化
        mAdapter.notifyDataSetChanged();
        startPlay(mDelayTime);
        mIndicator.setCellCount(bannerData.size());
    }

    /**
     * 开始播放
     *
     * @param delayMillis
     */
    public void startPlay(long delayMillis) {
        isPlay = true;
        mDelayTime = delayMillis;
        play();
    }

    private void play() {
        mCurrentIndex++;
        mHandler.sendEmptyMessageDelayed(PLAY, mDelayTime);
    }

    /**
     * 停止播放
     */
    public void stopPlay() {
        isPlay = false;
    }

    /**
     * 手指滑动时暂停自动轮播，手指松开时重新启动自动轮播
     *
     * @param state
     */
    private void onPageScrollStateChange(int state) {
        if (!mIsAutoScroll) { // 没有自动滑动
            return;
        }
        switch (state) {
            case ViewPager.SCROLL_STATE_IDLE: // 空闲
                if (!mGestureScroll) {
                    return;
                }
                mGestureScroll = false;
//                重新开始发生消息
                mHandler.removeMessages(PLAY);
                mHandler.sendEmptyMessageDelayed(PLAY, 100);
                break;
            case ViewPager.SCROLL_STATE_DRAGGING:
                // 手指滑动时，清除播放下一张，防止滑动过程中自动播放下一张
                mGestureScroll = true;
                mHandler.removeMessages(PLAY);
                break;
            default:
                break;
        }
    }

    /**
     * 滑动的时候更新下标
     *
     * @param position
     */
    public void setScrollPosition(int position) {
        mCurrentIndex = position;
    }

    /**
     * dp转px
     *
     * @param dpVal dp value
     * @return px value
     */
    public int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal,
                getContext().getResources().getDisplayMetrics());
    }

    /**
     * 内部Adapter，包装setAdapter传进来的adapter，设置getCount返回Integer.MAX_VALUE
     */
    private class InnerPagerAdapter extends PagerAdapter {

        public InnerPagerAdapter() {
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @NotNull
        @Override
        public Object instantiateItem(@NotNull ViewGroup container, int position) {
            CardView cardView = new CardView(getContext());
            cardView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            cardView.setCardElevation(5);
            cardView.setRadius(mBannerRadius);

            RoundImageView bannerIv = new RoundImageView(getContext());
            bannerIv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            bannerIv.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            int size = mBannerUrlList.size();
            if (size == 0) {
                return bannerIv;
            }
            position %= size;
            if (position < 0) {
                position = size + position;
            }
            Picasso.get().load(mBannerUrlList.get(position)).into(bannerIv);
            cardView.addView(bannerIv);
            container.addView(cardView);
            return cardView;
        }

        @Override
        public void destroyItem(@NotNull ViewGroup container, int position, @NotNull Object object) {

        }
    }
}

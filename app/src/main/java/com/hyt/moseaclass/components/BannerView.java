package com.hyt.moseaclass.components;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.hyt.moseaclass.R;
import com.hyt.moseaclass.databinding.ViewBannerBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BannerView extends LinearLayout {

    public static final int FLAG_MSG = 0x001;
    private static final String TAG = BannerView.class.getSimpleName();

    private final Context mContext;
    private ViewPager viewPager;
    private LinearLayout linearLayout;

    private List<String> imageUrl = new ArrayList<>();
    //    先前的位置
    private int prePosition = 0;

    private boolean isDragging = false;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == FLAG_MSG) {
                int position = viewPager.getCurrentItem() + 1;
                viewPager.setCurrentItem(position);

                handler.sendEmptyMessageDelayed(FLAG_MSG, 3000);
            }
        }
    };
    private final ArrayList<ImageView> imageViews = new ArrayList<>();

    public BannerView(Context context,ViewGroup parent, List<String> list) {
        super(context, null, 0);
        this.mContext = context;
        this.imageUrl = list;
        init(context, parent);
    }

    public BannerView(Context context, @Nullable AttributeSet attrs,ViewGroup parent,  List<String> list) {
        super(context, attrs, 0);
        this.mContext = context;
        init(context,parent);
    }

    public BannerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, List<String> list) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        Log.e(TAG, "BannerView: xml初始化");
    }

    private void init(Context context,ViewGroup parent) {
        ViewBannerBinding binding = ViewBannerBinding.inflate(LayoutInflater.from(context),parent);
        viewPager = binding.viewPager;
        linearLayout = binding.llPointGroup;
        initView();
        viewPager.setAdapter(new BannerAdapter());
//        初始可向左滑动
        int pos = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % imageUrl.size();
        viewPager.setCurrentItem(pos);
//        启动自动滑动
        handler.sendEmptyMessageDelayed(FLAG_MSG, 3000);
//        拖拽取消自动滑动，松开开始自动滑动
        slideControl();
    }

    private void initView() {
        //        初始化数据
        for (int i = 0; i < imageUrl.size(); i++) {
//            创建新的ImageView，添加到banner中
            RoundImageView roundImageView = new RoundImageView(mContext);
            Picasso.get().load(Uri.parse(imageUrl.get(i))).into(roundImageView);
            imageViews.add(roundImageView);
//            添加指示器
            ImageView point = new ImageView(mContext);
            point.setBackgroundResource(R.drawable.point_selector);
//            初始化指示器的高亮位置
            point.setSelected(i == 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(16,16);
            if (i != 0) {
//                不是第一个指示器，其他圆点的左边距设为8
                params.leftMargin = 16;
            }
            point.setLayoutParams(params);
            linearLayout.addView(point);
        }
    }


    private void slideControl() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                选中指定位置的图片，改变指示器高亮
                int realPosition = position % imageUrl.size();
                linearLayout.getChildAt(prePosition).setSelected(false);
                linearLayout.getChildAt(realPosition).setSelected(true);
                prePosition = realPosition;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
//                    手指拖拽时，停止自动滑动
                    isDragging = true;
                    handler.removeCallbacksAndMessages(null);
                } else if (state == ViewPager.SCROLL_STATE_IDLE && isDragging) {
//                    手指拖拽放开时，开始自动滑动
                    isDragging = false;
                    handler.removeCallbacksAndMessages(null);
                    handler.sendEmptyMessageDelayed(0,3000);
                }
            }
        });

    }


    class BannerAdapter extends PagerAdapter {

        private final String TAG = BannerAdapter.class.getSimpleName();

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            //            获取真实的图片索引
            int realPosition = position % imageUrl.size();
//            获取imageView
            ImageView imageView = imageViews.get(realPosition);
//            添加触摸事件
            imageView.setOnTouchListener((v, event) -> {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
//                        手指按下，取消自动滑动
                        handler.removeCallbacksAndMessages(null);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
//                        手指松开时，开始自动滑动
                        handler.removeCallbacksAndMessages(null);
                        handler.sendEmptyMessageDelayed(0,3000);
                        break;
                }
                return true;
            });
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

}

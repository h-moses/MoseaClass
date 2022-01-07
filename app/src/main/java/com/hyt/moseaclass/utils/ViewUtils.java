package com.hyt.moseaclass.utils;

import android.content.Context;
import android.util.Log;
import android.widget.GridLayout;

import com.hyt.moseaclass.components.CourseCardView;
import com.hyt.moseaclass.data.entity.CourseIntroduction;

import java.util.List;

public class ViewUtils {

    private static final String TAG = ViewUtils.class.getSimpleName();

    public static void initGridLayout(Context context, GridLayout gridLayout, int rowCount, int colCount, List<CourseIntroduction> list) {
        gridLayout.setRowCount(rowCount);
        gridLayout.setColumnCount(colCount);
        for (int i = 0; i < list.size(); i++) {
            CourseIntroduction introduction = list.get(i);
            CourseCardView courseCardView = new CourseCardView(context);
            courseCardView.setData(introduction.getCid(), introduction.getcImage(), introduction.getcName(), introduction.getcInstructor(), introduction.getcDesc());
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = GridLayout.LayoutParams.WRAP_CONTENT;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.rowSpec = GridLayout.spec(i / colCount, 1, 1f);
            params.columnSpec = GridLayout.spec(i % colCount, 1, 1f);
            if (i % colCount == 1) {
                params.leftMargin = 20;
            }
            gridLayout.addView(courseCardView, params);
        }
    }
}

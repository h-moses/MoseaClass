package com.hyt.moseaclass.utils;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.GridLayout;

import com.hyt.moseaclass.components.CourseCardView;
import com.hyt.moseaclass.model.CourseIntroduction;

import java.util.List;

public class ViewUtils {

    private static final String TAG = ViewUtils.class.getSimpleName();

    public static void initGridLayout(Context context, GridLayout gridLayout, int rowCount, int colCount, List<CourseIntroduction> list) {
        gridLayout.setRowCount(rowCount);
        gridLayout.setColumnCount(colCount);
        for (int i = 0; i < list.size(); i++) {
            CourseIntroduction introduction = list.get(i);
            CourseCardView courseCardView = new CourseCardView(context);
            courseCardView.setData(introduction.getcImage(), introduction.getcName(), introduction.getcInstructor());
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = GridLayout.LayoutParams.WRAP_CONTENT;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.rowSpec = GridLayout.spec(i % rowCount, 1, 1f);
            params.columnSpec = GridLayout.spec(i / rowCount, 1, 1f);
            gridLayout.addView(courseCardView, params);
        }
    }
}

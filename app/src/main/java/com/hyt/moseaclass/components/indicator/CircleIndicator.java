package com.hyt.moseaclass.components.indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;

/**
 * 小圆点指示器
 */
public class CircleIndicator extends BaseIndicator {

    public CircleIndicator(Context context) {
        super(context);
    }

    @Override
    public float getCellMargin() {
        return dp2px(8);
    }

    @Override
    public float getCellWidth() {
        return dp2px(8);
    }


    @Override
    protected IndicatorCell getCellView() {
        return new CircleCell(getContext());
    }

    public class CircleCell extends IndicatorCell {


        public CircleCell(Context context) {
            super(context);
        }

        @Override
        public void select() {
            mPaint.setColor(Color.parseColor("#00CC7E"));
        }

        @Override
        public void unSelect() {
            mPaint.setColor(Color.parseColor("#EDEDED"));
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawCircle(getCellWidth() / 2, getCellWidth() / 2, getCellWidth() / 2, mPaint);
        }
    }
}
package com.hyt.moseaclass.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

public class RoundImageView extends androidx.appcompat.widget.AppCompatImageView {

    private final Paint paint;

    public RoundImageView(Context context) {
        this(context, null, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paint = new Paint();
    }

    /**
     * 把资源图片转换成Bitmap
     *
     * @param drawable 资源图片
     * @return 位图
     */
    public static Bitmap getBitmapFromDrawable(Drawable drawable) {
//        得到依赖于设备dpi的图片宽高
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
//        创建位图
//        若支持透明，则使用ARGB_8888，否则使用RGB_565
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
                .getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565);
//        bitmap存储绘制像素，即画布绘制图像，是绘制在底层的bitmap上
        Canvas canvas = new Canvas(bitmap);
//        将自身绘制在画布上
        drawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap getRoundBitmapByShader(Bitmap bitmap, int outWidth, int outHeight, int radius) {
        if (bitmap == null) {
            return null;
        }
//        在该设备上的显示大小，经过之前的依赖于设备的方法调用，已成功转换
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
//        缩放系数
        float widthScale = outWidth * 1f / width;
        float heightScale = outHeight * 1f / height;

//        设置缩放比
        Matrix matrix = new Matrix();
        matrix.setScale(widthScale, heightScale);
        //创建输出的bitmap
        Bitmap desBitmap = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888);
        //创建canvas并传入desBitmap，这样绘制的内容都会在desBitmap上
        Canvas canvas = new Canvas(desBitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //创建着色器，如果着色器超出原始边界范围，会复制边缘颜色。
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //给着色器配置matrix
        bitmapShader.setLocalMatrix(matrix);
        paint.setShader(bitmapShader);
        //创建矩形区域并且预留出border
        RectF rect = new RectF(0, 0, outWidth, outHeight);
        //把传入的bitmap绘制到圆角矩形区域内
        canvas.drawRoundRect(rect, radius, radius, paint);

        return desBitmap;
    }

    /**
     * 绘制圆角矩形图片
     */
    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (null != drawable) {
            Bitmap bitmap = getBitmapFromDrawable(drawable);
            Bitmap b = getRoundBitmapByShader(bitmap, getWidth(), getHeight(), 15);
//            同样大小
            final Rect rectSrc = new Rect(0, 0, b.getWidth(), b.getHeight());
            final Rect rectDest = new Rect(0, 0, getWidth(), getHeight());
            paint.reset();
//            第一个是绘制大小，第二个绘制区域
            canvas.drawBitmap(b, rectSrc, rectDest, paint);
        } else {
            super.onDraw(canvas);
        }
    }

}

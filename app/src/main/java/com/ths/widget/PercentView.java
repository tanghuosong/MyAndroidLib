package com.ths.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * created by tanghuosong 2017/5/26
 * description:
 **/
public class PercentView extends View{

    private final static String TAG = PercentView.class.getSimpleName();
    private Paint mPaint;
    private RectF oval;

    //自定义view ，三个构造函数

    public PercentView(Context context) {
        super(context);
        init();
    }

    public PercentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PercentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        oval=new RectF();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        Log.e(TAG, "onMeasure--widthMode-->" + widthMode);
//        (1) UPSPECIFIED :父容器对于子容器没有任何限制,子容器想要多大就多大.
//        (2) EXACTLY父容器已经为子容器设置了尺寸,子容器应当服从这些边界,不论子容器想要多大的空间.
//        (3) AT_MOST子容器可以是声明大小内的任意大小.
        switch (widthMode) {
            case MeasureSpec.EXACTLY:

                break;
            case MeasureSpec.AT_MOST:

                break;
            case MeasureSpec.UNSPECIFIED:

                break;
        }
        Log.e(TAG, "onMeasure--widthSize-->" + widthSize);
        Log.e(TAG, "onMeasure--heightMode-->" + heightMode);
        Log.e(TAG, "onMeasure--heightSize-->" + heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e(TAG, "onLayout");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 拿到画笔，设置画笔颜色
        mPaint.setColor(Color.GRAY);
        // FILL填充, STROKE描边,FILL_AND_STROKE填充和描边
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        int with = getWidth();
        int height = getHeight();
        Log.e(TAG, "onDraw---->" + with + "*" + height);
        float radius = with / 4;
        canvas.drawCircle(with / 2, with / 2, radius, mPaint);
        mPaint.setColor(Color.BLUE);
        oval.set(with / 2 - radius, with / 2 - radius, with / 2
                + radius, with / 2 + radius);//用于定义的圆弧的形状和大小的界限
        canvas.drawArc(oval, 270, 120, true, mPaint);  //根据进度画圆弧
    }
}

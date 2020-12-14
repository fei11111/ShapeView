package com.fei.shapeview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @ClassName: ShapeView
 * @Description: java类作用描述
 * @Author: Fei
 * @CreateDate: 2020-12-14 20:10
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-12-14 20:10
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ShapeView extends View {

    private int mCircleColor = Color.GREEN;
    private int mRectColor = Color.BLUE;
    private int mTriangleColor = Color.YELLOW;
    private Shape mCurrentShape = Shape.SHAPE_RECT;
    private Paint mPaint;
    private Path mPath;
    private int mCenter;

    public enum Shape {
        SHAPE_RECT,
        SHAPE_CIRCLE,
        SHAPE_TRIANGLE
    }

    public ShapeView(Context context) {
        this(context, null);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttrs(context, attrs);
        initPaint();
        initPath();
    }

    private void initPath() {
        mPath = new Path();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
    }

    /**
     * 获取属性
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShapeView);
        mRectColor = typedArray.getColor(R.styleable.ShapeView_rectColor, mRectColor);
        mCircleColor = typedArray.getColor(R.styleable.ShapeView_circleColor, mCircleColor);
        mTriangleColor = typedArray.getColor(R.styleable.ShapeView_triangleColor, mTriangleColor);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int min = Math.min(width, height);
        mCenter = min / 2;
        setMeasuredDimension(min, min);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        switch (mCurrentShape) {
            case SHAPE_RECT:
                //画正方形
                mPaint.setColor(mRectColor);
                canvas.drawRect(getPaddingLeft(), getPaddingTop(),
                        getWidth() - getPaddingRight(), getHeight() - getPaddingBottom(), mPaint);
                break;
            case SHAPE_CIRCLE:
                //画圆形
                mPaint.setColor(mCircleColor);
                canvas.drawCircle(mCenter, mCenter, mCenter - getPaddingLeft(), mPaint);
                break;
            case SHAPE_TRIANGLE:
                //画三角形
                mPaint.setColor(mTriangleColor);
                mPath.moveTo(mCenter, 0);
                mPath.lineTo(getPaddingLeft(), (float) ((getWidth() - getPaddingLeft() - getPaddingRight()) / 2 * Math.sqrt(3)));
                mPath.lineTo(getWidth() - getPaddingRight(), (float) ((getWidth() - getPaddingLeft() - getPaddingRight()) / 2 * Math.sqrt(3)));
                mPath.close();
                canvas.drawPath(mPath, mPaint);
                break;
        }
    }

    public void exchangeShape() {
        switch (mCurrentShape) {
            case SHAPE_RECT:
                mCurrentShape = Shape.SHAPE_CIRCLE;
                break;
            case SHAPE_CIRCLE:
                mCurrentShape = Shape.SHAPE_TRIANGLE;
                break;
            case SHAPE_TRIANGLE:
                mCurrentShape = Shape.SHAPE_RECT;
                break;
        }
        invalidate();
    }
}

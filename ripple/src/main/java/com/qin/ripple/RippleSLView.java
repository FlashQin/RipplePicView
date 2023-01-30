package com.qin.ripple;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;



import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 涟漪效果
 * <p>
 * Created by flashqin on 2022-03-07.
 */
public class RippleSLView extends View {

    private Context mContext;

    // 画笔对象
    private Paint mPaint;
    private Paint mPaintPic1;
    private Paint mPaintPic2;
    private Paint mPaintPic3;
    private Paint mPaintPic4;

    private int initWith=100;
    // View宽
    private float mWidth;

    // View高
    private float mHeight;

    // 声波的圆圈集合
    private List<Circle> mRipples;

    private int sqrtNumber;

    // 圆圈扩散的速度
    private int mSpeed;

    // 圆圈之间的密度
    private int mDensity;

    // 圆圈的颜色
    private int mColor;

    // 圆圈是否为填充模式
    private boolean mIsFill;

    // 圆圈是否为渐变模式
    private boolean mIsAlpha;
    private boolean isDraw = false;
    List<Bitmap> bitmapList = new ArrayList<>();
    Integer[] pics = new Integer[]{R.drawable.zushou, R.drawable.yh1, R.drawable.yh2, R.drawable.yh3, R.drawable.yh4, R.drawable.yh5, R.drawable.yh6, R.drawable.yh7, R.drawable.yh8};

    public RippleSLView(Context context) {
        this(context, null);
    }

    public RippleSLView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RippleSLView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 获取用户配置属性
        TypedArray tya = context.obtainStyledAttributes(attrs, R.styleable.mRippleView);
        mColor = tya.getColor(R.styleable.mRippleView_cColor, Color.BLUE);
        mSpeed = tya.getInt(R.styleable.mRippleView_cSpeed, 1);
        mDensity = tya.getInt(R.styleable.mRippleView_cDensity, 10);
        mIsFill = tya.getBoolean(R.styleable.mRippleView_cIsFill, false);
        mIsAlpha = tya.getBoolean(R.styleable.mRippleView_cIsAlpha, false);
        tya.recycle();

        for (int i = 0; i < pics.length; i++) {
            Bitmap buttonImg = setImgSize(BitmapFactory.decodeResource(context.getResources(), pics[i]), 100, 100);
            bitmapList.add(buttonImg);
        }
        init();
    }

    public Bitmap setImgSize(Bitmap bm, float newWidth, float newHeight) {
        // 获得图片的宽高.
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例.
        float scaleWidth = newWidth / width;
        float scaleHeight = newHeight / height;
        // 取得想要缩放的matrix参数.
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片.
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

    private void init() {
        mContext = getContext();

        // 设置画笔样式
        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setStrokeWidth(DensityUtil.dip2px(mContext, 2));
        if (mIsFill) {
            mPaint.setStyle(Paint.Style.FILL);
        } else {
            mPaint.setStyle(Paint.Style.STROKE);
        }
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
        initImgPain();


        // 添加第一个圆圈
        mRipples = new ArrayList<>();
        Circle c = new Circle(initWith, 255, 0, getBitmapList());
        mRipples.add(c);

        mDensity = DensityUtil.dip2px(mContext, mDensity);

        // 设置View的圆为半透明
        setBackgroundColor(Color.TRANSPARENT);
    }

    public void initImgPain() {
        mPaintPic1 = new Paint();
        mPaintPic1.setColor(mColor);
        mPaintPic1.setStrokeWidth(DensityUtil.dip2px(mContext, 3));
        if (mIsFill) {
            mPaintPic1.setStyle(Paint.Style.FILL);
        } else {
            mPaintPic1.setStyle(Paint.Style.STROKE);
        }
        mPaintPic1.setStrokeCap(Paint.Cap.ROUND);
        mPaintPic1.setAntiAlias(true);

        mPaintPic2 = new Paint();
        mPaintPic2.setColor(mColor);
        mPaintPic2.setStrokeWidth(DensityUtil.dip2px(mContext, 1));
        if (mIsFill) {
            mPaintPic2.setStyle(Paint.Style.FILL);
        } else {
            mPaintPic2.setStyle(Paint.Style.STROKE);
        }
        mPaintPic2.setStrokeCap(Paint.Cap.ROUND);
        mPaintPic2.setAntiAlias(true);

        mPaintPic3 = new Paint();
        mPaintPic3.setColor(mColor);
        mPaintPic3.setStrokeWidth(DensityUtil.dip2px(mContext, 1));
        if (mIsFill) {
            mPaintPic3.setStyle(Paint.Style.FILL);
        } else {
            mPaintPic3.setStyle(Paint.Style.STROKE);
        }
        mPaintPic3.setStrokeCap(Paint.Cap.ROUND);
        mPaintPic3.setAntiAlias(true);

        mPaintPic4 = new Paint();
        mPaintPic4.setColor(mColor);
        mPaintPic4.setStrokeWidth(DensityUtil.dip2px(mContext, 1));
        if (mIsFill) {
            mPaintPic4.setStyle(Paint.Style.FILL);
        } else {
            mPaintPic4.setStyle(Paint.Style.STROKE);
        }
        mPaintPic4.setStrokeCap(Paint.Cap.ROUND);
        mPaintPic4.setAntiAlias(true);
    }

    public void start() {
        isDraw = true;
        invalidate();
    }

    public void stop() {
        isDraw = false;
        mRipples.clear();
        Circle c = new Circle(initWith, 255, 0, getBitmapList());
        mRipples.add(c);
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 内切正方形
        drawInCircle(canvas);
        //drawNomal(canvas);

        // 外切正方形
        // drawOutCircle(canvas);
    }


    /**
     * 圆到宽度
     *
     * @param canvas
     */
    private void drawInCircle(Canvas canvas) {
        if (isDraw) {
            canvas.save();
            // 处理每个圆的宽度和透明度
            for (int i = 0; i < mRipples.size(); i++) {
                LogUtil.d("圆的个数" + mRipples.size());
                Circle c = mRipples.get(i);
                mPaint.setAlpha(c.alpha);// （透明）0~255（不透明）
                mPaintPic1.setAlpha(c.alpha);// （透明）0~255（不透明）
                mPaintPic2.setAlpha(c.alpha);// （透明）0~255（不透明）
                mPaintPic3.setAlpha(c.alpha);// （透明）0~255（不透明）
                mPaintPic4.setAlpha(c.alpha);// （透明）0~255（不透明）
                float radis = c.width - mPaint.getStrokeWidth();
                float x1 = (mWidth / 2) + Math.round(Math.sin(Math.toRadians(c.angle)) * radis);
                float y1 = (mHeight / 2) + Math.round(Math.cos(Math.toRadians(c.angle)) * radis);

                float x2 = (mWidth / 2) + Math.round(Math.sin(Math.toRadians(c.angle + 110)) * radis);
                float y2 = (mHeight / 2) + Math.round(Math.cos(Math.toRadians(c.angle + 110)) * radis);

                float x3 = (mWidth / 2) + Math.round(Math.sin(Math.toRadians(c.angle + 200)) * radis);
                float y3 = (mHeight / 2) + Math.round(Math.cos(Math.toRadians(c.angle + 200)) * radis);

                float x4 = (mWidth / 2) + Math.round(Math.sin(Math.toRadians(c.angle + 290)) * radis);
                float y4 = (mHeight / 2) + Math.round(Math.cos(Math.toRadians(c.angle + 290)) * radis);


                canvas.drawBitmap(c.listBitmap.get(0), x1, y1, mPaintPic1);
                canvas.drawBitmap(c.listBitmap.get(1), x2, y2, mPaintPic2);
                canvas.drawBitmap(c.listBitmap.get(2), x3, y3, mPaintPic3);
                //canvas.drawBitmap(c.listBitmap.get(3), x4, y4, mPaintPic4);


                canvas.drawCircle(mWidth / 2, mHeight / 2, radis, mPaint);
                LogUtil.d(mWidth / 2 + "圆xin坐标" + mHeight / 2);
                // 当圆超出View的宽度后删除
                if (c.width > mWidth / 2) {
                    mRipples.remove(i);
                } else {
                    // 计算不透明的数值，这里有个小知识，就是如果不加上double的话，255除以一个任意比它大的数都将是0
                    if (mIsAlpha) {
                        double alpha = 255 - c.width * (255 / ((double) mWidth / 2));
                        c.alpha = (int) alpha;
                    }
                    // 修改这个值控制速度
                    c.width += mSpeed;
                    c.angle += mSpeed;
                }

            }


            // 里面添加圆
            if (mRipples.size() > 0) {
                // 控制第二个圆出来的间距
                if (mRipples.get(mRipples.size() - 1).width > DensityUtil.dip2px(mContext, mDensity*3)) {
                    mRipples.add(new Circle(initWith, 255, 0, getBitmapList()));
                }
            }


            invalidate();

            canvas.restore();
        }
    }


    /**
     * 圆到对角线
     *
     * @param canvas
     */
    private void drawOutCircle(Canvas canvas) {
        canvas.save();

        // 使用勾股定律求得一个外切正方形中心点离角的距离
        sqrtNumber = (int) (Math.sqrt(mWidth * mWidth + mHeight * mHeight) / 2);

        // 变大
        for (int i = 0; i < mRipples.size(); i++) {

            // 启动圆圈
            Circle c = mRipples.get(i);
            mPaint.setAlpha(c.alpha);// （透明）0~255（不透明）
            canvas.drawCircle(mWidth / 2, mHeight / 2, c.width - mPaint.getStrokeWidth(), mPaint);

            // 当圆超出对角线后删掉
            if (c.width > sqrtNumber) {
                mRipples.remove(i);
            } else {
                // 计算不透明的度数
                double degree = 255 - c.width * (255 / (double) sqrtNumber);
                c.alpha = (int) degree;
                c.width += 1;
            }
        }

        // 里面添加圆
        if (mRipples.size() > 0) {
            // 控制第二个圆出来的间距
            if (mRipples.get(mRipples.size() - 1).width == 50) {
                mRipples.add(new Circle(initWith, 255, 0, getBitmapList()));
            }
        }
        invalidate();
        canvas.restore();
    }

    public List<Bitmap> getBitmapList() {
        List<Bitmap> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(bitmapList.get(new Random().nextInt(bitmapList.size())));

        }

        return list;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int myWidthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int myWidthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int myHeightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int myHeightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        // 获取宽度
        if (myWidthSpecMode == MeasureSpec.EXACTLY) {
            // match_parent
            mWidth = myWidthSpecSize;
        } else {
            // wrap_content
            mWidth = DensityUtil.dip2px(mContext, 120);
        }

        // 获取高度
        if (myHeightSpecMode == MeasureSpec.EXACTLY) {
            mHeight = myHeightSpecSize;
        } else {
            // wrap_content
            mHeight = DensityUtil.dip2px(mContext, 120);
        }

        // 设置该view的宽高
        setMeasuredDimension((int) mWidth, (int) mHeight);
    }


    class Circle {
        Circle(int width, int alpha, int angle, List<Bitmap> listBitmap) {
            this.width = width;
            this.alpha = alpha;
            this.angle = alpha;
            this.listBitmap = listBitmap;
        }

        int width;
        int angle;
        int alpha;
        List<Bitmap> listBitmap;
    }

}
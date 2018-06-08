package com.example.b0917.tp_tea;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

import com.example.b0917.tp_tea.R;

public class LuckyPanView extends SurfaceView implements Callback, Runnable {
    private SurfaceHolder mHolder;
    /**
     * 與holder綁定的canva;
     */
    private Canvas mCanvas;
    /**
     * 用於繪製的線程
     */
    private Thread t;
    /**
     * 線程的開關
     */
    private boolean isRunning;
    /**
     * 轉盤上的文字
     */
    private String[] mStrs = new String[]{"再來一杯", "銘謝惠顧", "第二杯六折", "下次再來",
                                           "再玩一次", "買二送二"};
    /**
     * 轉盤上的顏色
     */
    private int[] mColors = new int[]{0xFFFF6565, 0xFFEF4182, 0xFF8A7CCB,
            0xFF42B3D0, 0xFF30BAA3, 0xFFF8B195};
    /**
     * 盤子的數量
     */
    private int mItemCount = 6;
    /**
     * 繪製盤子的範圍
     */
    private RectF mRange = new RectF();
    /**
     * 圓的直徑
     */
    private int mRadius;
    /**
     * 繪製盤快的畫筆
     */
    private Paint mArcPaint;
    /**
     * 繪製文字的畫筆
     */
    private Paint mTextPaint;
    /**
     * 滾動速度
     */
    private double mSpeed;
    private volatile float mStartAngle = 0;
    /**
     * 停止的開關
     */
    private boolean isShouldEnd;
    /**
     * 中心位置
     */
    private int mCenter;
    /**
     * 控件的padding
     */
    private int mPadding;
    /**
     * 背景图的bitmap
     */
    private Bitmap mBgBitmap = BitmapFactory.decodeResource(getResources(),
            R.drawable.blackbg);
    private Bitmap mBgBitmap2 = BitmapFactory.decodeResource(getResources(),
            R.mipmap.circle_bg);
    /**
     * 文字的大小
     */
    private float mTextSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, 20, getResources().getDisplayMetrics());

    public LuckyPanView(Context context) {
        this(context, null);
    }

    public LuckyPanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHolder = getHolder();
        mHolder.addCallback(this);

        setZOrderOnTop(true);// 设置画布 背景透明
        setZOrderMediaOverlay(true);
        mHolder.setFormat(PixelFormat.TRANSLUCENT);

        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = Math.min(getMeasuredWidth(), getMeasuredHeight());
        // 获取圆形的直径
        mRadius = width - getPaddingLeft() - getPaddingRight();
        // padding值
        mPadding = getPaddingLeft();
        // 中心点
        mCenter = width / 2;
        setMeasuredDimension(width, width);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // 初始化绘制圆弧的画笔
        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setDither(true);
        // 初始化绘制文字的画笔
        mTextPaint = new Paint();
        mTextPaint.setColor(0xFFffffff);
        mTextPaint.setTextSize(mTextSize);
        // 圆弧的绘制范围
        mRange = new RectF(getPaddingLeft(), getPaddingLeft(), mRadius
                + getPaddingLeft(), mRadius + getPaddingLeft());

        /* 开启线程*/
        isRunning = true;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // 通知关闭线程
        isRunning = false;
    }

    @Override
    public void run() {
    // 不断的进行draw
        while (isRunning) {
            long start = System.currentTimeMillis();
            draw();
            long end = System.currentTimeMillis();
            try {
                if (end - start < 50) {
                    Thread.sleep(50 - (end - start));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    private void draw()
    {
        try
        {
            // 获得canvas
            mCanvas = mHolder.lockCanvas();
            if (mCanvas != null)
            {
                // 绘制背景图
                drawBg();

                /**
                 * 绘制每个块块，每个块块上的文本，每个块块上的图片
                 */
                float tmpAngle = mStartAngle;
                float sweepAngle = (float) (360 / mItemCount);
                for (int i = 0; i < mItemCount; i++)
                {
                    // 绘制快快
                    mArcPaint.setColor(mColors[i]);
                    mCanvas.drawArc(mRange, tmpAngle, sweepAngle, true,
                            mArcPaint);
                    // 绘制文本
                    drawText(tmpAngle, sweepAngle, mStrs[i]);

                    tmpAngle += sweepAngle;
                }

                // 如果mSpeed不等于0，则相当于在滚动
                mStartAngle += mSpeed;

                // 点击停止时，设置mSpeed为递减，为0值转盘停止
                if (isShouldEnd)
                {
                    mSpeed -= 1;
                }
                if (mSpeed <= 0)
                {
                    mSpeed = 0;
                    isShouldEnd = false;
                }
                // 根据当前旋转的mStartAngle计算当前滚动到的区域
                calInExactArea(mStartAngle);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            if (mCanvas != null)
                mHolder.unlockCanvasAndPost(mCanvas);
        }

    }

    private void drawBg()
    {
        mCanvas.drawBitmap(mBgBitmap, null, new Rect(mPadding/200,
                mPadding/200, getMeasuredWidth() - mPadding / 200,
                getMeasuredWidth() - mPadding / 200), null);
        mCanvas.drawBitmap(mBgBitmap2,null,new Rect(mPadding/2,
                mPadding/2,getMeasuredWidth()-mPadding/2,
                getMeasuredWidth() -mPadding/2),null);
    }

    /**
     * 根据当前旋转的mStartAngle计算当前滚动到的区域
     *
     * @param startAngle
     */
    public void calInExactArea(float startAngle)
    {
        // 让指针从水平向右开始计算
        float rotate = startAngle + 90;
        rotate %= 360.0;
        for (int i = 0; i < mItemCount; i++)
        {
            // 每个的中奖范围
            float from = 360 - (i + 1) * (360 / mItemCount);
            float to = from + 360 - (i) * (360 / mItemCount);

            if ((rotate > from) && (rotate < to))
            {
                Log.d("TAG", mStrs[i]);
                return ;
            }
        }
    }

    /**
     * 绘制文本
     *
     * @param startAngle
     * @param sweepAngle
     * @param string
     */
    private void drawText(float startAngle, float sweepAngle, String string)
    {
        Path path = new Path();
        path.addArc(mRange, startAngle, sweepAngle);
        float textWidth = mTextPaint.measureText(string);
        // 利用水平偏移让文字居中
        float hOffset = (float) (mRadius * Math.PI / mItemCount / 2 - textWidth / 2);// 水平偏移
        float vOffset = mRadius / 2 / 6;// 垂直偏移
        mCanvas.drawTextOnPath(string, path, hOffset, vOffset, mTextPaint);
    }

    /**
     * 点击开始旋转
     *
     * @param luckyIndex
     */
    public void luckyStart(int luckyIndex)
    {
        // 每项角度大小
        float angle = (float) (360 / mItemCount);
        // 中奖角度范围（因为指针向上，所以水平第一项旋转到指针指向，需要旋转210-270；）
        float from = 270 - (luckyIndex + 1) * angle;
        float to = from + angle;
        // 停下来时旋转的距离
        float targetFrom = 4 * 360 + from;
        /**
         * <pre>
         *  (v1 + 0) * (v1+1) / 2 = target ;
         *  v1*v1 + v1 - 2target = 0 ;
         *  v1=-1+(1*1 + 8 *1 * target)/2;
         * </pre>
         */
        float v1 = (float) (Math.sqrt(1 * 1 + 8 * 1 * targetFrom) - 1) / 2;
        float targetTo = 4 * 360 + to;
        float v2 = (float) (Math.sqrt(1 * 1 + 8 * 1 * targetTo) - 1) / 2;

        mSpeed = (float) (v1 + Math.random() * (v2 - v1));
        isShouldEnd = false;
    }

    public void luckyEnd()
    {
        mStartAngle = 0;
        isShouldEnd = true;
    }

    public boolean isStart()
    {
        return mSpeed != 0;
    }

    public boolean isShouldEnd()
    {
        return isShouldEnd;
    }

    public String setLucky(int num){
        String text;
        text = mStrs[num];
        return text;
    }
}

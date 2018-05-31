package com.example.b0917.tp_tea;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import com.example.b0917.tp_tea.DensityUtil;
import com.example.b0917.tp_tea.R;

public class Circleview extends android.support.v7.widget.AppCompatImageView implements Runnable{
    private Bitmap mHourBitmap;
    private boolean binitComplete = false;
    private boolean stopRoter = true;
    float Angel = 0.0f;
    Matrix matx = new Matrix();
    float maxAngel = 0.0f;
    int screnWidth = 0;

    public Circleview(Context context,int width) {
        super(context);
        this.screnWidth = width;
        init();
        new Thread(this).start();
    }

    public void  init(){
        mHourBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.share_lottery_pointer);
        binitComplete = true;

    }

    public void setRotate_degree(float degree){Angel =degree;}

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        matx.reset();
        canvas.drawColor(Color.TRANSPARENT);

        if(!binitComplete){
            return;
        }
        Paint localPaint = new Paint();
        localPaint.setAntiAlias(true);
        localPaint.setFilterBitmap(true);
        /**
                *  初始指針
                */
        matx.setTranslate(this.screnWidth/2-mHourBitmap.getWidth()/2,DensityUtil.dip2px(getContext(),
                300)/2-mHourBitmap.getHeight()+DensityUtil.dip2px(getContext(),20));
        /**
                * 繞點旋轉
                */
        matx.preRotate(Angel,mHourBitmap.getWidth()/2,mHourBitmap.getHeight()*4/5);
        canvas.drawBitmap(mHourBitmap,matx,localPaint);
    }
    @Override
    public void run() {
        try {
         while (true){
             if(!isStopRoter()){
                 if(maxAngel == 0&&Angel>=maxAngel){
                     setStopRoter(true);
                     maxAngel = 0.0f;
                 }
                 else {
                     if(maxAngel-Angel<360){
                         setRotate_degree(Angel+=10);
                     }else {
                         setRotate_degree(Angel+=15);
                     }
                     this.postInvalidate();
                     Thread.sleep(50);
                 }
             }
         }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
         * 获取当前的角度，并设置停止角度
         * @param place 位置
         * @return
         */
    public void setStopPlace(int place){
        getRoterByPlace(place);
    }
    /**
        * 顺时针旋转
        * 1 = 330-30
        * 2 = 30-90
        * 3 = 90-150
        * 4 = 150-210
        * 5 = 210-270
        * 6 = 270-330
        * @param place
        * @return
        */
    void getRoterByPlace(int place){
        float roter = getRoteCenter(place);
        float currentRoter = getCurrentRoter();
        //如果当前的角度小于位置的角度，则表示需要多转多少角度
        float difRoter = currentRoter - roter;
        //固定三圈360*3，后在加上当前的角度差
        maxAngel = Angel + 360*2 + 360-difRoter;
    }
    /**
        * 得到奖项位置的角度 -转盘360度 根据奖项取各个奖项的平均值，在设置指定各个奖项的中间点
        * @param place
        * @return
        */
    float getRoteCenter(int place){
        float roter = 0.0f;
        switch (place){
            case 1:
                roter = 0;
                break;
            case 2:
                roter = 60/2 + 30;
                break;
            case 3:
                roter = 60/2 + 90;
                break;
            case 4:
                roter = 60/2 + 150;
                break;
            case 5:
                roter = 60/2 + 210;
                break;
            case 6:
                roter = 60/2 + 270;
                break;
            default:
                break;
        }
        return roter;
    }
    /**
        * 得到转动的实际角度--换算角度值
        * @return
        */
    float getCurrentRoter(){
        int current = (int) Angel/360;
        if(0==current){
            return Angel;
        }
        float roter = Angel-360*current;
        return roter;
    }
    public boolean isStopRoter() {
        return stopRoter;
    }

    public void setStopRoter(boolean stopRoter) {
        this.stopRoter = stopRoter;
    }


}

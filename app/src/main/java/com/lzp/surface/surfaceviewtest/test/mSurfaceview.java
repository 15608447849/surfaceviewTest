package com.lzp.surface.surfaceviewtest.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;

import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Administrator on 2016/7/26.
 * SurfaceView的使用
 首先继承SurfaceView，并实现SurfaceHolder.Callback接口，实现它的三个方法：surfaceCreated，surfaceChanged，surfaceDestroyed。


 surfaceCreated(SurfaceHolder holder)：surface创建的时候调用，一般在该方法中启动绘图的线程。
 surfaceChanged(SurfaceHolder holder, int format, int width,int height)：surface尺寸发生改变的时候调用，如横竖屏切换。
 surfaceDestroyed(SurfaceHolder holder) ：surface被销毁的时候调用，如退出游戏画面，一般在该方法中停止绘图线程。
 还需要获得SurfaceHolder，并添加回调函数，这样这三个方法才会执行。
 */

public class mSurfaceview extends SurfaceView implements SurfaceHolder.Callback,Runnable{


    private static final String  TAG = mSurfaceview.class.getName();

    /**
     * 构造 1
     * @param context
     */
    public mSurfaceview(Context context) {
        super(context);
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);

        //设置画布背景不为黑色　继承Sureface时这样处理才能透明
        setZOrderOnTop(true);//Order 顺序
        mSurfaceHolder.setFormat(PixelFormat.TRANSLUCENT);//透明 前景

        //背景色  开个方法对外公开
        setBackgroundColor(Color.parseColor(bgColor));
        //设置透明 开方法对外公开
         getBackground().setAlpha(bgalpha);
    }

    /**
     * 构造 2
     * @param
     */
    public mSurfaceview(Context context,boolean isMove) {
        this(context);

        this.isMove = isMove;
        setLoop(isMove());
    }













/**-----------------------------------------------------------overriee-----------------------------------------------------------------------------------------------------*/
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG,"getwidth():"+this.getWidth());

                        if(isMove){//滚动效果
                         if(orientation == MOVE_LEFT){//向左
                                 x = getWidth();
                             }else{//向右
                                 x = -(content.length()*10);
                             }
                         new Thread(this).start();
                     }else{//不滚动只画一次
                         draw();
                     }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i(TAG,"surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        loop = false;//停止线程
    }

    @Override
    public void run() {

                         while(loop){
                         synchronized (mSurfaceHolder) {
                                 draw();
                             }
                         try{
                                 Thread.sleep(speed);
                             }catch(InterruptedException ex){
                                 Log.e("TextSurfaceView",ex.getMessage()+"\n"+ex);
                             }
                            }
                        content = null;

    }

    /** -----------------------------------------------------------overriee-----------------------------------------------------------------------------------------------------










     /* *
     * 绘制
     */
    private void draw(){
        Log.i(TAG, "draw: start");
        //锁定画布
        Canvas canvas = mSurfaceHolder.lockCanvas();


        if(mSurfaceHolder == null || canvas == null) {
            Log.i(TAG, "draw: mSurfaceHolder 不存在"+mSurfaceHolder);
        return;
        }
        Paint paint = new Paint();

        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);   //清屏
        paint.setAntiAlias(true);//锯齿


        paint.setTypeface(Typeface.SANS_SERIF);//字体

        paint.setTextSize(fontSize);//字体大小

        paint.setColor(Color.parseColor(fontColor));//字体颜色

        paint.setAlpha(fontAlpha);//字体透明度
        canvas.drawText(content,x,(getHeight()/2+5), paint);//画文字


        mSurfaceHolder.unlockCanvasAndPost(canvas);//解锁显示


        //滚动
        if(isMove){
            //内容所占像素
            float conlen = paint.measureText(content);
            //组件宽度
            int w = getWidth();
            //方向
            if(orientation == MOVE_LEFT) {//向左
                if(x< -conlen){
                    x = w;
                } else{
                    x -= 2;
                }

            }else if (orientation == MOVE_RIGHT){//右边
                if(x >= w){
                    x = -conlen;
                }else{
                    x+=2;
                }

            }
        }
    }















































    //////////////////////////////////////////属性
    /**
     * 是否滚动
     */
    private boolean         isMove = false;
    /**
     * 移动方向
     */
    private int             orientation = MOVE_RIGHT;

    /**
     * 向左
     */
    public static final int MOVE_LEFT = 0;
    /**
     * 向右
     */
    public static final int MOVE_RIGHT = 1;

    /**
     * 移动速度 1.5s 一次
     */
    private long speed = 1500;

    /**
     * 字幕内容
     */
    private String content = "暂无内容";

    /**
     * 字幕背景色
     */
    private String bgColor = "#E7E7E7";
    /**
     * 背景透明度
     */
    private int bgalpha = 60;
    /**
     * 字体颜色
     */
    private String fontColor = "#000000";//"#FFFFFF";
    /**
     * 字体透明度
     * 0-255 透明->不透明
     */
    private int fontAlpha = 255;
    /**
     * 字体大小20
     */
    private float  fontSize = 20f;
    /**
     * 容器
     */
    private SurfaceHolder mSurfaceHolder ;
    /**
     * 线程控制
     */
    private boolean loop = true;
    /**
     * 内容滚动位置
     *  起始坐标
     */
    private float x = 0;







//get set
    public boolean isMove() {
                 return isMove;
             }
    public void setMove(boolean isMove) {
                 this.isMove = isMove;
             }
         public void setLoop(boolean loop) {
                 this.loop = loop;
             }
         public void setContent(String content) {
                 this.content = content;
             }
         public void setBgColor(String bgColor) {
                 this.bgColor = bgColor;
             }
         public void setBgalpha(int bgalpha) {
                 this.bgalpha = bgalpha;
             }
         public void setFontColor(String fontColor) {
                 this.fontColor = fontColor;
             }
         public void setFontAlpha(int fontAlpha) {
                 this.fontAlpha = fontAlpha;
             }
         public void setFontSize(float fontSize) {
                 this.fontSize = fontSize;
             }


    private int getOrientation() {
                 return orientation;
             }

    public void setOrientation(int orientation) {
                 this.orientation = orientation;
             }

    private long getSpeed() {
                 return speed;
             }

    public void setSpeed(long speed) {
                 this.speed = speed;
             }










}

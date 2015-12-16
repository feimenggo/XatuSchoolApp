package xatu.school.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.io.InputStream;

import xatu.school.R;

public class ChangeColorMyView extends View {
    private int mColor = 0xFF45C01A;  //默认值 绿色
    private Bitmap mIconBitmap;
    private String mText = "微信";
    private int mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12,
            getResources().getDisplayMetrics()); //默认值12sp

    //用于绘制可变色图标
    private Canvas mCanvas;
    private Bitmap mBitmap;
    private Paint mPaint;

    private float mAlpha;   //用于渐变的透明度

    private Rect mIconRect;
    private Rect mTextBound;

    private Paint mTextPaint;

    public ChangeColorMyView(Context context) {
        this(context, null);
    }

    public ChangeColorMyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChangeColorMyView(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        //获得自定义属性存入a数组中
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ChangeColorMyView);
        //遍历TypeArray中的所有属性值，得到我们在xml文件中自定义属性的值，再赋值给成员变量，再赋给控件
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.ChangeColorMyView_myicon:  //得到自定义图标的值
                    //得到一个自定义属性的值，得到的drawable类型
                    BitmapDrawable drawable = (BitmapDrawable) a.getDrawable(attr);
                    mIconBitmap = drawable.getBitmap();//将的到的drawable转换为bitmap类型
                    break;
                case R.styleable.ChangeColorMyView_mycolor:
                    //得到attr中也就是我们布局文件中赋给自定义属性的值
                    mColor = a.getColor(attr, 0xFF45C01A);
                    break;
                case R.styleable.ChangeColorMyView_mytext:
                    //得到attr中也就是我们布局文件中赋给自定义属性的值
                    mText = a.getString(attr);
                    break;
                case R.styleable.ChangeColorMyView_mytext_size:
                    //得到attr中也就是我们布局文件中赋给自定义属性的值
                    mTextSize = (int) a.getDimension(attr, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12,
                            getResources().getDisplayMetrics()));
                    break;
            }

        }
        a.recycle();
        mTextBound = new Rect();
        mTextPaint = new Paint();
        mTextPaint.setTextSize(mTextSize);  //设置文本字体大小为我们自定义属性字体大小
        mTextPaint.setColor(0xff555555);   //灰色
        //测量文本字体的范围,这时，TextBound中就包含了绘制text所需要的left，right等，以及宽高
        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获得图标的宽度
        int iconWidth = Math.min(getMeasuredWidth() - getPaddingLeft() -
                getPaddingRight(), getMeasuredHeight() -
                getPaddingTop() - getPaddingBottom() - mTextBound.height());
        //为icon设置绘制范围，需要left,right,top,bottom，也就是得到左上角和右下角坐标
        int left = getMeasuredWidth() / 2 - iconWidth / 2;//横向居中
        int top = (getMeasuredHeight() - mTextBound.height()) / 2 - iconWidth / 2;//除去文本高度之外的高度纵向居中
        mIconRect = new Rect(left, top, left + iconWidth, top + iconWidth);//画了一个矩形，包含了icon和文本文字

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mIconBitmap, null, mIconRect, null);//在mIconRect范围区域绘制了一个bitmap图标，无颜色
        //得到属性的alpha是0~255,Math.ceil()是向上取整
        int alpha = (int) Math.ceil(255 * mAlpha);
        //内存中准备bitmap，先在bitmap中绘制一个(纯色之前设置setAlpah)纯色再绘制(图标之前设置xfermode)图标
      //  setupTargeBitmap(alpha);  //通过改变alpha就可以实现渐变效果

        //绘制图标的文本，绘制变色文本
        drawSourcetText(canvas, alpha);
        drawTargetText(canvas, alpha); //绘制变色文本


      //  canvas.drawBitmap(mBitmap, 0, 0, null);//将纯色图标绘制出来

    }

    //绘制变色文本
    private void drawTargetText(Canvas canvas, int alpha) {
        mTextPaint.setColor(mColor);
       mTextPaint.setAlpha(alpha);

        int x = getMeasuredWidth() / 2 - mTextBound.width() / 2;
        int y = mIconRect.bottom + mTextBound.height();
        canvas.drawText(mText, x, y, mTextPaint); //x,y表示绘制的起始点

    }

    //绘制原文本
    private void drawSourcetText(Canvas canvas, int alpha) {
        mTextPaint.setColor(0xff333333);//灰色
        mTextPaint.setAlpha(255 - alpha);
        int x = getMeasuredWidth() / 2 - mTextBound.width() / 2;
        int y = mIconRect.bottom + mTextBound.height();
        canvas.drawText(mText, x, y, mTextPaint); //x,y表示绘制的起始点
    }

    //内存中绘制可变色的icon
    private void setupTargeBitmap(int alpha) {
        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap); //将纯色图标绘制在mBitmap上，以该bitmap作为画布
        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true); //设锯齿
        mPaint.setDither(true);
        mPaint.setAlpha(alpha);

        mCanvas.drawRect(mIconRect, mPaint);  //为图标设置一个纯色，就在图标的范围上设置
        //一个纯色bitmap，在其之上绘制一个mIconBitmap图标，DST_IN是绘制在两个区域重合的里面的区域
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));//自动根据颜色绘制在需要绘制的区域
        //mPaint.setAlpha(255);
        //纯色bitmap之上的mIconBitmap图标
        mCanvas.drawBitmap(mIconBitmap, null, mIconRect, mPaint);
    }
   //如果我们继承的不是view而是TextView，按home时，他会有自己需要存储的内容
    //第一个值代表TextView自己存储的标志，第二个表示我们自己需要存储的值
    private static final String INSTANCE_STATUS="instance_status";
    private static final String STATUS_ALPHA="status_alpha";
    //防止点击home键或转屏时 重绘activity图标的alpha发生变化
    @Override
    protected Parcelable onSaveInstanceState() {

        Bundle bundle=new Bundle();
        //将其本身父级存储的值放入INSTANCE_STATUS
        bundle.putParcelable(INSTANCE_STATUS, super.onSaveInstanceState());
        //存储我们自己的内容，alpha
        bundle.putFloat(STATUS_ALPHA,mAlpha);
        return bundle;
    }

    //点击home之后，重回activity时
    @Override
    protected void onRestoreInstanceState(Parcelable state) {

        //如果存储的是属于我们自己存的
        if(state instanceof Bundle){
            Bundle bundle= (Bundle) state;
            //取出我们自己存的alpha
            mAlpha=bundle.getFloat(STATUS_ALPHA);
            //取出我们存的系统本身存的东西
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATUS));
            return;
        }
        super.onRestoreInstanceState(state);
    }
    //设置变换图片
    public void setIconBitmap(int iconId){
        InputStream is=getResources().openRawResource(iconId);
        this.mIconBitmap= BitmapFactory.decodeStream(is);
        invalidate();
    }

    public void setIconAlpha(float alpha) {
        this.mAlpha = alpha;
        //重绘
        invalidateView();
    }

    private void invalidateView() {
        //判断是否为ui线程
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();   //如果是UI线程，这样重绘
        } else {
            postInvalidate();  //非Ui线程时，这样重绘
        }

    }
}

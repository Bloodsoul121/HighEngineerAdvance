package com.blood.highengineeradvance.toutiao.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.blood.highengineeradvance.R;
import com.blood.highengineeradvance.util.DensityUtils;

public class CustomPageTextView extends View {

    private static final String ELLIPSIS = "...";
    private Paint mPaint;
    private String mText;
    private float mTextSize;
    private int mTextColor;
    private Bitmap mBitmap;
    private int mBitmapWidth;
    private int mBitmapHeight;
    private float mPaddingLeft;
    private float mPaddingRight;
    private float mPaddingTop;
    private float mPaddingBottom;
    private float mPaddingDrawable;
    private float mPadding;
    private float mEllipsisLength;
    private String mLastText;
    private float mFontSpacing;
    private float mProgress;
    private boolean mIsPage;

    public CustomPageTextView(Context context) {
        super(context);
    }

    public CustomPageTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.custom_page_text_view);
        mText = typedArray.getString(R.styleable.custom_page_text_view_text);
        mTextSize = typedArray.getDimension(R.styleable.custom_page_text_view_textSize, DensityUtils.dp2px(context, 16));
        mTextColor = typedArray.getColor(R.styleable.custom_page_text_view_textColor, Color.parseColor("#000000"));
        int resourceId = typedArray.getResourceId(R.styleable.custom_page_text_view_leftDrawable, -1);
        mPadding = typedArray.getDimension(R.styleable.custom_page_text_view_padding, 0);
        mPaddingLeft = mPaddingRight = mPaddingTop = mPaddingBottom = mPadding;
        mPaddingLeft = typedArray.getDimension(R.styleable.custom_page_text_view_paddingLeft, 0);
        mPaddingRight = typedArray.getDimension(R.styleable.custom_page_text_view_paddingRight, 0);
        mPaddingTop = typedArray.getDimension(R.styleable.custom_page_text_view_paddingTop, 0);
        mPaddingBottom = typedArray.getDimension(R.styleable.custom_page_text_view_paddingBottom, 0);
        mPaddingDrawable = typedArray.getDimension(R.styleable.custom_page_text_view_paddingDrawable, 0);
        typedArray.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);

        if (resourceId > -1) {
            mBitmap = BitmapFactory.decodeResource(getResources(), resourceId);
            mBitmapWidth = mBitmap.getWidth();
            mBitmapHeight = mBitmap.getHeight();
        }

        mEllipsisLength = mPaint.measureText(ELLIPSIS);
        mFontSpacing = mPaint.getFontSpacing();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (TextUtils.isEmpty(mText)) {
            return;
        }
        int textWidth = (int) (mBitmapWidth + mPaint.measureText(mText) + mPaddingLeft + mPaddingRight + mPaddingDrawable);
        int textHeight = (int) (mFontSpacing + mPaddingTop + mPaddingBottom);
        int realWidth = resolveSize(textWidth, widthMeasureSpec);
        int realHeight = resolveSize(textHeight, heightMeasureSpec);
        setMeasuredDimension(realWidth, realHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        float textX = mPaddingLeft;
        float textY = height / 2;

        if (mBitmap != null) {
            float bitmapX = mPaddingLeft;
            float bitmapY = height / 2 - mBitmapHeight / 2;
            canvas.drawBitmap(mBitmap, bitmapX, bitmapY, mPaint);

            textX += (mBitmapWidth + mPaddingDrawable);
        }

        if (TextUtils.isEmpty(mText)) {
            return;
        }

        canvas.save();

        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float diffY = (- fontMetrics.top - fontMetrics.bottom) / 2;
        textY += diffY;

        canvas.clipRect(textX, textY + fontMetrics.top, width, textY + fontMetrics.bottom);

        if (mIsPage) {
            drawText(canvas, width, textX, textY, mLastText);
            drawText(canvas, width, textX, textY + mFontSpacing, mText);
        } else {
            drawText(canvas, width, textX, textY, mText);
        }
        canvas.restore();

    }

    private void drawText(Canvas canvas, int width, float textX, float textY, String text) {
        float textLength = mPaint.measureText(text);
        float diffX = textX + textLength - width;
        if (diffX < 0) {
            canvas.drawText(text, textX, textY - mFontSpacing * mProgress, mPaint);
        } else {
            int count = 0;
            float diff = textX + mEllipsisLength + mPaddingRight - width;
            while (mPaint.measureText(text.substring(0, count + 1)) + diff < 0) {
                count++;
            }
            canvas.drawText(text.substring(0, count) + ELLIPSIS, textX, textY - mFontSpacing * mProgress, mPaint);
        }
    }

    public void setText(String text) {
        mIsPage = false;
        mProgress = 0;
        mText = text;
        invalidate();
    }

    public void setTextByPage(String text) {
        mIsPage = true;
        mLastText = mText;
        mText = text;
        startAnimation();
    }

    private void startAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "progress", 0, 1);
        animator.setDuration(500);
        animator.start();
    }

    private void setProgress(float progress) {
        this.mProgress = progress;
        invalidate();
    }

    private float getProgress() {
        return this.mProgress;
    }

}

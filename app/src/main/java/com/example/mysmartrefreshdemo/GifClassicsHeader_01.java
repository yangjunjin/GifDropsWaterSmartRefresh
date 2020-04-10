package com.example.mysmartrefreshdemo;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * @author cenjunlei
 * @date 2018/6/13
 */
public class GifClassicsHeader_01 extends ClassicsHeader {
    private GifImageView gifStrokeImageView;
    private GifImageView gifRotateImageView;
    private GifDrawable mGifStrokeDrawable;

    private boolean mFirstHiddenGif;
    private final static int INTERVAL_TIME = 40;

    private static long lastclicktime = 0;
    private static float startPercent = 0.4f;
    private static final int mGifPopupSpeed = 9;

    public GifClassicsHeader_01(Context context) {
        this(context, null);
    }

    public GifClassicsHeader_01(Context context, AttributeSet attrs) {
        super(context, attrs);
        gifStrokeImageView = new GifImageView(context);
        gifStrokeImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        LayoutParams params = new LayoutParams(SizeUtils.dp2px(100), ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_IN_PARENT);
        gifStrokeImageView.setLayoutParams(params);

        gifRotateImageView = new GifImageView(context);
        gifRotateImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        params = new LayoutParams(SizeUtils.dp2px(100), ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_IN_PARENT);
        gifRotateImageView.setLayoutParams(params);

        params = new LayoutParams(SizeUtils.dp2px(100), ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_IN_PARENT);

        try {
            mGifStrokeDrawable = new GifDrawable(getResources(), R.drawable.loading_gif);
            gifStrokeImageView.setImageDrawable(mGifStrokeDrawable);

            gifRotateImageView.setImageResource(R.drawable.loading3);
            gifRotateImageView.setVisibility(INVISIBLE);

            mGifStrokeDrawable.stop();

            mTitleText.setVisibility(GONE);

            addView(gifRotateImageView);
            addView(gifStrokeImageView);

            reset();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {
        super.onMoving(isDragging, percent, offset, height, maxDragHeight);
        LogUtils.e("GifClassicsHeader中的onMoving:1");
        if (!isDragging) {
            LogUtils.e("GifClassicsHeader中的onMoving:2");
            return;
        }

        if (System.currentTimeMillis() - lastclicktime > INTERVAL_TIME) {
            LogUtils.e("GifClassicsHeader中的onMoving:3");
            lastclicktime = System.currentTimeMillis();
            if (percent < startPercent) {
                LogUtils.e("GifClassicsHeader中的onMoving:4");
                return;
            }

            if (animtorRotation != null && animtorRotation.isRunning()) {
                LogUtils.e("GifClassicsHeader中的onMoving:5");
                return;
            }

            gifStrokeImageView.setVisibility(VISIBLE);

            if (mGifStrokeDrawable != null) {
                LogUtils.e("GifClassicsHeader中的onMoving:6");
                int seek = (int) ((percent - startPercent) * mGifStrokeDrawable.getDuration());

                if (mGifStrokeDrawable.getCurrentPosition() == mGifStrokeDrawable.getDuration()) {
                    LogUtils.e("GifClassicsHeader中的onMoving:7");
                    return;
                }
                LogUtils.e("GifClassicsHeader中的onMoving:8");
                mGifStrokeDrawable.seekTo(seek);
            }
        }
    }


    private void reset() {
        gifRotateImageView.setVisibility(INVISIBLE);
        gifStrokeImageView.setVisibility(INVISIBLE);
    }

    private boolean isFinish;

    @Override
    public int onFinish(@NonNull RefreshLayout layout, boolean success) {
        if (mFirstHiddenGif) {
            LogUtils.e("GifClassicsHeader中的onFinish:1");
            setVisibility(VISIBLE);
            mFirstHiddenGif = false;
        }

        LogUtils.e("GifClassicsHeader中的onFinish:2");
        isFinish = true;

        gifRotateImageView.setVisibility(View.INVISIBLE);
        gifStrokeImageView.setVisibility(INVISIBLE);
        super.onFinish(layout, success);
        return mGifStrokeDrawable.getDuration() / mGifPopupSpeed;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        LogUtils.e("GifClassicsHeader中的onDetachedFromWindow:1");
        mGifStrokeDrawable.stop();
    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
        super.onStartAnimator(refreshLayout, height, maxDragHeight);
        isFinish = false;
        LogUtils.e("GifClassicsHeader中的onStartAnimator:1");
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState,
                               @NonNull RefreshState newState) {
        super.onStateChanged(refreshLayout, oldState, newState);
        switch (newState) {
            case None:
            case PullDownToRefresh:
                if (gifRotateImageView.getVisibility() == VISIBLE) {
                    gifRotateImageView.setVisibility(View.GONE);
                }
                gifStrokeImageView.setVisibility(VISIBLE);
                LogUtils.e("GifClassicsHeader中的onStateChanged:1");
                break;
            case RefreshReleased:
                if (gifStrokeImageView.getVisibility() == VISIBLE) {
                    gifStrokeImageView.setVisibility(GONE);
                }
                LogUtils.e("GifClassicsHeader中的onStateChanged:2");
                gifRotateImageView.setVisibility(View.VISIBLE);
                break;
            case RefreshFinish:
                LogUtils.e("GifClassicsHeader中的onStateChanged:3");
//                gifRotateImageView.setVisibility(View.GONE);
//                gifStrokeImageView.setVisibility(GONE);
                break;
            case ReleaseToTwoLevel:
                LogUtils.e("GifClassicsHeader中的onStateChanged:4");

                break;
            case Loading:
                LogUtils.e("GifClassicsHeader中的onStateChanged:5");
                break;
        }
    }

    public void setFirstHiddenGif(boolean mFirstHiddenGif) {
        setVisibility(INVISIBLE);
        this.mFirstHiddenGif = mFirstHiddenGif;
    }

    /**
     * 增加旋转动画
     */
    ObjectAnimator animtorRotation;

    @SuppressLint("WrongConstant")
    public void startRotation() {
        if (animtorRotation == null) {
            //设置旋转的样式
            animtorRotation = ObjectAnimator.ofFloat(gifRotateImageView, "rotation", 0f, 360f);
            //旋转不停顿
            animtorRotation.setInterpolator(new LinearInterpolator());
            //设置动画重复次数
            animtorRotation.setRepeatCount(ValueAnimator.INFINITE);
            animtorRotation.setRepeatMode(ValueAnimator.INFINITE);
            //旋转时长
            animtorRotation.setDuration(1000);
        }

        if (!animtorRotation.isRunning()) {
            gifStrokeImageView.setVisibility(INVISIBLE);
            gifRotateImageView.setVisibility(VISIBLE);
            //开始旋转
            animtorRotation.start();
        }
    }

    /**
     * 停止旋转
     */
    public void stopRotation() {
        if (animtorRotation != null) {
            animtorRotation.end();
        }
        isFinish = false;
    }

}

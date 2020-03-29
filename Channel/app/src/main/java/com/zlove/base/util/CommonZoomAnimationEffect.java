package com.zlove.base.util;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

import java.lang.ref.WeakReference;



public class CommonZoomAnimationEffect {
    
    private float from;
    private float to;
    private WeakReference<ImageView> praiseViewReference;
    private CommonZoomAnimListener listener;
    
    public CommonZoomAnimationEffect(float from, float to, ImageView effectView) {
        super();
        this.from = from;
        this.to = to;
        this.praiseViewReference = new WeakReference<>(effectView);
        this.listener = new CommonZoomAnimListener(effectView, to, from);
    }
    
    public void startAnim(){
        ImageView likeImageView = praiseViewReference.get();
        if (likeImageView != null) {
            rotateAnimRun(likeImageView, from, to, listener);
        }
    }
    
    @SuppressLint("NewApi")
    private void rotateAnimRun(final View view, float from, float to, AnimatorListener animatorListener) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "like_effect", from, to).setDuration(300);
        anim.start();
        anim.addUpdateListener(new AnimatorUpdateListener() {

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @SuppressLint("NewApi")
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (Build.VERSION.SDK_INT >= 11) {
                    float cVal = (Float) animation.getAnimatedValue();
                    view.setAlpha(cVal);
                    view.setScaleX(cVal);
                    view.setScaleY(cVal);
                }
            }
        });
        if (animatorListener != null) {
            anim.addListener(animatorListener);
        }
    }
    
    @SuppressLint("NewApi") 
    class CommonZoomAnimListener implements AnimatorListener {
        
        private WeakReference<ImageView> viewRef;
        private float from;
        private float to;

        public CommonZoomAnimListener(ImageView view, float from, float to) {
            this.viewRef = new WeakReference<ImageView>(view);
            this.from = from;
            this.to = to;
        }

        @Override
        public void onAnimationCancel(Animator animation) {
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            ImageView view = this.viewRef.get();
            if (view != null) {
                rotateAnimRun(view, from, to, null);
            }
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
        }

        @Override
        public void onAnimationStart(Animator animation) {
        }
        
    }
    
}

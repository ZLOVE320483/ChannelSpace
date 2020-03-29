package com.zlove.base.widget.pulllistview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zlove.channelsaleman.R;


public class CircleRefreshingHeader extends LinearLayout implements PullListHeader {

    private final int ROTATE_ANIM_DURATION = 180;

    private ViewGroup mContainer;
    private View mContentView;
    private CircleProgress mCircleProgressbar;
    private ImageView mArrowImageView;
    private TextView mRefreshTextIndicator;

    private State mState = State.STATE_NORMAL;
    private int mContentViewHeight;

    private Animation mRotateUpAnim;
    private Animation mRotateDownAnim;
    private Animation mCirclingAnim;

    public CircleRefreshingHeader(Context context) {
        super(context);
        initView(context);
    }

    public CircleRefreshingHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    @SuppressLint("InflateParams") 
    private void initView(Context context) {
        mContainer = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.common_view_circle_refreshing_header, null);
        mContentView = mContainer.findViewById(R.id.content);
        mCircleProgressbar = (CircleProgress) mContainer.findViewById(R.id.circle_pregress_bar);
        mArrowImageView = (ImageView) mContainer.findViewById(R.id.arrow);
        mRefreshTextIndicator = (TextView) mContainer.findViewById(R.id.indivating_text);

        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
        addView(mContainer, lp);
        setGravity(Gravity.BOTTOM);

        mRotateUpAnim = new RotateAnimation(0.0f, -180.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateUpAnim.setFillAfter(true);
        mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateDownAnim.setFillAfter(true);

        mCirclingAnim = AnimationUtils.loadAnimation(context, R.anim.refreshing_progress_bar_rotate);
        LinearInterpolator lin = new LinearInterpolator();
        mCirclingAnim.setInterpolator(lin);

        // init header height
        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                mContentViewHeight = mContentView.getHeight();
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    @Override
    public void setVisiableHeight(int height) {
        if (height < 0)
            height = 0;
        LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
        lp.height = height;
        mContainer.setLayoutParams(lp);
    }

    @Override
    public int getVisiableHeight() {
        return mContainer.getHeight();
    }

    @Override
    public void onStateChange(State state) {
        if (state == mState)
            return;

        if (state == State.STATE_REFRESH_ING) { // 显示进度
            mArrowImageView.clearAnimation();
            mArrowImageView.setVisibility(View.INVISIBLE);
        } else { // 显示箭头图片
            mArrowImageView.setVisibility(View.VISIBLE);
        }

        switch (state) {
            case STATE_NORMAL:
                if (mState == State.STATE_READY_TO_REFRESH) {
                    mArrowImageView.startAnimation(mRotateDownAnim);
                }
                if (mState == State.STATE_REFRESH_ING) {
                    mArrowImageView.clearAnimation();
                }
                mRefreshTextIndicator.setText("下拉刷新");
                break;
            case STATE_READY_TO_REFRESH:
                if (mState != State.STATE_READY_TO_REFRESH) {
                    mArrowImageView.clearAnimation();
                    mArrowImageView.startAnimation(mRotateUpAnim);
                    mRefreshTextIndicator.setText("松开刷新");
                }
                break;
            case STATE_REFRESH_ING:
                mCircleProgressbar.startAnimation(mCirclingAnim);
                mRefreshTextIndicator.setText("正在刷新");
                break;
            case STATE_REFRESH_COMPLETE:
                mCircleProgressbar.clearAnimation();
                break;
            default:
                break;
        }

        mState = state;
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public int getRefreshHeight() {
        return mContentViewHeight;
    }

    @Override
    public void enableRefresh(boolean enable) {
        if (enable) {
            mContainer.setVisibility(View.VISIBLE);
        } else {
            mContainer.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onHeaderHeightChange(int height) {
        int progress = 0;
        if (getRefreshHeight() > 0) {
            progress = height * 100 / getRefreshHeight();
        }
        if (progress > 94) {
            progress = 94;
        }
        mCircleProgressbar.setMainProgress(progress);
    }

}

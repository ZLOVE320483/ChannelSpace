package com.zlove.base.widget.pulllistview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Scroller;
import android.widget.TextView;

import com.zlove.channelsaleman.R;


public class PullListView extends ListView implements OnScrollListener {

    private final static int SCROLLBACK_HEADER = 0;
    private final static int SCROLLBACK_FOOTER = 1;
    private final static int SCROLL_DURATION = 400; // scroll back duration
    private final static int PULL_LOAD_MORE_DELTA = 50; // when pull up >= 50px at bottom, trigger load more.
    private final static float OFFSET_RADIO = 1.8f; // support iOS like pull feature.

    private float mLastY = -1; // save event y
    private Scroller mScroller; // used for scroll back
    private OnScrollListener mScrollListener; // user's scroll listener
    private PullableViewListener mListViewListener; // invoker to refresh and load
    private PullListHeader mHeaderView;
    private PullListViewFooter mFooterView;
    private boolean mEnablePullRefresh = true;
    private boolean mEnablePullLoad;
    public boolean mIsRefreshing = false;
    private boolean mIsLoading;
    private boolean mIsFooterAdded = false;
    private int mTotalItemCount; // total list items, used to detect is at the bottom of listview.
    private int mScrollBack; // for mScroller, scroll back from header or footer.
    private View noDataIndicatorHeaderView;
    private TextView noDataIndicatorTextView;

    /**
     * @param context
     */
    public PullListView(Context context) {

        super(context);
        initWithContext(context);
    }

    public PullListView(Context context, AttributeSet attrs) {

        super(context, attrs);
        initWithContext(context);
    }

    public PullListView(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);
        initWithContext(context);
    }

    @SuppressLint("InflateParams") 
    private void initWithContext(Context context) {

        mScroller = new Scroller(context, new DecelerateInterpolator());
        // XListView need the scroll event, and it will dispatch the event to
        // user's listener (as a proxy).
        super.setOnScrollListener(this);
        noDataIndicatorHeaderView = LayoutInflater.from(context).inflate(R.layout.common_view_no_data_indicator, null);
        noDataIndicatorTextView = (TextView) noDataIndicatorHeaderView.findViewById(R.id.no_data_indicator);
        
        mHeaderView = new CircleRefreshingHeader(context);
        addHeaderView(mHeaderView.getView());

        // init footer view
        mFooterView = new PullListViewFooter(context);
    }

    public void removeHeadView() {
        removeHeaderView(mHeaderView.getView());
    }

    @Override
    public void setAdapter(ListAdapter adapter) {

        // make sure XListViewFooter is the last footer view, and only add once.
        if (mIsFooterAdded == false) {
            mIsFooterAdded = true;
            addFooterView(mFooterView);
        }
        super.setAdapter(adapter);
    }

    public void setPullRefreshEnable(boolean enable) {

        mEnablePullRefresh = enable;
        if (mHeaderView != null) {
            mHeaderView.enableRefresh(mEnablePullRefresh);
        }
    }

    public void setPullLoadEnable(boolean enable) {

        mEnablePullLoad = enable;
        if (!mEnablePullLoad) {
            mFooterView.hide();
            mFooterView.setOnClickListener(null);
        } else {
            mIsLoading = false;
            mFooterView.show();
            mFooterView.setState(PullListViewFooter.STATE_NORMAL);
            // both "pull up" and "click" will invoke load more.
            mFooterView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    startLoadMore();
                }
            });
        }
    }

    /**
     * stop refresh, reset header view.
     */
    public void stopRefresh() {
        if (mIsRefreshing == true) {
            mIsRefreshing = false;
            mEnablePullRefresh = true;
            if (mHeaderView != null) {
                mHeaderView.onStateChange(PullListHeader.State.STATE_REFRESH_COMPLETE);
                resetHeaderHeight();
            }
        }
    }

    /**
     * stop load more, reset footer view.
     */
    public void stopLoadMore() {

        if (mIsLoading == true) {
            mIsLoading = false;
            mFooterView.setState(PullListViewFooter.STATE_NORMAL);
        }
    }

    /**
     * set last refresh time
     * 
     * @param time
     */
    @Deprecated
    public void setRefreshTime(String time) {

    }

    private void invokeOnScrolling() {

        if (mScrollListener instanceof PullableViewScrollListener) {
            PullableViewScrollListener l = (PullableViewScrollListener) mScrollListener;
            l.onPullableViewScroll(this);
        }
    }

    private void updateHeaderHeight(float delta) {
        if (mHeaderView != null) {
            mHeaderView.setVisiableHeight((int) delta + mHeaderView.getVisiableHeight());
            if (mEnablePullRefresh && !mIsRefreshing) {
                if (mHeaderView.getVisiableHeight() > mHeaderView.getRefreshHeight()) {
                    mHeaderView.onStateChange(PullListHeader.State.STATE_READY_TO_REFRESH);
                } else {
                    mHeaderView.onStateChange(PullListHeader.State.STATE_NORMAL);
                }
            }
        }
        setSelection(0); // scroll to top each time
    }

    /**
     * reset header view's height.
     */
    private void resetHeaderHeight() {
        if (mHeaderView == null)
            return;
        int height = mHeaderView.getVisiableHeight();
        if (height == 0) // not visible.
            return;
        // refreshing and header isn't shown fully. do nothing.
        if (mIsRefreshing && height <= mHeaderView.getRefreshHeight()) {
            return;
        }
        int finalHeight = 0; // default: scroll back to dismiss header.
        // is refreshing, just scroll back to show all the header.
        if (mIsRefreshing && height > mHeaderView.getRefreshHeight()) {
            finalHeight = mHeaderView.getRefreshHeight();
        }
        mScrollBack = SCROLLBACK_HEADER;
        mScroller.startScroll(0, height, 0, finalHeight - height, SCROLL_DURATION);
        // trigger computeScroll
        invalidate();
    }

    private void updateFooterHeight(float delta) {

        int height = mFooterView.getBottomMargin() + (int) delta;
        if (mEnablePullLoad && !mIsLoading) {
            if (height > PULL_LOAD_MORE_DELTA) { // height enough to invoke load
                                                 // more.
                mFooterView.setState(PullListViewFooter.STATE_READY);
            } else {
                mFooterView.setState(PullListViewFooter.STATE_NORMAL);
            }
        }
        mFooterView.setBottomMargin(height);
    }

    private void resetFooterHeight() {

        int bottomMargin = mFooterView.getBottomMargin();
        if (bottomMargin > 0) {
            mScrollBack = SCROLLBACK_FOOTER;
            mScroller.startScroll(0, bottomMargin, 0, -bottomMargin, SCROLL_DURATION);
            invalidate();
        }
    }

    private void startLoadMore() {

        mIsLoading = true;
        mFooterView.setState(PullListViewFooter.STATE_LOADING);
        if (mListViewListener != null) {
            mListViewListener.onLoadMore();
        }
    }

    boolean is_touching = false;

    @SuppressLint("ClickableViewAccessibility") 
    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (mLastY == -1) {
            mLastY = ev.getRawY();
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaY = ev.getRawY() - mLastY;
                mLastY = ev.getRawY();
                if (getFirstVisiblePosition() == 0 && mHeaderView != null && (mHeaderView.getVisiableHeight() > 0 || deltaY > 0)) {
                    // the first item is showing, header has shown or pull down.
                    updateHeaderHeight(deltaY / OFFSET_RADIO);
                    mHeaderView.onHeaderHeightChange(mHeaderView.getVisiableHeight());
                    invokeOnScrolling();
                } else if (mEnablePullLoad && getLastVisiblePosition() == mTotalItemCount - 1 && (mFooterView.getBottomMargin() > 0 || deltaY < 0)) {
                    // last item, already pulled up or want to pull up.
                    updateFooterHeight(-deltaY / OFFSET_RADIO);
                }
                break;
            default:
                mLastY = -1; // reset
                if (getFirstVisiblePosition() == 0) {
                    // invoke refresh
                    if (mEnablePullRefresh && mHeaderView != null && mHeaderView.getVisiableHeight() > mHeaderView.getRefreshHeight()) {
                        mIsRefreshing = true;
                        mEnablePullRefresh = false;
                        mHeaderView.onStateChange(PullListHeader.State.STATE_REFRESH_ING);
                        if (mListViewListener != null) {
                            dismissNoDataIndicator();
                            mListViewListener.onRefresh();
                        }
                    }
                    resetHeaderHeight();
                }
                if (getLastVisiblePosition() == mTotalItemCount - 1) {
                    // invoke load more.
                    if (mEnablePullLoad && mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA && !mIsLoading) {
                        startLoadMore();
                    }
                    resetFooterHeight();
                }
                is_touching = false;
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void computeScroll() {

        if (mScroller.computeScrollOffset()) {
            if (mScrollBack == SCROLLBACK_HEADER) {
                if (mHeaderView != null) {
                    mHeaderView.setVisiableHeight(mScroller.getCurrY());
                }
            } else {
                mFooterView.setBottomMargin(mScroller.getCurrY());
            }
            postInvalidate();
            invokeOnScrolling();
        }
        super.computeScroll();
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        mScrollListener = l;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        if (mScrollListener != null) {
            mScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        // send to user's listener
        mTotalItemCount = totalItemCount;
        if (mScrollListener != null) {
            mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    public void setPullableViewListener(PullableViewListener l) {

        mListViewListener = l;
    }

    public void setHeaderView(PullListHeader mHeaderView) {
        removeHeaderView(this.mHeaderView.getView());
        this.mHeaderView = mHeaderView;
        addHeaderView(mHeaderView.getView());
    }

    public void showNoDataIndicator(String notify) {
        removeHeaderView(noDataIndicatorHeaderView);
        if (!TextUtils.isEmpty(notify)) {
            noDataIndicatorTextView.setText(notify);
        } else {
            noDataIndicatorTextView.setText("");
        }
        addHeaderView(noDataIndicatorHeaderView);
        
    }
    
    public void dismissNoDataIndicator() {
        removeHeaderView(noDataIndicatorHeaderView);
    }

}

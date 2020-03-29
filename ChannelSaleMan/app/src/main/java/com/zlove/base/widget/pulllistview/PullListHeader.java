package com.zlove.base.widget.pulllistview;

import android.view.View;


public interface PullListHeader {

    public enum State {
        STATE_NORMAL, STATE_READY_TO_REFRESH, STATE_REFRESH_ING, STATE_REFRESH_COMPLETE
    }
    
    public void setVisiableHeight(int height);

    public int getVisiableHeight();

    public void onStateChange(State state);
    
    public void onHeaderHeightChange(int progress);
    
    public View getView();
    
    /**
     * @return the critical height, beyond which, the listview will turn to refresh
     */
    public int getRefreshHeight();
    
    public void enableRefresh(boolean enable);
}

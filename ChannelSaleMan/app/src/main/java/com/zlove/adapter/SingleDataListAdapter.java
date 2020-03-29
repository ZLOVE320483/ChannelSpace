package com.zlove.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.zlove.base.util.ListUtils;

public abstract class SingleDataListAdapter<T> extends BaseAdapter {
    
    protected List<T> mData;
    protected Context mContext;
    
    public SingleDataListAdapter(List<T> data, Context context) {
        mData = data;
        mContext = context;
    }

    @Override
    public int getCount() {
        if (ListUtils.isEmpty(mData)) {
            return 0;
        }
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        if (ListUtils.isEmpty(mData)) {
            return null;
        }
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
}

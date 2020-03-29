package com.zlove.frag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.zlove.adapter.CustomerChannelAdapter;
import com.zlove.base.BaseFragment;
import com.zlove.channelsaleman.R;
import com.zlove.constant.IntentKey;


public class CustomerChannelFragment extends BaseFragment implements OnItemClickListener {

    private ListView listView;
    private CustomerChannelAdapter adapter;
    private List<String> channels = new ArrayList<String>();
    
    @Override
    protected int getInflateLayout() {
        return R.layout.common_frag_listview_with_top;
    }

    @Override
    protected void setUpView(View view) {
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("选择认知渠道");
        listView = (ListView) view.findViewById(R.id.id_listview);
        listView.setOnItemClickListener(this);
        
        String[] array = getResources().getStringArray(R.array.customer_channel);
        Collections.addAll(channels, array);
        
        adapter = new CustomerChannelAdapter(channels, getActivity());
        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        String fromWay = channels.get(arg2);
        if (TextUtils.isEmpty(fromWay)) {
			return;
		}
        Intent intent = new Intent();
        intent.putExtra(IntentKey.INTENT_KEY_SELECT_CHANNEL, fromWay);
        finishActivity(intent);
    }

}

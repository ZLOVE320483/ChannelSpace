package com.zlove.frag;

import android.view.View;
import android.widget.TextView;

import com.zlove.base.BaseFragment;
import com.zlove.channelsaleman.R;


public class UserProtocolFragment extends BaseFragment {

    private TextView tvUserProtocol;
    
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_user_protocol;
    }

    @Override
    protected void setUpView(View view) {
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("用户协议");
        tvUserProtocol = (TextView) view.findViewById(R.id.id_user_protocol);
        tvUserProtocol.setText("用户协议");
    }

}

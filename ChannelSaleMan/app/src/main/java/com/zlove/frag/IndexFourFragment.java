package com.zlove.frag;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.zlove.act.ActLogin;
import com.zlove.base.BaseFragment;
import com.zlove.channelsaleman.R;
import com.zlove.config.ChannelCookie;


public class IndexFourFragment extends BaseFragment implements OnClickListener {

    private TextView jumpView;
    
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_index_4;
    }

    @Override
    protected void setUpView(View view) {
        jumpView = (TextView) view.findViewById(R.id.jump);
        jumpView.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
        if (v == jumpView) {
            ChannelCookie.getInstance().saveFirstStart(false);
            Intent intent = new Intent(getActivity(), ActLogin.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            getActivity().finish();
        }
    }

}

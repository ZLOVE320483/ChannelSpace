package com.zlove.frag;

import android.view.View;
import android.widget.TextView;

import com.zlove.base.BaseFragment;
import com.zlove.channelsaleman.R;


public class AboutFragment extends BaseFragment {

    @Override
    protected int getInflateLayout() {
        return R.layout.frag_app_about;
    }

    @Override
    protected void setUpView(View view) {
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("关于我们");
    }

}

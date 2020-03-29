package com.zlove.frag.independent;

import android.view.View;
import android.widget.TextView;

import com.zlove.base.BaseFragment;
import com.zlove.channel.R;


public class IndependentCooperateRuleFragment extends BaseFragment {

    @Override
    protected int getInflateLayout() {
        return R.layout.frag_cooperate_rule;
    }

    @Override
    protected void setUpView(View view) {
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("合作规则");
    
    }

}

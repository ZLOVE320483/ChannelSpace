package com.zlove.frag.independent;

import android.view.View;
import android.widget.TextView;

import com.zlove.base.BaseFragment;
import com.zlove.channel.R;


public class IndependentProjectDynamicDetailFragment extends BaseFragment {

    
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_independent_project_dynamic_detail;
    }

    @Override
    protected void setUpView(View view) {
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("动态详情");
    }
    

}

package com.zlove.frag.independent;

import android.view.View;
import android.widget.TextView;

import com.zlove.base.BaseFragment;
import com.zlove.channel.R;


public class IndependentCommissionFragment extends BaseFragment {

    private TextView tvNotSettleCommission = null;
    private TextView tvTotalCommission = null;
    
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_independent_commission;
    }

    @Override
    protected void setUpView(View view) {
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("我的佣金");
        tvNotSettleCommission = (TextView) view.findViewById(R.id.tv_not_settle_commission);
        tvTotalCommission = (TextView) view.findViewById(R.id.tv_total_commission);
        
        tvNotSettleCommission.setText("3886");
        tvTotalCommission.setText("860");
    }

}

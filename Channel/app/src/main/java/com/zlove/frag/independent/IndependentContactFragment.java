package com.zlove.frag.independent;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;

import com.zlove.base.BaseFragment;
import com.zlove.channel.R;


public class IndependentContactFragment extends BaseFragment implements OnClickListener {
    
    private RadioButton rbCallLog;
    private RadioButton rbContact;
    
    private IndependentCallLogFragment callLogFragment = null;
    private IndependentContactSubFragment contactFragment = null;
    
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_contact_main;
    }

    @Override
    protected void setUpView(View view) {
        setBackButton(view.findViewById(R.id.id_back));
        
        rbCallLog = (RadioButton) view.findViewById(R.id.rb_call_log);
        rbContact = (RadioButton) view.findViewById(R.id.rb_contact);
        
        rbCallLog.setChecked(true);
        
        rbCallLog.setOnClickListener(this);
        rbContact.setOnClickListener(this);
        
        showFragment(0);
    }
    
    @Override
    public void onClick(View v) {
        if (v == rbCallLog) {
            showFragment(0);
        } else if (v == rbContact) {
            showFragment(1);
        }
    }
    
    private void showFragment(int pos) {
        if (pos == 0) {
            rbCallLog.setChecked(true);
            rbContact.setChecked(false);
            if (callLogFragment == null) {
                callLogFragment = new IndependentCallLogFragment();
            }
            getChildFragmentManager().beginTransaction().replace(R.id.contact_frag_container, callLogFragment).commitAllowingStateLoss();
        } else if (pos == 1) {
            rbCallLog.setChecked(false);
            rbContact.setChecked(true);
            if (contactFragment == null) {
                contactFragment = new IndependentContactSubFragment();
            }
            getChildFragmentManager().beginTransaction().replace(R.id.contact_frag_container, contactFragment).commitAllowingStateLoss();
        }
    }

}

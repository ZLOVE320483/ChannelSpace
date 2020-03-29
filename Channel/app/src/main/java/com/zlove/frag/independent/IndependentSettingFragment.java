package com.zlove.frag.independent;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.zlove.act.independent.ActIndependentPwdChange;
import com.zlove.act.independent.ActIndependentUserAccountChange;
import com.zlove.act.independent.ActUserProtocol;
import com.zlove.base.BaseFragment;
import com.zlove.channel.R;


public class IndependentSettingFragment extends BaseFragment implements OnClickListener {
    
    private View modifyPwdView = null;
    private View modifyAccountView = null;
    private View aboutView = null;
    private View checkVersionView = null;
    private View userProtocolView = null;
    private Button btnExit = null;
    
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_setting;
    }

    @Override
    protected void setUpView(View view) {
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("设置");
        modifyPwdView = view.findViewById(R.id.id_modify_pwd);
        modifyAccountView = view.findViewById(R.id.id_modify_account);
        aboutView = view.findViewById(R.id.id_about);
        checkVersionView = view.findViewById(R.id.id_check_app_version);
        userProtocolView = view.findViewById(R.id.id_user_protocol);
        btnExit = (Button) view.findViewById(R.id.id_exit);
        
        modifyPwdView.setOnClickListener(this);
        modifyAccountView.setOnClickListener(this);
        aboutView.setOnClickListener(this);
        checkVersionView.setOnClickListener(this);
        userProtocolView.setOnClickListener(this);
        btnExit.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View view) {
        if (view == modifyPwdView) {
            Intent intent = new Intent(getActivity(), ActIndependentPwdChange.class);
            startActivity(intent);
        } else if (view == modifyAccountView) {
            Intent intent = new Intent(getActivity(), ActIndependentUserAccountChange.class);
            startActivity(intent);
        } else if (view == aboutView) {
            
        } else if (view == checkVersionView) {
            
        } else if (view == userProtocolView) {
            Intent intent = new Intent(getActivity(), ActUserProtocol.class);
            startActivity(intent);
        } else if (view == btnExit) {
            
        }
    }

}

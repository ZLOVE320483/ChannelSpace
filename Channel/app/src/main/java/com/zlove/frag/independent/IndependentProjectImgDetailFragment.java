package com.zlove.frag.independent;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;

import com.zlove.base.BaseFragment;
import com.zlove.channel.R;


public class IndependentProjectImgDetailFragment extends BaseFragment implements OnClickListener {

    private static final int TAB_EFFECT = 1;
    private static final int TAB_MODEL = 2;
    private static final int TAB_LAYOUT = 3;
    
    private RadioButton rbEffect;
    private RadioButton rbModel;
    private RadioButton rbLayout;
    
    private ProjectImgDetailEffectFragment effectFragment;
    private ProjectImgDetailModelFragment modelFragment;
    private ProjectImgDetailLayoutFragment layoutFragment;
    
    private int currentTab = 0;
    
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_project_img_detail;
    }

    @Override
    protected void setUpView(View view) {
        rbEffect = (RadioButton) view.findViewById(R.id.id_effect);
        rbModel = (RadioButton) view.findViewById(R.id.id_model);
        rbLayout = (RadioButton) view.findViewById(R.id.id_layout);
        
        rbEffect.setOnClickListener(this);
        rbModel.setOnClickListener(this);
        rbLayout.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
        if (v == rbEffect) {
            currentTab = 1;
            showEffectFragment();
        } else if (v == rbModel) {
            currentTab = 2;
            showModelFragment();
        } else if (v == rbLayout) {
            currentTab = 3;
            showLayoutFragment();
        }
        setTab();
    }
    
    private void setTab() {
        rbEffect.setChecked(currentTab == TAB_EFFECT);
        rbModel.setChecked(currentTab == TAB_MODEL);
        rbLayout.setChecked(currentTab == TAB_LAYOUT);
    }
    
    private void showEffectFragment() {
		if (effectFragment == null) {
			effectFragment = new ProjectImgDetailEffectFragment();
		}
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.id_framework, effectFragment).commitAllowingStateLoss();
	}
    
    private void showModelFragment() {
		if (modelFragment == null) {
			modelFragment = new ProjectImgDetailModelFragment();
		}
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.id_framework, modelFragment).commitAllowingStateLoss();
	}
    
    private void showLayoutFragment() {
		if (layoutFragment == null) {
			layoutFragment = new ProjectImgDetailLayoutFragment();
		}
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.id_framework, layoutFragment).commitAllowingStateLoss();
	}

}

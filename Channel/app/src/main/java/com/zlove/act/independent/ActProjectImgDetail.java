package com.zlove.act.independent;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.zlove.base.ActChannelBase;
import com.zlove.channel.R;
import com.zlove.constant.IntentKey;
import com.zlove.frag.independent.ProjectImgDetailEffectFragment;
import com.zlove.frag.independent.ProjectImgDetailLayoutFragment;
import com.zlove.frag.independent.ProjectImgDetailModelFragment;


public class ActProjectImgDetail extends ActChannelBase implements OnClickListener {
    
    private static final int TAB_EFFECT = 1;
    private static final int TAB_MODEL = 2;
    private static final int TAB_LAYOUT = 3;
    
    private RadioButton rbEffect;
    private RadioButton rbModel;
    private RadioButton rbLayout;
    
    private ImageView ivBack;
    
    private ProjectImgDetailEffectFragment effectFragment;
    private ProjectImgDetailModelFragment modelFragment;
    private ProjectImgDetailLayoutFragment layoutFragment;
    
    private int currentTab = 0;
    
    private ArrayList<String> effectUrls;
    private ArrayList<String> modeltUrls;
    private ArrayList<String> layoutUrls;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_project_img_detail);
        
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(IntentKey.INTENT_KEY_EFFECT_URL_LIST)) {
                effectUrls = intent.getStringArrayListExtra(IntentKey.INTENT_KEY_EFFECT_URL_LIST);
            }
            if (intent.hasExtra(IntentKey.INTENT_KEY_MODEL_URL_LIST)) {
                modeltUrls = intent.getStringArrayListExtra(IntentKey.INTENT_KEY_MODEL_URL_LIST);
            }
            if (intent.hasExtra(IntentKey.INTENT_KEY_LAYOUT_URL_LIST)) {
                layoutUrls = intent.getStringArrayListExtra(IntentKey.INTENT_KEY_LAYOUT_URL_LIST);
            }
        }
        
        ivBack = (ImageView) findViewById(R.id.id_back);
        
        rbEffect = (RadioButton) findViewById(R.id.id_effect);
        rbModel = (RadioButton) findViewById(R.id.id_model);
        rbLayout = (RadioButton) findViewById(R.id.id_layout);
        
        rbEffect.setOnClickListener(this);
        rbModel.setOnClickListener(this);
        rbLayout.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        
        selectTab(TAB_EFFECT);
    }
    
    @Override
    public void onClick(View v) {
        int changeIndex = 0;
        if (v == rbEffect) {
            changeIndex = TAB_EFFECT;
        } else if (v == rbModel) {
            changeIndex = TAB_MODEL;
        } else if (v == rbLayout) {
            changeIndex = TAB_LAYOUT;
        } else if (v == ivBack) {
			this.finish();
		}
        selectTab(changeIndex);
    }
    
    private void selectTab(int tab) {
        if (tab == currentTab) {
            return;
        }
        currentTab = tab;
        switch (tab) {
            case TAB_EFFECT: {
                showEffectFragment();
                break;
            }
            case TAB_MODEL: {
                showModelFragment();
                break;
            }
            case TAB_LAYOUT: {
                showLayoutFragment();
                break;
            }
            default:
                break;
        }
        onTabChange();
    }
    
    private void onTabChange() {
        rbEffect.setChecked(currentTab == TAB_EFFECT);
        rbModel.setChecked(currentTab == TAB_MODEL);
        rbLayout.setChecked(currentTab == TAB_LAYOUT);
    }
    
    private void showEffectFragment() {
        if (effectFragment == null) {
            effectFragment = new ProjectImgDetailEffectFragment();
            effectFragment.setImageUrls(effectUrls);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.id_framework, effectFragment).commitAllowingStateLoss();
    }
    
    private void showModelFragment() {
        if (modelFragment == null) {
            modelFragment = new ProjectImgDetailModelFragment();
            modelFragment.setImageUrls(modeltUrls);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.id_framework, modelFragment).commitAllowingStateLoss();
    }
    
    private void showLayoutFragment() {
        if (layoutFragment == null) {
            layoutFragment = new ProjectImgDetailLayoutFragment();
            layoutFragment.setImageUrls(layoutUrls);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.id_framework, layoutFragment).commitAllowingStateLoss();
    }

}

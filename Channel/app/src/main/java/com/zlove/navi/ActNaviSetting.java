package com.zlove.navi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.amap.api.navi.AMapNavi;
import com.zlove.channel.R;
import com.zlove.constant.IntentKey;


public class ActNaviSetting extends BaseActivity implements OnClickListener {
    
    // 导航模式    驾车  OR 步行
    private static final int NAVI_MODE_DRIVE = 0;
    private static final int NAVI_MODE_WALK = 1;
    private int naviMode = NAVI_MODE_DRIVE; // 默认驾车
    
    private int naviStrategy = AMapNavi.DrivingDefault;
    
    private View backView = null;
    private TextView tvTitle = null;
    private Button btnConfirm = null;
    
    private View naviModeDriveContainer = null;
    private RadioButton rbNaviModeDrive = null;
    private View naviModeWalkContainer = null;
    private RadioButton rbNaviModeWalk = null;
    
    private View naviStrategySpeedContainer = null;
    private RadioButton rbNaviStrategySpeed = null;
    private View naviStrategyCostContainer = null;
    private RadioButton rbNaviStrategyCost = null;
    private View naviStrategyDistanceContainer = null;
    private RadioButton rbNaviStrategyDistance = null;
    private View naviStrategyNoHighWayContainer = null;
    private RadioButton rbNaviStrategyNoHighWay = null;
    private View naviStrategyTimeNoJamContainer = null;
    private RadioButton rbNaviStrategyTimeNoJam = null;
    private View naviStrategyCostNoJamContainer = null;
    private RadioButton rbNaviStrategyCostNoJam = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_navi_setting);
        initView();
    }
    
    private void initView() {
        backView = findViewById(R.id.id_back);
        tvTitle = (TextView) findViewById(R.id.id_title);
        btnConfirm = (Button) findViewById(R.id.id_confirm);
        
        naviModeDriveContainer = findViewById(R.id.navi_mode_drive_container);
        rbNaviModeDrive = (RadioButton) findViewById(R.id.rb_navi_mode_drive);
        naviModeWalkContainer = findViewById(R.id.navi_mode_walk_container);
        rbNaviModeWalk = (RadioButton) findViewById(R.id.rb_navi_mode_walk);
        
        naviStrategySpeedContainer = findViewById(R.id.navi_strategy_speed_container);
        rbNaviStrategySpeed = (RadioButton) findViewById(R.id.rb_navi_strategy_speed);
        naviStrategyCostContainer = findViewById(R.id.navi_strategy_cost_container);
        rbNaviStrategyCost = (RadioButton) findViewById(R.id.rb_navi_strategy_cost);
        naviStrategyDistanceContainer = findViewById(R.id.navi_strategy_distance_container);
        rbNaviStrategyDistance = (RadioButton) findViewById(R.id.rb_navi_strategy_distance);
        naviStrategyNoHighWayContainer = findViewById(R.id.navi_strategy_nohighway_container);
        rbNaviStrategyNoHighWay = (RadioButton) findViewById(R.id.rb_navi_strategy_nohighway);
        naviStrategyTimeNoJamContainer = findViewById(R.id.navi_strategy_timenojam_container);
        rbNaviStrategyTimeNoJam = (RadioButton) findViewById(R.id.rb_navi_strategy_timenojam);
        naviStrategyCostNoJamContainer = findViewById(R.id.navi_strategy_costnojam_container);
        rbNaviStrategyCostNoJam = (RadioButton) findViewById(R.id.rb_navi_strategy_costnojam);
        tvTitle.setText("导航设置");
        
        initRadioButton();
        
        rbNaviModeDrive.setClickable(false);
        rbNaviModeWalk.setClickable(false);
        rbNaviStrategyCost.setClickable(false);
        rbNaviStrategyCostNoJam.setClickable(false);
        rbNaviStrategyDistance.setClickable(false);
        rbNaviStrategyNoHighWay.setClickable(false);
        rbNaviStrategySpeed.setClickable(false);
        rbNaviStrategyTimeNoJam.setClickable(false);
        
        backView.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        naviModeDriveContainer.setOnClickListener(this);
        naviModeWalkContainer.setOnClickListener(this);
        naviStrategySpeedContainer.setOnClickListener(this);
        naviStrategyCostContainer.setOnClickListener(this);
        naviStrategyDistanceContainer.setOnClickListener(this);
        naviStrategyNoHighWayContainer.setOnClickListener(this);
        naviStrategyTimeNoJamContainer.setOnClickListener(this);
        naviStrategyCostNoJamContainer.setOnClickListener(this);
    }
    
    private void initRadioButton() {
        if (getIntent() == null) {
            rbNaviModeDrive.setChecked(true);
            rbNaviStrategySpeed.setChecked(true);
        } else {
            naviMode = getIntent().getIntExtra(IntentKey.INTENT_KEY_NAVI_MODE, NAVI_MODE_DRIVE);
            naviStrategy = getIntent().getIntExtra(IntentKey.INTENT_KEY_NAVI_STRATEGY, AMapNavi.DrivingDefault);
            if (naviMode == NAVI_MODE_DRIVE) {
                rbNaviModeDrive.setChecked(true);
                rbNaviModeWalk.setChecked(false);
            } else if (naviMode == NAVI_MODE_WALK) {
                rbNaviModeDrive.setChecked(false);
                rbNaviModeWalk.setChecked(true);
            }
            if (naviStrategy == AMapNavi.DrivingDefault) {
                rbNaviStrategySpeed.setChecked(true);
                rbNaviStrategyCost.setChecked(false);
                rbNaviStrategyDistance.setChecked(false);
                rbNaviStrategyNoHighWay.setChecked(false);
                rbNaviStrategyTimeNoJam.setChecked(false);
                rbNaviStrategyCostNoJam.setChecked(false);
            } else if (naviStrategy == AMapNavi.DrivingSaveMoney) {
                rbNaviStrategySpeed.setChecked(false);
                rbNaviStrategyCost.setChecked(true);
                rbNaviStrategyDistance.setChecked(false);
                rbNaviStrategyNoHighWay.setChecked(false);
                rbNaviStrategyTimeNoJam.setChecked(false);
                rbNaviStrategyCostNoJam.setChecked(false);
            } else if (naviStrategy == AMapNavi.DrivingShortDistance) {
                rbNaviStrategySpeed.setChecked(false);
                rbNaviStrategyCost.setChecked(false);
                rbNaviStrategyDistance.setChecked(true);
                rbNaviStrategyNoHighWay.setChecked(false);
                rbNaviStrategyTimeNoJam.setChecked(false);
                rbNaviStrategyCostNoJam.setChecked(false);
            } else if (naviStrategy == AMapNavi.DrivingNoExpressways) {
                rbNaviStrategySpeed.setChecked(false);
                rbNaviStrategyCost.setChecked(false);
                rbNaviStrategyDistance.setChecked(false);
                rbNaviStrategyNoHighWay.setChecked(true);
                rbNaviStrategyTimeNoJam.setChecked(false);
                rbNaviStrategyCostNoJam.setChecked(false);
            } else if (naviStrategy == AMapNavi.DrivingFastestTime) {
                rbNaviStrategySpeed.setChecked(false);
                rbNaviStrategyCost.setChecked(false);
                rbNaviStrategyDistance.setChecked(false);
                rbNaviStrategyNoHighWay.setChecked(false);
                rbNaviStrategyTimeNoJam.setChecked(true);
                rbNaviStrategyCostNoJam.setChecked(false);
            } else if (naviStrategy == AMapNavi.DrivingAvoidCongestion) {
                rbNaviStrategySpeed.setChecked(false);
                rbNaviStrategyCost.setChecked(false);
                rbNaviStrategyDistance.setChecked(false);
                rbNaviStrategyNoHighWay.setChecked(false);
                rbNaviStrategyTimeNoJam.setChecked(false);
                rbNaviStrategyCostNoJam.setChecked(true);
            }
        }
    }
    
    @Override
    public void onClick(View v) {
        if (v == backView) {
            ActNaviSetting.this.finish();
        } else if (v == btnConfirm) {
        	onConfirmClick();
        } else if (v == naviModeDriveContainer) {
            onNaviModeDriveClick();
        } else if (v == naviModeWalkContainer) {
            onNaviModeWalkClick();
        } else if (v == naviStrategySpeedContainer) {
            onNaviStrateSpeedContainerClick();
        } else if (v == naviStrategyCostContainer) {
            onNaviStrateCostContainerClick();
        } else if (v == naviStrategyDistanceContainer) {
            onNaviStrateDistanceContainerClick();
        } else if (v == naviStrategyNoHighWayContainer) {
            onNaviStrateNoHighWayContainerClick();
        } else if (v == naviStrategyTimeNoJamContainer) {
            onNaviStrateTimeNoJamContainerClick();
        } else if (v == naviStrategyCostNoJamContainer) {
            onNaviStrateCostNoJamContainerClick();
        }
    }
    
    private void onNaviModeDriveClick() {
        if (naviMode == NAVI_MODE_DRIVE) {
            return;
        }
        naviMode = NAVI_MODE_DRIVE;
        rbNaviModeDrive.setChecked(true);
        rbNaviModeWalk.setChecked(false);
    }
    
    private void onNaviModeWalkClick() {
        if (naviMode == NAVI_MODE_WALK) {
            return;
        }
        naviMode = NAVI_MODE_WALK;
        rbNaviModeDrive.setChecked(false);
        rbNaviModeWalk.setChecked(true);
    }
    
    private void onNaviStrateSpeedContainerClick() {
        if (naviStrategy == AMapNavi.DrivingDefault) {
            return;
        }
        naviStrategy = AMapNavi.DrivingDefault;
        rbNaviStrategySpeed.setChecked(true);
        rbNaviStrategyCost.setChecked(false);
        rbNaviStrategyDistance.setChecked(false);
        rbNaviStrategyNoHighWay.setChecked(false);
        rbNaviStrategyTimeNoJam.setChecked(false);
        rbNaviStrategyCostNoJam.setChecked(false);
    }
    
    private void onNaviStrateCostContainerClick() {
        if (naviStrategy == AMapNavi.DrivingSaveMoney) {
            return;
        }
        naviStrategy = AMapNavi.DrivingSaveMoney;
        rbNaviStrategySpeed.setChecked(false);
        rbNaviStrategyCost.setChecked(true);
        rbNaviStrategyDistance.setChecked(false);
        rbNaviStrategyNoHighWay.setChecked(false);
        rbNaviStrategyTimeNoJam.setChecked(false);
        rbNaviStrategyCostNoJam.setChecked(false);
    }
    
    private void onNaviStrateDistanceContainerClick() {
        if (naviStrategy == AMapNavi.DrivingShortDistance) {
            return;
        }
        naviStrategy = AMapNavi.DrivingShortDistance;
        rbNaviStrategySpeed.setChecked(false);
        rbNaviStrategyCost.setChecked(false);
        rbNaviStrategyDistance.setChecked(true);
        rbNaviStrategyNoHighWay.setChecked(false);
        rbNaviStrategyTimeNoJam.setChecked(false);
        rbNaviStrategyCostNoJam.setChecked(false);
    }
    
    private void onNaviStrateNoHighWayContainerClick() {
        if (naviStrategy == AMapNavi.DrivingNoExpressways) {
            return;
        }
        naviStrategy = AMapNavi.DrivingNoExpressways;
        rbNaviStrategySpeed.setChecked(false);
        rbNaviStrategyCost.setChecked(false);
        rbNaviStrategyDistance.setChecked(false);
        rbNaviStrategyNoHighWay.setChecked(true);
        rbNaviStrategyTimeNoJam.setChecked(false);
        rbNaviStrategyCostNoJam.setChecked(false);
    }
    
    private void onNaviStrateTimeNoJamContainerClick() {
        if (naviStrategy == AMapNavi.DrivingFastestTime) {
            return;
        }
        naviStrategy = AMapNavi.DrivingFastestTime;
        rbNaviStrategySpeed.setChecked(false);
        rbNaviStrategyCost.setChecked(false);
        rbNaviStrategyDistance.setChecked(false);
        rbNaviStrategyNoHighWay.setChecked(false);
        rbNaviStrategyTimeNoJam.setChecked(true);
        rbNaviStrategyCostNoJam.setChecked(false);
    }
    
    private void onNaviStrateCostNoJamContainerClick() {
        if (naviStrategy == AMapNavi.DrivingAvoidCongestion) {
            return;
        }
        naviStrategy = AMapNavi.DrivingAvoidCongestion;
        rbNaviStrategySpeed.setChecked(false);
        rbNaviStrategyCost.setChecked(false);
        rbNaviStrategyDistance.setChecked(false);
        rbNaviStrategyNoHighWay.setChecked(false);
        rbNaviStrategyTimeNoJam.setChecked(false);
        rbNaviStrategyCostNoJam.setChecked(true);
    }
    
    private void onConfirmClick() {
		Intent intent = new Intent();
		intent.putExtra(IntentKey.INTENT_KEY_NAVI_MODE, naviMode);
		intent.putExtra(IntentKey.INTENT_KEY_NAVI_STRATEGY, naviStrategy);
		setResult(Activity.RESULT_OK, intent);
		ActNaviSetting.this.finish();
	}

    
}

package com.zlove.navi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.OnMapLoadedListener;
import com.amap.api.maps.MapView;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.view.RouteOverLay;
import com.zlove.channel.R;


public class ActRouteShow extends BaseActivity implements OnClickListener, OnMapLoadedListener {
    
    private View backView = null;
    private TextView tvTitle = null;
    private TextView tvRouteLength = null;
    private TextView tvRouteTime = null;
    private TextView tvRoutePay = null;
    private Button btnStatNavi = null;
    
    private MapView mapView = null;
    private AMap aMap;
    private AMapNavi aMapNavi;
    private RouteOverLay routeOverlay;
    
    private boolean isMapLoaded = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_navi_show_route);
        initView(savedInstanceState);
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        initNavi();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    
    private void initView(Bundle savedInstanceState) {
        backView = findViewById(R.id.id_back);
        tvTitle = (TextView) findViewById(R.id.id_title);
        tvRouteLength = (TextView) findViewById(R.id.route_length);
        tvRouteTime = (TextView) findViewById(R.id.route_time);
        tvRoutePay = (TextView) findViewById(R.id.route_pay);
        btnStatNavi = (Button) findViewById(R.id.id_confirm);
        
        mapView = (MapView) findViewById(R.id.id_map);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        aMap.setOnMapLoadedListener(this);
        routeOverlay = new RouteOverLay(aMap, null);
        
        backView.setOnClickListener(this);
        tvTitle.setText("线路预览");
        btnStatNavi.setOnClickListener(this);
    }
    
    private void initNavi() {
        aMapNavi = AMapNavi.getInstance(this);
        AMapNaviPath naviPath = aMapNavi.getNaviPath();
        if (naviPath == null) {
            return;
        }
        // 获取路径规划线路，显示到地图上
        routeOverlay.setRouteInfo(naviPath);
        routeOverlay.addToMap();
        if (isMapLoaded) {
            routeOverlay.zoomToSpan();
        }
        
        double length = ((int) (naviPath.getAllLength() / (double) 1000 * 10)) / (double) 10;
        // 不足分钟 按分钟计
        int time = (naviPath.getAllTime() + 59) / 60;
        int cost = naviPath.getTollCost();
        
        tvRouteLength.setText("全程约" + String.valueOf(length) + "公里");
        tvRouteTime.setText("花费约" + String.valueOf(time) + "分钟");
        tvRoutePay.setText("收费" + String.valueOf(cost) + "元");
    }
    
    @Override
    public void onClick(View v) {
        if (v == backView) {
            ActRouteShow.this.finish();
        } else if (v == btnStatNavi) {
        	Intent routeIntent = new Intent(ActRouteShow.this, NaviCustomActivity.class);
            startActivity(routeIntent);
        }
    }

    @Override
    public void onMapLoaded() {
        isMapLoaded = true;
        if (routeOverlay != null) {
            routeOverlay.zoomToSpan();
        }
    }
}

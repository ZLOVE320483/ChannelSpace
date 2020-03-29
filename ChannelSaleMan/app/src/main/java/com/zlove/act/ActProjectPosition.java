package com.zlove.act;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.zlove.base.ActChannelBase;
import com.zlove.channelsaleman.R;
import com.zlove.constant.IntentKey;


public class ActProjectPosition extends ActChannelBase implements OnClickListener {

    private String lng;
    private String lat;
    
    private View backView = null;
    private TextView tvTitle = null;

    private MapView mapView = null;
    private AMap aMap = null;
    
    private Marker positionMarker;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_project_position);
        if (getIntent() != null) {
            Intent intent = getIntent();
            if (intent.hasExtra(IntentKey.INTENT_KEY_PROJECT_LAT)) {
                lat = intent.getStringExtra(IntentKey.INTENT_KEY_PROJECT_LAT);
            }
            if (intent.hasExtra(IntentKey.INTENT_KEY_PROJECT_LNG)) {
                lng = intent.getStringExtra(IntentKey.INTENT_KEY_PROJECT_LNG);
            }
        }
        mapView = (MapView) findViewById(R.id.id_map);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();

        backView = findViewById(R.id.id_back);
        tvTitle = (TextView) findViewById(R.id.id_title);
        tvTitle.setText("楼盘位置");
        backView.setOnClickListener(this);
        
        if (positionMarker != null) {
            positionMarker.remove();
        }
        
        LatLng posLatLng = new LatLng(Double.valueOf(lat), Double.valueOf(lng));
        
        positionMarker = aMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.red_poi))));
        positionMarker.setPosition(posLatLng);
        
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posLatLng, 18));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
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
    
    @Override
    public void onClick(View view) {
        if (view == backView) {
            ActProjectPosition.this.finish();
        }
    }
}

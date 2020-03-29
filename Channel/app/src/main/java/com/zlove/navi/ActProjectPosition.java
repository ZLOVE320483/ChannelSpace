package com.zlove.navi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.zlove.channel.R;
import com.zlove.constant.IntentKey;

import java.util.ArrayList;
import java.util.List;

public class ActProjectPosition extends BaseActivity implements OnClickListener {
    
    private String lng;
    private String lat;
	
	private View backView = null;
	private TextView tvTitle = null;
	private Button btnCalRoute = null;
	private Button btnNaviSetting = null;
	
	private MapView mapView = null;
	private AMap aMap = null;
	private AMapNavi aMapNavi;
	private AMapNaviListener aMapNaviListener;
	private LocationManagerProxy locationManagerProxy;
	
	private List<NaviLatLng> startPois = new ArrayList<NaviLatLng>();
	private List<NaviLatLng> endPois = new ArrayList<NaviLatLng>();
	private NaviLatLng startPoint = new NaviLatLng();
	private NaviLatLng endPoint = new NaviLatLng();
	
	private Marker startMarker;
	private Marker endMarker;
	private Marker gpsMarker;
	private Marker positionMarker;
	
	// 导航模式    驾车  OR 步行
	private static final int NAVI_MODE_DRIVE = 0;
	private static final int NAVI_MODE_WALK = 1;
	private int naviMode = NAVI_MODE_DRIVE; // 默认驾车
	
	// 行驶策略        默认速度优先
	private int driveStrate = AMapNavi.DrivingDefault;
	
    private final static int CALCULATEERROR = 1;// 启动路径计算失败状态
    private final static int CALCULATESUCCESS = 2;// 启动路径计算成功状态
	
	private ProgressDialog gpsDialog; // 定位框
	private ProgressDialog routeCalDialog; // 路线规划框
	
	private boolean isGpsGet = false;
	
	private AMapLocationListener locationListener = new AMapLocationListener() {
        
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
        
        @Override
        public void onProviderEnabled(String provider) {
        }
        
        @Override
        public void onProviderDisabled(String provider) {
        }
        
        @Override
        public void onLocationChanged(Location location) {
        }
        
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (location != null && location.getAMapException().getErrorCode() == 0) {
                isGpsGet = true;
                startPoint = new NaviLatLng(location.getLatitude(), location.getLongitude());
                gpsMarker.setPosition(new LatLng(startPoint.getLatitude(), startPoint.getLongitude()));
                startPois.clear();
                startPois.add(startPoint);
                dismissGpsDialog();
                calculateRoute();
            } else {
                showShortToast("定位出现异常");
            }
        }
    };
	
	
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
		initView(savedInstanceState);
		initMapAndNavi();
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
        // 以上两句必须重写
        // 以下两句逻辑是为了保证进入首页开启定位和加入导航回调
        AMapNavi.getInstance(this).setAMapNaviListener(getAMapNaviListener());
        aMapNavi.startGPS();
        TTSController.getInstance(this).startSpeaking();
	}
	
	@Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        // 以上两句必须重写
        // 下边逻辑是移除监听
        AMapNavi.getInstance(this).removeAMapNaviListener(getAMapNaviListener());
    }
	
	@Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();

    }
	
	private void initView(Bundle savedInstanceState) {
	    mapView = (MapView) findViewById(R.id.id_map);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
	    btnCalRoute = (Button) findViewById(R.id.id_confirm);
	    btnNaviSetting = (Button) findViewById(R.id.id_setting);
		backView = findViewById(R.id.id_back);
		tvTitle = (TextView) findViewById(R.id.id_title);
		tvTitle.setText("楼盘位置");
		backView.setOnClickListener(this);
		btnCalRoute.setOnClickListener(this);
		btnNaviSetting.setOnClickListener(this);
	}
	
	private void initMapAndNavi() {
	    // 初始语音播报资源
	    setVolumeControlStream(AudioManager.STREAM_MUSIC);
	    // 初始化导航引擎
	    aMapNavi = AMapNavi.getInstance(this);
	    
	    startMarker = aMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.start))));
	    endMarker = aMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.end))));
	    gpsMarker = aMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.location_marker))));
	    
	    endPoint = new NaviLatLng(Double.valueOf(lat), Double.valueOf(lng));
	    endPois.clear();
	    endPois.add(endPoint);
	    
	    startMarker.setPosition(new LatLng(startPoint.getLatitude(), startPoint.getLongitude()));
	    
	    if (positionMarker != null) {
            positionMarker.remove();
        }
	    
	    positionMarker = aMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.red_poi))));
        positionMarker.setPosition(new LatLng(endPoint.getLatitude(), endPoint.getLongitude()));
	    
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(endPoint.getLatitude(), endPoint.getLongitude()), 18));
    }

	@Override
	public void onClick(View view) {
		if (view == backView) {
			ActProjectPosition.this.finish();
		} else if (view == btnCalRoute) {
            calculateRoute();
        } else if (view == btnNaviSetting) {
            Intent intent = new Intent(ActProjectPosition.this, ActNaviSetting.class);
            intent.putExtra(IntentKey.INTENT_KEY_NAVI_MODE, naviMode);
            intent.putExtra(IntentKey.INTENT_KEY_NAVI_STRATEGY, driveStrate);
            startActivityForResult(intent, IntentKey.REQUEST_CODE_NAVI_METHOD);
        }
	}
	
	private void showGpsDialog() {
        if (gpsDialog == null) {
            gpsDialog = new ProgressDialog(this);
        }
        gpsDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        gpsDialog.setIndeterminate(false);
        gpsDialog.setCancelable(true);
        gpsDialog.setMessage("定位中...");
        gpsDialog.show();
    }
	
	private void dismissGpsDialog() {
        if (gpsDialog != null && gpsDialog.isShowing()) {
            gpsDialog.dismiss();
        }
    }
	
	private void calculateRoute() {
        if (!isGpsGet) {
            locationManagerProxy = LocationManagerProxy.getInstance(this);
            // 进行一次定位
            locationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, -1, 15, locationListener);
            showGpsDialog();
            return;
        }
        isGpsGet = false;
        switch (naviMode) {
            case NAVI_MODE_DRIVE: {
                int res = calculateDriverRoute();
                if (res == CALCULATEERROR) {
                    showShortToast("很抱歉,路线计算失败");
                }
                break;
            }
            
            case NAVI_MODE_WALK: {
                int res = calculateWalkRoute();
                if (res == CALCULATEERROR) {
                    showShortToast("很抱歉,路线计算失败");
                }
                break;
            }

            default:
                break;
        }
        showRouteCalDialog();
    }
	
	private int calculateDriverRoute() {
        int code = CALCULATEERROR;
        if (aMapNavi.calculateDriveRoute(startPois, endPois, new ArrayList<NaviLatLng>(), driveStrate)) {
            code = CALCULATESUCCESS;
        } else {
            code = CALCULATEERROR;
        }
        return code;
    }
	
	private int calculateWalkRoute() {
        int code = CALCULATEERROR;
        if (aMapNavi.calculateWalkRoute(startPoint, endPoint)) {
            code = CALCULATESUCCESS;
        } else {
            code = CALCULATEERROR;
        }
        return code;
    }
	
	/**
	 * 导航回调函数
	 */
	private AMapNaviListener getAMapNaviListener() {
	    if (aMapNaviListener == null) {
	        aMapNaviListener = new AMapNaviListener() {

                @Override
                public void onTrafficStatusUpdate() {
                }

                @Override
                public void onStartNavi(int arg0) {
                }

                @Override
                public void onReCalculateRouteForYaw() {
                }

                @Override
                public void onReCalculateRouteForTrafficJam() {
                }

                @Override
                public void onLocationChange(AMapNaviLocation location) {
                }

                @Override
                public void onInitNaviSuccess() {
                }

                @Override
                public void onInitNaviFailure() {
                }

                @Override
                public void onGetNavigationText(int arg0, String arg1) {
                }

                @Override
                public void onEndEmulatorNavi() {
                }

                @Override
                public void onCalculateRouteSuccess() {
                    dismissRouteCalDialog();
                    positionMarker.remove();
                    endMarker.setPosition(new LatLng(endPoint.getLatitude(), endPoint.getLongitude()));
                    
                    Intent intent = new Intent(ActProjectPosition.this, ActRouteShow.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);

                }

                @Override
                public void onCalculateRouteFailure(int arg0) {
                    dismissRouteCalDialog();
                    showShortToast("很抱歉,路径规划出错");
                }

                @Override
                public void onArrivedWayPoint(int arg0) {
                }

                @Override
                public void onArriveDestination() {
                }

                @Override
                public void onGpsOpenStatus(boolean arg0) {
                }

                @Override
                public void onNaviInfoUpdated(AMapNaviInfo arg0) {
                }

                @Override
                public void onNaviInfoUpdate(NaviInfo arg0) {
                }
            };
        
        }
	    return aMapNaviListener;
	}
	
	private void showRouteCalDialog() {
	    if (routeCalDialog == null)
            routeCalDialog = new ProgressDialog(this);
        routeCalDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        routeCalDialog.setIndeterminate(false);
        routeCalDialog.setCancelable(true);
        routeCalDialog.setMessage("线路规划中...");
        routeCalDialog.show();
    }
	
	private void dismissRouteCalDialog() {
        if (routeCalDialog != null && routeCalDialog.isShowing()) {
            routeCalDialog.dismiss();
        }
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null && requestCode == IntentKey.REQUEST_CODE_NAVI_METHOD) {
			naviMode = data.getIntExtra(IntentKey.INTENT_KEY_NAVI_MODE, NAVI_MODE_DRIVE);
			driveStrate = data.getIntExtra(IntentKey.INTENT_KEY_NAVI_STRATEGY, AMapNavi.DrivingDefault);
		}
	}
}

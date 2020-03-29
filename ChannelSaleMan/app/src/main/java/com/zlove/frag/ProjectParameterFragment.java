package com.zlove.frag;

import org.apache.http.Header;

import android.app.Dialog;
import android.view.View;
import android.widget.TextView;

import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.util.DialogManager;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.UIUtil;
import com.zlove.bean.project.ProjectParamsBaseData;
import com.zlove.bean.project.ProjectParamsBean;
import com.zlove.bean.project.ProjectParamsData;
import com.zlove.bean.project.ProjectParamsOtherData;
import com.zlove.channelsaleman.R;
import com.zlove.config.ChannelCookie;
import com.zlove.http.ProjectHttpRequest;


public class ProjectParameterFragment extends BaseFragment {

    // 价格
    private TextView tvProjectPrice;
    // 物业类型
    private TextView tvProjectPropertyType;
    // 装修情况
    private TextView tvProjectDecoration;
    // 物业费
    private TextView tvProjectPropertyPrice;
    // 面积
    private TextView tvProjectArea;
    // 产品
    private TextView tvProjectProduct;
    // 得房率
    private TextView tvProjectHouseRate;
    // 容积率
    private TextView tvProjectPlotRate;
    // 总建面积
    private TextView tvProjectTotalArea;
    // 总占地
    private TextView tvProjectTotalFloorArea;
    // 总套数
    private TextView tvProjectTotalSet;
    // 已售套数
    private TextView tvProjectSaleSet;
    // 车位配比
    private TextView tvProjectParkSpace;
    // 开盘时间
    private TextView tvProjectOpenTime;
    // 入住时间
    private TextView tvProjectCheckInTime;
    // 开发商
    private TextView tvProjectDeveloper;
    // 物业公司
    private TextView tvProjectPropertyCorp;
    // 地址
    private TextView tvProjectAddress;
    // 项目简介
    private TextView tvProjectIntroduce;
    // 交通
    private TextView tvTraffic;
    // 医疗
    private TextView tvHospital;
    // 学校
    private TextView tvSchool;
    // 商业
    private TextView tvShop;
    
    private Dialog loadingDialog;
    
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_project_parameter;
    }

    @Override
    protected void setUpView(View view) {
        
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("楼盘参数");
        
        tvProjectPrice = (TextView) view.findViewById(R.id.id_project_price);
        tvProjectPropertyType = (TextView) view.findViewById(R.id.id_project_property_type);
        tvProjectDecoration = (TextView) view.findViewById(R.id.id_project_decoration);
        tvProjectPropertyPrice = (TextView) view.findViewById(R.id.id_project_property_price);
        tvProjectArea = (TextView) view.findViewById(R.id.id_project_area);
        tvProjectProduct = (TextView) view.findViewById(R.id.id_project_product);
        tvProjectHouseRate = (TextView) view.findViewById(R.id.id_project_house_rate);
        tvProjectPlotRate = (TextView) view.findViewById(R.id.id_project_plot_rate);
        tvProjectTotalArea = (TextView) view.findViewById(R.id.id_project_total_area);
        tvProjectTotalFloorArea = (TextView) view.findViewById(R.id.id_project_total_floor_area);
        tvProjectTotalSet = (TextView) view.findViewById(R.id.id_project_total_set);
        tvProjectSaleSet = (TextView) view.findViewById(R.id.id_project_sale_set);
        tvProjectParkSpace = (TextView) view.findViewById(R.id.id_project_park_space);
        tvProjectOpenTime = (TextView) view.findViewById(R.id.id_project_open_time);
        tvProjectCheckInTime = (TextView) view.findViewById(R.id.id_project_check_in_time);
        tvProjectDeveloper = (TextView) view.findViewById(R.id.id_project_developer);
        tvProjectPropertyCorp = (TextView) view.findViewById(R.id.id_project_property_corp);
        tvProjectAddress = (TextView) view.findViewById(R.id.id_project_address);
        tvProjectIntroduce = (TextView) view.findViewById(R.id.id_project_introduce);
        tvTraffic = (TextView) view.findViewById(R.id.tv_traffic);
        tvHospital = (TextView) view.findViewById(R.id.tv_hospital);
        tvSchool = (TextView) view.findViewById(R.id.tv_school);
        tvShop = (TextView) view.findViewById(R.id.tv_shop);

        if (loadingDialog == null) {
            loadingDialog = DialogManager.getLoadingDialog(getActivity(), "正在加载...");
        }
        
        ProjectHttpRequest.requestProjectParams(ChannelCookie.getInstance().getCurrentHouseId(), new GetProjectParamsHandler(this));
    }
    
    class GetProjectParamsHandler extends HttpResponseHandlerFragment<ProjectParameterFragment> {

        public GetProjectParamsHandler(ProjectParameterFragment context) {
            super(context);
        }
        
        @Override
        public void onStart() {
            super.onStart();
            if (loadingDialog != null) {
                loadingDialog.show();
            }
        }
        
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] content) {
            super.onSuccess(statusCode, headers, content);
            if (!isAdded()) {
                return;
            }
            if (content != null) {
                ProjectParamsBean bean = JsonUtil.toObject(new String(content), ProjectParamsBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        ProjectParamsData data = bean.getData();
                        if (data != null) {
                            tvProjectIntroduce.setText(data.getSummary());
                            ProjectParamsBaseData baseData = data.getBase_data();
                            if (baseData != null) {
                                tvProjectPrice.setText(UIUtil.replaceNullOrEmpty(baseData.getPrice_desc()));
                                tvProjectPropertyType.setText(UIUtil.replaceNullOrEmpty(baseData.getProperty_types()));
                                tvProjectDecoration.setText(UIUtil.replaceNullOrEmpty(baseData.getDecoration_types()));
                                tvProjectPropertyPrice.setText(UIUtil.replaceNullOrEmpty(baseData.getProperty_costs()));
                                tvProjectArea.setText(UIUtil.replaceNullOrEmpty(baseData.getCover_area()));
                                tvProjectHouseRate.setText(UIUtil.replaceNullOrEmpty(baseData.getOwn_rate()));
                                tvProjectProduct.setText(UIUtil.replaceNullOrEmpty(baseData.getHouse_types()));
                                tvProjectPlotRate.setText(UIUtil.replaceNullOrEmpty(baseData.getVolume_rate()));
                                tvProjectTotalArea.setText(UIUtil.replaceNullOrEmpty(baseData.getBuilding_area()));
                                tvProjectTotalFloorArea.setText(UIUtil.replaceNullOrEmpty(baseData.getTotal_area()));
                                tvProjectTotalSet.setText(UIUtil.replaceNullOrEmpty(baseData.getFamily_num()));
                                tvProjectSaleSet.setText(UIUtil.replaceNullOrEmpty(baseData.getSold_num()));
                                tvProjectParkSpace.setText(UIUtil.replaceNullOrEmpty(baseData.getParking_rate()));
                                tvProjectOpenTime.setText(UIUtil.replaceNullOrEmpty(baseData.getOpen_time()));
                                tvProjectCheckInTime.setText(UIUtil.replaceNullOrEmpty(baseData.getEntry_time()));
                                tvProjectDeveloper.setText(UIUtil.replaceNullOrEmpty(baseData.getDeveloper()));
                                tvProjectPropertyCorp.setText(UIUtil.replaceNullOrEmpty(baseData.getProperty_company()));
                                tvProjectAddress.setText(UIUtil.replaceNullOrEmpty(baseData.getAddress()));
                            }
                            ProjectParamsOtherData otherData = data.getOther();
                            if (otherData != null) {
                                tvTraffic.setText(UIUtil.replaceNullOrEmpty(otherData.getTraffic()));
                                tvHospital.setText(UIUtil.replaceNullOrEmpty(otherData.getHospital()));
                                tvSchool.setText(UIUtil.replaceNullOrEmpty(otherData.getSchool()));
                                tvShop.setText(UIUtil.replaceNullOrEmpty(otherData.getBusiness()));
                            }
                        }
                    } else {
                        showShortToast(bean.getMessage());
                    }
                }
            }
        }
        
        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] content,
                Throwable error) {
            super.onFailure(statusCode, headers, content, error);
        }
        
        @Override
        public void onFinish() {
            super.onFinish();
            if (loadingDialog != null && loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
        }
        
    }

}

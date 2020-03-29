
package com.zlove.frag;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zlove.act.ActCustomerAdd;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.util.DialogManager;
import com.zlove.base.util.JsonUtil;
import com.zlove.base.util.ListUtils;
import com.zlove.base.widget.PagerSlidingTabStrip;
import com.zlove.bean.project.HouseListBean;
import com.zlove.bean.project.HouseListData;
import com.zlove.bean.project.HouseListItem;
import com.zlove.channelsaleman.R;
import com.zlove.config.ChannelCookie;
import com.zlove.constant.IntentKey;
import com.zlove.http.ProjectHttpRequest;

import org.apache.http.Header;

import java.util.List;

public class CustomerMainFragment extends BaseFragment implements OnClickListener, OnPageChangeListener, DialogManager.ChangeProjectListener {

    private static final int TAB_COUNT = 2;

    private static final String[] TITLES = { "渠道客户", "我的客户" };

    private ImageView btnAddCustomer = null;

    private TextView tvTitle = null;

    private PagerSlidingTabStrip tabHead;

    private ViewPager viewPager;

    private CustomerPagerAdapter pagerAdapter;

    private CustomerFromIndependentFragment independentFragment;

    private CustomerFromSelfFragment selfFragment;

    private List<HouseListItem> houseList;

    private View topView;
    private PopupWindow popupWindow;

    @Override
    protected int getInflateLayout() {
        return R.layout.frag_customer_main;
    }

    @Override
    protected void setUpView(View view) {
        ((TextView) view.findViewById(R.id.id_title)).setText("客户");
        tvTitle = (TextView) view.findViewById(R.id.id_title);
        topView = view.findViewById(R.id.id_top);

        btnAddCustomer = (ImageView) view.findViewById(R.id.id_confirm);
        btnAddCustomer.setOnClickListener(this);
        btnAddCustomer.setVisibility(View.GONE);

        tabHead = (PagerSlidingTabStrip) view.findViewById(R.id.tab_head);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        pagerAdapter = new CustomerPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabHead.setViewPager(viewPager);
        tabHead.setOnPageChangeListener(this);
        tvTitle.setOnClickListener(this);

        ProjectHttpRequest.requestProjectList("", "", "0", "100", new GetHouseListHandler(this));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ChannelCookie.getInstance().getHouseNum() > 1) {
            String houseName = ChannelCookie.getInstance().getCurrentHouseName();
            if (!TextUtils.isEmpty(houseName)) {
                tvTitle.setText("客户(" + houseName + ")");
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnAddCustomer) {
            Intent intent = new Intent(getActivity(), ActCustomerAdd.class);
            startActivityForResult(intent, IntentKey.REQUEST_CODE_ADD_CUSTOMER);
        } else if (v == tvTitle) {
            if (ListUtils.isEmpty(houseList) || houseList.size() <= 1) {
                return;
            }
            popupWindow = DialogManager.getSelectProjectWindow(getActivity(), houseList, this);
            popupWindow.showAsDropDown(topView);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (independentFragment != null) {
                independentFragment.onActivityResult(requestCode, resultCode, data);
            }
            if (selfFragment != null) {
                selfFragment.onActivityResult(requestCode, resultCode, data);
            }
            if (requestCode == IntentKey.REQUEST_CODE_ADD_CUSTOMER) {
                if (selfFragment != null) {
                    selfFragment.onRefresh();
                }
            }
        }
    }

    class CustomerPagerAdapter extends FragmentPagerAdapter {

        public CustomerPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            switch (arg0) {
                case 0: {
                    if (independentFragment == null) {
                        independentFragment = new CustomerFromIndependentFragment();
                    }
                    return independentFragment;
                }

                case 1: {
                    if (selfFragment == null) {
                        selfFragment = new CustomerFromSelfFragment();
                    }
                    return selfFragment;
                }
                default:
                    break;
            }
            return null;
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int arg0) {
        if (arg0 == 0) {
            btnAddCustomer.setVisibility(View.GONE);
        } else if (arg0 == 1) {
            btnAddCustomer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void changeProject() {
        if (independentFragment != null) {
            independentFragment.onRefresh();
        }
        if (selfFragment != null) {
            selfFragment.onRefresh();
        }
        String houseName = ChannelCookie.getInstance().getCurrentHouseName();
        if (!TextUtils.isEmpty(houseName)) {
            tvTitle.setText("客户(" + houseName + ")");
        }
    }

    class GetHouseListHandler extends HttpResponseHandlerFragment<CustomerMainFragment> {

        public GetHouseListHandler(CustomerMainFragment context) {
            super(context);
        }

        @Override
        public void onStart() {
            super.onStart();
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] content) {
            super.onSuccess(statusCode, headers, content);
            if (content != null) {
                HouseListBean bean = JsonUtil.toObject(new String(content), HouseListBean.class);
                if (bean != null) {
                    HouseListData data = bean.getData();
                    if (data != null) {
                        houseList = data.getHouse_list();
                    }
                }
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] content, Throwable error) {
            super.onFailure(statusCode, headers, content, error);
        }

        @Override
        public void onFinish() {
            super.onFinish();
        }
    }

}

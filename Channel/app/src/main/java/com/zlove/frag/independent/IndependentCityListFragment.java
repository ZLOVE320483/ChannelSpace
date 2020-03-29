
package com.zlove.frag.independent;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zlove.adapter.common.CityAdapter;
import com.zlove.base.BaseFragment;
import com.zlove.base.widget.PinnedSectionListView;
import com.zlove.bean.city.CombineCityListItem;
import com.zlove.bean.common.CityInfo;
import com.zlove.bean.common.IAlphabetSection;
import com.zlove.channel.R;
import com.zlove.constant.IntentKey;
import com.zlove.http.CityHttpRequest;

import java.util.ArrayList;
import java.util.List;

public class IndependentCityListFragment extends BaseFragment implements OnItemClickListener {

    private PinnedSectionListView listView;
    private CityAdapter adapter;
    private List<CityInfo> infos = new ArrayList<CityInfo>();
    
    private String currectCityId = "";
    private String currectCityName = "";
    private ArrayList<CombineCityListItem> cityList = new ArrayList<CombineCityListItem>();

    @Override
    protected int getInflateLayout() {
        return R.layout.frag_city_list;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void setUpView(View view) {
        
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            if (intent.hasExtra(IntentKey.INTENT_KEY_CURRENT_CITY_ID)) {
                currectCityId = intent.getStringExtra(IntentKey.INTENT_KEY_CURRENT_CITY_ID);
            }
            if (intent.hasExtra(IntentKey.INTENT_KEY_CURRENT_CITY_NAME)) {
                currectCityName = intent.getStringExtra(IntentKey.INTENT_KEY_CURRENT_CITY_NAME);
            }
            if (intent.hasExtra(IntentKey.INTENT_KEY_COMBINE_CITY_LIST)) {
                cityList = (ArrayList<CombineCityListItem>) intent.getSerializableExtra(IntentKey.INTENT_KEY_COMBINE_CITY_LIST);
            }
        }
        
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("城市选择");

        listView = (PinnedSectionListView) view.findViewById(R.id.alphabet_list);

        listView.setOnItemClickListener(this);
        listView.setShadowVisible(false);
        
        CityInfo info1 = new CityInfo();
        info1.setSectionType(IAlphabetSection.SECTION);
        info1.setSectionText("当前城市");
        infos.add(info1);

        CityInfo info2 = new CityInfo();
        info2.setSectionType(IAlphabetSection.ITEM);
        info2.setName(currectCityName);
        infos.add(info2);

        if (cityList.size() > 1) {
            CityInfo info3 = new CityInfo();
            info3.setSectionType(IAlphabetSection.SECTION);
            info3.setSectionText("合作城市");
            infos.add(info3);
            for (CombineCityListItem item : cityList) {
                if (!item.getCity_id().equals(currectCityId)) {
                    CityInfo cityInfo = new CityInfo();
                    cityInfo.setSectionType(IAlphabetSection.ITEM);
                    cityInfo.setName(item.getCity_name());
                    cityInfo.setCityId(item.getCity_id());
                    infos.add(cityInfo);
                }
            }
        }

        if (adapter == null) {
            adapter = new CityAdapter(getActivity());
        }
        setListToArrayAdapter(infos, adapter);
        listView.setAdapter(adapter);
    }

    public static <T extends Object> void setListToArrayAdapter(List<T> objList, ArrayAdapter<T> arrayAdapter) {
        if (arrayAdapter == null) {
            return;
        }
        arrayAdapter.clear();
        appendListToArrayAdapter(objList, arrayAdapter);
    }

    public static <T extends Object> void appendListToArrayAdapter(List<T> objList, ArrayAdapter<T> arrayAdapter) {
        if (objList != null) {
            for (T object : objList) {
                if (object != null) {
                    arrayAdapter.add(object);
                }
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        if (arg2 == 0 || arg2 == 1) {
            return;
        }
        CityInfo item = adapter.getItem(arg2);
        if (item != null) {
            if (item.getSection() == IAlphabetSection.SECTION) {
                return;
            } else {
                CityHttpRequest.setCityRequest(item.getCityId(), new CommonHandler(this));
                Intent intent = new Intent();
                intent.putExtra(IntentKey.INTENT_KEY_COMBINE_CITY_ITEM, item);
                getActivity().setResult(Activity.RESULT_OK, intent);
                finishActivity();
            }
        }
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        infos.clear();
    }

}

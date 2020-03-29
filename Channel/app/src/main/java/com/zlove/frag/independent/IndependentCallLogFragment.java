
package com.zlove.frag.independent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.zlove.adapter.common.CallLogAdapter;
import com.zlove.base.BaseFragment;
import com.zlove.base.util.ApplicationUtil;
import com.zlove.base.util.DateFormatUtil;
import com.zlove.base.util.ListUtils;
import com.zlove.bean.common.CallLogInfo;
import com.zlove.channel.R;
import com.zlove.constant.IntentKey;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IndependentCallLogFragment extends BaseFragment implements OnItemClickListener {

    private AsyncQueryHandler queryHandler;

    private List<CallLogInfo> callLogInfos = new ArrayList<CallLogInfo>();

    private CallLogAdapter adapter;

    private ListView listView;

    @Override
    protected int getInflateLayout() {
        return R.layout.common_view_listview;
    }

    @Override
    protected void setUpView(View view) {
        listView = (ListView) view.findViewById(R.id.id_listview);
        listView.setOnItemClickListener(this);
        queryHandler = new GetCallLogHandler(ApplicationUtil.getApplicationContext().getContentResolver());
        initQuery();
    }

    private void initQuery() {
        Uri uri = CallLog.Calls.CONTENT_URI;

        String[] projection = { CallLog.Calls.DATE, CallLog.Calls.NUMBER, CallLog.Calls.TYPE, CallLog.Calls.CACHED_NAME, CallLog.Calls._ID }; // 查询的列
        queryHandler.startQuery(0, null, uri, projection, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
    }

    @SuppressLint("HandlerLeak")
    class GetCallLogHandler extends AsyncQueryHandler {

        public GetCallLogHandler(ContentResolver cr) {
            super(cr);
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0 && ListUtils.isEmpty(callLogInfos)) {
                cursor.moveToFirst();
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToPosition(i);
                    int id = cursor.getInt(cursor.getColumnIndex(CallLog.Calls._ID));
                    int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
                    String cachedName = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));// 缓存的名称与电话号码，如果它的存在
                    String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                    Date date = new Date(cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE)));

                    CallLogInfo info = new CallLogInfo();
                    info.setId(id);
                    info.setType(type);
                    if (!TextUtils.isEmpty(cachedName)) {
                        info.setName(cachedName);
                    } else {
                        info.setName(number);
                    }
                    info.setNumber(number);
                    info.setDate(DateFormatUtil.getCallLogDate(date));
                    callLogInfos.add(info);
                }
            }
            if (adapter == null) {
                adapter = new CallLogAdapter(getActivity(), callLogInfos);
            }
            listView.setAdapter(adapter);
        }
    }
    
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
    	CallLogInfo item = (CallLogInfo) adapter.getItem(arg2);
    	Intent intent = new Intent();
    	if (item != null) {
    		intent.putExtra(IntentKey.INTENT_KEY_CONTACT_NAME, item.getName());
    		intent.putExtra(IntentKey.INTENT_KEY_CONTACT_NUMBER, item.getNumber());
		}
    	getActivity().setResult(Activity.RESULT_OK, intent);
    	finishActivity();
    }

}

package com.zlove.frag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

import com.zlove.adapter.ContactAdapter;
import com.zlove.base.BaseFragment;
import com.zlove.base.util.ApplicationUtil;
import com.zlove.base.util.ListUtils;
import com.zlove.base.widget.AlphaBetIndexerBar;
import com.zlove.base.widget.PinnedSectionListView;
import com.zlove.bean.common.ContactInfo;
import com.zlove.bean.common.IAlphabetSection;
import com.zlove.channelsaleman.R;
import com.zlove.constant.IntentKey;


public class ContactSubFragment extends BaseFragment implements OnItemClickListener {

    private AsyncQueryHandler asyncQuery;
    private PinnedSectionListView listView;
    private AlphaBetIndexerBar indexerBar;
    private ContactAdapter adapter;
    private List<ContactInfo> contactInfos = new ArrayList<ContactInfo>();
    private Map<String, Integer> sectionMap = new HashMap<String, Integer>();
    private List<ContactInfo> afterHandleContactList = new ArrayList<ContactInfo>();

    @Override
    protected int getInflateLayout() {
        return R.layout.common_view_alphatbet_list;
    }

    @Override
    protected void setUpView(View view) {
        listView = (PinnedSectionListView) view.findViewById(R.id.alphabet_list);
        indexerBar = (AlphaBetIndexerBar) view.findViewById(R.id.index_bar);
        
        listView.setOnItemClickListener(this);

        asyncQuery = new GetContactHandler(ApplicationUtil.getApplicationContext().getContentResolver());
        initQuery();

        indexerBar.setOnTouchingLetterChangedListener(new AlphaBetIndexerBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                if (sectionMap != null && sectionMap.get(s) != null) {
                    if (AlphaBetIndexerBar.SEARCH_INDEXER.equals(s)) {
                        listView.setSelection(0);
                    } else {
                        listView.setSelection(sectionMap.get(s));
                    }
                }
            }
        });

        listView.setShadowVisible(false);
    }

    private void initQuery() {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI; // 联系人的Uri
        String[] projection = { ContactsContract.CommonDataKinds.Phone._ID, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.DATA1, "sort_key", ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.PHOTO_ID, ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY }; // 查询的列
        asyncQuery.startQuery(0, null, uri, projection, null, null, "sort_key COLLATE LOCALIZED asc"); // 按照sort_key升序查询
    }

    @SuppressLint("HandlerLeak")
    class GetContactHandler extends AsyncQueryHandler {

        public GetContactHandler(ContentResolver cr) {
            super(cr);
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0 && ListUtils.isEmpty(afterHandleContactList)) {
                cursor.moveToFirst();
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToPosition(i);
                    String name = cursor.getString(1);
                    String number = cursor.getString(2);
                    String sortKey = cursor.getString(3);

                    ContactInfo info = new ContactInfo();
                    info.setFullName(name);
                    info.setNamePinyin(sortKey);
                    info.setNumber(number);
                    contactInfos.add(info);
                }
                handleData();
            }
            if (adapter == null) {
                adapter = new ContactAdapter(getActivity());
            }
            setListToArrayAdapter(afterHandleContactList, adapter);
            listView.setAdapter(adapter);
        }
    }

    private void handleData() {
        int currentAlphabet = 0;
        boolean isInsert = false;
        for (ContactInfo item : contactInfos) {
            if (item != null) {
                IAlphabetSection alphabetSection = (IAlphabetSection) item;
                int itemSection = alphabetSection.getSection();
                if (itemSection == IAlphabetSection.ALPHABET_OTHER
                    || (itemSection >= IAlphabetSection.ALPHABET_START && itemSection <= IAlphabetSection.ALPHABET_END)) {
                    if (itemSection == IAlphabetSection.ALPHABET_OTHER) {
                        // ReIndex.
                        currentAlphabet = 0;
                    }
                    if (itemSection == IAlphabetSection.ALPHABET_OTHER) {
                        if (!isInsert) {
                            isInsert = true;
                            String sectionText = Character.toString((char) itemSection);
                            sectionMap.put(sectionText, afterHandleContactList.size());
                            IAlphabetSection sectionItem = alphabetSection.instanceSectionItem();
                            sectionItem.setSectionType(IAlphabetSection.SECTION);
                            sectionItem.setSectionText(sectionText);
                            afterHandleContactList.add((ContactInfo) sectionItem);
                            currentAlphabet = itemSection;
                        }
                    } else {
                        if (currentAlphabet < itemSection) {
                            String sectionText = Character.toString((char) itemSection);
                            sectionMap.put(sectionText, afterHandleContactList.size());
                            IAlphabetSection sectionItem = alphabetSection.instanceSectionItem();
                            sectionItem.setSectionType(IAlphabetSection.SECTION);
                            sectionItem.setSectionText(sectionText);
                            afterHandleContactList.add((ContactInfo) sectionItem);
                            currentAlphabet = itemSection;
                        }
                    }
                }
                afterHandleContactList.add(item);
            }
        }
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
        ContactInfo item = adapter.getItem(arg2);
        Intent intent = new Intent();
        if (item != null) {
            intent.putExtra(IntentKey.INTENT_KEY_CONTACT_NAME, item.getFullName());
            intent.putExtra(IntentKey.INTENT_KEY_CONTACT_NUMBER, item.getNumber());
        }
        getActivity().setResult(Activity.RESULT_OK, intent);
        finishActivity();
    }
}

package com.zlove.frag;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zlove.base.BaseFragment;
import com.zlove.channelsaleman.R;
import com.zlove.constant.IntentKey;


public class FriendPhoneChangeFragment extends BaseFragment implements OnClickListener {

    private Button btnConfirm = null;
    private EditText etUserName = null;

    private String friendPhone = "";
    
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_user_name_change;
    }

    @Override
    protected void setUpView(View view) {
        if (getActivity().getIntent() != null) {
            if (getActivity().getIntent().hasExtra(IntentKey.INTENT_KEY_FRIEND_PHONE)) {
                friendPhone = getActivity().getIntent().getStringExtra(IntentKey.INTENT_KEY_FRIEND_PHONE);
            }
        }
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("修改姓名");
        etUserName = (EditText) view.findViewById(R.id.id_user_name);
        etUserName.setText(friendPhone);
        btnConfirm = (Button) view.findViewById(R.id.id_confirm);
        btnConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btnConfirm) {
            friendPhone = etUserName.getText().toString().trim();
            if (TextUtils.isEmpty(friendPhone)) {
                showShortToast("请输入好友电话");
                return;
            }
        }
    }

}

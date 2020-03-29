package com.zlove.frag;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zlove.base.BaseFragment;
import com.zlove.channelsaleman.R;
import com.zlove.constant.IntentKey;


public class FriendNameChangeFragment extends BaseFragment implements OnClickListener {

    private Button btnConfirm = null;
    private EditText etUserName = null;

    private String userName = "";
    
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_user_name_change;
    }

    @Override
    protected void setUpView(View view) {
        if (getActivity().getIntent() != null) {
            if (getActivity().getIntent().hasExtra(IntentKey.INTENT_KEY_FRIEND_NAME)) {
                userName = getActivity().getIntent().getStringExtra(IntentKey.INTENT_KEY_FRIEND_NAME);
            }
        }
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("修改姓名");
        etUserName = (EditText) view.findViewById(R.id.id_user_name);
        etUserName.setText(userName);
        btnConfirm = (Button) view.findViewById(R.id.id_confirm);
        btnConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btnConfirm) {
            userName = etUserName.getText().toString().trim();
            if (TextUtils.isEmpty(userName)) {
                showShortToast("请输入好友姓名");
                return;
            } else {
				Intent intent = new Intent();
				intent.putExtra(IntentKey.INTENT_KEY_FRIEND_NAME, userName);
				finishActivity(intent);
			}
        }
    }

}

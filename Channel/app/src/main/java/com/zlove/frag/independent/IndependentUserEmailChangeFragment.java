package com.zlove.frag.independent;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zlove.base.BaseFragment;
import com.zlove.channel.R;
import com.zlove.constant.IntentKey;


public class IndependentUserEmailChangeFragment extends BaseFragment implements OnClickListener {
    
    private Button btnConfirm = null;
    private EditText etUserEmail = null;
    
    private String userEmail = "";

    @Override
    protected int getInflateLayout() {
        return R.layout.frag_user_email_change;
    }

    @Override
    protected void setUpView(View view) {
        if (getActivity().getIntent() != null) {
        if (getActivity().getIntent().hasExtra(IntentKey.INTENT_KEY_USER_PHONE)) {
            userEmail = getActivity().getIntent().getStringExtra(IntentKey.INTENT_KEY_USER_PHONE);
        }
    }
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("修改邮箱");
        etUserEmail = (EditText) view.findViewById(R.id.id_email);
        etUserEmail.setText(userEmail);
        btnConfirm = (Button) view.findViewById(R.id.id_confirm);
        btnConfirm.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View view) {
        if (view == btnConfirm) {
            userEmail = etUserEmail.getText().toString().trim();
            if (TextUtils.isEmpty(userEmail)) {
                showShortToast("请输入您的邮箱地址");
                return;
            } else {
                Intent intent = new Intent();
                intent.putExtra(IntentKey.INTENT_KEY_USER_PHONE, userEmail);
                getActivity().setResult(Activity.RESULT_OK, intent);
                getActivity().finish();
            }
        }
    }

}

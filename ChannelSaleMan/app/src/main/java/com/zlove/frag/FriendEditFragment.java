package com.zlove.frag;

import org.apache.http.Header;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.zlove.act.ActFriendNameChange;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.util.DialogManager;
import com.zlove.base.util.DialogManager.DegreeSelectListener;
import com.zlove.base.util.DialogManager.GenderSelectListener;
import com.zlove.base.util.JsonUtil;
import com.zlove.bean.common.CommonBean;
import com.zlove.bean.friend.FriendInfoData;
import com.zlove.channelsaleman.R;
import com.zlove.constant.IntentKey;
import com.zlove.http.FriendHttpRequest;


public class FriendEditFragment extends BaseFragment implements OnClickListener, GenderSelectListener, DegreeSelectListener {
    
    private View changeNameView;
    private View changeGenderView;
    private View changeDegreeView;
    
    private TextView tvFriendName;
    private TextView tvFriendGender;
    private TextView tvFriendPhone;
    private TextView tvFriendDegree;
    
    private Button btnSave;
    
    private String friendId = "";
    private String name = "";
    private String gender = "";
    private String phone = "";
    private String degree = "";
    
    private FriendInfoData data = null;

    @Override
    protected int getInflateLayout() {
        return R.layout.frag_friend_detail_edit;
    }

    @Override
    protected void setUpView(View view) {
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            if (intent.hasExtra(IntentKey.INTENT_KEY_FRIEND_INFO_DATA)) {
                data = (FriendInfoData) intent.getSerializableExtra(IntentKey.INTENT_KEY_FRIEND_INFO_DATA);
            }
            if (intent.hasExtra(IntentKey.INTENT_KEY_FRIEND_ID)) {
                friendId = intent.getStringExtra(IntentKey.INTENT_KEY_FRIEND_ID);
            }
        }
        
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("好友设置");
        
        tvFriendName = (TextView) view.findViewById(R.id.tv_user_name);
        tvFriendGender = (TextView) view.findViewById(R.id.tv_user_gender);
        tvFriendPhone = (TextView) view.findViewById(R.id.tv_user_account);
        tvFriendDegree = (TextView) view.findViewById(R.id.tv_friend_degree);
        
        if (data != null) {
            name = data.getFriend_name();
            gender = data.getFriend_gender();
            phone = data.getFriend_phone();
            degree = data.getCategory_id();
        }
        
        tvFriendName.setText(name);
        if (gender.equals("1")) {
            tvFriendGender.setText("男");
        } else if (gender.equals("2")) {
            tvFriendGender.setText("女");
        } else {
            tvFriendGender.setText("");
        }
        tvFriendPhone.setText(phone);
        if (degree.equals("1")) {
            tvFriendDegree.setText("A");
        } else if (degree.equals("2")) {
            tvFriendDegree.setText("B");
        } else if (degree.equals("3")) {
            tvFriendDegree.setText("C");
        } else if (degree.equals("4")) {
            tvFriendDegree.setText("D");
        }
        
        changeNameView = view.findViewById(R.id.id_user_name);
        changeNameView.setOnClickListener(this);
        
        changeGenderView = view.findViewById(R.id.id_gender);
        changeGenderView.setOnClickListener(this);
        
        changeDegreeView = view.findViewById(R.id.id_modify_friend_degree);
        changeDegreeView.setOnClickListener(this);
        
        btnSave = (Button) view.findViewById(R.id.id_confirm);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == changeNameView) {
            Intent intent = new Intent(getActivity(), ActFriendNameChange.class);
            intent.putExtra(IntentKey.INTENT_KEY_FRIEND_NAME, name);
            startActivityForResult(intent, IntentKey.REQUEST_CODE_EDIT_FRIEND_NAME);
        } else if (v == changeGenderView) {
            DialogManager.showGenderDialog(getActivity(), this);
        } else if (v == changeDegreeView) {
            DialogManager.showDegreeDialog(getActivity(), this);
        } else if (v == btnSave) {
        	FriendHttpRequest.updateFriendInfoRequest(friendId, name, gender, degree, new UpdateFriendInfoHandler(this));
        }
    }
    
    class UpdateFriendInfoHandler extends HttpResponseHandlerFragment<FriendEditFragment> {

		public UpdateFriendInfoHandler(FriendEditFragment context) {
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
				CommonBean bean = JsonUtil.toObject(new String(content), CommonBean.class);
				if (bean != null) {
					if (bean.getStatus() == 200) {
						showShortToast("修改成功");
						Intent intent = new Intent();
						finishActivity(intent);
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
		}
    	
    }

    @Override
    public void selectGender(String gender) {
        this.gender = gender;
        if (gender.equals("1")) {
            tvFriendGender.setText("男");
        } else {
            tvFriendGender.setText("女");
        }
    }

    @Override
    public void selectDegree(String degree) {
        this.degree = degree;
        if (degree.equals("1")) {
            tvFriendDegree.setText("A");
        } else if (degree.equals("2")) {
            tvFriendDegree.setText("B");
        } else if (degree.equals("3")) {
            tvFriendDegree.setText("C");
        } else if (degree.equals("4")) {
            tvFriendDegree.setText("D");
        }
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	if (requestCode == IntentKey.REQUEST_CODE_EDIT_FRIEND_NAME) {
			if (data != null) {
				name = data.getStringExtra(IntentKey.INTENT_KEY_FRIEND_NAME);
				tvFriendName.setText(name);
			}
		}
    }

}

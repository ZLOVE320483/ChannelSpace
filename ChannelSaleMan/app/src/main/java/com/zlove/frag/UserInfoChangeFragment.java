package com.zlove.frag;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.Header;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.zlove.act.ActUserNameChange;
import com.zlove.act.ActUserPwdChange;
import com.zlove.base.BaseFragment;
import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.util.DialogManager;
import com.zlove.base.util.DialogManager.GenderSelectListener;
import com.zlove.base.util.DialogManager.PhotoSelectListener;
import com.zlove.base.util.GImageLoader;
import com.zlove.base.util.JsonUtil;
import com.zlove.bean.common.CommonBean;
import com.zlove.bean.user.UserAvatarBean;
import com.zlove.bean.user.UserAvatarData;
import com.zlove.channelsaleman.ImageLoaderDisplayAsCircleListener;
import com.zlove.channelsaleman.R;
import com.zlove.config.AppConfig;
import com.zlove.config.ChannelCookie;
import com.zlove.constant.IntentKey;
import com.zlove.http.UserHttpRequest;


public class UserInfoChangeFragment extends BaseFragment implements OnClickListener, PhotoSelectListener, GenderSelectListener {
    
    private View avatarView = null;
    private View userNameView = null;
    private View userGenderView = null;
    private View userPwdView = null;
    
    private ImageView ivAvatar = null;
    private TextView tvGender = null;
    private TextView tvUserName = null;
    private TextView tvUserPhone = null;
    
    private File camraPhoto;
    private String fileUpLoadPath;
    
    private String userGender = "";
    
    @Override
    protected int getInflateLayout() {
        return R.layout.frag_user_info;
    }

    @Override
    protected void setUpView(View view) {
        setBackButton(view.findViewById(R.id.id_back));
        ((TextView) view.findViewById(R.id.id_title)).setText("个人设置");
        
        avatarView = view.findViewById(R.id.id_avatar);
        userNameView = view.findViewById(R.id.id_user_name);
        userGenderView = view.findViewById(R.id.id_gender);
        userPwdView = view.findViewById(R.id.id_modify_pwd);
        
        ivAvatar = (ImageView) view.findViewById(R.id.iv_user_avatar);
        tvGender = (TextView) view.findViewById(R.id.tv_user_gender);
        tvUserName = (TextView) view.findViewById(R.id.tv_user_name);
        tvUserPhone = (TextView) view.findViewById(R.id.tv_user_account);
        
        tvUserName.setText(ChannelCookie.getInstance().getUserName());
        tvGender.setText(ChannelCookie.getInstance().getUserGender());
        tvUserPhone.setText(ChannelCookie.getInstance().getAccount());
        GImageLoader.getInstance().getImageLoader().displayImage(ChannelCookie.getInstance().getUserAvatar(), 
            ivAvatar, GImageLoader.getInstance().getAvatarOptions(), new ImageLoaderDisplayAsCircleListener());
        
        avatarView.setOnClickListener(this);
        userNameView.setOnClickListener(this);
        userGenderView.setOnClickListener(this);
        userPwdView.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
        if (v == avatarView) {
            DialogManager.showPhotoDialog(getActivity(), UserInfoChangeFragment.this);
        } else if (v == userNameView) {
            Intent intent = new Intent(getActivity(), ActUserNameChange.class);
            startActivityForResult(intent, IntentKey.REQUEST_CODE_CHANGE_USER_NAME);
        } else if (v == userGenderView) {
            DialogManager.showGenderDialog(getActivity(), UserInfoChangeFragment.this);
        } else if (v == userPwdView) {
            Intent intent = new Intent(getActivity(), ActUserPwdChange.class);
            startActivity(intent);
        }
    }
    
    @Override
    public void selectFromCamera() {
        camraPhoto = AppConfig.generateTmFile(AppConfig.IMG_PREFIX, AppConfig.IMG_EXTENSION);
        if (camraPhoto == null) {
            return;
        }
        takePhoto(camraPhoto);
    }

    @Override
    public void selectFromAlbum() {
        pickPhoto();
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IntentKey.REQUEST_CODE_PICK_PHOTO) {
                if (data != null) {
                    cropPhoto(data.getData());
                }
            } else if (requestCode == IntentKey.REQUEST_CODE_TAKE_PHOTO) {
                String sdState = Environment.getExternalStorageState();
                if (!sdState.equals(Environment.MEDIA_MOUNTED)) {
                    showShortToast("手机没空间了");
                    return;
                }
                if (camraPhoto != null) {
                    if (camraPhoto.exists()) {
                        cropPhoto(Uri.fromFile(camraPhoto));
                    }
                } else {
                    showShortToast("遇到点麻烦,请重试");
                }
            } else if (requestCode == IntentKey.REQUEST_CODE_CROP_PHOTO) {
                if (data != null) {
                    try {
                        fileUpLoadPath = setPicToView(data);
                        UserHttpRequest.userModifyAvatarRequest(fileUpLoadPath, new ChangeAvatarHandler(this));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (requestCode == IntentKey.REQUEST_CODE_CHANGE_USER_NAME) {
                tvUserName.setText(ChannelCookie.getInstance().getUserName());
            }
        }
    }
    
    public void takePhoto(File outFile) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outFile));
        startActivityForResult(intent, IntentKey.REQUEST_CODE_TAKE_PHOTO);
    }

    public void pickPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, IntentKey.REQUEST_CODE_PICK_PHOTO);
    }
    
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, IntentKey.REQUEST_CODE_CROP_PHOTO);
    }
    
    public String setPicToView(Intent picdata) throws Exception {
        Bundle extras = picdata.getExtras();
        if (extras == null) {
            return "";
        }
        Bitmap bitmap = (Bitmap) extras.get("data");
        FileOutputStream out = null;
        String filename = AppConfig.getExternalCacheDir() + "/independent_avatar.png";
        File pic = new File(filename);
        if (!pic.exists()) {
            try {
                pic.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            out = new FileOutputStream(pic);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filename;
    }

    @Override
    public void selectGender(String gender) {
        this.userGender = gender;
        UserHttpRequest.userModifyGenderRequest(userGender, new ChangeUserGenderHandler(this));
    }
    
    class ChangeUserGenderHandler extends HttpResponseHandlerFragment<UserInfoChangeFragment> {

        public ChangeUserGenderHandler(UserInfoChangeFragment context) {
            super(context);
        }
        
        @Override
        public void onStart() {
            super.onStart();
        }
        
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] content) {
            super.onSuccess(statusCode, headers, content);
            if (!isAdded()) {
                return;
            }
            if (content != null) {
                CommonBean bean = JsonUtil.toObject(new String(content), CommonBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        if (userGender.equals("1")) {
                            ChannelCookie.getInstance().saveUserGender("男");
                        } else if (userGender.equals("2")) {
                            ChannelCookie.getInstance().saveUserGender("女");
                        }
                        tvGender.setText(ChannelCookie.getInstance().getUserGender());
                    } else {
                        showShortToast(bean.getMessage());
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
    
    class ChangeAvatarHandler extends HttpResponseHandlerFragment<UserInfoChangeFragment> {

        public ChangeAvatarHandler(UserInfoChangeFragment context) {
            super(context);
        }
        
        @Override
        public void onStart() {
            super.onStart();
        }
        
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] content) {
            super.onSuccess(statusCode, headers, content);
            if (!isAdded()) {
                return;
            }
            if (content != null) {
                UserAvatarBean bean = JsonUtil.toObject(new String(content), UserAvatarBean.class);
                if (bean != null) {
                    if (bean.getStatus() == 200) {
                        UserAvatarData data = bean.getData();
                        if (data != null) {
                            ChannelCookie.getInstance().saveUserAvatar(data.getUrl());
                            GImageLoader.getInstance().getImageLoader().displayImage(ChannelCookie.getInstance().getUserAvatar(), 
                                ivAvatar, GImageLoader.getInstance().getAvatarOptions(), new ImageLoaderDisplayAsCircleListener());
                        }
                    } else {
                        showShortToast(bean.getMessage());
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

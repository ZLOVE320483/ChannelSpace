package com.zlove.base;

import org.apache.http.Header;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zlove.base.http.HttpResponseHandlerFragment;
import com.zlove.base.util.ApplicationUtil;
import com.zlove.base.util.UIUtil;
import com.zlove.channelsaleman.R;

public abstract class BaseFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = configureLayoutInflater(inflater).inflate(getInflateLayout(), container, false);
        setUpView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    
    /**
     * ���ڸı� inflater for example, use {@link ContextThemeWrapper} to load special style.
     * 
     * @param inflater
     * @return
     */
    protected LayoutInflater configureLayoutInflater(LayoutInflater inflater) {
        return inflater;
    }

    /**
     * @return R.layout.*** ����ҳ��Ĳ��ֶ���
     */
    protected abstract int getInflateLayout();

    /**
     * ���ڻ�ȡ�����ϵĿؼ� for example, view.findViewById(R.id.***);
     * 
     * @param view ����ҳ��ĸ�View
     */
    protected abstract void setUpView(View view);

    /**
     * ��ʾToast
     * 
     * @param message
     */
    public void showShortToast(String message) {
        Toast.makeText(ApplicationUtil.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * ��ʾToast
     * 
     * @param message
     */
    public void showShortToast(int message) {
        Toast.makeText(ApplicationUtil.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * ����Ĭ�ϵĵ����Ӧ��getActivity().finish();
     * 
     * @param view, back button view
     */
    protected void setBackButton(View view) {
        if (view != null) {
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    UIUtil.hideKeyboard(getActivity());
                    getActivity().finish();
                }
            });
        }
    }
    
    /**
     * ���ٵ�ǰ��Activity
     */
    public void finishActivity() {
        finishActivity(null);
    }

    /**
     * ���ٵ�ǰ��Activity
     * 
     * @param result, ���صĽ��
     */
    protected void finishActivity(Intent result) {
        UIUtil.hideKeyboard(getActivity());
        try {
            if (getActivity() != null) {
                if (result != null) {
                    getActivity().setResult(Activity.RESULT_OK, result);
                }
                getActivity().finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private AlertDialog alertDialog;

    public AlertDialog getTipDialog(int message) {
        return getTipDialog(getString(R.string.tips), getString(message));
    }
    
    public AlertDialog getTipDialog(String message) {
        return getTipDialog(getString(R.string.tips), message);
    }
    
    public AlertDialog getTipDialog(String title, String message) {
        dismissAlertDialog();
        alertDialog = new AlertDialog.Builder(getActivity()).setTitle(title).setMessage(message).setPositiveButton(R.string.positive,
            new OnClickListener() {
                
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alertDialog.dismiss();                    
                }
            }).create();
        return alertDialog;
    }

    protected void dismissAlertDialog() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }
    
    protected class CommonHandler extends HttpResponseHandlerFragment<BaseFragment> {

        public CommonHandler(BaseFragment context) {
            super(context);
        }
        
        @Override
        public void onStart() {
            super.onStart();
        }
        
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] content) {
            super.onSuccess(statusCode, headers, content);
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
    

}

package com.zlove.base.widget;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zlove.bean.project.HouseListItem;
import com.zlove.channelsaleman.R;


public class HouseSelectDialog extends Dialog implements OnItemClickListener {

    private TextView tvTitle;
    private ListView listView;
    
    private String title;
    private List<HouseListItem> items;
    private HouseSelectListener listener;
    private List<String> houseNames = new ArrayList<String>();
    
    public HouseSelectDialog(Context context) {
        super(context, R.style.MessageBox);
    }
    
    public HouseSelectDialog(Context context, String title, List<HouseListItem> items, HouseSelectListener listener) {
        this(context);
        this.title = title;
        this.items = items;
        this.listener = listener;
    }
    
    public void showdialog() {
        // 设置窗口弹出动画
        getWindow().setWindowAnimations(R.style.centerDialogWindowAnim);
        // setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams wl = getWindow().getAttributes();
        wl.gravity = Gravity.CENTER;
        getWindow().setAttributes(wl);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        show();
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_view_house_select);
        initView();
    }
    
    private void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_dialog_title);
        tvTitle.setText(title);
        listView = (ListView) findViewById(R.id.id_listview);
        listView.setOnItemClickListener(this);
        
        for (HouseListItem item : items) {
            houseNames.add(item.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, houseNames);
        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        if (listener != null) {
            listener.selectHouse(items.get(arg2).getHouse_id(), items.get(arg2).getName());
        }
        dismiss();
    }
    
    public interface HouseSelectListener {
        void selectHouse(String houseId, String houseName);
    }
}

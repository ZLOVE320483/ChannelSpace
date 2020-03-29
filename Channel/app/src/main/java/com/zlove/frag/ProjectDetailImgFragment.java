package com.zlove.frag;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.zlove.act.independent.ActProjectImgDetail;
import com.zlove.base.BaseFragment;
import com.zlove.channel.R;


public class ProjectDetailImgFragment extends BaseFragment implements OnClickListener {
    
    private ImageView ivDetail;
    
    @Override
    protected int getInflateLayout() {
        return R.layout.viewpager_item_project_detail_img;
    }

    @Override
    protected void setUpView(View view) {
        ivDetail = (ImageView) view.findViewById(R.id.id_project_detail_img);
        ivDetail.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == ivDetail) {
            Intent intent = new Intent(getActivity(), ActProjectImgDetail.class);
            startActivity(intent);
        }
    }

}

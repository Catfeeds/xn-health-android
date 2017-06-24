package com.chengdai.ehealthproject.model.healthmanager.adapters;

import android.content.Context;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.model.healthmanager.acitivitys.HealthDoTestQuesitionActivity;
import com.chengdai.ehealthproject.model.healthmanager.acitivitys.TestPageDetailsActivity;
import com.chengdai.ehealthproject.model.healthmanager.model.TestPageListModel;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by 李先俊 on 2017/6/24.
 */

public class TestPageAdapter extends CommonAdapter<TestPageListModel.ListBean> {

    public TestPageAdapter(Context context, List<TestPageListModel.ListBean> datas) {
        super(context, R.layout.item_health_test_list, datas);
    }

    @Override
    protected void convert(ViewHolder holder, TestPageListModel.ListBean testPageListModel, int position) {

        if(testPageListModel == null){
            return;
        }

        holder.setText(R.id.tv_title,testPageListModel.getTitle());
        holder.setText(R.id.tv_content,testPageListModel.getSummary());
        ImgUtils.loadImgURL(mContext, MyConfig.IMGURL+testPageListModel.getAdvPic(),holder.getView(R.id.img_title));

        holder.setOnClickListener(R.id.tv_start_test,v ->{

            if(!SPUtilHelpr.isLogin(mContext)){
                return;
            }

            HealthDoTestQuesitionActivity.open(mContext,testPageListModel.getCode(),testPageListModel.getTitle());
        });
        holder.setOnClickListener(R.id.tv_look_deatil,v -> TestPageDetailsActivity.open(mContext,testPageListModel));


    }
}

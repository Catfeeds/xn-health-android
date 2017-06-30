package com.chengdai.ehealthproject.model.healthmanager.adapters;


import android.content.Context;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.model.healthmanager.model.HealthInfoListModel;
import com.chengdai.ehealthproject.model.healthmanager.model.SexHealthInfoListModel;
import com.chengdai.ehealthproject.uitls.DateUtil;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by 李先俊 on 2017/6/23.
 */

public class SexHealthInfoListAdapter extends CommonAdapter<SexHealthInfoListModel.ListBean> {

    public SexHealthInfoListAdapter(Context context, List<SexHealthInfoListModel.ListBean> datas) {
        super(context, R.layout.item_health_info, datas);
    }



    public void setData(List<SexHealthInfoListModel.ListBean> datas){
        if(datas!=null){
            this.mDatas=datas;
            notifyDataSetChanged();
        }
    }

    public void addData(List<SexHealthInfoListModel.ListBean> datas){
        if(datas!=null){
            this.mDatas.addAll(datas);
            notifyDataSetChanged();
        }
    }


    @Override
    protected void convert(ViewHolder viewHolder, SexHealthInfoListModel.ListBean item, int position) {
        if(item == null){
            return;
        }

        ImgUtils.loadImgURL(mContext, MyConfig.IMGURL+item.getSplitAdvPic(),viewHolder.getView(R.id.img_title));

        viewHolder.setText(R.id.tv_title,item.getTitle());
        viewHolder.setText(R.id.tv_content,item.getContent());
        viewHolder.setText(R.id.tv_time, DateUtil.formatStringData(item.getUpdateDatetime(),DateUtil.DATE_YMD));
    }
}

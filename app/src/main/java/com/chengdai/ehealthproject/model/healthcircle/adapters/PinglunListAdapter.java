package com.chengdai.ehealthproject.model.healthcircle.adapters;

import android.content.Context;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.model.healthcircle.models.PinglunListModel;
import com.chengdai.ehealthproject.uitls.DateUtil;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.adapter.recyclerview.CommonAdapter;

import java.util.List;

/**
 * Created by 李先俊 on 2017/6/21.
 */

public class PinglunListAdapter extends CommonAdapter<PinglunListModel.ListBean> {

    public PinglunListAdapter(Context context, List<PinglunListModel.ListBean> datas) {
        super(context,R.layout.item_pinlun_activity, datas);
    }

    @Override
    protected void convert(com.zhy.adapter.recyclerview.base.ViewHolder holder, PinglunListModel.ListBean listBean, int position) {
        if(listBean == null) return;

        ImgUtils.loadImgLogo(mContext, MyConfig.IMGURL+listBean.getPhoto(),holder.getView(R.id.img_user_logo));
        holder.setText(R.id.tv_content,listBean.getContent());
        holder.setText(R.id.tv_time, DateUtil.formatStringData(listBean.getCommDatetime(),DateUtil.DEFAULT_DATE_FMT));
        holder.setText(R.id.tv_name,listBean.getNickname());

    }

}

package com.chengdai.ehealthproject.model.tabmy.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.model.tabmy.model.OrderRecordModel;
import com.chengdai.ehealthproject.model.tabsurrounding.model.HotelListModel;
import com.chengdai.ehealthproject.uitls.DateUtil;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.Date;
import java.util.List;

/**
 * Created by 李先俊 on 2017/6/15.
 */

public class OrderRecordAdapter extends CommonAdapter<OrderRecordModel.ListBean> {

    public OrderRecordAdapter(Context context, List<OrderRecordModel.ListBean> datas) {
        super(context, R.layout.item_order_list, datas);
    }



    public void setData(List<OrderRecordModel.ListBean> datas){
        if(datas!=null){
            this.mDatas=datas;
            notifyDataSetChanged();
        }
    }

    public void addData(List<OrderRecordModel.ListBean> datas){
        if(datas!=null){
            this.mDatas.addAll(datas);
            notifyDataSetChanged();
        }
    }


    @Override
    protected void convert(ViewHolder viewHolder, OrderRecordModel.ListBean item, int position) {
        if(item ==null ){
            return;
        }

        TextView tvCode=viewHolder.getView(R.id.tv_code);
        TextView tvDate=viewHolder.getView(R.id.tv_date);
        TextView tvName=viewHolder.getView(R.id.tv_name);
        TextView tvPrice=viewHolder.getView(R.id.tv_price);
        ImageView imgTitle=viewHolder.getView(R.id.img_title);


        if(!TextUtils.isEmpty(item.getCreateDatetime())){
            tvDate.setText(DateUtil.format( new Date(item.getCreateDatetime()), DateUtil.DATE_YMD));
        }

        tvPrice.setText(mContext.getString(R.string.price_sing)+ StringUtils.showPrice(item.getPrice()));


        tvCode.setText(item.getCode());

        if(item.getStore() !=null ){
            tvName.setText(item.getStore().getName());
            ImgUtils.loadImgURL(mContext, MyConfig.IMGURL+item.getStore().getSplitAdvPic(),imgTitle);
        }


    }
}

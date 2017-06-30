package com.chengdai.ehealthproject.model.tabmy.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.model.tabmy.model.HotelOrderRecordModel;
import com.chengdai.ehealthproject.model.tabmy.model.OrderRecordModel;
import com.chengdai.ehealthproject.uitls.DateUtil;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.Date;
import java.util.List;

/**
 * Created by 李先俊 on 2017/6/15.
 */

public class HotelOrderRecordAdapter extends CommonAdapter<HotelOrderRecordModel.ListBean> {




    public void setData(List<HotelOrderRecordModel.ListBean> datas){
        if(datas!=null){
            this.mDatas=datas;
            notifyDataSetChanged();
        }
    }

    public void addData(List<HotelOrderRecordModel.ListBean> datas){
        if(datas!=null){
            this.mDatas.addAll(datas);
            notifyDataSetChanged();
        }
    }



    public HotelOrderRecordAdapter(Context context, List<HotelOrderRecordModel.ListBean> datas) {
        super(context, R.layout.item_hotelorder_list, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, HotelOrderRecordModel.ListBean item, int position) {
        if(item ==null ){
            return;
        }

        TextView tvCode=viewHolder.getView(R.id.tv_code);
        TextView tvDate=viewHolder.getView(R.id.tv_date);
        TextView tv_hotel_size=viewHolder.getView(R.id.tv_hotel_size);
        TextView tv_hotel_data=viewHolder.getView(R.id.tv_hotel_data);
        TextView tv_hotel_info=viewHolder.getView(R.id.tv_hotel_info);
        TextView tv_sure_pay=viewHolder.getView(R.id.tv_sure_pay);
        ImageView img_hotel_info=viewHolder.getView(R.id.img_hotel_info);

        tvCode.setText(item.getCode());

        if(!TextUtils.isEmpty(item.getPayDatetime())){
            tvDate.setText(DateUtil.format( new Date(item.getPayDatetime()), DateUtil.DATE_YMD));
        }


        if("0".equals(item.getStatus())){
            tv_sure_pay.setVisibility(View.VISIBLE);
        }else{
            tv_sure_pay.setVisibility(View.GONE);
        }

        if(item.getProduct() !=null){
            tv_hotel_size.setText(item.getProduct().getName());
            tv_hotel_info.setText(item.getProduct().getSlogan());
            ImgUtils.loadImgURL(mContext, MyConfig.IMGURL+item.getProduct().getSplitAdvPic(),img_hotel_info);

            if(!TextUtils.isEmpty(item.getStartDate()) && !TextUtils.isEmpty(item.getEndDate())){
                tv_hotel_data.setText(
                        "入住:"+DateUtil.format( new Date(item.getStartDate()),"MM月dd日")
                        +"离开:"+DateUtil.format( new Date(item.getEndDate()),"MM月dd日")
                        +"  "+(DateUtil.getDatesBetweenTwoDate(new Date(item.getStartDate()),new Date(item.getEndDate())).size()-1)+"晚"
                );
            }
        }


    }
}

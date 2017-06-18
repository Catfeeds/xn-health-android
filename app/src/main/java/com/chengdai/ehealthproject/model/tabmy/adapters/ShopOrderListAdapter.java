package com.chengdai.ehealthproject.model.tabmy.adapters;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.model.healthstore.acitivtys.ShopPayConfirmActivity;
import com.chengdai.ehealthproject.model.healthstore.models.ShopListModel;
import com.chengdai.ehealthproject.model.healthstore.models.ShopOrderDetailBean;
import com.chengdai.ehealthproject.uitls.DateUtil;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.Date;
import java.util.List;

/**
 * Created by 李先俊 on 2017/6/18.
 */

public class ShopOrderListAdapter extends CommonAdapter<ShopOrderDetailBean> {



    public void setData(List<ShopOrderDetailBean> datas){
        if(datas!=null){
            this.mDatas=datas;
            notifyDataSetChanged();
        }
    }

    public void addData(List<ShopOrderDetailBean> datas){
        if(datas!=null){
            this.mDatas.addAll(datas);
            notifyDataSetChanged();
        }
    }
    public ShopOrderListAdapter(Context context, List datas) {
        super(context, R.layout.item_shop_order, datas);
    }


    @Override
    protected void convert(ViewHolder viewHolder, ShopOrderDetailBean item, int position) {

        if(item== null){
            return;
        }

        TextView txtOrderId=viewHolder.getView(R.id.txt_orderId);
        TextView txtTime=viewHolder.getView(R.id.txt_time);
        TextView txtBtn=viewHolder.getView(R.id.txt_btn);
        TextView txtPrice=viewHolder.getView(R.id.txt_price);
        ImageView imgGood=viewHolder.getView(R.id.img_good);
        TextView tvNumber=viewHolder.getView(R.id.txt_number);
        TextView tvName=viewHolder.getView(R.id.txt_name);


        txtOrderId.setText(item.getCode());

        txtTime.setText(DateUtil.format(new Date(item.getApplyDatetime()),DateUtil.DATE_YMD));

        txtBtn.setText(StringUtils.getOrderState(item.getStatus()));



        if(item.getProductOrderList() !=null && item.getProductOrderList().size()>0 && item.getProductOrderList().get(0) !=null
                && item.getProductOrderList().get(0).getProduct()!=null ){
            ImgUtils.loadImgURL(mContext, MyConfig.IMGURL+item.getProductOrderList().get(0).getProduct().getAdvPic(),imgGood);

            txtPrice.setText(StringUtils.getShowPriceSign(item.getProductOrderList().get(0).getPrice1()));

            tvNumber.setText("X" + item.getProductOrderList().get(0).getQuantity());

            tvName.setText(item.getProductOrderList().get(0).getProduct().getName());

        }

        txtBtn.setOnClickListener(v -> {

            if (StringUtils.canDoPay(item.getStatus())){
                ShopListModel.ListBean.ProductSpecsListBean data=new ShopListModel.ListBean.ProductSpecsListBean();

                if(item.getProductOrderList() !=null && item.getProductOrderList().size()>0 && item.getProductOrderList().get(0) !=null) {
                    data.setPrice1(item.getProductOrderList().get(0).getPrice1());
                    data.setmBuyNum(item.getProductOrderList().get(0).getQuantity());

                    ShopPayConfirmActivity.open(mContext,data,item.getCode(),false);
                }

            }
        });


    }




}

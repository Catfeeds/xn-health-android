package com.chengdai.ehealthproject.model.healthstore.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.model.healthstore.models.getOrderAddressModel;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerListHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 李先俊 on 2017/6/17.
 */

public class AddressAdapter extends CommonAdapter<getOrderAddressModel> {

    public AddressAdapter(Context context, List<getOrderAddressModel> datas) {
        super(context, R.layout.item_address, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, getOrderAddressModel item, int position) {

        if(item == null){
            return;
        }

        TextView txtName=viewHolder.getView(R.id.txt_name);
        TextView txtPhone=viewHolder.getView(R.id.txt_phone);
        TextView txtAddress=viewHolder.getView(R.id.txt_address);
        ImageView imgChoose=viewHolder.getView(R.id.img_choose);
        LinearLayout layoutEdit=viewHolder.getView(R.id.layout_edit);
        LinearLayout layoutDelete=viewHolder.getView(R.id.layout_delete);



        if ("1".equals(item.getIsDefault())) {
           imgChoose.setBackgroundResource(R.mipmap.address_choose);
        } else {
           imgChoose.setBackgroundResource(R.mipmap.address_unchoose);
        }

       txtName.setText(item.getAddressee());
       txtPhone.setText(item.getMobile());
       txtAddress.setText(item.getProvince() + " " + item.getCity() + " " + item.getDistrict() + "" + item.getDetailAddress());


        imgChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("1".equals(item.getIsDefault())) {
                    setDefaultAddressRequest(item.getCode());
                }
            }
        });

        layoutEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        layoutDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                tip(i);
            }
        });


    }


    /**
     * 设置默认地址请求
     */
    public void setDefaultAddressRequest(String code) {

        Map<String ,String > map=new HashMap<>();

        map.put("code", code);
        map.put("token", SPUtilHelpr.getUserToken());
        map.put("userId", SPUtilHelpr.getUserId());
        map.put("systemCode", MyConfig.SYSTEMCODE);

        RetrofitUtils.getLoaderServer().SetDefultAddress("805163", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(mContext))
                .filter(isSuccessModes -> isSuccessModes != null && isSuccessModes.isSuccess()) //设置默认地址成功时
                .map(isSuccessModes -> {
                    Map<String,String> map2=new HashMap<>();
                    map2.put("userId",SPUtilHelpr.getUserId());
                    map2.put("token", SPUtilHelpr.getUserToken());
                    return map2;
                })
                .flatMap(requestData ->  RetrofitUtils.getLoaderServer().GetAddress("805165", StringUtils.getJsonToString(requestData)))
                .compose(RxTransformerListHelper.applySchedulerResult(null))
                .subscribe(datas -> {

                    mDatas=datas;

                    notifyDataSetChanged();

                },Throwable::printStackTrace);
    }



}

package com.chengdai.ehealthproject.model.tabmy.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityHotelOrderDetailsBinding;
import com.chengdai.ehealthproject.model.tabmy.model.OrderRecordModel;
import com.chengdai.ehealthproject.model.tabsurrounding.activitys.HoteldetailsActivity;
import com.chengdai.ehealthproject.uitls.DateUtil;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;

import java.util.Date;

/**订单详情查看
 * Created by 李先俊 on 2017/6/15.
 */

public class OrderDetailsActivity extends AbsBaseActivity{

    private ActivityHotelOrderDetailsBinding mBinding;

    private OrderRecordModel.ListBean mShowData;



    /**
     * 打开当前页面
     * @param context
     * @param data 要显示的数据
     */
    public static void open(Context context, OrderRecordModel.ListBean data){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,OrderDetailsActivity.class);

        intent.putExtra("data",data);

        context.startActivity(intent);
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_hotel_order_details, null, false);

        if(getIntent()!=null){

            mShowData= getIntent().getParcelableExtra("data");

        }

        setTopTitle("订单详情");

        setSubLeftImgState(true);

        setShowData();

        addMainView(mBinding.getRoot());

    }

    private void setShowData() {
        if(mShowData==null){
            return;
        }

        mBinding.tvCode.setText(mShowData.getCode());
        mBinding.tvOrderState.setText("已支付");
        mBinding.tvPrice.setText(getString(R.string.price_sing)+ StringUtils.showPrice(mShowData.getPrice()));


        if(mShowData.getStore()!=null){
            mBinding.tvName.setText(mShowData.getStore().getName());
            ImgUtils.loadImgURL(this, MyConfig.IMGURL+mShowData.getStore().getSplitAdvPic(),   mBinding.imgTitle);
        }

        if(!TextUtils.isEmpty(mShowData.getCreateDatetime())){
            mBinding.tvPayDate.setText(DateUtil.format( new Date(mShowData.getCreateDatetime()), DateUtil.DATE_YYMMddHHmm));
        }


    }
}

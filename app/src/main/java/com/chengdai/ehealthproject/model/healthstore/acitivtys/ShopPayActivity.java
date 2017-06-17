package com.chengdai.ehealthproject.model.healthstore.acitivtys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityShopPayBinding;
import com.chengdai.ehealthproject.model.common.model.activitys.AddAddressActivity;
import com.chengdai.ehealthproject.model.healthstore.models.getOrderAddressModel;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerListHelper;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**商城支付
 * Created by 李先俊 on 2017/6/17.
 */

public class ShopPayActivity extends AbsBaseActivity{

    private ActivityShopPayBinding mBinding;


    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,ShopPayActivity.class);

        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_shop_pay, null, false);

        addMainView(mBinding.getRoot());

        setSubLeftImgState(true);

        setTopTitle(getString(R.string.txt_pay));

        getAddressRequst();

        mBinding.layoutAdd.setOnClickListener(v -> {
            AddAddressActivity.open(this);
        });

    }


    /**
     * 请求收获地址
     */
   private void getAddressRequst(){

       Map<String,String> map=new HashMap<>();

       map.put("userId",SPUtilHelpr.getUserId());
       map.put("isDefault", "1");
       map.put("token", SPUtilHelpr.getUserToken());

       mSubscription.add(RetrofitUtils.getLoaderServer().GetAddress("805165", StringUtils.getJsonToString(map))
               .compose(RxTransformerListHelper.applySchedulerResult(this))
               .subscribe(data ->{
                   setAddressState(data);

               },Throwable::printStackTrace));

   }

    /**
     * 设置地址显示状态 有地址是显示默认地址  无地址时显示添加按钮
     */
    private void setAddressState(List<getOrderAddressModel> addressList) {
        if (addressList == null || addressList.size() == 0) {
            mBinding.layoutAddress.setVisibility(View.GONE);
            mBinding. layoutNoAddress.setVisibility(View.VISIBLE);
        } else {
            mBinding.layoutAddress.setVisibility(View.VISIBLE);
            mBinding.layoutNoAddress.setVisibility(View.GONE);

            mBinding. txtConsignee.setText(addressList.get(0).getAddressee());
            mBinding. txtPhone.setText(addressList.get(0).getMobile());
            mBinding. txtAddress.setText("收货地址：" + addressList.get(0).getProvince() + " " + addressList.get(0).getCity() + " " + addressList.get(0).getDistrict() + "" + addressList.get(0).getDetailAddress());
        }
    }


}

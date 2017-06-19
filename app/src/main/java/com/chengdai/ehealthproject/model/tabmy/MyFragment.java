package com.chengdai.ehealthproject.model.tabmy;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.BaseLazyFragment;
import com.chengdai.ehealthproject.databinding.ActivityShopAllOrderLookBinding;
import com.chengdai.ehealthproject.databinding.FragmentMyBinding;
import com.chengdai.ehealthproject.model.tabmy.activitys.ShopAllOrderLookActivity;
import com.chengdai.ehealthproject.model.tabmy.activitys.ShopOrderStateLookActivity;
import com.chengdai.ehealthproject.model.tabmy.activitys.HotelOrderStateLookActivity;
import com.chengdai.ehealthproject.model.tabmy.activitys.SettingActivity;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerListHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;

import java.util.HashMap;
import java.util.Map;

/**我的
 * Created by 李先俊 on 2017/6/8.
 */

public class MyFragment extends BaseLazyFragment{

    private FragmentMyBinding mBinding;

    private boolean isCreate;


    /**
     * 获得fragment实例
     * @return
     */
    public static MyFragment getInstanse(){
        MyFragment fragment=new MyFragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding= DataBindingUtil.inflate(getLayoutInflater(savedInstanceState), R.layout.fragment_my, null, false);


        mBinding.tvSurroundingService.setOnClickListener(v -> {

            HotelOrderStateLookActivity.open(mActivity);
        });

        mBinding.linShop.setOnClickListener(v -> {

            ShopAllOrderLookActivity.open(mActivity);


        });

        mBinding.linSetting.setOnClickListener(v -> {
            SettingActivity.open(mActivity);
        });


        isCreate=true;
        return mBinding.getRoot();
    }

    @Override
    protected void lazyLoad() {
        if (isCreate){
            getAmountRequest();
            getJifenRequest();
            getUserInfoRequest();
        }

    }

    @Override
    protected void onInvisible() {

    }

    /**
     * 获取余额请求
     */
    private  void getAmountRequest(){

        Map<String,String> map=new HashMap<>();

        map.put("userId", SPUtilHelpr.getUserId());
        map.put("currency","CNY");
        map.put("token",SPUtilHelpr.getUserToken());

       mSubscription.add( RetrofitUtils.getLoaderServer().getAmount("802503", StringUtils.getJsonToString(map))
                .compose(RxTransformerListHelper.applySchedulerResult(null))
                .filter(r -> r !=null && r.size() >0  && r.get(0)!=null)
                .subscribe(r -> {
                    mBinding.tvYe.setText(getString(R.string.price_sing)+StringUtils.showPrice(r.get(0).getAmount()));

                },Throwable::printStackTrace));


    }

    /**
     * 获取积分请求
     */
    private  void getJifenRequest(){

        Map<String,String> map=new HashMap<>();

        map.put("userId", SPUtilHelpr.getUserId());
        map.put("currency","JF");
        map.put("token",SPUtilHelpr.getUserToken());

       mSubscription.add( RetrofitUtils.getLoaderServer().getAmount("802503", StringUtils.getJsonToString(map))
                .compose(RxTransformerListHelper.applySchedulerResult(mActivity))
                .filter(r -> r !=null && r.size() >0  && r.get(0)!=null)
                .subscribe(r -> {
                    if(r.get(0).getAmount().intValue()>0){
                        mBinding.tvJf.setText(r.get(0).getAmount().intValue());
                    }else{
                        mBinding.tvJf.setText("0");
                    }

                },Throwable::printStackTrace));


    }

    /**
     * 获取积分请求
     */
    private  void getUserInfoRequest(){

        Map<String,String> map=new HashMap<>();

        map.put("userId", SPUtilHelpr.getUserId());
        map.put("token",SPUtilHelpr.getUserToken());

       mSubscription.add( RetrofitUtils.getLoaderServer().GetUserInfo("805056", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(mActivity))

               .filter(r -> r!=null)

                .subscribe(r -> {
                    mBinding.tvUserName.setText(r.getLoginName());

                    if(r.getUserExt() == null) return;

                    ImgUtils.loadImgURL(mActivity, MyConfig.IMGURL+r.getUserExt().getPhoto(),mBinding.imtUserLogo);
                    if("0".equals(r.getUserExt().getGender())){
                        ImgUtils.loadImgId(mActivity,R.mipmap.man,mBinding.imgUserSex);
                    }else if ("1".equals(r.getUserExt().getGender())){
                        ImgUtils.loadImgId(mActivity,R.mipmap.woman,mBinding.imgUserSex);
                    }

                },Throwable::printStackTrace));


    }

}

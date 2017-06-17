package com.chengdai.ehealthproject.model.healthstore.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.BaseFragment;
import com.chengdai.ehealthproject.databinding.FragmentShopCommodityBinding;
import com.chengdai.ehealthproject.model.common.model.activitys.WebViewActivity;
import com.chengdai.ehealthproject.model.healthstore.models.ShopListModel;
import com.chengdai.ehealthproject.model.tabmy.fragments.HotelOrderRecordFragment;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.weigit.GlideImageLoader;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李先俊 on 2017/6/17.
 */

public class ShopCommodityFragment extends BaseFragment {

    private FragmentShopCommodityBinding mBinding;

    private ShopListModel.ListBean mData;

    /**
     * 获得fragment实例
     * @return
     */
    public static ShopCommodityFragment getInstanse(ShopListModel.ListBean data){
        ShopCommodityFragment fragment=new ShopCommodityFragment();
        Bundle bundle=new Bundle();
        bundle.putParcelable("data",data);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding= DataBindingUtil.inflate(getLayoutInflater(savedInstanceState), R.layout.fragment_shop_commodity, null, false);

        if(getArguments() != null){
            mData=getArguments().getParcelable("data");
        }

        if(mData != null){

            initBanner();

            initDataShow();
        }


        return mBinding.getRoot();
    }


    /**
     * 初始化显示数据
     */
    private void initDataShow() {

        mBinding.txtName.setText(mData.getName());

        mBinding.txtInfo.setText(mData.getSlogan());

        if(mData.getProductSpecsList()!=null && mData.getProductSpecsList().size()>0){
            mBinding.txtPrice.setText(getString(R.string.price_sing)+StringUtils.showPrice(mData.getProductSpecsList().get(0).getPrice1()));
        }

    }

    /**
     * 初始化广告图片
     */
    private void initBanner() {

        List<String>   banaers= StringUtils.splitBannerList(mData.getPic());

        //设置图片集合
        mBinding.banner.setImages(banaers);
        mBinding.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBinding.banner.setIndicatorGravity(BannerConfig.CENTER);

        mBinding.banner.setImageLoader(new GlideImageLoader());
        //banner设置方法全部调用完毕时最后调用
        mBinding.banner.start();
        mBinding.banner.startAutoPlay();

    }

}

package com.chengdai.ehealthproject.model.healthstore.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.BaseFragment;
import com.chengdai.ehealthproject.databinding.FragmentShopTabDetailsBinding;
import com.chengdai.ehealthproject.model.healthstore.models.ShopListModel;
import com.zzhoujay.richtext.RichText;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 李先俊 on 2017/6/17.
 */

public class ShopEvaluateFragment extends BaseFragment {

    private FragmentShopTabDetailsBinding mBinding;

    private ShopListModel.ListBean mData;

    /**
     * 获得fragment实例
     * @return
     */
    public static ShopEvaluateFragment getInstanse(ShopListModel.ListBean data){
        ShopEvaluateFragment fragment=new ShopEvaluateFragment();
        Bundle bundle=new Bundle();
        bundle.putParcelable("data",data);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding= DataBindingUtil.inflate(getLayoutInflater(savedInstanceState), R.layout.fragment_shop_tab_details, null, false);

        if(getArguments() != null){
            mData=getArguments().getParcelable("data");
        }

        if(mData != null){
            RichText.from(mData.getDescription()).into(mBinding.tvDetails);
        }


        return mBinding.getRoot();
    }


    private  void getEvaluateRequest(){

        Map<String,String> object=new HashMap<>();
        object.put("productCode",mData.getCode());
        object.put("start", "1");
        object.put("limit", "10");
        object.put("type", "3");


    }



}

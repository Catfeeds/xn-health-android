package com.chengdai.ehealthproject.model.healthmanager.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.BaseFragment;
import com.chengdai.ehealthproject.databinding.LayoutImgBinding;
import com.chengdai.ehealthproject.model.healthmanager.acitivitys.SexMenuActivity;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.liaoinstan.springview.container.BaseFooter;

/**
 * Created by 李先俊 on 2017/6/23.
 */

public class BeautySexFragment extends BaseFragment {

    private LayoutImgBinding mBinding;

    private  int mSexType;

    /**
     * 获得fragment实例
     * @return
     */
    public static BeautySexFragment getInstanse(int sexType){
        BeautySexFragment fragment=new BeautySexFragment();

        Bundle bundle=new Bundle();
        bundle.putInt("sexType",sexType);
        fragment.setArguments(bundle);

        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding= DataBindingUtil.inflate(getLayoutInflater(savedInstanceState), R.layout.layout_img, null, false);

        if(getArguments()!=null){
            mSexType=getArguments().getInt("sexType");
        }

        if(mSexType == 1){
            ImgUtils.loadImgId(mActivity,R.mipmap.manager_man,mBinding.img);
        }else{
            ImgUtils.loadImgId(mActivity,R.mipmap.manager_woman,mBinding.img);
        }

        mBinding.img.setOnClickListener(v -> {
            SexMenuActivity.open(mActivity);
        });

        return mBinding.getRoot();
    }
}

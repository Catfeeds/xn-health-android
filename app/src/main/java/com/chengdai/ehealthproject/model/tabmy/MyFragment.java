package com.chengdai.ehealthproject.model.tabmy;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.BaseLazyFragment;
import com.chengdai.ehealthproject.databinding.FragmentHealthManagerBinding;
import com.chengdai.ehealthproject.databinding.FragmentMyBinding;
import com.chengdai.ehealthproject.model.dataadapters.TablayoutAdapter;
import com.chengdai.ehealthproject.model.tabmy.activitys.HotelOrderStateLookActivity;

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


        isCreate=true;
        return mBinding.getRoot();
    }

    @Override
    protected void lazyLoad() {

        if (isCreate){

            isCreate=false;
        }

    }

    @Override
    protected void onInvisible() {

    }
}

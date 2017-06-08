package com.chengdai.ehealthproject.model.healthstore;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.BaseLazyFragment;
import com.chengdai.ehealthproject.databinding.FragmentHealthManagerBinding;

/**健康商城
 * Created by 李先俊 on 2017/6/8.
 */

public class HealthStoreFragment extends BaseLazyFragment{

    private FragmentHealthManagerBinding managerBinding;

    private boolean isCreate;


    /**
     * 获得fragment实例
     * @return
     */
    public static HealthStoreFragment getInstanse(){
        HealthStoreFragment fragment=new HealthStoreFragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        managerBinding= DataBindingUtil.inflate(getLayoutInflater(savedInstanceState), R.layout.fragment_health_manager, null, false);

        isCreate=true;

        return managerBinding.getRoot();

    }

    @Override
    protected void lazyLoad() {

        if (isCreate){

        }

    }

    @Override
    protected void onInvisible() {

    }
}

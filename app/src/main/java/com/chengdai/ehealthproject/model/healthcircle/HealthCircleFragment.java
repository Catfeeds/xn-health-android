package com.chengdai.ehealthproject.model.healthcircle;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.BaseLazyFragment;
import com.chengdai.ehealthproject.databinding.FragmentHealthCircleBinding;
import com.chengdai.ehealthproject.model.dataadapters.ViewPagerAdapter;
import com.chengdai.ehealthproject.model.healthcircle.activitys.SendEditInfoActivity;
import com.chengdai.ehealthproject.model.healthcircle.fragments.HealthCircleHotFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**健康圈
 * Created by 李先俊 on 2017/6/8.
 */

public class HealthCircleFragment extends BaseLazyFragment{

    private FragmentHealthCircleBinding managerBinding;

    private boolean isCreate;


    /**
     * 获得fragment实例
     * @return
     */
    public static HealthCircleFragment getInstanse(){
        HealthCircleFragment fragment=new HealthCircleFragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        managerBinding= DataBindingUtil.inflate(getLayoutInflater(savedInstanceState), R.layout.fragment_health_circle, null, false);

        isCreate=true;


        initViewPager();

        managerBinding.imgAddTiezi.setOnClickListener(v -> {
            SendEditInfoActivity.open(mActivity);
        });


        return managerBinding.getRoot();

    }

    private void initViewPager() {
        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getChildFragmentManager(),getFragmes());

        managerBinding.viewPager.setAdapter(viewPagerAdapter);
        managerBinding.indicatorTab.setmLinWidth(50);
        managerBinding.indicatorTab.setTabItemTitles(Arrays.asList("全部","热门"));
        managerBinding.indicatorTab.setViewPager(managerBinding.viewPager, 0);

        managerBinding.viewPager.setOffscreenPageLimit(viewPagerAdapter.getCount());
    }

    @Override
    protected void lazyLoad() {

        if (isCreate){

        }
    }

    @Override
    protected void onInvisible() {
    }

    public List<Fragment> getFragmes() {
        List<Fragment> fragmes=new ArrayList<>();
        fragmes.add(HealthCircleHotFragment.getInstanse(HealthCircleHotFragment.ALLTYPE));
        fragmes.add(HealthCircleHotFragment.getInstanse(HealthCircleHotFragment.HOTTYPE));
        return fragmes;
    }
}

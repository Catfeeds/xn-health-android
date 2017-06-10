package com.chengdai.ehealthproject.model.tabsurrounding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.BaseLazyFragment;
import com.chengdai.ehealthproject.databinding.FragmentSurroundingBinding;
import com.chengdai.ehealthproject.model.tabsurrounding.activitys.SurroundingMenuSeletActivity;
import com.chengdai.ehealthproject.model.tabsurrounding.model.BannerModel;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.ToastUtil;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerListHelper;
import com.chengdai.ehealthproject.weigit.GlideImageLoader;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**周边
 * Created by 李先俊 on 2017/6/8.
 */

public class SurroundingFragment extends BaseLazyFragment{

    private FragmentSurroundingBinding mBinding;

    private boolean isCreate=false;


    /**
     * 获得fragment实例
     * @return
     */
    public static SurroundingFragment getInstanse(){
        SurroundingFragment fragment=new SurroundingFragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding= DataBindingUtil.inflate(getLayoutInflater(savedInstanceState), R.layout.fragment_surrounding, null, false);

        isCreate=true;

        initBanner();

        initViews();

        initSpringViews();

        return mBinding.getRoot();

    }

    private void initSpringViews() {

        mBinding.springviewSurrounding.setType(SpringView.Type.FOLLOW);
        mBinding.springviewSurrounding.setGive(SpringView.Give.TOP);
        mBinding.springviewSurrounding.setHeader(new DefaultHeader(getActivity()));
        mBinding.springviewSurrounding.setFooter(new DefaultFooter(getActivity()));

        mBinding.springviewSurrounding.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                mBinding.springviewSurrounding.onFinishFreshAndLoad();
            }
            @Override
            public void onLoadmore() {
                mBinding.springviewSurrounding.onFinishFreshAndLoad();
            }
        });
    }

    /**
     * 设置view
     */
    private void initViews() {

        //健康美食
        setTvListener(mBinding.layoutSurroundingMenu.linFood,mActivity.getResources().getString(R.string.txt_food));

        setTvListener(mBinding.layoutSurroundingMenu.linMovement,mActivity.getResources().getString(R.string.txt_movement));

        setTvListener(mBinding.layoutSurroundingMenu.linWomenhome,mActivity.getResources().getString(R.string.txt_women_home));

        setTvListener(mBinding.layoutSurroundingMenu.linLife,mActivity.getResources().getString(R.string.txt_life));

        setTvListener(mBinding.layoutSurroundingMenu.linHotel,mActivity.getResources().getString(R.string.txt_hotel));

        setTvListener(mBinding.layoutSurroundingMenu.linDabaojian,mActivity.getResources().getString(R.string.txt_dabaojian));

        setTvListener(mBinding.layoutSurroundingMenu.linLife2,mActivity.getResources().getString(R.string.txt_life2));

        setTvListener(mBinding.layoutSurroundingMenu.linShopping,mActivity.getResources().getString(R.string.txt_sopping));

/*        //运动健身
        mBinding.layoutSurroundingMenu.linMovement.setOnClickListener(v -> {

        });

        //女人馆
        mBinding.layoutSurroundingMenu.linWomenhome.setOnClickListener(v -> {

        });

       //养生休闲
        mBinding.layoutSurroundingMenu.linWomenhome.setOnClickListener(v -> {

        });

       //酒店住宿
        mBinding.layoutSurroundingMenu.linHotel.setOnClickListener(v -> {

        });

       //医疗保健
        mBinding.layoutSurroundingMenu.linDabaojian.setOnClickListener(v -> {

        });

       //便民生活
        mBinding.layoutSurroundingMenu.linLife2.setOnClickListener(v -> {

        });

     //周边购物
        mBinding.layoutSurroundingMenu.linShopping.setOnClickListener(v -> {

        });*/


    }

    private void setTvListener(LinearLayout lin,String tv) {
        lin.setOnClickListener(v -> {
            SurroundingMenuSeletActivity.open(mActivity,tv);
        });
    }

    private void initBanner() {

        mBinding.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBinding.banner.setIndicatorGravity(BannerConfig.CENTER);

        mBinding.banner.setImageLoader(new GlideImageLoader());

        mBinding.banner.setOnBannerListener((position) -> {  //banner点击事件
            ToastUtil.show(getActivity(),position+"");
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding.banner.stopAutoPlay();
    }
    @Override
    public void onResume() {
        super.onResume();
        mBinding.banner.startAutoPlay();
    }
    @Override
    public void onPause() {
        super.onPause();
        mBinding.banner.stopAutoPlay();
    }

    @Override
    protected void lazyLoad() {
        if (isCreate){

            bannerDataRequest();

            if(mBinding!=null && mBinding.banner!=null){
                mBinding.banner.start();
                mBinding.banner.startAutoPlay();
            }
            isCreate=false;

        }

    }

    /**
     * 获取banner图片
     */
    private void bannerDataRequest() {
        Map map=new HashMap();

        map.put("type","2");
        map.put("systemCode", MyConfig.SYSTEMCODE);
        map.put("token", SPUtilHelpr.getUserToken());

        RetrofitUtils.getLoaderServer().GetBanner("806052", StringUtils.getJsonToString(map))
                .compose(RxTransformerListHelper.applySchedulerResult(mActivity))
                .map(banners -> {
                    List images=new ArrayList();
                    for(BannerModel ba:banners){
                        images.add(MyConfig.IMGURL+ba.getPic());
                    }
                    return images;

                })
                .subscribe(banners -> {

                    //设置图片集合
                    mBinding.banner.setImages(banners);
                    //banner设置方法全部调用完毕时最后调用
                    mBinding.banner.start();


                },Throwable::printStackTrace);
    }

    @Override
    protected void onInvisible() {
        if(isCreate && mBinding!=null && mBinding.banner!=null){
            mBinding.banner.stopAutoPlay();
        }

    }
}

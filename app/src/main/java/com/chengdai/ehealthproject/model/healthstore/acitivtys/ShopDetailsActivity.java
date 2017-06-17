package com.chengdai.ehealthproject.model.healthstore.acitivtys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.base.BaseFragment;
import com.chengdai.ehealthproject.databinding.ActivityShopDetailsBinding;
import com.chengdai.ehealthproject.model.dataadapters.ViewPagerAdapter;
import com.chengdai.ehealthproject.model.healthstore.fragments.ShopCommodityFragment;
import com.chengdai.ehealthproject.model.healthstore.fragments.ShopDeatilsFragment;
import com.chengdai.ehealthproject.model.healthstore.models.ShopListModel;
import com.chengdai.ehealthproject.weigit.popwindows.ShopPayPopup;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxAdapterView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李先俊 on 2017/6/17.
 */

public class ShopDetailsActivity extends AbsBaseActivity {

    private ActivityShopDetailsBinding mBinding;

    private ShopListModel.ListBean mData;
    private ShopPayPopup shopPayPopup;


    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context, ShopListModel.ListBean data){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,ShopDetailsActivity.class);
        intent.putExtra("data",data);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_shop_details, null, false);
        hintTitleView();
        addMainView(mBinding.getRoot());

        if(getIntent() != null) mData=getIntent().getParcelableExtra("data");

        initViews();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(shopPayPopup !=null){
            shopPayPopup.dismiss();
            shopPayPopup=null;
        }
    }

    /**
     * 初始化View
     */
    private void initViews() {
        //返回
        mBinding.layoutBack.setOnClickListener(v -> {
            finish();
        });
       //商品
        mBinding.txtCommodity.setOnClickListener(v -> {
            setTopTabTitleShowState(1);
            mBinding.viewpager.setCurrentItem(0);

        });
       //详情
        mBinding.txtDetail.setOnClickListener(v -> {
            setTopTabTitleShowState(2);
            mBinding.viewpager.setCurrentItem(1);
        });
        //评价
        mBinding.txtEvaluate.setOnClickListener(v -> {
            setTopTabTitleShowState(3);
            mBinding.viewpager.setCurrentItem(2);
        });



        mBinding.tvPay.setOnClickListener(v -> {

            if(shopPayPopup == null ){
                shopPayPopup = new ShopPayPopup(this);
                shopPayPopup.setmData(mData);
            }
            shopPayPopup.showPopupWindow();

        });


        initViewPager();
    }

    /**
     * 初始化ViewPgaer
     */
    private void initViewPager() {

        List<Fragment> fragments=new ArrayList<>();
        fragments.add(ShopCommodityFragment.getInstanse(mData));
        fragments.add(ShopDeatilsFragment.getInstanse(mData));
        fragments.add(ShopCommodityFragment.getInstanse(mData));

        mBinding.viewpager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),fragments));


        mBinding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               setTopTabTitleShowState(position+1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 设置顶部tab状态
     *
     */
    private void setTopTabTitleShowState(int state) {

        switch (state){
            case 1:
                mBinding.txtDetail.setTextColor(ContextCompat.getColor(this, R.color.text_gray2));
                mBinding.txtEvaluate.setTextColor(ContextCompat.getColor(this,R.color.text_gray2));
                mBinding.txtCommodity.setTextColor(ContextCompat.getColor(this,R.color.orange));

                mBinding.lineCommodity.setVisibility(View.VISIBLE);
                mBinding.lineDetail.setVisibility(View.INVISIBLE);
                mBinding.lineEvaluate.setVisibility(View.INVISIBLE);
                break;
            case 2:
                mBinding.txtCommodity.setTextColor(ContextCompat.getColor(this,R.color.text_gray2));
                mBinding.txtEvaluate.setTextColor(ContextCompat.getColor(this,R.color.text_gray2));
                mBinding.txtDetail.setTextColor(ContextCompat.getColor(this,R.color.orange));

                mBinding.lineCommodity.setVisibility(View.INVISIBLE);
                mBinding.lineDetail.setVisibility(View.VISIBLE);
                mBinding.lineEvaluate.setVisibility(View.INVISIBLE);
                break;
            case 3:
                mBinding.txtCommodity.setTextColor(ContextCompat.getColor(this,R.color.text_gray2));
                mBinding.txtDetail.setTextColor(ContextCompat.getColor(this,R.color.text_gray2));
                mBinding.txtEvaluate.setTextColor(ContextCompat.getColor(this,R.color.orange));

                mBinding.lineCommodity.setVisibility(View.INVISIBLE);
                mBinding.lineDetail.setVisibility(View.INVISIBLE);
                mBinding.lineEvaluate.setVisibility(View.VISIBLE);

                break;
        }

    }
}

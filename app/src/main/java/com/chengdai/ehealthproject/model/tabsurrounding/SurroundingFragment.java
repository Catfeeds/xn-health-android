package com.chengdai.ehealthproject.model.tabsurrounding;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.BaseLazyFragment;
import com.chengdai.ehealthproject.databinding.FragmentSurroundingBinding;
import com.chengdai.ehealthproject.model.common.model.CityModel;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.common.model.LocationModel;
import com.chengdai.ehealthproject.model.common.model.activitys.CitySelectActivity;
import com.chengdai.ehealthproject.model.common.model.activitys.SearchActivity;
import com.chengdai.ehealthproject.model.common.model.activitys.WebViewActivity;
import com.chengdai.ehealthproject.model.tabsurrounding.activitys.HotelSelectActivity;
import com.chengdai.ehealthproject.model.tabsurrounding.activitys.HoteldetailsActivity;
import com.chengdai.ehealthproject.model.tabsurrounding.activitys.StoredetailsActivity;
import com.chengdai.ehealthproject.model.tabsurrounding.activitys.SurroundingMenuSeletActivity;
import com.chengdai.ehealthproject.model.tabsurrounding.adapters.StoreTypeListAdapter;
import com.chengdai.ehealthproject.model.tabsurrounding.adapters.SurroundingMenuLeftAdapter;
import com.chengdai.ehealthproject.model.tabsurrounding.adapters.SurroundingStoreTypeAdapter;
import com.chengdai.ehealthproject.model.tabsurrounding.model.BannerModel;
import com.chengdai.ehealthproject.model.tabsurrounding.model.DZUpdateModel;
import com.chengdai.ehealthproject.model.tabsurrounding.model.StoreListModel;
import com.chengdai.ehealthproject.model.tabsurrounding.model.StoreTypeModel;
import com.chengdai.ehealthproject.uitls.LogUtil;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.ToastUtil;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerListHelper;
import com.chengdai.ehealthproject.weigit.GlideImageLoader;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.youth.banner.BannerConfig;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;


import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

import static com.chengdai.ehealthproject.weigit.appmanager.MyConfig.HOTELTYPE;


/**周边
 * Created by 李先俊 on 2017/6/8.
 */

public class SurroundingFragment extends BaseLazyFragment{

    private FragmentSurroundingBinding mBinding;

    private boolean isCreate=false;
    private StoreTypeListAdapter mStoreTypeAdapter;
    private SurroundingStoreTypeAdapter mStoreMenuAdapter;

    private int mStoreStart=1;
    private LocationModel locationModel;

    private List<String> bannerLinks=new ArrayList<>();


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

        locationModel = SPUtilHelpr.getLocationInfo();
        if(locationModel!=null && !TextUtils.isEmpty(locationModel.getCityName())){
            mBinding.tvLocation.setText(locationModel.getCityName());
        }else{
            mBinding.tvLocation.setText(R.string.txt_change_city);
        }

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
                mStoreStart=1;

                storeTypeRequest(null);

                bannerDataRequest(null);

                getStoreListRequest(null,1);

                mBinding.springviewSurrounding.onFinishFreshAndLoad();
            }
            @Override
            public void onLoadmore() {
                mStoreStart++;
                getStoreListRequest(null,2);
                mBinding.springviewSurrounding.onFinishFreshAndLoad();
            }
        });
    }

    /**
     * 设置view
     */
    private void initViews() {

        //健康美食
      /*  setTvListener(mBinding.layoutSurroundingMenu.linFood,mActivity.getResources().getString(R.string.txt_food));

        setTvListener(mBinding.layoutSurroundingMenu.linMovement,mActivity.getResources().getString(R.string.txt_movement));

        setTvListener(mBinding.layoutSurroundingMenu.linWomenhome,mActivity.getResources().getString(R.string.txt_women_home));

        setTvListener(mBinding.layoutSurroundingMenu.linLife,mActivity.getResources().getString(R.string.txt_life));

        setTvListener(mBinding.layoutSurroundingMenu.linHotel,mActivity.getResources().getString(R.string.txt_hotel));

        setTvListener(mBinding.layoutSurroundingMenu.linDabaojian,mActivity.getResources().getString(R.string.txt_dabaojian));

        setTvListener(mBinding.layoutSurroundingMenu.linLife2,mActivity.getResources().getString(R.string.txt_life2));

        setTvListener(mBinding.layoutSurroundingMenu.linShopping,mActivity.getResources().getString(R.string.txt_sopping));*/

        mStoreMenuAdapter = new SurroundingStoreTypeAdapter(mActivity,new ArrayList<>());
        mBinding.gridStoreType.setAdapter(mStoreMenuAdapter);

        mBinding.lvStoreList.setOnItemClickListener((parent, view, position, id) -> {
            StoreListModel.ListBean model= (StoreListModel.ListBean) mStoreTypeAdapter.getItem(position-mBinding.lvStoreList.getHeaderViewsCount());

            if(HOTELTYPE.equals(model.getType())){  //酒店类型
                HoteldetailsActivity.open(mActivity,model.getCode());
            }else{
                StoredetailsActivity.open(mActivity,model.getCode());
            }

        });

        mBinding.gridStoreType.setOnItemClickListener((parent, view, position, id) -> {

            if(mStoreMenuAdapter!=null){
                StoreTypeModel model= (StoreTypeModel) mStoreMenuAdapter.getItem(position);
                if(model!=null){

                    SurroundingMenuSeletActivity.open(mActivity,model.getCode());
                }
            }

        });

        mStoreTypeAdapter = new StoreTypeListAdapter(mActivity,new ArrayList<>(),false);
        mBinding.lvStoreList.setAdapter(mStoreTypeAdapter);


        mBinding.search.linSerchtop.setOnClickListener(v -> {
            SearchActivity.open(mActivity,"周边搜索");
        });

        mBinding.linLocation.setOnClickListener(v -> {
            CitySelectActivity.open(mActivity);
        });

        mBinding.search.editSerchView.setHint("请输入您感兴趣的商户");
    }

    private void initBanner() {

        mBinding.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBinding.banner.setIndicatorGravity(BannerConfig.CENTER);

        mBinding.banner.setImageLoader(new GlideImageLoader());

        mBinding.banner.setOnBannerListener((position) -> {  //banner点击事件

            if(!TextUtils.isEmpty(bannerLinks.get(position))){
                WebViewActivity.open(mActivity,bannerLinks.get(position));
            }

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
        if(getUserVisibleHint()){
            mBinding.banner.startAutoPlay();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        mBinding.banner.stopAutoPlay();
    }

    @Override
    protected void lazyLoad() {
        if (isCreate){

            getAllData();
            isCreate=false;

        }

    }
//获取所有接口数据
    private void getAllData() {

        storeTypeRequest(mActivity);

        bannerDataRequest(mActivity);

        getStoreListRequest(mActivity,1);



        if(mBinding!=null && mBinding.banner!=null){
            mBinding.banner.start();
            mBinding.banner.startAutoPlay();
        }
    }

    /**
     * 获取banner图片
     */
    private void bannerDataRequest(Context context) {
        Map map=new HashMap();

        map.put("location","0");//0周边1推荐
        map.put("type","2"); //(1 菜单 2 banner 3 模块 4 引流)
        map.put("systemCode", MyConfig.SYSTEMCODE);
        map.put("token", SPUtilHelpr.getUserToken());

        mSubscription.add(RetrofitUtils.getLoaderServer().GetBanner("806052", StringUtils.getJsonToString(map))
                .compose(RxTransformerListHelper.applySchedulerResult(context))
                .filter(banners -> banners!=null)
                .map(banners -> {
                    List images=new ArrayList();
                    for(BannerModel ba:banners){
                        if(ba !=null ){
                            images.add(ba.getPic());
                            bannerLinks.add(ba.getUrl());
                        }

                    }
                    return images;

                })
                .subscribe(banners -> {

                    //设置图片集合
                    mBinding.banner.setImages(banners);
                    //banner设置方法全部调用完毕时最后调用
                    mBinding.banner.start();


                },Throwable::printStackTrace));
    }

    /**
     * 获取商户列表
     * @param act
     * @param loadType 加载类型 1 下拉 2上拉
     */
    public void getStoreListRequest(Activity act,int loadType){
       Map map=new HashMap();

        map.put("userId",SPUtilHelpr.getUserId());

        if(locationModel !=null){
            map.put("province", locationModel.getProvinceName());
            map.put("city", locationModel.getCityName());
            map.put("area", locationModel.getAreaName());
            map.put("longitude", locationModel.getLatitude());
            map.put("latitude", locationModel.getLongitud());
        }else if(!TextUtils.isEmpty(SPUtilHelpr.getResetLocationInfo().getCityName())){
            map.put("city", SPUtilHelpr.getResetLocationInfo().getCityName());
        }
        map.put("status","2");
        map.put("start",mStoreStart+"");
        map.put("limit","10");
        map.put("uiLocation","1");//0普通 1推荐
        map.put("companyCode",MyConfig.COMPANYCODE);
        map.put("systemCode",MyConfig.SYSTEMCODE);
        map.put("orderDir","asc");
        map.put("orderColumn","ui_order");
        mSubscription.add(  RetrofitUtils.getLoaderServer().GetStoreList("808217",StringUtils.getJsonToString(map))

                .compose(RxTransformerHelper.applySchedulerResult(act))

              .filter(storeListModel -> storeListModel!=null)

                .subscribe(storeListModel -> {
                    if(loadType==1){
                        if(storeListModel.getList()==null){ //分页
                            return;
                        }
                        mStoreTypeAdapter.setData(storeListModel.getList());

                    }else{
                        if(storeListModel.getList()==null || storeListModel.getList().size()==0 ){ //分页
                            if(mStoreStart>1){
                                mStoreStart--;
                            }
                            return;
                        }

                        mStoreTypeAdapter.addData(storeListModel.getList());
                    }


                },Throwable::printStackTrace));


    }




    private void storeTypeRequest(Context context) {


        Map<String,String> map=new HashMap();

        map.put("parentCode","0");
        map.put("type","2");            //1 -商城分类 2 店铺分类
        map.put("status","1");
        map.put("companyCode",MyConfig.COMPANYCODE);
        map.put("systemCode", MyConfig.SYSTEMCODE);


        mSubscription.add( RetrofitUtils.getLoaderServer().GetStoreType("808007", StringUtils.getJsonToString(map))

                .compose(RxTransformerListHelper.applySchedulerResult(context))

                .filter(storeTypeModels -> storeTypeModels!=null)

                .subscribe(r -> {
                    mStoreMenuAdapter.setDatas(r);
                },Throwable::printStackTrace));

    }

    /**
     * 点赞效果刷新
     * @param
     */
    @Subscribe//  StoreTypeListAdapter StoredetailsActivity
    public void dzUpdate(DZUpdateModel dzUpdateModel){
        if(mStoreTypeAdapter!=null){
            mStoreTypeAdapter.setDzInfo(dzUpdateModel);
        }
    }

    @Subscribe
    public void SurroundingFragmentRefresh(EventBusModel eventBusModel){
        if(eventBusModel == null)return;
        if(TextUtils.equals(eventBusModel.getTag(),"SurroundingFragmentRefresh")){
            getAllData();
        }
    }

    /**
     * 城市选择
     * @param
     */
    @Subscribe
    public void citySelect(CityModel cityModel){
        if(cityModel!=null){
            SPUtilHelpr.saveRestLocationInfo(cityModel.getName());
            SPUtilHelpr.saveLocationInfo("");
            mBinding.tvLocation.setText(cityModel.getName());
            getStoreListRequest(null,1);
        }
    }

    @Override
    protected void onInvisible() {
        if(isCreate && mBinding!=null && mBinding.banner!=null){
            mBinding.banner.stopAutoPlay();
        }

    }
}

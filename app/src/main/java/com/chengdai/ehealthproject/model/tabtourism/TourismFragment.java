package com.chengdai.ehealthproject.model.tabtourism;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.amap.api.location.AMapLocation;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.BaseLazyFragment;
import com.chengdai.ehealthproject.databinding.FragmentTourismListBinding;
import com.chengdai.ehealthproject.model.common.model.CityModel;
import com.chengdai.ehealthproject.model.common.model.LocationModel;
import com.chengdai.ehealthproject.model.common.model.activitys.CitySelectActivity;
import com.chengdai.ehealthproject.model.common.model.activitys.SearchActivity;
import com.chengdai.ehealthproject.model.tabsurrounding.activitys.HoteldetailsActivity;
import com.chengdai.ehealthproject.model.tabsurrounding.activitys.StoredetailsActivity;
import com.chengdai.ehealthproject.model.tabsurrounding.adapters.StoreTypeListAdapter;
import com.chengdai.ehealthproject.model.tabsurrounding.model.DZUpdateModel;
import com.chengdai.ehealthproject.model.tabsurrounding.model.StoreListModel;
import com.chengdai.ehealthproject.uitls.LogUtil;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.chengdai.ehealthproject.weigit.appmanager.MyConfig.HOTELTYPE;

/**旅游民宿
 * Created by 李先俊 on 2017/7/31.
 */

public class TourismFragment extends BaseLazyFragment {


    private FragmentTourismListBinding mBinding;
    private StoreTypeListAdapter mAdapter;

    private int mStoreStart=1;

    private String mType="mingsu";//接口查询小类

    private boolean isCreate=false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding= DataBindingUtil.inflate(getLayoutInflater(savedInstanceState), R.layout.fragment_tourism_list, null, false);

        initViews();
        isCreate=true;
        return mBinding.getRoot();

    }


    private void initViews() {


        mBinding.linLocation.setOnClickListener(v -> {
            CitySelectActivity.open(mActivity);
        });


        mAdapter = new StoreTypeListAdapter(mActivity,new ArrayList<>(),true);
        mBinding.lvStoreType.setAdapter(mAdapter);

        mBinding.lvStoreType.setOnItemClickListener((parent, view, position, id) -> {

            StoreListModel.ListBean model= (StoreListModel.ListBean) mAdapter.getItem(position-mBinding.lvStoreType.getHeaderViewsCount());

            LogUtil.E("type"+model.getType());


            if(HOTELTYPE.equals(model.getType())){  //酒店类型
                HoteldetailsActivity.open(mActivity,model.getCode());
            }else{
                StoredetailsActivity.open(mActivity,model.getCode());
            }

        });


        mBinding.springvew.setType(SpringView.Type.FOLLOW);
        mBinding.springvew.setGive(SpringView.Give.TOP);
        mBinding.springvew.setHeader(new DefaultHeader(mActivity));
        mBinding.springvew.setFooter(new DefaultFooter(mActivity));


        mBinding.springvew.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                mStoreStart=1;
                getStoreListRequest(null,1);
                mBinding.springvew.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                mStoreStart++;
                getStoreListRequest(null,2);
                mBinding.springvew.onFinishFreshAndLoad();
            }
        });

    }


    /**
     * 获取商户列表
     */
    public void getStoreListRequest(Context context,int loadType){
        LocationModel locationModel = SPUtilHelpr.getLocationInfo();
        Map map=new HashMap();
        map.put("userId", SPUtilHelpr.getUserId());
        map.put("type", mType);
        if(locationModel !=null){
            map.put("province", locationModel.getProvinceName());
            map.put("city", locationModel.getCityName());
//            map.put("area", locationModel.getAreaName());
            map.put("longitude", locationModel.getLatitude());
            map.put("latitude", locationModel.getLongitud());
        }else if(!TextUtils.isEmpty(SPUtilHelpr.getResetLocationInfo().getCityName())){
            map.put("city", SPUtilHelpr.getResetLocationInfo().getCityName());
        }else{
            map.put("city","金华");
        }
        map.put("start",mStoreStart+"");
        map.put("limit","10");
        map.put("companyCode", MyConfig.COMPANYCODE);
        map.put("systemCode",MyConfig.SYSTEMCODE);
        map.put("orderDir","asc");
        map.put("orderColumn","ui_order");
        mSubscription.add( RetrofitUtils.getLoaderServer().GetStoreList("808217", StringUtils.getJsonToString(map))

                .compose(RxTransformerHelper.applySchedulerResult(context))

                .filter(storeListModel -> storeListModel!=null)

                .subscribe(storeListModel -> {

                    if(loadType==1){
                        if(storeListModel.getList()!=null ){ //分页
                            mAdapter.setData(storeListModel.getList());
                        }

                    }else{
                        if(storeListModel.getList()==null || storeListModel.getList().size()==0 ){ //分页
                            if(mStoreStart>1){
                                mStoreStart--;
                            }
                            return;
                        }

                        mAdapter.addData(storeListModel.getList());
                    }

                    if(mAdapter.getCount()==0){
                        mBinding.tvEmpty.setVisibility(View.VISIBLE);
                    }else{
                        mBinding.tvEmpty.setVisibility(View.GONE);
                    }

                },Throwable::printStackTrace));

    }

    /**
     * 点赞效果刷新
     * @param
     */
    @Subscribe
    public void dzUpdate(DZUpdateModel dzUpdateModel){
        if(mAdapter!=null){
            mAdapter.setDzInfo(dzUpdateModel);
        }

    }

    /**
     * 城市选择
     * @param
     */
    @Subscribe
    public void citySelect(CityModel cityModel){
        if(cityModel!=null){
            mBinding.tvLocation.setText(cityModel.getName());
            mStoreStart=1;
            getStoreListRequest(null,1);
        }
    }


    /**
     * @param
     */
    @Subscribe
    public void locationFailure(AMapLocation cityModel){
        if(cityModel!=null && isCreate){
            mBinding.tvLocation.setText(cityModel.getCity());
            mStoreStart=1;
            getStoreListRequest(null,1);
        }
    }

    @Override
    protected void lazyLoad() {
        if (isCreate){
            LocationModel locationModel = SPUtilHelpr.getLocationInfo();
            if(locationModel!=null){
                mBinding.tvLocation.setText(SPUtilHelpr.getLocationInfo().getCityName());
            }else if(!TextUtils.isEmpty(SPUtilHelpr.getResetLocationInfo().getCityName())){
                mBinding.tvLocation.setText(SPUtilHelpr.getResetLocationInfo().getCityName());
            }else{
                mBinding.tvLocation.setText("金华");
            }
            getStoreListRequest(mActivity,1);

            isCreate=false;

        }

    }

    @Override
    protected void onInvisible() {

    }
}

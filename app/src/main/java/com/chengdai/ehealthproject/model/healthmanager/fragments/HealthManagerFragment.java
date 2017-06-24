package com.chengdai.ehealthproject.model.healthmanager.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.location.AMapLocation;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.BaseLazyFragment;
import com.chengdai.ehealthproject.databinding.FragmentHealthManagerBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.common.model.LocationModel;
import com.chengdai.ehealthproject.model.healthcircle.adapters.LuntanListAdapter;
import com.chengdai.ehealthproject.model.healthmanager.acitivitys.HealthAssistantActivity;
import com.chengdai.ehealthproject.model.healthmanager.acitivitys.HealthinfoActivity;
import com.chengdai.ehealthproject.model.healthmanager.model.WeatherModel;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.uitls.LogUtil;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerListHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**健康管理
 * Created by 李先俊 on 2017/6/8.
 */

public class HealthManagerFragment extends BaseLazyFragment{

    private FragmentHealthManagerBinding mBinding;

    private boolean isCreate;

    private int mPageStart=1;
    private LuntanListAdapter mAdapter;


    /**
     * 获得fragment实例
     * @return
     */
    public static HealthManagerFragment getInstanse(){
        HealthManagerFragment fragment=new HealthManagerFragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding= DataBindingUtil.inflate(getLayoutInflater(savedInstanceState), R.layout.fragment_health_manager, null, false);

        isCreate=true;

        getDataRequest(mActivity);

        getUserInfoRequest(null);

        getJifenRequest(null);

        initListView();

        initSpringView();

        initViewListener();

        return mBinding.getRoot();

    }

    private void initViewListener() {

        //健康资讯
        mBinding.layoutMenuHealthInfo.setOnClickListener(v -> {
            HealthinfoActivity.open(mActivity);
        });
        //健康助手
        mBinding.layoutMenuHealthAssistant.setOnClickListener(v -> {
            HealthAssistantActivity.open(mActivity);

        });

    }

    private void initSpringView() {

        mBinding.springview.setType(SpringView.Type.FOLLOW);
        mBinding.springview.setGive(SpringView.Give.TOP);
        mBinding.springview.setHeader(new DefaultHeader(mActivity));
        mBinding.springview.setFooter(new DefaultFooter(mActivity));

        mBinding.springview.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {

                mPageStart=1;
                LocationModel locationModel=SPUtilHelpr.getLocationInfo();
                if(locationModel==null ){
                    getWeatherData("");
                }else if(TextUtils.isEmpty(locationModel.getAreaName())){
                    getWeatherData("");
                }else{
                    getWeatherData(locationModel.getAreaName());
                }

                getDataRequest(null);
                getUserInfoRequest(null);
                getJifenRequest(null);
                mBinding.springview.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                mPageStart++;
                getDataRequest(null);
                mBinding.springview.onFinishFreshAndLoad();
            }
        });

    }


    private void initListView() {

        mAdapter = new LuntanListAdapter(mActivity,new ArrayList<>());
        mBinding.lvFooter.setAdapter(mAdapter);
    }




    @Override
    protected void lazyLoad() {

        if (isCreate){

        }

    }

    @Override
    protected void onInvisible() {

    }

    @Subscribe
    public void locationSuccessful(AMapLocation aMapLocation){

        LogUtil.E("定位成功");

        if(aMapLocation == null){
            getWeatherData("");
            mBinding.weatherlayout.tvCityName.setText("金华");
            return;
        }

       mBinding.weatherlayout.tvCityName.setText(aMapLocation.getCity());

        if(! TextUtils.isEmpty(aMapLocation.getAdCode())){
           mSubscription.add( getWeatheObservable(aMapLocation.getAdCode()).subscribe(weatherModel -> {
                setWeahterShow(weatherModel);
            },throwable -> {
               mBinding.weatherlayout.tvCityName.setText("金华");
                setWeahterShow(null);
            }));
        }else{
            getWeatherData(aMapLocation.getCity());
        }
    }
    @Subscribe
    public void locationFailure(EventBusModel e){
        LogUtil.E("定位失败");
        if(e==null){
            getWeatherData("");
            return;
        }
        if( TextUtils.equals(e.getTag(),"locationFailure")){
            getWeatherData("");

        }else  if( TextUtils.equals(e.getTag(),"LuntanDzRefeshEnent")){
            mPageStart=1;
            getDataRequest(null);
        }

    }

    /**
     * 获取天气
     * @param cityname
     */
    private void getWeatherData(String cityname) {

        if(TextUtils.isEmpty(cityname)) cityname="金华";
        mBinding.weatherlayout.tvCityName.setText(cityname);
    mSubscription.add(    RetrofitUtils.getLoaderServer().getCityCode(cityname,"ad51a85d0046c4e083a9f263ae96868e","0")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(data ->{
                    if(data == null || !TextUtils.equals("10000",data.getInfocode()) || data.getDistricts() == null
                            ||data.getDistricts().size() ==0 || data.getDistricts().get(0)==null
                            || TextUtils.isEmpty(data.getDistricts().get(0).getAdcode())){
                        return "330700";//金华code
                    }

                    return data.getDistricts().get(0).getAdcode();

                }).flatMap(s ->getWeatheObservable(s) )

                .subscribe(weatherModel -> {

                    setWeahterShow(weatherModel);

                },throwable -> {
                    mBinding.weatherlayout.tvCityName.setText("金华");
                    setWeahterShow(null);
                }));

    }

    /**
     * 设置天气数据显示
     * @param weatherModel
     */
    private void setWeahterShow(WeatherModel weatherModel) {

        if(weatherModel == null || weatherModel.getForecasts()==null ||  weatherModel.getForecasts().size()==0
                || weatherModel.getForecasts().get(0)==null ||  weatherModel.getForecasts().get(0).getCasts()  == null
                ||        weatherModel.getForecasts().get(0).getCasts().size()==0
                ){


            mBinding.weatherlayout.tvDay.setText("今天 ");
            mBinding.weatherlayout.tvWeather.setText("获取天气信息失败");

            return;
        }
       WeatherModel.ForecastsBean.CastsBean weahter= weatherModel.getForecasts().get(0).getCasts().get(0);

        if(weahter == null){
            return;
        }

        mBinding.weatherlayout.tvDay.setText("今天 "+ weahter.getDate());
        mBinding.weatherlayout.tvWeather.setText(weahter.getDayweather()+" "+weahter.getNighttemp()+"℃-"+weahter.getDaytemp()+"℃" );
    }

    private Observable<WeatherModel> getWeatheObservable(String code){

      return   RetrofitUtils.getLoaderServer().getCityWeather(code,"ad51a85d0046c4e083a9f263ae96868e","all")
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }

    /**
     * 获取积分请求
     */
    private  void getJifenRequest(Context context){

        Map<String,String> map=new HashMap<>();

        map.put("userId", SPUtilHelpr.getUserId());
        map.put("currency","JF");
        map.put("token",SPUtilHelpr.getUserToken());

        mSubscription.add( RetrofitUtils.getLoaderServer().getAmount("802503", StringUtils.getJsonToString(map))
                .compose(RxTransformerListHelper.applySchedulerResult(context))
                .filter(r -> r !=null && r.size() >0  && r.get(0)!=null)
                .subscribe(r -> {
                    mBinding.jf.tvJf.setText(StringUtils.showPrice(r.get(0).getAmount()));

                },Throwable::printStackTrace));


    }
    /**
     * 获取用户信息请求
     */
    private  void getUserInfoRequest(Context context){

        Map<String,String> map=new HashMap<>();

        map.put("userId", SPUtilHelpr.getUserId());
        map.put("token",SPUtilHelpr.getUserToken());

        mSubscription.add( RetrofitUtils.getLoaderServer().GetUserInfo("805056", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(context))

                .filter(r -> r!=null)

                .subscribe(r -> {
                    mBinding.jf.txtName.setText(r.getLoginName());

                    if(r.getUserExt() == null) return;

                    ImgUtils.loadImgLogo(mActivity, MyConfig.IMGURL+r.getUserExt().getPhoto(),mBinding.jf.imgUserLogo);
                    if("0".equals(r.getUserExt().getGender())){
                        ImgUtils.loadImgId(mActivity,R.mipmap.man,mBinding.jf.imgSex);
                    }else if ("1".equals(r.getUserExt().getGender())){
                        ImgUtils.loadImgId(mActivity,R.mipmap.woman,mBinding.jf.imgSex);
                    }

                },Throwable::printStackTrace));

    }

    /**
     * 获取列表数据
     * @param context
     */

    public void getDataRequest(Context context){

        Map<String,String> map=new HashMap<>();

        map.put("userId", SPUtilHelpr.getUserId());
        map.put("location","1");
        map.put("status","BD");//审核通过并发布
        map.put("start",mPageStart+"");
        map.put("limit","10");

        mSubscription.add(RetrofitUtils.getLoaderServer().GetArticleLisData("621040", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(context))
                .subscribe(s -> {
                    if(mPageStart == 1){
                        if(s.getList()!=null){
                          mAdapter.setData(s.getList());
                        }


                    }else if(mPageStart >1){
                        if(s.getList()==null || s.getList().size()==0){
                            mPageStart--;
                            return;
                        }
                        mAdapter.addData(s.getList());
                    }
                },throwable -> {

                }));

    }





}

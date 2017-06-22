package com.chengdai.ehealthproject.model.healthmanager.fragments;

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
import com.chengdai.ehealthproject.model.healthmanager.model.WeatherModel;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerListHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;

import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**健康管理
 * Created by 李先俊 on 2017/6/8.
 */

public class HealthManagerFragment extends BaseLazyFragment{

    private FragmentHealthManagerBinding managerBinding;

    private boolean isCreate;


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

        managerBinding= DataBindingUtil.inflate(getLayoutInflater(savedInstanceState), R.layout.fragment_health_manager, null, false);

        isCreate=true;

        getUserInfoRequest();
        getJifenRequest();

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

    @Subscribe
    public void locationSuccessful(AMapLocation aMapLocation){

        if(aMapLocation == null){
            return;
        }

       managerBinding.weatherlayout.tvCityName.setText(aMapLocation.getCity());

        if(! TextUtils.isEmpty(aMapLocation.getAdCode())){
            getWeatheObservable(aMapLocation.getAdCode()).subscribe(weatherModel -> {
                setWeahterShow(weatherModel);
            });
        }else{
            getWeatherData(aMapLocation.getCity());
        }

    }
    @Subscribe
    public void locationFailure(EventBusModel e){
        if(e!=null && TextUtils.equals(e.getTag(),"locationFailure")){
            getWeatherData("");
        }
    }

    /**
     * 获取天气
     * @param cityname
     */
    private void getWeatherData(String cityname) {

        if(TextUtils.isEmpty(cityname)) cityname="金华";
        managerBinding.weatherlayout.tvCityName.setText(cityname);
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
            return;
        }
       WeatherModel.ForecastsBean.CastsBean weahter= weatherModel.getForecasts().get(0).getCasts().get(0);

        if(weahter == null){
            return;
        }

        managerBinding.weatherlayout.tvDay.setText("今天 "+ weahter.getDate());
        managerBinding.weatherlayout.tvWeather.setText(weahter.getDayweather()+" "+weahter.getNighttemp()+"℃-"+weahter.getDaytemp()+"℃" );
    }

    private Observable<WeatherModel> getWeatheObservable(String code){

      return   RetrofitUtils.getLoaderServer().getCityWeather(code,"ad51a85d0046c4e083a9f263ae96868e","all")
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }

    /**
     * 获取积分请求
     */
    private  void getJifenRequest(){

        Map<String,String> map=new HashMap<>();

        map.put("userId", SPUtilHelpr.getUserId());
        map.put("currency","JF");
        map.put("token",SPUtilHelpr.getUserToken());

        mSubscription.add( RetrofitUtils.getLoaderServer().getAmount("802503", StringUtils.getJsonToString(map))
                .compose(RxTransformerListHelper.applySchedulerResult(mActivity))
                .filter(r -> r !=null && r.size() >0  && r.get(0)!=null)
                .subscribe(r -> {
                    managerBinding.jf.tvJf.setText(StringUtils.showPrice(r.get(0).getAmount()));

                },Throwable::printStackTrace));


    }
    /**
     * 获取用户信息请求
     */
    private  void getUserInfoRequest(){

        Map<String,String> map=new HashMap<>();

        map.put("userId", SPUtilHelpr.getUserId());
        map.put("token",SPUtilHelpr.getUserToken());

        mSubscription.add( RetrofitUtils.getLoaderServer().GetUserInfo("805056", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(mActivity))

                .filter(r -> r!=null)

                .subscribe(r -> {
                    managerBinding.jf.txtName.setText(r.getLoginName());

                    if(r.getUserExt() == null) return;

                    ImgUtils.loadImgLogo(mActivity, MyConfig.IMGURL+r.getUserExt().getPhoto(),managerBinding.jf.imgUserLogo);
                    if("0".equals(r.getUserExt().getGender())){
                        ImgUtils.loadImgId(mActivity,R.mipmap.man,managerBinding.jf.imgSex);
                    }else if ("1".equals(r.getUserExt().getGender())){
                        ImgUtils.loadImgId(mActivity,R.mipmap.woman,managerBinding.jf.imgSex);
                    }

                },Throwable::printStackTrace));


    }

}

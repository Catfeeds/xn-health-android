package com.chengdai.ehealthproject.model.healthmanager.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.BaseFragment;
import com.chengdai.ehealthproject.databinding.FragmentManagerFirstBinding;
import com.chengdai.ehealthproject.databinding.FragmentManagerHeadeViewBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.common.model.LocationModel;
import com.chengdai.ehealthproject.model.healthcircle.adapters.LuntanListAdapter;
import com.chengdai.ehealthproject.model.healthmanager.acitivitys.AddHelathTaskLisActivity;
import com.chengdai.ehealthproject.model.healthmanager.acitivitys.DoHealthTaskActivity;
import com.chengdai.ehealthproject.model.healthmanager.acitivitys.HealthAssistantActivity;
import com.chengdai.ehealthproject.model.healthmanager.acitivitys.HealthDoTestQuesitionActivity;
import com.chengdai.ehealthproject.model.healthmanager.acitivitys.HealthMeaicalActivity;
import com.chengdai.ehealthproject.model.healthmanager.acitivitys.HealthTestActivity;
import com.chengdai.ehealthproject.model.healthmanager.acitivitys.HealthinfoActivity;
import com.chengdai.ehealthproject.model.healthmanager.model.TaskNowModel;
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
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

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

public class HealthManagerFragment extends BaseFragment{

    private FragmentManagerFirstBinding mBinding;

    private FragmentManagerHeadeViewBinding mHeadViewBinding;

    private int mPageStart=1;
    private LuntanListAdapter mAdapter;

    private String mTestCode;//用于打开测试页
    private String mTestTitle;;//用于打开测试页


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

        mBinding= DataBindingUtil.inflate(getLayoutInflater(savedInstanceState), R.layout.fragment_manager_first, null, false);


        getDataRequest(mActivity);


        initListView();

        initSpringView();

        initViewListener();

        if(SPUtilHelpr.isLoginNoStart()){
            getTestPageRequest();
            getNowHealthTask();
            getUserInfoRequest(null);
            getJifenRequest(null);
        }else{
            mHeadViewBinding.linNoTask.setVisibility(View.VISIBLE);
            mHeadViewBinding.linHaveTask.setVisibility(View.GONE);
        }

        return mBinding.getRoot();

    }

    private void initViewListener() {

        //健康资讯
        mHeadViewBinding.layoutMenuHealthInfo.setOnClickListener(v -> {
            HealthinfoActivity.open(mActivity);
        });
        //健康助手
        mHeadViewBinding.layoutMenuHealthAssistant.setOnClickListener(v -> {
            HealthAssistantActivity.open(mActivity);

        });
        //健康自测
        mHeadViewBinding.layoutMenuHealthTest.setOnClickListener(v -> {
            HealthTestActivity.open(mActivity,HealthTestActivity.TYPE1);
        });
        //健康风险评估
        mHeadViewBinding.imgHealthRisk.setOnClickListener(v -> {
            HealthTestActivity.open(mActivity,HealthTestActivity.TYPE2);

        });

        //j健康任务评测
        mHeadViewBinding.tvStartTest.setOnClickListener(v -> {
            if(TextUtils.isEmpty(mTestCode) || TextUtils.isEmpty(mTestTitle)){
                return;
            }

            HealthDoTestQuesitionActivity.open(mActivity,mTestCode,mTestTitle);
//            getNowHealthTask();
        });
        //添加健康任务
        mHeadViewBinding.tvAddHealthTask.setOnClickListener(v -> {
            if(!SPUtilHelpr.isLogin(mActivity)){
                return;
            }
            AddHelathTaskLisActivity.open(mActivity);
        });
        //添加健康任务
        mHeadViewBinding.tvAddHealthTask2.setOnClickListener(v -> {
            if(!SPUtilHelpr.isLogin(mActivity)){
                return;
            }
            AddHelathTaskLisActivity.open(mActivity);
        });
        //健康体检
        mHeadViewBinding.linMeaical.setOnClickListener(v -> {
            HealthMeaicalActivity.open(mActivity);
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
                if(SPUtilHelpr.isLoginNoStart()){
                    getTestPageRequest();
                    getNowHealthTask();
                    getUserInfoRequest(null);
                    getJifenRequest(null);
                }

                getDataRequest(null);

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

        mHeadViewBinding=  DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.fragment_manager_heade_view, null, false);

        mBinding.lvManagerFirst.addHeaderView(mHeadViewBinding.getRoot(),null,false);

        mAdapter = new LuntanListAdapter(mActivity,new ArrayList<>(),false);
        mBinding.lvManagerFirst.setAdapter(mAdapter);



    }


    @Subscribe
    public void locationSuccessful(AMapLocation aMapLocation){

        mHeadViewBinding.tvCityName.setText(aMapLocation.getCity());

        if(! TextUtils.isEmpty(aMapLocation.getAdCode())){
            mSubscription.add( getWeatheObservable(aMapLocation.getAdCode()).subscribe(weatherModel -> {
                setWeahterShow(weatherModel);
            },throwable -> {
                mHeadViewBinding.tvCityName.setText("金华");
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
        mHeadViewBinding.tvCityName.setText(cityname);
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
                    mHeadViewBinding.tvCityName.setText("金华");
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


            mHeadViewBinding.tvDay.setText("今天 ");
            mHeadViewBinding.tvWeather.setText("获取天气信息失败");

            return;
        }
        WeatherModel.ForecastsBean.CastsBean weahter= weatherModel.getForecasts().get(0).getCasts().get(0);

        if(weahter == null){
            return;
        }

        mHeadViewBinding.tvDay.setText("今天 "+ weahter.getDate());
        mHeadViewBinding.tvWeather.setText(weahter.getDayweather()+" "+weahter.getNighttemp()+"℃-"+weahter.getDaytemp()+"℃" );
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
                    mHeadViewBinding.tvJf.setText(StringUtils.showPrice(r.get(0).getAmount()));

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
                    mHeadViewBinding.txtName.setText(r.getNickname());

                    if(r.getUserExt() == null) return;

                    ImgUtils.loadImgLogo(mActivity, MyConfig.IMGURL+r.getUserExt().getPhoto(),mHeadViewBinding.imgUserLogo);
                    if(MyConfig.GENDERMAN.equals(r.getUserExt().getGender())){
                        ImgUtils.loadImgId(mActivity,R.mipmap.man,mHeadViewBinding.imgSex);
                    }else if (MyConfig.GENDERWOMAN.equals(r.getUserExt().getGender())){
                        ImgUtils.loadImgId(mActivity,R.mipmap.woman,mHeadViewBinding.imgSex);
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


    /**
     * 获取测试问卷
     */

    public void getTestPageRequest(){

        Map<String,String> map=new HashMap<>();

        map.put("type","1");
        map.put("parentKey","questionare_kind_1");
        map.put("companyCode", MyConfig.COMPANYCODE);
        map.put("systemCode", MyConfig.SYSTEMCODE);

        mSubscription.add(RetrofitUtils.getLoaderServer().getTestMenu("621906", StringUtils.getJsonToString(map))
                .compose(RxTransformerListHelper.applySchedulerResult(null))
                .filter(assistantMenuListModels -> assistantMenuListModels!=null && assistantMenuListModels.size()>0 & assistantMenuListModels.get(0)!=null)
                .map(assistantMenuListModels -> assistantMenuListModels.get(0).getDkey())
                .flatMap(type -> {
                    Map<String,String> map2=new HashMap<>();
                    map2.put("kind","questionare_kind_1");
                    map2.put("type",type);
                    map2.put("start","1");
                    map2.put("limit","10");
                    map2.put("status","1");
                    return   RetrofitUtils.getLoaderServer().getTestPageList("621205", StringUtils.getJsonToString(map2))
                            .compose(RxTransformerHelper.applySchedulerResult(null));

                })
                .filter(testPageListModel -> testPageListModel!=null && testPageListModel.getList()!=null && testPageListModel.getList().get(0)!=null)
                .map(testPageListModel -> testPageListModel.getList().get(0))

                .flatMap(code -> {
                    Map<String,String> map3=new HashMap<>();
                    map3.put("userId",SPUtilHelpr.getUserId());
                    map3.put("wjCode",code.getCode());

                    mTestCode=code.getCode();
                    mTestTitle=code.getTitle();

                    return RetrofitUtils.getLoaderServer().getTestScoreData("621248",StringUtils.getJsonToString(map3))
                            .compose(RxTransformerHelper.applySchedulerResult(null));

                })
                .filter(testPageListModel -> testPageListModel!=null)

                .subscribe(data -> {

                    if(data.getList() == null || data.getList().size()==0){
                        mHeadViewBinding.tvScore.setText("0分");
                        mHeadViewBinding.tvStartTest.setText("请测评");
                        return;
                    }
                    mHeadViewBinding.tvStartTest.setText("重测");

                    int score=data.getList().get(0).getScore();

                    mHeadViewBinding.tvScore.setText(score+"分");

                    mHeadViewBinding.imgHereOne.setVisibility(View.INVISIBLE);
                    mHeadViewBinding.imgHereTwo.setVisibility(View.INVISIBLE);
                    mHeadViewBinding.imgHereThree.setVisibility(View.INVISIBLE);
                    mHeadViewBinding.imgHereFour.setVisibility(View.INVISIBLE);

                    if(score<=25){
                        mHeadViewBinding.imgHereOne.setVisibility(View.VISIBLE);
                    }else if(score<=50){
                        mHeadViewBinding.imgHereTwo.setVisibility(View.VISIBLE);
                    } else if(score<=75){
                        mHeadViewBinding.imgHereThree.setVisibility(View.VISIBLE);
                    }else if(score<=100){
                        mHeadViewBinding.imgHereFour.setVisibility(View.VISIBLE);
                    }

                },Throwable::printStackTrace));

    }


    public void getNowHealthTask(){
        Map<String,String> map=new HashMap<>();
        map.put("userId",SPUtilHelpr.getUserId());

      mSubscription .add( RetrofitUtils.getLoaderServer().getNowHealthTask("621169",StringUtils.getJsonToString(map))
                .compose(RxTransformerListHelper.applySchedulerResult(mActivity))
                .subscribe(r -> {

                    if(r==null || r.size()==0){
                        mHeadViewBinding.linNoTask.setVisibility(View.VISIBLE);
                        mHeadViewBinding.linHaveTask.setVisibility(View.GONE);
                        return;
                    }
                    mHeadViewBinding.linNoTask.setVisibility(View.GONE);
                    mHeadViewBinding.linHaveTask.setVisibility(View.VISIBLE);
                    mHeadViewBinding.listTask.setAdapter(new CommonAdapter<TaskNowModel>(mActivity,R.layout.item_task_now,r) {
                        @Override
                        protected void convert(ViewHolder viewHolder, TaskNowModel item, int position) {
                          if(item == null){
                              return;
                          }

                            TextView tvGone=viewHolder.getView(R.id.tv_task_gone);

                          if(TextUtils.equals(item.getIsZX(),"0")){
                              tvGone.setText("未完成");
                              tvGone.setBackgroundResource(R.drawable.bg_line_blue);


                          }else{
                              tvGone.setText("已完成");
                              tvGone.setBackground(null);
                              tvGone.setTextColor(ContextCompat.getColor(mContext,R.color.txt_gray));
                          }

                            tvGone.setOnClickListener(v ->   DoHealthTaskActivity.open(mContext,item));

                          if(item.getHealthTask()!=null){

                          viewHolder.setText(R.id.tv_task_name,item.getHealthTask().getName());
                          viewHolder.setText(R.id.tv_task_info,item.getHealthTask().getSummary());
                          ImgUtils.loadImgURL(mActivity,MyConfig.IMGURL+item.getHealthTask().getLogo(),viewHolder.getView(R.id.img_task_title));
                          }

                        }
                    });


                },throwable -> {
                    mHeadViewBinding.linNoTask.setVisibility(View.VISIBLE);
                    mHeadViewBinding.linHaveTask.setVisibility(View.GONE);
                }));

    }

    @Subscribe
    public void HealthManagerFragmentEvent(EventBusModel eventBusModel){

        if(eventBusModel == null) return;

        if(TextUtils.equals(eventBusModel.getTag(),"HealthManagerFragmentRefhsh")){//刷新评测数据

            if(SPUtilHelpr.isLoginNoStart()){
                getTestPageRequest();
            }


        }else if(TextUtils.equals(eventBusModel.getTag(),"LOGINSTATEREFHSH") && eventBusModel.isEvBoolean()){//已登录
                getTestPageRequest();

                getNowHealthTask();

        }else if(TextUtils.equals(eventBusModel.getTag(),"LOGINSTATEREFHSH") && !eventBusModel.isEvBoolean()){
            mHeadViewBinding.tvScore.setText("0分");
            mHeadViewBinding.tvStartTest.setText("请测评");
            mHeadViewBinding.imgHereOne.setVisibility(View.INVISIBLE);
            mHeadViewBinding.imgHereTwo.setVisibility(View.INVISIBLE);
            mHeadViewBinding.imgHereThree.setVisibility(View.INVISIBLE);
            mHeadViewBinding.imgHereFour.setVisibility(View.INVISIBLE);

            mHeadViewBinding.linNoTask.setVisibility(View.VISIBLE);
            mHeadViewBinding.linHaveTask.setVisibility(View.GONE);

        }else  if(TextUtils.equals(eventBusModel.getTag(),"HealthManagerFragmentRefhsh_Task")){//刷新任务数据
            if(SPUtilHelpr.isLoginNoStart()){
                getNowHealthTask();
            }

        }

    }



}
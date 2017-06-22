package com.chengdai.ehealthproject.model.other;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.amap.api.location.AMapLocation;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.BaseLocationActivity;
import com.chengdai.ehealthproject.databinding.ActivityMainBinding;
import com.chengdai.ehealthproject.model.common.model.LocationModel;
import com.chengdai.ehealthproject.model.dataadapters.ViewPagerAdapter;
import com.chengdai.ehealthproject.model.healthcircle.HealthCircleFragment;
import com.chengdai.ehealthproject.model.healthcircle.activitys.SendEditInfoActivity;
import com.chengdai.ehealthproject.model.healthmanager.fragments.HealthManagerFragment;
import com.chengdai.ehealthproject.model.healthstore.HealthStoreFragment;
import com.chengdai.ehealthproject.model.tabmy.MyFragment;
import com.chengdai.ehealthproject.model.tabsurrounding.SurroundingFragment;
import com.chengdai.ehealthproject.model.user.LoginActivity;
import com.chengdai.ehealthproject.uitls.LogUtil;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页面
 */

public class MainActivity extends BaseLocationActivity {

    private ActivityMainBinding mainBinding;

    private int mTabIndex=0;//记录用户点击下标 用于未登录时恢复状态

    private int mShowIndex=0;//显示相应页面


    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context,int select){
        if(context==null){
            return;
        }
        Intent intent= new Intent(context,MainActivity.class);

        intent.putExtra("select",select);

        context.startActivity(intent);
    }

    @Override
    protected void locationSuccessful(AMapLocation aMapLocation) {
        LocationModel locationModel  = new LocationModel(aMapLocation.getCountry(),
                aMapLocation.getProvince(),aMapLocation.getCity(),aMapLocation.getDistrict(),aMapLocation.getLatitude()+"",aMapLocation.getLongitude()+"");
        SPUtilHelpr.saveLocationInfo(StringUtils.getJsonToString(locationModel));


    }

    @Override
    protected void locationFailure(AMapLocation aMapLocation) {
        SPUtilHelpr.saveLocationInfo("");
        LogUtil.E("定位失败"+aMapLocation.getErrorCode()+aMapLocation.getErrorInfo());
    }

    @Override
    protected void onNegativeButton() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_main, null, false);;

        addMainView(mainBinding.getRoot());

        hintTitleView();

        if(getIntent()!=null){
            mShowIndex=getIntent().getIntExtra("select",0);
        }

        initViewState();

         startLocation();
    }

    /**
     * 初始化View状态
     */
    private void initViewState() {
        mainBinding.pagerMain.setPagingEnabled(false);//禁止左右切换

        List<Fragment> fragments=new ArrayList<>(); //设置fragment数据

        fragments.add(new HealthManagerFragment());
        fragments.add(new HealthCircleFragment());
        fragments.add(new SurroundingFragment());
        fragments.add(new HealthStoreFragment());
        fragments.add(new MyFragment());

        mainBinding.pagerMain.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),fragments));

        mainBinding.pagerMain.setOffscreenPageLimit(fragments.size());

        mainBinding.layoutMainButtom.radioMainTab1.setOnClickListener(v -> {
            mainBinding.pagerMain.setCurrentItem(0,false);
            mTabIndex=1;
        });
        mainBinding.layoutMainButtom.radioMainTab2.setOnClickListener(v -> {

            mainBinding.pagerMain.setCurrentItem(1,false);
            mTabIndex=2;
        });
        mainBinding.layoutMainButtom.radioMainTab3.setOnClickListener(v -> {
            mainBinding.pagerMain.setCurrentItem(2,false);
            mTabIndex=3;
        });
        mainBinding.layoutMainButtom.radioMainTab4.setOnClickListener(v -> {
            mainBinding.pagerMain.setCurrentItem(3,false);
            mTabIndex=4;
        });
         mainBinding.layoutMainButtom.radioMainTab5.setOnClickListener(v -> {
             if(!SPUtilHelpr.isLoginNoStart()){

                 switch (mTabIndex){
                     case 1:
                         mainBinding.layoutMainButtom.radioMainTab1.setChecked(true);
                         break;
                     case 2:
                         mainBinding.layoutMainButtom.radioMainTab2.setChecked(true);
                         break;
                     case 3:
                         mainBinding.layoutMainButtom.radioMainTab3.setChecked(true);
                         break;
                     case 4:
                         mainBinding.layoutMainButtom.radioMainTab4.setChecked(true);
                         break;
                 }

                 LoginActivity.open(this,true);
             }else{
                 mTabIndex=5;
                 mainBinding.pagerMain.setCurrentItem(4,false);
             }
        });

        mainBinding.pagerMain.setCurrentItem(mShowIndex,false);

/*       mSubscription.add( RxRadioGroup.checkedChanges(mainBinding.layoutMainButtom.radiogroup) //点击切换
               .subscribe(integer -> {
                   switch (integer){
                       case R.id.radio_main_tab_1:
                           mainBinding.pagerMain.setCurrentItem(0,false);
                           break;
                       case R.id.radio_main_tab_2:
                           mainBinding.pagerMain.setCurrentItem(1,false);
                           break;
                       case R.id.radio_main_tab_3:
                           mainBinding.pagerMain.setCurrentItem(2,false);
                           break;
                       case R.id.radio_main_tab_4:
                           mainBinding.pagerMain.setCurrentItem(3,false);
                           break;
                       case R.id.radio_main_tab_5:

                           if(!SPUtilHelpr.isLogin(this)){
                               LoginActivity.open(this,false);
                           }else{
                               mainBinding.pagerMain.setCurrentItem(4,false);
                           }


                           break;
                   }

          },Throwable::printStackTrace));*/

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SPUtilHelpr.saveLocationInfo("");
    }
}

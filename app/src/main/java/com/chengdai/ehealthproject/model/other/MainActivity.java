package com.chengdai.ehealthproject.model.other;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.BaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityMainBinding;
import com.chengdai.ehealthproject.model.dataadapters.ViewPagerAdapter;
import com.chengdai.ehealthproject.model.healthcircle.HealthCircleFragment;
import com.chengdai.ehealthproject.model.healthmanager.fragments.HealthManagerFragment;
import com.chengdai.ehealthproject.model.healthstore.HealthStoreFragment;
import com.chengdai.ehealthproject.model.tabmy.MyFragment;
import com.chengdai.ehealthproject.model.tabsurrounding.SurroundingFragment;
import com.chengdai.ehealthproject.model.user.LoginActivity;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxRadioGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页面
 */

public class MainActivity extends BaseActivity {

    private ActivityMainBinding mainBinding;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context){
        if(context==null){
            return;
        }
        context.startActivity(new Intent(context,MainActivity.class));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initViewState();
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

       mSubscription.add( RxRadioGroup.checkedChanges(mainBinding.layoutMainButtom.radiogroup) //点击切换
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
                           LoginActivity.open(this);
                           mainBinding.pagerMain.setCurrentItem(4,false);
                           break;
                   }

          },Throwable::printStackTrace));

    }
}

package com.chengdai.ehealthproject.model.other;

import android.content.Intent;
import android.os.Bundle;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.BaseActivity;
import com.chengdai.ehealthproject.model.user.LoginActivity;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;


import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**启动页
 * Created by 李先俊 on 2017/6/8.
 */

public class WelcomeAcitivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 用于第一次安装APP，进入到除这个启动activity的其他activity，点击home键，再点击桌面启动图标时，
        // 系统会重启此activty，而不是直接打开之前已经打开过的activity，因此需要关闭此activity

        if (getIntent()!=null && (getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }

        setContentView(R.layout.activity_welcom);

        mSubscription.add(Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(aLong -> {//延迟两秒进行跳转
                    MainActivity.open(this,1);
           /*   if(SPUtilHelpr.isLogin()){
                  MainActivity.open(this);
              }else{
                  LoginActivity.open(this);
              }*/
                    finish();
                },Throwable::printStackTrace));

    }

}

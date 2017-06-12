package com.chengdai.ehealthproject.model.other;

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
        setContentView(R.layout.activity_welcom);
    }

    @Override
    protected void onResume() {
        super.onResume();
      mSubscription.add(Observable.timer(2, TimeUnit.SECONDS)
              .subscribe(aLong -> {//延迟两秒进行跳转
                  MainActivity.open(this);
           /*   if(SPUtilHelpr.isLogin()){
                  MainActivity.open(this);
              }else{
                  LoginActivity.open(this);
              }*/
              finish();
        },Throwable::printStackTrace));

    }
}

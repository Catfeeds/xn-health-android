package com.chengdai.ehealthproject.model.tabmy.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivitySettingBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.tabmy.model.OrderRecordModel;
import com.chengdai.ehealthproject.model.user.LoginActivity;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by 李先俊 on 2017/6/16.
 */

public class SettingActivity extends AbsBaseActivity{

    private ActivitySettingBinding mBinding;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,SettingActivity.class);

        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_setting, null, false);
        addMainView(mBinding.getRoot());
        setTopTitle(getString(R.string.txt_setting));
        setSubLeftImgState(true);

        mBinding.txtLogout.setOnClickListener(v -> {
            SPUtilHelpr.saveUserId("");
            SPUtilHelpr.saveUserToken("");

            EventBusModel eventBusModel=new EventBusModel();
            eventBusModel.setTag("AllFINISH");
            EventBus.getDefault().post(eventBusModel); //结束掉所有界面

            EventBusModel eventBusModel2=new EventBusModel();
            eventBusModel2.setTag("LOGINSTATEREFHSH");
            eventBusModel2.setEvBoolean(false);
            EventBus.getDefault().post(eventBusModel2); //刷新未登录数据
            LoginActivity.open(this,true);
            finish();
        });

    }
}

package com.chengdai.ehealthproject.uitls;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.chengdai.ehealthproject.base.BaseApplication;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.user.LoginActivity;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;

import org.greenrobot.eventbus.EventBus;

/**
 * 用于处理服务器 错误码
 * Created by Administrator on 2016-09-06.
 */
public class OnLoginFailure {

    public static void StartDoFailure(final Context context, String errorMessage) {

        SPUtilHelpr.saveUserId("");
        SPUtilHelpr.saveUserToken("");

        if (context == null) {
            Intent intent = new Intent(BaseApplication.getInstance(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("isStartMain", false);
            BaseApplication.getInstance().startActivity(intent);
            ToastUtil.show(BaseApplication.getInstance(),errorMessage);
            return;
        }

        try{
            if(context instanceof  Activity){
                LoginActivity.open(context,false);
            }

        }catch (Exception e){

        }
    }

}

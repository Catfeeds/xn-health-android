package com.chengdai.ehealthproject.uitls.nets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.chengdai.ehealthproject.base.BaseApplication;
import com.chengdai.ehealthproject.model.other.MainActivity;
import com.chengdai.ehealthproject.model.user.LoginActivity;
import com.chengdai.ehealthproject.uitls.ToastUtil;
import com.chengdai.ehealthproject.weigit.dialog.CommonDialog;

/**
 * 用于处理服务器 错误码
 * Created by Administrator on 2016-09-06.
 */
public class OnOkFailure {

    public static void StartDoFailure(final Context context, String errorMessage) {

//        ToastUtil.show(context,"请先登录");

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

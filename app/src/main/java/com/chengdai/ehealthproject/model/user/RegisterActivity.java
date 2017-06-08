package com.chengdai.ehealthproject.model.user;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityLoginBinding;
import com.chengdai.ehealthproject.databinding.ActivityRegisterBinding;
import com.chengdai.ehealthproject.uitls.AppUtils;
import com.chengdai.ehealthproject.uitls.ImgUtils;

/**注册
 * Created by 李先俊 on 2017/6/8.
 */

public class RegisterActivity extends AbsBaseActivity {

    private ActivityRegisterBinding activityLoginBinding;


    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context){
        if(context==null){
            return;
        }
        context.startActivity(new Intent(context,RegisterActivity.class));
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_register, null, false);
        addMainView(activityLoginBinding.getRoot());

        setTopTitle(getString(R.string.txt_register));

        ImgUtils.loadImgIdforRound(this,R.mipmap.icon,activityLoginBinding.imgLoginIcon);

        activityLoginBinding.btnSendCode.setOnClickListener(v -> { //启动倒计时
            mSubscription.add(AppUtils.startCodeDown(60,activityLoginBinding.btnSendCode));
        });

    }
}

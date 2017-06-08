package com.chengdai.ehealthproject.model.user;

import android.content.Context;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityLoginBinding;
import com.chengdai.ehealthproject.model.other.MainActivity;
import com.chengdai.ehealthproject.uitls.ImgUtils;

/**
 * Created by 李先俊 on 2017/6/8.
 */

public class LoginActivity extends AbsBaseActivity {

    private ActivityLoginBinding activityLoginBinding;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context){
        if(context==null){
            return;
        }
        context.startActivity(new Intent(context,LoginActivity.class));
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_login, null, false);
        addMainView(activityLoginBinding.getRoot());

        setTopTitle(getString(R.string.txt_login));

        ImgUtils.loadImgIdforRound(this,R.mipmap.icon,activityLoginBinding.imgLoginIcon);

        activityLoginBinding.tvStartRegistr.setOnClickListener(v -> {
            RegisterActivity.open(this);
        });

    }
}

package com.chengdai.ehealthproject.model.healthmanager.acitivitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityMedicalBinding;
import com.chengdai.ehealthproject.databinding.ActivitySettingBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.user.LoginActivity;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;

import org.greenrobot.eventbus.EventBus;

/**健康体检
 * Created by 李先俊 on 2017/6/16.
 */

public class HealthMeaicalActivity extends AbsBaseActivity{

    private ActivityMedicalBinding mBinding;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,HealthMeaicalActivity.class);

        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_medical, null, false);
        addMainView(mBinding.getRoot());
        setTopTitle("体检");
        setSubLeftImgState(true);

        //心率测试
        mBinding.fraXinlvTest.setOnClickListener(v -> {
            HealthMeaicalTestActivity.openXinlv(this);
        });
        //肺活量
        mBinding.fraFeiTest.setOnClickListener(v -> {
            HealthMeaicalTestActivity.openFei(this);
        });
        //血氧测试
        mBinding.fraXueyangTest.setOnClickListener(v -> {
            HealthMeaicalTestActivity.openXueYang(this);
        });

    }
}

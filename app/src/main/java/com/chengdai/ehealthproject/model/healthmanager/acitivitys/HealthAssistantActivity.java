package com.chengdai.ehealthproject.model.healthmanager.acitivitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityHealthAssistantBinding;

/**健康助手
 * Created by 李先俊 on 2017/6/23.
 */

public class HealthAssistantActivity extends AbsBaseActivity{

    private ActivityHealthAssistantBinding mBinding;


    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,HealthAssistantActivity.class);

        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_health_assistant, null, false);

        addMainView(mBinding.getRoot());

        setTopTitle("健康助手");

        setSubLeftImgState(true);

        setViewListener();

    }

    /**
     * 设置View事件
     */
    private void setViewListener() {

        //食物计算器
        mBinding.fraAssistantMenu1.setOnClickListener(v -> {
            AssistantMenuActivity.open(this);
        });
        //膳食建议
        mBinding.fraAssistantMenu2.setOnClickListener(v -> {
            EatCalculateActivity.open(this);
        });

     //医学美容
        mBinding.fraAssistantMenu3.setOnClickListener(v -> {
            BeautyActivity.open(this);
        });
        //BMI
        mBinding.fraAssistantMenu4.setOnClickListener(v -> {
            BMICalculateActivity.open(this);
        });


    }
}

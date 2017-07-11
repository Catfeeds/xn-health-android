package com.chengdai.ehealthproject.model.healthmanager.acitivitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityBmiCalculateBinding;
import com.chengdai.ehealthproject.databinding.ActivitySettingBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.user.LoginActivity;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.uitls.LogUtil;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;

/**BMI指数页面
 * Created by 李先俊 on 2017/6/16.
 */

public class BMICalculateActivity extends AbsBaseActivity{

    private ActivityBmiCalculateBinding mBinding;

    private boolean boyType=true;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,BMICalculateActivity.class);

        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_bmi_calculate, null, false);
        addMainView(mBinding.getRoot());
        setTopTitle("BMI指数");
        setSubLeftImgState(true);
        mBinding.editWeight.setSelection(mBinding.editWeight.getText().toString().length());
        mBinding.eidtHeight.setSelection(mBinding.eidtHeight.getText().toString().length());
        mBinding.imgBoy.setOnClickListener(v -> {

            if(boyType){
                mBinding.imgBoy.setImageResource(R.mipmap.life_girl);

            }else{
                mBinding.imgBoy.setImageResource(R.mipmap.life_boy);
            }
            boyType=!boyType;
        });

        mBinding.tvStartCalculate.setOnClickListener(v -> {
            if(TextUtils.isEmpty(mBinding.editWeight.getText().toString())){
                showToast("请输入体重");
                return;
            }

            if(TextUtils.isEmpty(mBinding.eidtHeight.getText().toString())){
                showToast("请输入身高");
                return;
            }
            BigDecimal height=new BigDecimal(mBinding.eidtHeight.getText().toString());
            BigDecimal weight=new BigDecimal(mBinding.editWeight.getText().toString());


            if(height.doubleValue()<80 || height.doubleValue()>300){
                showToast("请输入正确的身高");
                return;
            }

            if(weight.doubleValue()<30 ){
                showToast("请输入正确的体重");
                return;
            }

            BigDecimal h=height.divide(new BigDecimal(100),2,BigDecimal.ROUND_UNNECESSARY);
            BigDecimal hh=h.multiply(h);
            BigDecimal bmi=weight.divide(hh,2,BigDecimal.ROUND_HALF_UP);
            BMICalculateDetalisActivity.open(this,bmi.doubleValue()+"");


        });


    }

}

package com.chengdai.ehealthproject.model.healthmanager.acitivitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityBmiCalculateBinding;

import java.math.BigDecimal;

/**膳食建议
 * Created by 李先俊 on 2017/6/16.
 */

public class EatCalculateActivity extends AbsBaseActivity{

    private ActivityBmiCalculateBinding mBinding;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,EatCalculateActivity.class);

        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_bmi_calculate, null, false);
        addMainView(mBinding.getRoot());
        setTopTitle("膳食建议");
        setSubLeftImgState(true);

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

            BigDecimal h=height.divide(new BigDecimal(100));

            BigDecimal hh=h.multiply(h);

            BigDecimal bmi=weight.divide(hh,2);

            EatCalculateDetalisActivity.open(this,"");


        });


    }

}

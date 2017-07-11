package com.chengdai.ehealthproject.model.healthmanager.acitivitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityBmiCalculateBinding;
import com.chengdai.ehealthproject.databinding.ActivityEatCalculateBinding;

import java.math.BigDecimal;

/**膳食建议
 * Created by 李先俊 on 2017/6/16.
 */

public class EatCalculateActivity extends AbsBaseActivity{

    private ActivityEatCalculateBinding mBinding;

    private boolean boyType=true;
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

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_eat_calculate, null, false);
        addMainView(mBinding.getRoot());
        setTopTitle("膳食建议");
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
/*女性REE = (10 × 体重) ＋ (6.25 × 身高) - (5 × 年龄) - 161
男性REE = (10 × 体重) ＋ (6.25 × 身高) - (5 × 年龄) ＋ 5*/


               if(!boyType){
                   EatCalculateDetalisActivity.open(this,(10*weight.intValue()+6.25*height.intValue()-5*25-161)+"");
               }else{
                   EatCalculateDetalisActivity.open(this,(10*weight.intValue()+6.25*height.intValue()-5*25+5)+"");
               }

        });


    }

}

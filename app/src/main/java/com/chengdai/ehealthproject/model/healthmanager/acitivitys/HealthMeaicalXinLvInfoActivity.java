package com.chengdai.ehealthproject.model.healthmanager.acitivitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityMedicalBinding;
import com.chengdai.ehealthproject.databinding.ActivityXinlvTestDetailsBinding;

/**心率测试
 * Created by 李先俊 on 2017/6/16.
 */

public class HealthMeaicalXinLvInfoActivity extends AbsBaseActivity{

    private ActivityXinlvTestDetailsBinding mBinding;

    private int mNumber;

    private int mType;

    /**
     * 打开心率测试
     * @param context
     */
    public static void openXinlv(Context context,int number){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,HealthMeaicalXinLvInfoActivity.class);
        intent.putExtra("type",1);
        intent.putExtra("number",number);
        context.startActivity(intent);
    }
    /**
     * 打开肺活量测试
     * @param context
     */
    public static void openFei(Context context,int number){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,HealthMeaicalXinLvInfoActivity.class);
        intent.putExtra("type",2);
        intent.putExtra("number",number);
        context.startActivity(intent);
    }
    /**
     * 打开肺活量测试
     * @param context
     */
    public static void openXueYang(Context context,int number){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,HealthMeaicalXinLvInfoActivity.class);
        intent.putExtra("type",3);
        intent.putExtra("number",number);
        context.startActivity(intent);
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_xinlv_test_details, null, false);
        addMainView(mBinding.getRoot());

        setSubLeftImgState(true);


        if(getIntent() !=null){
            mNumber=getIntent().getIntExtra("number",1);
            mType=getIntent().getIntExtra("type",1);
        }
        mBinding.tvNum.setText(mNumber+"");

        setTypeShow();

    }

    private void setTypeShow() {

        if(mType == 1){//心率测试
            setTopTitle("心率测量结果");
            mBinding.linXinlv.setVisibility(View.VISIBLE);

            if(mNumber <40){
                mBinding.tvInfo.setText(getString(R.string.txt_xinlv_40));
            }else if(mNumber==40 && mNumber<60){
                mBinding.tvInfo.setText(getString(R.string.txt_xinlv_40_60));
            }
            else if(mNumber>=60 && mNumber<100){
                mBinding.tvInfo.setText(getString(R.string.txt_xinlv_60_100));
            }
            else if(mNumber>100 ){
                mBinding.tvInfo.setText(getString(R.string.txt_xinlv_100));
            }

        }else if(mType == 2){//肺活量测试
            setTopTitle("肺活量测量结果");
            mBinding.linFei.setVisibility(View.VISIBLE);
            if(mNumber<=1500 ){
                mBinding.tvInfo.setText(getString(R.string.txt_fei_1500));
            }else if(mNumber>1500 && mNumber<=3000 ){
                mBinding.tvInfo.setText(getString(R.string.txt_fei_1500_3000));
            }else if(mNumber>3000 && mNumber<=4500 ){
                mBinding.tvInfo.setText(getString(R.string.txt_fei_3000_4500));
            }else if(mNumber>4500){
                mBinding.tvInfo.setText(getString(R.string.txt_fei_4500));
            }

        }else{//血氧测试
            setTopTitle("血氧测量结果");
            mBinding.linXueyang.setVisibility(View.VISIBLE);
            if(mNumber<90  ){
                mBinding.tvInfo.setText(getString(R.string.txt_xueyang_90));
            }else if(mNumber==90 && mNumber<93 ){
                mBinding.tvInfo.setText(getString(R.string.txt_xueyang_90_93));
            }else if(mNumber>=93 ){
                mBinding.tvInfo.setText(getString(R.string.txt_xueyang_93));
            }
        }

    }
}

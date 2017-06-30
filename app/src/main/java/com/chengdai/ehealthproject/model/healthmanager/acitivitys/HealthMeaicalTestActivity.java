package com.chengdai.ehealthproject.model.healthmanager.acitivitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityMedicalBinding;
import com.chengdai.ehealthproject.databinding.ActivityXinlvTestBinding;

/**健康体检测试
 * Created by 李先俊 on 2017/6/16.
 */

public class HealthMeaicalTestActivity extends AbsBaseActivity{

    private ActivityXinlvTestBinding mBinding;

    private int mType;

    /**
     * 打开心率测试
     * @param context
     */
    public static void openXinlv(Context context){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,HealthMeaicalTestActivity.class);
        intent.putExtra("type",1);
        context.startActivity(intent);
    }
    /**
     * 打开肺活量测试
     * @param context
     */
    public static void openFei(Context context){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,HealthMeaicalTestActivity.class);
        intent.putExtra("type",2);
        context.startActivity(intent);
    }
    /**
     * 打开肺活量测试
     * @param context
     */
    public static void openXueYang(Context context){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,HealthMeaicalTestActivity.class);
        intent.putExtra("type",3);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_xinlv_test, null, false);
        addMainView(mBinding.getRoot());

        if(getIntent()!=null){
            mType=getIntent().getIntExtra("type",1);
        }

        setTypeState();


        setSubLeftImgState(true);


        mBinding.tvSure.setOnClickListener(v -> {

            if(TextUtils.isEmpty(mBinding.editNum.getText().toString())){
                showToast("请输入数值");
                return;
            }

            int  inputNum=Integer.parseInt(mBinding.editNum.getText().toString());
            if(mType == 1){//心率测试

                if(inputNum<30|| inputNum>220){
                    showToast("请输入正确的心率值");
                    return;
                }

                HealthMeaicalXinLvInfoActivity.openXinlv(this,inputNum);
            }else if(mType == 2){//肺活量测试
                if( inputNum>10000){
                    showToast("请输入正确的肺活量");
                    return;
                }
                HealthMeaicalXinLvInfoActivity.openFei(this,inputNum);
            }else{//血氧测试
                if( inputNum<60){
                    showToast("请输入正确的血氧饱和度");
                    return;
                }
                HealthMeaicalXinLvInfoActivity.openXueYang(this,inputNum);
            }
        });
    }

    private void setTypeState() {

        if(mType == 1){//心率测试
            mBinding.tvInputTitle.setText("心率值");
            mBinding.tvTitle.setText(getString(R.string.txt_xinlv_test));
            mBinding.tvTips.setText("请输入心率值");
            mBinding.editNum.setHint("30-220");
            setTopTitle("心率测试");
        }else if(mType == 2){//肺活量测试
            mBinding.tvInputTitle.setText("肺活量");
            mBinding.tvTitle.setText(getString(R.string.txt_fei_test));
            mBinding.tvTips.setText("请输入肺活量");
            mBinding.editNum.setHint("0-10000");
            setTopTitle("肺活量测试");
        }else{//血氧测试
            mBinding.tvInputTitle.setText("血氧饱和度");
            mBinding.tvTitle.setText(getString(R.string.txt_xuey_test));
            mBinding.tvTips.setText("请输入血氧饱和度");
            mBinding.editNum.setHint("60-100");
            setTopTitle("血氧测试");
        }


    }
}

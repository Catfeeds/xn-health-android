package com.chengdai.ehealthproject.model.healthmanager.acitivitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityBmiDetailsBinding;
import com.chengdai.ehealthproject.databinding.ActivityEatDetailsBinding;

/**BMI指数页面
 * Created by 李先俊 on 2017/6/16.
 */

public class EatCalculateDetalisActivity extends AbsBaseActivity{

    private ActivityEatDetailsBinding mBinding;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context,String bminum){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,EatCalculateDetalisActivity.class);
        intent.putExtra("bimnum",bminum);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_eat_details, null, false);
        addMainView(mBinding.getRoot());
        setTopTitle("膳食建议");
        setSubLeftImgState(true);

        if(getIntent()!=null){
            mBinding.tvNum.setText(getIntent().getStringExtra("bimnum")+"卡");
        }

    }

}

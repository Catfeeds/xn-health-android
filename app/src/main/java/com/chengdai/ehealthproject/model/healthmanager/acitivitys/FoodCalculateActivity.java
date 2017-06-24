package com.chengdai.ehealthproject.model.healthmanager.acitivitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityFoodCalculateBinding;
import com.chengdai.ehealthproject.model.healthmanager.adapters.FoodHotListData;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;

import java.util.HashMap;
import java.util.Map;

/**食物计算器详情
 * Created by 李先俊 on 2017/6/23.
 */

public class FoodCalculateActivity extends AbsBaseActivity {

    public ActivityFoodCalculateBinding mBinding;

    private FoodHotListData mData;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context,FoodHotListData code){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,FoodCalculateActivity.class);
        intent.putExtra("data",code);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_food_calculate, null, false);

        if(getIntent()!=null){
            mData=getIntent().getParcelableExtra("data");
        }

        setTopTitle("食物计算器");

        setSubLeftImgState(true);

        addMainView(mBinding.getRoot());

        if(mData !=null){
            mBinding.tvFoodName.setText("每100g"+mData.getName()+"热量");
            mBinding.tvHotSum.setText(mData.getCalorie()+"卡");

        }
    }


}
